package org.example.service;

import java.math.BigDecimal;

public class AverageRateService {
   public BigDecimal get(String curAbbr, int month) {
       validate(curAbbr, month);



       return null;
   }

   private void validate(String curAbbr, int month) {
       if(curAbbr.isBlank() || curAbbr.isEmpty() || curAbbr.length() > 3) {
           throw new IllegalArgumentException("Invalid currency type");
       }
       if(month < 0 || month > 12) {
           throw new IllegalArgumentException("No such month");
       }
       if(curAbbr == null) { //is in db?
           throw new IllegalArgumentException("No such currency");
       }
   }
}
