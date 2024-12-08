package by.kiok.motorshow.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

@Entity
@Getter
@Setter
@Indexed
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reviews")
@Accessors(chain = true)
@ToString(exclude = {"client", "cars", "clients"})
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @FullTextField(analyzer = "standard")
    private String reviewText;

    @GenericField
    private int rank;

    @ManyToOne
    private Client client;

    @ManyToOne
    @JoinColumn(name = "cars_id")
    private Car cars;

    @ManyToOne
    @JoinColumn(name = "clients_id")
    private Client clients;
}
