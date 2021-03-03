package Controller;

import Model.Forum;
import Model.Subscriptions;
import Model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "SubscriptionManager", urlPatterns = {"/SubscriptionManager"})
public class SubscriptionManager extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Processes requests for both HTTP  <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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
        String forumId = request.getParameter("id_forum");
        String type = request.getParameter("type");
        List<Forum> listForum = new ArrayList<Forum>();
        try {

            HttpSession session = request.getSession();
            int userId = (Integer) session.getAttribute("userId");

            if (forumId != null && type != null && !"".equals(forumId) && !"".equals(type)) {
                int id_forum = Integer.parseInt(forumId);
                if (type.equalsIgnoreCase("unsubscribe")) {
                    Subscriptions subscriptions = new Subscriptions(userId, id_forum);
                    subscriptions.delete();
                } else if (type.equalsIgnoreCase("subscribe")) {
                    Subscriptions subscriptions = new Subscriptions();
                    subscriptions.setId_user(userId);
                    subscriptions.setId_forum(id_forum);
                    subscriptions.save();
                }
            }

            User user = new User(userId);
            user.LoadForumSubscriptions();
            listForum = user.getForumSubscriptions();
            request.setAttribute("listForum", listForum);
            if (type.equalsIgnoreCase("all")) {
                List<Forum> allForums = Forum.FindAll();
                request.setAttribute("listForum", allForums);
                List<Integer> subscriptIds = new ArrayList<Integer>();
                for (Forum forum : listForum) {
                    subscriptIds.add(forum.getId());
                }
                request.setAttribute("listSubscriptions", subscriptIds);
            }
            request.setAttribute("type", type);


            RequestDispatcher rd = request.getRequestDispatcher("subscriptionManager.jsp");
            rd.forward(request, response);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(SubscriptionManager.class.getName()).log(Level.SEVERE, null, ex);
        }

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
