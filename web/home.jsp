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
        <link rel="stylesheet" href="assets/css/home.css" />
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
                    <a href="#">
                        <i class="bx bx-grid-alt"></i>
                        <span class="link_name">Agenda</span>
                    </a>
                    <ul class="sub-menu blank">
                        <li><a class="link_name" href="#">Agenda</a></li>
                    </ul>
                </li>
                <c:if test="${sessionScope.user.emp.staffs eq null}">
                    <li>
                        <a href="#">
                            <i class="bx bx-pie-chart-alt-2"></i>
                            <span class="link_name">Analytics</span>
                        </a>
                        <ul class="sub-menu blank">
                            <li><a class="link_name" href="#">Analytics</a></li>
                        </ul>
                    </li>
                </c:if>
                <li>
                    <a href="#">
                        <i class="bx bx-line-chart"></i>
                        <span class="link_name">Chart</span>
                    </a>
                    <ul class="sub-menu blank">
                        <li><a class="link_name" href="#">Chart</a></li>
                    </ul>
                </li>
                <li>
                    <a href="#">
                        <i class="bx bx-compass"></i>
                        <span class="link_name">Explore</span>
                    </a>
                    <ul class="sub-menu blank">
                        <li><a class="link_name" href="#">Explore</a></li>
                    </ul>
                </li>
                <li>
                    <a href="#">
                        <i class="bx bx-history"></i>
                        <span class="link_name">History</span>
                    </a>
                    <ul class="sub-menu blank">
                        <li><a class="link_name" href="#">History</a></li>
                    </ul>
                </li>

                <li>
                    <a href="#">
                        <i class="bx bx-log-out"></i>
                        <span class="link_name">Log Out</span>
                    </a>
                    <ul class="sub-menu blank">
                        <li><a class="link_name" href="#">Log Out</a></li>
                    </ul>
                </li>

            </ul>

            <!-- Logout Link Container - placed outside nav-links for bottom right positioning -->


        </div>

        <!--Section for display content-->
        <section class="home-section">


            <!--<!-- Content -->
            <h1>Hello ${sessionScope.user.username}</h1>

            <c:if test="${sessionScope.user.emp.manager ne null}">
                Report to ${sessionScope.user.emp.manager.empName}  <br/>
            </c:if>
            Report to you: <br/>
            <c:forEach items="${sessionScope.user.emp.staffs}" var="s"> 
                ${s.empName} <br/>
            </c:forEach>
                
                <a href="request.jsp">Create a leave request</a>
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
