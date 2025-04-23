package hcl.tech.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hcl.tech.entity.StockEntry;

public interface StockEntryRepository extends JpaRepository<StockEntry, Long> {
    List<StockEntry> findByUserEmail(String email);
    List<StockEntry> findByUserEmailAndAddedAtBetween(String email, LocalDateTime start, LocalDateTime end);

}
