package AviaBooking.service.Impl;

import AviaBooking.model.Plane;
import AviaBooking.model.Seats;
import AviaBooking.repository.SeatsRepository;
import AviaBooking.service.SeatsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatsServiceImpl implements SeatsService {
    private final SeatsRepository seatsRepository;

    public SeatsServiceImpl(SeatsRepository seatsRepository) {
        this.seatsRepository = seatsRepository;
    }

    @Override
    public Seats create(Seats seats) {
        return seatsRepository.save(seats);
    }

    @Override
    public Seats readById(int id) {
        return seatsRepository.getOne(id);
    }

    @Override
    public Seats update(Seats seats) {
        return seatsRepository.save(seats);
    }

    @Override
    public void delete(int id) {
        seatsRepository.deleteById(id);
    }

    @Override
    public List<Seats> getAll() {
        return seatsRepository.findAll();
    }

    @Override
    public List<Seats> getAllByPlane(Plane plane) {
        return seatsRepository.findByPlane(plane);
    }

    @Override
    public Seats getByNumber(String number) {
        return seatsRepository.findByNumber(number);
    }
}
