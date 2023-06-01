package antifraud.authentication;

import antifraud.repository.UserDB;
import antifraud.repository.UserRepository;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class RegistrationController {
    @Autowired
    UserRepository userRepo;
    @Autowired
    PasswordEncoder encoder;

    @PostMapping("/api/auth/user")
    public ResponseEntity<UserToReturn> register(@RequestBody UserToAdd user) {

        // Checking data correctness
        if (user == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empty user data");
        if (StringUtils.isBlank(user.getName())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empty user name");
        if (StringUtils.isBlank(user.getUsername())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empty user username");
        if (StringUtils.isBlank(user.getPassword())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empty user password");

        // Checking if the username is occupied
        if (userRepo.findByUsernameIgnoreCase(user.getUsername()).size() > 0)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "The username already exists");

        // hashing password
        user.setPassword(encoder.encode(user.getPassword()));

        // Saving user to DB
        UserDB userDB = new UserDB(user.getName().trim(), user.getUsername().trim(), user.getPassword(), "ROLE_USER");
        userDB = userRepo.save(userDB);

        // returning user data
        UserToReturn userToReturn = new UserToReturn(userDB.getId(), userDB.getName(), userDB.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED).body(userToReturn);
    }
}