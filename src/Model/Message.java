/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.IOException;
import java.sql.*;
import java.util.Date;
import java.util.List;

/**
 *
 * @author lounis
 */
public class Message extends ActiveRecordBase{

    private String content;
    private Date datePublication;
    private User editor;
    private Forum destination; 

    public Forum getDestination() {
        return destination;
    }

    public void setDestination(Forum destination) {
        this.destination = destination;
    }

    /*
    java.text.SimpleDateFormat sdf = 
     new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    String currentTime = sdf.format(dt);
     */
    public Message() {
        _builtFromDB=false;
        this.datePublication = new Date();

    }

    public Message(String contenu, User editeur) {
        this.content = contenu;
        this.datePublication = new Date();
        this.editor = editeur;
    }

    public Message(ResultSet res) throws SQLException, IOException, ClassNotFoundException {
        this.id = res.getInt("id");
        this.content = res.getString(2);
        this.editor = new User(res.getInt(3));
        this.destination = new Forum(res.getInt(4));
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDatePublication() {
        return datePublication;
    }

    public void setDatePublication(Date datePublication) {
        this.datePublication = datePublication;
    }

    public User getEditor() {
        return editor;
    }

    public void setEditor(User editor) {
        this.editor = editor;
    }


//DB access methods
    
    @Override
    protected String _delete() {
        return "DELETE FROM `db_sr03`.`message` WHERE (`id` = '"+id+"');"; 
    }

    @Override
    protected String _insert() {
        return "INSERT INTO `db_sr03`.`message` (`content`, `editor`, `destination`) "
                + "VALUES ('"+content+"', '"+editor.getId()+"', '"+destination.getId()+"');";
    }

    @Override
    protected String _update() {
        return "update`db_sr03`.`message` set  `content`='"+content+"', "
                + "`editor`='"+editor.getId()+"', `destination`='"+destination.getId()
                +"'  WHERE (`id` = '"+id+"');";
    }
    
    public static Message FindbyId(int id){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static List<Message> FindbyForum(int idForum){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static List<Message> FindbyUser(int idUser){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void DeleteMessagesByUser(int idUser) throws IOException, ClassNotFoundException, SQLException {
        Connection conn = MyConnectionClass.getInstance();
        String update_query = "update `db_sr03`.`message` set editor = 1 where editor = ?";
        PreparedStatement sql = null;
        sql = conn.prepareStatement(update_query);
        sql.setInt(1, idUser);
        boolean flag = sql.execute();
    }
}
