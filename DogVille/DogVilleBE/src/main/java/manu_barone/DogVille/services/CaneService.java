package manu_barone.DogVille.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import manu_barone.DogVille.entities.Cane;
import manu_barone.DogVille.entities.ProfiloPsicologico;
import manu_barone.DogVille.entities.Utente;
import manu_barone.DogVille.exceptions.BadRequestException;
import manu_barone.DogVille.exceptions.NotFoundException;
import manu_barone.DogVille.payloads.CaneDTO;
import manu_barone.DogVille.payloads.validationGroups.Update;
import manu_barone.DogVille.repositories.CaneRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class CaneService {
    @Autowired
    private CaneRepo caneRepo;

    @Autowired
    private Cloudinary cloudinaryUploader;

    @Autowired
    private ProfiloPsicologicoService pps;


    public Page<Cane> findWithFilters(String adopted, Integer age, String weaned, String race, String healthState, Character gender, String dogSize, Pageable pageable) {
        Specification<Cane> specs = (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
//                Specification.where((root, query, criteriaBuilder) ->
//                criteriaBuilder.equal(root.get("adopted"), false ));

        if (age != null)
            specs = specs.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("age"), age));
        if (weaned != null)
            specs = specs.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("weanedCheck"), weaned));
        if (race != null)
            specs = specs.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("race"), race));
        if (healthState != null)
            specs = specs.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("healthState"), healthState));
        if (gender != null)
            specs = specs.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("gender"), gender));
        if (dogSize != null)
            specs = specs.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("dogSize"), dogSize));
        if (adopted != null)
            specs = specs.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("adoptedCheck"), adopted));

        return caneRepo.findAll(specs, pageable);
    }

    public Cane addCane(CaneDTO body) {
        return caneRepo.save(new Cane(body.name(),
                body.age(),
                body.dogSize(),
                body.race(),
                body.healthState(),
                body.gender().charAt(0),
                body.description(),
                body.weaned()));
    }

    public Cane findById(UUID id) {
        return caneRepo.findById(id).orElseThrow(() -> new NotFoundException("Nessun cane trovato con questo ID: " + id));
    }

    public List<Cane> getAllCaniOrderedByLikes() {
        return caneRepo.findAllByOrderByLikeCountDesc();
    }

    public Cane updateCane(UUID id, CaneDTO body) {
        Cane cane = findById(id);

        if (body.name() != null) cane.setName(body.name());
        if (body.age() != null) cane.setAge(body.age());
        if (body.dogSize() != null) cane.setDogSize(body.dogSize());
        if (body.race() != null) cane.setRace(body.race());
        if (body.healthState() != null) cane.setHealthState(body.healthState());
        if (body.gender() != null) cane.setGender(body.gender().charAt(0));
        if (body.description() != null) cane.setDescription(body.description());
        if(body.weanedCheck() != null) cane.setWeanedCheck(body.weanedCheck());
        if(body.adoptedCheck() != null) cane.setAdoptedCheck(body.adoptedCheck());
        cane.setWeaned(cane.getWeanedCheck().equalsIgnoreCase("yes"));
        cane.setAdopted(cane.getAdoptedCheck().equalsIgnoreCase("yes"));
        return caneRepo.save(cane);
    }

    public void deleteCane(UUID id) {
        Cane cane = this.findById(id);
        caneRepo.delete(cane);
    }

    public String uploadPhoto(MultipartFile file, UUID idCane) {
        String url = null;
        try {
            url = (String) cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        } catch (IOException e) {
            throw new BadRequestException("Ci sono stati problemi con l'upload del file!");
        }
        Cane found = this.findById(idCane);
        found.setProfileImage(url);
        caneRepo.save(found);
        return url;
    }

    public List<ProfiloPsicologico> getDogProfiles(UUID dogId) {
        Cane cane = findById(dogId);
        return cane.getDogsPsycologicalProfiles();
    }

    public ProfiloPsicologico addProfileToDog(UUID dogId, String profileType) {
        Cane cane = findById(dogId);
        ProfiloPsicologico profilo = pps.getProfiloPsicologicoByType(profileType);
        if (!cane.getDogsPsycologicalProfiles().contains(profilo)) {
            cane.getDogsPsycologicalProfiles().add(profilo);
            caneRepo.save(cane);
        }

        return profilo;
    }

    public void removeProfileFromUser(UUID dogId,String profileType) {
        Cane cane = this.findById(dogId);
        ProfiloPsicologico profilo = pps.getProfiloPsicologicoByType(profileType);
        cane.getDogsPsycologicalProfiles().remove(profilo);
        caneRepo.save(cane);
    }



}
