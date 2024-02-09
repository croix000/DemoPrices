package com.inditex.demo.core.domain.service;

import com.inditex.demo.core.domain.entity.Price;
import com.inditex.demo.core.exception.PriceNotFoundException;
import com.inditex.demo.core.port.outcoming.PricePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PriceService {

    private final PricePort pricePort;

    @Autowired
    public PriceService(PricePort pricePort) {
        this.pricePort = pricePort;
    }

    public Price calculatePrice(LocalDateTime date, Long productId, Integer brandId) {

        List<Price> prices = pricePort.findPrices(productId, brandId);
        Price applicablePrice = null;

        for (Price price : prices) {
            if (!date.isBefore(price.getStartDate()) && !date.isAfter(price.getEndDate())) {
                if (applicablePrice == null || price.getPriority() > applicablePrice.getPriority()) {
                    applicablePrice = price;
                }
            }
        }

        if (applicablePrice == null) {
            throw new PriceNotFoundException("No applicable price found for the given date and product/brand IDs.");
        }

        return applicablePrice;
    }
}