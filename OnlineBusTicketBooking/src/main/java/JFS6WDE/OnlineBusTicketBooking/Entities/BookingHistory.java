package JFS6WDE.OnlineBusTicketBooking.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "bus_id")
    private Bus bus;

    private String routeFrom;
    private String routeTo;
    private Integer distance;
    private Integer fare;

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