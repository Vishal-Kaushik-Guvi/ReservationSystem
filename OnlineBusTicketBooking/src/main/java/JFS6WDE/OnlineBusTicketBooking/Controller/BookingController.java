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
import JFS6WDE.OnlineBusTicketBooking.Entities.BookingHistory;
import JFS6WDE.OnlineBusTicketBooking.Services.BookingServiceImpl;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingServiceImpl bookingService;

    // Display all booking history entries
    @GetMapping("/viewBookingHistory")
    public String getAllBookings(Model model) {
        List<BookingHistory> bookingHistoryList = bookingService.getAllBooking();
        model.addAttribute("bookingHistoryList", bookingHistoryList);
        return "bookinghistory"; // Thymeleaf template name
    }

    // Delete booking history entry
    @PostMapping("/deleteBooking/{id}")
    public String deleteBooking(@PathVariable long id) {
        bookingService.deleteBookingById(id);
        return "redirect:/bookings/viewBookingHistory"; // Redirect to the booking history list page
    }

    // Find booking by id
    @GetMapping("/viewBooking")
    public String findBookingById(@RequestParam long id, Model model) {
        BookingHistory booking = bookingService.getBookingById(id);
        model.addAttribute("booking", booking);
        return "viewbooking"; // Adjusted template name
    }

    // Show add booking form
    @GetMapping("/addBooking")
    public String showAddBookingForm(@RequestParam long busId, Model model) {
        model.addAttribute("busId", busId);
        model.addAttribute("bookingDto", new BookingDto());
        return "addbooking"; // Thymeleaf template name for adding booking
    }

    // Process add booking form
    @PostMapping("/addBooking")
    public String addBooking(
            @RequestParam long busId,
            @Valid @ModelAttribute BookingDto bookingDto, 
            BindingResult bindingResult,
            @AuthenticationPrincipal UserDetails currentUser,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("bookingDto", bookingDto);
            model.addAttribute("busId", busId);
            return "addbooking"; // Return to form with validation errors
        }

        String email = currentUser.getUsername();
        bookingService.saveBooking(bookingDto, email, busId);
        return "redirect:/bookings/viewBookingHistory"; // Redirect to the booking history list page
    }
}
