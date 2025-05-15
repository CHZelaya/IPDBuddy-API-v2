package beto.projects.ipdbuddyapiv2.controllers;


import beto.projects.ipdbuddyapiv2.config.FirebaseConfig;
import beto.projects.ipdbuddyapiv2.dto.contractors.ContractorProfileResponseDTO;
import beto.projects.ipdbuddyapiv2.dto.contractors.ContractorProfileUpdateDTO;
import beto.projects.ipdbuddyapiv2.services.ContractorService;
import com.google.firebase.auth.FirebaseToken;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/v1/contractor")
public class ContractorController {


    private static final Logger log = LoggerFactory.getLogger(ContractorController.class);
    private final ContractorService contractorService;

    public ContractorController(ContractorService contractorService) {
        this.contractorService = contractorService;
    }

    @GetMapping("/me")
    public ResponseEntity<ContractorProfileResponseDTO> getMyProfile(Authentication authentication) {
        FirebaseToken firebaseToken = (FirebaseToken) authentication.getPrincipal();
        String email = firebaseToken.getEmail();
        log.info("Handling /contractor/me for userId: {}", email);

        ContractorProfileResponseDTO dto = contractorService.getContractorByEmail(email);

        return ResponseEntity.ok(dto);
    }


    @PutMapping("/me")
    public ResponseEntity<?> updateMyProfile(
            Authentication authentication,
            @Valid @RequestBody ContractorProfileUpdateDTO dto) {

        FirebaseToken firebaseToken = (FirebaseToken) authentication.getPrincipal();
        String email = firebaseToken.getEmail();

        contractorService.updateProfile(email, dto);
        return ResponseEntity.ok().build();
    }



}
