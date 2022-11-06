package com.zephie.house.controllers.web.servlets.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zephie.house.core.dto.PizzaInfoDTO;
import com.zephie.house.services.api.IPizzaInfoService;
import com.zephie.house.services.singleton.PizzaInfoServiceSingleton;
import com.zephie.house.util.exceptions.FKNotFound;
import com.zephie.house.util.exceptions.NotFoundException;
import com.zephie.house.util.exceptions.ValidationException;
import com.zephie.house.util.exceptions.WrongVersionException;
import com.zephie.house.util.json.ObjectMapperFactory;
import com.zephie.house.util.time.UnixTimeToLocalDateTimeConverter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet(name = "PizzaInfoServlet", urlPatterns = "/pizza_info")
public class PizzaInfoServlet extends HttpServlet {
    private final IPizzaInfoService pizzaInfoService = PizzaInfoServiceSingleton.getInstance();

    private final ObjectMapper mapper = ObjectMapperFactory.getObjectMapper();

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
            } catch (Exception e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            try {
                pizzaInfoService.read(id).ifPresentOrElse(
                        pizzaInfo -> {
                            try {
                                resp.getWriter().write(mapper.writeValueAsString(pizzaInfo));
                            } catch (IOException e) {
                                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                            }
                        },
                        () -> resp.setStatus(HttpServletResponse.SC_NO_CONTENT)
                );
            } catch (Exception e) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            try {
                resp.getWriter().write(mapper.writeValueAsString(pizzaInfoService.read()));
            } catch (Exception e) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding(CHARSET);
        resp.setCharacterEncoding(CHARSET);
        resp.setContentType(CONTENT_TYPE);

        PizzaInfoDTO pizzaInfoDTO;

        try {
            pizzaInfoDTO = mapper.readValue(req.getReader(), PizzaInfoDTO.class);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            resp.getWriter().write(mapper.writeValueAsString(pizzaInfoService.create(pizzaInfoDTO)));
        } catch (ValidationException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (FKNotFound e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
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
        PizzaInfoDTO pizzaInfoDTO;

        try {
            id = Long.parseLong(stringId);
            versionDate = UnixTimeToLocalDateTimeConverter.convert(Long.parseLong(version));
            pizzaInfoDTO = mapper.readValue(req.getReader(), PizzaInfoDTO.class);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            resp.getWriter().write(mapper.writeValueAsString(pizzaInfoService.update(id, pizzaInfoDTO, versionDate)));
        } catch (ValidationException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (FKNotFound e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (NotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (WrongVersionException e) {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding(CHARSET);
        resp.setCharacterEncoding(CHARSET);

        String stringId = req.getParameter("id");
        String version = req.getParameter("version");

        long id;
        LocalDateTime versionDate;

        try {
            id = Long.parseLong(stringId);
            versionDate = UnixTimeToLocalDateTimeConverter.convert(Long.parseLong(version));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            pizzaInfoService.delete(id, versionDate);
        } catch (NotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (WrongVersionException e) {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}