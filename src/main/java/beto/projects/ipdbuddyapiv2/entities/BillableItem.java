package beto.projects.ipdbuddyapiv2.entities;

import beto.projects.ipdbuddyapiv2.enums.Billables;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;


@Entity
public class BillableItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private Billables billableType;

    @NotNull
    private int quantity;

    @NotNull
    private BigDecimal rate;

    @NotNull
    private BigDecimal totalPrice;



    @NotNull
    @ManyToOne
    private Job job;

    public BillableItem() {
    }

    public long getId() {
        return id;
    }

    public Billables getBillableType() {
        return billableType;
    }

    public void setBillableType(Billables billableType) {
        this.billableType = billableType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }
}
