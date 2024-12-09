package AviaBooking.service.Impl;

import AviaBooking.exception.EntityErrorException;
import AviaBooking.model.Loyalty;
import AviaBooking.model.User;
import AviaBooking.repository.LoyaltyRepository;
import AviaBooking.repository.UserRepository;
import AviaBooking.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final LoyaltyRepository loyaltyRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, LoyaltyRepository loyaltyRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.loyaltyRepository = loyaltyRepository;
    }



    @Override
    public User create(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }catch (Exception e){
            throw new EntityErrorException("User with this code already exists");
        }
    }

    @Override
    public User readById(int id) {
        return userRepository.getOne(id);
    }

    @Override
    public User update(User user) {
        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // Обновить только изменённые поля
        existingUser.setName(user.getName());
        existingUser.setSurname(user.getSurname());
            existingUser.setAge(user.getAge());
            existingUser.setLogin(user.getLogin());
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                user.setPassword(user.getPassword());
            }
            existingUser.setEmail(user.getEmail());

     //   try {

         //   System.out.println(user.getRole().getRole());
            return userRepository.save(existingUser);
//        }catch (Exception e){
//            throw new EntityErrorException("User with this code already exists");
//        }
    }

    @Override
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User readByEmail(String email) {
        User user = userRepository.findUserByEmail(email);
        if (user != null) {
            return user;
        }
        throw new UsernameNotFoundException(String.format("User with email '%s' not found.", email));
    }
}
