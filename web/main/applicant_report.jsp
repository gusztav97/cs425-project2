<%-- 
    Document   : applicant_reports.jsp
    Created on : Dec 7, 2019, 3:26:12 PM
    Author     : guszt
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="applicant" class="edu.jsu.mcis.cs425.project2.BeanApplicant" scope="session" />
<jsp:setProperty name="applicant" property="*" />
   <%
      if (applicant.getJobs() != null) {
         applicant.setJobsList();
      }
   %>
<!DOCTYPE html>
<html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>Job Report</title>
   </head>
   <body>
      <%
         response.sendRedirect( request.getContextPath() + "/jobreport" );
      %>
      
        <a href="applicant_main.jsp">Login Page</a>
   </body>
</html>