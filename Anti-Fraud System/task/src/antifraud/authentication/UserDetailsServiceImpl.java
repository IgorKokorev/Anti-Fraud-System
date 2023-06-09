package antifraud.authentication;

import antifraud.DBtables.UserDB;
import antifraud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String cutTheCrap = username.trim();
        List<UserDB> userDBList = userRepo.findByUsernameIgnoreCase(cutTheCrap);

        if (userDBList.size() != 1) {
            throw new UsernameNotFoundException("Not found: " + cutTheCrap);
        }

        return new UserDetailsImpl(userDBList.get(0));
    }
}