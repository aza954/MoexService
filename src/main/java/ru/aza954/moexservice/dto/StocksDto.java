package ru.aza954.moexservice.dto;

import lombok.Value;
import ru.aza954.moexservice.Model.Stock;

import java.util.List;

@Value
public class StocksDto {
    List<Stock> stocks;
}
