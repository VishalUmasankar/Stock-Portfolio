package com.stockportfolio.repository;
import com.stockportfolio.entity.Holding;
import java.util.List;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoldingRepository extends JpaRepository<Holding, Long> {
    Optional<Holding> findByUserDetails_IdAndStockSymbol(Long userId, String stockSymbol);
    List<Holding> findAllByUserDetails_Id(Long userId);
    List<Holding> findByAlert(String alert);
}


