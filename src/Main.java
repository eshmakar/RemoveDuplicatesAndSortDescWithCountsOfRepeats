import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader inputFile = new BufferedReader(new FileReader("C:/thinking.txt"));
        FileWriter outputFile = new FileWriter(new File("C:/t.txt"));

        Map<String, Integer> map = new HashMap<>();
        List<String> rawList = null;
        StringBuilder sb = new StringBuilder();

        Scanner scanner = new Scanner(inputFile);
        while (scanner.hasNext()) {
            sb.append(scanner.nextLine().replaceAll("\\W+|\\d+", " "))//W+ удаляем все, что не является словом и d+ цифрой
                    .append(" ");// добавляем пустую строку на StringBuilder после каждой строки

            rawList = Stream.of(sb.toString().split(" "))//sb содержит одну огромную строку, с помощью split разделяем на отдельные слова
                    .filter(e -> !e.isEmpty() && e.length() > 2)//если слово не меньше 2 символов
                    .map(String::toLowerCase)//получаем на нижнем регистре
                    .collect(Collectors.toCollection(ArrayList::new));//потом все это добро добавляем в лист, то есть каждое слово отдельно
        }

        System.out.println("Was added all words to list. List contains " + rawList.size() + " elements");
        inputFile.close();
        scanner.close();

        for (String x : rawList) {
            int count = map.getOrDefault(x, 0);
            map.put(x, count + 1);
        }

        System.out.println("Was added all words to map");

        for (String x : rawList) {
            if (map.getOrDefault(x, 0) < 50)//если слово повторяется меньше 50 раз, тогда удаляем из списка
                map.remove(x);
        }

        System.out.println("Was removed all less than 50 repeats from map");

        Map<String, Integer> sor = new MapUtil().sortByValue(map);
        System.out.println("Sorting map...");

        for (Map.Entry<String, Integer> entry : sor.entrySet()) {
            outputFile.write(entry.getKey() + " - " + entry.getValue().toString());//пишем результат на файл
            outputFile.write("\n");
        }

        outputFile.close();
        System.out.println("Finished!");

    }
}

class MapUtil implements Comparator {
    public <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue(this));


        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    @Override
    public int compare(Object o1, Object o2) {
        return Integer.parseInt(o2.toString()) - Integer.parseInt(o1.toString());
    }
}
