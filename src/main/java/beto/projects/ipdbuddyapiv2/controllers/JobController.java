package beto.projects.ipdbuddyapiv2.controllers;

import beto.projects.ipdbuddyapiv2.dto.jobs.JobResponseDTO;
import beto.projects.ipdbuddyapiv2.dto.jobs.JobSubmissionRequestDTO;
import beto.projects.ipdbuddyapiv2.dto.jobs.JobSubmissionResponseDTO;
import beto.projects.ipdbuddyapiv2.services.JobService;
import com.google.firebase.auth.FirebaseToken;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/api/v1/job")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }


    @PostMapping("/submit")
    public ResponseEntity<JobSubmissionResponseDTO> submitJob(
            Authentication authentication,
            @RequestBody @Valid JobSubmissionRequestDTO requestDTO) {

        FirebaseToken firebaseToken = (FirebaseToken) authentication.getPrincipal();
        String email = firebaseToken.getEmail();

        JobSubmissionResponseDTO responseDTO = jobService.handleJobSubmission(email, requestDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{jobId}")
                .buildAndExpand(responseDTO.getJobId())
                .toUri();

        return ResponseEntity.created(location).body(responseDTO);
    }


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
