<%-- 
    Document   : login
    Created on : Mar 5, 2025, 11:56:35 PM
    Author     : admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">


        <!-- BOXICONS -->
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
        <!-- CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/login.css" />

        <title>Login</title>
    </head>
    <body>
        <!--All wrapper around form-->
        <div class="wrapper">
            <!--Form header-->
            <div class="form-header">
                <!--Title of form-->
                <div class="titles">
                    <div class="title-login">Login</div>
                </div>
            </div>

            <!--LOGIN form-->
            <form action="login" method="post" class="login-form" autocomplete="on">
                <c:if test="${not empty requestScope.error_login}">
                    <div style="color: red; font-weight: 100px;" class="error" >
                        ${requestScope.error_login}
                    </div>
                </c:if>

                <!--Input username-->
                <div class="input-box">
                    <input type="text" name="username" class="input-field" id="username" placeholder="Enter your username">
                    <label for="username" class="label">Username</label>
                    <i class='bx bx-user icon'></i>
                </div>

                <!--Input password-->
                <div class="input-box">
                    <input type="password" name="password" class="input-field" id="password" placeholder="Enter your password">
                    <label for="password" class="label">Password</label>
                    <i class='bx bx-lock-alt icon'></i>
                </div>

                <!--Button submit-->
                <div class="input-box">
                    <button class="btn-submit" type="submit">Login <i class='bx bx-log-in'></i></button>
                    <p class="separator">or</p>
                    <!--Using google Account-->
                    <div class="separator">
                        <a href="${pageContext.request.contextPath}/loginGG" >Sign in with Google Account</a>
                    </div>
                </div>

            </form>

        </div>

    </body>
</html>
