package byog.Core;

import java.io.*;

public class InOutput {
    public static void writeObject(String pathName, World world) {
        try {
            FileOutputStream fileOut = new FileOutputStream(pathName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(world);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static World readObject(String pathName) {
        World world = null;
        try {
            FileInputStream fileIn = new FileInputStream(pathName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            world = (World) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return world;
    }
}
