package com.inditex.demo.adapter.outgoing.persistence;

import com.inditex.demo.core.domain.entity.Price;
import com.inditex.demo.core.port.outcoming.PricePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class PriceRepositoryAdapter implements PricePort {

    private final PriceRepository priceRepository;

    @Autowired
    public PriceRepositoryAdapter(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public List<Price> findPrices(Long productId, Integer brandId) {
        return priceRepository.findPrices(productId, brandId);
    }
}