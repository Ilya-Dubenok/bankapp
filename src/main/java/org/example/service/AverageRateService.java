package org.example.service;

import org.example.core.dto.CurrencyDTO;
import org.example.core.dto.AverageRateDTO;
import org.example.core.dto.RateRangeDTO;
import org.example.service.api.IAverageRateService;
import org.example.service.factory.AddRatesForRangeServiceFactory;
import org.example.service.factory.WeekendServiceFactory;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.Year;
import java.util.List;

public class AverageRateService implements IAverageRateService {
    private static final LocalDate START_PERMISSIBLE_DATE = LocalDate.of(2022, 12, 1);
    private static final LocalDate END_PERMISSIBLE_DATE = LocalDate.of(2023, 5, 31);
   public AverageRateDTO get(AverageRateDTO dto) {

       int year =  Year.now().getValue();
       int month = dto.getMonth();
       int lastDay;
       if(month == MonthDay.now().getMonthValue()) {
           lastDay = MonthDay.now().getDayOfMonth();
       } else {
           switch (month) {
               case 4, 6, 9, 11 -> lastDay = 30;
               case 2 -> lastDay = 28;
               default -> lastDay = 31;
           }
       }

       LocalDate dateFrom  = LocalDate.of( year, month, 1);
       LocalDate dateTo = LocalDate.of(year ,month, lastDay);
       if(dateFrom.isAfter(dateTo)) {
           throw new IllegalArgumentException("Начальная дата раньше конечной.");
       }
       if (dateFrom.isBefore(START_PERMISSIBLE_DATE) || dateFrom.isAfter(END_PERMISSIBLE_DATE) ||
               dateTo.isBefore(START_PERMISSIBLE_DATE) || dateTo.isAfter(END_PERMISSIBLE_DATE)) {
           throw new IllegalArgumentException("Недопустимая дата. Доступны данный в период с 01.12.2022 по 31.05.2023");
       }

       List<CurrencyDTO> rateMonthly = AddRatesForRangeServiceFactory.getInstance()
               .save(new RateRangeDTO(dto.getCurName(), dateFrom, dateTo));

       List<LocalDate> weekends  = WeekendServiceFactory.getInstance().getMonthlyWeekends(month);

       if(rateMonthly != null && weekends != null) {
           BigDecimal sum = BigDecimal.ONE;
           for (CurrencyDTO currencyDTO : rateMonthly) {
               if(!weekends.contains(currencyDTO.getDate())) {
                   sum = countGeoMean(sum, currencyDTO.getRate());
               }
           }
           dto.setAvgRate(sum);
       }

       return dto;
   }

   private BigDecimal countGeoMean(BigDecimal sum, BigDecimal second) throws ArithmeticException{
       BigDecimal result = sum.multiply(second);
       return result.sqrt(MathContext.DECIMAL64);
   }
}
