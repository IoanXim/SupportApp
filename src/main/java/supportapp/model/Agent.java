package supportapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"user", "products"})
@ToString(exclude = {"user", "products"})
@Table(name = "AGENTS")
public class Agent {
    @Id
    @Column(name = "AG_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long agId;

    @Column(name = "FIRSTNAME")
    private String firstname;

    @Column(name = "LASTNAME")
    private String lastname;

    @Column(name = "SSN")
    private String ssn;

    @Column(name = "IS_AVAILABLE")
    private Boolean isAvailable = true;

    @OneToOne
    @JoinColumn(name = "USER_ID", unique = true)
    private User user;

    @ManyToMany
    @JoinTable(
            name = "PRODUCTS_AGENTS",
            joinColumns = @JoinColumn(name = "AG_ID"),
            inverseJoinColumns = @JoinColumn(name = "PROD_ID")
    )
    private Set<Product> products = new HashSet<>();
}
