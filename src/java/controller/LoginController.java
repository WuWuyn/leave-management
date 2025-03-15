/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.emails.Email;
import com.mailersend.sdk.exceptions.MailerSendException;
import dal.UserDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;



/**
 *
 * @author admin
 */
public class LoginController extends HttpServlet {

    private void sendOtp(String toEmail, String otp) {
        Email email = new Email();
        
        email.setFrom("admin", "admin@trial-3yxj6ljkjx7gdo2r.mlsender.net");
        email.addRecipient("user", toEmail);
        
        email.setSubject("Your OTP Code");
        
        email.setPlain("Your OTP code is: "+otp);
        
        MailerSend ms = new MailerSend();
        ms.setToken("mlsn.9e19f476fefa2375e2d5952dea97517f7a6b2ae089b03f4ca5cfaa78ace73f32");
        
        try {
            MailerSendResponse response = ms.emails().send(email);
            System.out.println("Email sent successfully, Message ID: "+response.messageId);
        } catch (MailerSendException ex) {
            ex.printStackTrace();
        }
        
    }

    private String generateOTP() {
        Random r = new Random();
        int otp = r.nextInt(100000);
        return String.format("%06d", otp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        
        UserDBContext udb = new UserDBContext();
        String email = udb.getEmail(username, password);
        
        if (email != null) {
            
//            String otp = generateOTP();
//            req.getSession().setAttribute("otp", otp);
//            req.getSession().setAttribute("otp_timestamp", System.currentTimeMillis());
            req.getSession().setAttribute("email", email);


            resp.sendRedirect("home");
            
//            sendOtp(email, otp);
//
//            resp.sendRedirect("verify.jsp");
        } else {
            req.setAttribute("error_login", "Invalid username or password");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }

        
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

}
