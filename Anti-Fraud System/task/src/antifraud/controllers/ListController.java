package antifraud.controllers;

import antifraud.DBtables.UserDB;
import antifraud.DTO.UserToReturn;
import antifraud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ListController {
    @Autowired
    UserRepository userRepo;
    @GetMapping("/api/auth/list")
    public ResponseEntity<List<UserToReturn>> returnUsersList() {
        List<UserDB> usersDB = userRepo.findAllByOrderById();
        List<UserToReturn> usersToReturn = new ArrayList<>();

        for (UserDB user: usersDB) {
            usersToReturn.add(new UserToReturn(user));
        }

        return ResponseEntity.status(HttpStatus.OK).body(usersToReturn);
    }
}
