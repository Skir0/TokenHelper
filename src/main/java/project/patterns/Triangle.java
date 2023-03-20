// нужно изменить фильтрацию, выбрать правильный алгоритм лучшего случая

package project.patterns;
import project.ConsoleHelper;
import project.DataGetter;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class Triangle extends AbstractPattern{

    public static List<Double> constList;

    private String name = "ТРЕУГОЛЬНИК";
    private String shortDescription = "Продолжение графика, " +
            "выходящее за одну из зон поддержки " +
            "(верхнюю или нижнюю)\nописывает дальнейшее движение тренда " +
            "(соответсвенно вверх или вниз). ";
    private String path;


    @Override
    public String checkPattern(String token) {

        System.out.println("in check pattern");
        List<Double>[] arrayOfLists = DataGetter.filter(DataGetter.getData(token));
        maxes = arrayOfLists[0];
        mines = arrayOfLists[1];

        utilForX = Collections.max(maxes)/30;

        List<CorrectCase> correctCasesOfUpSupport = getCorrectCasesOfSupport(Direction.UP);
        List<CorrectCase> correctCasesOfDownSupport = getCorrectCasesOfSupport(Direction.DOWN);





        if (correctCasesOfUpSupport.size() != 0 && correctCasesOfDownSupport.size() != 0) {
            CorrectCase[] theBestCases = getTheBestGeneralCase(
                    correctCasesOfUpSupport, correctCasesOfDownSupport
            );
            System.out.println(theBestCases[0]);
            System.out.println(theBestCases[1]);
            CorrectCase theBestUpCase = theBestCases[0];
            CorrectCase theBestDownCase = theBestCases[1];

            if (theBestUpCase != null && theBestDownCase != null) {

                // какой вид паттерна мы имеем
                String modeOfPattern = "";


                if (theBestDownCase.getDirection() == Direction.UP && theBestDownCase.getDirection() == Direction.DOWN) {
                    modeOfPattern = " (расширяющийся)";
                }
                else if (theBestDownCase.getDirection() == Direction.DOWN && theBestDownCase.getDirection() == Direction.UP) {
                    modeOfPattern = " (сужающийся)";
                }
                else {
                    if (Math.atan(theBestUpCase.getTanBetweenIAndJ())*ConsoleHelper.RADIANS_TO_DEGREES < 5
                    || Math.atan(theBestUpCase.getTanBetweenIAndJ())*ConsoleHelper.RADIANS_TO_DEGREES < 5) {
                        modeOfPattern = " (барьерный)";

                    }
                }
//                System.out.println(maxes+"\n");
//                System.out.println(mines+"\n");
//                System.out.println(theBestUpCase);
//                System.out.println(theBestDownCase);
                // вывод данных

//                ConsoleHelper.writeMessage(
//                        name, modeOfPattern, maxes.get(theBestDownCase.getJ()-1), maxes.get(theBestDownCase.getI()-1),
//                        mines.get(theBestDownCase.getJ()-1), mines.get(theBestDownCase.getI()-1), shortDescription
//                );

                System.out.println("end check pattern");

                double upJ = maxes.get(theBestDownCase.getJ()-1);
                System.out.println(upJ);
                double upI = maxes.get(theBestDownCase.getI()-1);
                double downJ = mines.get(theBestDownCase.getJ()-1);
                double downI = mines.get(theBestDownCase.getI()-1);

                int daysAgoForUpJ = (constList.indexOf(upJ)+1-30)*-1;
                System.out.println((indexOf(upJ)-30)*-1);
                System.out.println(daysAgoForUpJ);
                int daysAgoForUpI = (constList.indexOf(upI)+1-30)*-1;
                int daysAgoForDownJ = (constList.indexOf(downJ)+1-30)*-1;
                int daysAgoForDownI = (constList.indexOf(downI)+1-30)*-11;

                System.out.println(daysAgoForDownI);

                System.out.println("На графике наблюдается элемент паттерна " +
                        name+modeOfPattern+
                        ".\nВерняя зона поддержки этого паттерна проходит через точки "+upJ+ getDate(daysAgoForUpJ) +
                        " и "+upI+getDate(daysAgoForUpI)+"."+
                        "\nНижняя зона поддержки этого паттерна проходит через точки "+downJ+ getDate(daysAgoForDownJ) +
                        " и "+downI+getDate(daysAgoForDownI)+".\n"+shortDescription);
                return "На графике наблюдается элемент паттерна " +
                        name+modeOfPattern+
                        ".\nВерняя зона поддержки этого паттерна проходит через точки "+upJ+ getDate(daysAgoForUpJ) +
                        " и "+upI+getDate(daysAgoForUpI)+"."+
                        "\nНижняя зона поддержки этого паттерна проходит через точки "+downJ+ getDate(daysAgoForDownJ) +
                        " и "+downI+getDate(daysAgoForDownI)+".\n"+shortDescription;
            }

        }
        return "В данный момент не наблюдается подходящий паттерн";
    }

    // мы ищем cases, период дней которых наиболее похож
    public CorrectCase[] getTheBestGeneralCase(List<CorrectCase> correctCasesOfUpSupport, List<CorrectCase> correctCasesOfDownSupport) {

        double minDifferenceOfPeriods = 30;

        CorrectCase theBestUpCorrectCase = null;
        CorrectCase theBestDownCorrectCase = null;

        for (CorrectCase correctUpCase: correctCasesOfUpSupport) {
            for (CorrectCase correctDownCase: correctCasesOfDownSupport) {
                if (correctUpCase.getDirection() != correctDownCase.getDirection()) {
                    // эксперимент (разница между днями чтобы была наиболее похожей)
//                    double differenceOfPeriods = countOfPointsBetween(maxes.get(correctUpCase.getI()), maxes.get(correctUpCase.getJ())) -
//                            countOfPointsBetween(mines.get(correctUpCase.getI()), mines.get(correctUpCase.getJ()));
                    double differenceOfPeriods = Math.abs(correctUpCase.getI() - correctUpCase.getJ()) -
                            Math.abs(correctDownCase.getI() - correctDownCase.getJ());
                    if (differenceOfPeriods < minDifferenceOfPeriods) {
                        minDifferenceOfPeriods = differenceOfPeriods;
                        theBestUpCorrectCase = correctUpCase;
                        theBestDownCorrectCase = correctDownCase;

                    }
                }
            }
        }
        return new CorrectCase[]{theBestUpCorrectCase, theBestDownCorrectCase};
    }

    public static String getDate(int daysAgo) {
        LocalDate todayCopy = ConsoleHelper.TODAY;
        LocalDate theRightDay = todayCopy.minusDays(daysAgo);
        return " ("+theRightDay.format(ConsoleHelper.DTF)+")";

    }

    // indexOf в constList
    public static int indexOf(double value) {
        return constList.indexOf(value)+1;
    }

    public static void main(String[] args) {
        new Triangle().checkPattern("ethereum-classic");
    }



}
