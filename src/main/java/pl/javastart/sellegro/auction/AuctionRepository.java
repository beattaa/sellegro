package pl.javastart.sellegro.auction;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {
    List<Auction> findAllByTitleContainingIgnoreCase(String like);

    List<Auction> findAllByCarMakeContainingIgnoreCase(String like);

    List<Auction> findAllByCarModelContainingIgnoreCase(String like);

    List<Auction> findAllByColorContainingIgnoreCase(String like);

    List<Auction> findAllBy();

    List<Auction> findAllByOrderByTitle();

    List<Auction> findAllByOrderByPriceAsc();

    List<Auction> findAllByOrderByColor();

    List<Auction> findAllByOrderByEndDate();

    List<Auction> findTop4ByOrderByPriceDesc();
}
