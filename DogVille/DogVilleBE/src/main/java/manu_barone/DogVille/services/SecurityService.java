package manu_barone.DogVille.services;

import manu_barone.DogVille.entities.Utente;
import manu_barone.DogVille.exceptions.UnauthorizedException;
import manu_barone.DogVille.payloads.LoginDTO;
import manu_barone.DogVille.tools.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    @Autowired
    private UtenteService userService;

    @Autowired
    private JWT jwt;

    @Autowired
    private PasswordEncoder bcrypt;

    public String checkCredentialsAndGenerateToken(LoginDTO body) {
        Utente found = this.userService.findByEmail(body.email());

        if (bcrypt.matches(body.password(), found.getPassword())) {
            return jwt.createToken(found);
        } else {
            throw new UnauthorizedException("Credenziali errate!");
        }
    }
}
