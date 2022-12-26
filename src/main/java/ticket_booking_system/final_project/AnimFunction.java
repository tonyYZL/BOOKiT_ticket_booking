package ticket_booking_system.final_project;

import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.util.Duration;

import static ticket_booking_system.final_project.PublicController.currentScreenIndicator;

public class AnimFunction {

    public static void moveTo(int y) {
        KeyValue value = new KeyValue(currentScreenIndicator.layoutYProperty(), y, new Interpolator() {
            @Override
            protected double curve(double t) {
                return (t == 1) ? 1.0 : 1 - Math.pow(2.0, -10 * t);
            }
        });
        KeyFrame move = new KeyFrame(Duration.millis(800), value);
        Timeline animation = new Timeline(move);
        animation.play();
    }

    public static void showTopPopup(Pane pane, HBox msg) {
        pane.getChildren().add(msg);
        msg.setLayoutX(302);
        msg.setLayoutY(-80);
        moveYTo(pane, msg, 30);
    }

    public static void moveYTo(Pane pane, HBox hBox, int y) {
        KeyValue value = new KeyValue(hBox.layoutYProperty(), y);
        KeyFrame move = new KeyFrame(Duration.millis(250), value);
        KeyFrame delay = new KeyFrame(Duration.millis(1500));

        Timeline animation = new Timeline(move, delay);
        animation.setAutoReverse(true);
        animation.setCycleCount(2);
        animation.play();
        animation.setOnFinished(e -> pane.getChildren().remove(hBox));
    }

    public static void arrowUp(SVGPath svgPath) {
        KeyValue rotate = new KeyValue(svgPath.rotateProperty(), -180, new Interpolator() {
            @Override
            protected double curve(double t) {
                return (t == 1) ? 1.0 : 1 - Math.pow(2.0, -10 * t);
            }
        });
        KeyFrame frame = new KeyFrame(Duration.millis(500), rotate);
        Timeline animation = new Timeline(frame);
        animation.play();
    }

    public static void arrowDown(SVGPath svgPath) {
        KeyValue rotate = new KeyValue(svgPath.rotateProperty(), 0, new Interpolator() {
            @Override
            protected double curve(double t) {
                return (t == 1) ? 1.0 : 1 - Math.pow(2.0, -10 * t);
            }
        });
        KeyFrame frame = new KeyFrame(Duration.millis(500), rotate);
        Timeline animation = new Timeline(frame);
        animation.play();
    }

    public static void mouseOverBackgroundChange(Pane pane) {
        final Animation animation = new Transition() {

            {
                setCycleDuration(Duration.millis(100));
                setInterpolator(Interpolator.EASE_OUT);
            }

            @Override
            protected void interpolate(double frac) {
                Color vColor = new Color(1, 1, 1, frac*0.6);
                pane.setBackground(new Background(new BackgroundFill(vColor, CornerRadii.EMPTY, Insets.EMPTY)));
            }
        };
        animation.play();
    }

    public static void mouseOutBackgroundChange(Pane pane) {
        final Animation animation = new Transition() {

            {
                setCycleDuration(Duration.millis(100));
                setInterpolator(Interpolator.EASE_OUT);
            }

            @Override
            protected void interpolate(double frac) {
                Color vColor = new Color(1, 1, 1, (1 - frac)*0.6);
                pane.setBackground(new Background(new BackgroundFill(vColor, CornerRadii.EMPTY, Insets.EMPTY)));
            }
        };
        animation.play();
    }

    public static void fadeIn(Pane pane) {
        KeyValue value = new KeyValue(pane.layoutXProperty(), 0, new Interpolator() {
            @Override
            protected double curve(double t) {
                return (t == 1) ? 1.0 : 1 - Math.pow(2.0, -10 * t);
            }
        });
        KeyFrame frame = new KeyFrame(Duration.millis(1000), value);
        Timeline animation = new Timeline(frame);
        animation.play();
    }

    public static void fadeOut(Pane pane) {
        KeyValue value2 = new KeyValue(pane.layoutXProperty(), -159, new Interpolator() {
            @Override
            protected double curve(double t) {
                return (t == 1) ? 1.0 : 1 - Math.pow(2.0, -10 * t);
            }
        });
        KeyFrame frame = new KeyFrame(Duration.millis(1000), value2);
        Timeline animation = new Timeline(frame);
        animation.play();
    }
}
