package AviaBooking.controller;

import AviaBooking.exception.EntityErrorException;
import AviaBooking.model.Plane;
import AviaBooking.service.PlaneService;
import AviaBooking.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/planes")
public class PlaneController {
    private final PlaneService planeService;
    private final UserService userService;

    @Autowired
    public PlaneController(PlaneService planeService, UserService userService) {
        this.planeService = planeService;
        this.userService = userService;
    }



    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/create")
    public String create(@ModelAttribute("newPlane") @Valid Plane plane, BindingResult result, Model model) {
        if (result.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            result.getAllErrors().stream().map(i -> i.getDefaultMessage()).forEach(i -> errors.append(i).append('\n'));
            model.addAttribute("errors", errors);
            return "plane/plane-create";
        }
        try {
//            user.setPassword(user.getPassword());
//            user.setRole(userRoleService.readById(2));

            planeService.create(plane);
        }catch (EntityErrorException e){
            model.addAttribute("info", e.getMessage());
            return "500";
        }
        return "redirect:/planes/all";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("newPlane", new Plane());
        return "plane/plane-create";
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{id}/edit")
    public String update(@PathVariable("id") int id, @ModelAttribute("editedPlane") @Valid Plane plane, BindingResult result, Model model) {
        if (result.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            result.getAllErrors().stream().map(i -> i.getDefaultMessage()).forEach(i -> errors.append(i).append('\n'));
            model.addAttribute("errors", errors);
            return "plane/plane-update";
        }
        try {
            //manager.setDateStartWork(LocalDate.now());
            planeService.update(plane);
        }catch (EntityErrorException e){
            model.addAttribute("info", e.getMessage());
            return "500";
        }


        return "redirect:/planes/all";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}/edit")
    public String update(@PathVariable("id") int id, Model model) {
        Plane editedPlane = planeService.readById(id);
        model.addAttribute("editedPlane", editedPlane);
        return "plane/plane-update";
    }






    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") long id, Model model) {
        model.addAttribute("id", id);
        return "plane/plane-delete";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        planeService.delete(id);
        return "redirect:/planes/all";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/all")
    public String getAll(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        int userId = userService.readByEmail(userDetails.getUsername()).getId(); // Отримання id користувача
        model.addAttribute("userId", userId);
        model.addAttribute("planes", planeService.getAll());
        return "plane/plane-all";
    }
}
