package org.jboss.test.clusterbench.web.exit;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Invoking this servlet causes server to completely stop without sending proper response and triggering shutdown hooks.
 * This servlet is mainly used in failover scenarios.
 */
@WebServlet(name = "ExitServlet", urlPatterns = "/exit")
public class ExitServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Runtime.getRuntime().halt(0);
    }
}
