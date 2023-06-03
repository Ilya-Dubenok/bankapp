package org.example.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.core.dto.CurrencyDTO;
import org.example.core.dto.CurrencyTypeDTO;
import org.example.core.dto.RateRangeDTO;
import org.example.service.api.IBankService;
import org.example.service.factory.ObjectMapperFactory;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NBRBService implements IBankService {

    private static final String URL_CURRENCY = "https://api.nbrb.by/exrates/rates/";
    private static final String URL_TYPE = "https://api.nbrb.by/exrates/currencies";
    private final ObjectMapper mapper = ObjectMapperFactory.getInstance();

    @Override
    public List<CurrencyDTO> getCurrency(RateRangeDTO dto) {

        List<CurrencyDTO> currencyDTOS = new ArrayList<>();

        List<LocalDate> datesOfPeriod = dto.getBeginDate().datesUntil(dto.getEndDate().plusDays(1)).toList();

        for (LocalDate date : datesOfPeriod) {
            String urlString = URL_CURRENCY +
                    dto.getCurrencyName()
                    + "?parammode=2"
                    + "&ondate="
                    + date;
            try {
                currencyDTOS.add(mapper.readValue(new URL(urlString), CurrencyDTO.class));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return currencyDTOS;
    }

    @Override
    public List<CurrencyTypeDTO> getCurrencyTypes() {
        List<CurrencyTypeDTO> types;
        try {
            types = mapper.readValue(new URL(URL_TYPE), new TypeReference<>(){});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return types;
    }

    @Override
    public CurrencyTypeDTO getCurrencyType(String name) {
        CurrencyTypeDTO typeDTO = null;
        String urlString = URL_CURRENCY + name + "?parammode=2";
        try {
            typeDTO = mapper.readValue(new URL(urlString), CurrencyTypeDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return typeDTO;
    }
}