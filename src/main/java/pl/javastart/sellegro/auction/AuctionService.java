package pl.javastart.sellegro.auction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class AuctionService {
    private final AuctionRepository auctionRepository;
    private static final String[] ADJECTIVES = {"Niesamowity", "Jedyny taki", "IGŁA", "HIT", "Jak nowy",
            "Perełka", "OKAZJA", "Wyjątkowy"};
    List<Auction> auctions = new ArrayList<>();

    @Autowired
    public AuctionService(AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
        updateTitles();
    }

    private void updateTitles() {
        List<Auction> all = auctionRepository.findAll();
        for (Auction auction: all){
            Random random = new Random();
            String adjective = ADJECTIVES[random.nextInt(ADJECTIVES.length)];
            String title = String.format("%s %s $s", adjective, auction.getCarMake(), auction.getCarModel());
            auction.setTitle(title);
            auctionRepository.save(auction);
        }
    }

    public List<Auction> find4MostExpensive() {
        return auctionRepository.findTop4ByOrderByPriceDesc();
    }

    public List<Auction> findAllForFiltersWithSort(AuctionFilters auctionFilters, String sortBy) {
        Sort by = Sort.by(sortBy);
        return auctionRepository.findAllByTitleContainsIgnoreCaseAndCarMakeContainsIgnoreCaseAndCarModelContainsIgnoreCaseAndColorContainsIgnoreCase(auctionFilters.getTitle(), auctionFilters.getCarMaker(), auctionFilters.getCarModel(), auctionFilters.getColor(), by);
    }

    public List<Auction> findAllForFiltersWithoutSort(AuctionFilters auctionFilters) {
        return auctionRepository.findAllByTitleContainsIgnoreCaseAndCarMakeContainsIgnoreCaseAndCarModelContainsIgnoreCaseAndColorContainsIgnoreCase(auctionFilters.getTitle(), auctionFilters.getCarMaker(), auctionFilters.getCarModel(), auctionFilters.getColor());
    }

    public Page<Auction> findPaginated(Pageable pageable, List <Auction> auctions) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Auction> list;

        if (auctions.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, auctions.size());
            list = auctions.subList(startItem, toIndex);
        }

        Page<Auction> auctionPage = new PageImpl<Auction>(list, PageRequest.of(currentPage, pageSize), auctions.size());

        return auctionPage;
    }
}