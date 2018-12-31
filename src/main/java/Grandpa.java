import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public class Grandpa extends GoodTeam {
    Grandpa(int x, int y) {
        super(x, y);
    }

    @Override
    public void initImageView() {
        Image image = new Image("老爷爷.jpg");
        imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitWidth(size);
        imageView.setFitHeight(size);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);
    }

}
