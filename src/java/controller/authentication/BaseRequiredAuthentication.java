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
import model.Feature;
import model.Role;
import model.User;

/**
 *
 * @author admin
 */
public abstract class BaseRequiredAuthentication extends HttpServlet{
    
    private User getLoggedUser(HttpServletRequest req){
        return (User) req.getSession().getAttribute("user");
    }
    
    private boolean isAllowedAccess(User u, HttpServletRequest req){
        String current_endpoint = req.getServletPath();
        for (Role role : u.getRoles()) {
            for (Feature feature : role.getFeatures()) {
                if(feature.getUrl().equals(current_endpoint))
                    return true;
            }
        }
        return false;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User u = getLoggedUser(req);
        if(u != null && isAllowedAccess(u, req)){
            doPost(req, resp, u);
        } else{
            req.getRequestDispatcher("/auth/403.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User u = getLoggedUser(req);
        if(u != null && isAllowedAccess(u, req)){
            doGet(req, resp, u);
        } else{
            req.getRequestDispatcher("/auth/403.jsp").forward(req, resp);
        }
    }

    protected abstract void doPost(HttpServletRequest req, HttpServletResponse resp, User u);

    protected abstract void doGet(HttpServletRequest req, HttpServletResponse resp, User u);
    
    
}
