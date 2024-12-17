package manu_barone.DogVille.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "services")
@ToString
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"enabled", "accountNonLocked", "credentialsNonExpired", "accountNonExpired", "authorities"})
public class Servizio {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;

    private String name;
    private String description;
    private double price;
    private LocalDate insertionDate;
    private LocalDate lastUpdate;

    @OneToMany(mappedBy = "service" ,cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<OggettoOrdine> productItems;


    public Servizio(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.insertionDate = LocalDate.now();
    }


    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public LocalDate getInsertionDate() {
        return insertionDate;
    }

    public LocalDate getLastUpdate() {
        return lastUpdate;
    }
}
