package supportapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"agents"})
@ToString(exclude = {"agents"})
@Table(name = "PRODUCTS")
public class Product {
    @Id
    @Column(name = "PROD_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prodId;

    @Column(name = "NAME")
    private String name;

    @ManyToMany(mappedBy = "products")
    private Set<Agent> agents = new HashSet<>();
}
