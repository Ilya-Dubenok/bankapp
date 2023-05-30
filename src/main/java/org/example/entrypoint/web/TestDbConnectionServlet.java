package org.example.entrypoint.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dao.db.ds.DataSourceConnector;

import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Сервлет для быстрой проверки, есть ли коннект с базой
 * Если на странице вывело true, значит коннект есть и настройки верны
 */
@WebServlet(urlPatterns = "/dbtest")
public class TestDbConnectionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Writer writer = resp.getWriter();

        try (
                Connection connection = DataSourceConnector.getInstance().getDataSource().getConnection();
                PreparedStatement ps = connection.prepareStatement(
                        "SHOW search_path;"
                )



        ) {

            ResultSet set = ps.executeQuery();
            if (set.next()) {
                writer.write(String.valueOf(set.getString(1).startsWith("app")));

            }


        } catch (SQLException e) {

            writer.write(e.getMessage());

        }

    }

}
