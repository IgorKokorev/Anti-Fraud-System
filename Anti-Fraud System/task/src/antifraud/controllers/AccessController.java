package antifraud.controllers;

import antifraud.DBtables.UserDB;
import antifraud.DTO.Status;
import antifraud.DTO.UserToReturn;
import antifraud.DTO.UsernameOperation;
import antifraud.DTO.UsernameRole;
import antifraud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class AccessController {
    @Autowired
    UserRepository userRepo;

    @PutMapping("/api/auth/access")
    public ResponseEntity<Status> changeRole(@RequestBody UsernameOperation usernameOperation) {
        if (usernameOperation == null || usernameOperation.getUsername() == null || usernameOperation.getOperation() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incomplete data");

        List<UserDB> usersDB = userRepo.findByUsernameIgnoreCase(usernameOperation.getUsername());
        if (usersDB.size() == 0) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");

        if (!usernameOperation.getOperation().equals("LOCK") && !usernameOperation.getOperation().equals("UNLOCK"))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect operation");

        UserDB userDB = usersDB.get(0);
        boolean toUnlock = usernameOperation.getOperation().equals("UNLOCK");

        if (userDB.getRole().equals("ADMINISTRATOR") && !toUnlock)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Administrator can't be locked");

        userDB.setAccountNonLocked(toUnlock);
        userRepo.save(userDB);

        // saving user data
        UserToReturn userToReturn = new UserToReturn(userDB);

        String status = "User " + userDB.getUsername() + " " + (toUnlock ? "un" : "") + "locked!";
        return ResponseEntity.status(HttpStatus.OK).body(new Status(status));
    }
}
