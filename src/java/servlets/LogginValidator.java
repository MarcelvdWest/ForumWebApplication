/*
 * @(#)LogginValidator.java 1.0 2019/12/13
 */

package servlets;

/*
    Filename:           LogginValidator.java
    Author:             Marcel van der Westhuizen
    Date created:       2019/12/13
    Operating System:   Microsoft Windows 7
    Version:            1.0
    Description:        This class checks the login credentials and sets the logginStatus if they are valid
*/

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import beans.DatabaseBean;

/**
 * This class checks the login credentials and sets the logginStatus if they are valid
 * 
 * @author  Marcel van der Westhuizen
 * @since   2019/12/13
 * @version 1.0
 */
public class LogginValidator extends HttpServlet {
    
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
            String password = request.getParameter("password");
            String username= "";
            DatabaseBean db = new DatabaseBean();
            boolean[][] result = db.logginCheck(email, password);
        
        try{ 
            if(!result[0][0] || !result[0][1]){
                response.sendRedirect("LoginPageFail.jsp");
            }else{
                username = db.getUsername(email);
                
                request.getSession().setAttribute("logginStatus", result[0][1]);
                request.getSession().setAttribute("username", username);
                String loc = (String)request.getSession().getAttribute("loc");

                response.sendRedirect(loc);   
            
            }   
             
        }catch(Exception e){
            System.out.println("An SQL Error has occurred: " + e.getMessage());
            e.printStackTrace();
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
