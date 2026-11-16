package software.ulpgc.katas.architecture.model;

public class TriangularSeries {
    public static int[] calculate(int n) {
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            result[i] = (i + 1) * (i + 2) / 2;
        }
        return result;
    }
}
