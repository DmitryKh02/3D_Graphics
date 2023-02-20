package control;

import camera.Camera3D;
import math.Affine;
import math.Model;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class print extends JPanel {
    private static final int PREF_W = 2000; // ширина
    private static final int PREF_H = 2000; // высота
    private static final int L = 30; // Граница Слева
    private static final int R = 30; // Граница Справа
    private static final int B = 30; // Граница Снизу
    private static final int T = 30; // Граница Сверху

    public static int POS_X = 0; // позиция курсора
    public static int POS_Y = 0;
    private static int POS_ZERO_X = 0; // позиция центра
    private static int POS_ZERO_Y = 0;
    private static int DASH_WIDTH = 10; // ширина черточек

    private static final Color GRAPH_COLOR = Color.black; // цвет графика
    private static final Color GRAPH_POINT_COLOR = new Color(0, 50, 50, 200); // цвет точек
    private static final Stroke GRAPH_STROKE = new BasicStroke(3f); // Ширина (Жирность) линий
    private static final int GRAPH_POINT_WIDTH = 20; // Диаметр точек

    public static int COUNT_X_POINTS = 50; // Количество точек по X
    public static int COUNT_Y_POINTS = 50; // Количество точек по Y
    public static int COUNT_Z_POINTS = 50; // Количество точек по Z

    public static Camera3D cam = new Camera3D(COUNT_X_POINTS, COUNT_Y_POINTS, COUNT_Z_POINTS);
    public static Model model = new Model(6, 12);
    public static JFrame frame;
    private static mouse mMouse;

    public print() {
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(PREF_W, PREF_H);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGui();
            }
        });
    }

    private static void createAndShowGui() {
        print mainPanel = new print();

        mMouse = new mouse();
        mMouse.init();

        model.addVertexExample(cam);
        model.addEdgesExample();

        mainPanel.addMouseListener(mMouse);
        mainPanel.addMouseMotionListener(mMouse);
        mainPanel.addMouseWheelListener(mMouse);

        frame = new JFrame("second");
        frame.addKeyListener(new keyboard());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    static public void repWindow() {
        cam.setAll(COUNT_X_POINTS, COUNT_Y_POINTS, COUNT_Z_POINTS);
        frame.repaint();
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Масштаб модели
        double Scale = (((double) getHeight() - T - B) / (COUNT_Y_POINTS - 1));

        // Отрисовка границ графика
        g2.drawLine(L, T, L, getHeight() - B);
        g2.drawLine(L, B, getWidth() - R, B);
        g2.drawLine(L, getHeight() - B, getWidth() - R, getHeight() - B);
        g2.drawLine(getWidth() - R, getHeight() - B, getWidth() - R, T);

        // создание осей Х, Y, Z
        g2.drawLine((getWidth() - L - R) / 2 + L + POS_X, getHeight() - B, (getWidth() - L - R) / 2 + L + POS_X, T);
        g2.drawLine(L, (getHeight() - B - T) / 2 + T + POS_Y, getWidth() - R, (getHeight() - B - T) / 2 + T + POS_Y);
        // g2.drawLine(getWidth() - R + POS_X, L + POS_Y, T + POS_X, getHeight() - B +
        // POS_Y);

        // Установка начала осей координат
        POS_ZERO_X = (getWidth() - L - R) / 2 + L + POS_X;
        POS_ZERO_Y = (getHeight() - B - T) / 2 + T + POS_Y;

        // Штриховка на оси X
        for (int i = 0; i < COUNT_X_POINTS * 4; i++) {
            int x0 = (int) (i * Scale * cam.getD() / cam.getOv()[0]) + POS_ZERO_X;
            int y0 = POS_ZERO_Y - DASH_WIDTH;
            int x1 = x0;
            int y1 = y0 + 2 * DASH_WIDTH;

            if ((x0 > L) && (x0 < (getWidth() - R)))
                g2.drawLine(x0, y0, x1, y1);

            x0 = POS_ZERO_X - (int) (i * Scale * cam.getD() / cam.getOv()[0]);
            y0 = POS_ZERO_Y - DASH_WIDTH;
            x1 = x0;
            y1 = y0 + 2 * DASH_WIDTH;

            if ((x0 > L) && (x0 < (getWidth() - R)))
                g2.drawLine(x0, y0, x1, y1);
        }

        // Штриховка на оси Y
        for (int i = 0; i < COUNT_Y_POINTS * 4; i++) {
            int x0 = POS_ZERO_X - DASH_WIDTH;
            int y0 = (int) (i * Scale * cam.getD() / cam.getOv()[1]) + POS_ZERO_Y;
            int x1 = x0 + 2 * DASH_WIDTH;
            int y1 = y0;

            if ((y0 > T) && (y0 < (getHeight() - B)))
                g2.drawLine(x0, y0, x1, y1);

            x0 = POS_ZERO_X - DASH_WIDTH;
            y0 = POS_ZERO_Y - (int) (i * Scale * cam.getD() / cam.getOv()[1]);
            x1 = x0 + 2 * DASH_WIDTH;
            y1 = y0;

            if ((y0 > T) && (y0 < (getHeight() - B)))
                g2.drawLine(x0, y0, x1, y1);
        }

        // Штриховка на оси Z
        for (int i = 0; i < COUNT_Z_POINTS * 4; i++) {
            int x0 = POS_ZERO_X + (int) (i * Scale * 0.625) - DASH_WIDTH;
            int y0 = POS_ZERO_Y - (int) (i * Scale * 0.625) - DASH_WIDTH;
            int x1 = x0 + 2 * DASH_WIDTH;
            int y1 = y0 + 2 * DASH_WIDTH;

            if ((y0 > T) && (y0 < (getHeight() - B)) && (x0 > L) && (x0 < (getWidth() - R)))
                g2.drawLine(x0, y0, x1, y1);

            x0 = POS_ZERO_X - (int) (i * Scale * 0.625) - DASH_WIDTH;
            y0 = POS_ZERO_Y + (int) (i * Scale * 0.625) - DASH_WIDTH;
            x1 = x0 + 2 * DASH_WIDTH;
            y1 = y0 + 2 * DASH_WIDTH;

            if ((y0 > T) && (y0 < (getHeight() - B)) && (x0 > L) && (x0 < (getWidth() - R)))
                g2.drawLine(x0, y0, x1, y1);
        }

        // Заполнение списка точками
        List<Point> graphPoints = new ArrayList<Point>();

        for (int i = 0; i < model.getCountVertex(); i++) {
            int x1 = (int) ((model.getProjectionCoordinates().get(i)[0] + cam.getD()) * Scale) + POS_ZERO_X;
            int y1 = POS_ZERO_Y - (int) ((model.getProjectionCoordinates().get(i)[1] + cam.getD()) * Scale);

            graphPoints.add(new Point(x1, y1));
        }

        Stroke oldStroke = g2.getStroke();
        g2.setColor(GRAPH_COLOR);
        g2.setStroke(GRAPH_STROKE);

        printModelEdges(graphPoints, g2);

        g2.setStroke(oldStroke);
        g2.setColor(GRAPH_POINT_COLOR);

        printModelVertex(graphPoints, g2);

    }

    // Отрисовка точек
    public void printModelVertex(List<Point> graphPoints, Graphics g2) {

        for (Point graphPoint : graphPoints) {
            int x = graphPoint.x - GRAPH_POINT_WIDTH / 2;
            int y = graphPoint.y - GRAPH_POINT_WIDTH / 2;

            if ((x > L) && (x < (getWidth() - R)) && (y > T) && (y < (getHeight() - B)))
                g2.fillOval(x, y, GRAPH_POINT_WIDTH, GRAPH_POINT_WIDTH);
        }
    }

    // Отрисовка граней
    public void printModelEdges(List<Point> graphPoints, Graphics g2) {
        for (int i = 0; i < model.getCountEdge(); i++) {

            int x1 = graphPoints.get(model.getEdgesFirst(i)).x;
            int y1 = graphPoints.get(model.getEdgesFirst(i)).y;

            if ((x1 > L) && (x1 < (getWidth() - R)) && (y1 > T) && (y1 < (getHeight() - B))) {
                int x2 = graphPoints.get(model.getEdgesSecond(i)).x;
                int y2 = graphPoints.get(model.getEdgesSecond(i)).y;

                if ((x2 > L) && (x2 < (getWidth() - R)) && (y2 > T) && (y2 < (getHeight() - B)))
                    g2.drawLine(x1, y1, x2, y2);
            }
        }
    }

    public static void moveFigure(int x_coordinates, int y_coordinates, int z_coordinates) {
        Affine res = new Affine();
        res.move(x_coordinates, y_coordinates, z_coordinates, model);
        model = res.getModel();
        model.setProjectionCoordinates(cam);
    }

    public static void rotationFigure(double corner, int typeOfAxis) {
        Affine res = new Affine();

        switch (typeOfAxis) {
            case 0:
                res.rotationFigOX(corner, model);
                break;
            case 1:
                res.rotationFigOY(corner, model);
                break;
            case 2:
                res.rotationFigOZ(corner, model);
                break;
            default:
                break;
        }

        model = res.getModel();
        model.setProjectionCoordinates(cam);
    }

    public static void axisReflectionFigure(int typeOfAxis) {
        Affine res = new Affine();

        res.reflection(typeOfAxis, model);
        model = res.getModel();
        model.setProjectionCoordinates(cam);
    }

    public static void scaleFigure(double x_scale, double y_scale, double z_scale) {
        Affine res = new Affine();

        res.scale(x_scale, y_scale, z_scale, model);
        model = res.getModel();
        model.setProjectionCoordinates(cam);
    }
}
