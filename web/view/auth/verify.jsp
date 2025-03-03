<%-- 
    Document   : verify
    Created on : Mar 2, 2025, 12:04:55 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Verify Email</title>
    </head>
    <body>
        <<form action="/home" method="POST">
            <input type="text" name="verify_code">
            <input type="submit" value="Submit">
        </form>
    </body>
</html>
