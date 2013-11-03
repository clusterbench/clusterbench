/*
 * Copyright 2013 Radoslav Husár
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jboss.test.clusterbench.web.load;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.test.clusterbench.common.load.AverageSystemLoad;

/**
 * AverageSystemLoadServlet
 * 
 * @author Michal Babacek 
 *
 * This simple servlet is used for stressing the server's CPU.
 * 
 * Usage:
 * 
 * You may GET e.g. this URL: http://localhost:8080/clusterbench/averagesystemload?milliseconds=20000&threads=4
 * By doing so, there will be 4 threads with evil active-loops created.
 * These threads will be running for 20000 milliseconds.
 * 
 * After the aforementioned time, you shall receive a response saying something like:
 * 
 * DONE, I was stressing CPU with 4 evil threads for 20000 milliseconds (including warm-up).
 * 
 * NOTE: Do not forget to set some reasonable time-out on your client...
 * That's it. No more functionality. 
 */
public class AverageSystemLoadServlet extends HttpServlet {
   private static final Logger log = Logger.getLogger(AverageSystemLoadServlet.class.getName());
   private final AverageSystemLoad averageSystemLoad = new AverageSystemLoad();
   
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      int milliseconds = Integer.parseInt(request.getParameter("milliseconds"));
      int numberOfThreads = Integer.parseInt(request.getParameter("threads")); 
      response.setContentType("text/plain");
      String resultLogMessage = averageSystemLoad.spawnLoadThreads(numberOfThreads, milliseconds);
      log.log(Level.INFO,resultLogMessage);
      response.getWriter().print("DONE");
   }

   @Override
   public String getServletInfo() {
      return "By invoking AverageSystemLoadServlet, you stress CPU.";
   }
}
