package AviaBooking.service.Impl;

import AviaBooking.exception.EntityErrorException;
import AviaBooking.model.Plane;
import AviaBooking.repository.PlaneRepository;
import AviaBooking.service.PlaneService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaneServiceImpl implements PlaneService {
    private final PlaneRepository planeRepository;

    public PlaneServiceImpl(PlaneRepository planeRepository) {
        this.planeRepository = planeRepository;
    }

    @Override
    public Plane create(Plane plane) {
        try {
            return planeRepository.save(plane);
        }catch (Exception e){
            throw new EntityErrorException("Plane with this number already exists");
        }
    }

    @Override
    public Plane readById(int id) {
        return planeRepository.getOne(id);
    }

    @Override
    public Plane update(Plane plane) {
        try {
            return planeRepository.save(plane);
        }catch (Exception e){
            throw new EntityErrorException("Plane with this number already exists");
        }
    }

    @Override
    public void delete(int id) {
        planeRepository.deleteById(id);
    }

    @Override
    public List<Plane> getAll() {
        return planeRepository.findAll();
    }
}
