import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Chat App");

        // Create a layout and input fields
        javafx.scene.layout.VBox layout = new javafx.scene.layout.VBox(10);
        javafx.scene.control.TextField inputField = new javafx.scene.control.TextField();
        javafx.scene.control.Button sendButton = new javafx.scene.control.Button("Send");
        javafx.scene.control.Label messageLabel = new javafx.scene.control.Label();

        // Set up button action
        sendButton.setOnAction(e -> {
            String message = inputField.getText();
            messageLabel.setText("You: " + message);
            inputField.clear();
        });

        // Add components to layout
        layout.getChildren().addAll(inputField, sendButton, messageLabel);

        // Set up the scene and stage
        javafx.scene.Scene scene = new javafx.scene.Scene(layout, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

