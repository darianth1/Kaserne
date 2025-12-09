package kaserne;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DBConnection {
    // Pfad zu deiner SQLite-Datei
    String url = "jdbc:sqlite:P:\\\\IFSY12\\\\sqllite\\\\Kaserne\\\\Datenbank_Kaserne.db";

    public void connectToDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection connection = DriverManager.getConnection(url)) {
                System.out.println("Verbindung zur Datenbank erfolgreich hergestellt!");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC-Treiber nicht gefunden.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Fehler bei Verbindung: " + e.getMessage());
        }
    }

    public void benutzerErstellen(String benutzername, String passwort) {
        String sql = "INSERT INTO Benutzer (Benutzername, Passwort) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, benutzername);
            pstmt.setString(2, passwort);

            int rows = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
  public ObservableList<ObservableList<String>> ladeTabelle(String tabellenName) {
    ObservableList<ObservableList<String>> daten = FXCollections.observableArrayList();
    String sql = "SELECT * FROM " + tabellenName;

    try (Connection conn = DriverManager.getConnection(url);
         PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {

        int spalten = rs.getMetaData().getColumnCount();

        while (rs.next()) {
            ObservableList<String> zeile = FXCollections.observableArrayList();
            for (int i = 1; i <= spalten; i++) {
                zeile.add(rs.getString(i));
            }
            daten.add(zeile);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return daten;
}
  
 public ObservableList<String> ladeSpaltennamen(String tabellenName) {
    ObservableList<String> spaltenNamen = FXCollections.observableArrayList();

    String sql = "PRAGMA table_info(" + tabellenName + ")";

    try (Connection conn = DriverManager.getConnection(url);
         PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {

        while (rs.next()) {
            spaltenNamen.add(rs.getString("name"));
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return spaltenNamen;
}
  
  
  
  
    
    
    public boolean benutzerLogin(String benutzername, String passwort) {
        String sql = "SELECT 1 FROM Benutzer WHERE Benutzername = ? AND Passwort = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, benutzername);
            pstmt.setString(2, passwort);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // true, wenn Benutzer existiert
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }




public ObservableList<ObservableList<String>> StandortSoldaten(String standort) {
    ObservableList<ObservableList<String>> daten = FXCollections.observableArrayList();

    String sql = "SELECT k.Standort, a.Name AS Abteilungsname, s.Vorname, s.Nachname, s.Dienstgrad " +
                 "FROM Kasernen k " +
                 "JOIN Abteilungen a ON a.Kaserne_ID = k.Kaserne_ID " +
                 "JOIN Soldaten s ON s.Abteilung_ID = a.Abteilung_ID " +
                 "WHERE k.Standort = ? " +
                 "ORDER BY a.Name, s.Nachname";

    try (Connection conn = DriverManager.getConnection(url);
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, standort);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            ObservableList<String> row = FXCollections.observableArrayList();
            row.add(rs.getString("Standort"));
            row.add(rs.getString("Abteilungsname"));
            row.add(rs.getString("Vorname"));
            row.add(rs.getString("Nachname"));
            row.add(rs.getString("Dienstgrad"));
            daten.add(row);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return daten;
}

//Test
public void aktualisiereDatensatz(String tabelle, String idSpalte, String id, ObservableList<String> neueWerte) {
    try (Connection conn = DriverManager.getConnection(url)) {

        ObservableList<String> spalten = ladeSpaltennamen(tabelle);

        StringBuilder set = new StringBuilder();
        for (int i = 1; i < spalten.size(); i++) {
            set.append(spalten.get(i)).append("=?");
            if (i < spalten.size() - 1) set.append(", ");
        }

        String frage = "UPDATE " + tabelle + " SET " + set + " WHERE " + idSpalte + "=?";

        PreparedStatement stmt = conn.prepareStatement(frage);

        int index = 1;
        for (int i = 1; i < neueWerte.size(); i++)
            stmt.setString(index++, neueWerte.get(i));

        stmt.setString(index, id);
        stmt.executeUpdate();

    } catch (Exception e) { e.printStackTrace(); }
}



}