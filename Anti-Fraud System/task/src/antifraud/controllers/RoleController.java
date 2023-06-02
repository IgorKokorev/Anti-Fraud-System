package antifraud.controllers;

import antifraud.DBtables.UserDB;
import antifraud.DTO.UserToReturn;
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
public class RoleController {
    @Autowired
    UserRepository userRepo;

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
}
