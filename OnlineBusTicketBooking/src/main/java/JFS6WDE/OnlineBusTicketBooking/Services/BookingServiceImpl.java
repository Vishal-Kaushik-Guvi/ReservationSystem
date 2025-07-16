package JFS6WDE.OnlineBusTicketBooking.Services;

import java.time.LocalDate;

import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JFS6WDE.OnlineBusTicketBooking.Dto.BookingDto;
import JFS6WDE.OnlineBusTicketBooking.Entities.Booking;
import JFS6WDE.OnlineBusTicketBooking.Entities.Bus;
import JFS6WDE.OnlineBusTicketBooking.Entities.User;
import JFS6WDE.OnlineBusTicketBooking.Repository.BookingRepository;
import JFS6WDE.OnlineBusTicketBooking.Repository.BusRepository;
import JFS6WDE.OnlineBusTicketBooking.Repository.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BusRepository busRepository;

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public void deleteBookingById(long id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public Booking getBookingById(long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + id));
    }

    @Override
    @Transactional
    public Long saveBooking(BookingDto bookingDto, String userEmail, long busId) {
        User user = userRepository.findByEmail(userEmail);
        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new RuntimeException("Bus not found with ID: " + busId));

        int availableSeats = bus.getSeats() - bus.getBookings().stream()
                .mapToInt(Booking::getBookseat)
                .sum();

        if (bookingDto.getBookseat() > availableSeats) {
            throw new RuntimeException("Not enough seats available. Requested: " +
                    bookingDto.getBookseat() + ", Available: " + availableSeats);
        }

        if (bookingDto.getPassengerNames().size() != bookingDto.getBookseat()
                || bookingDto.getPassengerAges().size() != bookingDto.getBookseat()) {
            throw new RuntimeException("Passenger details do not match number of seats.");
        }

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setBus(bus);
        booking.setRouteFrom(bus.getRouteFrom());
        booking.setRouteTo(bus.getRouteTo());
        booking.setDistance(bus.getDistance());
        booking.setFare(bus.getFare() * bookingDto.getBookseat());
        booking.setBookseat(bookingDto.getBookseat());
        booking.setBookingDate(LocalDate.now());
        booking.setBookingTime(LocalTime.now());
        booking.setMobileNumber(bookingDto.getMobileNumber());
        booking.setPassengerNames(bookingDto.getPassengerNames());
        booking.setPassengerAges(bookingDto.getPassengerAges());

        bookingRepository.save(booking);
        return booking.getId();
    }
    
    @Override
    public void updateBookingWithPayment(Booking booking) {
        bookingRepository.save(booking); // Cascade will save the Payment
    }
    
    // ✅ Cancel a booking and mark payment as "CANCELLED"
    @Override
    public void cancelBooking(Long bookingId) {
        Booking booking = getBookingById(bookingId);
        if (booking != null && booking.getPayment() != null) {
            booking.getPayment().setPaymentStatus("CANCELLED");
            bookingRepository.save(booking); // cascade saves payment
        }
    }

    // ✅ Save/Update a modified booking
    @Override
    public void saveUpdatedBooking(Booking booking) {
        bookingRepository.save(booking);
    }

    @Override
    public List<Booking> getPaidBookingsByUserEmail(String email) {
        User user = userRepository.findByEmail(email);
        List<Booking> all = bookingRepository.findByUser(user);
        return all.stream()
                  .filter(b -> b.getPayment() != null)
                  .toList(); // You can also filter by status if needed
    }

}
