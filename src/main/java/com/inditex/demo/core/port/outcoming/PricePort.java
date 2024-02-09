package com.inditex.demo.core.port.outcoming;

import com.inditex.demo.core.domain.entity.Price;
import java.time.LocalDateTime;
import java.util.List;

public interface PricePort {
    List<Price> findPrices(Long productId, Integer brandId);
}