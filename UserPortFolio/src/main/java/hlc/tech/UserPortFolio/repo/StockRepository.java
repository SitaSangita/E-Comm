package hlc.tech.UserPortFolio.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hlc.tech.UserPortFolio.entity.Stock;

public interface StockRepository extends JpaRepository<Stock, Long> {
    List<Stock> findByUserId(Long userId);
    Optional<Stock> findByStockCodeAndUserId(String code, Long userId);
    void deleteByUserId(Long userId);
}
