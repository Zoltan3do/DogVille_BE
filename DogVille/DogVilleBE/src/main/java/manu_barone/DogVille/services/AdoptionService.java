package manu_barone.DogVille.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import manu_barone.DogVille.configs.MailgunSender;
import manu_barone.DogVille.entities.Adozione;
import manu_barone.DogVille.entities.Cane;
import manu_barone.DogVille.entities.Utente;
import manu_barone.DogVille.entities.enums.StatoAdozione;
import manu_barone.DogVille.exceptions.BadRequestException;
import manu_barone.DogVille.exceptions.NotFoundException;
import manu_barone.DogVille.exceptions.UnauthorizedException;
import manu_barone.DogVille.payloads.AdoptionDTO;
import manu_barone.DogVille.repositories.AdozioneRepo;
import manu_barone.DogVille.repositories.CaneRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class AdoptionService {

    @Autowired
    private AdozioneRepo adozioneRepo;

    @Autowired
    private UtenteService us;

    @Autowired
    private CaneService cs;

    @Autowired
    private CaneRepo cr;

    @Autowired
    private Cloudinary cloudinaryUploader;

    @Autowired
    private MailgunSender mailgunSender;

    public Adozione findById(UUID id) {
        return adozioneRepo.findById(id).orElseThrow(() -> new NotFoundException("Nessun adozione trovata"));
    }

    public Page<Adozione> findAdoptionByUser(String userEmail, Pageable pageable) {
        Utente user = us.findByEmail(userEmail);
        return adozioneRepo.findByUserAdoptions(user, pageable);
    }

    public Adozione createAdoptionRequest(AdoptionDTO body) {
        Cane dog = cs.findById(body.caneId());
        if (adozioneRepo.existsByDog(dog)) {
            throw new BadRequestException("Questo cane ha già una adozione assegnatagli");
        }
        Utente user = us.findByEmail(body.userEmail());
        Adozione adoption = new Adozione(dog, user);
        mailgunSender.sendAdoptionEmail(user, "Richiesta di adozione da parte di " + user.getName(),
                "La richiesta di adozione per " + dog.getName() + " da parte di " + user.getName() + " " + user.getSurname() + " è avvenuta con successo!");
        return adozioneRepo.save(adoption);
    }

    public StatoAdozione getAdoptionStateById(UUID adoptionId) {
        Adozione adozione = adozioneRepo.findById(adoptionId)
                .orElseThrow(() -> new NotFoundException("Richiesta di adozione non trovata con ID: " + adoptionId));
        return adozione.getState();
    }

    public Adozione updateAdoptionState(UUID adoptionId, String newState) {
        Adozione adoption = this.findById(adoptionId);
        Cane cane = cs.findById(adoption.getDog().getId());
        StatoAdozione stateEnum;
        try {
            stateEnum = StatoAdozione.valueOf(newState.toUpperCase());
            if (stateEnum.equals(StatoAdozione.IN_ATTESA_VISITA) && adoption.getDocument() != null
                    || stateEnum == StatoAdozione.VISITA_SUPERATA && adoption.getVisitDate() != null
                    || stateEnum == StatoAdozione.ADOZIONE_COMPLETATA && adoption.getState() == StatoAdozione.VISITA_SUPERATA) {
                adoption.setState(stateEnum);
                adoption.setLastUpdate(LocalDate.now());
                if (stateEnum == StatoAdozione.ADOZIONE_COMPLETATA) {
                    cane.setAdopted(true);
                    cane.setAdoptedCheck("yes");
                    cr.save(cane);
                }
                return adozioneRepo.save(adoption);
            } else {
                throw new BadRequestException("Questa operazione non è disponibile a questo punto dell'adozione");
            }
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Stato non valido: " + newState + ". I valori accettati sono: "
                    + Arrays.toString(StatoAdozione.values()));
        }
    }

    public void deleteCane(UUID id) {
        Adozione adoption = this.findById(id);
        Cane cane = cs.findById(adoption.getDog().getId());
        cane.setAdoptedCheck("no");
        cane.setAdopted(false);
        cr.save(cane);
        adozioneRepo.delete(adoption);
        mailgunSender.sendAdoptionEmail(adoption.getUserAdoptions(), "Eliminazione adozione da parte di " + adoption.getUserAdoptions().getName(),
                "L'adozione per " + cane.getName() + " da parte di " + adoption.getUserAdoptions().getName() + " "
                        + adoption.getUserAdoptions().getSurname()
                        + " è stata eliminata!");
    }

    public String uploadDocument(MultipartFile file, UUID idAdozione, @AuthenticationPrincipal Utente currentUtente) {
        String url = null;
        try {
            url = (String) cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        } catch (IOException e) {
            throw new BadRequestException("Ci sono stati problemi con l'upload del file!");
        }
        Adozione found = this.findById(idAdozione);
        System.out.println(currentUtente.getId());
        System.out.println(found.getUserAdoptions().getId());
        if (!found.getUserAdoptions().getId().equals(currentUtente.getId())) {
            throw new UnauthorizedException("Non hai il permesso per modificare questa adozione!");
        }
        if (found.getState() == StatoAdozione.IN_ATTESA_DOCUMENTI) {
            found.setDocument(url);
            adozioneRepo.save(found);
            mailgunSender.sendDocumentEmail(found.getUserAdoptions(), "Documento inviato da parte di " + found.getUserAdoptions().getName(),
                    "Documento inviato per da parte di " + found.getUserAdoptions().getName() + " "
                            + found.getUserAdoptions().getSurname()
                            + " in attesa di approvazione", url);
        } else {
            throw new BadRequestException("Il documento è già stato validato!");
        }
        return url;
    }

    public String uploadSign(MultipartFile file, UUID idAdozione, @AuthenticationPrincipal Utente currentUtente) {
        String url = null;
        try {
            url = (String) cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        } catch (IOException e) {
            throw new BadRequestException("Ci sono stati problemi con l'upload del file!");
        }
        Adozione found = this.findById(idAdozione);
        System.out.println(currentUtente.getId());
        System.out.println(found.getUserAdoptions().getId());
        if (!found.getUserAdoptions().getId().equals(currentUtente.getId())) {
            throw new UnauthorizedException("Non hai il permesso per modificare questa adozione!");
        }
        if (found.getState() == StatoAdozione.IN_ATTESA_DOCUMENTI) {
            found.setSign(url);
            adozioneRepo.save(found);
            mailgunSender.sendDocumentEmail(found.getUserAdoptions(), "Firma inviata da parte di " + found.getUserAdoptions().getName(),
                    "Firma inviata per da parte di " + found.getUserAdoptions().getName() + " "
                            + found.getUserAdoptions().getSurname()
                            + " in attesa di approvazione", url);
        } else {
            throw new BadRequestException("La firma è già stato validato!");
        }
        return url;
    }

    public LocalDate addVisitDate(LocalDate data, UUID idAdozione, @AuthenticationPrincipal Utente currentUtente) {
        Adozione found = this.findById(idAdozione);
        if (found.getState().equals(StatoAdozione.IN_ATTESA_VISITA)) {
            found.setVisitDate(data);
        }
        adozioneRepo.save(found);
        return found.getVisitDate();
    }

    public List<Adozione> findAll() {
        return adozioneRepo.findAll();
    }

}
