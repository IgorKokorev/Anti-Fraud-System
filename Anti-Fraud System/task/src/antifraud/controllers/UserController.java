package antifraud.controllers;

import antifraud.DBtables.UserDB;
import antifraud.DTO.*;
import antifraud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserRepository userRepo;

    @DeleteMapping("/api/auth/user/{username}")
    public UsernameStatus deleteUser(@PathVariable String username) {
        List<UserDB> usersDB = userRepo.findByUsernameIgnoreCase(username);
        if (usersDB.size() == 0) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");

        userRepo.deleteById(usersDB.get(0).getId());
        return new UsernameStatus(username, "Deleted successfully!");
    }

    @GetMapping("/api/auth/list")
    public ResponseEntity<List<UserToReturn>> returnUsersList() {
        List<UserDB> usersDB = userRepo.findAllByOrderById();
        List<UserToReturn> usersToReturn = new ArrayList<>();

        for (UserDB user: usersDB) {
            usersToReturn.add(new UserToReturn(user));
        }

        return ResponseEntity.status(HttpStatus.OK).body(usersToReturn);
    }

    @PutMapping("/api/auth/role")
    public ResponseEntity<UserToReturn> changeRole(@RequestBody UsernameRole usernameRole) {
        if (usernameRole == null || usernameRole.getUsername() == null || usernameRole.getRole() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incomplete data");

        List<UserDB> usersDB = userRepo.findByUsernameIgnoreCase(usernameRole.getUsername());
        if (usersDB.size() == 0) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");

        if (!usernameRole.getRole().equals("SUPPORT") && !usernameRole.getRole().equals("MERCHANT"))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect role");

        UserDB userDB = usersDB.get(0);
        if (userDB.getRole().equals(usernameRole.getRole()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Role already assigned");

        userDB.setRole(usernameRole.getRole());
        userDB = userRepo.save(userDB);

        // returning user data
        UserToReturn userToReturn = new UserToReturn(userDB);
        return ResponseEntity.status(HttpStatus.OK).body(userToReturn);
    }

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
