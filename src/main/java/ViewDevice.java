import javafx.scene.image.ImageView;
public interface ViewDevice {
    void printMethod(int x, int y, ImageView imageView);
    void swap(Creature c1, Creature c2);
    void clear(Creature c, Air air);
    void end();
}
