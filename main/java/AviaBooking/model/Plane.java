package AviaBooking.model;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "plane")
public class Plane {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 50)
    private String model;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false, unique = true, length = 20)
    private String number;

    @Column(nullable = false)
    private int countSeats;

    @OneToMany(mappedBy = "plane", cascade = CascadeType.ALL)
    private List<Seats> seats;

    @OneToMany(mappedBy = "plane", cascade = CascadeType.ALL)
    private List<Flight> flights;

    public Plane() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getCountSeats() {
        return countSeats;
    }

    public void setCountSeats(int countSeats) {
        this.countSeats = countSeats;
    }

    public List<Seats> getSeats() {
        return seats;
    }

    public void setSeats(List<Seats> seats) {
        this.seats = seats;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plane plane = (Plane) o;
        return id == plane.id && year == plane.year && countSeats == plane.countSeats && Objects.equals(model, plane.model) && Objects.equals(number, plane.number) && Objects.equals(seats, plane.seats) && Objects.equals(flights, plane.flights);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, model, year, number, countSeats, seats, flights);
    }
}