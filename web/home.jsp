<%-- 
    Document   : home
    Created on : Mar 6, 2025, 7:36:43 PM
    Author     : admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello ${sessionScope.user.username}</h1>
        
        <c:if test="${sessionScope.user.emp.manager ne null}">
           Report to ${sessionScope.user.emp.manager.empName}  <br/>
        </c:if>
        Report to you: <br/>
        <c:forEach items="${sessionScope.user.emp.staffs}" var="s"> 
            ${s.empName} <br/>
        </c:forEach>
    </body>
</html>

