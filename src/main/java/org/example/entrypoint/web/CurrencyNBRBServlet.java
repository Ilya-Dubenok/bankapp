package org.example.entrypoint.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.ICurrencyNBRBService;
import org.example.service.factory.CurrencyNBRBServiceFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WebServlet("/add_currency")
public class CurrencyNBRBServlet extends HttpServlet {
    ICurrencyNBRBService service = CurrencyNBRBServiceFactory.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String currencyName = req.getParameter("name");
        String str = "2016-03-04 11:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate beginDate = LocalDate.parse(req.getParameter("beginDate"), formatter);
        LocalDate endDate = LocalDate.parse(req.getParameter("endDate"), formatter);

        service.save();
    }
}
