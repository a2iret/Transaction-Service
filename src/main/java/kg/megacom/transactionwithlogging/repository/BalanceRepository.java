package kg.megacom.transactionwithlogging.repository;

import jakarta.transaction.Transactional;
import kg.megacom.transactionwithlogging.models.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Integer> {
    @Modifying
    @Transactional
    @Query("UPDATE Balance b SET b.balance = b.balance - :amount WHERE b.id = :id")
    void updateSenderBalance(Integer id, double amount);

    @Modifying
    @Transactional
    @Query("UPDATE Balance b SET b.balance = b.balance + :amount WHERE b.id = :id")
    void updateReceiverBalance(Integer id, double amount);
    Balance findByUserId(Integer id);
}
