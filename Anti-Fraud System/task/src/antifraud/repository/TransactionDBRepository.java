package antifraud.repository;

import antifraud.DBtables.TransactionDB;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TransactionDBRepository extends CrudRepository<TransactionDB, Long> {
    Optional<TransactionDB> findByTransactionId(long id);
    List<TransactionDB> findAllByOrderByTransactionIdAsc();
    List<TransactionDB> findAllByNumberOrderByTransactionIdAsc(String number);
    @Query(value = "SELECT COUNT (DISTINCT region) FROM transactions WHERE region != ?1 AND number = ?2 AND date > ?3 AND date < ?4", nativeQuery = true)
    int countRegionsByTime(String region, String number, LocalDateTime from, LocalDateTime to);

    @Query(value = "SELECT COUNT (DISTINCT ip) FROM transactions WHERE ip != ?1 AND number = ?2 AND date > ?3 AND date < ?4", nativeQuery = true)
    int countIpsByTime(String ip, String number, LocalDateTime from, LocalDateTime to);
}
