package com.zephie.house.controllers.web.servlets.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zephie.house.core.dto.TicketDTO;
import com.zephie.house.services.api.ITicketService;
import com.zephie.house.services.singleton.TicketServiceSingleton;
import com.zephie.house.util.exceptions.FKNotFound;
import com.zephie.house.util.exceptions.NotFoundException;
import com.zephie.house.util.exceptions.NotUniqueException;
import com.zephie.house.util.exceptions.ValidationException;
import com.zephie.house.util.json.ObjectMapperFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "TicketServlet", urlPatterns = "/ticket")
public class TicketServlet extends HttpServlet {
    private final ITicketService ticketService = TicketServiceSingleton.getInstance();

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
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            try {
                ticketService.read(id).ifPresentOrElse(
                        ticket -> {
                            try {
                                resp.getWriter().write(mapper.writeValueAsString(ticket));
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
                resp.getWriter().write(mapper.writeValueAsString(ticketService.read()));
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

        TicketDTO ticketDTO;

        try {
            ticketDTO = mapper.readValue(req.getReader(), TicketDTO.class);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            resp.getWriter().write(mapper.writeValueAsString(ticketService.create(ticketDTO)));
        } catch (ValidationException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (FKNotFound e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (NotUniqueException e) {
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

        long id;

        try {
            id = Long.parseLong(stringId);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            ticketService.delete(id);
        } catch (NotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
