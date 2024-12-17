package manu_barone.DogVille.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "psycologicalProfiles")
@ToString
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"enabled", "accountNonLocked", "credentialsNonExpired", "accountNonExpired", "authorities"})
public class ProfiloPsicologico {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;

    private String type;

    private String descrizione;  // Nuovo campo aggiunto per la descrizione

    @ManyToMany(mappedBy = "dogsPsycologicalProfiles", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Cane> dogs = new ArrayList<>();

    @ManyToMany(mappedBy = "usersPsycologicalProfiles", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Utente> users = new ArrayList<>();

    @OneToMany(mappedBy = "parentProfile", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<ProfiloPsicologico> compatibleProfiles = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "parent_profile_id")
    @JsonIgnore
    private ProfiloPsicologico parentProfile;

    public ProfiloPsicologico(String type, String descrizione) {
        this.type = type;
        this.descrizione = descrizione;
    }

    // Getter e Setter
    public UUID getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public List<Cane> getDogs() {
        return dogs;
    }

    public List<Utente> getUsers() {
        return users;
    }

    public List<ProfiloPsicologico> getCompatibleProfiles() {
        return compatibleProfiles;
    }

    public ProfiloPsicologico getParentProfile() {
        return parentProfile;
    }
}
