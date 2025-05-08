package supportapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MESSAGES")
public class Message {
    @Id
    @Column(name = "MSG_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    @ManyToOne
    @JoinColumn(name = "SESSION_ID")
    private Session session;

    @ManyToOne
    @JoinColumn(name = "SENDER_ID")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "RECIPIENT_ID")
    private User recipient;

    @Column(name = "MESSAGE_TEXT")
    private String content;

    @Column(name = "SENT_AT")
    private LocalDateTime sentAt;
}
