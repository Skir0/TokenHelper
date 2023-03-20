package project;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import project.patterns.Triangle;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataGetter {

    // получение html документа
    private static Document getDocument(String token) {
        Document document = null;
        try {
            document = Jsoup.connect(String.format("https://ru.investing.com/crypto/%s/historical-data", token))
                    .userAgent("Chrome/4.0.249.0 Safari/532.5")
                    .referrer("http://www.google.com")
                    .get();
            document.normalise();
        } catch (Exception e) {
            System.out.println("Error on site");
        }
        return document;
    }

    // получаем информацию о цене монеты на протяжении меяца
    public static List<Double> getData(String token) {

        // html документ
        String HTMLDocument = getDocument(token).html();

        // dtf такой, который встречается в документе
        String[] dateIntervals = getDateIntervals(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        String from = dateIntervals[0];
        String to = dateIntervals[1];

        // часть документа, в которй встречаются сами значения
        String linesWithValues = HTMLDocument.substring(HTMLDocument.indexOf(to), HTMLDocument.indexOf(from));

        return getFinalListOfValues(linesWithValues);


    }

    // получение интервалов дат (сегодня и 30 дней назад)
    private static String[] getDateIntervals(DateTimeFormatter dtf) {

        // интервалы, между которыми мы берём значения
        LocalDate today = LocalDate.now();
        LocalDate monthAgo = LocalDate.now().minusDays(30);

        // форматирум дату к варианту, который встречается в html документе
        String from = monthAgo.format(dtf);
        String to = today.format(dtf);
        return new String[]{from, to};
    }



    // фильтрация строки (получаем сами значения)
    public static List<Double> getFinalListOfValues(String linesWithValues) {

        String[] tempArray = linesWithValues.replaceAll("<td data-real-value=", "").split("\n");
        List<Double> finalListOfValues = new ArrayList();
        for (String line : tempArray) {
            if (line.contains("class") && !line.contains("%") && line.contains(",")) {
                line = line.replaceAll("\\.", "");
                line = line.replaceAll(",", ".");
                finalListOfValues.add(Double.parseDouble(line.substring(10, line.indexOf("\" class"))));
            }
        }
        Collections.reverse(finalListOfValues);

        // сохраняем list в глобальную пременную
        ConsoleHelper.constList = finalListOfValues;
        Triangle.constList = finalListOfValues;
        return finalListOfValues;
    }

    // надо сделать фильтрацию получше
    public static List<Double>[] filter(List<Double> finalListOfValues) {

        // заготовка списков
        ArrayList<Double> maxes = new ArrayList<>();
        ArrayList<Double> mines = new ArrayList<>();

        // получаем отфильтрованные списки
        List<Double>[] filteredLists = getFilteredLists(
                finalListOfValues, maxes, mines
        );

        // logging
//        System.out.println("originalList: "+ConsoleHelper.constList+"\n");
//        System.out.println("maxes: "+maxes+"\n");
//        System.out.println("mines: "+mines+"\n");

        return filteredLists;
    }

    // делим общий список на дочерние списки
    // значения отправляем в соответствующие списки
    private static List<Double>[] getFilteredLists(List<Double> finalListOfValues, ArrayList<Double> maxes, ArrayList<Double> mines) {

        //идём по циклу и выбираем интервалом подходящие данные
        for (int i = 0; i < 30; i+=5) {
            List<Double> subList = finalListOfValues.subList(i, i+5);
            double median = getMedian(subList);
            // System.out.println("Медиана между значениями "+ (i+1)+" и "+ (i+5)+": "+ median);
            for (double value: subList) {
                if (value >= median) {
                    maxes.add(value);
                }
                else {
                    mines.add(value);
                }
            }

        }
        return new ArrayList[]{
            maxes, mines
        };
    }

    // находим среднее арифметическое 5 значений
    public static double getMedian(List<Double> subList) {


        return subList.stream()
                .mapToDouble(Double::doubleValue)
                .sum()/5;
    }






}



//package project;
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//public class DataGetter {
//
//    // получение html документа
//    private static Document getDocument() {
//        Document document = null;
//        try {
//            document = Jsoup.connect(Inputer.input())
//                    .get();
//            document.normalise();
//        } catch (Exception e) {
//            System.out.println("Error on site");
//        }
//        return document;
//    }
//
//    // получаем информацию о цене монеты на протяжении меяца
//    public static List<Double> getData() {
//
//        // html документ
//        String HTMLDocument = getDocument().html();
//
//        // dtf такой, который встречается в документе
//        String[] dateIntervals = getDateIntervals(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
//
//        String from = dateIntervals[0];
//        String to = dateIntervals[1];
//
//        // часть документа, в которй встречаются сами значения
//        String linesWithValues = HTMLDocument.substring(HTMLDocument.indexOf(to), HTMLDocument.indexOf(from));
//
//        return getFinalListOfValues(linesWithValues);
//
//
//    }
//
//    // получение интервалов дат (сегодня и 30 дней назад)
//    private static String[] getDateIntervals(DateTimeFormatter dtf) {
//
//        // интервалы, между которыми мы берём значения
//        LocalDate today = LocalDate.now();
//        LocalDate monthAgo = LocalDate.now().minusDays(30);
//
//        // форматирум дату к варианту, который встречается в html документе
//        String from = monthAgo.format(dtf);
//        String to = today.format(dtf);
//        return new String[]{from, to};
//    }
//
//
//
//    // фильтрация строки (получаем сами значения)
//    public static List<Double> getFinalListOfValues(String linesWithValues) {
//
//        String[] tempArray = linesWithValues.replaceAll("<td data-real-value=", "").split("\n");
//        List<Double> finalListOfValues = new ArrayList();
//        for (String line : tempArray) {
//            if (line.contains("class") && !line.contains("%") && line.contains(",")) {
//                line = line.replaceAll("\\.", "");
//                line = line.replaceAll(",", ".");
//                finalListOfValues.add(Double.parseDouble(line.substring(10, line.indexOf("\" class"))));
//            }
//        }
//        Collections.reverse(finalListOfValues);
//
//        // сохраняем list в глобальную пременную
//        ConsoleHelper.constList = finalListOfValues;
//        return finalListOfValues;
//    }
//
//    // надо сделать фильтрацию получше
//    public static List<Double>[] filter(List<Double> finalListOfValues) {
//
//        // заготовка списков
//        ArrayList<Double> maxesAndMines = new ArrayList<>();
//        ArrayList<Double> maxes = new ArrayList<>();
//        ArrayList<Double> mines = new ArrayList<>();
//
//        // получаем отфильтрованные списки
//        List<Double>[] filteredLists = getFilteredLists(
//                finalListOfValues, maxesAndMines, maxes, mines
//        );
//
//        // logging
//        System.out.println(Collections.max(maxes)/30);
//        System.out.println("originalList: "+ConsoleHelper.constList+"\n");
//        System.out.println("maxesAndMines: "+maxesAndMines+"\n");
//        System.out.println("maxes: "+maxes+"\n");
//        System.out.println("mines: "+mines+"\n");
//
//        return filteredLists;
//    }
//
//    // делим общий список на дочерние списки, фильруем их
//    private static List<Double>[] getFilteredLists(List<Double> finalListOfValues, ArrayList<Double> maxesAndMines, ArrayList<Double> maxes, ArrayList<Double> mines) {
//        // идём по циклу и выбираем интервалом подходящие данные
//        for (int i = 0; i < 30; i+=5) {
//            List<Double> subList = finalListOfValues.subList(i, i+5);
//            double max = Collections.max(subList);
//            double min = Collections.min(subList);
//            maxes.add(max);
//            mines.add(min);
//            if (subList.indexOf(max) < subList.indexOf(min)) {
//                maxesAndMines.add(max);
//                maxesAndMines.add(min);
//            }
//            else {
//                maxesAndMines.add(min);
//                maxesAndMines.add(max);
//            }
//        }
//        return new ArrayList[]{
//                maxesAndMines, maxes, mines
//        };
//    }
//
//    public static double getMedian(List<Double> finalListOfValues) {
//        List<Double> copy = finalListOfValues;
//        Collections.sort(copy);
//        double median;
//        if (copy.size() % 2 == 0)
//            median = (copy.get(copy.size()/2) + copy.get((copy.size()-1)/2))/2;
//        else
//            median = copy.get(copy.size()/2);
//        return median;
//    }
//
//
//
//
//
//
//}
