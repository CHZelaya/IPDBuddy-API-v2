package beto.projects.ipdbuddyapiv2.dto.jobs;

import beto.projects.ipdbuddyapiv2.dto.billables.BillableItemSummaryResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
@AllArgsConstructor
public class JobSubmissionResponseDTO {

    private Long jobId;
    private BigDecimal taxAmount;
    private BigDecimal savingsAmount;
    private BigDecimal grandTotalAmount;
    private String message;

    private List<BillableItemSummaryResponseDTO> billableItemsSummary;

    public JobSubmissionResponseDTO() {
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

    public String getMessage() {
        return message;
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
                ", message='" + message + '\'' +
                ", billableItemsSummary=" + billableItemsSummary +
                '}';
    }
}
