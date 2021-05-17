/*
 * @(#)validateUser.java 1.0 2019/12/13
 */

package servlets;

/*
    Filename:           ValidateUser.java
    Author:             Marcel van der Westhuizen
    Date created:       2019/12/13
    Operating System:   Microsoft Windows 7
    Version:            1.0
    Description:        This class checks the user credentials and returns the result. This will indicate whether a user is who they say they are
*/

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import beans.DatabaseBean;

/**
 * This class checks the user credentials and returns the result. This will indicate whether a user is who they say they are
 * 
 * @author  Marcel van der Westhuizen
 * @since   2019/12/13
 * @version 1.0
 */
public class ValidateUser extends HttpServlet {
    
    boolean[][] success;
    DatabaseBean db = new DatabaseBean();
    
    
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
        String email = request.getParameter("email");
        String answ = request.getParameter("answ");
        String username= "";
        boolean[][] result = db.userCheck(email, answ);
        
        if(!result[0][0] || !result[0][1]){
            response.sendRedirect("ValidateUserFail.jsp");
        }else{
            username = db.getUsername(email);
            request.getSession().setAttribute("username", username);


            response.sendRedirect("ChangePassword.jsp");  

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
