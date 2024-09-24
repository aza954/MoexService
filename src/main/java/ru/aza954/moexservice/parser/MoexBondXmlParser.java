package ru.aza954.moexservice.parser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import ru.aza954.moexservice.dto.BondDto;
import ru.aza954.moexservice.exception.BondParsingException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MoexBondXmlParser implements Parser {
    @Override
    public List<BondDto> parse(String ratesAsString) {
        var bonds = new ArrayList<BondDto>();

        try {
            var dbf = DocumentBuilderFactory.newInstance();
            dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            var db = dbf.newDocumentBuilder();



            try (var reader = new StringReader(ratesAsString)) {
                InputSource is = new InputSource();
                is.setCharacterStream(reader);
                Document doc = db.parse(is);


                Element root = doc.getDocumentElement();


                // doc.getDocumentElement().normalize();

                NodeList rowsList = doc.getElementsByTagName("row");


                for (var rowIdx = 0; rowIdx < rowsList.getLength(); rowIdx++) {
                    var element = (Element) rowsList.item(rowIdx);
                    String ticker = element.getAttribute("SECID");
                    String price = element.getAttribute("PREVLEGALCLOSEPRICE");
                    String name = element.getAttribute("SHORTNAME");
                    if (price.isEmpty()){
                        price="0";
                    }



                    if (!ticker.isEmpty() && !name.isEmpty()) {
                        bonds.add(new BondDto(ticker, name, Double.parseDouble(price)));
                    }
                }
            }
        } catch (Exception ex) {
            log.error("xml parsing error, xml:{}", ratesAsString, ex);
            throw new BondParsingException(ex);
        }
        return bonds;
    }
}
