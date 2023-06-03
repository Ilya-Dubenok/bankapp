package org.example.entrypoint.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.core.dto.CurrencyDTO;
import org.example.core.dto.RateRangeDTO;
import org.example.service.api.IAddRatesForRangeService;
import org.example.service.factory.AddRatesForRangeServiceFactory;
import org.example.service.factory.DateTimeFormatterFactory;
import org.example.service.factory.ObjectMapperFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@WebServlet("/add_rates")
public class AddRatesForRangeServlet extends HttpServlet {
    private final IAddRatesForRangeService service = AddRatesForRangeServiceFactory.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper mapper = ObjectMapperFactory.getInstance();
        DateTimeFormatter formatter = DateTimeFormatterFactory.getInstance();

        String currencyName = req.getParameter("name");
        LocalDate beginDate, endDate;

        try{
            beginDate = LocalDate.parse(req.getParameter("beginDate"), formatter);
            endDate = LocalDate.parse(req.getParameter("endDate"), formatter);
        } catch (DateTimeParseException e){
            resp.getWriter().write(mapper.writeValueAsString(e));
            return;
        }

        boolean showOnlyNew = false;
        if (req.getParameterMap().containsKey("showOnlyNew")){
            showOnlyNew = Boolean.parseBoolean(req.getParameter("showOnlyNew"));
        }

        try {
            List<CurrencyDTO> rates = service.save(new RateRangeDTO(currencyName, beginDate, endDate), showOnlyNew);
            resp.getWriter().write(mapper.writeValueAsString(rates));
        } catch (IllegalArgumentException e){
            resp.getWriter().write(mapper.writeValueAsString(e));
        }
    }
}
