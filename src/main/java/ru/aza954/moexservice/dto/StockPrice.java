package ru.aza954.moexservice.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value

@AllArgsConstructor
public class StockPrice {
    private String figi;
    private Double price;
}
