package manu_barone.DogVille.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import manu_barone.DogVille.entities.enums.StatoAdozione;
import org.apache.catalina.User;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "adoptions")
@ToString
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"enabled", "accountNonLocked", "credentialsNonExpired", "accountNonExpired", "authorities"})
public class Adozione {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;
    private LocalDate visitDate;
    private String document;

    @Enumerated(EnumType.STRING)
    private StatoAdozione state;

    private LocalDate creationDate;
    private LocalDate lastUpdate;

    @OneToOne
    @JoinColumn(name = "dog_id")
    private Cane dog;

    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonIgnore
    private Utente userAdoptions;

    public Adozione(Cane dog, Utente user) {
        this.state = StatoAdozione.IN_ATTESA_DOCUMENTI;
        this.creationDate = LocalDate.now();
        this.dog = dog;
        this.userAdoptions = user;
    }


    public UUID getId() {
        return id;
    }

    public LocalDate getVisitDate() {
        return visitDate;
    }

    public String getDocument() {
        return document;
    }

    public StatoAdozione getState() {
        return state;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public LocalDate getLastUpdate() {
        return lastUpdate;
    }

    public Cane getDog() {
        return dog;
    }

    public Utente getUserAdoptions() {
        return userAdoptions;
    }
}
