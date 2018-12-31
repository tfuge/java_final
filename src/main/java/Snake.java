import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public class Snake extends BadTeam {
    Snake(int x, int y) {
        super(x, y);
    }

    @Override
    public void initImageView() {
        Image image = new Image("蛇精.jpg");
        imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitWidth(size);
        imageView.setFitHeight(size);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);
    }
}
