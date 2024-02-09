package com.inditex.demo.adapter.outgoing.persistence;

import com.inditex.demo.core.domain.entity.Price;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PriceRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PriceRepository priceRepository;

    @Test
    void findPrices_ReturnsPrice() {
        List<Price> foundPrices = priceRepository.findPrices(1L, 1);

        assertThat(foundPrices).isNotNull();
    }
}
