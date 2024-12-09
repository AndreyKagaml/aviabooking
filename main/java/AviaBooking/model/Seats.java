package AviaBooking.model;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "seats")
public class Seats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "plane_id", nullable = false)
    private Plane plane;

    @Column(nullable = false, length = 20)
    private String seatClass;

    @Column(nullable = false, length = 10)
    private String number;

    @Column(nullable = false)
    private boolean book;

    @OneToMany(mappedBy = "seat", cascade = CascadeType.ALL)
    private List<Booking> bookings;

    public Seats() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public String getSeatClass() {
        return seatClass;
    }

    public void setSeatClass(String seatClass) {
        this.seatClass = seatClass;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isBook() {
        return book;
    }

    public void setBook(boolean book) {
        this.book = book;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seats seats = (Seats) o;
        return id == seats.id && book == seats.book && Objects.equals(plane, seats.plane) && Objects.equals(seatClass, seats.seatClass) && Objects.equals(number, seats.number) && Objects.equals(bookings, seats.bookings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, plane, seatClass, number, book, bookings);
    }
}
