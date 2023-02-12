package math;

import java.util.ArrayList;
import java.util.Scanner;

import camera.Camera3D;

public class Model {
    private int countVertex;
    private int countEdge;
    private int countFaces;
    private ArrayList<Coordinates> vertexList = new ArrayList<Coordinates>();
    private ArrayList<int[]> edgeList = new ArrayList<int[]>();
    private ArrayList<int[]> facesList = new ArrayList<int[]>();
    private int k_max;
    private ArrayList<double[]> projectionCoordinates = new ArrayList<double[]>();

    public Model(int countV, int countE) {
        countVertex = countV;
        countEdge = countE;
        k_max = 1;
    }

    public int getCountVertex() {
        return countVertex;
    }

    public int getCountEdge() {
        return countEdge;
    }

    public int getCountFaces() {
        return countFaces;
    }

    public ArrayList<int[]> getFacesList() {
        return facesList;
    }

    public int getEdgesFirst(int pos) {
        return edgeList.get(pos)[0];
    }

    public int getEdgesSecond(int pos) {
        return edgeList.get(pos)[1];
    }

    public ArrayList<int[]> getEdgeList() {
        return edgeList;
    }

    public double getXVertex(int pos) {
        return vertexList.get(pos).getX();
    }

    public double getYVertex(int pos) {
        return vertexList.get(pos).getY();
    }

    public double getZVertex(int pos) {
        return vertexList.get(pos).getZ();
    }

    public ArrayList<Coordinates> getVertexList() {
        return vertexList;
    }

    public int getK_max() {
        return k_max;
    }

    public ArrayList<double[]> getProjectionCoordinates() {
        return projectionCoordinates;
    }

    public void setProjectionCoordinates(Camera3D cam) {
        // Camera3D cam = new Camera3D();
        double[] result = new double[3];
        double sum = 0;

        for (int i = 0; i < vertexList.size(); i++) {
            result = new double[3];

            for (int j = 0; j < 3; j++) {

                for (int k = 0; k < 4; k++) {
                    sum += vertexList.get(i).getElem(k) * cam.getWorlToProjection().get(j)[k];
                }
                result[j] = sum;
                sum = 0;
            }

            result[0] = result[0] / result[2];
            result[1] = result[1] / result[2];
            result[2] = 1;

            projectionCoordinates.add(result);
        }
    }

    public void setEdgeList(ArrayList<int[]> eList) {
        edgeList = eList;
    }

    public void setCoordinatesList(ArrayList<double[]> points) {
        double coordinates = 0;
        vertexList.clear();

        for (int i = 0; i < countVertex; i++) {
            Coordinates newCoordinates = new Coordinates();

            coordinates = points.get(i)[0];
            newCoordinates.setX(coordinates);
            coordinates = points.get(i)[1];
            newCoordinates.setY(coordinates);
            coordinates = points.get(i)[2];
            newCoordinates.setZ(coordinates);
            coordinates = points.get(i)[3];
            newCoordinates.setK((int) coordinates);

            vertexList.add(newCoordinates);
        }
    }

    public ArrayList<double[]> getListCoordinates() {
        ArrayList<double[]> result = new ArrayList<>();

        for (int i = 0; i < countVertex; i++) {
            result.add(vertexList.get(i).getListCoordinates());
        }

        return result;
    }

    public void addVertex() {
        System.out.println("Enter x and y without ',' " + countVertex + " times: ");
        Scanner in = new Scanner(System.in);

        for (int i = 0; i < countVertex; i++) {
            Coordinates coordinates = new Coordinates();

            double x = 0, y = 0, z = 0;

            x = in.nextDouble();
            y = in.nextDouble();
            z = in.nextDouble();

            coordinates.setCoordinates(x, y, z);
            vertexList.add(coordinates);

            if (k_max < coordinates.getK())
                k_max = coordinates.getK();

        }

        for (int i = 0; i < countVertex; i++) {
            vertexList.get(i).recalculationOfMaxK(k_max);
        }

        in.close();
    }

    public void addEdgesExample() {
        edgeList.add(new int[] { 0, 1 });
        edgeList.add(new int[] { 1, 2 });
        edgeList.add(new int[] { 2, 0 });
        edgeList.add(new int[] { 3, 4 });
        edgeList.add(new int[] { 4, 5 });
        edgeList.add(new int[] { 5, 3 });
        edgeList.add(new int[] { 0, 5 });
        edgeList.add(new int[] { 1, 5 });
        edgeList.add(new int[] { 0, 3 });
        edgeList.add(new int[] { 1, 4 });
        edgeList.add(new int[] { 2, 3 });
        edgeList.add(new int[] { 2, 4 });
        countEdge = 12;
    }

    public void addVertexExample(Camera3D cam) {
        Coordinates coordinates = new Coordinates();

        coordinates.setCoordinates(6, 0, 0); // 0
        vertexList.add(coordinates);

        if (k_max < coordinates.getK())
            k_max = coordinates.getK();

        coordinates = new Coordinates();

        coordinates.setCoordinates(-6, 0, 0); // 1
        vertexList.add(coordinates);

        if (k_max < coordinates.getK())
            k_max = coordinates.getK();

        coordinates = new Coordinates();

        coordinates.setCoordinates(0, 6, 0); // 2
        vertexList.add(coordinates);

        if (k_max < coordinates.getK())
            k_max = coordinates.getK();

        coordinates = new Coordinates();

        coordinates.setCoordinates(6, 6, 10); // 3
        vertexList.add(coordinates);

        if (k_max < coordinates.getK())
            k_max = coordinates.getK();

        coordinates = new Coordinates();

        coordinates.setCoordinates(-6, 6, 10); // 4
        vertexList.add(coordinates);

        if (k_max < coordinates.getK())
            k_max = coordinates.getK();

        coordinates = new Coordinates();

        coordinates.setCoordinates(0, 0, 10); // 5
        vertexList.add(coordinates);

        if (k_max < coordinates.getK())
            k_max = coordinates.getK();

        coordinates = new Coordinates();

        if (k_max < coordinates.getK())
            k_max = coordinates.getK();

        setProjectionCoordinates(cam);
    }

    public void addFaces(int point) {
        for (int i = 0; i < countVertex; i++) {

        }
    }

    public void print() {
        System.out.println("Vertex: ");
        for (int i = 0; i < countVertex; i++) {
            System.out.println("______________________________");
            vertexList.get(i).printCoordinates();
            System.out.println("______________________________");
        }

        System.out.println("Edges: ");
        for (int i = 0; i < countEdge; i++) {
            System.out.println("______________________________");
            System.out.println(edgeList.get(i)[0] + " " + edgeList.get(i)[1]);
            System.out.println("______________________________");
        }
    }
}
