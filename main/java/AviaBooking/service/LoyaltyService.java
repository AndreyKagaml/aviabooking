package AviaBooking.service;

import AviaBooking.model.Loyalty;
import AviaBooking.model.User;
import AviaBooking.model.UserRole;

import java.util.List;

public interface LoyaltyService {
    Loyalty create(Loyalty loyalty);
    Loyalty readById(int id);
    Loyalty update(Loyalty loyalty);
    void delete(int id);
    List<Loyalty> getAll();
    Loyalty findByUser(User user);
}
