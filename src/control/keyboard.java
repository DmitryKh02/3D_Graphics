package control;

import java.awt.event.*;
import javax.swing.JFrame;

public class keyboard extends JFrame implements KeyListener { // расширяем абстрактный класс KeyAdapter

    @Override
    public void keyPressed(KeyEvent event) {
        int key = event.getKeyCode();

        switch (key) {
            case KeyEvent.VK_W:
                print.moveFigure(0, 1, 0);
                break;
            case KeyEvent.VK_A:
                print.moveFigure(-1, 0, 0);
                break;
            case KeyEvent.VK_S:
                print.moveFigure(0, -1, 0);
                break;
            case KeyEvent.VK_D:
                print.moveFigure(1, 0, 0);
                break;
            case KeyEvent.VK_Q:
                print.moveFigure(0, 0, 1);
                break;
            case KeyEvent.VK_E:
                print.moveFigure(0, 0, -1);
                break;
            case KeyEvent.VK_O:
                print.rotationFigure(-0.2, 0); // OX
                break;
            case KeyEvent.VK_P:
                print.rotationFigure(0.2, 0); // OX
                break;
            case KeyEvent.VK_K:
                print.rotationFigure(-0.2, 1); // OY
                break;
            case KeyEvent.VK_L:
                print.rotationFigure(0.2, 1); // OY
                break;
            case KeyEvent.VK_N:
                print.rotationFigure(-0.2, 2); // OZ
                break;
            case KeyEvent.VK_M:
                print.rotationFigure(0.2, 2); // OZ
                break;
            case KeyEvent.VK_0: // 0.0
                print.axisReflectionFigure(0);
                break;
            case KeyEvent.VK_1: // OX
                print.axisReflectionFigure(1);
                break;
            case KeyEvent.VK_2: // OY
                print.axisReflectionFigure(2);
                break;
            case KeyEvent.VK_3: // OZ
                print.axisReflectionFigure(3);
                break;
            case KeyEvent.VK_4: // YOZ
                print.axisReflectionFigure(4);
                break;
            case KeyEvent.VK_5: // ZOX
                print.axisReflectionFigure(5);
                break;
            case KeyEvent.VK_6: // XOY
                print.axisReflectionFigure(6);
                break;
            case KeyEvent.VK_UP:
                print.scaleFigure(1, 1.1, 1);
                break;
            case KeyEvent.VK_DOWN:
                print.scaleFigure(1, 0.9, 1);
                break;
            case KeyEvent.VK_RIGHT:
                print.scaleFigure(1.1, 1, 1);
                break;
            case KeyEvent.VK_LEFT:
                print.scaleFigure(0.9, 1, 1);
                break;
            case KeyEvent.VK_8:
                print.scaleFigure(1, 1, 1.1);
                break;
            case KeyEvent.VK_9:
                print.scaleFigure(1, 1, 0.9);
                break;
        }
        print.repWindow();
    }

    @Override
    public void keyTyped(KeyEvent event) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}