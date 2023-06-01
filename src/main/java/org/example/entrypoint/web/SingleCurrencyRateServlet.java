package org.example.entrypoint.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.core.dto.CurrencyDTO;
import org.example.service.api.ISingleCurrencyRateService;
import org.example.service.factory.ObjectMapperFactory;
import org.example.service.factory.SingleCurrencyRateServiceFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = "/singlecurrencyrate")
public class SingleCurrencyRateServlet extends HttpServlet {

    private static final String TYPE_CURRENCY_PARAM = "typeCurrency";


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();

        String typeCurrency = req.getParameter(TYPE_CURRENCY_PARAM);

        ISingleCurrencyRateService singleCurrencyRateService = SingleCurrencyRateServiceFactory.getInstance();
        List<CurrencyDTO> currencyDTOS = null;
        if (typeCurrency != null && !"".equals(typeCurrency)) {
            currencyDTOS = singleCurrencyRateService.get(typeCurrency);
        } else {
            resp.sendError(400);
        }

        ObjectMapper objectMapper = ObjectMapperFactory.getInstance();

        if (currencyDTOS != null) {
            writer.write(objectMapper.writeValueAsString(currencyDTOS));
        } else {
            resp.sendError(400);
        }
    }
}
