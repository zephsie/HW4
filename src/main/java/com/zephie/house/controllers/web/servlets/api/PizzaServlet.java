package com.zephie.house.controllers.web.servlets.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zephie.house.core.dto.PizzaDTO;
import com.zephie.house.services.api.IPizzaService;
import com.zephie.house.services.entity.PizzaService;
import com.zephie.house.util.exceptions.NotFoundException;
import com.zephie.house.util.exceptions.NotUniqueException;
import com.zephie.house.util.exceptions.ValidationException;
import com.zephie.house.util.exceptions.WrongVersionException;
import com.zephie.house.util.mappers.MapperFactory;
import com.zephie.house.util.time.UnixTimeToLocalDateTime;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet(name = "PizzaServlet", urlPatterns = "/pizza")
public class PizzaServlet extends HttpServlet {

    private final IPizzaService pizzaService = PizzaService.getInstance();

    private final ObjectMapper mapper = MapperFactory.getObjectMapper();

    private final String CHARSET = "UTF-8";

    private final String CONTENT_TYPE = "application/json";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding(CHARSET);
        resp.setCharacterEncoding(CHARSET);
        resp.setContentType(CONTENT_TYPE);

        String stringId = req.getParameter("id");
        if (stringId != null) {
            long id;

            try {
                id = Long.parseLong(stringId);
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            try {
                pizzaService.read(id).ifPresentOrElse(
                        pizza -> {
                            try {
                                resp.getWriter().write(mapper.writeValueAsString(pizza));
                            } catch (IOException e) {
                                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                            }
                        },
                        () -> resp.setStatus(HttpServletResponse.SC_NOT_FOUND)
                );
            } catch (Exception e) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            try {
                resp.getWriter().write(mapper.writeValueAsString(pizzaService.read()));
            } catch (IOException e) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding(CHARSET);
        resp.setCharacterEncoding(CHARSET);
        resp.setContentType(CONTENT_TYPE);

        try {
            resp.getWriter().write(mapper.writeValueAsString(pizzaService.create(mapper.readValue(req.getReader(), PizzaDTO.class))));
        } catch (Exception e) {
            if (e instanceof ValidationException) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            if (e instanceof NotUniqueException) {
                resp.setStatus(HttpServletResponse.SC_CONFLICT);
                return;
            }

            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding(CHARSET);
        resp.setCharacterEncoding(CHARSET);
        resp.setContentType(CONTENT_TYPE);

        String stringId = req.getParameter("id");
        String version = req.getParameter("version");

        long id;
        LocalDateTime versionDate;

        try {
            id = Long.parseLong(stringId);
            versionDate = UnixTimeToLocalDateTime.convert(Long.parseLong(version));
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            resp.getWriter().write(mapper.writeValueAsString(pizzaService.update(id, mapper.readValue(req.getReader(), PizzaDTO.class), versionDate)));
        } catch (Exception e) {
            if (e instanceof ValidationException) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            if (e instanceof NotUniqueException) {
                resp.setStatus(HttpServletResponse.SC_CONFLICT);
                return;
            }

            if (e instanceof NotFoundException) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            if (e instanceof WrongVersionException) {
                resp.setStatus(HttpServletResponse.SC_CONFLICT);
                return;
            }

            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding(CHARSET);
        resp.setCharacterEncoding(CHARSET);
        resp.setContentType(CONTENT_TYPE);

        String stringId = req.getParameter("id");
        String version = req.getParameter("version");

        long id;
        LocalDateTime versionDate;

        try {
            id = Long.parseLong(stringId);
            versionDate = UnixTimeToLocalDateTime.convert(Long.parseLong(version));
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            pizzaService.delete(id, versionDate);
        } catch (Exception e) {
            if (e instanceof NotFoundException) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            if (e instanceof WrongVersionException) {
                resp.setStatus(HttpServletResponse.SC_CONFLICT);
                return;
            }

            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}