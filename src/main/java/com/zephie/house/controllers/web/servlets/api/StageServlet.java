package com.zephie.house.controllers.web.servlets.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zephie.house.core.dto.StageDTO;
import com.zephie.house.services.api.IStageService;
import com.zephie.house.services.singleton.StageServiceSingleton;
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

@WebServlet(name = "StageServlet", urlPatterns = "/stage")
public class StageServlet extends HttpServlet {
    private final IStageService stageService = StageServiceSingleton.getInstance();

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
            } catch (Exception e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            try {
                stageService.read(id).ifPresentOrElse(
                        stage -> {
                            try {
                                resp.getWriter().write(mapper.writeValueAsString(stage));
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
                resp.getWriter().write(mapper.writeValueAsString(stageService.read()));
            } catch (Exception e) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType(CONTENT_TYPE);

        StageDTO stageDTO;

        try {
            stageDTO = mapper.readValue(req.getReader(), StageDTO.class);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            resp.getWriter().write(mapper.writeValueAsString(stageService.create(stageDTO)));
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
        StageDTO stageDTO;

        try {
            id = Long.parseLong(stringId);
            versionDate = UnixTimeToLocalDateTimeConverter.convert(Long.parseLong(version));
            stageDTO = mapper.readValue(req.getReader(), StageDTO.class);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            resp.getWriter().write(mapper.writeValueAsString(stageService.update(id, stageDTO, versionDate)));
        } catch (ValidationException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (NotUniqueException | WrongVersionException e) {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
        } catch (NotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
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
            stageService.delete(id, versionDate);
        } catch (NotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (WrongVersionException e) {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}