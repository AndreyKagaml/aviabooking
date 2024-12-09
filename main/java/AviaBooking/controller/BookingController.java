package AviaBooking.controller;

import AviaBooking.model.*;
import AviaBooking.service.BookingService;
import AviaBooking.service.FlightService;
import AviaBooking.service.SeatsService;
import AviaBooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;

    private final UserService userService;

    private final FlightService flightService;

    private final SeatsService seatsService;

    @Autowired
    public BookingController(BookingService bookingService, UserService userService, FlightService flightService, SeatsService seatsService) {
        this.bookingService = bookingService;
        this.userService = userService;
        this.flightService = flightService;
        this.seatsService = seatsService;
    }


    @PostMapping("/create")
    public String createBooking(@RequestParam int seatId,
                                                           @RequestParam int userId,
                                                           @RequestParam int flightId) {
        System.out.println("Получаем данные: userId=" + userId + ", seatId=" + seatId + ", flightId=" + flightId);


        User user = userService.readById(userId);
        Seats seat = seatsService.readById(seatId);
        Flight flight = flightService.readById(flightId);

        System.out.println("Пользователь: " + user);
        System.out.println("Место: " + seat);
        System.out.println("Рейс: " + flight);


            // Получаем место по номеру и ряду (или создаем новое)

            // Создаем объект бронирования
            Booking booking = new Booking();
            booking.setFlight(flight);
            booking.setUser(user);

            double price = flight.getPrice();

            booking.setPrice(seat.getSeatClass().equals("Economy") ? price : price * 1.7);
            Loyalty userLoyalty = user.getLoyalty();
            userLoyalty.setPoints(userLoyalty.getPoints() * 1.14);

            booking.setSeat(seat);
            seat.setBook(true);
            booking.setStatus("BOOKED"); // Статус бронирования

            // Сохраняем бронирование в базу данных
            bookingService.create(booking);
        System.out.println("Бронирование создано: " + booking);



        return "redirect:/users/profile/"+ userId +"/bookings";
    }


    @PreAuthorize("hasAuthority('USER') and @userController.isPermitted(#id)")
    @GetMapping("/create")
    public String create(Model model) {
        List<Flight> flights = flightService.getAll();
        model.addAttribute("flights", flights);
        model.addAttribute("newBooking", new Booking());
        return "booking/booking-create";
    }

  //  @PreAuthorize("hasAuthority('USER') and @userController.isPermitted(#id)")
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id, Model model) {
        model.addAttribute("id", id);

        int userId = bookingService.readById(id).getUser().getId();
        model.addAttribute("userId", userId);
        return "booking/booking-delete";
    }

 //   @PreAuthorize("hasAuthority('USER') and @userController.isPermitted(#id)")
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        Booking booking = bookingService.readById(id);
        int userId = booking.getUser().getId();
        booking.getSeat().setBook(false);
        bookingService.delete(id);
        return "redirect:/users/profile/"+ userId +"/bookings";
    }



    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/all")
    public String getAll(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        int userId = userService.readByEmail(userDetails.getUsername()).getId(); // Отримання id користувача
        model.addAttribute("userId", userId);
        List<Booking> bookings = bookingService.getAll();
        model.addAttribute("bookings", bookings);
        return "booking/booking-all";
    }

}
