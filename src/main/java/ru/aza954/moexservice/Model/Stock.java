package ru.aza954.moexservice.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;



@Value
@AllArgsConstructor
@Builder
public class Stock {
    String ticker;
    String figi;
    String name;
    String type;
    Currency currency;
    String source;
}

