<%-- 
    Document   : verify
    Created on : Mar 6, 2025, 7:11:49 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <!-- BOXICONS -->
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>

        <!-- CSS -->
        <link rel="stylesheet" href="assets/css/style_verify.css">

        <title>Verify your email</title>
    </head>
    <body>
        <div class="otp-form">

            <div class="header-form">
                <div class="auth-icon">
                    <i class='bx bx-envelope'></i>
                </div>
                <h4>Verify Your Email</h4>
                <p>Please enter the verification code we sent<br> to your email</p>

                <br>
                <c:if test="${not empty requestScope.error_verify}">
                    <p style="color:red">${requestScope.error_verify}</p>
                </c:if>
            </div>

            <form action="verify" method="post">
                <div class="auth-pin-wrap">
                    <input type="text" class="code-input" name="num1" maxlength="1" required>
                    <input type="text" class="code-input" name="num2" maxlength="1" required>
                    <input type="text" class="code-input" name="num3" maxlength="1" required>
                    <input type="text" class="code-input" name="num4" maxlength="1" required>
                    <input type="text" class="code-input" name="num5" maxlength="1" required>
                    <input type="text" class="code-input" name="num6" maxlength="1" required>
                </div>

                <div>
                    <button type="submit" class="btn-submit" value="Confirm">Confirm</button>
                </div>
            </form>

        </div>

        <script src="assets/js/verify_script.js"></script>



    </body>
</html>
