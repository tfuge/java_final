import javafx.collections.ObservableList;
import org.junit.Before;
import org.junit.Test;
import javafx.scene.Node;
import static org.junit.Assert.*;

public class GraphicTest {
    Graphic graphic;
    Ground ground;
    Main main;
    ObservableList<Node> list;
    @Before
    public void init(){
        this.main = new Main();
        this.graphic = new Graphic(main);
        this.ground = new Ground(20, 15, graphic);
        this.list = graphic.getPane().getChildren();

    }
    @Test
    public void testChildren() throws Exception{
        graphic.swap(ground.getCreature(1, 2), ground.getCreature(3, 4));
        assertArrayEquals(this.list.sorted().toArray(), graphic.getPane().getChildren().sorted().toArray());
    }
}
