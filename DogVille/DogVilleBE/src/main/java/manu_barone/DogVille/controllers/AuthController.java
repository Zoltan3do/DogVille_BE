package manu_barone.DogVille.controllers;

import manu_barone.DogVille.entities.Utente;
import manu_barone.DogVille.exceptions.BadRequestException;
import manu_barone.DogVille.payloads.LoginDTO;
import manu_barone.DogVille.payloads.UtenteDTO;
import manu_barone.DogVille.payloads.UtenteLoginResponseDTO;
import manu_barone.DogVille.payloads.validationGroups.Create;
import manu_barone.DogVille.services.SecurityService;
import manu_barone.DogVille.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private SecurityService ss;

    @Autowired
    private UtenteService userService;

    @PostMapping("/login")
    public UtenteLoginResponseDTO LoginResponseDTO(@RequestBody @Validated LoginDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }
        return new UtenteLoginResponseDTO(this.ss.checkCredentialsAndGenerateToken(body));
    }


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Utente save(@RequestBody @Validated(Create.class) UtenteDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }
        return this.userService.save(body);
    }


}
