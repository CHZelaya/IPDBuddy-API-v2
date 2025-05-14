package beto.projects.ipdbuddyapiv2.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "contractor")
public class Contractor {

    //* Contractor details
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    @Size(min = 2, max = 20)
    private String firstName;

    @NotNull
    @Column(nullable = false)
    @Size(min = 2, max = 20)
    private String lastName;

    @NotNull
    @Column(unique = true, nullable = false)
    @Email
    private String email;

    @NotNull
    @Column(nullable = false)
    @Pattern(regexp = "^\\d{10}$", message = "Must be a 10-digit number")
    private String phoneNumber;

    @OneToMany(mappedBy = "contractor")
    private List<Job> jobs = new ArrayList<>();

    @NotNull
    @DecimalMin(value = "0.00", inclusive = true, message = "Must be a non-negative rate")
    @DecimalMax(value = "1.00", inclusive = true, message = "Must be less than or equal to 1.0")

    //* Money and Tax related
    private BigDecimal taxRate;
    @NotNull
    @DecimalMin(value = "0.00", inclusive = true, message = "Must be a non-negative rate")
    @DecimalMax(value = "1.00", inclusive = true, message = "Must be less than or equal to 1.0")

    private BigDecimal savingsRate;

    public Contractor() {
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimal getSavingsRate() {
        return savingsRate;
    }

    public void setSavingsRate(BigDecimal savingsRate) {
        this.savingsRate = savingsRate;
    }

    @Override
    public String toString() {
        return "Contractor{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", jobs=" + jobs +
                ", taxRate=" + taxRate +
                ", savingsRate=" + savingsRate +
                '}';
    }
}
