package org.example.entrypoint.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.core.dto.RateRangeCreateDTO;
import org.example.core.dto.CurrencyDTO;
import org.example.service.api.IAddRatesForRangeService;
import org.example.service.factory.AddRatesForRangeServiceFactory;
import org.example.service.factory.ObjectMapperFactory;

import java.io.IOException;
import java.util.List;

@WebServlet("/add_rates")
public class AddRatesForRangeServlet extends HttpServlet {
    IAddRatesForRangeService service = AddRatesForRangeServiceFactory.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = ObjectMapperFactory.getInstance();
        String jsonResponse;

        String currencyName = req.getParameter("name");
        String beginDate = req.getParameter("beginDate");
        String endDate = req.getParameter("endDate");
        Boolean showOnlyNew = false;
        if (req.getParameterMap().containsKey("showOnlyNew")){
            showOnlyNew = Boolean.parseBoolean(req.getParameter("showOnlyNew"));
        }

        try {
            List<CurrencyDTO> rates = service.save(new RateRangeCreateDTO(currencyName, beginDate, endDate), showOnlyNew);
            jsonResponse = mapper.writeValueAsString(rates);
        } catch (IllegalArgumentException e){
            jsonResponse = mapper.writeValueAsString(e);
        }

        resp.getWriter().write(jsonResponse);
    }


}
