<%-- 
    Document   : processRequest
    Created on : Mar 24, 2025, 12:12:52 AM
    Author     : admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- BOXICONS -->
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
    <!-- CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/approve.css">
    <title>Process</title>
</head>

<body>
    <!--All wrapper around form-->
    <div class="wrapper">

        <!--Form header-->
        <div class="form-header">
            <!--Title of form-->
            <div class="titles">
                <div class="title-leave">Leave Form</div>
            </div>
        </div>

        <!--Leave form-->
        <form action="process" method="post" class="leave-form">
            
            <input type="hidden" name="id" value="${requestScope.re.id}">

            <!--Input Title-->
            <div class="input-box">
                <input type="text" value="${requestScope.re.title}" class="input-field" id="id" name="title" readonly>
                <label for="id" class="label">Title</label>
                <i class='bx bx-book-open icon' ></i>
            </div>

            <div class="user-details">

                <!--Input empID-->
                <div class="input-box">
                    <input type="text" value="${requestScope.re.owner.empID}" class="input-field" id="id" name="id" readonly>
                    <label for="id" class="label">Employee ID</label>
                    <i class='bx bxs-id-card icon'></i>
                </div>

                <!--Input fullname-->
                <div class="input-box">
                    <input type="text" value="${requestScope.re.owner.empName}" class="input-field" id="name" name="name" readonly>
                    <label for="name" class="label">Full name</label>
                    <i class='bx bxs-user-detail icon'></i>
                </div>
                

            </div>

            <!--Input Leave time-->
            <div class="form-cols">
                <div class="input-box">
                    <input type="date" value="${requestScope.re.startDate}" class="input-field" name="from-date" readonly>
                    <label for="from-date" class="label">From Date</label>
                </div>

                <div class="input-box">
                    <input type="date" value="${requestScope.re.endDate}" class="input-field" name="to-date" readonly>
                    <label for="to-date" class="label">To Date</label>
                </div>
            </div>


            

            <!--Input reason-->
            <div class="input-box">
                <textarea rows="15"class="input-textarea" readonly>${requestScope.re.reason}</textarea>
                <label for="reason" class="label">Reason</label>
            </div>

            <hr>

            <!--Input comment-->
            <div class="input-box">
                <textarea rows="15" class="input-textarea" name="comment"></textarea>
                <label for="reason" class="label">Manager Comment</label>
            </div>

            <!--Button-->
            <div class="form-cols">
                <button class="btn-submit" name="action" type="submit" value="Approved">Approved<i class='bx bx-check' ></i></button>
                <button class="btn-submit" name="action" type="submit" value="Rejected">Rejected<i class='bx bx-x'></i></button>
            </div>

        </form>

    </div>
</body>

</html>