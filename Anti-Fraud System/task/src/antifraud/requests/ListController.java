package antifraud.requests;

import antifraud.authentication.UserToReturn;
import antifraud.repository.UserDB;
import antifraud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
            usersToReturn.add(new UserToReturn(user.getId(), user.getName(), user.getUsername()));
        }

        return ResponseEntity.status(HttpStatus.OK).body(usersToReturn);
    }

    @DeleteMapping("/api/auth/user/{username}")
    public UsernameStatus deleteUser(@PathVariable String username) {
        List<UserDB> usersDB = userRepo.findByUsernameIgnoreCase(username);
        if (usersDB.size() == 0) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");

        userRepo.deleteById(usersDB.get(0).getId());
        return new UsernameStatus(username, "Deleted successfully!");
    }
}
