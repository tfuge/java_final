import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;
//    Image image = new Image(filePath);
//            imageView = new ImageView();
//            imageView.setImage(image);
//            imageView.setFitWidth(40);
//            imageView.setPreserveRatio(true);
//            imageView.setSmooth(true);
//            imageView.setCache(true);

public class Main extends Application{


    private Ground ground;
    private Graphic graphic;
    private ExecutorService exec;
    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("project.fxml"));
        this.graphic = new Graphic(this);
        this.ground = new Ground(20,15, graphic);
        Formation formation = new Formation(ground.getWidth(),ground.getLength(),ground);
        System.out.println("初始化：");
        formation.orderMethod(FormationType.gooseFlyArray);
        formation.orderMethod(FormationType.longSnakeArray);
        ground.show();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(graphic.getPane()));
        primaryStage.show();
    }

    public void fight(){
        exec = Executors.newCachedThreadPool();
        ArrayList<BadTeam> badTeam = ground.getAllBad();
        ArrayList<GoodTeam> goodTeam = ground.getAllGood();
        for(BadTeam bad : badTeam){
            exec.execute(bad);
        }
        for(GoodTeam good : goodTeam){
            exec.execute(good);
        }
        exec.shutdown();
        System.out.println(GoodTeam.numberOfLives+" "+BadTeam.numberOfLives);
        if (GoodTeam.numberOfLives>BadTeam.numberOfLives){
            System.out.println("Good win");
        }else if(GoodTeam.numberOfLives<BadTeam.numberOfLives){
            System.out.println("Bad win");
        }else{
            System.out.println("tie");
        }
        try {
            ground.writer.flush();
            ground.writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void replay(){
        GourdBoys.rank = 0;
        GoodTeam.numberOfLives = 8;
        BadTeam.numberOfLives = 8;
        this.ground = new Ground(20,15, graphic);
        Formation formation = new Formation(ground.getWidth(),ground.getLength(),ground);
        System.out.println("初始化：");
        formation.orderMethod(FormationType.gooseFlyArray);
        formation.orderMethod(FormationType.longSnakeArray);
        ground.show();
        BufferedReader reader;
        ArrayList<String> array = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(new File("temp")));
            String temp;
            while ((temp = reader.readLine())!=null){
                array.add(temp);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(String s : array){
            String[] tempArray = s.split(" ");
            if(tempArray[0].equals("moveForward")){
                int x1 = Integer.parseInt(tempArray[1]);
                int y1 = Integer.parseInt(tempArray[2]);
                if(ground.getCreature(x1, y1).getClass().getSuperclass().getName()=="GoodTeam"){
                    ((GoodTeam)ground.getCreature(x1, y1)).moveForward();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else{
                    assert (ground.getCreature(x1, y1).getClass().getSuperclass().getName()=="BadTeam");
                    ((BadTeam)ground.getCreature(x1, y1)).moveForward();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }else {
                assert (tempArray[0].equals("attack"));
                int x1 = Integer.parseInt(tempArray[1]);
                int y1 = Integer.parseInt(tempArray[2]);
                if(ground.getCreature(x1, y1).getClass().getSuperclass().getName()=="GoodTeam"){
                    ((GoodTeam)ground.getCreature(x1, y1)).attack();
                }else{
                    assert (ground.getCreature(x1, y1).getClass().getSuperclass().getName()=="BadTeam");
                    ((BadTeam)ground.getCreature(x1, y1)).attack();
                }
            }
        }
    }

    public synchronized void end(){
        exec.shutdown();

//        synchronized (exec.shutdownNow()){
//
//        }
//        try{
//            Thread.sleep(1000);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }
    public static void main(String[] args) {
        launch(args);
    }

}
