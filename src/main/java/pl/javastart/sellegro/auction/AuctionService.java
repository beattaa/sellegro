package pl.javastart.sellegro.auction;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
public class AuctionService {
    private final AuctionRepository auctionRepository;
    private static final String[] ADJECTIVES = {"Niesamowity", "Jedyny taki", "IGŁA", "HIT", "Jak nowy",
            "Perełka", "OKAZJA", "Wyjątkowy"};

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
            auctionRepository.save(auction);
        }
    }

    public List<Auction> find4MostExpensive() {
        return auctionRepository.findTop4ByOrderByPriceDesc();
    }

    public List<Auction> findAllForFilters(AuctionFilters auctionFilters) {
        return auctionRepository.findAuctionByMultipleParams(auctionFilters.getTitle(), auctionFilters.getCarMaker(), auctionFilters.getCarModel(), auctionFilters.getColor());
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
}