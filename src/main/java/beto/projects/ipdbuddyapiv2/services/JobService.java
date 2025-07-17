package beto.projects.ipdbuddyapiv2.services;


import beto.projects.ipdbuddyapiv2.dto.billables.BillableItemSummaryResponseDTO;
import beto.projects.ipdbuddyapiv2.dto.billables.BillableItemInputDTO;
import beto.projects.ipdbuddyapiv2.dto.contractors.EarningsSummaryDTO;
import beto.projects.ipdbuddyapiv2.dto.jobs.JobSummaryDTO;
import beto.projects.ipdbuddyapiv2.dto.jobs.JobSubmissionRequestDTO;
import beto.projects.ipdbuddyapiv2.dto.jobs.JobSubmissionResponseDTO;
import beto.projects.ipdbuddyapiv2.entities.BillableItem;
import beto.projects.ipdbuddyapiv2.entities.Contractor;
import beto.projects.ipdbuddyapiv2.entities.Job;
import beto.projects.ipdbuddyapiv2.enums.Billables;
import beto.projects.ipdbuddyapiv2.repos.BillableItemRepo;
import beto.projects.ipdbuddyapiv2.repos.ContractorRepo;
import beto.projects.ipdbuddyapiv2.repos.JobRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseToken;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class JobService {

    private final JobRepo jobRepo;
    private final ContractorRepo contractorRepo;
    private final BillableItemRepo billableItemRepo;


    public JobService(JobRepo jobRepo, ContractorRepo contractorRepo, BillableItemRepo billableItemRepo, ObjectMapper jacksonObjectMapper) {
        this.jobRepo = jobRepo;
        this.contractorRepo = contractorRepo;
        this.billableItemRepo = billableItemRepo;

    }

    /**
     * Handles the submission of a job, including creating a job entity, calculating relevant financial details,
     * saving the job and associated billable items, and preparing a response with the submission details.
     *
     * @param email the email address of the contractor submitting the job
     * @param requestDTO the job submission request details containing information such as date, address, notes, and billable items
     * @return a {@code JobSubmissionResponseDTO} containing the details of the submitted job, including
     *         the job ID (if successfully saved), billable item summaries, grand total amount, tax amount, savings amount, and any relevant notes
     * @throws EntityNotFoundException if a contractor with the specified email does not exist
     */
    public JobSubmissionResponseDTO handleJobSubmission(String email, JobSubmissionRequestDTO requestDTO) {
        //Fetching the Contractor ID from the request
        Contractor contractor = contractorRepo.findByEmail( email);

        if (contractor == null){
            throw new EntityNotFoundException("Contractor matching email: "+ email + " not found.");
        }

        Job job = new Job();
        job.setContractor(contractor);
        log.info("Job being created for contractor: {} ({})", contractor.getFirstName(), contractor.getEmail());

        job.setDate(requestDTO.getDate() != null ? requestDTO.getDate() : LocalDate.now());
        job.setAddress(requestDTO.getAddress());
        job.setNotes(requestDTO.getNotes());

        boolean jobSaved = false;
        boolean billablesSaved = false;

        // Building everything up front - like a chef prepping ingredients before cooking
        List<BillableItemSummaryResponseDTO> itemsSummaryList = new ArrayList<>();
        List<BillableItemInputDTO> billableInputs = requestDTO.getBillables();
        List<BillableItem> billableItems = new ArrayList<>();

        BigDecimal grandTotal = BigDecimal.ZERO;


        if (billableInputs != null && !billableInputs.isEmpty()) {
            for (BillableItemInputDTO itemsRequest : requestDTO.getBillables()) {
                Billables billablesType = Billables.valueOf(itemsRequest.getBillableType());
                //* Calculate quantity * rate for each task done
                BigDecimal rate = billablesType.getRate();
                BigDecimal quantity = BigDecimal.valueOf(itemsRequest.getQuantity());
                BigDecimal total = rate.multiply(quantity);

                //* Helper method(s) to check for special cases and amend total based on special cases
                if (billablesType.equals(Billables.FIRE_CAULKING)) {
                    total = capFireCaulkingPay(rate, quantity);
                }

                BillableItem submission = new BillableItem();
                submission.setJob(job);
                submission.setBillableType(billablesType);
                submission.setQuantity(itemsRequest.getQuantity());
                submission.setTotalPrice(total);
                submission.setRate(rate);

                // Adding to list, not saving yet
                billableItems.add(submission);

                // Building response summary
                itemsSummaryList.add(
                        BillableItemSummaryResponseDTO.builder()
                                .name(billablesType.name())
                                .type(billablesType.name())
                                .description(billablesType.getDescription())
                                .quantity(itemsRequest.getQuantity())
                                .rate(rate)
                                .total(total)
                                .jobAddress(job.getAddress())
                                .jobDate(job.getDate())
                                .build()
                );

                grandTotal = grandTotal.add(total);
            }

        }

        job.setGrandTotalAmount(grandTotal);
        job.setTaxAmount(grandTotal.multiply(contractor.getTaxRate()));
        job.setSavingsAmount(grandTotal.multiply(contractor.getSavingsRate()));

        try {
            job = jobRepo.save(job);
            jobSaved = true;
        } catch (Exception e ){
            log.error("Failed to save the job to the database", e);
        }

        try {
            if (jobSaved) {
                for (BillableItem billableItem : billableItems) {
                    billableItem.setJob(job);
                }
                billableItemRepo.saveAll(billableItems);
                billablesSaved = true;
            }
        }  catch (Exception e) {
            log.error("Failed to save the billables", e);
        }

        //Building the response despite persistence.
        System.out.println("Job Service: Building the response!");
        return JobSubmissionResponseDTO.builder()
                .jobId(jobSaved ? job.getId() : null)
                .billableItemsSummary(itemsSummaryList)
                .grandTotalAmount(grandTotal)
                .taxAmount(job.getTaxAmount())
                .savingsAmount(job.getSavingsAmount())
                .notes(job.getNotes())
                .build();

    }


    /**
     * Calculates the total pay for fire caulking work based on the given rate and quantity,
     * capping the pay at a maximum value of $75.00.
     * A helper method used by handleJobSubmission
     *
     * @param rate the rate per unit of fire caulking work
     * @param quantity the quantity of work completed
     * @return the capped total pay for the fire caulking work as a BigDecimal.
     */
    private BigDecimal capFireCaulkingPay(BigDecimal rate, BigDecimal quantity) {
        final BigDecimal maxPay = new BigDecimal("75.00");
        final BigDecimal total = rate.multiply(quantity);

        //* Capping pay at $75 for Fire Caulking.
        return total.compareTo(maxPay) > 0 ? maxPay : total;

    }




    public List<JobSummaryDTO> getAllContractorJobs(FirebaseToken firebaseToken) {

        String email = firebaseToken.getEmail();

        Contractor contractor = contractorRepo.findByEmail(email);
        if (contractor == null) {
            throw new EntityNotFoundException("Contractor not found for email: " + email);
        }

        // Grabbing the Id from the email
        Long contractorId = contractor.getId();

        List<Job> jobs = jobRepo.findAllByContractor_Id(contractorId);

        List<JobSummaryDTO> jobResponseDto = new ArrayList<>();

        for (Job job : jobs) {

            JobSummaryDTO dto = JobSummaryDTO.builder()
                    .jobId(job.getId())
                    .taxAmount(job.getTaxAmount())
                    .savingsAmount(job.getSavingsAmount())
                    .grandTotalAmount(job.getGrandTotalAmount())
                    .date(job.getDate())
                    .address(job.getAddress())
                    .build();

            jobResponseDto.add(dto);
        }

        return jobResponseDto;

    }

    public EarningsSummaryDTO calculateEarningsSummaryForContractor(Contractor contractor) {
        if (contractor == null) {
            throw new EntityNotFoundException("Contractor not found.");
        }

        List<Job> jobs = jobRepo.findAllByContractor_Id(contractor.getId());

        BigDecimal totalEarnings = BigDecimal.ZERO;
        BigDecimal earnedThisWeek = BigDecimal.ZERO;
        BigDecimal earnedThisMonth = BigDecimal.ZERO;
        BigDecimal earnedThisYear = BigDecimal.ZERO;
        BigDecimal highestJobValue = BigDecimal.ZERO;

        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.minusDays(today.getDayOfWeek().getValue() - 1);
        LocalDate startOfMonth = today.withDayOfMonth(1);
        LocalDate startOfYear = today.withDayOfYear(1);

        for (Job job : jobs) {
            totalEarnings = totalEarnings.add(job.getGrandTotalAmount());

            if (job.getDate().isAfter(startOfWeek.minusDays(1))) {
                earnedThisWeek = earnedThisWeek.add(job.getGrandTotalAmount());
            }
            if (job.getDate().isAfter(startOfMonth.minusDays(1))) {
                earnedThisMonth = earnedThisMonth.add(job.getGrandTotalAmount());
            }
            if (job.getDate().isAfter(startOfYear.minusDays(1))) {
                earnedThisYear = earnedThisYear.add(job.getGrandTotalAmount());
            }

            if (job.getGrandTotalAmount().compareTo(highestJobValue) > 0) {
                highestJobValue = job.getGrandTotalAmount();
            }
        }

        BigDecimal averageJobValue = jobs.isEmpty() ? BigDecimal.ZERO : totalEarnings.divide(BigDecimal.valueOf(jobs.size()), 2, BigDecimal.ROUND_HALF_UP);

        return EarningsSummaryDTO.builder()
                .totalEarnings(totalEarnings)
                .earnedThisWeek(earnedThisWeek)
                .earnedThisMonth(earnedThisMonth)
                .earnedThisYear(earnedThisYear)
                .averageJobValue(averageJobValue)
                .highestJobValue(highestJobValue)
                .build();
    }


    //! Implement later - Get this shipped.
//    public JobResponseDTO getJobByContractor(String email, long id) {
//
//        Contractor contractor = contractorRepo.findByEmail(email).orElseThrow(()-> new IllegalArgumentException("Contractor Not Found"));
//        // Grabbing the Id from the email
//        Job job = jobRepo.findById(id);
//
//        if (!job.getContractor().getId().equals(contractor.getId())){
//            throw new IllegalArgumentException("Job does not belong to a contractor");
//        }
//        // Grabbing all the billables
//
//        List<BillableItemSubmission> submissions = billableItemSubmissionRepo.findAllByJob_Id(job.getId());
//
//        List<BillableItemsSummary> itemsSummaryList = new ArrayList<>();
//
//        for (BillableItemSubmission submission : submissions){
//            BillableItemsSummary summary = new BillableItemsSummary(
//                    submission.getBillableType().name(),
//                    submission.getBillableType().getDescription(),
//                    submission.getQuantity(),
//                    submission.getBillableType().getRate(),
//                    submission.getTotalPrice(),
//                    job.getAddress(),
//                    job.getDate()
//            );
//            itemsSummaryList.add(summary);
//        }
//
//        return new JobResponseDTO(
//                job.getId(),
//                job.getDate(),
//                job.getAddress(),
//                itemsSummaryList
//        );
//
//    }
//
//    public void deleteJobByContractor(String email, long id) {
//        Contractor contractor = contractorRepo.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Contractor Not Found"));
//
//        Job job = jobRepo.findById(id);
//
//        if (!job.getContractor().getId().equals(contractor.getId())) {
//            throw new IllegalArgumentException("Unauthorized: Job does not belog to this contractor");
//        }
//
//        jobRepo.delete(job);
//
//    }

} //! Class
