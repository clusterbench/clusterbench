<%@ page import="java.util.*" %>
<html>

<head>
    <title>Authentication Error</title>
</head>

<body>
    <h2> Authentication Error </h2>
    <h3> HTTP Request Headers Received: </h3>
    <table>
        <% Enumeration enumeration = request.getHeaderNames();
           while (enumeration.hasMoreElements()) {
           String name= (String) enumeration.nextElement();
           String value = request.getHeader(name);
           %>
            <tr>
                <td>
                    <%=name %>
                </td>
                <td>
                    <%=value %>
                </td>
            </tr>
            <%
            }
            %>
    </table>
    <h3> HTTP Request Headers Returned: </h3>
    <table>
        <%
           for (String name1 : response.getHeaderNames()) {
           String value1 = response.getHeader(name1);
           %>
            <tr>
                <td>
                    <%=name1 %>
                </td>
                <td>
                    <%=value1 %>
                </td>
            </tr>
            <%
            }
            %>
    </table>
</body>
</html>