/*
 * @(#)AddTopic.java 1.0 2019/12/13
 */
package servlets;

/*
    Filename:           AddTopic.java
    Author:             Marcel van der Westhuizen
    Date created:       2019/12/13
    Operating System:   Microsoft Windows 7
    Version:            1.0
    Description:        This class sends the user input from the jsp page through the web method to the database then redirects back to the index page 
*/


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import beans.DatabaseBean;

/**
 * This class sends the user input from the jsp page through the web method to the database then redirects back to the index page  
 * 
 * @author  Marcel van der Westhuizen
 * @since   2019/12/13
 * @version 1.0
 */
public class AddTopic extends HttpServlet {

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
        String topic = request.getParameter("topic");
        topic = topic.replace("'", "''");
        topic = topic.replace("\"", "\\\"");
        String desc = request.getParameter("desc");
        desc = desc.replace("'", "''");
        desc = desc.replace("\"", "\\\"");
        String user = request.getParameter("username");
        
        
        db.addTopic(topic, desc, user);
        
        response.sendRedirect("index2.jsp");
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
