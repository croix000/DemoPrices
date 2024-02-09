package com.inditex.demo.core.domain.service;

import com.inditex.demo.core.domain.entity.Price;
import com.inditex.demo.core.exception.PriceNotFoundException;
import com.inditex.demo.core.port.outcoming.PricePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PriceServiceTest {

    @Mock
    private PricePort pricePort;

    @InjectMocks
    private PriceService priceService;

    private final List<Price> prices = new ArrayList<>();
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Price expectedPrice = new Price(1,
                LocalDateTime.of(2020, 6, 14, 0, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59, 59),1,1L,1,
                35.50f,"EUR");
        prices.add(expectedPrice);
        expectedPrice = new Price(1,
                LocalDateTime.of(2020, 6, 14, 15, 0),
                LocalDateTime.of(2020, 6, 14, 18, 30, 0),2,1L,1,
                25.45f,"EUR");
        prices.add(expectedPrice);
        expectedPrice = new Price(1,
                LocalDateTime.of(2020, 6, 15, 0, 0),
                LocalDateTime.of(2020, 6, 15, 11, 0, 0),3,1L,1,
                30.50f,"EUR");
        prices.add(expectedPrice);
        expectedPrice = new Price(1,
                LocalDateTime.of(2020, 6, 15, 16, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59, 59),4,1L,1,
                38.95f,"EUR");
        prices.add(expectedPrice);

    }

    @Test
    void calculatePrice_ReturnsCorrectPrice() {
        // Arrange
        LocalDateTime testDate = LocalDateTime.of(2020, 6, 14, 11, 0);
        Price expectedPrice = new Price(1,  LocalDateTime.of(2020, 6, 14, 10, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59, 59),1,1L,1,
                35.50f,"EUR");

        when(pricePort.findPrices(any(Long.class), any(Integer.class))).thenReturn(prices);

        // Act
        Price result = priceService.calculatePrice(testDate, 1L, 1);

        // Assert
        assertNotNull(result);
        assertEquals(expectedPrice, result);
    }

    @Test
    void calculatePrice_ThrowsPriceNotFoundException() {
        // Arrange
        when(pricePort.findPrices(any(Long.class), any(Integer.class))).thenReturn(List.of());

        // Act & Assert
        assertThrows(PriceNotFoundException.class, () -> priceService.calculatePrice(LocalDateTime.of(2024, 2, 10, 10, 0), 1L, 1));
    }


    @Test
    void calculatePrice_At1000Day14() {
        // Arrange
        LocalDateTime testDate = LocalDateTime.of(2020, 6, 14, 10, 0);
        long productId = 35455L; // Asumiendo que este es el ID del producto para el ejemplo
        int brandId = 1; // ZARA

        // Act
        Price result = priceService.calculatePrice(testDate, productId, brandId);

        // Assert
        assertNotNull(result);
        assertEquals(35.50f, result.getPrice());
    }

    @Test
    void calculatePrice_At1600Day14() {
        // Arrange
        LocalDateTime testDate = LocalDateTime.of(2020, 6, 14, 16, 0);
        long productId = 35455L;
        int brandId = 1; // ZARA

        // Act
        Price result = priceService.calculatePrice(testDate, productId, brandId);

        // Assert
        assertNotNull(result);
        assertEquals(25.45f, result.getPrice());
    }

    @Test
    void calculatePrice_At2100() {
        // Arrange
        LocalDateTime testDate = LocalDateTime.of(2020, 6, 14, 21, 0);
        long productId = 35455L;
        int brandId = 1; // ZARA

        // Act
        Price result = priceService.calculatePrice(testDate, productId, brandId);

        // Assert
        assertNotNull(result);
        assertEquals(35.50f, result.getPrice());
    }

    @Test
    void calculatePrice_At1000Day15() {
        // Arrange
        LocalDateTime testDate = LocalDateTime.of(2020, 6, 15, 10, 0);
        long productId = 35455L;
        int brandId = 1; // ZARA

        // Act
        Price result = priceService.calculatePrice(testDate, productId, brandId);

        // Assert
        assertNotNull(result);
        assertEquals(30.50f, result.getPrice());
    }

    @Test
    void calculatePrice_At2100Day16() {
        // Arrange
        LocalDateTime testDate = LocalDateTime.of(2020, 6, 16, 21, 0);
        long productId = 35455L;
        int brandId = 1; // ZARA

        // Act
        Price result = priceService.calculatePrice(testDate, productId, brandId);

        // Assert
        assertNotNull(result);
        assertEquals(38.95f, result.getPrice());
    }
}
