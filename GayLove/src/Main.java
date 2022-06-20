import java.util.Scanner;

public class Main {
    static Scanner s;
    static LinkFinder link = new LinkFinder();
    static Resizer resizer = new Resizer();

    public static void main(String[] args){
        s = new Scanner(System.in);

        System.out.println("wo soll gespeichert? (Directory über den klassen insbesondere.)");
        link.setPath(s.nextLine());

        for (int i = 0; i < 4; i++) {
            System.out.println("gib ein was du suchen");
            link.run(s.nextLine(), "Klasse" + (i+1));
        }
        for (int i = 0; i < 4; i++) {
            resizer.resizeImages();
        }

    }
}
