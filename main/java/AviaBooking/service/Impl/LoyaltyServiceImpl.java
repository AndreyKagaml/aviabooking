package AviaBooking.service.Impl;

import AviaBooking.model.Loyalty;
import AviaBooking.model.User;
import AviaBooking.repository.LoyaltyRepository;
import AviaBooking.service.LoyaltyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoyaltyServiceImpl implements LoyaltyService {

    private final LoyaltyRepository loyaltyRepository;

    public LoyaltyServiceImpl(LoyaltyRepository loyaltyRepository) {
        this.loyaltyRepository = loyaltyRepository;
    }

    @Override
    public Loyalty create(Loyalty loyalty) {
        return loyaltyRepository.save(loyalty);
    }

    @Override
    public Loyalty readById(int id) {
        return loyaltyRepository.getOne(id);
    }

    @Override
    public Loyalty update(Loyalty loyalty) {
        return loyaltyRepository.save(loyalty);
    }

    @Override
    public void delete(int id) {
        loyaltyRepository.deleteById(id);
    }

    @Override
    public List<Loyalty> getAll() {
        return loyaltyRepository.findAll();
    }

    @Override
    public Loyalty findByUser(User user) {
        return loyaltyRepository.findLoyaltyByUser(user);
    }
}
