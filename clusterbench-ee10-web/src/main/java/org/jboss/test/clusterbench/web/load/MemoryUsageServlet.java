package org.jboss.test.clusterbench.web.load;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jboss.test.clusterbench.common.load.MemoryUsageStress;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * MemoryUsageStress
 *
 * @author Michal Babacek
 *         <p/>
 *         This simple servlet is used for stressing memory.
 *         <p/>
 *         The objective is to force JVM to allocate a given amount of memory (in megabytes). After the memory is filled up, we
 *         wait for a specified number of milliseconds before dereferencing.
 *         <p/>
 *         E.g.:
 *         <p/>
 *         http://localhost:8080/clusterbench/memoryusage?milliseconds=20000&megabytes=500
 *         <p/>
 *         will allocate 500MB and keep them for 20000ms.
 *         <p/>
 *         Warning: Obviously, if you set megabytes=1000 while having -Xmx512m you will experience an unpleasant and utterly
 *         inevitable OOM.
 *         <p/>
 *         Note: Naturally, if -Xms < -Xmx, JVM will be allocating more system physical memory. You can observe system physical
 *         memory values in the message you shall get from this servlet.
 */
@WebServlet(name = "MemoryUsageServlet", urlPatterns = {"/memoryusage"})
public class MemoryUsageServlet extends HttpServlet {
    private static final long serialVersionUID = -3449582182280915069L;
    @SuppressWarnings("unused")
    private static final Logger log = Logger.getLogger(MemoryUsageServlet.class.getName());
    private final MemoryUsageStress memoryUsageStress = new MemoryUsageStress();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int milliseconds = Integer.parseInt(request.getParameter("milliseconds"));
        int megabytes = Integer.parseInt(request.getParameter("megabytes"));
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(memoryUsageStress.stressSystemMemory(megabytes, milliseconds));
    }

    @Override
    public String getServletInfo() {
        return "By invoking MemoryUsageStress, you stress JVM memory up to a set percentage of maxMemory.";
    }

}
