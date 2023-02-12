package camera;
import java.util.ArrayList;

public class Camera3D {
    private ArrayList<double[]> worldToVie = new ArrayList<double[]>();
    private ArrayList<double[]> vieToProjection = new ArrayList<double[]>();
    private ArrayList<double[]> worlToProjection = new ArrayList<double[]>();

    private double[] Ov; // начало ВСК
    private double[] T; // Вектор нормали объекта
    private double[] N; // Вектор нормали плоскости ВСК
    private double D; // Расстояние до точки фокуса

    private double[] iv = new double[3];
    private double[] jv = new double[3];
    private double[] kv = new double[3];

    public Camera3D(int x, int y, int z) {
        recalculation(x, y, z);
    }

    public void setAll(int x, int y, int z) {
        setN();
        setT();
        setD();
        setOv(x, y, z);
        setIv();
        setKv();
        setJv();
    }

    public void setOv(int x, int y, int z) {
        Ov = new double[3];

        Ov[0] = x; // Перемещение камеры по OX(При увеличении вправо, а фигура влево)
        Ov[1] = y; // Перемещение камеры по OY(При увелечении вверх, а фигура вниз)
        Ov[2] = y; // Перемещение камеры по OZ(При увеличении Отдаляется) Если равно D -
                   // соблюдается масштаб
    }

    public void setT() {
        T = new double[3];

        T[0] = 0;
        T[1] = 1;
        T[2] = 0;
    }

    public void setN() {
        N = new double[3];

        N[0] = 0;
        N[1] = 0;
        N[2] = 1;
    }

    public void setD() {
        D = 25;
    }

    public void setIv() {
        double normN = lenghtVec(N);
        double normT = lenghtVec(T);
        double norm = normN * normT;

        for (int i = 0; i < 3; i++) {
            iv[i] = N[i] * T[i] / norm;
        }

        iv[0] = 1; // Рястяжение или сжатие по x
        iv[1] = 0; // Смещение право верхних вершин по x
        iv[2] = 0; // Смещение влево абсолютно всех вершин
    }

    public void setJv() {

        for (int i = 0; i < 3; i++) {
            jv[i] = kv[i] * iv[i];
        }

        jv[0] = 0; // Поворот вершин относительно OY
        jv[1] = 1; // Растяжение или сжатие по OY
        jv[2] = 0; // Перемещение вершин вниз или вверх по OY
    }

    public void setKv() {

        for (int i = 0; i < 3; i++) {
            kv[i] = N[i] / lenghtVec(N);
        }

        kv[0] = 0; // Вытягивание(сжатие) вершин в сторону OX
        kv[1] = 0; // вытягивание, сжатие вержин в сторону OZ
        kv[2] = 1; // увелижение или уменьшение потносительно OZ
    }

    public double[] getOv() {
        return Ov;
    }

    public double[] getT() {
        return T;
    }

    public double[] getN() {
        return N;
    }

    public double getD() {
        return D;
    }

    public double[] getIv() {
        return iv;
    }

    public double[] getJv() {
        return jv;
    }

    public double[] getKv() {
        return kv;
    }

    public ArrayList<double[]> getWorldToVie() {
        return worldToVie;
    }

    public ArrayList<double[]> getVieToProjection() {
        return vieToProjection;
    }

    public ArrayList<double[]> getWorlToProjection() {
        return worlToProjection;
    }

    public double lenghtVec(double[] vec) {
        double lenght = 0;

        for (int i = 0; i < vec.length; i++) 
            lenght += vec[i] * vec[i];

        return lenght;
    }

    private void WorldToVie() {
        worldToVie = new ArrayList<>();

        worldToVie.add(new double[] { iv[0], iv[1], iv[2], -(iv[0] * Ov[0] + iv[1] * Ov[1] + iv[2] * Ov[2]) });
        worldToVie.add(new double[] { jv[0], jv[1], jv[2], -(jv[0] * Ov[0] + jv[1] * Ov[1] + jv[2] * Ov[2]) });
        worldToVie.add(new double[] { kv[0], kv[1], kv[2], -(kv[0] * Ov[0] + kv[1] * Ov[1] + kv[2] * Ov[2]) });
        worldToVie.add(new double[] { 0, 0, 0, 1 });
    }

    private void VieToProjection() {
        vieToProjection.add(new double[] { 1, 0, 0, 0 });
        vieToProjection.add(new double[] { 0, 1, 0, 0 });
        vieToProjection.add(new double[] { 0, 0, -1 / D, 0 });
    }

    private void WorldToProjection() {
        double[] result = new double[4];
        double sum = 0;

        for (int i = 0; i < 3; i++) {
            result = new double[4];

            for (int j = 0; j < 4; j++) {

                for (int k = 0; k < 4; k++) {
                    sum += vieToProjection.get(i)[k] * worldToVie.get(k)[j];
                }
                result[j] = sum;
                sum = 0;
            }
            worlToProjection.add(result);
        }
    }

    public void recalculation(int x, int y, int z) {
        setAll(x, y, z);
        WorldToVie();
        VieToProjection();
        WorldToProjection();
    }
}
