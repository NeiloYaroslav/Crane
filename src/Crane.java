import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Crane {
    String brand; //Марка крана
    int maxLoadWeight; //Максимальная грузоподъемность
    LinkedHashMap<Double, DistanceToLoadWeight> arrowLengthToDistanceLoadWeight = new LinkedHashMap<>(); //Таблица грузоподъемности крана

    public Crane (String filePath) throws IOException {
        LinkedList<Double> distances = new LinkedList<>();

        try (BufferedReader readFile = new BufferedReader(new FileReader(filePath))) {
            while (readFile.ready()) {
                String readLine = readFile.readLine();
                String craneCharacteristic = readLine.split(":")[0];

                Double arrowLength = 0.00;

                if (craneCharacteristic.startsWith("DistanceToLoadWeight")) {
                    Pattern pattern = Pattern.compile("(\\d+\\.*\\d*)");
                    Matcher matcher = pattern.matcher(craneCharacteristic);
                    if (matcher.find()) {
                        arrowLength = Double.parseDouble(matcher.group(0));
                    }
                    craneCharacteristic = craneCharacteristic.split("\\s+")[0];
                }

                switch (craneCharacteristic) {
                    case "Brand":
                        this.brand = readLine.split(": ")[1]; //Марка крана
                        break;

                    case "MaxLoadWeight":
                        this.maxLoadWeight = Integer.parseInt(readLine.split(": ")[1]); //Грузоподъемность крана
                        break;

                    case "Distance":
                        for (String dist : splitReadLine(readLine)) {
                            distances.add(Double.parseDouble(dist));
                        }
                        break;

                    case "DistanceToLoadWeight":
                        String[] loadLine = splitReadLine(readLine);
                        DistanceToLoadWeight distanceToLoadWeight = new DistanceToLoadWeight();

                        for (int i = 0; i < loadLine.length; i++) {
                            distanceToLoadWeight.addDistanceToLoadWeight(distances.get(i), Double.parseDouble(loadLine[i]));
                        }

                        arrowLengthToDistanceLoadWeight.put(arrowLength, distanceToLoadWeight);
                        break;
                }
            }
        }
    }

    private String[] splitReadLine (String readLine) {
        String[] elements = readLine.split(":")[1]
                .replace("\t", "")
                .replace(" ", "")
                .split("\\|");
        return elements;
    }

    public String findLoadWeight (double goalDistance, double goalWeight) {
        double tableDistance; //Расстояние характерных точек по таблице грузоподъемности крана
        double tableWeight; //Значение грузоподъемности при tableDistance
        double arrowLength; //Длина стрелы
        String result = ""; //Переменная в которую записываем результат

        for (Map.Entry<Double, DistanceToLoadWeight> craneEntry : arrowLengthToDistanceLoadWeight.entrySet()) {
            arrowLength = craneEntry.getKey();
            DistanceToLoadWeight distanceToLoadWeight = craneEntry.getValue();
            LinkedHashMap<Double, Double> arrowLoad = distanceToLoadWeight.getDistanceToLoadWeight(); //Карта грузоподъемности стрелы данного крана

            //Если длина стрелы менше расчетного растояния от кнара до места установки груза
            //пропускаем дальнейший поиск по заведомо неподходящей карте грузоподъемности стрелы
            if (arrowLength < goalDistance) {
                continue;
            }

            //Проходим по карте грузоподъемности проверяя подходит ли грузоподъемность исходным данным
            for (Map.Entry arrowEntry : arrowLoad.entrySet()) {
                if ((double) arrowEntry.getKey() >= goalDistance) {
                    tableDistance = (double) arrowEntry.getKey();

                    if ((double) arrowEntry.getValue() > goalWeight) {
                        tableWeight = (double) arrowEntry.getValue();
                        result = "Вам подходит кран " + this.brand
                                + " г/п " + this.maxLoadWeight
                                + "т и длиной стрелы равной " + arrowLength
                                + "м. Максимальная г/п крана при вылете стрелы " + tableDistance
                                + "м равна " + tableWeight + "т."
                                + "\n" + "\n";
                        return result;
                    }
                }
            }
        }
        return "Вам не подходит кран " + this.brand + " с г/п " + this.maxLoadWeight + "т." + "\n" + "\n";
    }

    @Override
    public String toString() {
        return "Crane{" +
                "brand='" + brand + '\'' +
                ", maxLoad=" + maxLoadWeight +
                ", arrowLengthToDistanceLoadWeight=" + arrowLengthToDistanceLoadWeight +
                '}';
    }
}
