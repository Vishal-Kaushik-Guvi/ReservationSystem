package JFS6WDE.OnlineBusTicketBooking.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cardNumber;
    private String upiId;
    private String paymentStatus; // "PAID", "FAILED", etc.

    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;
}
