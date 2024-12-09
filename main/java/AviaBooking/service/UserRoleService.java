package AviaBooking.service;

import AviaBooking.model.User;
import AviaBooking.model.UserRole;

import java.util.List;

public interface UserRoleService {
    UserRole create(UserRole userRole);
    UserRole readById(int id);
    UserRole update(UserRole userRole);
    void delete(int id);
    List<UserRole> getAll();
}
