package project;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ConsoleHelper {

    // глобальные переменные
    public static final LocalDate TODAY = LocalDate.now();
    public static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    public static final double RADIANS_TO_DEGREES = 57.296;

    // список со всеми значениями за месяц
    public static List<Double> constList;




    // вывод информации по графику
    public static void writeMessage(String name, String modeOfPattern, double upJ, double upI,
                                    double downJ, double downI, String shortDescription) {

        System.out.println("in write message");


        // сколько дней назад была такая точка
        int daysAgoForUpJ = (indexOf(upJ)-30)*-1;
        int daysAgoForUpI = (indexOf(upI)-30)*-1;
        int daysAgoForDownJ = (indexOf(downJ)-30)*-1;
        int daysAgoForDownI = (indexOf(downI)-30)*-1;


        System.out.println("На графике наблюдается элемент паттерна " +
                name+modeOfPattern+
                ".\nВерняя зона поддержки этого паттерна проходит через точки "+upJ+ getDate(daysAgoForUpJ) +
                " и "+upI+getDate(daysAgoForUpI)+"."+
                "\nНижняя зона поддержки этого паттерна проходит через точки "+downJ+ getDate(daysAgoForDownJ) +
                " и "+downI+getDate(daysAgoForDownI)+".\n"+shortDescription);


    }

    // находим дату и форматируем её
    public static String getDate(int daysAgo) {
        LocalDate todayCopy = TODAY;
        LocalDate theRightDay = todayCopy.minusDays(daysAgo);
        return " ("+theRightDay.format(DTF)+")";

    }

    // indexOf в constList
    public static int indexOf(double value) {
        return constList.indexOf(value)+1;
    }
}
