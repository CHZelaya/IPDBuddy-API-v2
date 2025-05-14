package beto.projects.ipdbuddyapiv2.services;

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

    public ContractorProfileResponseDTO getContractorByEmail(String email) {
        Contractor contractor = contractorRepo.findByEmail(email);

        if (contractor == null) {
        throw new EntityNotFoundException("Contractor not found for email: " + email);
        }

        return ContractorProfileResponseDTO.builder()
                .firstName(contractor.getFirstName())
                .lastName(contractor.getLastName())
                .phoneNumber(contractor.getPhoneNumber())
                .savingsRate(contractor.getSavingsRate())
                .taxRate(contractor.getTaxRate())
                .build();
    }


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


} //! Class
