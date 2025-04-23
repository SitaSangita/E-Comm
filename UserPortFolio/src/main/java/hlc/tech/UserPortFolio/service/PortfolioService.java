package hlc.tech.UserPortFolio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hlc.tech.UserPortFolio.entity.Stock;
import hlc.tech.UserPortFolio.entity.User;
import hlc.tech.UserPortFolio.repo.StockRepository;
import hlc.tech.UserPortFolio.repo.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PortfolioService {
	@Autowired
    private final StockRepository stockRepo;
	@Autowired
    private final UserRepository userRepo;

    public String addStock(Long userId, String code, String name, double price, String range, double change) {
        User user = userRepo.findById(userId).orElseThrow();
        Stock stock = new Stock();
        stock.setStockCode(code);
        stock.setStockName(name);
        stock.setPrice(price);
        stock.setRange(range);
        stock.setPercentageChange(change);
        stock.setUser(user);
        stockRepo.save(stock);
        return "Hi '" + user.getName() + "', '" + name + "' stock added to your user portfolio.";
    }

    public String deleteStock(Long userId, String code) {
        Stock stock = stockRepo.findByStockCodeAndUserId(code, userId).orElseThrow();
        stockRepo.delete(stock);
        return "Hi '" + stock.getUser().getName() + "', '" + stock.getStockName() + "' stock is removed from your user portfolio.";
    }
    public String deleteAllStocks(Long userId) {
        List<Stock> stocks = stockRepo.findByUserId(userId);
        List<String> names = stocks.stream().map(Stock::getStockName).toList();
        stockRepo.deleteAll(stocks);
        return "Hi 'User', '" + String.join(", ", names) + "' stocks removed from your user portfolio. And your portfolio is empty.";
    }
}
