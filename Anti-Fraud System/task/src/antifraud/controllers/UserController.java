package antifraud.controllers;

import antifraud.DBtables.UserDB;
import antifraud.DTO.UsernameStatus;
import antifraud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
}
