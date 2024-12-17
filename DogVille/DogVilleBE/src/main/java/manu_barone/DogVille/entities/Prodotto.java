package manu_barone.DogVille.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import manu_barone.DogVille.entities.enums.Categoria;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "products")
@ToString
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"enabled", "accountNonLocked", "credentialsNonExpired", "accountNonExpired", "authorities"})
public class Prodotto {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;

    private String name;
    private String description;
    private double price;
    private int quantity;

    @Enumerated(EnumType.STRING)
    private Categoria category;

    private LocalDate insertionDate;
    private LocalDate lastUpdate;

    @OneToMany(mappedBy = "product",cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<OggettoOrdine> productItems;


    public Prodotto(String name, String description, double price, int quantity, Categoria category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
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

    public int getQuantity() {
        return quantity;
    }

    public Categoria getCategory() {
        return category;
    }

    public LocalDate getInsertionDate() {
        return insertionDate;
    }

    public LocalDate getLastUpdate() {
        return lastUpdate;
    }
}
