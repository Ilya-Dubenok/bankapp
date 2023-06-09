package org.example.entrypoint.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.core.dto.AverageRateDTO;
import org.example.service.factory.AverageRateServiceFactory;
import org.example.service.factory.ObjectMapperFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Map;

@WebServlet(urlPatterns = "/averagerate")
public class AverageRateServlet extends HttpServlet {
    private static final String CURRENCY_PARAM_NAME = "currency";
    private static final String MONTH_PARAM_NAME = "month";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();

        Map<String, String[]> parameterMap = req.getParameterMap();

        String[] currencyRaw = parameterMap.get(CURRENCY_PARAM_NAME);
        String curAbbr = null;
        if(currencyRaw != null) {
            if(currencyRaw.length > 1) {
                throw new IllegalArgumentException("More than 1 currency selected.");
            }
            if(currencyRaw.length == 1) {
                curAbbr = currencyRaw[0];
            }
        } else {
            throw new IllegalArgumentException("No currency selected.");
        }

        String[] monthRaw = parameterMap.get(MONTH_PARAM_NAME);
        int month = 0;
        if(monthRaw != null) {
            if(monthRaw.length > 1) {
                throw new IllegalArgumentException("More than 1 month selected.");
            }
            if(monthRaw.length == 1) {
                month = Integer.parseInt(monthRaw[0]);
                if(month < 0 || month > 12) {
                    throw new IllegalArgumentException("No such month.");
                }
            }
        } else {
            throw new IllegalArgumentException("No month selected.");
        }

        AverageRateDTO dto = AverageRateServiceFactory.getInstance().get(new AverageRateDTO(curAbbr, month));
        if(dto.getAvgRate().equals(BigDecimal.ZERO)) {
            resp.sendError(400, "Average rate for " + dto.getCurName() + " cannot be processed due to unexpected error.");
        }
        writer.write(ObjectMapperFactory.getInstance().writeValueAsString(dto.getAvgRate().toString()));
    }
}
