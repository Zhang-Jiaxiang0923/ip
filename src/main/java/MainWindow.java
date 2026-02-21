import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import sigma.Sigma;
import javafx.util.Duration;


public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Sigma sigma;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/Sigma.png"));
    private Image sigmaImage = new Image(this.getClass().getResourceAsStream("/images/User.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Sigma instance */
    public void setSigma(Sigma sigma) {
        this.sigma = sigma;
        dialogContainer.getChildren().add(
                DialogBox.getSigmaDialog(sigma.getWelcomeMessage(), sigmaImage)
        );
    }

    @FXML
    private void handleUserInput() {
        String input = userInput.getText().trim();
        if (input.isEmpty()) {
            return;
        }
        String response = sigma.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getSigmaDialog(response, sigmaImage)
        );
        userInput.clear();

        if (input.equalsIgnoreCase("bye")) {
            PauseTransition delay = new PauseTransition(Duration.millis(1500));
            delay.setOnFinished(e -> Platform.exit());
            delay.play();
        }
    }



}
