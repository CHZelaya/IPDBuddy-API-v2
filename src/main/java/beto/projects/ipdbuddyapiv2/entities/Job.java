package beto.projects.ipdbuddyapiv2.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private LocalDate date;

    @NotNull
    @Column(nullable = false)
    private String address;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "contractor_id", referencedColumnName = "id")
    private Contractor contractor;

    @OneToMany(mappedBy = "job")
    private List<BillableItem> billableItems = new ArrayList<>();

    @NotNull
    private BigDecimal grandTotalAmount;

    @NotNull
    private BigDecimal taxAmount;

    @NotNull
    private BigDecimal savingsAmount;

    @Lob
    private String notes;

    public Job() {
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Contractor getContractor() {
        return contractor;
    }

    public void setContractor(Contractor contractor) {
        this.contractor = contractor;
    }

    public List<BillableItem> getBillableItems() {
        return billableItems;
    }

    public void setBillableItems(List<BillableItem> billableItems) {
        this.billableItems = billableItems;
    }

    public BigDecimal getGrandTotalAmount() {
        return grandTotalAmount;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public BigDecimal getSavingsAmount() {
        return savingsAmount;
    }

    public void setGrandTotalAmount(BigDecimal grandTotalAmount) {
        this.grandTotalAmount = grandTotalAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public void setSavingsAmount(BigDecimal savingsAmount) {
        this.savingsAmount = savingsAmount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", date=" + date +
                ", address='" + address + '\'' +
                ", contractor=" + contractor +
                ", billableItems=" + billableItems +
                ", grandTotalAmount=" + grandTotalAmount +
                ", taxAmount=" + taxAmount +
                ", savingsAmount=" + savingsAmount +
                ", notes='" + notes + '\'' +
                '}';
    }
}
