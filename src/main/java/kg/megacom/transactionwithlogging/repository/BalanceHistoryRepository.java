package kg.megacom.transactionwithlogging.repository;

import kg.megacom.transactionwithlogging.models.BalanceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceHistoryRepository extends JpaRepository<BalanceHistory, Integer> {
}
