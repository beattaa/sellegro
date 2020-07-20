package pl.javastart.sellegro.auction;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {

    @Query("SELECT a FROM Auction a WHERE (:title is null or a.title = :title) and (:carMake is null or a.carMake = :carMake) and (:carModel is null or a.carModel = :carModel) and (:color is null or a.color = :color)")
    List<Auction> findAuctionByMultipleParams(@Param("title") String title, @Param("carMake") String carMake, @Param("carModel") String carModel, @Param("color") String color);


//    List<Auction> findAllByCarMakeContainingIgnoreCaseAnd(String like);
//
//    List<Auction> findAllByCarModelContainingIgnoreCase(String like);
//
//    List<Auction> findAllByColorContainingIgnoreCase(String like);

    List<Auction> findAllBy();

    List<Auction> findAllByOrderByTitle();

    List<Auction> findAllByOrderByPriceAsc();

    List<Auction> findAllByOrderByColor();

    List<Auction> findAllByOrderByEndDate();

    List<Auction> findTop4ByOrderByPriceDesc();
}
