package antifraud.repository;

import antifraud.DBtables.CardLimitsDB;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CardLimitsDBRepository extends CrudRepository<CardLimitsDB, Long> {
    Optional<CardLimitsDB> findByNumber(String number);
}
