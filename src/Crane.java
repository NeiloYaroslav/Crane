import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

public class Crane {
    String brand; //Марка крана
    int maxLoad; //Максимальная грузоподъемность
    LinkedHashMap<Double, LinkedHashMap<Double, Double>> craneTableLoadWeight = new LinkedHashMap<>(); //Таблица грузоподъемности крана
//    LinkedHashSet<ArrowLoadWeight> craneTableLoadWeight = new LinkedHashMap<>(); //Таблица грузоподъемности крана
//    LinkedHashMap<Double, DistLoadWeight> craneTableLoadWeight = new LinkedHashMap<>(); //Таблица грузоподъемности крана


    public Crane (String fileName) throws IOException {
        //Считываем файл с характеристиками крана
        String distanceLoad = ""; //Расстояния до характерных точек определенной грузоподъемности

        //Список грузоподъемности каждой стрелы
        //Первое значение - длина стрелы, остальные - грузоподъемность в характерных точках
        LinkedHashSet<String> arrowLoadWeight = new LinkedHashSet<>();
        double[] arrowLoadWeight = new double[];

        try (BufferedReader readFile = new BufferedReader(new FileReader(fileName))) {
            //Счетчик колличества считаных строк с файла
            int counter = 0;

            //Считываем и обрабатываем данные из файла
            while (readFile.ready()) {
                String readLine = readFile.readLine();
                counter++;

                String parameterIdentificator = Pattern.compile("\\w").matcher(readLine).group(0);

                switch (parameterIdentificator) {
                    case "ArrowLength": //Первая строчка - марка крана
                        this.brand = readLine;
                        break;
                    case 2: //Вторая строчка - максимальная грузоподъемность крана
                        this.maxLoad = Integer.parseInt(readLine);
                        break;
                    case 3: //Расстояния до характерных точек определенной грузоподъемности
                        distanceLoad = readLine;
                        break;
                    default: //Добавляем в список считаную строку с грузоподъемностью каждой стрелы
                        arrowLoadWeight = readLine.split("\\s+");
                        arrowLoadWeight.add(readLine);
                        break;
                }
            }
        }

        //Массив характерных точек
        String[] distances = distanceLoad.split("\\s+");

        for (String line : arrowLoadWeight) {
            //Массив грузоподъемности каждой стрелы
            //Первое знавение - длина стрелы, остальные грзоподъемность на расстоянии distances
            String[] arrowLoad = line.split("\\s+");

            //Карта грузоподъемности каждой стрелы крана,
            //ключ - расстояние до характерной точки, значение - грузоподъемность в характерной точке
            LinkedHashMap<Double, Double> arrowWeight = new LinkedHashMap<>();
            for (int i = 1; i < arrowLoad.length; i++) {
                arrowWeight.put(Double.parseDouble(distances[i]), Double.parseDouble(arrowLoad[i]));
            }
            //Заполняем карту характеристики крана, где
            //Ключ - длина стрелы, значение - карта с грузоподъемностью в характерных точках, где
            //ключ - расстояние до характерной точки, значение - грузоподъемность в характерной точке
            this.craneTableLoadWeight.put(Double.parseDouble(arrowLoad[0]), arrowWeight);
        }
    }

    public String findLoadWeight (double goalDistance, double calculationweight) {
        double tableDistance; //Расстояние характерных точек по таблице грузоподъемности крана
        double tableWeight; //Значение грузоподъемности при tableDistance
        double arrowLength; //Длина стрелы
        String result = ""; //Переменная в которую записываем результат

        //Проходим по карте крана, где ключ - длина стрелы, значение - карта грузоподъемности стрелы данного крана
        for (Map.Entry craneEntry : craneTableLoadWeight.entrySet()) {
            arrowLength = (double) craneEntry.getKey();

            //Карта грузоподъемности стрелы данного крана
            HashMap<Double, Double> arrowLoad = (HashMap<Double, Double>) craneEntry.getValue();

            //Если длина стрелы менше расчетного растояния от кнара до места установки груза
            //пропускаем дальнейший поиск по заведомо неподходящей карте грузоподъемности стрелы
            if (arrowLength < goalDistance) {
                continue;
            }

            //Проходим по карте грузоподъемности проверяя подходит ли грузоподъемность исходным данным
            for (Map.Entry arrowEntry : arrowLoad.entrySet()) {
                if ((double) arrowEntry.getKey() >= goalDistance) {
                    tableDistance = (double) arrowEntry.getKey();

                    if ((double) arrowEntry.getValue() > calculationweight) {
                        tableWeight = (double) arrowEntry.getValue();
                        result = "Вам подходит кран " + this.brand
                                + " грузоподъемностью " + this.maxLoad
                                + "т с длиной стрелы равной " + arrowLength
                                + "м. \nМаксимальная грузоподъемность крана при вылете стрелы " + tableDistance
                                + "м равна " + tableWeight
                                + "т.\nМаксимальная масса груза " + calculationweight
                                + "т, расстояние от оси крана до места установки груза " + goalDistance + "м.";
                        return result;
                    }
                }
            }
        }
        return "Кран " + this.brand + " с грузоподъемностью " + this.maxLoad
                + "т вам не подходят: низкая грузоподъемность крана.";
    }

    @Override
    public String toString() {
        return "Crane{" +
                "brand='" + brand + '\'' +
                ", maxLoad=" + maxLoad +
                ", craneTableLoadWeight=" + craneTableLoadWeight +
                '}';
    }
}
