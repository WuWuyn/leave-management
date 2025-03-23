/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.ArrayList;
import model.User;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Employee;
import model.Feature;
import model.Role;

/**
 *
 * @author admin
 */
public class UserDB extends DBContext<User> {

    public String getEmail(String username, String password) {
        String email = null;
        try {
            String sql = "select email from Users where username = ? and password = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                email = rs.getString("email");
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return email;
    }

    public User get(String email) {
        try {
            String sql = "SELECT u.username,\n"
                    + "       r.roleID, r.roleName,\n"
                    + "       f.featureID, f.url,\n"
                    + "       e.empID, e.empName,\n"
                    + "       m.empID AS [managerID],\n"
                    + "       m.empName AS [managerName]\n"
                    + "FROM Users u\n"
                    + "INNER JOIN Employees e ON e.empID = u.empID\n"
                    + "LEFT JOIN Employees m ON e.managerID = m.empID\n"
                    + "LEFT JOIN UserRole ur ON ur.username = u.username\n"
                    + "LEFT JOIN Roles r ON r.roleID = ur.roleID\n"
                    + "LEFT JOIN RoleFeature rf ON r.roleID = rf.roleID\n"
                    + "LEFT JOIN Features f ON f.featureID = rf.featureID\n"
                    + "WHERE u.email = ? and u.isActive = 1;";
            
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, email);
            ResultSet rs = stm.executeQuery();
            
            User user = null;
            Role current_role = new Role();
            current_role.setId(-1);
            
            while(rs.next()){
                if(user == null){
                    user = new User();
                    user.setUsername(rs.getString("username"));
                    user.setEmail(email);
                    
                    Employee e = new Employee();
                    e.setEmpID(rs.getString("empID"));
                    e.setEmpName(rs.getNString("empName"));
                    user.setEmp(e);
                    
                    String managerID = rs.getString("managerID");
                    if(managerID != null){
                        Employee m = new Employee();
                        m.setEmpID(managerID);
                        m.setEmpName(rs.getNString("managerName"));
                        e.setManager(m);
                    }
                }
                
                int rid = rs.getInt("roleID");
                if(rid > 0 && rid != current_role.getId()){
                    current_role = new Role();
                    current_role.setId(rid);
                    current_role.setName(rs.getNString("roleName"));
                    current_role.getUsers().add(user);
                    user.getRoles().add(current_role);
                }
                
                int fid = rs.getInt("featureID");
                if(fid > 0){
                    Feature f = new Feature();
                    f.setId(fid);
                    f.setUrl(rs.getString("url"));
                    current_role.getFeatures().add(f);
                    f.getRoles().add(current_role);
                }
            }
            return user;
        } catch (SQLException ex) {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            if(connection != null){
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        return null;
    }

    @Override
    public ArrayList<User> list() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public User get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insert(User model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(User model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(User model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
