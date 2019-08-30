import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {

        ArrayList<Crane> craneList = new ArrayList<>();
        craneList.add(new Crane("src/crane/KC3577-14.txt"));
        craneList.add(new Crane("src/crane/KC45717-25.txt"));
        craneList.add(new Crane("src/crane/KC557Kp-30.txt"));
        craneList.add(new Crane("src/crane/KC55713-6K-32.txt"));
        craneList.add(new Crane("src/crane/KC65719-1K-40.txt"));

        Main.findCrane(craneList, 10, 7);
    }

    public static void findCrane (ArrayList<Crane> craneList, double distance, double weight) {
        for (Crane crane : craneList) {
            System.out.println(crane.findLoadWeight(distance, weight));
        }
    }
}
