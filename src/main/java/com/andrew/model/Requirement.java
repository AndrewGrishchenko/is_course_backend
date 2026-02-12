package com.andrew.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "requirements")
public class Requirement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "reward_id", nullable = false)
    private Supply reward;

    @Column(name = "reward_amount")
    private Long rewardAmount;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "until_date")
    private LocalDate untilDate;

    public Requirement() {
    }

    public Requirement(User user, Supply reward, Long rewardAmount, LocalDate startDate, LocalDate untilDate) {
        this.user = user;
        this.reward = reward;
        this.rewardAmount = rewardAmount;
        this.startDate = startDate;
        this.untilDate = untilDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Supply getReward() {
        return reward;
    }

    public void setReward(Supply reward) {
        this.reward = reward;
    }

    public Long getRewardAmount() {
        return rewardAmount;
    }

    public void setRewardAmount(Long rewardAmount) {
        this.rewardAmount = rewardAmount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getUntilDate() {
        return untilDate;
    }

    public void setUntilDate(LocalDate untilDate) {
        this.untilDate = untilDate;
    }
}
