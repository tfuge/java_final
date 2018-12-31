import java.util.ArrayList;

enum FormationType {
    longSnakeArray, CraneWingArray, gooseFlyArray, yokeArray, squareArray
}

public class Formation{
    private int length,width;
    private Ground ground;
    Formation(int length, int width, Ground ground){
        this.width = width;
        this.length = length;
        this.ground=ground;
    }

    public void orderMethod(FormationType formationType) {
        //默认长蛇阵是葫芦娃，其他阵型是小喽啰，因此没有设定额外标志位。
        int count=0;
        ArrayList<Creature> creatures = new ArrayList<>();
//        Creature[] creatures = new Creature[8];
//        int[] x = new int[8];
//        int[] y = new int[8];
        if(formationType==FormationType.longSnakeArray){
            for(int i=0;i<width;i++) {
                for (int j = 0; j < length; j++) {
                    Creature creature = ground.getCreature(i, j);
                    String TeamName = creature.getClass().getSuperclass().getName();
                    if (TeamName.equals("GoodTeam")) {
//                        creatures[count] = creature;
//                        x[count]=i;
//                        y[count]=j;
//                        count++;
                        creatures.add(creature);
                        count++;
                    }
                }
            }
        }
        else{
            for(int i=0;i<width;i++) {
                for (int j = 0; j < length; j++) {
                    Creature creature = ground.getCreature(i, j);
                    String TeamName = creature.getClass().getSuperclass().getName();
                    if (TeamName.equals("BadTeam")) {
//                        creatures[count] = creature;
//                        x[count]=i;
//                        y[count]=j;
//                        count++;
                        creatures.add(creature);
                        count++;
                    }
                }
            }
        }
        switch (formationType){//除方円阵对怪物数量严格限制为7外，其他阵型均使用数学公式实现。阵型可扩展性较好。
            case longSnakeArray:
                for(int i=0;i<count;i++){
                    for(int j=0;j<count;j++){//对每个case中以j计数的循环，均为判断欲移动的位置与待移动的生物是否冲突
                        if(creatures.get(j).getX() ==width/2-count/2+i&& creatures.get(j).getY() ==length/3){
                            creatures.get(j).setX(creatures.get(i).getX());
                            creatures.get(j).setY(creatures.get(i).getY());break;
//                            x.set(j, x.get(i));
//                            y.set(j, y.get(i));break;
                        }
                    }
                    ground.swap(creatures.get(i).getX(),creatures.get(i).getY(),width/2-count/2+i,length/3);
                    //((GoodTeam) creatures.get(i)).move(width/2-count/2+i,length/3);
                }
                break;
            case gooseFlyArray:
                for(int i=0;i<count;i++){
                    for(int j=0;j<count;j++){
                        if(creatures.get(j).getX() ==width/2-count/2+i&& creatures.get(j).getY() ==2*length/3-count/2+i){
                            creatures.get(j).setX(creatures.get(i).getX());
                            creatures.get(j).setY(creatures.get(i).getY());break;
                        }
                    }
                    ground.swap(creatures.get(i).getX(),creatures.get(i).getY(),width/2-count/2+i,2*length/3-count/2+i);
                }
                break;
            case CraneWingArray:
                for(int i=0;i<count;i++){
                    for(int j=0;j<count;j++){
                        if(creatures.get(j).getX() ==width/2+(i+1)/2*(i%2*2-1)&& creatures.get(j).getY() ==2*length/3+(i+1)/2){
                            creatures.get(j).setX(creatures.get(i).getX());
                            creatures.get(j).setY(creatures.get(i).getY());break;
                        }
                    }
                    ground.swap(creatures.get(i).getX(),creatures.get(i).getY(),width/2+(i+1)/2*(i%2*2-1),2*length/3+(i+1)/2);
                }
                break;
            case squareArray://对count严格要求为8
                for(int i=0;i<count;i++){
                    switch(i){
                        case 0:
                            for(int j=0;j<count;j++){
                                if(creatures.get(j).getX() ==width/2&& creatures.get(j).getY() ==2*length/3){
                                    creatures.get(j).setX(creatures.get(i).getX());
                                    creatures.get(j).setY(creatures.get(i).getY());break;
                                }
                            }
                            ground.swap(creatures.get(i).getX(),creatures.get(i).getY(),width/2,2*length/3);
                            break;
                        case 1:
                            for(int j=0;j<count;j++){
                                if(creatures.get(j).getX() ==width/2+1&& creatures.get(j).getY() ==2*length/3+1){
                                    creatures.get(j).setX(creatures.get(i).getX());
                                    creatures.get(j).setY(creatures.get(i).getY());break;
                                }
                            }
                            ground.swap(creatures.get(i).getX(),creatures.get(i).getY(),width/2+1,2*length/3+1);
                            break;
                        case 2:
                            for(int j=0;j<count;j++){
                                if(creatures.get(j).getX() ==width/2+2&& creatures.get(j).getY() ==2*length/3+2){
                                    creatures.get(j).setX(creatures.get(i).getX());
                                    creatures.get(j).setY(creatures.get(i).getY());break;
                                }
                            }
                            ground.swap(creatures.get(i).getX(),creatures.get(i).getY(),width/2+2,2*length/3+2);
                            break;
                        case 3:
                            for(int j=0;j<count;j++){
                                if(creatures.get(j).getX() ==width/2+1&& creatures.get(j).getY() ==2*length/3+3){
                                    creatures.get(j).setX(creatures.get(i).getX());
                                    creatures.get(j).setY(creatures.get(i).getY());break;
                                }
                            }
                            ground.swap(creatures.get(i).getX(),creatures.get(i).getY(),width/2+1,2*length/3+3);
                            break;
                        case 4:
                            for(int j=0;j<count;j++){
                                if(creatures.get(j).getX() ==width/2&& creatures.get(j).getY() ==2*length/3+4){
                                    creatures.get(j).setX(creatures.get(i).getX());
                                    creatures.get(j).setY(creatures.get(i).getY());break;
                                }
                            }
                            ground.swap(creatures.get(i).getX(),creatures.get(i).getY(),width/2,2*length/3+4);
                            break;
                        case 5:
                            for(int j=0;j<count;j++){
                                if(creatures.get(j).getX() ==width/2-1&& creatures.get(j).getY() ==2*length/3+3){
                                    creatures.get(j).setX(creatures.get(i).getX());
                                    creatures.get(j).setY(creatures.get(i).getY());break;
                                }
                            }
                            ground.swap(creatures.get(i).getX(),creatures.get(i).getY(),width/2-1,2*length/3+3);
                            break;
                        case 6:
                            for(int j=0;j<count;j++){
                                if(creatures.get(j).getX() ==width/2-2&& creatures.get(j).getY() ==2*length/3+2){
                                    creatures.get(j).setX(creatures.get(i).getX());
                                    creatures.get(j).setY(creatures.get(i).getY());break;
                                }
                            }
                            ground.swap(creatures.get(i).getX(),creatures.get(i).getY(),width/2-2,2*length/3+2);
                            break;
                        case 7:
                            for(int j=0;j<count;j++){
                                if(creatures.get(j).getX() ==width/2-1&& creatures.get(j).getY() ==2*length/3+1){
                                    creatures.get(j).setX(creatures.get(i).getX());
                                    creatures.get(j).setY(creatures.get(i).getY());break;
                                }
                            }
                            ground.swap(creatures.get(i).getX(),creatures.get(i).getY(),width/2-1,2*length/3+1);
                            break;
                    }

                }
                break;
            case yokeArray:
                for(int i=0;i<count/2;i++){
                    for(int j=0;j<count;j++){
                        if(creatures.get(j).getX() ==width/2-count/2+2*i&& creatures.get(j).getY() ==2*length/3){
                            creatures.get(j).setX(creatures.get(i).getX());
                            creatures.get(j).setY(creatures.get(i).getY());break;
                        }
                    }
                    ground.swap(creatures.get(i).getX(),creatures.get(i).getY(),width/2-count/2+2*i,2*length/3);
                }
                for(int i=count/2;i<count;i++){
                    for(int j=0;j<count;j++){
                        if(creatures.get(j).getX() ==width/2-count*3/2+2*i+1&& creatures.get(j).getY() ==2*length/3+1){
                            creatures.get(j).setX(creatures.get(i).getX());
                            creatures.get(j).setY(creatures.get(i).getY());break;
                        }
                    }
                    ground.swap(creatures.get(i).getX(),creatures.get(i).getY(),width/2-count*3/2+2*i+1,2*length/3+1);
                }
                break;
        }
    }
}
