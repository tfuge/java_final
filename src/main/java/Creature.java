import javafx.scene.image.ImageView;
public abstract class Creature{
    final int size = 40;
    protected int x;
    protected int y;
    ImageView imageView;
    private ViewDevice viewDevice;
    public void print(int x, int y){
        viewDevice.printMethod(x, y, imageView);
    }
    abstract void initImageView();

    public void setViewDevice(ViewDevice viewDevice) {
        this.viewDevice = viewDevice;
    }

    Creature(int x, int y){
        this.x = x;
        this.y = y;
        initImageView();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }
}
