package com.example.cinema_schedule.model;

import javax.persistence.*;

@Entity
@Table(name = "Bookings")
public class Booking {
    @Id
    private Long id;

    private int seat;

    @ManyToOne
    private User user;

    @ManyToOne
    private Broadcast broadcast;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Broadcast getBroadcast() {
        return broadcast;
    }

    public void setBroadcast(Broadcast broadcast) {
        this.broadcast = broadcast;
    }
}
