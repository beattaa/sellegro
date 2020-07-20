package pl.javastart.sellegro.auction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.awt.print.Book;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
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

    public AuctionService(AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
        try {
            loadData();
        } catch (IOException e) {
            System.out.println("Error loading data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadData() throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("dane.csv");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        Random random = new Random();
        String line = bufferedReader.readLine(); // skip first line
        while ((line = bufferedReader.readLine()) != null) {
            String[] data = line.split(",");
            long id = Long.parseLong(data[0]);
            String randomAdjective = ADJECTIVES[random.nextInt(ADJECTIVES.length)];
            String title = randomAdjective + " " + data[1] + " " + data[2];
            BigDecimal price = new BigDecimal(data[4].replace("\\.", ","));
            LocalDate endDate = LocalDate.parse(data[5]);
            Auction auction = new Auction(id, title, data[1], data[2], data[3], price, endDate);
            auctions.add(auction);
            auctionRepository.save(auction);
        }
    }

    public List<Auction> find4MostExpensive() {
        return auctionRepository.findTop4ByOrderByPriceDesc();
    }

    public List<Auction> findAllForFilters(AuctionFilters auctionFilters, String sort) {
        if (auctionFilters==null){
           return findAllSorted(sort);
        }
        else if (!StringUtils.isEmpty(auctionFilters.getTitle())) {
            return auctionRepository.findAllByTitleContainingIgnoreCase(auctionFilters.getTitle(), sort);
        } else if (!StringUtils.isEmpty(auctionFilters.getCarMaker())) {
            return auctionRepository.findAllByCarMakeContainingIgnoreCase(auctionFilters.getCarMaker(), sort);
        } else if (!StringUtils.isEmpty(auctionFilters.getCarModel())) {
            return auctionRepository.findAllByCarModelContainingIgnoreCase(auctionFilters.getCarModel(), sort);
        } else if (!StringUtils.isEmpty(auctionFilters.getColor())) {
            return auctionRepository.findAllByColorContainingIgnoreCase(auctionFilters.getColor(), sort);
        } else {
            return auctionRepository.findAllBy();
        }
    }

    public List<Auction> findAllSorted(String sort) {
        if (sort.equals("title")) {
            return auctionRepository.findAllByOrderByTitle();
        } else if (sort.equals("price")) {
            return auctionRepository.findAllByOrderByPriceAsc();
        } else if (sort.equals("color")) {
            return auctionRepository.findAllByOrderByColor();
        } else {
            return auctionRepository.findAllByOrderByEndDate();
        }
    }

    public Page<Auction> findPaginated(Pageable pageable) {
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