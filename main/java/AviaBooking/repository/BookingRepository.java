package AviaBooking.repository;

import AviaBooking.model.Booking;
import AviaBooking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    List<Booking> getBookingsByUser(User user);
}
