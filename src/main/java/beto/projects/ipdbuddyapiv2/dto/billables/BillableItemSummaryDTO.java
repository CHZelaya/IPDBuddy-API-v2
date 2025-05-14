package beto.projects.ipdbuddyapiv2.dto.billables;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public class BillableItemSummaryDTO {

    private String type;
    private String description;
    private int quantity;
    private BigDecimal rate;
    private BigDecimal total;
    private String jobAddress;
    private LocalDate jobDate;

    public BillableItemSummaryDTO() {
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public String getJobAddress() {
        return jobAddress;
    }

    public LocalDate getJobDate() {
        return jobDate;
    }


    @Override
    public String toString() {
        return "BillableItemSummary{" +
                "type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", rate=" + rate +
                ", total=" + total +
                ", jobAddress='" + jobAddress + '\'' +
                ", jobDate=" + jobDate +
                '}';
    }
}
