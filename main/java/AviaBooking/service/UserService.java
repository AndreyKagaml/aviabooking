package AviaBooking.service;

import AviaBooking.model.User;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {
    User create(User user);
    User readById(int id);
    User update(User user);
    void delete(int id);
    List<User> getAll();
    User readByEmail(String email);
}
