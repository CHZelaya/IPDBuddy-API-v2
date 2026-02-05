package beto.projects.ipdbuddyapiv2.dto.contractors;


import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class ContractorProfileUpdateDTO {


    @NotNull
    @Size(min = 2, max = 20)
    private String firstName;

    @NotNull
    @Size(min = 2, max = 20)
    private String lastName;

    @NotNull
    @Pattern(regexp = "^\\d{10}$", message = "Must be a 10-digit number")
    private String phoneNumber;

    @NotNull
    @DecimalMin("0.00")
    @DecimalMax("1.00")
    private BigDecimal taxRate;

    @NotNull
    @DecimalMin("0.00")
    @DecimalMax("1.00")
    private BigDecimal savingsRate;

    public ContractorProfileUpdateDTO() {
    }

    public ContractorProfileUpdateDTO(@NotNull @Size(min = 2, max = 20) String firstName, @NotNull @Size(min = 2, max = 20) String lastName, @NotNull @Pattern(regexp = "^\\d{10}$", message = "Must be a 10-digit number") String phoneNumber, @NotNull @DecimalMin("0.00") @DecimalMax("1.00") BigDecimal taxRate, @NotNull @DecimalMin("0.00") @DecimalMax("1.00") BigDecimal savingsRate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.taxRate = taxRate;
        this.savingsRate = savingsRate;
    }

    public static ContractorProfileUpdateDTOBuilder builder() {
        return new ContractorProfileUpdateDTOBuilder();
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

    @Override
    public String toString() {
        return "ContractorProfileUpdateDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", taxRate=" + taxRate +
                ", savingsRate=" + savingsRate +
                '}';
    }

    public static class ContractorProfileUpdateDTOBuilder {
        private @NotNull
        @Size(min = 2, max = 20) String firstName;
        private @NotNull
        @Size(min = 2, max = 20) String lastName;
        private @NotNull
        @Pattern(regexp = "^\\d{10}$", message = "Must be a 10-digit number") String phoneNumber;
        private @NotNull
        @DecimalMin("0.00")
        @DecimalMax("1.00") BigDecimal taxRate;
        private @NotNull
        @DecimalMin("0.00")
        @DecimalMax("1.00") BigDecimal savingsRate;

        ContractorProfileUpdateDTOBuilder() {
        }

        public ContractorProfileUpdateDTOBuilder firstName(@NotNull @Size(min = 2, max = 20) String firstName) {
            this.firstName = firstName;
            return this;
        }

        public ContractorProfileUpdateDTOBuilder lastName(@NotNull @Size(min = 2, max = 20) String lastName) {
            this.lastName = lastName;
            return this;
        }

        public ContractorProfileUpdateDTOBuilder phoneNumber(@NotNull @Pattern(regexp = "^\\d{10}$", message = "Must be a 10-digit number") String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public ContractorProfileUpdateDTOBuilder taxRate(@NotNull @DecimalMin("0.00") @DecimalMax("1.00") BigDecimal taxRate) {
            this.taxRate = taxRate;
            return this;
        }

        public ContractorProfileUpdateDTOBuilder savingsRate(@NotNull @DecimalMin("0.00") @DecimalMax("1.00") BigDecimal savingsRate) {
            this.savingsRate = savingsRate;
            return this;
        }

        public ContractorProfileUpdateDTO build() {
            return new ContractorProfileUpdateDTO(this.firstName, this.lastName, this.phoneNumber, this.taxRate, this.savingsRate);
        }

        public String toString() {
            return "ContractorProfileUpdateDTO.ContractorProfileUpdateDTOBuilder(firstName=" + this.firstName + ", lastName=" + this.lastName + ", phoneNumber=" + this.phoneNumber + ", taxRate=" + this.taxRate + ", savingsRate=" + this.savingsRate + ")";
        }
    }
}
