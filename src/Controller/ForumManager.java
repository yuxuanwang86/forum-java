package Controller;

import Model.Forum;
import Model.Message;
import Model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "ForumManager", urlPatterns = {"/ForumManager"})
public class ForumManager extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        if (session.getAttribute("login") == null) {
            try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<meta http-equiv='refresh' content='5; URL=connexion.html' />");
                out.println("<title> Non autorisé</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Vous n'êtes pas connecté ou vous n'êtes pas admin => redirigé vers la page connexion </h1>");
                out.println("</body>");
                out.println("</html>");
            }
        } else {
            String content = request.getParameter("Message Content");
            if (content == null || "".equals(content)) {
                if (!"admin".equalsIgnoreCase((String) session.getAttribute("role"))) {
                    try (PrintWriter out = response.getWriter()) {
                        out.println("<!DOCTYPE html>");
                        out.println("<html>");
                        out.println("<head>");
                        out.println("<meta http-equiv='refresh' content='5; URL=connexion.html' />");
                        out.println("<title> Non autorisé</title>");
                        out.println("</head>");
                        out.println("<body>");
                        out.println("<h1>Vous n'êtes pas connecté ou vous n'êtes pas admin => redirigé vers la page connexion </h1>");
                        out.println("</body>");
                        out.println("</html>");
                    }
                } else {
                    try {
                        String title = request.getParameter("Forum title");
                        String description = request.getParameter("Forum Description");

                        if (title == null || description == null) {
                            System.out.println("Champs non renseignés");
                            RequestDispatcher rd = request.getRequestDispatcher("forumManager.jsp");
                            rd.forward(request, response);
                        } else if ("".equals(title) || "".equals(description)) {
                            System.out.println("Champs vides");
                            RequestDispatcher rd = request.getRequestDispatcher("forumManager.jsp");
                            rd.forward(request, response);
                        } else {
                            int userId = (Integer) session.getAttribute("userId");
                            User u = User.FindByID(userId);
                            Forum forum = new Forum(title, description, u);
                            forum.save();
                            response.setContentType("text/html;charset=UTF-8");
                            try (PrintWriter out = response.getWriter()) {
                                out.println("<!DOCTYPE html>");
                                out.println("<html>");
                                out.println("<head>");
                                out.println("<meta http-equiv='refresh' content='3; URL=ForumManager' />");
                                out.println("<title>Un nouveau forum </title>");
                                out.println("</head>");
                                out.println("<body>");
                                out.println("<h1> Un nouveau forum est ajouté : </h1>");
                                out.println(forum.toString());
                                out.println("</body>");
                                out.println("</html>");

                            }
                        }
                    } catch (SQLException | ClassNotFoundException ex) {
                        Logger.getLogger(ForumManager.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                try {
                    String forumId = request.getParameter("id");
                    int id_forum = Integer.parseInt(forumId);
                    int userId = (Integer) session.getAttribute("userId");
                    Message message = new Message();
                    message.setContent(content);
                    message.setDestination(new Forum(id_forum));
                    message.setEditor(new User(userId));
                    message.save();
                    response.setContentType("text/html;charset=UTF-8");
                    try (PrintWriter out = response.getWriter()) {
                        out.println("<!DOCTYPE html>");
                        out.println("<html>");
                        out.println("<head>");
                        out.println("<meta http-equiv='refresh' content='3; URL=ForumManager?type=detail&id=" + forumId + "' />");
                        out.println("<title>Un nouveau message </title>");
                        out.println("</head>");
                        out.println("<body>");
                        out.println("<h1> Ajouter un nouveau message: </h1>");
                        out.println(message.getContent());
                        out.println("</body>");
                        out.println("</html>");

                    }
                } catch (NumberFormatException | ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(ForumManager.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
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
        response.setContentType("text/html;charset=UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        if (session.getAttribute("login") == null) {
            try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<meta http-equiv='refresh' content='5; URL=connexion.html' />");
                out.println("<title> Non autorisé</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Vous n'êtes pas connecté ou vous n'êtes pas admin => redirigé vers la page connexion </h1>");
                out.println("</body>");
                out.println("</html>");
            }
        } else {
            String id = request.getParameter("id");
            if (id == null || "".equals(id)) {// supprimer des forum
                List<Forum> listForum;
                try {
                    listForum = Forum.FindAll();
                    request.setAttribute("listForum", listForum);
                    RequestDispatcher rd = request.getRequestDispatcher("forumManager.jsp");
                    rd.forward(request, response);
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(ForumManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                String type = request.getParameter("type");

                try {
                    int forumId = Integer.parseInt(id);
                    Forum forum = new Forum(forumId);
                    if (type.equalsIgnoreCase("detail")) {
                        forum.LoadMessages();
                        List<Message> listMessage = forum.getMessages();
                        request.setAttribute("listMessage", listMessage);
                        request.setAttribute("forum", forum);

                        RequestDispatcher rd = request.getRequestDispatcher("messageManager.jsp");
                        rd.forward(request, response);
                    } else if (type.equalsIgnoreCase("del")) {
                        forum.delete();
                        try (PrintWriter out = response.getWriter()) {
                            out.println("<!DOCTYPE html>");
                            out.println("<html>");
                            out.println("<head>");
                            out.println("<meta http-equiv='refresh' content='3; URL=ForumManager' />");
                            out.println("<title> Supprimer le forum</title>");
                            out.println("</head>");
                            out.println("<body>");
                            out.println("<h1>Forum supprimé avec succès => redirigé vers la page gestion de forum </h1>");
                            out.println("</body>");
                            out.println("</html>");
                        }
                    }
                } catch (NumberFormatException | ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(ForumManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
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
