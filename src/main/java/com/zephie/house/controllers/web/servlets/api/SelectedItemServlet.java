package com.zephie.house.controllers.web.servlets.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zephie.house.core.dto.SelectedItemDTO;
import com.zephie.house.services.api.ISelectedItemService;
import com.zephie.house.services.singleton.SelectedItemServiceSingleton;
import com.zephie.house.util.exceptions.FKNotFound;
import com.zephie.house.util.exceptions.NotFoundException;
import com.zephie.house.util.exceptions.ValidationException;
import com.zephie.house.util.exceptions.WrongVersionException;
import com.zephie.house.util.mappers.ObjectMapperFactory;
import com.zephie.house.util.time.UnixTimeToLocalDateTimeConverter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet(name = "SelectedItemServlet", urlPatterns = "/selected_item")
public class SelectedItemServlet extends HttpServlet {
    private final ISelectedItemService selectedItemService = SelectedItemServiceSingleton.getInstance();

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
                selectedItemService.read(id).ifPresentOrElse(
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
                resp.getWriter().write(mapper.writeValueAsString(selectedItemService.read()));
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
            resp.getWriter().write(mapper.writeValueAsString(selectedItemService.create(mapper.readValue(req.getReader(), SelectedItemDTO.class))));
        } catch (Exception e) {
            if (e instanceof ValidationException || e instanceof IOException || e instanceof NullPointerException) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            if (e instanceof FKNotFound) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
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
            versionDate = UnixTimeToLocalDateTimeConverter.convert(Long.parseLong(version));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            resp.getWriter().write(mapper.writeValueAsString(selectedItemService.update(id, mapper.readValue(req.getReader(), SelectedItemDTO.class), versionDate)));
        } catch (Exception e) {
            if (e instanceof ValidationException || e instanceof IOException || e instanceof NullPointerException) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            if (e instanceof NotFoundException || e instanceof FKNotFound) {
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
            selectedItemService.delete(id, versionDate);
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