package manu_barone.DogVille.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "order_items")
@ToString
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"enabled", "accountNonLocked", "credentialsNonExpired", "accountNonExpired", "authorities"})
public class OggettoOrdine {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;
    private int quantity;
    private double price;
    private LocalDate creationDate;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Ordine order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Prodotto product;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Servizio service;


    public OggettoOrdine(int quantity, double price) {
        this.quantity = quantity;
        this.price = price;
        this.creationDate = LocalDate.now();
    }

    public UUID getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }
}
