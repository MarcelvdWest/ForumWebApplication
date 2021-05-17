/*
 * @(#)ChangeUser.java 1.0 2019/12/13
 */
package servlets;

/*
    Filename:           ChangeUser.java
    Author:             Marcel van der Westhuizen
    Date created:       2019/12/13
    Operating System:   Microsoft Windows 7
    Version:            1.0
    Description:        This class gets parameters gotten by the method in the jsp page and sends them through a web method to the database an 
                        then redirects back to the applicable page  
*/

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import beans.DatabaseBean;

/**
 * This class gets parameters gotten by the method in the jsp page and sends them through a web method to the database an 
 * then redirects back to the applicable page  
 * 
 * @author  Marcel van der Westhuizen
 * @since   2019/12/13
 * @version 1.0
 */
public class ChangeUser extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DatabaseBean db = new DatabaseBean();
        int id = Integer.parseInt(request.getParameter("id"));
        String username = request.getParameter("username"); 
        request.getSession().setAttribute("username", username);
        String email = request.getParameter("email");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String password = request.getParameter("password");
        String confirmPassw = request.getParameter("confirmPassw");
        String securityAnsw = request.getParameter("securityAnsw");
        boolean[] checks = new boolean[2];
        
        checks = db.editUser(id, username, email, name, surname, password, confirmPassw, securityAnsw);
        
        if(checks[1]){
            response.sendRedirect("ChangeUserResultPage.jsp?message=");
        } if(!checks[1]){
            response.sendRedirect("ChangeUserResultPage.jsp?message=Your passwords don't match!");
        }
        
       
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>



}
