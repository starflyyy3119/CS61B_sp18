# Phase 1
## 世界生成主要思路:
世界由Room、HighWay和Wall组成。而Room和HighWay又由Point构成，所以首先需要建立 Point 类，Room 类 和 HighWay类。
首先考虑不采用算法，直接随机生成Room放入figure中，这样的复杂度很高，因为每当加入一个新的Room都要考虑怎么与其他的已经存在的Room相连。其次考虑采用算法，先将 Room 抽象成点，每次可以向上下左右扩展，然后将新产生的 Room 与当前 Room 连起来即可。其实这就是搜索算法，在具体的实现中采用了基于 Priority Queue 的搜索方法和深度优先搜索。具体实验中，也可以发现，在生成同样个数的 Room 的条件下，广度优先搜索会优先加大深度；而由于基于 Priority Queue 的方法定义离中心近的 Room 为优先，所以会先将中间区域占满。

具体步骤：
- Step1：随机生成第一个 Room， 将 Room 加入容器。
- Step2：将 Room 取出容器，确定上下左右四块区域的界限，用于生成新 Room 的中心。
- Step3：利用新 Room 的中心生成对应的新的 Room ，判定 Room 是否合法（是否与已经存在的Floor相交，是否超出地图的界限）
- Step4：将合法的新 Room 与 Step2 取出的 Room 通过 HighWay 相连。
- Step5：将合法的新 Room 加入容器中，返回 Step2。

问题：
使用如上的方法生成的 HighWay 都是直的， 因为只生成了竖直的和水平的 HighWay。
解决方法：
允许生成长或者宽为1的 Room，这样的 Room 与其他的 Room 通过 HighWay 相连时可以产生 HighWay 交叉的效果。
（不过要注意为了避免生成1 × 1的 Room， RoomGenerator 需要加入一些特殊情况判断）

## Scanner
@source:https://docs.oracle.com/javase/7/docs/api/java/util/Scanner.html

在建立Scanner对象时注意的问题：
```java
Scanner s = new Scanner(new File(pathname));
```
如果使用相对路径，注意 . 为主文件夹，如在 proj2 中，将 load.txt 放在 ~/CS61B/CS61B_sp18/proj2/byog/Core 中，所以 pathname = "./byog/Core/load.txt"。在antograder 的评价中，发现需要直接将 .txt 文件放置在 proj2 目录下，故 pathname = "./load.txt", 也可以直接写成 pathname = "load.txt"。

## Java int 类型的最大值
int 的范围是 $ -2^31 - 2^31-1 $, 而本 project 要求seed 支持 9223372036854775807， 所以应当使用 long。

# Phase 2

## Saving and loading
先前直接使用txt文件保存需要保存的world，看到 specification 中提到的 Serialize interface, 决定尝试一下。

@source:https://www.tutorialspoint.com/java/java_serialization.htm

### What is serialization?
Java provides a mechanism, called object serialization where an object can be represented as a sequence of bytes that includes the object's data as well as information about the object's type and the types of data stored in the object.

### Why use serialization?
- A object can be read from the file and deserialized that is, the type information and bytes that represent the object and its data can be used to recreate the object in memory.

- JVM is independent, can be deserialized on different platforms.

### How to use?
Two important classes: **ObjectInputStream** and **ObjectOutputStream** are high-level streams that contain the methods for serializing and deserializing an object.

```java
public final void writeObject(Object x) throws IOException

public final Object readObject() throws IOException, ClassNotFoundException
```
Notice that for a class to be serialized successfully, two conditions must be met:
- The class must implement the java.io.Serializable interface.
- All of the fields in the class must be serializable. If a field is not serializable, it must be marked **transient**.

Example:
```java
public class Employee implements java.io.Serializable {
	public String name;
	public String address;
	public transient int SSN;
	pubilc int number;

	public void mailCheck() {
		System.out.println("Mailing a check to" + name + " " + address);
	}
}
```
Serializing an Object:
```java
import java.io.*;
public class SerializeDemo {
	public static void main(String[] args) {
		Employee e = new Employee();
		e.name = "Reyan Ali";
		e.address = "Phokka Kuan, Ambehta Peer";
		e.SSN = 123234;
		e.number = 0;
	}
	try {
		FileOutputStream fileOut = new FileOutputSteam(filepath);
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(e);
		out.close();
		fileOut.close();
		System.out.println("Serialized data is saved in filepath");
	} catch (IOException i) {
		i.printStackTrace();
	}
}
```
Deserializing an Object:
```java
import java.io.*;
pubilc class DeserializeDemo {
	public static void main(String[] args) {
		Employee e = null;
		try {
			FileInputStream fileIn = new FileInputStream(pathname);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			e = (Employee) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
			return;
		} catch (ClassNotFoundException c) {
			System.out.println("Employee class not found");
			c.printStackTrace();
			return;
		}
	}
}
```
Note: The value of SNN after deserializing is 0 because it is marked transient.

### Application Problems
问题：
@source: https://stackoverflow.com/questions/13895867/java-io-notserializableexception
当 World 类实现Serializable 接口后，在 InOutput 类定义了 readObject 和 writeObject 方法。但却抛出了 java.io.NotSerializableException, 原因是 the fields of the World object have in turn their fields, some of which do not implement Serializable. 
解决方法：
Point、TETile 实现 Serializable 接口。


## 加入新特性
### Flower
新特性：
生成 World 时同时生成 Flower, 当 Player 拿到 Flower 时才能打开 Locked Door。

实现：
直接在 World 类中新定义 Flower 变量即可，相应的初始化过程中进行添加即可。

### Monster
新特性：
生成World 时同时生成 Monster, Monster 以恒定的速度在地图中随机游走。当 Player 碰到 Monster 时，游戏结束，出现 You lose! 的提示标语。

问题：
由于通过键盘和通过字符串运行的速度不同（电脑直接运行，键盘需要输入），所以会导致相同的 operation 产生不同的结果。故将 monster 特性删除。

