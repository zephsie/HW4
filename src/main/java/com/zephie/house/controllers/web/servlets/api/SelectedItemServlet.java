package com.zephie.house.controllers.web.servlets.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zephie.house.services.entity.SelectedItemService;
import com.zephie.house.services.singleton.SelectedItemServiceSingleton;
import com.zephie.house.util.json.ObjectMapperFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SelectedItemServlet", urlPatterns = "/selected_item")
public class SelectedItemServlet extends HttpServlet {
    private final SelectedItemService selectedItemService = SelectedItemServiceSingleton.getInstance();

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
                        () -> resp.setStatus(HttpServletResponse.SC_NO_CONTENT)
                );
            } catch (Exception e) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}