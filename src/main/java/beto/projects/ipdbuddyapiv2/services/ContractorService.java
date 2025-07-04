package beto.projects.ipdbuddyapiv2.services;

import beto.projects.ipdbuddyapiv2.dto.contractors.admin.CreateContractorProfileRequestDTO;
import beto.projects.ipdbuddyapiv2.dto.contractors.ContractorProfileResponseDTO;
import beto.projects.ipdbuddyapiv2.dto.contractors.ContractorProfileUpdateDTO;
import beto.projects.ipdbuddyapiv2.entities.Contractor;
import beto.projects.ipdbuddyapiv2.repos.ContractorRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;


@Service
public class ContractorService {

    private final ContractorRepo contractorRepo;

    public ContractorService(ContractorRepo contractorRepo) {
        this.contractorRepo = contractorRepo;
    }

    /* METHODS */

    /**
     * Retrieves the contractor information associated with the provided email.
     *
     * @param email The email of the contractor to be retrieved.
     * @return A ContractorProfileResponseDTO containing the contractor's profile details.
     * @throws EntityNotFoundException If no contractor is found with the specified email.
     */
    public ContractorProfileResponseDTO getContractorByEmail(String email) {
        Contractor contractor = contractorRepo.findByEmail(email);

        if (contractor == null) {
        throw new EntityNotFoundException("Contractor not found for email: " + email);
        }

        return ContractorProfileResponseDTO.builder()
                .firstName(contractor.getFirstName())
                .lastName(contractor.getLastName())
                .email(contractor.getEmail())
                .phoneNumber(contractor.getPhoneNumber())
                .savingsRate(contractor.getSavingsRate())
                .taxRate(contractor.getTaxRate())
                .build();
    }


    /**
     * Updates the profile information of a contractor based on the provided email and update data.
     *
     * @param email The email of the contractor whose profile is to be updated.
     * @param dto The data transfer object containing the updated profile information.
     * @throws EntityNotFoundException if no contractor is found for the provided email.
     */
    public void updateProfile(String email, @Valid ContractorProfileUpdateDTO dto) {
        Contractor contractor = contractorRepo.findByEmail(email);

        if (contractor == null) {
            throw new EntityNotFoundException("No contractor found for that email: " + email + " Unable to complete update");
        }

        contractor.setFirstName(dto.getFirstName());
        contractor.setLastName(dto.getLastName());
        contractor.setPhoneNumber(dto.getPhoneNumber());
        contractor.setSavingsRate(dto.getSavingsRate());
        contractor.setTaxRate(dto.getTaxRate());

        contractorRepo.save(contractor);

    }

    public void adminCreateContractorProfile(@Valid CreateContractorProfileRequestDTO dto){

        Contractor contractor = new Contractor();
        contractor.setFirstName(dto.getFirstName());
        contractor.setLastName(dto.getLastName());
        contractor.setEmail(dto.getEmail());
        contractor.setPhoneNumber(dto.getPhoneNumber());
        contractor.setSavingsRate(dto.getSavingsRate());
        contractor.setTaxRate(dto.getTaxRate());

        contractorRepo.save(contractor);
    }


} //! Class
