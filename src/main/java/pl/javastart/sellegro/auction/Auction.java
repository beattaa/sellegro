package pl.javastart.sellegro.auction;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String carMake;
    private String carModel;
    private String color;
    private BigDecimal price;
    private LocalDate endDate;

    public Auction(Long id, String title, String carMake, String carModel, String color, BigDecimal price, LocalDate endDate) {
        this.id = id;
        this.title = title;
        this.carMake = carMake;
        this.carModel = carModel;
        this.color = color;
        this.price = price;
        this.endDate = endDate;
    }
}
