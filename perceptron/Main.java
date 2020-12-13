package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Optional;


public class Main extends Application {

    Canvas canvas;
    Neuron p = new Neuron();

    @Override
    public void start(Stage primaryStage) {

        canvas = new Canvas(300, 300);

        final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.setStroke(Color.RED);
        graphicsContext.setLineWidth(3);
        graphicsContext.fill();

        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED,
                new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent event) {
                        graphicsContext.beginPath();
                        graphicsContext.moveTo(event.getX(), event.getY());
                        graphicsContext.stroke();
                    }
                });

        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent event) {
                        graphicsContext.lineTo(event.getX(), event.getY());
                        graphicsContext.stroke();
                    }
                });

        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED,
                new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent event) {

                    }
                });

        StackPane root = new StackPane();
        VBox vBox = new VBox();
        vBox.getChildren().addAll(canvas, initButtons());
        root.getChildren().addAll(vBox);
        Scene scene = new Scene(root, 300, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public HBox initButtons() {
        Button guessTry = new Button("Что это?");
        guessTry.setPrefSize(300,100);

        guessTry.setOnMouseClicked(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Обучение нейрона");
            WritableImage image = canvas.snapshot(null, null);
            Utils.scaleImage(image);
            canvas.getGraphicsContext2D().drawImage(image, 0, 0);
            double value = p.sum(image);

            String result = "A";
            if (value == 1) {
                result = "B";
            }
            alert.setHeaderText("Это " + result + "?");

            // option != null.
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get() == null) {

            } else if (option.get() == ButtonType.OK) {
            } else if (option.get() == ButtonType.CANCEL) {
                double delta = (result.equals("A") ? 1 : 0) - value;
                System.out.println(delta);
                p.learnNeuron(delta);
            } else {

            }
            canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        });

        HBox hBox = new HBox();
        hBox.getChildren().addAll(guessTry);
        return hBox;
    }


    public static void main(String[] args) {
        launch(args);
    }
}