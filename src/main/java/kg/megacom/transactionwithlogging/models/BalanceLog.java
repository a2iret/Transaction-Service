package kg.megacom.transactionwithlogging.models;

import jakarta.persistence.*;
import kg.megacom.transactionwithlogging.enums.Status;
import kg.megacom.transactionwithlogging.enums.TypeOfAction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Table(name = "balance_logs")
public class BalanceLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Instant addDate;
    @ManyToOne
    @JoinColumn(name = "balance_id")
    private Balance balance;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String message;
    @Enumerated(EnumType.STRING)
    private TypeOfAction typeOfAction;

}
