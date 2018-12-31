import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.application.Platform;
import javafx.util.Duration;

public class Graphic implements ViewDevice {
    private GridPane pane;
    private Main main;
    Graphic(Main main){
        this.main = main;
        pane = new GridPane();
//        pane.setHgap(20);
//        pane.setVgap(20);
        pane.setGridLinesVisible(true);
//        pane.setOnKeyPressed(event -> {
//            System.out.println("Press");
//            if(event.getCode()== KeyCode.SPACE){
//                System.out.println("space");
//
//            }else if(event.getCode()==KeyCode.L){
//                System.out.println("Load");
//            }
//        });
        pane.setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.PRIMARY){
                System.out.println("left clicked");
                main.fight();
            }
            else if(event.getButton() == MouseButton.SECONDARY){
                System.out.println("right clicked");
                main.replay();
            }
        });
//        pane.setPadding(new Insets(5,0,0,0));

    }
    @Override
    public void printMethod(int x, int y, ImageView imageView) {
//        pane.add(imageView, y, x);
        GridPane.setRowIndex(imageView,x);
        GridPane.setColumnIndex(imageView, y);
        pane.getChildren().add(imageView);
//        System.out.println(pane.getRowCount() + " " + pane.getColumnCount()+" "+x+" "+y);
    }

    @Override
    public void swap(Creature c1, Creature c2) {

        Platform.runLater(() -> {
            synchronized (c2){
                pane.getChildren().removeAll(c1.imageView,c2.imageView);
                printMethod(c1.getX(),c1.getY(),c2.imageView);
                printMethod(c2.getX(),c2.getY(),c1.imageView);
                System.out.println(c1.getX()+" "+c1.getY()+" "+c2.getX()+" "+c2.getY());
            }

        });
    }

    @Override
    public synchronized void clear(Creature c, Air air) {
        Platform.runLater(() -> {
            pane.getChildren().remove(c.imageView);
            synchronized (pane.getChildren()){
                printMethod(c.getX(),c.getY(),air.imageView);
            }
        });
    }

    @Override
    public void end() {
        main.end();
    }

    public GridPane getPane() {
        return pane;
    }
}
