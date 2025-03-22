/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.authentication;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author admin
 */
public class VerifyController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String num1 = req.getParameter("num1");
        String num2 = req.getParameter("num2");
        String num3 = req.getParameter("num3");
        String num4 = req.getParameter("num4");
        String num5 = req.getParameter("num5");
        String num6 = req.getParameter("num6");

        String enteredOTP = num1 + num2 + num3 + num4 + num5 + num6;

        String correctOTP = (String) req.getSession().getAttribute("otp");
        long otpTimeStammp = (Long) req.getSession().getAttribute("otp_timestamp");

        long currentTime = System.currentTimeMillis();

        if ((currentTime - otpTimeStammp) > TimeUnit.MINUTES.toMillis(1)) {
            req.getSession().removeAttribute("otp");
            req.getSession().removeAttribute("otp_timestamp");
            req.setAttribute("error_login", "The OTP has expired. Please login again.");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
        }

        if (enteredOTP != null && correctOTP.equals(enteredOTP)) {
            req.getSession().removeAttribute("otp");
            req.getSession().removeAttribute("otp_timestamp");
            resp.sendRedirect("home");
        } else {
            req.setAttribute("error_verify", "Wrong OTP. Please try again!");
            req.getRequestDispatcher("auth/verify.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().removeAttribute("otp");
        req.getSession().removeAttribute("otp_timestamp");
        resp.sendRedirect("login");
    }

}
