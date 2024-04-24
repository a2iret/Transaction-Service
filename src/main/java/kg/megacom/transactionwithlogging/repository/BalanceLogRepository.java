package kg.megacom.transactionwithlogging.repository;

import kg.megacom.transactionwithlogging.models.BalanceLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceLogRepository extends JpaRepository<BalanceLog, Integer> {
}
