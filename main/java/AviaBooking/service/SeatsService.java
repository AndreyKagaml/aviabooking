package AviaBooking.service;

import AviaBooking.model.Plane;
import AviaBooking.model.Seats;

import java.util.List;

public interface SeatsService {
    Seats create(Seats seats);
    Seats readById(int id);
    Seats update(Seats seats);
    void delete(int id);
    List<Seats> getAll();
    List<Seats> getAllByPlane(Plane plane);
    Seats getByNumber(String number);
}
