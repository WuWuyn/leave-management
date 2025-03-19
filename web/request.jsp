<%-- 
    Document   : createRequest
    Created on : Mar 15, 2025, 9:29:49 PM
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
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/request.css" />
        <title>Register</title>
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
            <form action="create" method="post" class="leave-form" enctype="multipart/form-data">

                <!--Input Title-->
                <div class="input-box">
                    <input type="text" class="input-field" id="id" name="title" placeholder="Title of your form" required="">
                    <label for="id" class="label">Title</label>
                    <i class='bx bxs-id-card icon'></i>
                </div>


                <div class="user-details">
                    <!--Input empID-->
                    <div class="input-box">
                        <label for="empID" class="label">Employee ID</label>
                        <select class="input-field" name="empID" id="empID" onchange="updateName()" required="">
                            <option disabled selected>Select the Owner's ID</option> 
                            <c:forEach items="${requestScope.employees}" var="e">
                                <option value="${e.empID}" data-name="${e.empName}">${e.empID}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <!--Input fullname-->
                    <div class="input-box">
                        <input type="text" class="input-field" id="name" name="name" placeholder="Enter full name" readonly>
                        <label for="name" class="label">Full name</label>
                        <i class='bx bxs-user-detail icon'></i>
                    </div>


                </div>

                <!--Input Leave time-->
                <div class="form-cols">
                    <div class="input-box">
                        <input type="date" class="input-field" name="from-date" required="">
                        <label for="from-date" class="label">From Date</label>
                    </div>

                    <div class="input-box">
                        <input type="date" class="input-field" name="to-date" required="">
                        <label for="to-date" class="label">To Date</label>
                    </div>
                </div>

                <!--Input type reason and attaching file-->

                <div class="user-details">
                    <div class="input-box">
                        <label class="label">Leave Type </label>
                        <select class="input-field" name="typeID" required="">
                            <c:forEach items="${requestScope.leaveType}" var="t">
                                <option value="${t.typeID}">${t.typeName}</option>
                            </c:forEach>
                        </select><br/>
                    </div>

                    <div class="input-box">
                        <label for="file-upload" class="file-upload-label">Attachment</label><b>
                            <input type="file" id="file-upload" class="" name="file"/>
                    </div>

                </div>


                <!--Input reason-->
                <div class="input-box">
                    <textarea rows="15" class="input-textarea" name="reason" required=""></textarea>
                    <label for="reason" class="label">Reason</label>
                </div>

                <!--Button-->
                <button class="btn-submit" type="submit">Submit<i class='bx bx-right-arrow-alt'></i></button>
            </form>

        </div>

        <!-- JavaScript for name population -->
        <script>
            function updateName() {
                var empIDDropdown = document.getElementById("empID");
                var nameInputField = document.getElementById("name");

                // Get the selected option
                var selectedOption = empIDDropdown.options[empIDDropdown.selectedIndex];

                // Get the employee name from the data-name attribute of the selected option
                var empName = selectedOption.getAttribute("data-name");

                // Set the value of the name input field
                nameInputField.value = empName;
            }
        </script>
    </body>

</html>
        
    </body>

</html>
