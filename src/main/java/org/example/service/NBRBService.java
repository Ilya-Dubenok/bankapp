package org.example.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.core.dto.CurrencyDTO;
import org.example.core.dto.CurrencyTypeDTO;
import org.example.core.dto.RateRangeDTO;
import org.example.service.api.IBankService;
import org.example.service.api.ICurrencyTypeService;
import org.example.service.factory.CurrencyTypeServiceFactory;
import org.example.service.factory.ObjectMapperFactory;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class NBRBService implements IBankService {

    private static final String URL_CURRENCY = "https://api.nbrb.by/exrates/rates/dynamics/";
    private static final String URL_TYPE = "https://api.nbrb.by/exrates/rates/";
    private final ObjectMapper mapper = ObjectMapperFactory.getInstance();
    private final ICurrencyTypeService typeServiceFactory = CurrencyTypeServiceFactory.getInstance();

    @Override
    public List<CurrencyDTO> getCurrency(RateRangeDTO dto) {

        List<CurrencyDTO> currencyDTOS;

        String currencyName = dto.getCurrencyName();
        long id = typeServiceFactory.getCurrencyType(currencyName).getId();
        String urlString = URL_CURRENCY
                + id
                + "?startDate=" + dto.getBeginDate()
                + "&endDate=" + dto.getEndDate();

        try {
            currencyDTOS = mapper.readValue(new URL(urlString), new TypeReference<>() {});
            for (CurrencyDTO currencyDTO : currencyDTOS) {
                currencyDTO.setName(currencyName);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return currencyDTOS;
    }

    @Override
    public CurrencyTypeDTO getCurrencyType(String name) {
        CurrencyTypeDTO typeDTO = null;
        String urlString = URL_TYPE + name + "?parammode=2";
        try {
            typeDTO = mapper.readValue(new URL(urlString), CurrencyTypeDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return typeDTO;
    }
}