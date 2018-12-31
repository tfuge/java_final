import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class Ground implements MoveDevice, DeadDevice, AttackDevice {
    private List<List<Creature>> ground;
    private ViewDevice viewDevice;
    PrintWriter writer;
    private int numberOfLivingGood, numberOfLivingBad;
    private int numberOfEscapedGood,numberOfEscapedBad;
    public void moveMethod(int x1, int y1, int x2, int y2){
        synchronized (ground.get(x2).get(y2)){
            viewDevice.swap(ground.get(x1).get(y1), ground.get(x2).get(y2));
            Creature temp = ground.get(x1).get(y1);
            ground.get(x1).set(y1, ground.get(x2).get(y2));
            ground.get(x2).set(y2, temp);
            ground.get(x2).get(y2).setX(x2);
            ground.get(x2).get(y2).setY(y2);
            ground.get(x1).get(y1).setX(x1);
            ground.get(x1).get(y1).setY(y1);
            if(y2 == 0 || y2 == 19){
                if(ground.get(x2).get(y2).getClass().getSuperclass().getName().equals("GoodTeam"))
                    GoodTeam.numberOfEscapes++;
                else
                    BadTeam.numberOfEscapes++;
            }
            try{
                writer.println("moveForward "+x1 + " " + y1+"\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public Boolean forwardable(Creature creature) {
        String type = creature.getClass().getSuperclass().getName();
        if (type.equals("GoodTeam")){
            synchronized (ground.get(creature.getX()).get(creature.getY()+1)){
                System.out.println(creature.getClass().getName()+ creature.getX()+", "+creature.getY()+ " : "+ ground.get(creature.getX()).get(creature.getY()+1).getClass().getName());
                return ground.get(creature.getX()).get(creature.getY()+1).getClass().getName().equals("Air");
            }

        }else{
            assert (type.equals("BadTeam"));
            synchronized (ground.get(creature.getX()).get(creature.getY()-1)){
                System.out.println(creature.getClass().getName()+ creature.getX()+", "+creature.getY()+ " : "+ ground.get(creature.getX()).get(creature.getY()-1).getClass().getName());
                return ground.get(creature.getX()).get(creature.getY()-1).getClass().getName().equals("Air");
            }
        }
    }

    public void swap(int x1, int y1, int x2, int y2){
        Creature temp = ground.get(x1).get(y1);
        ground.get(x1).set(y1, ground.get(x2).get(y2));
        ground.get(x2).set(y2, temp);
        ground.get(x2).get(y2).setX(x2);
        ground.get(x2).get(y2).setY(y2);
        ground.get(x1).get(y1).setX(x1);
        ground.get(x1).get(y1).setY(y1);
    }
    Ground(int length, int width, ViewDevice viewDevice){
        try{
            writer = new PrintWriter(new BufferedWriter(new FileWriter("temp")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.viewDevice = viewDevice;
        numberOfLivingBad = 8;
        numberOfLivingGood = 8;
        numberOfEscapedBad = 0;
        numberOfEscapedGood = 0;
        ground = new ArrayList<>(width);//Creature[width][length];
        for(int i=0;i<width;i++){
            ground.add(new ArrayList<>());
            for(int j=0;j<length;j++){
                ground.get(i).add(null);
            }
        }
        ArrayList<GourdBoys> gourdBoys = new ArrayList<>();
        gourdBoys.add(new GourdBoys(0, 3));
        gourdBoys.add(new GourdBoys(0, 4));
        gourdBoys.add(new GourdBoys(0, 5));
        gourdBoys.add(new GourdBoys(0, 6));
        gourdBoys.add(new GourdBoys(0, 7));
        gourdBoys.add(new GourdBoys(0, 8));
        gourdBoys.add(new GourdBoys(0, 9));
        for (GourdBoys gourdBoy : gourdBoys) {
            gourdBoy.setDevices(this);
            gourdBoy.setViewDevice(viewDevice);
        }
        ArrayList<Goblins> goblins= new ArrayList<>();
        for(int i=0;i<6;i++){
            goblins.add(new Goblins(0, 3 + i + gourdBoys.size()));
            goblins.get(i).setDevices(this);
            goblins.get(i).setViewDevice(viewDevice);
        }
        Scorpion scorpion = new Scorpion(0, 2);
        scorpion.setDevices(this);
        scorpion.setViewDevice(viewDevice);
        Snake snake = new Snake(0, 1);
        snake.setDevices(this);
        snake.setViewDevice(viewDevice);
//        snake.setViewDevice(this);
        Grandpa grandpa = new Grandpa(0, 0);
        grandpa.setDevices(this);
        grandpa.setViewDevice(viewDevice);
//        grandpa.setViewDevice(this);

        for(int i=3+gourdBoys.size()+goblins.size(); i<ground.size();i++){
            ground.get(0).set(i, new Air(0, i));
            ground.get(0).get(i).setViewDevice(viewDevice);
        }
        for(int i = 0; i < width; i++){
            for(int j = 0; j < length; j++){
                ground.get(i).set(j, new Air(i, j));
                ground.get(i).get(j).setViewDevice(viewDevice);
            }
        }
        ground.get(0).set(0, grandpa);
        ground.get(0).set(1, snake);
        ground.get(0).set(2, scorpion);
        for(int i = 0; i < gourdBoys.size(); i++){
            ground.get(0).set(3 + i, gourdBoys.get(i));
        }
        for(int i = 0; i < goblins.size(); i++){
            ground.get(0).set(3 + gourdBoys.size() + i, goblins.get(i));
        }

        randomArrange();

    }

    public void viewMethod() {
        for(int i = 0; i < ground.size(); i++){
            for(int j = 0; j < ground.size(); j++){
                ground.get(i).get(j).print(i, j);
            }
            //System.out.print("\n");
        }
    }

    public void show(){
        for(int i = 0; i < ground.size(); i++){
            for(int j = 0; j < ground.get(0).size(); j++){
                assert (ground.get(i).get(j).getX()==i);
                assert (ground.get(i).get(j).getY()==j);
                ground.get(i).get(j).print(i, j);
            }
            //System.out.print("\n");
        }
    }
    private void randomArrange (){
        Random random = new Random();
        for(int i = 0; i < ground.size(); i++){
            int posx = random.nextInt(ground.size());
            for(int j = 0; j < ground.get(i).size(); j++){
                int posy = random.nextInt(ground.get(i).size());
                swap(posx,posy,i,j);
            }
        }
    }
    public void fallIn(){
        for(int i = 0; i < ground.size(); i++){
            for(int j = 0; j < ground.get(0).size(); j++){
                String temp = ground.get(i).get(j).getClass().getName();
                if(temp.equals("Snake")){
                    ((BadTeam) ground.get(i).get(j)).move(ground.size()-1, ground.get(0).size()*2/3);
//                    ((BadTeam) ground.get(ground.size() - 1).get(ground.get(0).size() * 2 / 3)).setOrderDevice(formation);
                }
                if(temp.equals("Grandpa")){
                    ((GoodTeam) ground.get(i).get(j)).move(ground.size()-1, ground.get(0).size()/3);
//                    ((GoodTeam) ground.get(ground.size() - 1).get(ground.get(0).size() / 3)).setOrderDevice(formation);
                }
            }
        }

    }

    public int getLength() {
        return ground.size();
    }

    public int getWidth() {
        return ground.get(0).size();
    }

    public Creature getCreature(int i, int j){return ground.get(i).get(j);}

    public ArrayList<GoodTeam> getAllGood(){
        ArrayList<GoodTeam> goodTeam = new ArrayList<>();
        for(List<Creature> creatures : ground ){
            for(Creature creature : creatures){
                String TeamName = creature.getClass().getSuperclass().getName();
                if(TeamName.equals("GoodTeam")){
                    goodTeam.add((GoodTeam)creature);
                }
            }
        }
        return goodTeam;
    }
    public ArrayList<BadTeam> getAllBad(){
        ArrayList<BadTeam> badTeam = new ArrayList<>();
        for(List<Creature> creatures : ground ){
            for(Creature creature : creatures){
                String TeamName = creature.getClass().getSuperclass().getName();
                if(TeamName.equals("BadTeam")){
                    badTeam.add((BadTeam)creature);
                }
            }
        }
        return badTeam;
    }

    @Override
    public void dead(Creature c) {
        String type = c.getClass().getSuperclass().getName();
        if(type.equals("GoodTeam")){
            numberOfLivingGood--;
            System.out.println(c.getClass().getName() +" dead");
        }else{
            assert(type.equals("BadTeam"));
            numberOfLivingBad--;
            System.out.println(c.getClass().getName() +" dead");
        }
        Air air = new Air(c.getX(),c.getY());
        viewDevice.clear(c, air);
        ground.get(c.getX()).set(c.getY(),air);
//        if(numberOfLivingBad ==0){
//            System.out.println("Good Win");
//            viewDevice.end();
//            //System.exit(0);
//        }else if(numberOfLivingGood == 0){
//            System.out.println("Bad win");
//            viewDevice.end();
//            //System.exit(0);
//        }
    }

    @Override
    public synchronized void attackMethod(Creature creature, int attack) {
        try {
            writer.println("attack "+creature.getX()+" "+creature.getY());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String type = creature.getClass().getSuperclass().getName();
        System.out.println(creature.getClass().getName()+" attack");
        if(type.equals("GoodTeam")){
            Creature c1 = ground.get(creature.getX()).get(creature.getY()+1);
            if(c1.getClass().getSuperclass().getName().equals("BadTeam")){
                ((BadTeam)c1).hurt(attack);
            }
            Creature c2 = ground.get(creature.getX()-1).get(creature.getY()+1);
            if(c2.getClass().getSuperclass().getName().equals("BadTeam")){
                ((BadTeam)c2).hurt(attack);
            }
            Creature c3 = ground.get(creature.getX()+1).get(creature.getY()+1);
            if(c3.getClass().getSuperclass().getName().equals("BadTeam")){
                ((BadTeam)c3).hurt(attack);
            }
        }else{
            assert(type.equals("BadTeam"));
            Creature c1 = ground.get(creature.getX()).get(creature.getY()-1);
            if(c1.getClass().getSuperclass().getName().equals("GoodTeam")){
                ((GoodTeam)c1).hurt(attack);
            }
        }
    }

}
