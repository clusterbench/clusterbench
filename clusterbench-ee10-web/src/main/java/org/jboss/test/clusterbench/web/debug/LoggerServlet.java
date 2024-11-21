package org.jboss.test.clusterbench.web.debug;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.logging.Logger;

@WebServlet("/log")
public class LoggerServlet extends HttpServlet {
	private static final Logger LOG = Logger.getLogger(LoggerServlet.class.getCanonicalName());

	private void logMarker(String msg) {
		LOG.info(msg);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String msg = request.getParameter("msg");
		logMarker(msg);
		response.getWriter().print("Success");
	}
}
