package AviaBooking.controller;

import AviaBooking.exception.EntityErrorException;
import AviaBooking.model.Booking;
import AviaBooking.model.Loyalty;
import AviaBooking.model.User;
import AviaBooking.model.UserRole;
import AviaBooking.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserRoleService userRoleService;
    private final FlightService flightService;
    private final PlaneService planeService;
    private final BookingService bookingService;
    private final LoyaltyService loyaltyService;

    @Autowired
    public UserController(UserService userService, UserRoleService userRoleService, FlightService flightService, PlaneService planeService, BookingService bookingService, LoyaltyService loyaltyService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.flightService = flightService;
        this.planeService = planeService;
        this.bookingService = bookingService;
        this.loyaltyService = loyaltyService;
    }









    @GetMapping("/profile/{id}")
    public String userProfile(@PathVariable int id, Model model) {
        // Получаем данные пользователя по его ID
        User user = userService.readById(id);

        model.addAttribute("user", user);
        if (user.getRole().getRole().equals("ADMIN")) {
            model.addAttribute("flights", flightService.getAll());
            model.addAttribute("planes", planeService.getAll());
            model.addAttribute("users", userService.getAll());
            return "user/admin-profile";
        } else if (user.getRole().getRole().equals("USER")) {
            model.addAttribute("flights", flightService.getAll());
            return "user/user-profile";
        }

        return "error"; // Если роль не определена
    }

    @GetMapping("/profile/{id}/bookings")
    public String userBookings(@PathVariable int id, Model model) {
        // Получаем данные пользователя по его ID
        User user = userService.readById(id);
        List<Booking> bookings = bookingService.getByUser(user);

        model.addAttribute("user", user);
        if (user.getRole().getRole().equals("USER")) {
            model.addAttribute("bookings", bookings);
            model.addAttribute("user", user);
            return "booking/user-booking";
        }

        return "error"; // Если роль не определена
    }






















    @PostMapping("/create")
    public String create(@ModelAttribute("newUser") @Valid User user, BindingResult result, Model model) {
      //  user.setDateStartWork(LocalDate.now());
        if (result.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            result.getAllErrors().stream().map(i -> i.getDefaultMessage()).forEach(i -> errors.append(i).append('\n'));
            model.addAttribute("errors", errors);
            return "user/user-create";
        }
        try {
            user.setPassword(user.getPassword());
            user.setRole(userRoleService.readById(1));
            userService.create(user);
            if (user.getRole().getRole().equals("USER")) {
                Loyalty loyalty = new Loyalty();
                loyalty.setPoints(0);
                loyalty.setUser(user);
                loyaltyService.create(loyalty);
            }
        }catch (EntityErrorException e){
            model.addAttribute("info", e.getMessage());
            return "500";
        }
        return "redirect:/users/all";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("newUser", new User());
        return "user/user-create";
    }


//    @GetMapping("/{id}/read")
//    public String read(@PathVariable("id") long id, Model model) {
//        Manager manager = managerService.readById(id);
//        model.addAttribute("manager", manager);
//        return "user-info";
//    }

//    @PreAuthorize("hasAuthority('USER')")
//    @GetMapping("/home")
//    public String read(Model model) {
//        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Manager manager = managerService.readByEmail(principal.getUsername());
//        model.addAttribute("manager", manager);
//        return "home";
//    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER') and @userController.isPermitted(#id)")
    @PostMapping("/{id}/edit")
    public String update(@PathVariable("id") int id, @ModelAttribute("editedUser") @Valid User user, BindingResult result, Model model) {
         if (result.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            result.getAllErrors().stream().map(i -> i.getDefaultMessage()).forEach(i -> errors.append(i).append('\n'));
            model.addAttribute("errors", errors);
            return "user/user-update";
        }
        try {

            userService.update(user);
        }catch (EntityErrorException e){
            model.addAttribute("info", e.getMessage());
            return "500";
        }

        userService.update(user);
        return "redirect:/users/profile/" + id;
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER') and @userController.isPermitted(#id)")
    @GetMapping("/{id}/edit")
    public String update(@PathVariable("id") int id, Model model) {
        User editedUser = userService.readById(id);
        model.addAttribute("editedUser", editedUser);
        return "user/user-update";
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") long id, Model model) {
        model.addAttribute("id", id);
        return "user/user-delete";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/users/all";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/all")
    public String getAll(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        int userId = userService.readByEmail(userDetails.getUsername()).getId(); // Отримання id користувача
        model.addAttribute("userId", userId);
        model.addAttribute("users", userService.getAll());
        return "user/user-all";
    }

    public boolean isPermitted(long id) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.readByEmail(principal.getUsername());
        return user.getId() == id;
    }

    public boolean isPermittedEmail(String email) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.readByEmail(principal.getUsername());
        return user.getEmail().equals(email);
    }

}
