package project.patterns;

// случай, в которых i и j формируют гипотенузу, которая не пресекается другими точками
public class CorrectCase {

    private int i;
    private int j;
    private double tanBetweenIAndJ;
    private Direction direction;

    public CorrectCase(int i, int j, double tanBetweenIAndJ, Direction direction) {
        this.i = i;
        this.j = j;
        this.tanBetweenIAndJ = tanBetweenIAndJ;
        this.direction = direction;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public double getTanBetweenIAndJ() {
        return tanBetweenIAndJ;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return "CorrectCase{" +
                "i=" + i +
                ", j=" + j +
                ", tanBetweenIAndJ=" + tanBetweenIAndJ +
                ", direction=" + direction +
                '}';
    }
}
