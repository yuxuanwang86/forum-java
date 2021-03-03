/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 *
 * @author lounis
 */
public abstract class ActiveRecordBase{

    protected int id;
    protected boolean _builtFromDB;

    protected abstract String _update();

    protected abstract String _insert();

    protected abstract String _delete();

    public void save() throws SQLException, IOException, ClassNotFoundException {
        Connection conn = MyConnectionClass.getInstance();
        Statement s = conn.createStatement();
        if (_builtFromDB) {
            System.out.print("Executing this command : " + _update() + "\n");
            s.executeUpdate(_update());
        } else {
            System.out.println("Executing this command: " + _insert() + "\n");

            // Récuoérer la valeur de clé artificielle (auto_incrément)
            s.executeLargeUpdate(_insert(), Statement.RETURN_GENERATED_KEYS);
            _builtFromDB = true;
            ResultSet r = s.getGeneratedKeys();
            while (r.next()) {
                id = r.getInt(1);
            }
        }
       
    }

    public void delete() throws IOException, ClassNotFoundException, SQLException {
        Connection conn = MyConnectionClass.getInstance();
        Statement s = conn.createStatement();
        if (_builtFromDB) {
            System.out.println("Executing this command: " + _delete() + "\n");
            s.executeUpdate(_delete());
           
            
        } else {
            System.out.println("Objet non persistant!");
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    

}
