package byog.Core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class InOutput {
    public static String read(String pathName) throws FileNotFoundException {
        Scanner s = new Scanner(new File(pathName));
        StringBuilder sb = new StringBuilder();
        while (s.hasNextLine()) {
            sb.append(s.nextLine());
        }
        s.close();
        return sb.toString();
    }

    public static void write(String pathName, String string) {
        try
        {
            FileWriter writer = new FileWriter(pathName);
            writer.write(string);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
