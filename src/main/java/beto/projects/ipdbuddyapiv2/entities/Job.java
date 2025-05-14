package beto.projects.ipdbuddyapiv2.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

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

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", date=" + date +
                ", address='" + address + '\'' +
                ", contractor=" + contractor +
                ", billableItems=" + billableItems +
                '}';
    }
}
