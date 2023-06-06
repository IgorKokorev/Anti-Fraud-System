package antifraud.controllers;

import antifraud.DBtables.StolencardDB;
import antifraud.DTORequests.StolencardToAdd;
import antifraud.DTOResponses.Status;
import antifraud.config.CheckData;
import antifraud.repository.StolencardDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class StolenCardsController {
    @Autowired
    StolencardDBRepository cardRepo;

    @PostMapping(value = "/api/antifraud/stolencard")
    public StolencardDB addStolenCardNumber(@RequestBody StolencardToAdd number) {
        String card = number.getNumber().replaceAll("\\s", "");

        if (!CheckData.isCardNumberCorrect(card))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Incorrect card number");

        List<StolencardDB> cardsInDB = cardRepo.findByNumber(card);

        if (cardsInDB.size() > 0)
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Card number is already in DB");

        StolencardDB stolencardDB = new StolencardDB();
        stolencardDB.setNumber(card);

        stolencardDB = cardRepo.save(stolencardDB);

        return stolencardDB;
    }

    @DeleteMapping(value = "/api/antifraud/stolencard/{number}")
    public Status deleteStolenCard(@PathVariable String number) {
        number = number.replaceAll("\\s", "");

        if (!CheckData.isCardNumberCorrect(number)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Incorrect card number");
        }

        List<StolencardDB> stolencardDBS = cardRepo.findByNumber(number);

        if (stolencardDBS.size() == 0)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "The card number was not found in DB");

        stolencardDBS.forEach(suspiciousIp -> cardRepo.deleteById(suspiciousIp.getId()));
        return new Status("Card " + number + " successfully removed!");
    }

    @GetMapping(value = "/api/antifraud/stolencard")
    public List<StolencardDB> getListOfStolenCards() {
        return cardRepo.findAllByOrderById();
    }
}
