package project.patterns;//package project.patterns;
//
//import project.ConsoleHelper;
//import project.DataGetter;
//
//import java.util.Collections;
//import java.util.List;
//
//public class HeadAndShoulders extends AbstractPattern {
//
//    private String name = "Голова и плечи";
//    private String shortDescription = " ";
//    private String path;
//    private static List<Double> goodList;
//    private static List<Double> maxes;
//    private static List<Double> mines;
//    private static List<Double> listOfValues;
//
//    /* переменная, которая меняется на true, когда i встретила значение, большее своего
//    то есть нужда в проверке больше не нужна */
//    private static boolean isThereProblemWithI = false;
//
//    // аналогично с j
//    private static boolean isThereProblemWithJ = false;
//
//    //отношение ординат от абсцисс (приводим Y к реальному значению)
//    public static double utilForX;
//
//    @Override
//    public void checkPattern() {
//        List<Double>[] arrayOfLists = DataGetter.filter(DataGetter.getData());
//        goodList = arrayOfLists[0];
//        maxes = arrayOfLists[1];
//        mines = arrayOfLists[2];
//
//        utilForX = Collections.max(maxes)/30;
//
//        List<CorrectCase> correctCasesOfUpSupport = getCorrectCasesOfSupport(Direction.UP);
//        List<CorrectCase> correctCasesOfDownSupport = getCorrectCasesOfSupport(Direction.DOWN);
//
//        if (correctCasesOfUpSupport.size() != 0 && correctCasesOfDownSupport.size() != 0) {
//            CorrectCase[] theBestCases = getTheBestGeneralCase(
//                    correctCasesOfUpSupport, correctCasesOfDownSupport
//            );
//            CorrectCase theBestUpCase = theBestCases[0];
//            CorrectCase theBestDownCase = theBestCases[1];
//
//            if (theBestUpCase != null && theBestDownCase != null) {
//
//                // какой вид паттерна мы имеем
//                String modeOfPattern = "";
//
//
//                if (theBestDownCase.getDirection() == Direction.UP && theBestDownCase.getDirection() == Direction.DOWN) {
//                    modeOfPattern = " (расширяющийся)";
//                }
//                else if (theBestDownCase.getDirection() == Direction.DOWN && theBestDownCase.getDirection() == Direction.UP) {
//                    modeOfPattern = " (сужающийся)";
//                }
//                else {
//                    if (Math.atan(theBestUpCase.getTanBetweenIAndJ())* ConsoleHelper.RADIANS_TO_DEGREES < 5
//                            || Math.atan(theBestUpCase.getTanBetweenIAndJ())*ConsoleHelper.RADIANS_TO_DEGREES < 5) {
//                        modeOfPattern = " (барьерный)";
//
//                    }
//                }
//
//                // вывод данных
//                ConsoleHelper.writeMessage(
//                        name, modeOfPattern, goodList, maxes.get(theBestDownCase.getJ()), maxes.get(theBestDownCase.getI()),
//                        mines.get(theBestDownCase.getJ()), mines.get(theBestDownCase.getI()), shortDescription
//                );
//            }
//        }
//    }
//}
