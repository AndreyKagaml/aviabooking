package AviaBooking.repository;

import AviaBooking.model.Loyalty;
import AviaBooking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoyaltyRepository extends JpaRepository<Loyalty, Integer> {
    Loyalty findLoyaltyByUser(User user);
}
