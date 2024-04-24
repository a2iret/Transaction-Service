package kg.megacom.transactionwithlogging.service;

import kg.megacom.transactionwithlogging.dto.TransferDto;
import kg.megacom.transactionwithlogging.enums.Status;
import kg.megacom.transactionwithlogging.enums.TypeOfAction;
import kg.megacom.transactionwithlogging.models.Balance;
import kg.megacom.transactionwithlogging.models.BalanceHistory;
import kg.megacom.transactionwithlogging.models.BalanceLog;
import kg.megacom.transactionwithlogging.models.User;
import kg.megacom.transactionwithlogging.repository.BalanceHistoryRepository;
import kg.megacom.transactionwithlogging.repository.BalanceLogRepository;
import kg.megacom.transactionwithlogging.repository.BalanceRepository;
import kg.megacom.transactionwithlogging.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TransactionService {
    private final UserRepository userRepository;
    private final BalanceRepository balanceRepository;
    private final BalanceHistoryRepository balanceHistoryRepository;
    private final BalanceLogRepository balanceLogRepository;

    public TransactionService(UserRepository userRepository, BalanceRepository balanceRepository, BalanceHistoryRepository balanceHistoryRepository, BalanceLogRepository balanceLogRepository) {
        this.userRepository = userRepository;
        this.balanceRepository = balanceRepository;
        this.balanceHistoryRepository = balanceHistoryRepository;
        this.balanceLogRepository = balanceLogRepository;
    }

    public String createTransfer(TransferDto transferDto){
        User sender = userRepository.getReferenceById(transferDto.sender_id());
        User receiver = userRepository.getReferenceById(transferDto.receiver_id());

        Balance senderBalance = balanceRepository.findByUserId(sender.getId());
        Balance receiverBalance = balanceRepository.findByUserId(receiver.getId());

        balanceRepository.updateSenderBalance(sender.getId(), transferDto.amount());
        balanceRepository.updateReceiverBalance(receiver.getId(), transferDto.amount());

        BalanceHistory senderBalanceHistory = new BalanceHistory().setAdd_date(Instant.now())
                .setAmount(transferDto.amount()).setBalance(senderBalance);
        balanceHistoryRepository.save(senderBalanceHistory);

        BalanceLog senderBalanceLog = new BalanceLog().setAddDate(Instant.now())
                .setBalance(senderBalance).setStatus(Status.OK)
                .setMessage("Транзакция прошла успешна вы пополнили "+receiver.getFirstName()+",сумма: "+transferDto.amount()+"$")
                .setTypeOfAction(TypeOfAction.WITHDRAWALS);
        balanceLogRepository.save(senderBalanceLog);

        BalanceHistory receiverBalanceHistory = new BalanceHistory().setAdd_date(Instant.now())
                .setAmount(transferDto.amount()).setBalance(receiverBalance);
        balanceHistoryRepository.save(receiverBalanceHistory);

        BalanceLog receiverBalanceLog = new BalanceLog().setAddDate(Instant.now())
                .setBalance(receiverBalance).setStatus(Status.OK)
                .setMessage("Вам пополнил "+receiver.getFirstName()+",cуммa: "+transferDto.amount()+"$")
                .setTypeOfAction(TypeOfAction.REPLENISHMENT);
        balanceLogRepository.save(receiverBalanceLog);

        double balance = senderBalance.getBalance() - transferDto.amount();
        return "Перевод: "+ transferDto.amount() + "\n"+ receiver.getFirstName()
                +"\nДоступно: " + balance;
    }
}
