package by.kiok.motorshow.models;

import by.kiok.motorshow.models.enums.CarBrand;
import by.kiok.motorshow.models.enums.CarBrandConverter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cars")
@Accessors(chain = true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String model;

    @Convert(converter = CarBrandConverter.class)
    private CarBrand brandCar;

    private LocalDate yearOfProduction;

    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "car_showroom_id")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private CarShowroom showroom;

    @OneToMany(mappedBy = "cars", cascade = CascadeType.ALL, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<Review> review = new ArrayList<>();

    @ManyToMany(mappedBy = "cars", cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE})
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<Client> clients = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "category_id")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Category categories;
}
