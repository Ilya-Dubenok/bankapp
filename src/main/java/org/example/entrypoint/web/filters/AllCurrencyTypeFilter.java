package org.example.entrypoint.web.filters;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import org.example.core.dto.CurrencyTypeDTO;
import org.example.service.api.IBankService;
import org.example.service.api.ICurrencyTypeService;
import org.example.service.factory.CurrencyTypeServiceFactory;
import org.example.service.factory.NBRBServiceFactory;

import java.io.IOException;
import java.util.List;

@WebFilter(urlPatterns = "/*")
public class AllCurrencyTypeFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        boolean isFirst = true;
        if(isFirst) {
            isFirst = false;
            ICurrencyTypeService currencyTypeService = CurrencyTypeServiceFactory.getInstance();
            IBankService bankService = NBRBServiceFactory.getInstance();
            List<CurrencyTypeDTO> currencyTypeDTOS = currencyTypeService.get();
            List<CurrencyTypeDTO> currencyTypes = bankService.getCurrencyTypes();

            if (currencyTypes.size() != currencyTypeDTOS.size()) {
                currencyTypes.forEach(currencyTypeService::saveCurrencyType);
            }
        }
        chain.doFilter(request, response);
    }
}
