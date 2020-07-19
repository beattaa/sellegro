package pl.javastart.sellegro.auction;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuctionFilters {

    private String title;
    private String carMaker;
    private String carModel;
    private String color;
}
