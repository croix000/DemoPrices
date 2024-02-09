package com.inditex.demo.adapter.incoming.rest;

import com.inditex.demo.adapter.incoming.rest.dto.PriceResponseDTO;
import com.inditex.demo.core.domain.entity.Price;
import com.inditex.demo.core.domain.service.PriceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PriceController.class)
class PriceControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private PriceService priceService;

    @InjectMocks
    private PriceController priceController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(priceController).build();
    }

    @Test
    void getPrices_ReturnsPrice() throws Exception {


        Price price = new Price(1,  LocalDateTime.of(2024, 2, 10, 10, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59, 59),1,1L,1,
                35.50f,"EUR");


        given(priceService.calculatePrice(any(LocalDateTime.class), anyLong(), anyInt())).willReturn(price);

        mockMvc.perform(get("/api/prices/validate")
                        .param("date", "2024-02-10T10:00:00")
                        .param("productId", "1")
                        .param("brandId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").value(price.getProductId()))
                .andExpect(jsonPath("$.brandId").value(price.getBrandId()));
    }
}
