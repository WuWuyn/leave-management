<%-- 
    Document   : process
    Created on : Mar 23, 2025, 11:57:45 PM
    Author     : admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html lang="en" dir="ltr">

    <head>
        <meta charset="UTF-8" />
        <!--<title> Drop Down Sidebar Menu | CodingLab </title>-->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/home.css" />
        <!-- Boxiocns CDN Link -->
        <link href="https://unpkg.com/boxicons@2.0.7/css/boxicons.min.css" rel="stylesheet" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />

        <style>
            /* Add custom styles for logout at bottom right */
            .sidebar .logout-link-container {
                position: absolute; /* Absolute positioning within sidebar */
                bottom: 0;         /* Align to the bottom */
                right: 0;          /* Align to the right */
                width: 100%;       /* Take full width of sidebar for positioning context */
                /* Removed background, border-top and text-align here - let it inherit from li styles */
            }

            .sidebar.close .logout-link-container {
                width: 78px; /* Adjust width in closed state if needed */
                padding: 15px 0; /* Adjust padding in closed state */
                text-align: center; /* Center icon in closed state */
            }

            /* Removed redundant link, icon and link_name styles - they are already defined for sidebar links */

            .sidebar.close .logout-link-container a .link_name {
                opacity: 0;
                pointer-events: none;
                display: none; /* Hide text in closed state */
            }

            .content-wrapper {
                background-color: white;
                border-radius: 10px;
                padding: 20px;
                width: 90%;
                margin: 0 auto 20px;
            }

            .content-wrapper button {
                background-color: #11101d; /* Green background */
                color: white;
                padding: 10px 20px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                margin-bottom: 20px; /* Space between button and table */
            }

            .content-wrapper button:hover {
                background-color: lightblue; /* Darker green on hover */
            }

            table {
                width: 100%;
                border-collapse: collapse;
                font-size: 16px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                border-radius: 10px;
                overflow: hidden;
            }

            table th, table td {
                padding: 12px 15px;
                border: 1px solid #ddd;
                text-align: left;
            }

            table th {
                background-color: #f9f9f9; /* Màu nền nhạt cho tiêu đề */
                font-weight: bold;
            }

            table tr:nth-child(even) {
                background-color: #f2f2f2;
            }

            table tr:nth-child(odd) {
                background-color: #ffffff;
            }

            table tr:hover {
                background-color: #e0e0e0;
            }

            table a {
                color: #4CAF50;
                text-decoration: none;
                padding: 5px 10px;
                border: 1px solid #4CAF50;
                border-radius: 5px;
            }

            table a:hover {
                background-color: #4CAF50;
                color: white;
            }

            /* Điều chỉnh độ rộng cột */
            table th:nth-child(1), table td:nth-child(1) {
                width: 50px;
                text-align: center;
            }

            table th:nth-child(2), table td:nth-child(2) {
                width: 70px;
            }

            table th:nth-child(3), table td:nth-child(3) {
                width: 120px;
            }

            /* Căn chỉnh các cột */
            table th:nth-child(1), table td:nth-child(1),
            table th:nth-child(6), table td:nth-child(6) {
                text-align: center;
            }

            table th:nth-child(4), table td:nth-child(4),
            table th:nth-child(5), table td:nth-child(5){
                width:100px;
            }
            table th:nth-child(9), table td:nth-child(9){
                width: 70px;
            }
        </style>

    </head>

    <body>

        <!--Task bar-->
        <div class="sidebar close">
            <div class="logo-details">
                <i class="bx bxl-c-plus-plus"></i>
                <span class="logo_name">X Company</span>
            </div>
            <ul class="nav-links">
                <li>
                    <a>
                        <i class="bx bx-menu"></i>
                        <span class="link_name">Menu</span>
                    </a>
                </li>

                <li>
                    <a href="${pageContext.request.contextPath}/home">
                        <i class="bx bx-grid-alt"></i>
                        <span class="link_name">Home</span>
                    </a>
                    <ul class="sub-menu blank">
                        <li><a class="link_name">Home</a></li>
                    </ul>
                </li>
                <c:if test="${!(sessionScope.user.emp.staffs eq null)}">
                    <li>
                        <a href="${pageContext.request.contextPath}/staffrequest">
                            <i class="bx bx-line-chart"></i>
                            <span class="link_name">Staff Request</span>
                        </a>
                        <ul class="sub-menu blank">
                            <li><a class="link_name">Staff Request</a></li>
                        </ul>
                    </li>
                </c:if>

                <li>
                    <a href="${pageContext.request.contextPath}/request">
                        <i class="bx bx-history"></i>
                        <span class="link_name">Requests</span>
                    </a>
                    <ul class="sub-menu blank">
                        <li><a class="link_name">Requests</a></li>
                    </ul>
                </li>

                <li>
                    <a href="${pageContext.request.contextPath}/logout">
                        <i class="bx bx-log-out"></i>
                        <span class="link_name">Log Out</span>
                    </a>
                    <ul class="sub-menu blank">
                        <li><a class="link_name">Log Out</a></li>
                    </ul>
                </li>

            </ul>

            <!-- Logout Link Container - placed outside nav-links for bottom right positioning -->


        </div>

        <!--Section for display content-->
        <section class="home-section">
            <div class="content-wrapper">
                <h2>All Staffs' Requests</h2>
                <br>
                <table border="1">
                    <thead>
                        <tr>
                            <th>Index</th>
                            <th>Owner ID</th>
                            <th>Type</th>
                            <th>From Date</th>
                            <th>To Date</th>
                            <th>Status</th>
                            <th>Created Date</th>
                            <th>Approval Date</th>
                            <th>File</th>
                            <th>Process</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${sessionScope.staffRequest}" var="r" varStatus="status">
                            <tr>
                                <td>${status.index + 1}</td>
                                <td>${r.owner.empID}</td>
                                <td>${r.type.typeName}</td>
                                <td>${r.startDate}</td>
                                <td>${r.endDate}</td>
                                <td>${r.status}</td>
                                <td>${r.createdDate}</td>
                                <td>${r.approvalDate}</td>
                                <td>
                                    <c:if test="${not empty r.attachment}">
                                        <a href="${pageContext.request.contextPath}/download?filePath=${r.attachment}">
                                            <i class='bx bx-download'></i>
                                        </a>
                                    </c:if>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${r.status == 'Pending'}">
                                            <form action="${pageContext.request.contextPath}/request/process">
                                                <input type="hidden" name="id" value="${r.id}">
                                                <button type="submit" class="process-btn">Process</button>
                                            </form>
                                        </c:when>
                                        <c:otherwise>
                                            ${r.approvalBy.username}
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

        </section>

        <script>
            let arrow = document.querySelectorAll(".arrow");
            for (var i = 0; i < arrow.length; i++) {
                arrow[i].addEventListener("click", (e) => {
                    let arrowParent = e.target.parentElement.parentElement; //selecting main parent of arrow
                    arrowParent.classList.toggle("showMenu");
                });
            }
            let sidebar = document.querySelector(".sidebar");
            let sidebarBtn = document.querySelector(".bx-menu");
            console.log(sidebarBtn);
            sidebarBtn.addEventListener("click", () => {
                sidebar.classList.toggle("close");
            });
        </script>
    </body>

</html>
