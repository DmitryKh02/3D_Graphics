package math;

public class Coordinates {
    private double X;
    private double Y;
    private double Z;
    private int K;

    public Coordinates() {
        X = 0;
        Y = 0;
        K = 1;
    }

    public double getElem(int pos) {
        double result = 0;
        switch (pos) {
            case 0:
                result = X;
                break;
            case 1:
                result = Y;
                break;
            case 2:
                result = Z;
                break;
            case 3:
                result = K;
                break;
        }

        return result;
    }

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

    public double getZ() {
        return Z;
    }

    public int getK() {
        return K;
    }

    public double[] getListCoordinates() {
        double[] result = new double[] { X, Y, Z, K };
        return result;
    }

    public void setX(double x) {
        X = x;
    }

    public void setY(double y) {
        Y = y;
    }

    public void setZ(double z) {
        Z = z;
    }

    public void setK(int k) {
        K = k;
    }

    public void setCoordinates(double x, double y, double z) {
        int k_x = gettingRidOfTheDecimal(x), k_y = gettingRidOfTheDecimal(y), k_z = gettingRidOfTheDecimal(z);

        if (k_x >= k_y && k_x >= k_z)
            K = k_x;
        else if (k_y >= k_x && k_y >= k_z)
            K = k_y;
        else
            K = k_z;

        X = x * K;
        Y = y * K;
        Z = z * K;
    }

    public int gettingRidOfTheDecimal(double x) {
        int result = 1;
        int x_int = (int) x;
        double diff = x - x_int;

        while (diff < 1 && diff > 0.000001) {
            diff *= 10;
            diff = diff - (int) diff;
            result *= 10;
        }

        return result;
    }

    public void recalculationOfMaxK(int k) {
        if (k > K) {
            X *= (int) k / K;
            Y *= (int) k / K;
            K = k;
        }
    }

    public void printCoordinates() {
        System.out.println("X: " + X + "\nY: " + Y + "\nZ: " + Z + "\nK: " + K);
    }
}
