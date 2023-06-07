package antifraud.controllers;

import antifraud.DBtables.TransactionDB;
import antifraud.DTOResponses.TransactionResultResponse;
import antifraud.config.CheckData;
import antifraud.repository.TransactionDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class HistoryController {
    @Autowired
    TransactionDBRepository transactionRepo;

    @GetMapping(value = "/api/antifraud/history")
    public List<TransactionDB> transactionsHystory() {
        return transactionRepo.findAllByOrderByTransactionIdAsc();
    }

    @GetMapping(value = "/api/antifraud/history/{number}")
    public List<TransactionDB> transactionsHystoryByNumber(@PathVariable String number) {
        if (!CheckData.isCardNumberCorrect(number)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Incorrect card number");
        }

        List<TransactionDB> list = transactionRepo.findAllByNumberOrderByTransactionIdAsc(number);
        if (list.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Card not found");
        }

        return list;
    }
}
