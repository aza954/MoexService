package ru.aza954.moexservice.parser;

import ru.aza954.moexservice.dto.BondDto;

import java.util.List;

public interface Parser {
    List<BondDto> parse(String ratesAsString);
}
