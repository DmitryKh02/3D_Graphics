package math;

import java.util.ArrayList;

public class Affine {
    private Model figureNew;

    public Affine() {
    }

    public Model getModel() {
        return figureNew;
    }

    public void setModel(Model model) {
        figureNew = model;
    }

    public void move(int x, int y, int z, Model point) {
        figureNew = new Model(point.getCountVertex(), point.getCountEdge());
        figureNew.setEdgeList(point.getEdgeList());
        figureNew.setCoordinatesList(multiply(translation(x, y, z), point.getListCoordinates()));
    }

    public void rotationFigOX(double phi, Model point) {
        figureNew = new Model(point.getCountVertex(), point.getCountEdge());
        figureNew.setEdgeList(point.getEdgeList());
        figureNew.setCoordinatesList(multiply(rotationOX(phi), point.getListCoordinates()));
    }

    public void rotationFigOY(double phi, Model point) {
        figureNew = new Model(point.getCountVertex(), point.getCountEdge());
        figureNew.setEdgeList(point.getEdgeList());
        figureNew.setCoordinatesList(multiply(rotationOY(phi), point.getListCoordinates()));
    }

    public void rotationFigOZ(double phi, Model point) {
        figureNew = new Model(point.getCountVertex(), point.getCountEdge());
        figureNew.setEdgeList(point.getEdgeList());
        figureNew.setCoordinatesList(multiply(rotationOZ(phi), point.getListCoordinates()));
    }

    public void reflection(int number, Model point) {
        figureNew = new Model(point.getCountVertex(), point.getCountEdge());
        figureNew.setEdgeList(point.getEdgeList());
        switch (number) {

            case 0:
                figureNew.setCoordinatesList(multiply(reflectionOO(), point.getListCoordinates()));
                break;
            case 1:
                figureNew.setCoordinatesList(multiply(reflectionOX(), point.getListCoordinates()));
                break;
            case 2:
                figureNew.setCoordinatesList(multiply(reflectionOY(), point.getListCoordinates()));
                break;
            case 3:
                figureNew.setCoordinatesList(multiply(reflectionOZ(), point.getListCoordinates()));
                break;
            case 4:
                figureNew.setCoordinatesList(multiply(reflectionYOZ(), point.getListCoordinates()));
                break;
            case 5:
                figureNew.setCoordinatesList(multiply(reflectionZOX(), point.getListCoordinates()));
                break;
            case 6:
                figureNew.setCoordinatesList(multiply(reflectionXOY(), point.getListCoordinates()));
                break;
        }

    }

    public void scale(double kx, double ky, double kz, Model point) {
        figureNew = new Model(point.getCountVertex(), point.getCountEdge());
        figureNew.setEdgeList(point.getEdgeList());
        figureNew.setCoordinatesList(multiply(scaling(kx, ky, kz), point.getListCoordinates()));
    }

    private ArrayList<double[]> multiply(ArrayList<double[]> first, ArrayList<double[]> second) {
        ArrayList<double[]> result = new ArrayList<>();
        double mean = 0;

        for (int k = 0; k < second.size(); k++) {
            result.add(new double[] { 0, 0, 0, 0 });

            for (int i = 0; i < 4; i++) {

                for (int j = 0; j < 4; j++) {
                    mean += first.get(i)[j] * second.get(k)[j];
                }

                result.get(k)[i] = mean;
                mean = 0;
            }
        }

        return result;
    }

    private ArrayList<double[]> translation(double x, double y, double z) {
        ArrayList<double[]> result = new ArrayList<>();

        result.add(new double[] { 1, 0, 0, x });
        result.add(new double[] { 0, 1, 0, y });
        result.add(new double[] { 0, 0, 1, z });
        result.add(new double[] { 0, 0, 0, 1 });

        return result;
    }

    private ArrayList<double[]> scaling(double kx, double ky, double kz) {
        ArrayList<double[]> result = new ArrayList<>();

        result.add(new double[] { kx, 0, 0, 0 });
        result.add(new double[] { 0, ky, 0, 0 });
        result.add(new double[] { 0, 0, kz, 0 });
        result.add(new double[] { 0, 0, 0, 1 });

        return result;
    }

