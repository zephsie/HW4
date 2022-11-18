package com.zephie.house.controllers.web.servlets.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zephie.house.core.dto.MenuDTO;
import com.zephie.house.services.api.IMenuService;
import com.zephie.house.services.singleton.MenuServiceSingleton;
import com.zephie.house.util.exceptions.NotFoundException;
import com.zephie.house.util.exceptions.NotUniqueException;
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

@WebServlet(name = "MenuServlet", urlPatterns = "/menu")
public class MenuServlet extends HttpServlet {

    private final IMenuService menuService = MenuServiceSingleton.getInstance();

    private final ObjectMapper mapper = ObjectMapperFactory.getObjectMapper();

    private final String CONTENT_TYPE = "application/json";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
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
                menuService.read(id).ifPresentOrElse(
                        pizzaInfo -> {
                            try {
                                resp.getWriter().write(mapper.writeValueAsString(pizzaInfo));
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
                resp.getWriter().write(mapper.writeValueAsString(menuService.read()));
            } catch (Exception e) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType(CONTENT_TYPE);

        MenuDTO menuDTO;

        try {
            menuDTO = mapper.readValue(req.getReader(), MenuDTO.class);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            resp.getWriter().write(mapper.writeValueAsString(menuService.create(menuDTO)));
        } catch (ValidationException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (NotUniqueException e) {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType(CONTENT_TYPE);

        String stringId = req.getParameter("id");
        String version = req.getParameter("version");

        long id;
        LocalDateTime versionDate;
        MenuDTO menuDTO;

        try {
            id = Long.parseLong(stringId);
            versionDate = UnixTimeToLocalDateTimeConverter.convert(Long.parseLong(version));
            menuDTO = mapper.readValue(req.getReader(), MenuDTO.class);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            resp.getWriter().write(mapper.writeValueAsString(menuService.update(id, menuDTO, versionDate)));
        } catch (ValidationException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (NotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (WrongVersionException | NotUniqueException e) {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
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
            menuService.delete(id, versionDate);
        } catch (NotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (WrongVersionException e) {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}