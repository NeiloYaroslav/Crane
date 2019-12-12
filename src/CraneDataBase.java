import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CraneDataBase {
    List<Crane> craneList = new ArrayList<>();

    public CraneDataBase () throws IOException {
        craneList.add(new Crane("src/crane/KC3577-14.txt"));
        craneList.add(new Crane("src/crane/KC45717-25.txt"));
        craneList.add(new Crane("src/crane/KC557Kp-30.txt"));
        craneList.add(new Crane("src/crane/KC55713-6K-32.txt"));
        craneList.add(new Crane("src/crane/KC65719-1K-40.txt"));
    }

    public StringBuilder findCrane (double distance, double weight) {
        StringBuilder result = new StringBuilder();
        for (Crane crane : craneList) {
            result.append(crane.findLoadWeight(distance, weight));
        }
        return result;
    }
}
