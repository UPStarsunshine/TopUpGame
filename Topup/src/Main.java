import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Pos;
 
public class Main extends Application {
    private Game selectedGame;
    private String selectedNominal = null;
    private String selectedPayment = null;

    public Game getSelectedGame() {
        return selectedGame;
    }

    public void setSelectedGame(Game selectedGame) {
        this.selectedGame = selectedGame;
    }

    public String getSelectedNominal() {
        return selectedNominal;
    }

    public void setSelectedNominal(String selectedNominal) {
        this.selectedNominal = selectedNominal;
    }

    public String getSelectedPayment() {
        return selectedPayment;
    }

    public void setSelectedPayment(String selectedPayment) {
        this.selectedPayment = selectedPayment;
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            HBox header = new HBox(20);
            header.setAlignment(Pos.CENTER_LEFT);
            Image logoImage = new Image("file:///E:/Eclipse Project/Topup/src/application/logotopup.png");
            ImageView logo = new ImageView(logoImage);
            logo.setFitHeight(30);
            logo.setPreserveRatio(true);
            header.getChildren().addAll(logo);
            header.setStyle("-fx-padding: 10px; -fx-background-color: #86bedb;");

            VBox body = new VBox(20);
            body.setAlignment(Pos.CENTER);
            body.setStyle("-fx-padding: 20px;");

            HBox gameBox = new HBox(20);
            gameBox.setAlignment(Pos.CENTER);

            Button gameButton1 = new GameButton("Game 1", "file:///E:/Eclipse Project/Topup/src/application/logotopup.png", this);
            Button gameButton2 = new GameButton("Game 2", "file:///E:/Eclipse Project/Topup/src/application/logotopup.png", this);
            Button gameButton3 = new GameButton("Game 3", "file:///E:/Eclipse Project/Topup/src/application/logotopup.png", this);
            gameBox.getChildren().addAll(gameButton1, gameButton2, gameButton3);

            TextField userIdField = new TextField();
            userIdField.setPromptText("Masukkan User ID");

            HBox topupBox = new HBox(10);
            topupBox.setAlignment(Pos.CENTER);
            Button topup1 = new TopupButton("10000", this);
            Button topup2 = new TopupButton("50000", this);
            Button topup3 = new TopupButton("100000", this);
            topupBox.getChildren().addAll(topup1, topup2, topup3);

            HBox paymentBox = new HBox(10);
            paymentBox.setAlignment(Pos.CENTER);
            Button paymentQRIS = new PaymentButton("QRIS", this);
            Button paymentBank = new PaymentButton("Transfer Bank", this);
            Button paymentEmoney = new PaymentButton("Emoney", this);
            paymentBox.getChildren().addAll(paymentQRIS, paymentBank, paymentEmoney);

            Button confirmButton = new Button("Konfirmasi");

            body.getChildren().addAll(gameBox, userIdField, topupBox, paymentBox, confirmButton);

            BorderPane root = new BorderPane();
            root.setTop(header);
            root.setCenter(body);
            Scene scene = new Scene(root, 600, 500);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

            confirmButton.setOnAction(e -> {
                String userId = userIdField.getText();

                if (userId != null && !userId.isEmpty() && getSelectedGame() != null && getSelectedNominal() != null && getSelectedPayment() != null) {
                    if (getSelectedPayment().equals("Transfer Bank")) {
                        Alert bankAlert = new Alert(Alert.AlertType.INFORMATION);
                        bankAlert.setTitle("Informasi Bank");
                        bankAlert.setHeaderText("Detail Rekening Bank");
                        bankAlert.setContentText("BCA: 1234567890\nBNI: 0987654321\nMandiri: 1122334455");
                        bankAlert.showAndWait();
                    } else if (getSelectedPayment().equals("Emoney")) {
                        Alert emoneyAlert = new Alert(Alert.AlertType.INFORMATION);
                        emoneyAlert.setTitle("Informasi Emoney");
                        emoneyAlert.setHeaderText("Detail Akun Emoney");
                        emoneyAlert.setContentText("Gopay: 081234567890\nDana: 081298765432\nOvo: 081112223334");
                        emoneyAlert.showAndWait();
                    }

                    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmationAlert.setTitle("Konfirmasi");
                    confirmationAlert.setHeaderText("Konfirmasi Pembayaran");
                    confirmationAlert.setContentText("Apakah Anda yakin ingin melanjutkan?");
                    confirmationAlert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            Alert invoiceAlert = new Alert(Alert.AlertType.INFORMATION);
                            invoiceAlert.setTitle("Invoice");
                            invoiceAlert.setHeaderText("Pembayaran Sukses");
                            invoiceAlert.setContentText("User ID: " + userId + "\nGame: " + getSelectedGame().getName() +
                                    "\nNominal Topup: " + getSelectedNominal() + "\nMetode Pembayaran: " + getSelectedPayment());
                            invoiceAlert.show();
                        }
                    });
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText("Data Tidak Lengkap");
                    alert.setContentText("Pastikan semua data telah diisi.");
                    alert.showAndWait();
                }
            });


            primaryStage.setScene(scene);
            primaryStage.setTitle("Game Topup");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateBorders(HBox container, String selected) {
        for (javafx.scene.Node node : container.getChildren()) {
            Button button = (Button) node;
            if (button.getText().equals(selected)) {
                button.setStyle("-fx-border-color: #3498db; -fx-border-width: 2px; -fx-background-color: #eaf4ff;");
            } else {
                button.setStyle("-fx-background-color: #ecf0f1; -fx-border-color: #7f8c8d;");
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

abstract class ButtonBase extends Button {
    protected Main main;

    public ButtonBase(String text, Main main) {
        super(text);
        this.main = main;
        setStyle("-fx-background-color: #ecf0f1; -fx-border-color: #7f8c8d;");
    }

    public abstract void onButtonClick();
}

class GameButton extends ButtonBase {
    private String imagePath;

    public GameButton(String text, String imagePath, Main main) {
        super(text, main);
        this.imagePath = imagePath;
        Image image = new Image(imagePath);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(100);
        imageView.setPreserveRatio(true);
        setGraphic(imageView);

        setOnAction(e -> onButtonClick());
    }

    @Override
    public void onButtonClick() {
        main.setSelectedGame(new Game(getText(), imagePath));
        main.updateBorders((HBox) getParent(), getText());
    }
}

class TopupButton extends ButtonBase {
    public TopupButton(String text, Main main) {
        super(text, main);
        setOnAction(e -> onButtonClick());
    }

    @Override
    public void onButtonClick() {
        main.setSelectedNominal(getText());
        main.updateBorders((HBox) getParent(), getText());
    }
}

class PaymentButton extends ButtonBase {
    public PaymentButton(String text, Main main) {
        super(text, main);
        setOnAction(e -> onButtonClick());
    }

    @Override
    public void onButtonClick() {
        main.setSelectedPayment(getText());
        main.updateBorders((HBox) getParent(), getText());
    }
}

class Game {
    private String name;
    private String imagePath;

    public Game(String name, String imagePath) {
        this.name = name;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public String getImagePath() {
        return imagePath;
    }
} 