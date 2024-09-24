package ru.aza954.moexservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import ru.aza954.moexservice.MoexClient.CorporateBondsClient;
import ru.aza954.moexservice.MoexClient.GovBondsClient;
import ru.aza954.moexservice.dto.BondDto;
import ru.aza954.moexservice.exception.LimitRequestsException;
import ru.aza954.moexservice.parser.MoexBondXmlParser;

import java.util.List;
@Component
@Log4j2
@RequiredArgsConstructor
public class BondRepository {
    private final CorporateBondsClient corporateBondsClient;
    private final GovBondsClient govBondsClient;
    private final MoexBondXmlParser parser;
    @Cacheable(value = "corps")
    public List<BondDto> getCorporateBonds(){
        String xmlFromMoex = corporateBondsClient.getBondsFromMoex();
        List<BondDto> bondDtos = parser.parse(xmlFromMoex);


        if (bondDtos.isEmpty()){
            log.error("Moex");
            throw new LimitRequestsException("Moex ex");

        }
        log.info("Беру Corporate Bonds");
        return bondDtos;

    }

    @Cacheable(value = "gov")
    public List<BondDto> getGovBonds(){
        String xmlFromMoex = govBondsClient.getBondsFromMoex();

        List<BondDto> bondDtos = parser.parse(xmlFromMoex);

        if (bondDtos.isEmpty()){
            log.error("Moex");
            throw new LimitRequestsException("Moex ex");

        }
        log.info("Беру Gov Bonds");
        return bondDtos;

    }
}
