package AviaBooking.service;

import AviaBooking.model.Loyalty;
import AviaBooking.model.Plane;

import java.util.List;

public interface PlaneService {
    Plane create(Plane plane);
    Plane readById(int id);
    Plane update(Plane plane);
    void delete(int id);
    List<Plane> getAll();
}
