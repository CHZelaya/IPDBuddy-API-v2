package beto.projects.ipdbuddyapiv2.dto.jobs;

import beto.projects.ipdbuddyapiv2.dto.billables.BillableItemSummaryResponseDTO;

import java.math.BigDecimal;
import java.util.List;

public class JobSubmissionResponseDTO {

    private Long jobId;
    private BigDecimal taxAmount;
    private BigDecimal savingsAmount;
    private BigDecimal grandTotalAmount;
    private String notes;

    private List<BillableItemSummaryResponseDTO> billableItemsSummary;

    public JobSubmissionResponseDTO() {
    }

    public JobSubmissionResponseDTO(Long jobId, BigDecimal taxAmount, BigDecimal savingsAmount, BigDecimal grandTotalAmount, String notes, List<BillableItemSummaryResponseDTO> billableItemsSummary) {
        this.jobId = jobId;
        this.taxAmount = taxAmount;
        this.savingsAmount = savingsAmount;
        this.grandTotalAmount = grandTotalAmount;
        this.notes = notes;
        this.billableItemsSummary = billableItemsSummary;
    }

    public static JobSubmissionResponseDTOBuilder builder() {
        return new JobSubmissionResponseDTOBuilder();
    }

    public Long getJobId() {
        return jobId;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public BigDecimal getSavingsAmount() {
        return savingsAmount;
    }

    public BigDecimal getGrandTotalAmount() {
        return grandTotalAmount;
    }

    public String getNotes() {
        return notes;
    }

    public List<BillableItemSummaryResponseDTO> getBillableItemsSummary() {
        return billableItemsSummary;
    }

    @Override
    public String toString() {
        return "JobSubmissionResponseDTO{" +
                "jobId=" + jobId +
                ", taxAmount=" + taxAmount +
                ", savingsAmount=" + savingsAmount +
                ", grandTotalAmount=" + grandTotalAmount +
                ", notes='" + notes + '\'' +
                ", billableItemsSummary=" + billableItemsSummary +
                '}';
    }

    public static class JobSubmissionResponseDTOBuilder {
        private Long jobId;
        private BigDecimal taxAmount;
        private BigDecimal savingsAmount;
        private BigDecimal grandTotalAmount;
        private String notes;
        private List<BillableItemSummaryResponseDTO> billableItemsSummary;

        JobSubmissionResponseDTOBuilder() {
        }

        public JobSubmissionResponseDTOBuilder jobId(Long jobId) {
            this.jobId = jobId;
            return this;
        }

        public JobSubmissionResponseDTOBuilder taxAmount(BigDecimal taxAmount) {
            this.taxAmount = taxAmount;
            return this;
        }

        public JobSubmissionResponseDTOBuilder savingsAmount(BigDecimal savingsAmount) {
            this.savingsAmount = savingsAmount;
            return this;
        }

        public JobSubmissionResponseDTOBuilder grandTotalAmount(BigDecimal grandTotalAmount) {
            this.grandTotalAmount = grandTotalAmount;
            return this;
        }

        public JobSubmissionResponseDTOBuilder notes(String notes) {
            this.notes = notes;
            return this;
        }

        public JobSubmissionResponseDTOBuilder billableItemsSummary(List<BillableItemSummaryResponseDTO> billableItemsSummary) {
            this.billableItemsSummary = billableItemsSummary;
            return this;
        }

        public JobSubmissionResponseDTO build() {
            return new JobSubmissionResponseDTO(this.jobId, this.taxAmount, this.savingsAmount, this.grandTotalAmount, this.notes, this.billableItemsSummary);
        }

        public String toString() {
            return "JobSubmissionResponseDTO.JobSubmissionResponseDTOBuilder(jobId=" + this.jobId + ", taxAmount=" + this.taxAmount + ", savingsAmount=" + this.savingsAmount + ", grandTotalAmount=" + this.grandTotalAmount + ", notes=" + this.notes + ", billableItemsSummary=" + this.billableItemsSummary + ")";
        }
    }
}
