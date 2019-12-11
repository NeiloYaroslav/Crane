import java.awt.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new GUI(); // create GUI
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
