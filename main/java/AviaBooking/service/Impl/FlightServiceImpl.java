package AviaBooking.service.Impl;

import AviaBooking.model.Flight;
import AviaBooking.repository.FlightRepository;
import AviaBooking.service.FlightService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {
    private final FlightRepository flightRepository;

    public FlightServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }


    @Override
    public Flight create(Flight flight) {
        return flightRepository.save(flight);
    }

    @Override
    public Flight readById(int id) {
        return flightRepository.getOne(id);
    }

    @Override
    public Flight update(Flight flight) {
        return flightRepository.save(flight);
    }

    @Override
    public void delete(int id) {
        flightRepository.deleteById(id);
    }

    @Override
    public List<Flight> getAll() {
        return flightRepository.findAll();
    }

    @Override
    public List<Flight> getAllByDepartureAndAndArrival(String departure, String arrival) {
        return flightRepository.findAllByDepartureAndAndArrival(departure, arrival);
    }
}
