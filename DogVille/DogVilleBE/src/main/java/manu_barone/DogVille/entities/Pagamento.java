package manu_barone.DogVille.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import manu_barone.DogVille.entities.enums.MetodoPagamento;
import manu_barone.DogVille.entities.enums.StatoPagamento;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "payments")
@ToString
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"enabled", "accountNonLocked", "credentialsNonExpired", "accountNonExpired", "authorities"})
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;

    private MetodoPagamento metodo;
    private double amount;

    @Enumerated(EnumType.STRING)
    private StatoPagamento state;

    private LocalDate executionDate;
    private LocalDate lastUpdate;

    @OneToOne(mappedBy = "payment")
    @JsonIgnore
    private Ordine order;

    @ManyToOne
    @JoinColumn(name="user_id")
    private Utente userPayments;

    public Pagamento(MetodoPagamento metodo, double amount) {
        this.metodo = metodo;
        this.amount = amount;
        this.state =  StatoPagamento.IN_ATTESA;
        this.executionDate = LocalDate.now();
    }

    public UUID getId() {
        return id;
    }

    public MetodoPagamento getMetodo() {
        return metodo;
    }

    public double getAmount() {
        return amount;
    }

    public StatoPagamento getState() {
        return state;
    }

    public LocalDate getExecutionDate() {
        return executionDate;
    }

    public LocalDate getLastUpdate() {
        return lastUpdate;
    }
}
