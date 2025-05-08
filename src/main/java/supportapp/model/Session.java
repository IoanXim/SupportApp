package supportapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SESSIONS")
public class Session {
    @Id
    @Column(name = "SESSION_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sessionId;

    @ManyToOne
    @JoinColumn(name = "CUST_ID")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "AG_ID")
    private Agent agent;

    @ManyToOne
    @JoinColumn(name = "PROD_ID")
    private Product product;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL)
    private Set<Message> messages = new HashSet<>();

    @Column(name = "STATUS")
    private String status;

    @Column(name = "CREATED_AT")
    private LocalDateTime startedAt;

    @Column(name = "ENDED_AT")
    private LocalDateTime endedAt;

}
