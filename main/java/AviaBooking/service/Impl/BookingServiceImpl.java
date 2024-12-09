package AviaBooking.service.Impl;

import AviaBooking.exception.EntityErrorException;
import AviaBooking.model.Booking;
import AviaBooking.model.User;
import AviaBooking.repository.BookingRepository;
import AviaBooking.service.BookingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Booking create(Booking booking) {
            return bookingRepository.save(booking);
    }

    @Override
    public Booking readById(int id) {
        return bookingRepository.getOne(id);
    }

    @Override
    public Booking update(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public void delete(int id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public List<Booking> getAll() {
        return bookingRepository.findAll();
    }

    @Override
    public List<Booking> getByUser(User user) {
        return bookingRepository.getBookingsByUser(user);
    }
}
