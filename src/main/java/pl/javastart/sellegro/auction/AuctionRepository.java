package pl.javastart.sellegro.auction;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {

    @Query("SELECT a FROM Auction a " +
            "WHERE a.title LIKE CONCAT(:starting, '%') " +
            "ORDER BY :order")
    List<Auction> findAllByTitleContainingIgnoreCase(@Param("starting") String starting, @Param("order") String order);

    @Query("SELECT a FROM Auction a " +
            "WHERE a.carMake LIKE CONCAT(:starting, '%') " +
            "ORDER BY :order")
    List<Auction> findAllByCarMakeContainingIgnoreCase(@Param("starting") String starting, @Param("order") String order);

    @Query("SELECT a FROM Auction a " +
            "WHERE a.carModel LIKE CONCAT(:starting, '%') " +
            "ORDER BY :order")
    List<Auction> findAllByCarModelContainingIgnoreCase(@Param("starting") String starting, @Param("order") String order);


    @Query("SELECT a FROM Auction a " +
            "WHERE a.color LIKE CONCAT(:starting, '%') " +
            "ORDER BY :order")
    List<Auction> findAllByColorContainingIgnoreCase(@Param("starting") String starting, @Param("order") String order);





    //
//    List<Auction> findAllByTitleContainingIgnoreCase(String like);
//
//    List<Auction> findAllByCarMakeContainingIgnoreCase(String like);
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
