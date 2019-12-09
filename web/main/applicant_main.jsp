<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="applicant" class="edu.jsu.mcis.cs425.project2.BeanApplicant" scope="session" />
<jsp:setProperty name="applicant" property="username" value="<%= request.getRemoteUser() %>" />
<% applicant.setUserInfo(); %>

<!DOCTYPE html>
<html>
    
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Applicant Welcome Page</title>
    </head>
    
    <body>
        <h1>Hello, <jsp:getProperty name="applicant" property="username" />!</h1>
        <h1>Hello, <jsp:getProperty name="applicant" property="displayname" />!</h1>
        <h1>Hello, <jsp:getProperty name="applicant" property="id" />!</h1>
        <a href="applicant_skills.jsp">Sorted Skills</a>
        <a href="applicant_jobs.jsp">Jobs</a>
        <a href="<%= request.getContextPath() %>/public/logout.jsp" target="_self">Log Out</a>
    </body>
    
</html>