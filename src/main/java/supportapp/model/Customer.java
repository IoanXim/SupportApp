package supportapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CUSTOMERS")
public class Customer {
    @Id
    @Column(name = "CUST_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long custId;

    @Column(name = "FIRSTNAME")
    private String firstname;

    @Column(name = "LASTNAME")
    private String lastname;

    @Column(name = "GENDER")
    private Character gender;

    @Column(name = "BIRTHDAY")
    private LocalDate birthday;

    @OneToOne
    @JoinColumn(name = "USER_ID", unique = true)
    private User user;
}
