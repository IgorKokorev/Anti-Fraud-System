package antifraud.controllers;

import antifraud.DBtables.CardLimitsDB;
import antifraud.DBtables.TransactionDB;
import antifraud.DTORequests.TransactionToAdd;
import antifraud.DTOResponses.TransactionResultResponse;
import antifraud.config.CheckData;
import antifraud.config.TransactionResult;
import antifraud.repository.CardLimitsDBRepository;
import antifraud.repository.StolencardDBRepository;
import antifraud.repository.SuspiciousIpDBRepository;
import antifraud.repository.TransactionDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.temporal.ChronoUnit;
import java.util.Optional;

@RestController
public class TransactionController {
    @Autowired
    SuspiciousIpDBRepository ipRepository;
    @Autowired
    StolencardDBRepository cardRepo;
    @Autowired
    TransactionDBRepository transactionRepo;
    @Autowired
    CardLimitsDBRepository cardLimitRepo;

    // Creating transaction result and info
    TransactionResult result;
    StringBuilder infoBuilder;

    @PostMapping(value = "/api/antifraud/transaction")
    public TransactionResultResponse makeTransaction(@RequestBody TransactionToAdd transaction) {
        // Checking data correctness
        checkTransactionParameters(transaction);

        // By amount
        checkTransactionByAmount(transaction);

        // By card number
        checkTransactionByCardNumber(transaction);

        // By ip
        checkTransactionByIp(transaction);

        // By ip correlation
        checkTransactionByIpCorrelation(transaction);

        // By region correlation
        checkTransactionByRegionCorrelation(transaction);

        String info = infoBuilder.toString();

        //saving to DB
        TransactionDB transactionDB = new TransactionDB(transaction, result);
        transactionDB = transactionRepo.save(transactionDB);

        return new TransactionResultResponse(result, info);
    }

    private static void checkTransactionParameters(TransactionToAdd transaction) {
        /*        LocalDateTime dateTime;
        try {
            dateTime = LocalDateTime.parse(transaction.getDate());
        } catch (DateTimeParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Incorrect date format");
        }*/

        if (!CheckData.isIPcorrect(transaction.getIp())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Incorrect IP");
        }

        if (!CheckData.isCardNumberCorrect(transaction.getNumber())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Incorrect card number");
        }

        if (transaction.getAmount() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Amount should be a positive long number");
        }

        if (!CheckData.isRegionCorrect(transaction.getRegion())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Incorrect region code");
        }
    }

    private void checkTransactionByAmount(TransactionToAdd transaction) {

        Optional<CardLimitsDB> optionalCardLimits = cardLimitRepo.findByNumber(transaction.getNumber());
        CardLimitsDB cardLimits;
        if (optionalCardLimits.isEmpty()) {
            cardLimits = new CardLimitsDB(transaction.getNumber());
        } else {
            cardLimits = optionalCardLimits.get();
        }

        if (transaction.getAmount() > cardLimits.getMaxManual()) {
            result = TransactionResult.PROHIBITED;
            infoBuilder = new StringBuilder("amount");
        } else if (transaction.getAmount() > cardLimits.getMaxAllowed()) {
            result = TransactionResult.MANUAL_PROCESSING;
            infoBuilder = new StringBuilder("amount");
        } else {
            result = TransactionResult.ALLOWED;
            infoBuilder = new StringBuilder("none");
        }
    }

    private void checkTransactionByCardNumber(TransactionToAdd transaction) {
        if (cardRepo.existsByNumber(transaction.getNumber())) {
            if (result.equals(TransactionResult.PROHIBITED)) {
                infoBuilder.append(", card-number");
            } else {
                infoBuilder = new StringBuilder("card-number");
                result = TransactionResult.PROHIBITED;
            }
        }
    }

    private void checkTransactionByIp(TransactionToAdd transaction) {
        if (ipRepository.existsByIp(transaction.getIp())) {
            if (result.equals(TransactionResult.PROHIBITED)) {
                infoBuilder.append(", ip");
            } else {
                infoBuilder = new StringBuilder("ip");
                result = TransactionResult.PROHIBITED;
            }
        }
    }

    private void checkTransactionByIpCorrelation(TransactionToAdd transaction) {
        int ipCount = transactionRepo.countIpsByTime(
                transaction.getIp(),
                transaction.getNumber(),
                transaction.getDate().minus(1, ChronoUnit.HOURS),
                transaction.getDate()
        );
        if (ipCount > 2) {
            if (result.equals(TransactionResult.PROHIBITED)) {
                infoBuilder.append(", ip-correlation");
            } else {
                infoBuilder = new StringBuilder("ip-correlation");
                result = TransactionResult.PROHIBITED;
            }
        } else if (ipCount == 2) {
            if (result.equals(TransactionResult.MANUAL_PROCESSING)) {
                infoBuilder.append(", ip-correlation");
            } else if (result.equals(TransactionResult.ALLOWED)) {
                infoBuilder = new StringBuilder("ip-correlation");
                result = TransactionResult.MANUAL_PROCESSING;
            }
        }
    }

    private void checkTransactionByRegionCorrelation(TransactionToAdd transaction) {
        int regionCount = transactionRepo.countRegionsByTime(
                transaction.getRegion(),
                transaction.getNumber(),
                transaction.getDate().minus(1, ChronoUnit.HOURS),
                transaction.getDate()
        );
        if (regionCount > 2) {
            if (result.equals(TransactionResult.PROHIBITED)) {
                infoBuilder.append(", region-correlation");
            } else {
                infoBuilder = new StringBuilder("region-correlation");
                result = TransactionResult.PROHIBITED;
            }
        } else if (regionCount == 2) {
            if (result.equals(TransactionResult.MANUAL_PROCESSING)) {
                infoBuilder.append(", region-correlation");
            } else if (result.equals(TransactionResult.ALLOWED)) {
                infoBuilder = new StringBuilder("region-correlation");
                result = TransactionResult.MANUAL_PROCESSING;
            }
        }
    }
}
