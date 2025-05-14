package beto.projects.ipdbuddyapiv2.dto.jobs;

import beto.projects.ipdbuddyapiv2.entities.BillableItem;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


public class JobResponseDTO {

    private Long jobId;
    private BigDecimal taxAmount;
    private BigDecimal savingsAmount;
    private BigDecimal grandTotalAmount;
    private String message;
    private LocalDate date = LocalDate.now();
    private String address;
    private List<BillableItem> billableItems;

    public JobResponseDTO() {
    }

    //? Learning about builder patterns here
    //? Typing one out manually - after I understand the flow, I'll use Lombok's @Builder to avoid the boilerplate code.

    // * Static Builder class
    public static class Builder {

        //? Builders hold the same properties as the outer DTO (seems redundant - but this is how it's done)
//        private Long jobId;
        private BigDecimal taxAmount;
        private BigDecimal savingsAmount;
        private BigDecimal grandTotalAmount;
        private String message;
        private LocalDate date;
        private String address;
        private List<BillableItem> billableItems;

        //? "Setters" return the builder itself, "Builder setters" prevent the need for traditional setters

//        public Builder jobId(Long jobId){
//            this.jobId = jobId;
//            return this;
//        }

        public Builder taxAmount(BigDecimal taxAmount){
            this.taxAmount = taxAmount;
            return this;
        }

        public Builder savingsAmount(BigDecimal savingsAmount){
            this.savingsAmount = savingsAmount;
            return this;
        }

        public Builder grandTotalAmount(BigDecimal grandTotalAmount){
            this.grandTotalAmount = grandTotalAmount;
            return this;
        }

        public Builder message(String message){
            this.message = message;
            return this;
        }

        public Builder date(LocalDate date){
            this.date = date;
            return this;
        }

        public Builder address(String address){
            this.address = address;
            return this;
        }

        public Builder billableItems(List billableItems){
            this.billableItems = billableItems;
            return this;
        }

        //? build() method creates the DTO when called
        public JobResponseDTO build(){
            JobResponseDTO dto = new JobResponseDTO();

//            dto.jobId = this.jobId;
            dto.taxAmount = this.taxAmount;
            dto.savingsAmount = this.savingsAmount;
            dto.grandTotalAmount = this.grandTotalAmount;
            dto.message = this.message;
            dto.date = this.date;
            dto.address = this.address;
            dto.billableItems = this.billableItems;

            return dto;
        }
    }

    public static Builder builder(){
        return new Builder();
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

    public LocalDate getDate() {
        return date;
    }

    public String getAddress() {
        return address;
    }

    public List<BillableItem> getBillableItems() {
        return billableItems;
    }

    @Override
    public String toString() {
        return "JobResponseDTO{" +
                "jobId=" + jobId +
                ", taxAmount=" + taxAmount +
                ", savingsAmount=" + savingsAmount +
                ", grandTotalAmount=" + grandTotalAmount +
                ", message='" + message + '\'' +
                ", date=" + date +
                ", address='" + address + '\'' +
                '}';
    }
}
