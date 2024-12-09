package AviaBooking.controller;

import AviaBooking.model.Flight;
import AviaBooking.model.Plane;
import AviaBooking.model.Seats;
import AviaBooking.model.User;
import AviaBooking.service.FlightService;
import AviaBooking.service.PlaneService;
import AviaBooking.service.SeatsService;
import AviaBooking.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/seats")
public class SeatsController {
    private final SeatsService seatsService;
    private final PlaneService planeService;
    private final FlightService flightService;

    private final UserService userService;


    @Autowired
    public SeatsController(SeatsService seatsService, PlaneService planeService, FlightService flightService, UserService userService) {
        this.seatsService = seatsService;
        this.planeService = planeService;
        this.flightService = flightService;
        this.userService = userService;
    }

//    @GetMapping("/flight/{flightId}")
//    public String getSeatsByFlight(@PathVariable int flightId, Model model) {
//
//
//        // Получаем рейс по ID
//        Flight flight = flightService.readById(flightId);
//        if (flight == null) {
//            return "error"; // Если рейс не найден
//        }
//
//        // Получаем самолет, связанный с рейсом
//        Plane plane = flight.getPlane();
//        if (plane == null) {
//            return "error"; // Если самолет не найден
//        }
//
//        // Получаем список мест самолета
//        List<Seats> seats = seatsService.getAllByPlane(plane);
//
//        model.addAttribute("flight", flight);
//        model.addAttribute("plane", plane);
//        model.addAttribute("seats", seats);
//
//        return "seat/flight-seats"; // Страница с местами
//    }

    @GetMapping("/flight/{flightId}")
    public String getSeatsByFlight(@PathVariable int flightId, Model model) throws JsonProcessingException {
        // Получаем рейс по ID
        Flight flight = flightService.readById(flightId);
        if (flight == null) {
            return "error"; // Если рейс не найден
        }

        // Получаем самолет, связанный с рейсом
        Plane plane = flight.getPlane();
        if (plane == null) {
            return "error"; // Если самолет не найден
        }

        // Получаем список мест самолета
        List<Seats> seats = seatsService.getAllByPlane(plane);

        // Группируем места по рядам
        Map<Integer, List<Seats>> rows = new HashMap<>();
        for (Seats seat : seats) {
            int row = Integer.parseInt(seat.getNumber().replaceAll("\\D", ""));  // Извлекаем номер ряда
            rows.putIfAbsent(row, new ArrayList<>());
            rows.get(row).add(seat);
        }

        // Преобразуем в формат для JSON
        List<Map<String, Object>> seatRows = new ArrayList<>();
        for (Map.Entry<Integer, List<Seats>> entry : rows.entrySet()) {
            List<Map<String, Object>> seatListInRow = new ArrayList<>();
            for (Seats seat : entry.getValue()) {
                Map<String, Object> seatInfo = new HashMap<>();
                seatInfo.put("id", seat.getId());
                seatInfo.put("number", seat.getNumber());
                seatInfo.put("book", seat.isBook());
                seatListInRow.add(seatInfo);
            }
            Map<String, Object> rowInfo = new HashMap<>();
            rowInfo.put("row", entry.getKey());
            rowInfo.put("seats", seatListInRow);
            seatRows.add(rowInfo);
        }

        // Добавляем данные в модель
        model.addAttribute("seatRowsJson", new ObjectMapper().writeValueAsString(seatRows)); // JSON для Thymeleaf
        model.addAttribute("flight", flight);
        model.addAttribute("plane", plane);
        System.out.println("JSON: " + new ObjectMapper().writeValueAsString(seatRows));



        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Имя пользователя (обычно это email или login)

        // Найти объект пользователя по имени пользователя
        User user = userService.readByEmail(username);
        model.addAttribute("user", user);


        return "seat/flight-seats"; // Страница с местами
    }



}
