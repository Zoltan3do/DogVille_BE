package manu_barone.DogVille.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import manu_barone.DogVille.entities.enums.Ruolo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@ToString
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"password", "enabled", "accountNonLocked", "credentialsNonExpired", "accountNonExpired", "authorities"})
public class Utente implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String name;
    private String surname;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Ruolo role;

    private LocalDate registrationDate;
    private String profileImage;
    private String address;
    private String telephoneNumber;

    @OneToMany(mappedBy = "userAdoptions",cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Adozione> adozioni;

    @OneToMany(mappedBy = "userOrders",cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Ordine> ordini;

    @OneToMany(mappedBy = "userPayments", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Pagamento> pagamenti;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "likes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "dog_id"))
    private List<Cane> likes = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_profiles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "psycological_profile_id")
    )
    private List<ProfiloPsicologico> usersPsycologicalProfiles = new ArrayList<>();


    public Utente(String name, String surname, String email, String password, String address,String telephoneNumber) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.role = Ruolo.USER;
        this.registrationDate = LocalDate.now();
        this.profileImage = "https://ui-avatars.com/api/?name=" + name + "+" + surname;
        this.address = address;
        this.telephoneNumber =telephoneNumber;
    }

    public List<Adozione> getAdozioni() {
        return adozioni;
    }

    public List<Ordine> getOrdini() {
        return ordini;
    }

    public List<Pagamento> getPagamenti() {
        return pagamenti;
    }

    public List<Cane> getLikes() {
        return likes;
    }

    public List<ProfiloPsicologico> getUsersPsycologicalProfiles() {
        return usersPsycologicalProfiles;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public String getAddress() {
        return address;
    }


    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    public Ruolo getRole() {
        return role;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public String getProfileImage() {
        return profileImage;
    }

}
