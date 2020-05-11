package Server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {


    public static void main(String[] args) throws IOException, InterruptedException {
        new Controller(3000,2);
    }

    @Override
    public void start(Stage primarystage) throws IOException {
        primarystage.setTitle("Game");
        Parent root = FXMLLoader.load(getClass().getResource("game.fxml"));
        primarystage.setScene(new Scene(root, 500, 400));
        primarystage.show();
    }
}
