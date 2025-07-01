package JFS6WDE.OnlineBusTicketBooking.Services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JFS6WDE.OnlineBusTicketBooking.Dto.BookingDto;
import JFS6WDE.OnlineBusTicketBooking.Entities.BookingHistory;
import JFS6WDE.OnlineBusTicketBooking.Entities.Bus;
import JFS6WDE.OnlineBusTicketBooking.Entities.User;
import JFS6WDE.OnlineBusTicketBooking.Exception.ResourceNotFound;
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
    public List<BookingHistory> getAllBooking() {
        return bookingRepository.findAll();
    }

    @Override
    public void deleteBookingById(long id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public BookingHistory getBookingById(long id) {
        return bookingRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Booking not found with id: " + id));
    }

    @Override
    @Transactional
    public void saveBooking(BookingDto bookingDto, String userEmail, long id) {
        User user = userRepository.findByEmail(userEmail);
    
        Bus bus = busRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bus not found with ID: " + bookingDto.getId()));
    
        // Check if the requested number of seats is available
        int availableSeats = bus.getSeats() - bus.getBookingHistoryList().stream()
                .mapToInt(BookingHistory::getBookseat)
                .sum();
    
        if (bookingDto.getBookseat() > availableSeats) {
            throw new RuntimeException("Not enough seats available. Requested: " + bookingDto.getBookseat() + ", Available: " + availableSeats);
        }

        List<BookingHistory> existingBookings = bookingRepository.findByUserAndBus(user, bus);
        if (!existingBookings.isEmpty()) {
            throw new IllegalArgumentException("Booking already exists for this user and bus.");
        }
    
        BookingHistory bookingHistory = new BookingHistory();
        
        bookingHistory.setUser(user);
        bookingHistory.setBus(bus);
        bookingHistory.setRouteFrom(bus.getRouteFrom());
        bookingHistory.setRouteTo(bus.getRouteTo());
        bookingHistory.setDistance(bus.getDistance());
        bookingHistory.setFare(bus.getFare()*bookingDto.getBookseat());

        bookingHistory.setBookseat(bookingDto.getBookseat());
        bookingHistory.setCardNumber(bookingDto.getCardNumber());
        bookingHistory.setUpiId(bookingDto.getUpiId());

        bookingHistory.setBookingDate(LocalDate.now());
        bookingHistory.setBookingTime(LocalTime.now());
    
        bookingRepository.save(bookingHistory);
    }
}
