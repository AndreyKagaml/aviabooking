package AviaBooking.service;

import AviaBooking.model.Booking;
import AviaBooking.model.User;

import java.util.List;

public interface BookingService {
    Booking create(Booking booking);
    Booking readById(int id);
    Booking update(Booking booking);
    void delete(int id);
    List<Booking> getAll();
    List<Booking> getByUser(User user);
}
