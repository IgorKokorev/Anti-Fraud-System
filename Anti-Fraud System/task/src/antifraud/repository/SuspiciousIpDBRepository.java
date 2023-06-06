package antifraud.repository;

import antifraud.DBtables.SuspiciousIpDB;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
@Repository
public interface SuspiciousIpDBRepository extends CrudRepository<SuspiciousIpDB, Long> {
    List<SuspiciousIpDB> findByIp(String ip);
    List<SuspiciousIpDB> findAllByOrderById();
    boolean existsByIp(String ip);
}
