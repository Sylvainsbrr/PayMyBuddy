package com.paymybuddy.sylvain.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "internal_transfer")
@PrimaryKeyJoinColumn(name = "transfer_id") // Primary Key de l'entité mere
public class InternalTransfer extends Transfer {
    @JoinColumn(name = "sender")
    @ManyToOne
    private User userSender;

    @JoinColumn(name = "receiver")
    @ManyToOne
    private User userReceiver;

    public InternalTransfer() {
    }

    public InternalTransfer(User userSender, User userReceiver) {
        super();
        this.userSender = userSender;
        this.userReceiver = userReceiver;
    }

    public User getUserSender() {
        return userSender;
    }

    public void setUserSender(User userSender) {
        this.userSender = userSender;
    }

    public User getUserReceiver() {
        return userReceiver;
    }

    public void setUserReceiver(User userReceiver) {
        this.userReceiver = userReceiver;
    }
}
