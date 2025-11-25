package kaserne;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

       
public class DBConnection {
  String url = "jdbc:sqlite:P:/IFSY12/sqllite/localdb/schueler.db";   
  Connection connection = null;
    public void connectToDB() {

 
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
            System.out.println("Verbindung zur Datenbank erfolgreich hergestellt!");
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC-Treiber nicht gefunden.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }
}