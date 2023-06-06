package antifraud.repository;

import antifraud.DBtables.StolencardDB;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StolencardDBRepository extends CrudRepository<StolencardDB, Long> {
    List<StolencardDB> findByNumber(String number);
    List<StolencardDB> findAllByOrderById();
    boolean existsByNumber(String number);
}
