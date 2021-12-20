package com.paymybuddy.sylvain.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "external_transfer")
@PrimaryKeyJoinColumn(name = "transfer_id") // Primary Key de l'entité mere
public class ExternalTransfer extends Transfer {
    @Column(name = "fees")
    private BigDecimal fees;

    @ManyToOne
    @JoinColumn(name = "bank_account_iban")
    private BankAccount bankAccount;

    public ExternalTransfer() {
    }

    public ExternalTransfer( BigDecimal fees, BankAccount bankAccount) {
        super();
        this.fees = fees;
        this.bankAccount = bankAccount;
    }

    public BigDecimal getFees() {
        return fees;
    }

    public void setFees(BigDecimal fees) {
        this.fees = fees;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }
}
