package project.patterns;

import project.ConsoleHelper;

import java.util.ArrayList;
import java.util.List;



public abstract class AbstractPattern {

    private String name;
    private String path;

    public static List<Double> maxes;
    public static List<Double> mines;
    public static List<Double> listOfValues;

    public static boolean isThereProblemWithI = false;

    // аналогично с j
    public static boolean isThereProblemWithJ = false;

    //отношение ординат от абсцисс (приводим Y к реальному значению)
    public static double utilForX;

    public String getName() {
        return name;
    };

    public abstract String checkPattern(String token);

    public List<CorrectCase> getCorrectCasesOfSupport(Direction directionOfSupport) {

        // тангенс между потенциальными точками поддержки
        double tanBetweenIAndJ;

        double valueI;
        double valueJ;

//        System.out.println("В методе getCorrectCasesOfSupport:");
//        System.out.println("maxes: "+maxes);
//        System.out.println("mines: "+mines);
//        System.out.println("utilForX: "+ utilForX);
//        System.out.println();


        // список "правильных" случаев
        List<CorrectCase> correctCases = new ArrayList();

        // можно ли случай записать в "правильные случаи"
        boolean isCaseCorrect;

        // напраление графика в случае точек i и j
        Direction direction;

        // определяем, с каким списком мы работаем с помощью парамметра метода
        listOfValues =
                directionOfSupport == Direction.UP ? maxes : mines;

        //System.out.println("listOfValues: "+listOfValues);

        // ищем две подходящие точки поддержки
        for (int i = listOfValues.size()-1; i > -1; i--) {
            valueI = listOfValues.get(i);
            isThereProblemWithI = false;
            isThereProblemWithJ = false;
            for (int j = i-1; j > -1; j--) {

                // default значение
                isCaseCorrect = true;
                valueJ = listOfValues.get(j);

                // проверка на проблемы (экономия времени)
                if (valueI > valueJ) {
                    if (isThereProblemWithI) {
                        continue;
                    }
                }
                else {
                    if (isThereProblemWithJ) {
                        continue;
                    }
                }
                // если это соседние точки, то в проверке они не нуждаются (между ними нет точек)
                //if (i-j>1) {
                //System.out.print("valueI: "+valueI+" valueJ: "+valueJ);

                // при разном неравенстве мы должны учитывать знак тангенса, чтобы результат был верным
                int signOfTangs = 1;
                if (valueI < valueJ) {
                    signOfTangs = -1;
                }

                // находим тангенс между точками i и j
                tanBetweenIAndJ = getTan(valueI, valueJ);
                //System.out.println("тангенс между "+valueI+" и "+valueJ+": "+tanBetweenIAndJ);

                if (!isSpaceClear(i, j, tanBetweenIAndJ, signOfTangs)) {
                    isCaseCorrect = false;
                }

                if (isCaseCorrect) {

                    // определение напрвления
                    direction = valueJ < valueI ? Direction.UP : Direction.DOWN;

                    correctCases.add(new CorrectCase(
                            i, j, tanBetweenIAndJ, direction
                    ));
                }
            }
        }
        return correctCases;
    }

    // проверка, нет ли таких точек, которые пересекают гипотенузу i и j
    public boolean isSpaceClear(int i, int j, double tanBetweenIAndJ, int signOfTangs) {
        double constValueI = listOfValues.get(i);
        double constValueJ = listOfValues.get(j);

        // сравнительное число, по которму происходит доп проверка
        double comparativeConstValue = signOfTangs == 1 ? constValueI : constValueJ;

        for (int k = listOfValues.size()-1; k > j; k--) {
            double value = listOfValues.get(k);
            if (value == constValueI)
                continue;

            // в случае положительной доп проверки, мы должны запомнить этот случай
            if (isHigherThanValue(comparativeConstValue, value)) {
                if (comparativeConstValue == constValueI) {
                    isThereProblemWithI = true;
                }
                else {
                    isThereProblemWithJ = true;
                }
                return false;
            }
            else if (i < k) {
                if (getTan(constValueI, value)*signOfTangs*-1 < tanBetweenIAndJ*signOfTangs*-1) {
                    return false;
                }
            }
            else if (i > k) {
                if (getTan(constValueI, value)*signOfTangs < tanBetweenIAndJ*signOfTangs) {
                    return false;
                }
            }
        }
        return true;
    }

    // доп проверка (если число больше константы, то оно автоматически пересекает гипотенузу)
    public boolean isHigherThanValue(double constValue, double value) {
        return value > constValue;
    }

    // получение тагенса между двумя значениями
    public double getTan(double firstValue, double secondValue) {
        double countPoints = countOfPointsBetween(firstValue, secondValue);
//        System.out.println("countPoints: "+ countPoints);
//        System.out.println("разница между занчениями: "+Math.abs(firstValue-secondValue));

        return Math.abs(firstValue-secondValue)/ // <-- противолежащий катет
                (countPoints*utilForX); // <-- прилежащий катет
    }

    // нахождение прилежащего катета
    public double countOfPointsBetween(double firstValue, double secondValue) {

        double countOfPoints = Math.abs(ConsoleHelper.indexOf(firstValue)-ConsoleHelper.indexOf(secondValue))+1;
        return countOfPoints;
    }
}
