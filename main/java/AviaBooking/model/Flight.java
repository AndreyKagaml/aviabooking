package AviaBooking.model;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "flight")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private LocalDate dateDeparture;

    @Column(nullable = false)
    private LocalTime timeDeparture;

    @Column(nullable = false)
    private LocalDate dateArrival;

    @Column(nullable = false)
    private LocalTime timeArrival;

    @Column(nullable = false, length = 100)
    private String departure;

    @Column(nullable = false, length = 100)
    private String arrival;

    @Column(nullable = false)
    private double price;

    @Column(name = "aviaCompany", nullable = false, length = 100)
    private String aviaCompany;

    @ManyToOne
    @JoinColumn(name = "plane_id", nullable = false)
    private Plane plane;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL)
    private List<Booking> bookings;

    public Flight() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDateDeparture() {
        return dateDeparture;
    }

    public void setDateDeparture(LocalDate dateDeparture) {
        this.dateDeparture = dateDeparture;
    }

    public LocalTime getTimeDeparture() {
        return timeDeparture;
    }

    public void setTimeDeparture(LocalTime timeDeparture) {
        this.timeDeparture = timeDeparture;
    }

    public LocalDate getDateArrival() {
        return dateArrival;
    }

    public void setDateArrival(LocalDate dateArrival) {
        this.dateArrival = dateArrival;
    }

    public LocalTime getTimeArrival() {
        return timeArrival;
    }

    public void setTimeArrival(LocalTime timeArrival) {
        this.timeArrival = timeArrival;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getAviaCompany() {
        return aviaCompany;
    }

    public void setAviaCompany(String aviaCompany) {
        this.aviaCompany = aviaCompany;
    }

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return id == flight.id && Objects.equals(dateDeparture, flight.dateDeparture) && Objects.equals(timeDeparture, flight.timeDeparture) && Objects.equals(dateArrival, flight.dateArrival) && Objects.equals(timeArrival, flight.timeArrival) && Objects.equals(departure, flight.departure) && Objects.equals(arrival, flight.arrival) && Objects.equals(aviaCompany, flight.aviaCompany) && Objects.equals(plane, flight.plane) && Objects.equals(bookings, flight.bookings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateDeparture, timeDeparture, dateArrival, timeArrival, departure, arrival, aviaCompany, plane, bookings);
    }
}
