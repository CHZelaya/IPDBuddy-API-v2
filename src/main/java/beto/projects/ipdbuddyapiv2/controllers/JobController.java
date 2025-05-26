package beto.projects.ipdbuddyapiv2.controllers;

import beto.projects.ipdbuddyapiv2.dto.jobs.JobResponseDTO;
import beto.projects.ipdbuddyapiv2.dto.jobs.JobSubmissionRequestDTO;
import beto.projects.ipdbuddyapiv2.dto.jobs.JobSubmissionResponseDTO;
import beto.projects.ipdbuddyapiv2.services.JobService;
import com.google.firebase.auth.FirebaseToken;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;


@RestController
@RequestMapping("/api/v1/job")
public class JobController {

    private static final Logger log = LoggerFactory.getLogger(JobController.class);

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }


    @PostMapping("/submit")
    public ResponseEntity<JobSubmissionResponseDTO> submitJob(
            Authentication authentication,
            @RequestBody @Valid JobSubmissionRequestDTO requestDTO) {

        log.info("Received Job Payload: {}", requestDTO);

        if (!(authentication.getPrincipal() instanceof FirebaseToken firebaseToken)) {
            log.warn("Principal is not a FirebaseToken. Rejecting request.");
            return ResponseEntity.status(403).build();
        }

        String email = firebaseToken.getEmail();

        try {
            JobSubmissionResponseDTO responseDTO = jobService.handleJobSubmission(email, requestDTO);
            return ResponseEntity.ok(responseDTO);

        } catch (EntityNotFoundException e) {
            log.error("Contractor not found for email {}: {}", email, e.getMessage());
            return ResponseEntity.status(404).build();

        } catch (Exception persistenceException) {
            log.error("Persistence failed: {}", persistenceException.getMessage(), persistenceException);
            return ResponseEntity.status(500).build();
        }
    }


    //! Live on API, not implemented on Frontend yet.
    @GetMapping("/jobs")
    public ResponseEntity<List<JobResponseDTO>> getAllContractorJobs(
            Authentication authentication) {

        FirebaseToken firebaseToken = (FirebaseToken) authentication.getPrincipal();
        String email = firebaseToken.getEmail();

        List<JobResponseDTO> responseDTO = jobService.getAllContractorJobs(email);
        return ResponseEntity.ok(responseDTO);

    }

    //! Implement later, ship first - No Data to pull from anyways...yet
//
//    @GetMapping("/jobs/{id}")
//    public ResponseEntity<JobResponseDTO> getJobByContractor(
//            Authentication  authentication,
//            @PathVariable Long id) {
//
//        FirebaseToken firebaseToken = (FirebaseToken) authentication.getPrincipal();
//        String email = firebaseToken.getEmail();
//
//        JobResponseDTO responseDTO = jobService.getJobByContractor(email, id);
//        return ResponseEntity.ok(responseDTO);
//    }

//    @DeleteMapping("/jobs/{id}")
//    public ResponseEntity<Void> deleteJob(
//            Authentication authentication,
//            @PathVariable Long id) {
//
//        FirebaseToken firebaseToken = (FirebaseToken) authentication.getPrincipal();
//        String email = firebaseToken.getEmail();
//        jobService.deleteJobByContractor(email, id);
//        return ResponseEntity.noContent().build();
//    }








} //!Class
