package AviaBooking.controller;

import AviaBooking.exception.EntityErrorException;
import AviaBooking.model.Flight;
import AviaBooking.model.Plane;
import AviaBooking.model.User;
import AviaBooking.service.BookingService;
import AviaBooking.service.FlightService;
import AviaBooking.service.PlaneService;
import AviaBooking.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/flights")
public class FlightController {
    private final FlightService flightService;
    private final PlaneService planeService;
    private final BookingService bookingService;

    private final UserService userService;

    @Autowired
    public FlightController(FlightService flightService, PlaneService planeService, BookingService bookingService, UserService userService) {
        this.flightService = flightService;
        this.planeService = planeService;
        this.bookingService = bookingService;
        this.userService = userService;
    }


    //TODO
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/create")
    public String create(@ModelAttribute("newFlight") @Valid Flight flight, BindingResult result, Model model) {
        if (result.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            result.getAllErrors().stream().map(i -> i.getDefaultMessage()).forEach(i -> errors.append(i).append('\n'));
            model.addAttribute("errors", errors);
            return "flight/flight-create";
        }
        try {
//            user.setPassword(user.getPassword());
//            user.setRole(userRoleService.readById(2));
            flight.setPlane(flight.getPlane());
            flightService.create(flight);
        }catch (EntityErrorException e){
            model.addAttribute("info", e.getMessage());
            return "500";
        }
        return "redirect:/flights/all";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/create")
    public String create(Model model) {
        List<Plane> planes = planeService.getAll(); // Метод для получения списка самолетов
        model.addAttribute("planes", planes);
        model.addAttribute("newFlight", new Flight());
        return "flight/flight-create";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/all")
    public String getAll(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        int userId = userService.readByEmail(userDetails.getUsername()).getId(); // Отримання id користувача
        model.addAttribute("userId", userId);
        model.addAttribute("flights", flightService.getAll());
        return "flight/flight-all";
    }










    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{id}/edit")
    public String update(@PathVariable("id") int id, @ModelAttribute("editedFlight") @Valid Flight flight, BindingResult result, Model model) {
        if (result.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            result.getAllErrors().stream().map(i -> i.getDefaultMessage()).forEach(i -> errors.append(i).append('\n'));
            model.addAttribute("errors", errors);
            return "flight/flight-update";
        }
        try {
            //manager.setDateStartWork(LocalDate.now());
            flightService.update(flight);
        }catch (EntityErrorException e){
            model.addAttribute("info", e.getMessage());
            return "500";
        }


        return "redirect:/flights/all";
    }


//    @PostMapping("/{id}/update")
//    public String update(@PathVariable("id") long id, @ModelAttribute("updatedPhone") @Valid Phone updatedPhone, BindingResult result, Model model) {
//        long userId = updatedPhone.getManager().getId();
//        if (result.hasErrors()) {
//            StringBuilder errors = new StringBuilder();
//            result.getAllErrors().stream().map(i -> i.getDefaultMessage()).forEach(i -> errors.append(i).append('\n'));
//            model.addAttribute("errors", errors);
//            return "phone/phone-create";
//        }
//        try {
//            updatedPhone.setManager(managerService.readById(userId));
//            phonesService.update(updatedPhone);
//        }catch (EntityErrorException e){
//            model.addAttribute("info", e.getMessage());
//            return "500";
//        }
//        return "redirect:/phones/all?userId=" + userId;
//    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}/edit")
    public String update(@PathVariable("id") int id, Model model) {
        Flight editedFlight = flightService.readById(id);
        List<Plane> planes = planeService.getAll(); // Метод для получения списка самолетов
        model.addAttribute("planes", planes);

        model.addAttribute("editedFlight", editedFlight);
        return "flight/flight-update";
    }

















    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") long id, Model model) {
        model.addAttribute("id", id);
        return "flight/flight-delete";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        flightService.delete(id);
        return "redirect:/flights/all";
    }


    @GetMapping("/search")
    public String searchFlights(@RequestParam("departure") String departure,
                                @RequestParam("arrival") String arrival,
                               // @RequestParam("date") String date,
                                Model model) {
        // Преобразуем дату из строки в LocalDate
       // LocalDate flightDate = LocalDate.parse(date);

        // Выполняем поиск рейсов через сервис
        List<Flight> availableFlights = flightService.getAllByDepartureAndAndArrival(departure, arrival);
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.readByEmail(principal.getUsername());
        model.addAttribute("userId", user.getId()); // Передаем ID пользователя

        // Передаем данные в модель для отображения на странице
        model.addAttribute("flights", availableFlights);
        model.addAttribute("departure", departure);
        model.addAttribute("arrival", arrival);
      //  model.addAttribute("date", flightDate);

        // Возвращаем имя шаблона с результатами
        return "flight/search-results";
    }
}
