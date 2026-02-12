import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sigma.Sigma;

public class Main extends Application {
    private static final Path target = Paths.get(System.getProperty("user.dir"))
            .resolve(Paths.get("data", "Sigma.txt"));
    private final Sigma sigma = new Sigma(target);


    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setSigma(sigma);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
