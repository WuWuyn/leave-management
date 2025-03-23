<%-- 
    Document   : home
    Created on : Mar 6, 2025, 7:36:43 PM
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


            .box-container {
                display: flex;
                justify-content: center;
                gap: 20px; /* Space between boxes */
            }
            .box {
                background-color: white;
                padding: 20px;
                border-radius: 8px; /* Rounded corners */
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); /* Subtle shadow */
                width: 200px;
                text-align: left;
                transition: transform 0.2s; /* Hover effect */
            }
            .box:hover {
                transform: translateY(-5px); /* Slight lift on hover */
            }
            .box h3 {
                font-size: 14px;
                color: #666; /* Gray color for title */
                text-transform: uppercase;
                margin: 0 0 10px 0;
            }
            .box p {
                font-size: 32px;
                font-weight: bold;
                color: #003087; /* Dark blue for numbers */
                margin: 0;
            }


            .calendar-container {
                margin-top: 20px;
                overflow-x: auto;
                padding: 15px;
                background-color: #fff;
                border-radius: 10px;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            }
            .calendar-controls {
                margin-bottom: 15px;
                display: flex;
                justify-content: space-between;
                align-items: center;
                font-size: 16px;
            }
            .calendar-table {
                border-collapse: collapse;
                width: 100%;
                min-width: 600px;
                font-size: 14px;
            }
            .calendar-table th, .calendar-table td {
                border: 1px solid #ddd;
                padding: 12px;
                text-align: center;
                min-width: 80px;
            }
            .calendar-table th {
                background-color: #f2f2f2;
                font-weight: 600;
                color: #333;
                text-transform: uppercase;
            }
            .calendar-table td.off {
                background-color: #e63946;
                color: white;
            }
            .calendar-table td.working {
                background-color: #06d6a0;
                color: white;
            }
            .calendar-table tr:hover {
                background-color: #f9f9f9;
            }
            .nav-btn {
                padding: 8px 20px;
                background-color: #003087;
                color: white;
                text-decoration: none;
                border-radius: 5px;
                transition: background-color 0.3s ease;
            }
            .nav-btn:hover {
                background-color: #0056b3;
            }
            @media (max-width: 768px) {
                .calendar-table th, .calendar-table td {
                    padding: 8px;
                    min-width: 60px;
                }
                .nav-btn {
                    padding: 6px 12px;
                }
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
                <!--<!-- Content -->
                <h1>Hello ${sessionScope.user.username}</h1>

                <div class="box-container">
                    <div class="box">
                        <h3>Total Staffs</h3>
                        <p>${totalStaffs}</p>
                    </div>
                    <div class="box">
                        <h3>Total Days Off</h3>
                        <p>${totalDaysOff}</p>
                    </div>
                </div>

                <div class="calendar-container">
                    <h2>Staff Agenda</h2>
                    <div class="calendar-controls">
                        <a href="${pageContext.request.contextPath}/home?startDate=${prevStartDate}" class="nav-btn">Previous Week</a>
                        <span>Week of ${dateList[0]} to ${dateList[6]}</span>
                        <a href="${pageContext.request.contextPath}/home?startDate=${nextStartDate}" class="nav-btn">Next Week</a>
                    </div>
                    <table class="calendar-table">
                        <tr>
                            <th>Staff</th>
                                <c:forEach items="${dateList}" var="dateStr">
                                <th>${dateStr}</th>
                                </c:forEach>
                        </tr>
                        <c:forEach items="${staffList}" var="staff">
                            <tr>
                                <td>${staff.empName}</td>
                                <c:forEach items="${statusMap[staff]}" var="status">
                                    <td class="${status}"></td>
                                </c:forEach>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
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
