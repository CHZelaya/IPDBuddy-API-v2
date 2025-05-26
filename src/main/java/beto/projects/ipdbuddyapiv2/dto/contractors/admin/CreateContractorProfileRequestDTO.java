package beto.projects.ipdbuddyapiv2.dto.contractors.admin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
public class CreateContractorProfileRequestDTO {

    @NotNull
    @Pattern(regexp = "^[A-Za-zÀ-ÿ]+(-[A-Za-zÀ-ÿ]+)*$\n")
    private String firstName;

    @NotNull
    @Pattern(regexp = "^[A-Za-zÀ-ÿ]+(-[A-Za-zÀ-ÿ]+)*$\n")
    private String lastName;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Pattern(regexp = "^(\\+1[-.\\s]?)?(\\(?\\d{3}\\)?[-.\\s]?)\\d{3}[-.\\s]?\\d{4}$\n" )
    private String phoneNumber;

    private BigDecimal taxRate = BigDecimal.valueOf(00.10);

    private BigDecimal savingsRate = BigDecimal.valueOf(00.10);

    public CreateContractorProfileRequestDTO() {
    }

    public String getFirstName() {
        return firstName;
    }


    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
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
        return "ContractorProfileDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", taxRate=" + taxRate +
                ", savingsRate=" + savingsRate +
                '}';
    }
}

