package pl.javastart.sellegro.auction;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {
    List<Auction> findTop4ByOrderByPriceDesc();
    List<Auction> findAllByOrderByCarMakeAscCarModelAsc();
    List<Auction> findAllByTitleContainsIgnoreCaseAndCarMakeContainsIgnoreCaseAndCarModelContainsIgnoreCaseAndColorContainsIgnoreCase(String title, String carMake, String carModel, String color, Sort sort);
    List<Auction> findAllByTitleContainsIgnoreCaseAndCarMakeContainsIgnoreCaseAndCarModelContainsIgnoreCaseAndColorContainsIgnoreCase(String title, String carMake, String carModel, String color);


}
