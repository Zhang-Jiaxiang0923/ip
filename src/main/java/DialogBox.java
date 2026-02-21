import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;

public class DialogBox extends HBox {
    @FXML private Label dialog;
    @FXML private ImageView displayPicture;
    @FXML private Region spacer;

    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);

        // 1) 先把图做成“居中正方形裁剪”
        double w = img.getWidth();
        double h = img.getHeight();
        double side = Math.min(w, h);
        double x = (w - side) / 2.0;
        double y = (h - side) / 2.0;
        displayPicture.setViewport(new Rectangle2D(x, y, side, side));

        // 2) 再设置显示尺寸（80x80）
        displayPicture.setFitWidth(80);
        displayPicture.setFitHeight(80);
        displayPicture.setPreserveRatio(false); // 因为 viewport 已经是正方形了
        displayPicture.setSmooth(true);

        // 3) 圆形 clip（用 bounds 更稳）
        Circle clip = new Circle(40, 40, 40);
        displayPicture.setClip(clip);

        // spacer must grow to push to edges
        HBox.setHgrow(spacer, Priority.ALWAYS);
        spacer.setMinWidth(0);

        setMaxWidth(Double.MAX_VALUE);
    }

    public static DialogBox getSigmaDialog(String s, Image i) {
        DialogBox db = new DialogBox(s, i);
        // bot on LEFT: [avatar][bubble][spacer]
        db.setAlignment(Pos.TOP_LEFT);
        db.getStyleClass().add("sigma-row");
        return db;
    }

    public static DialogBox getUserDialog(String s, Image i) {
        DialogBox db = new DialogBox(s, i);
        // user on RIGHT: [spacer][bubble][avatar]
        Node avatar = db.displayPicture;
        Node bubble = db.dialog;
        Node sp = db.spacer;

        db.getChildren().setAll(sp, bubble, avatar);

        db.setAlignment(Pos.TOP_RIGHT);
        db.getStyleClass().add("user-row");
        return db;
    }
}