package beto.projects.ipdbuddyapiv2.services;


import beto.projects.ipdbuddyapiv2.dto.billables.BillableItemSummaryDTO;
import beto.projects.ipdbuddyapiv2.dto.billables.BillableItemsRequestDTO;
import beto.projects.ipdbuddyapiv2.dto.jobs.JobResponseDTO;
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
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class JobService {

    private final JobRepo jobRepo;
    private final ContractorRepo contractorRepo;
    private final BillableItemRepo billableItemRepo;


    public JobService(JobRepo jobRepo, ContractorRepo contractorRepo, BillableItemRepo billableItemRepo, ObjectMapper jacksonObjectMapper) {
        this.jobRepo = jobRepo;
        this.contractorRepo = contractorRepo;
        this.billableItemRepo = billableItemRepo;

    }

    public JobSubmissionResponseDTO handleJobSubmission(String email, JobSubmissionRequestDTO requestDTO) {
        //Fetching the Contractor ID from the request
        Contractor contractor = contractorRepo.findByEmail( email);
        // * Putting it here will result in the job being created with the grand total being multiplied by 0, equalling 0


        if (contractor == null){
            throw new EntityNotFoundException("Contractor matching email: "+ email + " not found.");
        }

        //Creating and saving a new Job
        Job job = new Job();
        job.setContractor(contractor);
        job.setAddress(requestDTO.getAddress());
        job.setDate(requestDTO.getDate() != null ? requestDTO.getDate() : LocalDate.now());

        job.setGrandTotalAmount(BigDecimal.ZERO);
        job.setSavingsAmount(BigDecimal.ZERO);
        job.setTaxAmount(BigDecimal.ZERO);

        job = jobRepo.save(job); // Saving early to generate a job with an ID



        //Preparing the summary
        List<BillableItemSummaryDTO> itemsSummaryList = new ArrayList<>();
        BigDecimal grandTotal = BigDecimal.ZERO;
        List<BillableItemsRequestDTO> itemsRequests = requestDTO.getBillables();

        List<BillableItem> billableItemsToSave = new ArrayList<>();

        if (itemsRequests != null && !itemsRequests.isEmpty()) {
            for (BillableItemsRequestDTO itemsRequest : requestDTO.getBillables()) {
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
                billableItemsToSave.add(submission);

                // Building response summary
                itemsSummaryList.add(
                        BillableItemSummaryDTO.builder()
                                .name(billablesType.name())
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
            //! This is what I was missing, batch saving all billables AFTER the loop.
            billableItemRepo.saveAll(billableItemsToSave);

        }

        job.setGrandTotalAmount(grandTotal);
        job.setTaxAmount(grandTotal.multiply(contractor.getTaxRate()));
        job.setSavingsAmount(grandTotal.multiply(contractor.getSavingsRate()));
        // ✅ DEMO MODE TOGGLE 🟠
        boolean DEMO_MODE = true;
        if (DEMO_MODE) {
            System.out.println("\n🚧 DEMO MODE ACTIVE - Job NOT persisted to DB. Returning calculated totals only.\n");
            return JobSubmissionResponseDTO.builder()
                    .jobId(null)  // No actual DB ID since not saved
                    .billableItemsSummary(itemsSummaryList)
                    .grandTotalAmount(grandTotal)
                    .taxAmount(grandTotal.multiply(contractor.getTaxRate()))
                    .savingsAmount(grandTotal.multiply(contractor.getSavingsRate()))
                    .build();
        }

        jobRepo.save(job);

        JobSubmissionResponseDTO response = JobSubmissionResponseDTO.builder()
                .jobId(job.getId())
                .billableItemsSummary(itemsSummaryList)
                .grandTotalAmount(job.getGrandTotalAmount())
                .taxAmount(job.getTaxAmount())
                .savingsAmount(job.getSavingsAmount())
                .build();

        return response;
    }

    private BigDecimal capFireCaulkingPay(BigDecimal rate, BigDecimal quantity) {
        final BigDecimal maxPay = new BigDecimal("75.00");
        final BigDecimal total = rate.multiply(quantity);

        //* Capping pay at $75 for Fire Caulking.
        return total.compareTo(maxPay) > 0 ? maxPay : total;

    }




    public List<JobResponseDTO> getAllContractorJobs(String email) {

        Contractor contractor = contractorRepo.findByEmail(email);

        if (contractor == null) {
            throw new EntityNotFoundException("Contractor not found");
        }

        // Grabbing the Id from the email
        Long contractorId = contractor.getId();

        List<Job> jobs = jobRepo.findAllByContractor_Id(contractorId);

        List<JobResponseDTO> jobResponseDto = new ArrayList<>();

        for (Job job : jobs) {

            JobResponseDTO.builder()
                    .jobId(job.getId())
                    .taxAmount(job.getTaxAmount())
                    .savingsAmount(job.getSavingsAmount())
                    .grandTotalAmount(job.getGrandTotalAmount())
                    .notes(job.getNotes())
                    .date(job.getDate())
                    .address(job.getAddress())
                    .build();
        }

        return jobResponseDto;



    }

    public void attemptToPersistCalculatedJob(String email, JobSubmissionResponseDTO calculatedResponse) {
        Contractor contractor = contractorRepo.findByEmail(email);

        if (contractor == null) {
            throw new EntityNotFoundException("Contractor matching email: " + email + " not found.");
        }

        // Rebuild Job with calculated totals
        Job job = new Job();
        job.setContractor(contractor);
        job.setAddress(calculatedResponse.getBillableItemsSummary().isEmpty() ? "" :
                calculatedResponse.getBillableItemsSummary().get(0).getJobAddress());
        job.setDate(LocalDate.now());
        job.setGrandTotalAmount(calculatedResponse.getGrandTotalAmount());
        job.setTaxAmount(calculatedResponse.getTaxAmount());
        job.setSavingsAmount(calculatedResponse.getSavingsAmount());
        job = jobRepo.save(job);

        // Rebuild BillableItems and save them
        List<BillableItem> billableItems = new ArrayList<>();
        for (BillableItemSummaryDTO summary : calculatedResponse.getBillableItemsSummary()) {
            BillableItem item = new BillableItem();
            item.setJob(job);
            item.setBillableType(Billables.valueOf(summary.getName()));
            item.setQuantity(summary.getQuantity());
            item.setTotalPrice(summary.getTotal());
            item.setRate(summary.getRate());
            billableItems.add(item);
        }

        billableItemRepo.saveAll(billableItems);
    }


//
//
//    public JobSubmissionResponseDTO preCalculateJobSubmission(String email, JobSubmissionRequestDTO requestDTO) {
//        Contractor contractor = contractorRepo.findByEmail(email);
//
//        if (contractor == null) {
//            throw new EntityNotFoundException("Contractor matching email: " + email + " not found.");
//        }
//
//        List<BillableItemSummaryDTO> itemsSummaryList = new ArrayList<>();
//        BigDecimal grandTotal = BigDecimal.ZERO;
//
//        List<BillableItemsRequestDTO> itemsRequests = requestDTO.getBillables();
//
//        if (itemsRequests != null && !itemsRequests.isEmpty()) {
//            for (BillableItemsRequestDTO itemsRequest : itemsRequests) {
//                Billables billablesType = Billables.valueOf(itemsRequest.getBillableType());
//                BigDecimal rate = billablesType.getRate();
//                BigDecimal quantity = BigDecimal.valueOf(itemsRequest.getQuantity());
//                BigDecimal total = rate.multiply(quantity);
//
//                if (billablesType.equals(Billables.FIRE_CAULKING)) {
//                    total = capFireCaulkingPay(rate, quantity);
//                }
//
//                itemsSummaryList.add(
//                        BillableItemSummaryDTO.builder()
//                                .name(billablesType.name())
//                                .description(billablesType.getDescription())
//                                .quantity(itemsRequest.getQuantity())
//                                .rate(rate)
//                                .total(total)
//                                .jobAddress(requestDTO.getAddress())
//                                .jobDate(requestDTO.getDate() != null ? requestDTO.getDate() : LocalDate.now())
//                                .build()
//                );
//
//                grandTotal = grandTotal.add(total);
//            }
//        }
//
//        return JobSubmissionResponseDTO.builder()
//                .jobId(null)  // No persistence yet, so no job ID
//                .billableItemsSummary(itemsSummaryList)
//                .grandTotalAmount(grandTotal)
//                .taxAmount(grandTotal.multiply(contractor.getTaxRate()))
//                .savingsAmount(grandTotal.multiply(contractor.getSavingsRate()))
//                .build();
//    }

//
//    public JobSubmissionResponseDTO preCalculateJobSubmissionWithoutContractor(@Valid JobSubmissionRequestDTO requestDTO) {
//        List<BillableItemSummaryDTO> itemsSummaryList = new ArrayList<>();
//        BigDecimal grandTotal = BigDecimal.ZERO;
//
//        List<BillableItemsRequestDTO> itemsRequests = requestDTO.getBillables();
//
//        if (itemsRequests != null && !itemsRequests.isEmpty()) {
//            for (BillableItemsRequestDTO itemsRequest : itemsRequests) {
//                Billables billablesType = Billables.valueOf(itemsRequest.getBillableType());
//                BigDecimal rate = billablesType.getRate();
//                BigDecimal quantity = BigDecimal.valueOf(itemsRequest.getQuantity());
//                BigDecimal total = rate.multiply(quantity);
//
//                if (billablesType.equals(Billables.FIRE_CAULKING)) {
//                    total = capFireCaulkingPay(rate, quantity);
//                }
//
//                itemsSummaryList.add(
//                        BillableItemSummaryDTO.builder()
//                                .name(billablesType.name())
//                                .description(billablesType.getDescription())
//                                .quantity(itemsRequest.getQuantity())
//                                .rate(rate)
//                                .total(total)
//                                .jobAddress(requestDTO.getAddress())
//                                .jobDate(requestDTO.getDate() != null ? requestDTO.getDate() : LocalDate.now())
//                                .build()
//                );
//
//                grandTotal = grandTotal.add(total);
//            }
//        }
//
//        return JobSubmissionResponseDTO.builder()
//                .jobId(null)  // No persistence yet
//                .billableItemsSummary(itemsSummaryList)
//                .grandTotalAmount(grandTotal)
//                .taxAmount(BigDecimal.ZERO)    // Contractor missing, no tax
//                .savingsAmount(BigDecimal.ZERO) // Contractor missing, no savings
//                .build();
//    }


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
