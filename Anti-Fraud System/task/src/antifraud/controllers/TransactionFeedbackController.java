package antifraud.controllers;

import antifraud.DBtables.CardLimitsDB;
import antifraud.DBtables.TransactionDB;
import antifraud.DTORequests.FeedbackRequest;
import antifraud.config.TransactionResult;
import antifraud.repository.CardLimitsDBRepository;
import antifraud.repository.TransactionDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Optional;

@RestController
public class TransactionFeedbackController {

    @Autowired
    TransactionDBRepository transactionRepo;
    @Autowired
    CardLimitsDBRepository cardLimitRepo;

    @PutMapping(value = "/api/antifraud/transaction")
    public TransactionDB transactionFeedback(@RequestBody FeedbackRequest feedbackRequest) {

        Optional<TransactionDB> optionalTr = transactionRepo.findByTransactionId(feedbackRequest.getTransactionId());
        TransactionDB tr = optionalTr.get();

        checkInputData(feedbackRequest, optionalTr, tr);

        CardLimitsDB cardLimits = getCardLimits(tr);

        updateCardLimits(feedbackRequest, tr, cardLimits);
        cardLimits = cardLimitRepo.save(cardLimits);

        tr.setFeedback(feedbackRequest.getFeedback().toString());
        tr = transactionRepo.save(tr);
        return tr;
    }

    private static void updateCardLimits(FeedbackRequest feedbackRequest, TransactionDB tr, CardLimitsDB cardLimits) {
        if (feedbackRequest.getFeedback().equals(TransactionResult.ALLOWED)) {
            cardLimits.setMaxAllowed(Double.valueOf(Math.ceil(0.8 * cardLimits.getMaxAllowed() + 0.2 * tr.getAmount())).longValue());
            if (tr.getResult().equals(TransactionResult.PROHIBITED)) {
                cardLimits.setMaxManual(Double.valueOf(Math.ceil(0.8 * cardLimits.getMaxManual() + 0.2 * tr.getAmount())).longValue());
            }
        } else if (feedbackRequest.getFeedback().equals(TransactionResult.MANUAL_PROCESSING)) {
            if (tr.getResult().equals(TransactionResult.PROHIBITED)) {
                cardLimits.setMaxManual(Double.valueOf(Math.ceil(0.8 * cardLimits.getMaxManual() + 0.2 * tr.getAmount())).longValue());
            } else if (tr.getResult().equals(TransactionResult.ALLOWED)) {
                cardLimits.setMaxAllowed(Double.valueOf(Math.ceil(0.8 * cardLimits.getMaxAllowed() - 0.2 * tr.getAmount())).longValue());
            }
        } else {
            cardLimits.setMaxManual(Double.valueOf(Math.ceil(0.8 * cardLimits.getMaxManual() - 0.2 * tr.getAmount())).longValue());
            if (tr.getResult().equals(TransactionResult.ALLOWED)) {
                cardLimits.setMaxAllowed(Double.valueOf(Math.ceil(0.8 * cardLimits.getMaxAllowed() - 0.2 * tr.getAmount())).longValue());
            }
        }
    }

    private CardLimitsDB getCardLimits(TransactionDB tr) {
        Optional<CardLimitsDB> optionalCardLimits = cardLimitRepo.findByNumber(tr.getNumber());
        CardLimitsDB cardLimits;
        if (optionalCardLimits.isEmpty()) {
            cardLimits = new CardLimitsDB(tr.getNumber());
        } else {
            cardLimits = optionalCardLimits.get();
        }
        return cardLimits;
    }

    private static void checkInputData(FeedbackRequest feedbackRequest, Optional<TransactionDB> optionalTr, TransactionDB tr) {
        if (!Arrays.stream(TransactionResult.values()).toList().contains(feedbackRequest.getFeedback())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Incorrect feedback");
        }

        if (optionalTr.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Transaction not found");
        }

        if (!tr.getFeedback().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Transaction already has feedback");
        }

        if (feedbackRequest.getFeedback().equals(tr.getResult())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Feedback equals to result of operation");
        }
    }

}
