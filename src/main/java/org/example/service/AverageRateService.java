package org.example.service;

import org.example.core.dto.CurrencyDTO;
import org.example.core.dto.AverageRateDTO;
import org.example.service.api.IAverageRateService;
import org.example.service.factory.ValidationCurrencyServiceFactory;
import org.example.service.factory.WeekendServiceFactory;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class AverageRateService implements IAverageRateService {
   public AverageRateDTO get(String curAbbr, int month) {
       validate(curAbbr, month);
       AverageRateDTO dto = new AverageRateDTO(curAbbr, month);

       int year =  Year.now().getValue();
       int lastDay;
       if(month == MonthDay.now().getMonthValue()) {
           lastDay = MonthDay.now().getDayOfMonth();
       } else {
           switch (month) {
               case 4, 6, 9, 11 -> {
                   lastDay = 30;
               }
               case 2 -> {
                   lastDay = 28;
               }
               default -> lastDay = 31;
           }
       }

       LocalDate dateFrom  = LocalDate.of( year, month, 1);
       LocalDate dateTo = LocalDate.of(year ,month, lastDay);
       List<CurrencyDTO> rateMonthly = new ArrayList<>(); // use service

       List<LocalDate> weekends  = WeekendServiceFactory.getInstance().getMonthlyWeekends(month);

       BigDecimal sum = BigDecimal.ONE;
       for (CurrencyDTO currencyDTO : rateMonthly) {
           if(!weekends.contains(currencyDTO.getDate())) {
               sum = countGeoMean(sum, currencyDTO.getRate());
           }
       }
       dto.setAvgRate(sum);

       return dto;
   }

   private BigDecimal countGeoMean(BigDecimal sum, BigDecimal second) {
       sum = sum.multiply(second);
       sum = sum.sqrt(new MathContext(0));
       return sum;
   }

   private void validate(String curAbbr, int month) { //use validate service?
       ValidationCurrencyServiceFactory.getInstance().validateTypeCurrency(curAbbr);
       if(month < 0 || month > 12) {
           throw new IllegalArgumentException("No such month");
       }
   }
}
