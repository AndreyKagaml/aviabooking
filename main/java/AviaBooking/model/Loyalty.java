package AviaBooking.model;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "loyalty")
public class Loyalty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private double points;

    public Loyalty() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loyalty loyalty = (Loyalty) o;
        return id == loyalty.id && points == loyalty.points;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, points);
    }
}
