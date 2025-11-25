package kaserne;

public class Main {
    public static void main(String[] args) {
        DBConnection database = new DBConnection();
        javafx.application.Application.launch(App.class, args);
    }
}