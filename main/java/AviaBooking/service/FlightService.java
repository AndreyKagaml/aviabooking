package AviaBooking.service;

import AviaBooking.model.Booking;
import AviaBooking.model.Flight;

import java.util.List;

public interface FlightService {
    Flight create(Flight flight);
    Flight readById(int id);
    Flight update(Flight flight);
    void delete(int id);
    List<Flight> getAll();
    List<Flight> getAllByDepartureAndAndArrival(String departure, String arrival);
}
