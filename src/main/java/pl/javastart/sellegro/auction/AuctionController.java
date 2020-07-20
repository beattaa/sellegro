package pl.javastart.sellegro.auction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class AuctionController {

    private AuctionService auctionService;

    @Autowired
    public AuctionController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @GetMapping("/auctions")
    public String auctions(Model model,
                           @RequestParam(required = false) String sort,
                           AuctionFilters auctionFilters,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size) {
        List<Auction> auctions;
        if (sort != null) {
            auctions = auctionService.findAllSorted(sort);
        } else {
            auctions = auctionService.findAllForFilters(auctionFilters, sort);
        }

        model.addAttribute("cars", auctions);
        model.addAttribute("filters", auctionFilters);

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(50);

        Page<Auction> auctionPage = auctionService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("auctionPage", auctionPage);

        int totalPages = auctionPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "auctions";
    }
}
