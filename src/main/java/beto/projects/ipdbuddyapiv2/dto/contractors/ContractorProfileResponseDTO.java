package beto.projects.ipdbuddyapiv2.dto.contractors;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
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
}
