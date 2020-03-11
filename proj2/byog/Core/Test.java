package byog.Core;

import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        String regx = "[\\d]+";
        String input = "n123sswwdasdassadwas:q";
        String[] result = input.split(regx);
        System.out.println(result[0]);
        System.out.println(result[1]);
        System.out.println(input);
        System.out.println(Arrays.toString(result[1].split(":")));
    }
}
