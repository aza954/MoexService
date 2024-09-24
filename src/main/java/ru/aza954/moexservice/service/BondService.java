package ru.aza954.moexservice.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.aza954.moexservice.Model.Currency;
import ru.aza954.moexservice.Model.Stock;
import ru.aza954.moexservice.MoexClient.CorporateBondsClient;
import ru.aza954.moexservice.MoexClient.GovBondsClient;
import ru.aza954.moexservice.dto.*;
import ru.aza954.moexservice.exception.BondNotFoundException;
import ru.aza954.moexservice.exception.LimitRequestsException;
import ru.aza954.moexservice.parser.Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BondService {
    private static final Logger log = LoggerFactory.getLogger(BondService.class);
    private final CacheManager cacheManager;
    private final BondRepository bondRepository;
    public StocksDto getBondsFromMoex(TickersDto tickersDto){
    List<BondDto> allBonds = new ArrayList<>();
    allBonds.addAll(bondRepository.getCorporateBonds());
    allBonds.addAll(bondRepository.getGovBonds());

    List<BondDto> resultBonds = allBonds.stream().filter(b -> tickersDto.getTickers().contains(b.getTicker())).collect(Collectors.toList());
    if (resultBonds.isEmpty()){
        throw new BondNotFoundException("Такой не найден");
    }
    List<Stock> stocks = resultBonds.stream().map(b ->{
        return Stock.builder()
                .ticker(b.getTicker())
                .name(b.getName())
                .figi(b.getTicker())
                .type("Bond")
                .currency(Currency.RUB)
                .source("MOEX")
                .build();
    }).collect(Collectors.toList());
    return new StocksDto(stocks);
    }


    public StocksPricesDto getPricesByFigies(FigiesDto figiesDto) {
        log.info("Запрос для figies {}", figiesDto.getFigies());
        List<String> figies = new ArrayList<>(figiesDto.getFigies());
        List<BondDto> allBonds = new ArrayList<>();
        allBonds.addAll(bondRepository.getCorporateBonds());
        allBonds.addAll(bondRepository.getGovBonds());
        figies.removeAll(allBonds.stream().map(b -> b.getTicker()).collect(Collectors.toList()));
        if (!figies.isEmpty()){
            throw new BondNotFoundException("Bonds not found");
        }
        List<StockPrice> prices = allBonds.stream().filter(b -> figiesDto.getFigies().contains(b.getTicker())).map(b -> new StockPrice(b.getTicker(), b.getPrice()*10)).collect(Collectors.toList());
        
        return new StocksPricesDto(prices);
    }
}
