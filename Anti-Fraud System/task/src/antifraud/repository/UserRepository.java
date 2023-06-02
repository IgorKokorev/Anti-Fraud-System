package antifraud.repository;

import antifraud.DBtables.UserDB;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Component
@Repository
public interface UserRepository extends CrudRepository<UserDB, Long> {
    Optional<UserDB> findById(Long id);
    List<UserDB> findByUsernameIgnoreCase(String username);
    List<UserDB> findAllByOrderById();
//    List<UserDB> findAll();

}
