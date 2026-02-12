package com.andrew.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "deliveries")
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "supply_id", nullable = false)
    private Supply supply;

    @ManyToOne
    @JoinColumn(name = "visit_request_id", nullable = false)
    private VisitRequest visitRequest;

    @Column(nullable = false)
    private Integer amount;

    public Delivery() {
    }

    public Delivery(Supply supply, VisitRequest visitRequest, Integer amount) {
        this.supply = supply;
        this.visitRequest = visitRequest;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Supply getSupply() {
        return supply;
    }

    public void setSupply(Supply supply) {
        this.supply = supply;
    }

    public VisitRequest getVisitRequest() {
        return visitRequest;
    }

    public void setVisitRequest(VisitRequest visitRequest) {
        this.visitRequest = visitRequest;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
