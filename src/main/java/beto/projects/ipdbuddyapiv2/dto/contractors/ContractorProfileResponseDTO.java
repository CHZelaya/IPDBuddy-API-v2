package beto.projects.ipdbuddyapiv2.dto.contractors;

import java.math.BigDecimal;

public class ContractorProfileResponseDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private BigDecimal taxRate;
    private BigDecimal savingsRate;

    private EarningsSummaryDTO earningsSummary;


    public ContractorProfileResponseDTO() {
    }

    public ContractorProfileResponseDTO(String firstName, String lastName, String email, String phoneNumber, BigDecimal taxRate, BigDecimal savingsRate, EarningsSummaryDTO earningsSummary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.taxRate = taxRate;
        this.savingsRate = savingsRate;
        this.earningsSummary = earningsSummary;
    }

    public static ContractorProfileResponseDTOBuilder builder() {
        return new ContractorProfileResponseDTOBuilder();
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public BigDecimal getSavingsRate() {
        return savingsRate;
    }

    public EarningsSummaryDTO getEarningsSummary() {
        return earningsSummary;
    }

    @Override
    public String toString() {
        return "ContractorProfileResponseDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", taxRate=" + taxRate +
                ", savingsRate=" + savingsRate +
                '}';
    }

    public static class ContractorProfileResponseDTOBuilder {
        private String firstName;
        private String lastName;
        private String email;
        private String phoneNumber;
        private BigDecimal taxRate;
        private BigDecimal savingsRate;
        private EarningsSummaryDTO earningsSummary;

        ContractorProfileResponseDTOBuilder() {
        }

        public ContractorProfileResponseDTOBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public ContractorProfileResponseDTOBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public ContractorProfileResponseDTOBuilder email(String email) {
            this.email = email;
            return this;
        }

        public ContractorProfileResponseDTOBuilder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public ContractorProfileResponseDTOBuilder taxRate(BigDecimal taxRate) {
            this.taxRate = taxRate;
            return this;
        }

        public ContractorProfileResponseDTOBuilder savingsRate(BigDecimal savingsRate) {
            this.savingsRate = savingsRate;
            return this;
        }

        public ContractorProfileResponseDTOBuilder earningsSummary(EarningsSummaryDTO earningsSummary) {
            this.earningsSummary = earningsSummary;
            return this;
        }

        public ContractorProfileResponseDTO build() {
            return new ContractorProfileResponseDTO(this.firstName, this.lastName, this.email, this.phoneNumber, this.taxRate, this.savingsRate, this.earningsSummary);
        }

        public String toString() {
            return "ContractorProfileResponseDTO.ContractorProfileResponseDTOBuilder(firstName=" + this.firstName + ", lastName=" + this.lastName + ", email=" + this.email + ", phoneNumber=" + this.phoneNumber + ", taxRate=" + this.taxRate + ", savingsRate=" + this.savingsRate + ", earningsSummary=" + this.earningsSummary + ")";
        }
    }
}
