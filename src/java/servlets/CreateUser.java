/*
 * @(#)CreateUser.java 1.0 2019/12/13
 */
package servlets;

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
public class CreateUser extends HttpServlet {

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
        String username = request.getParameter("username"); 
        String email = request.getParameter("email");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String password = request.getParameter("password");
        String confirmPassw = request.getParameter("confirmPassw");
        String securityAnsw = request.getParameter("securityAnsw");
        DatabaseBean db = new DatabaseBean();
        
        
        String message = db.addUser(username, email, name, surname, password, confirmPassw, securityAnsw);
        
        //The code block send the applicable message to the browser
        if(message.equals("That username is already in use.")){
            username = "";
            response.sendRedirect("SignUpPageFail.jsp?username=" + username + "&email=" + email + "&name=" + name + "&surname=" + surname + 
                    "&securityAnsw=" + securityAnsw + "&message=" + message);
        }else if(message.equals("That email is already in use.")){
            email = "";
            response.sendRedirect("SignUpPageFail.jsp?username=" + username + "&email=" + email + "&name=" + name + "&surname=" + surname + 
                    "&securityAnsw=" + securityAnsw + "&message=" + message);
        }else if(message.equals("The passwords you entered doesn't match")){
            response.sendRedirect("SignUpPageFail.jsp?username=" + username + "&email=" + email + "&name=" + name + "&surname=" + surname + 
                    "&securityAnsw=" + securityAnsw + "&message=" + message);
        }else if(message.equals("Username must be one word and and start with a Capital letter")){
            response.sendRedirect("SignUpPageFail.jsp?username=" + username + "&email=" + email + "&name=" + name + "&surname=" + surname + 
                    "&securityAnsw=" + securityAnsw + "&message=" + message);
        }else if(message.equals("Content of email field must be an email")){
            response.sendRedirect("SignUpPageFail.jsp?username=" + username + "&email=" + email + "&name=" + name + "&surname=" + surname + 
                    "&securityAnsw=" + securityAnsw + "&message=" + message);
        }else if(message.equals("Name must be one word and and start with a Capital letter")){
            response.sendRedirect("SignUpPageFail.jsp?username=" + username + "&email=" + email + "&name=" + name + "&surname=" + surname + 
                    "&securityAnsw=" + securityAnsw + "&message=" + message);
        }else if(message.equals("Surname can have multiple parts but each part must start with a capital")){
            response.sendRedirect("SignUpPageFail.jsp?username=" + username + "&email=" + email + "&name=" + name + "&surname=" + surname + 
                    "&securityAnsw=" + securityAnsw + "&message=" + message);
        }else if(message.equals("Thank you for signing up to our service " + name + " " + surname)){
            response.sendRedirect("SignUpPageConfirm.jsp?message=" + message);
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
