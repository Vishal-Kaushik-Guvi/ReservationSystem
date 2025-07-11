package JFS6WDE.OnlineBusTicketBooking.Dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {
    private Long id;

    @Max(20)
    private int bookseat;

    @NotBlank(message = "Card number cannot be empty")
    private String cardNumber;

    private String upiId;

    private LocalDate bookingDate;
    private LocalTime bookingTime;

    @NotBlank(message = "Mobile number is required")
    private String mobileNumber;

    // New: List of passenger names (same size as number of seats)
    @NotNull(message = "Passenger names are required")
    private List<String> passengerNames;

    // New: Corresponding ages
    @NotNull(message = "Passenger ages are required")
    private List<Integer> passengerAges;
}
