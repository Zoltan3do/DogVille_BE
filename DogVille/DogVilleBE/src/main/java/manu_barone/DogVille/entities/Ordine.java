package manu_barone.DogVille.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import manu_barone.DogVille.entities.enums.StatoOrdine;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "orders")
@ToString
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"enabled", "accountNonLocked", "credentialsNonExpired", "accountNonExpired", "authorities"})
public class Ordine {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;
    private double totalPrice;

    @Enumerated(EnumType.STRING)
    private StatoOrdine state = StatoOrdine.IN_ATTESA;

    private LocalDate creationDate = LocalDate.now();
    private LocalDate lastUpdate;

    @ManyToOne
    @JoinColumn(name="user_id")
    private Utente userOrders;

    @OneToOne
    @JoinColumn(name="payment_id")
    private Pagamento payment;



    public UUID getId() {
        return id;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public StatoOrdine getState() {
        return state;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public LocalDate getLastUpdate() {
        return lastUpdate;
    }
}
