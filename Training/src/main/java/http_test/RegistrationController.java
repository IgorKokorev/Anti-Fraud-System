package antifraud.testing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {
    @Autowired
    UserRepository userRepo;
    @Autowired
    PasswordEncoder encoder;

    @PostMapping("/register")
    public void register(@RequestBody User user) {
        // input validation omitted for brevity

        user.setPassword(encoder.encode(user.getPassword()));

        userRepo.save(user);
    }
}