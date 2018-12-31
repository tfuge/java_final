# java_final
## 一、接口：
| 接口名       | 意义             |实现的类|
| ----------- | ---------------- | ------|
| AttackDevice| 生物进行攻击的设备 | Ground|
| MoveDevice  | 生物进行移动的设备 | Ground|
| DeadDevice  | 生物死亡的设备     | Ground|
| ViewDevice  | 监视地图变化的设备 |Graphic|
##  二、与生物相关的抽象类与类：
| （抽象）类名 | 意义     |夫类    | 子类|
| ----------- | ------- | ------|-----|
| Creature    | 一切存在 | null  | Air, GoodTeam, BadTeam|
| Air       | 空气，占位 | Creature| null|
| GoodTeam  | 正义阵营 | Creature|GourdBoys, Grandpa|
| BadTeam  | 邪恶阵营 |Creature|Snake, Scropion, Goblins|
| GourdBoys | 葫芦娃 | GoodTeam| null|
| Grandpa  | 老爷爷 | GoodTeam| null|
| Snake     | 蛇精     | BadTeam| null|
| Scropion  | 蝎子精 | BadTeam| null|
| Goblins  | 小喽啰 | BadTeam| null|

### 1、一些重要方法、属性
#### Creature:
      imageView:存放对象的图片的视。
      x, y:坐标。
####  GoodTeam, BadTeam:
      move, attack:移动、攻击等。
      hurt:受伤，接受对面阵营生物的attack信号。需要加锁，防止同时被多个生物攻击可能导致的多次被杀死问题。
      run:实现并发机制的方法。主要是存活状态下调用前进和攻击方法。内部lock部分防止同时多个生物移动造成多个生物占据同一个位置的问题。
      两个类方法很类似，主要是提供反射机制来协助判断敌我的作用。
####  GourdBoys:
      rank: 静态成员，协助识别葫芦娃座次。为了使用抽象类继承，从枚举类重构为普通类后增加的需要。
####  各个具体生物类：
      主要为了获取不同的ImageView对象。
### 2、并发
      实现过程中可能在一些额外的地方加了锁。
##  三、Ground类
### 1、构造器
      其中实完成了对地图的初始化工作。并为自身增加一个监视器。使用了设计模式。
### 2、moveMethod与swap
      主要区别是有没有加锁。
### 3、dead
      原来生物的位置用空气代替。
### 4、实现三个Device的目的
      为了分离不同的职能。提供给生物调用。生物还拥有额外的完整的Ground引用，为了加锁的便利。
### 5、测试
      一些地方加了assert。有点像C/C++的习惯。
 ## 四、Graphic类
### 1、构造器
      本类对象持有一个GridPane对象。除了初始化之外，将对于鼠标按键的监听写在这里。注意：这里有一些问题在于键盘监听总是识别。因此使用鼠标左键点击开始战斗，鼠标右键点击开始回放。
### 2、对于ViewDevice的实现
      使用GridPane增减children的方法来实现。
 ## 五、Main类
### 1、start方法
      对ground和graphic进行初始化。
### 2、fight方法
      建立线程池并开始多线程运行程序。
### 3、reply方法
      读取文件并开始回放。**有bug。**
 ## 六、战斗简介
    1、GoodTeam阵营有范围攻击，攻击力较低。BadTeam阵营为单体攻击，攻击力较高。目前数值下GoodTeam优势较大。
 ## 七、单元测试
    1、GraphicTest类测试swap方法。
      
