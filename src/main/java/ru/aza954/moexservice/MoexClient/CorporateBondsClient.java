package ru.aza954.moexservice.MoexClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "corporatebonds", url = "https://iss.moex.com/iss/engines/stock/markets/bonds/boards/TQCB/securities.xml?iss.meta=off&iss.only=securities&securities.columns=SECID,PREVLEGALCLOSEPRICE,SHORTNAME",configuration = FeignConfig.class)
public interface CorporateBondsClient {
    @GetMapping
    String getBondsFromMoex();
}
