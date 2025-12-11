package kaserne;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;

public class App extends Application {

    DBConnection database = new DBConnection();

    @Override
    public void start(Stage primaryStage) {

        database.connectToDB();


        
        
        
        
        
        
        
        // --- Layout 1: Login ---
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

        // --- Layout 2: Neuer Benutzer ---
        Label neuerBenutzerLabel = new Label("Neuen Benutzer erstellen");
        TextField neuerBenutzername = new TextField();
        neuerBenutzername.setPromptText("Benutzername");
        PasswordField neuesPasswort = new PasswordField();
        neuesPasswort.setPromptText("Passwort");

        Button speichernButton = new Button("Speichern");
        Button zurückLoginButton = new Button("Zurück zum Login");

        VBox neuerBenutzerLayout = new VBox(10, neuerBenutzerLabel, neuerBenutzername, neuesPasswort, speichernButton, zurückLoginButton);
        neuerBenutzerLayout.setPadding(new Insets(20));
        neuerBenutzerLayout.setAlignment(Pos.CENTER);

        Scene neuerBenutzerScene = new Scene(neuerBenutzerLayout, 400, 250);

        // --- Layout 3: Auswahl ---
        Button KaserneButton = new Button("Kasernen");
        Button SoldatenButton = new Button("Soldaten");
        Button AbteilungenButton = new Button("Abteilungen");
        Button BodenfahrzeugeButton = new Button("Bodenfahrzeuge");
        Button AusruestungButton = new Button("Ausrüstung");
        Button LuftfahrzeugButton = new Button("Luftfahrzeuge");
        Button DienstplanButton = new Button("Dienstpläne");
        Button AbfragenButton = new Button("Standort Abfragen");
        Button AbmeldenButton = new Button("Abmelden");

        VBox AuswahlLayout = new VBox(10, KaserneButton, SoldatenButton, AbteilungenButton,
                BodenfahrzeugeButton, AusruestungButton, DienstplanButton, LuftfahrzeugButton, AbfragenButton,AbmeldenButton);
        AuswahlLayout.setPadding(new Insets(20));
        AuswahlLayout.setAlignment(Pos.CENTER);

        Scene AuswahlScene = new Scene(AuswahlLayout, 400, 400);

        // --- Layout 4: TableView direkt editierbar ---
        TableView<ObservableList<String>> table = new TableView<>();
        table.setEditable(true);


        Button zurückZuAuswahl = new Button("Zurück");

        VBox tabelleLayout = new VBox(10, table, new HBox(10,zurückZuAuswahl));
        tabelleLayout.setPadding(new Insets(20));
        tabelleLayout.setAlignment(Pos.CENTER);

        Scene tableScene = new Scene(tabelleLayout, 800, 500);

        // --- Layout 5: Abfragen ---
        Label StandortAbrageLabel = new Label("Geben sie ein Standort an um die Soldaten abzufragen");
        TextField AbfrageText = new TextField();
        AbfrageText.setPromptText("Standort eingeben...");
        Button AbfrageSuchenButton = new Button("Suchen");
        Button AbfrageZurückButton = new Button("Zurück");

        VBox AbfragenLayout = new VBox(10,StandortAbrageLabel, AbfrageText, AbfrageSuchenButton,AbfrageZurückButton);
        AbfragenLayout.setPadding(new Insets(20));
        AbfragenLayout.setAlignment(Pos.CENTER);

        Scene AbfragenScene = new Scene(AbfragenLayout, 400, 250);

        // --- Aktionen ---

        // Login
        anmeldenButton.setOnAction(e -> {
            if (database.benutzerLogin(loginBenutzername.getText(), loginPasswort.getText())) {
                primaryStage.setScene(AuswahlScene);
            } else {
                System.out.println("Login fehlgeschlagen!");
            }
        });

        neuerAccountButton.setOnAction(e -> primaryStage.setScene(neuerBenutzerScene));
        zurückLoginButton.setOnAction(e -> primaryStage.setScene(loginScene));
        AbmeldenButton.setOnAction(e -> primaryStage.setScene(loginScene));
        AbfrageZurückButton.setOnAction(e -> primaryStage.setScene(AuswahlScene));
        zurückZuAuswahl.setOnAction(e -> primaryStage.setScene(AuswahlScene));          
        
        speichernButton.setOnAction(e -> {
            if (!neuerBenutzername.getText().isEmpty() && !neuesPasswort.getText().isEmpty()) {
                database.benutzerErstellen(neuerBenutzername.getText(), neuesPasswort.getText());
                primaryStage.setScene(loginScene);
            }
        });
   

        // Tabellen-Buttons
        KaserneButton.setOnAction(e -> { zeigeTabelle("Kasernen", table); primaryStage.setScene(tableScene); });
        SoldatenButton.setOnAction(e -> { zeigeTabelle("Soldaten", table); primaryStage.setScene(tableScene); });
        AbteilungenButton.setOnAction(e -> { zeigeTabelle("Abteilungen", table); primaryStage.setScene(tableScene); });
        BodenfahrzeugeButton.setOnAction(e -> { zeigeTabelle("Bodenfahrzeuge", table); primaryStage.setScene(tableScene); });
        AusruestungButton.setOnAction(e -> { zeigeTabelle("Ausruestung", table); primaryStage.setScene(tableScene); });
        DienstplanButton.setOnAction(e -> { zeigeTabelle("Dienstplaene", table); primaryStage.setScene(tableScene); });
        LuftfahrzeugButton.setOnAction(e -> { zeigeTabelle("Luftfahrzeuge", table); primaryStage.setScene(tableScene); });

        AbfragenButton.setOnAction(e -> primaryStage.setScene(AbfragenScene));

        // Abfrage Standorte
        AbfrageSuchenButton.setOnAction(e -> {
            ObservableList<ObservableList<String>> daten = database.StandortSoldaten(AbfrageText.getText());
            table.getItems().clear();
            table.getColumns().clear();

            if (!daten.isEmpty()) {
                String[] spaltenNamen = {"Standort", "Abteilung", "Vorname", "Nachname", "Dienstgrad"};
                for (int j = 0; j < spaltenNamen.length; j++) {
                    final int idx = j;
                    TableColumn<ObservableList<String>, String> column = new TableColumn<>(spaltenNamen[j]);
                    column.setCellFactory(TextFieldTableCell.forTableColumn());
                    column.setOnEditCommit(event -> {
                        ObservableList<String> row = event.getRowValue();
                        row.set(idx, event.getNewValue());
                        database.aktualisiereDatensatz("Soldaten", "ID", row.get(0), row);
                    });
                    column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(idx)));
                    table.getColumns().add(column);
                }
                table.setItems(daten);
            }
            primaryStage.setScene(tableScene);
        });

loginScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
neuerBenutzerScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
AuswahlScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
tableScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
AbfragenScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        
        
        
        
        
        primaryStage.setTitle("Kaserne Verwaltung");
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    // --- Tabelle editierbar anzeigen ---
    private void zeigeTabelle(String tabellenName, TableView<ObservableList<String>> table) {

        table.getItems().clear();
        table.getColumns().clear();

        ObservableList<ObservableList<String>> daten = database.ladeTabelle(tabellenName);
        ObservableList<String> spaltenNamen = database.ladeSpaltennamen(tabellenName);

        for (int i = 0; i < spaltenNamen.size(); i++) {
            final int colIndex = i;
            TableColumn<ObservableList<String>, String> col = new TableColumn<>(spaltenNamen.get(i));
            col.setCellFactory(TextFieldTableCell.forTableColumn());
            col.setOnEditCommit(event -> {
                ObservableList<String> row = event.getRowValue();
                row.set(colIndex, event.getNewValue());
                database.aktualisiereDatensatz(tabellenName, spaltenNamen.get(0), row.get(0), row);
            });
            col.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(colIndex)));
            table.getColumns().add(col);
        }

        table.setItems(daten);
    }
       
    
    
    
    
}