package com.inditex.demo.adapter.incoming.rest;

import com.inditex.demo.adapter.incoming.rest.dto.PriceResponseDTO;
import com.inditex.demo.core.domain.entity.Price;
import com.inditex.demo.core.domain.service.PriceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/prices")
public class PriceController {

    private final PriceService priceService;

    @Autowired
    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @GetMapping("/validate")
    @Operation(summary = "Get price information",
            description = "Returns price details for a given product ID, brand ID and date.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(schema = @Schema(implementation = PriceResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "404", description = "Price not found")
            })
    public PriceResponseDTO getPrices(
            @Parameter(description = "Date and time of price check", required = true)
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,

            @Parameter(description = "Product ID to check", required = true)
            @RequestParam("productId") Long productId,

            @Parameter(description = "Brand ID of the product", required = true)
            @RequestParam("brandId") Integer brandId) {

        Price price = priceService.calculatePrice(date, productId, brandId);
        return convertToDto(price);
    }

    private PriceResponseDTO convertToDto(Price price) {
        return new PriceResponseDTO(
                price.getProductId(),
                price.getBrandId(),
                price.getPriceList(),
                price.getStartDate(),
                price.getEndDate(),
                price.getPrice()
        );
    }
}
