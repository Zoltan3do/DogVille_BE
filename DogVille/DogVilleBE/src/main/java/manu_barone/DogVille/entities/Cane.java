package manu_barone.DogVille.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import manu_barone.DogVille.entities.enums.StatoSalute;
import manu_barone.DogVille.entities.enums.Taglia;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "dogs")
@ToString
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"enabled", "accountNonLocked", "credentialsNonExpired", "accountNonExpired", "authorities"})
public class Cane {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String name;
    private int age;

    @Enumerated(EnumType.STRING)
    private Taglia dogSize;

    private String race;
    private boolean adopted;

    @Enumerated(EnumType.STRING)
    private StatoSalute healthState;

    private char gender;
    private int likeCount;
    private String description;

    private boolean weaned;

    private LocalDate insertionDate;
    private String profileImage;

    private String adoptedCheck;
    private String weanedCheck;

    @ManyToMany(mappedBy = "likes", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Utente> likes;

    @OneToOne(mappedBy = "dog", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("dog")
    private Adozione adoption;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "dogs_profiles",
            joinColumns = @JoinColumn(name = "dog_id"),
            inverseJoinColumns = @JoinColumn(name = "psycological_profile_id")
    )
    private List<ProfiloPsicologico> dogsPsycologicalProfiles = new ArrayList<>();

    public Cane(String name, int age, Taglia size, String race, StatoSalute healthState, char gender, String description, boolean weaned) {
        this.name = name;
        this.age = age;
        this.dogSize = size;
        this.race = race;
        this.healthState = healthState;
        this.gender = gender;
        this.description = description;
        this.weaned = weaned;
        this.likeCount = 0;
        this.insertionDate = LocalDate.now();
        this.profileImage = "https://ui-avatars.com/api/?name=" + name;
        this.adopted = false;
        this.weanedCheck = this.weaned ? "yes" : "no";
        this.adoptedCheck = "no";
    }


    public Adozione getAdoption() {
        return adoption;
    }

    public String getAdoptedCheck() {
        return adoptedCheck;
    }

    public String getWeanedCheck() {
        return weanedCheck;
    }

    public Boolean getAdopted() {
        return adopted;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public Boolean getWeaned() {
        return weaned;
    }

    public Taglia getDogSize() {
        return dogSize;
    }

    public List<Utente> getLikes() {
        return likes;
    }

    public List<ProfiloPsicologico> getDogsPsycologicalProfiles() {
        return dogsPsycologicalProfiles;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Taglia dogSize() {
        return dogSize;
    }

    public String getRace() {
        return race;
    }

    public StatoSalute getHealthState() {
        return healthState;
    }

    public char getGender() {
        return gender;
    }

    public int getLike_count() {
        return likeCount;
    }

    public String getDescription() {
        return description;
    }

    public boolean weaned() {
        return weaned;
    }

    public LocalDate getInsertionDate() {
        return insertionDate;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public boolean adopted() {
        return adopted;
    }
}
