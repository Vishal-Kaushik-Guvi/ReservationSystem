package JFS6WDE.OnlineBusTicketBooking.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import JFS6WDE.OnlineBusTicketBooking.Dto.BookingDto;
import JFS6WDE.OnlineBusTicketBooking.Entities.Booking;
import JFS6WDE.OnlineBusTicketBooking.Entities.Bus;
import JFS6WDE.OnlineBusTicketBooking.Services.BookingService;
import JFS6WDE.OnlineBusTicketBooking.Services.BusService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BusService busService;

    // View all bookings
    @GetMapping("/viewBookingHistory")
    public String getAllBookings(Model model) {
        List<Booking> bookings = bookingService.getAllBookings();
        model.addAttribute("bookings", bookings);
        return "bookinghistory";
    }

    // Delete a booking by ID
    @PostMapping("/deleteBooking/{id}")
    public String deleteBooking(@PathVariable long id) {
        bookingService.deleteBookingById(id);
        return "redirect:/bookings/viewBookingHistory";
    }

    // View a specific booking
    @GetMapping("/viewBooking")
    public String findBookingById(@RequestParam long id, Model model) {
        Booking booking = bookingService.getBookingById(id);
        model.addAttribute("booking", booking);
        return "viewbooking";
    }

    // Show form to book a specific bus
    @GetMapping("/addBooking")
    public String showAddBookingForm(@RequestParam long busId, Model model) {
        Bus bus = busService.getBusById(busId);
        model.addAttribute("bus", bus);
        model.addAttribute("bookingDto", new BookingDto());
        return "addbooking";
    }

    // Submit booking
    @PostMapping("/addBooking")
    public String addBooking(
            @RequestParam long busId,
            @Valid @ModelAttribute BookingDto bookingDto,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserDetails currentUser,
            Model model) {

        if (bindingResult.hasErrors()) {
            Bus bus = busService.getBusById(busId);
            model.addAttribute("bus", bus);
            model.addAttribute("bookingDto", bookingDto);
            return "addbooking";
        }

        String email = currentUser.getUsername();
        Long bookingId = bookingService.saveBooking(bookingDto, email, busId);
        return "redirect:/bookings/payment?bookingId=" + bookingId;
    }
}
