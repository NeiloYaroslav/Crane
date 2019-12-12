import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GUI {
    JFrame frame;				// main frame
    JPanel inputPanel;
    JTextField weightField;
    JTextField distanceField;
    JPanel resultPanel;
    JTextArea resultField;

    InputData inputData;

    public GUI () {
        initialize();
    }

    private void initialize() {
        // initialize GUI frame
        frame = new JFrame();
        frame.setTitle("Кран");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
//        toolkit.beep();
        Dimension size = toolkit.getScreenSize();
        frame.setBounds((size.width/2 - 200), (size.height/2 - 300), 400, 600);
        frame.getContentPane().setLayout(null);

        createLabel("Масса груза:", 45, 50);
        createLabel("Расстояние:", 45, 75);

        // initializing text field for input weight
        weightField = new JTextField();
        weightField.setBackground(Color.WHITE);
        weightField.setBounds(150, 47, 180, 20);
        weightField.setVisible(true);
        weightField.setText("");
        weightField.setBorder(new LineBorder(Color.BLACK, 1, true));
        frame.getContentPane().add(weightField);

        // initializing text field for input distance
        distanceField = new JTextField();
        distanceField.setBackground(Color.WHITE);
        distanceField.setBounds(150, 72, 180, 20);
        distanceField.setText("");
        distanceField.setBorder(new LineBorder(Color.BLACK, 1, true));
        frame.getContentPane().add(distanceField);

        // initializing panel for input data
        inputPanel = new JPanel();
        inputPanel.setBounds(20, 20, 350, 95);
        inputPanel.setVisible(true);
        Border border = BorderFactory.createEtchedBorder();
        Border title = BorderFactory.createTitledBorder(border,
                " Данные для поиска ",
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("times new roman",Font.BOLD,14),
                Color.BLACK);
        inputPanel.setBorder(title);
        frame.getContentPane().add(inputPanel);

        // initializing button for get result
        JButton buttonFindCrane = new JButton("Найти кран");
        buttonFindCrane.setBounds(140, 125, 100, 40);
        frame.getContentPane().add(buttonFindCrane);
        buttonFindCrane.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                //todo validator
                inputData = new InputData();
                try {
                    inputData.weight = Double.parseDouble(weightField.getText());
                    inputData.distance = Double.parseDouble(distanceField.getText());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                System.out.println(inputData);

                try {
                    resultField.setText(new String(new CraneDataBase().findCrane(inputData.distance, inputData.weight)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // initializing panel for result data
        resultPanel = new JPanel();
        resultPanel.setBounds(20, 180, 350, 350);
        resultPanel.setVisible(true);
        Border border1 = BorderFactory.createEtchedBorder();
        Border title1 = BorderFactory.createTitledBorder(border1,
                " Результат ",
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("times new roman",Font.BOLD,14),
                Color.BLACK);
        resultPanel.setBorder(title1);
        frame.getContentPane().add(resultPanel);

        // initializing result field
        resultField = new JTextArea();
        resultField.setBackground(new Color(238,238,238));
        resultField.setOpaque(false);
        resultField.setBounds(120, 200, 300, 200);
        resultField.setLineWrap(true);
        resultField.setWrapStyleWord(true);
        resultField.setVisible(true);
//        resultField.setBorder(new LineBorder(Color.BLACK, 1, true));
        resultPanel.add(resultField);
    }

    private void createLabel (String value, int x, int y) {
        JLabel label = new JLabel(value);
        label.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        label.setBounds(x, y, 100, 14);
        frame.getContentPane().add(label);
    }
}
