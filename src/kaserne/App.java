package kaserne;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

public class App extends Application {

    DBConnection database = new DBConnection(); // DBConnection in der App nutzen

    @Override
    public void start(Stage primaryStage) {
        database.connectToDB();

        //Layout 1: Login
        Label loginLabel = new Label("Benutzer anmelden");
        TextField loginBenutzername = new TextField();
        loginBenutzername.setPromptText("Benutzername");
        PasswordField loginPasswort = new PasswordField();
        loginPasswort.setPromptText("Passwort");

        Button anmeldenButton = new Button("Anmelden");
        Button neuerAccountButton = new Button("Neuen Account erstellen");

        VBox loginLayout = new VBox(10, loginLabel, loginBenutzername, loginPasswort, anmeldenButton, neuerAccountButton);
        loginLayout.setPadding(new Insets(20));
        loginLayout.setAlignment(Pos.CENTER);

        Scene loginScene = new Scene(loginLayout, 400, 250);

        //Layout 2: Neuer Benutzer
                
        Label neuerBenutzerLabel = new Label("Neuen Benutzer erstellen");
        TextField neuerBenutzername = new TextField();
        neuerBenutzername.setPromptText("Benutzername");
        PasswordField neuesPasswort = new PasswordField();
        neuesPasswort.setPromptText("Passwort");
        Button speichernButton = new Button("Speichern");
        Button zurückButton = new Button("Zurück zum Login");

        VBox neuerBenutzerLayout = new VBox(10, neuerBenutzerLabel, neuerBenutzername, neuesPasswort, speichernButton, zurückButton);
        neuerBenutzerLayout.setPadding(new Insets(20));
        neuerBenutzerLayout.setAlignment(Pos.CENTER);

        Scene neuerBenutzerScene = new Scene(neuerBenutzerLayout, 400, 250);

        
        // Layout 3: Auswahl
        
        
        Label AuswahlLabel = new Label("Auswahl");
        Button KaserneButton = new Button("Kaserne");
 

        
        
        VBox AuswahlLayout = new VBox(KaserneButton);
        AuswahlLayout.setPadding(new Insets(20));
        AuswahlLayout.setAlignment(Pos.CENTER);
                Scene AuswahlScene = new Scene(AuswahlLayout, 400, 250);
        
        
        //Button Aktionen
        neuerAccountButton.setOnAction(e -> primaryStage.setScene(neuerBenutzerScene));
        zurückButton.setOnAction(e -> primaryStage.setScene(loginScene));

        speichernButton.setOnAction(e -> {
            String benutzer = neuerBenutzername.getText();
            String passwort = neuesPasswort.getText();

            if (!benutzer.isEmpty() && !passwort.isEmpty()) {
                database.benutzerErstellen(benutzer, passwort);
                System.out.println("Neuer Benutzer erstellt: " + benutzer);
                neuerBenutzername.clear();
                neuesPasswort.clear();
                primaryStage.setScene(loginScene);
            } else {
                System.out.println("Bitte Benutzername und Passwort eingeben!");
            }
        });

        anmeldenButton.setOnAction(e -> {
            String benutzer = loginBenutzername.getText();
            String passwort = loginPasswort.getText();

            if (database.benutzerLogin(benutzer, passwort)) {
                System.out.println("Login erfolgreich: " + benutzer);
            primaryStage.setScene(AuswahlScene);    
            } else {
                System.out.println("Benutzername oder Passwort falsch!");
            }

            loginBenutzername.clear();
            loginPasswort.clear();
        });

        //Stage Setup
        primaryStage.setTitle("Kaserne Verwaltung");
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }
}