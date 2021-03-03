/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lounis
 */
public class Forum extends ActiveRecordBase {

    private String title;
    private String description;
    private List<Message> messages;
    private static String _query = "select * from forum"; // for findAll static Method
    private User owner;

    /**
     *
     * @return
     */
    public List<Message> getMessages() {
        return messages;
    }

    /**
     *
     * @param messages
     */
    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Forum(String titre, String description, User u) {
        this.messages = new ArrayList<Message>();
        this.title = titre;
        this.description = description;
        this.owner = u;
    }

    public Forum(ResultSet res) throws SQLException, IOException, ClassNotFoundException {
        this.id = res.getInt("id");
        this.title = res.getString(2);
        this.owner = new User(res.getInt(3));
        this.description = res.getString(4);
    }

    public Forum() {
        this.messages = new ArrayList<Message>();
    }

    public Forum(int id) throws SQLException, IOException, ClassNotFoundException {
        Connection conn = MyConnectionClass.getInstance();
        String select_query = "select * from `db_sr03`.`forum` where `id` = '" + id + "';";
        Statement sql = null;
        sql = conn.createStatement();
        ResultSet res = sql.executeQuery(select_query);
        if (res.next()) {
            this.id = res.getInt("id");
            this.title = res.getString(2);
            this.owner = new User(res.getInt(3));
            this.description = res.getString(4);
            _builtFromDB = true;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Message> getFilDiscussion(String choix) {
        if ("all".equalsIgnoreCase(choix)) {
            return this.messages;
        }
        //ToDo il faut traiter d'autres choix.
        return null;
    }

    // DB access method
    @Override
    protected String _delete() {
        return "DELETE FROM `db_sr03`.`forum` WHERE (`id` = '" + id + "');";
    }

    @Override
    protected String _insert() {
        return "INSERT INTO `db_sr03`.`forum` (`title`, `owner`,`description`) "
                + "VALUES ('" + title + "', '" + owner.getId() + "', '"+ description +"');";
    }

    @Override
    protected String _update() {
        return "UPDATE `db_sr03`.`forum` SET `title` = '" + title + "', "
                + "`owner`='" + owner.getId() + "', `description` = '"+description+"'   WHERE (`id` = '" + id + "');";
    }

    public void LoadMessages() throws IOException, ClassNotFoundException, SQLException {
        this.messages = new ArrayList<Message>();
        Connection conn = MyConnectionClass.getInstance();
        String select_query = "select * from `db_sr03`.`message` where destination = ?";
        PreparedStatement sql = null;
        sql = conn.prepareStatement(select_query);
        sql.setInt(1, this.id);
        ResultSet res = sql.executeQuery();
        while (res.next()) {
            Message newMessage = new Message(res);
            this.messages.add(newMessage);
        }
    }

    public void addMessage() {

    }

    public static List<Forum> FindAll() throws IOException, ClassNotFoundException, SQLException {
        List<Forum> listForum = new ArrayList<Forum>();
        Connection conn = MyConnectionClass.getInstance();
        Statement sql = conn.createStatement();
        ResultSet res = sql.executeQuery(_query);
        while (res.next()) {
            Forum newForum = new Forum(res);
            listForum.add(newForum);
        }
        return listForum;
    }

    @Override
    public String toString() {
        return "Forum{" + "title=" + title + ", description=" + description + ""
                + ", owner=" + owner.getLogin() + '}';
    }
}
