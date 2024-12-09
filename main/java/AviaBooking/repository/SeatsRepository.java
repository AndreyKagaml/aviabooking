package AviaBooking.repository;

import AviaBooking.model.Plane;
import AviaBooking.model.Seats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatsRepository extends JpaRepository<Seats, Integer> {
    List<Seats> findByPlane(Plane plane);
    Seats findByNumber(String number);
}
