package JFS6WDE.OnlineBusTicketBooking.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import JFS6WDE.OnlineBusTicketBooking.Entities.Booking;
import JFS6WDE.OnlineBusTicketBooking.Entities.Bus;
import JFS6WDE.OnlineBusTicketBooking.Entities.User;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUserAndBus(User user, Bus bus);

    List<Booking> findByUser(User user);

    List<Booking> findByBus(Bus bus);

    @Query("SELECT SUM(b.bookseat) FROM Booking b WHERE b.bus = :bus")
    Integer countBookedSeatsByBus(@Param("bus") Bus bus);
}
