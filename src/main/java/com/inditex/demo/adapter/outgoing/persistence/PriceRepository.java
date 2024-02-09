package com.inditex.demo.adapter.outgoing.persistence;

import com.inditex.demo.core.domain.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {


    @Query("SELECT p FROM Price p WHERE p.productId = :productId AND p.brandId = :brandId")
    List<Price> findPrices(
            @Param("productId") Long productId,
            @Param("brandId") Integer brandId);
}

