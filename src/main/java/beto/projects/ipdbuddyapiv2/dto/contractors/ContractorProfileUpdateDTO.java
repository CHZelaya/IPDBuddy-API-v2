package beto.projects.ipdbuddyapiv2.dto.contractors;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
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
}
