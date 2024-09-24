package ru.aza954.moexservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.aza954.moexservice.dto.FigiesDto;
import ru.aza954.moexservice.dto.StocksDto;
import ru.aza954.moexservice.dto.StocksPricesDto;
import ru.aza954.moexservice.dto.TickersDto;
import ru.aza954.moexservice.service.BondService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bonds")
public class MoexController {
    private final BondService bondService;
    @PostMapping("/getBondsByTickers")
    public StocksDto getBondsFromMoex(@RequestBody TickersDto tickersDto){
        return bondService.getBondsFromMoex(tickersDto);
    }
    @PostMapping("/prices")
    public StocksPricesDto getPricesByFigies(@RequestBody FigiesDto figiesDto){
        return bondService.getPricesByFigies(figiesDto);
    }
}
