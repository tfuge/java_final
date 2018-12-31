import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public class GourdBoys extends GoodTeam {
    static int rank = 0;
    public GourdBoys(int x, int y)
    {
        super(x, y);
        this.rank ++;
    }
    @Override
    public void initImageView() {
        String url;
        switch(rank){
            case 0: url = "大娃.jpg";break;
            case 1: url = "二娃.jpg";break;
            case 2: url = "三娃.jpg";break;
            case 3: url = "四娃.jpg";break;
            case 4: url = "五娃.jpg";break;
            case 5: url = "六娃.jpg";break;
            case 6: url = "七娃.jpg";break;
            default:url = ""; assert (false);
        }
        Image image = new Image(url);
        imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitWidth(size);
        imageView.setFitHeight(size);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);
    }

}
