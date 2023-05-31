package org.example.entrypoint.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.core.dto.RateRangeDTO;
import org.example.core.dto.CurrencyDTO;
import org.example.service.api.IAddRatesForRangeService;
import org.example.service.factory.AddRatesForRangeServiceFactory;
import org.example.service.factory.DateTimeFormatterFactory;
import org.example.service.factory.ObjectMapperFactory;

import java.io.IOException;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/add_rates")
public class AddRatesForRangeServlet extends HttpServlet {
    IAddRatesForRangeService service = AddRatesForRangeServiceFactory.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = ObjectMapperFactory.getInstance();
        StringBuilder builder = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String currencyName = req.getParameter("name");
        LocalDate beginDate = LocalDate.parse(req.getParameter("beginDate"), formatter);
        LocalDate endDate = LocalDate.parse(req.getParameter("endDate"), formatter);
        Boolean showOnlyNew = false;
        if (req.getParameterMap().containsKey("showOnlyNew")){
            showOnlyNew = Boolean.parseBoolean(req.getParameter("showOnlyNew"));
        }

        List<CurrencyDTO> rates = service.save(new RateRangeDTO(currencyName, beginDate, endDate), showOnlyNew);

        resp.getWriter().write(mapper.writeValueAsString(rates));
    }
}
