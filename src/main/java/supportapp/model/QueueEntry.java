package supportapp.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "QUEUE")
public class QueueEntry {

    @Id
    @Column(name = "QUEUE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CUST_ID")
    private Customer customer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "PROD_ID")
    private Product product;

    @Column(name = "QUEUED_AT")
    private LocalDateTime queuedAt;
}
