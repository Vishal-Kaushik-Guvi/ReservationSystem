package JFS6WDE.OnlineBusTicketBooking.Dto;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;

import jakarta.validation.constraints.Max;
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
 
    @NotNull(message = "Card number cannot be null or empty")
    @Column(name = "Bank_Card")
    private String cardNumber;
    
    @Column(name = "Bank_Upi")
    private String upiId;

    private LocalDate bookingDate;
    private LocalTime bookingTime;

}
