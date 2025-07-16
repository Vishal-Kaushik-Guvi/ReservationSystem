package JFS6WDE.OnlineBusTicketBooking.Controller;

import JFS6WDE.OnlineBusTicketBooking.Dto.PaymentDto;
import JFS6WDE.OnlineBusTicketBooking.Entities.Booking;
import JFS6WDE.OnlineBusTicketBooking.Entities.Payment;
import JFS6WDE.OnlineBusTicketBooking.Services.BookingServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bookings")
public class PaymentController {

    @Autowired
    private BookingServiceImpl bookingService;

    @GetMapping("/payment")
    public String showPaymentForm(@RequestParam("bookingId") Long bookingId, Model model) {
        Booking booking = bookingService.getBookingById(bookingId);
        model.addAttribute("booking", booking);
        model.addAttribute("paymentDto", new PaymentDto());
        return "payment";
    }

    @PostMapping("/payment")
    public String processPayment(@RequestParam("bookingId") Long bookingId,
                                 @ModelAttribute PaymentDto paymentDto,
                                 Model model) {
        Booking booking = bookingService.getBookingById(bookingId);
        if (booking == null) {
            model.addAttribute("error", "Booking not found.");
            return "payment";
        }

        Payment payment = new Payment();
        payment.setCardNumber(paymentDto.getCardNumber());
        payment.setUpiId(paymentDto.getUpiId());
        payment.setPaymentStatus("PAID");
        payment.setBooking(booking);

        booking.setPayment(payment); // Maintain bi-directional consistency
        bookingService.updateBookingWithPayment(booking); // should cascade to save Payment

        model.addAttribute("booking", booking);
        return "paymentconfirmation";
    }
    
}
