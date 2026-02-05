package beto.projects.ipdbuddyapiv2.dto.billables;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BillableItemSummaryResponseDTO {

    private String name;
    private String type;
    private String description;
    private int quantity;
    private BigDecimal rate;
    private BigDecimal total;
    private String jobAddress;
    private LocalDate jobDate;

    public BillableItemSummaryResponseDTO() {
    }

    public BillableItemSummaryResponseDTO(String name, String type, String description, int quantity, BigDecimal rate, BigDecimal total, String jobAddress, LocalDate jobDate) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.quantity = quantity;
        this.rate = rate;
        this.total = total;
        this.jobAddress = jobAddress;
        this.jobDate = jobDate;
    }

    public static BillableItemSummaryResponseDTOBuilder builder() {
        return new BillableItemSummaryResponseDTOBuilder();
    }

    public String getName() {
        return name;
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

    public static class BillableItemSummaryResponseDTOBuilder {
        private String name;
        private String type;
        private String description;
        private int quantity;
        private BigDecimal rate;
        private BigDecimal total;
        private String jobAddress;
        private LocalDate jobDate;

        BillableItemSummaryResponseDTOBuilder() {
        }

        public BillableItemSummaryResponseDTOBuilder name(String name) {
            this.name = name;
            return this;
        }

        public BillableItemSummaryResponseDTOBuilder type(String type) {
            this.type = type;
            return this;
        }

        public BillableItemSummaryResponseDTOBuilder description(String description) {
            this.description = description;
            return this;
        }

        public BillableItemSummaryResponseDTOBuilder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public BillableItemSummaryResponseDTOBuilder rate(BigDecimal rate) {
            this.rate = rate;
            return this;
        }

        public BillableItemSummaryResponseDTOBuilder total(BigDecimal total) {
            this.total = total;
            return this;
        }

        public BillableItemSummaryResponseDTOBuilder jobAddress(String jobAddress) {
            this.jobAddress = jobAddress;
            return this;
        }

        public BillableItemSummaryResponseDTOBuilder jobDate(LocalDate jobDate) {
            this.jobDate = jobDate;
            return this;
        }

        public BillableItemSummaryResponseDTO build() {
            return new BillableItemSummaryResponseDTO(this.name, this.type, this.description, this.quantity, this.rate, this.total, this.jobAddress, this.jobDate);
        }

        public String toString() {
            return "BillableItemSummaryResponseDTO.BillableItemSummaryResponseDTOBuilder(name=" + this.name + ", type=" + this.type + ", description=" + this.description + ", quantity=" + this.quantity + ", rate=" + this.rate + ", total=" + this.total + ", jobAddress=" + this.jobAddress + ", jobDate=" + this.jobDate + ")";
        }
    }
}
