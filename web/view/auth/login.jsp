<%-- 
    Document   : login
    Created on : Feb 26, 2025, 1:59:24 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>

        <!-- BOXICONS -->
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>

        <!-- CSS -->
        <link rel="stylesheet" href="view/auth/css/style_login.css">
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
            <form action="/ctrl/auth/login" method="post" class="login-form" autocomplete="on">

                <!--Input username-->
                <div class="input-box">
                    <input type="text" class="input-field" id="username" name="username" placeholder="Enter your username">
                    <label for="username" class="label">Username</label>
                    <i class='bx bx-user icon'></i>
                </div>

                <!--Input password-->
                <div class="input-box">
                    <input type="password" class="input-field" id="password" name="password" placeholder="Enter your password">
                    <label for="password" class="label">Password</label>
                    <i class='bx bx-lock-alt icon'></i>
                </div>

                <!--Forgot password-->
                <div class="form-cols">
                    <div class="col-1"></div>
                    <div class="col-2">     <!--Make it to right hand-->
                        <a href="#">Forgot password</a>
                    </div>
                </div>

                <!--Button submit-->
                <div class="input-box">
                    <button class="btn-submit" value="Login">Login <i class='bx bx-log-in'></i></button>
                    <p class="separator">or</p>
                    <!--Using google Account-->
                    <button class="btn-submit" value="LoginGG">Sign in with Google<i class='bx bxl-google'></i></button>
                </div>

                <!--Register-->
                <div class="switch-form">
                    <span>Don't have an account? <a href="register.html">Register</a> </span>
                </div>
            </form>

        </div>
    </body>
</html>