    private ArrayList<double[]> reflectionYOZ() {
        ArrayList<double[]> result = new ArrayList<>();

        result.add(new double[] { -1, 0, 0, 0 });
        result.add(new double[] { 0, 1, 0, 0 });
        result.add(new double[] { 0, 0, 1, 0 });
        result.add(new double[] { 0, 0, 0, 1 });

        return result;
    }

    private ArrayList<double[]> reflectionZOX() {
        ArrayList<double[]> result = new ArrayList<>();

        result.add(new double[] { 1, 0, 0, 0 });
        result.add(new double[] { 0, -1, 0, 0 });
        result.add(new double[] { 0, 0, 1, 0 });
        result.add(new double[] { 0, 0, 0, 1 });

        return result;
    }

    private ArrayList<double[]> reflectionXOY() {
        ArrayList<double[]> result = new ArrayList<>();

        result.add(new double[] { 1, 0, 0, 0 });
        result.add(new double[] { 0, 1, 0, 0 });
        result.add(new double[] { 0, 0, -1, 0 });
        result.add(new double[] { 0, 0, 0, 1 });

        return result;
    }

    private ArrayList<double[]> reflectionOX() {
        ArrayList<double[]> result = new ArrayList<>();

        result.add(new double[] { 1, 0, 0, 0 });
        result.add(new double[] { 0, -1, 0, 0 });
        result.add(new double[] { 0, 0, -1, 0 });
        result.add(new double[] { 0, 0, 0, 1 });

        return result;
    }

    private ArrayList<double[]> reflectionOY() {
        ArrayList<double[]> result = new ArrayList<>();

        result.add(new double[] { -1, 0, 0, 0 });
        result.add(new double[] { 0, 1, 0, 0 });
        result.add(new double[] { 0, 0, -1, 0 });
        result.add(new double[] { 0, 0, 0, 1 });

        return result;
    }

    private ArrayList<double[]> reflectionOZ() {
        ArrayList<double[]> result = new ArrayList<>();

        result.add(new double[] { -1, 0, 0, 0 });
        result.add(new double[] { 0, -1, 0, 0 });
        result.add(new double[] { 0, 0, 1, 0 });
        result.add(new double[] { 0, 0, 0, 1 });

        return result;
    }

    private ArrayList<double[]> reflectionOO() {
        ArrayList<double[]> result = new ArrayList<>();

        result.add(new double[] { -1, 0, 0, 0 });
        result.add(new double[] { 0, -1, 0, 0 });
        result.add(new double[] { 0, 0, -1, 0 });
        result.add(new double[] { 0, 0, 0, 1 });

        return result;
    }

    private ArrayList<double[]> rotationOZ(double phi) {
        ArrayList<double[]> result = new ArrayList<>();

        result.add(new double[] { Math.cos(Math.toRadians(phi)), -Math.sin(Math.toRadians(phi)), 0, 0 });
        result.add(new double[] { Math.sin(Math.toRadians(phi)), Math.cos(Math.toRadians(phi)), 0, 0 });
        result.add(new double[] { 0, 0, 1, 0 });
        result.add(new double[] { 0, 0, 0, 1 });

        return result;
    }

    private ArrayList<double[]> rotationOX(double phi) {
        ArrayList<double[]> result = new ArrayList<>();

        result.add(new double[] { 1, 0, 0, 0 });
        result.add(new double[] { 0, Math.cos(Math.toRadians(phi)), -Math.sin(Math.toRadians(phi)), 0 });
        result.add(new double[] { 0, Math.sin(Math.toRadians(phi)), Math.cos(Math.toRadians(phi)), 0 });
        result.add(new double[] { 0, 0, 0, 1 });

        return result;
    }

    private ArrayList<double[]> rotationOY(double phi) {
        ArrayList<double[]> result = new ArrayList<>();

        result.add(new double[] { Math.cos(Math.toRadians(phi)), 0, Math.sin(Math.toRadians(phi)), 0 });
        result.add(new double[] { 0, 1, 0, 0 });
        result.add(new double[] { -Math.sin(Math.toRadians(phi)), 0, Math.cos(Math.toRadians(phi)), 0 });
        result.add(new double[] { 0, 0, 0, 1 });

        return result;
    }
}
