package org.jboss.clusterbench;

import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/cdi")
public class CdiServlet extends HttpServlet {

   @Inject
   private SessionScopedCdiBean bean;

   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      resp.setContentType("text/plain");

      Integer id = bean.getSerialId();
      bean.setSerialId(id + 1);

      resp.getWriter().print(id);
      //resp.getWriter().flush();

   }
}
