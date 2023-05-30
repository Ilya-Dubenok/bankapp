package org.example.entrypoint.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.core.dto.RateRangeDTO;
import org.example.core.dto.CurrencyDTO;
import org.example.service.api.IBankService;
import org.example.service.factory.NBRBServiceFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/add_rates")
public class AddRatesForRangeServlet extends HttpServlet {
    IBankService service = NBRBServiceFactory.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter writer = resp.getWriter();

        String currencyName = req.getParameter("name");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate beginDate = LocalDate.parse(req.getParameter("beginDate"), formatter);
        LocalDate endDate = LocalDate.parse(req.getParameter("endDate"), formatter);

        List<CurrencyDTO> rates = service.getCurrency(new RateRangeDTO(currencyName, beginDate, endDate));
        for (CurrencyDTO rate : rates) {
            writer.write(rate.getName() + " " + rate.getDate() + " " + rate.getRate() + "<br>");
        }
    }
}
