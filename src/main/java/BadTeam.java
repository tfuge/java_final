import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class BadTeam extends Creature implements Runnable{
    public static int numberOfLives = 8;
    public static int numberOfEscapes = 0;
    Ground ground;
    private MoveDevice moveDevice;
    private DeadDevice deadDevice;
    private AttackDevice attackDevice;
    int HP = 100;
    private int attack = 20;
    private Lock lock = new ReentrantLock();
    Boolean dead;
    BadTeam(int x, int y) {
        super(x, y);
        dead = false;
    }

    public void move(int x, int y){
        moveDevice.moveMethod(this.x, this.y, x, y);
    }
    public void setDevices(Ground ground){
        this.moveDevice = ground;
        this.deadDevice = ground;
        this.attackDevice = ground;
        this.ground = ground;
    }
    public synchronized void hurt(int attack){
        System.out.println(this.getClass().getName()+" hurt: "+HP+" - "+attack);
        synchronized (ground.getAllGood()){
            HP -= attack;
            if(HP<=0) {
                numberOfLives--;
                deadDevice.dead(this);
            }
        }
    }
    public void attack(){
        attackDevice.attackMethod(this, this.attack);
    }
    public void moveForward(){moveDevice.moveMethod(this.x, this.y, this.x, this.y-1);}
    @Override
    public void run() {
        try{
            while(HP > 0 && y > 0){
                lock.lock();
                if (moveDevice.forwardable(this)){
                    moveForward();
                }
//                HP -= 10;
                else {
                    attack();
                }
                lock.unlock();
                Thread.sleep(1000);
            }
        }catch (Exception e){
            System.out.println(this.getClass().getName().toString()+" interrupted");
        }


    }
}
