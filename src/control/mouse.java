package control;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;

public class mouse extends Applet implements MouseListener, MouseMotionListener, MouseWheelListener {

	private String msg = "";
	private int mouseX = 0, mouseY = 0; // координаты курсора мыши

	public void init() {
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	// обработать событие от щелчка кнопкой мыши
	public void mouseClicked(MouseEvent me) {
		// сохранить координаты
		mouseX = me.getX();
		mouseY = me.getY();

		print.COUNT_X_POINTS -= 5;
		print.COUNT_Y_POINTS -= 5;

		// second.out.println("Clicked!");

		print.repWindow();
	}

	// обработать событие наведения курсора мыши
	public void mouseEntered(MouseEvent me) {

	}

	// обработать событие отведения курсора мыши
	public void mouseExited(MouseEvent me) {

	}

	// обработать событие нажатия кнопки мыши
	public void mousePressed(MouseEvent me) {
		// сохранить координаты
		mouseX = me.getX();
		mouseY = me.getY();
	}

	// обработать событие отпускания кнопки мыши
	public void mouseReleased(MouseEvent me) {
		// сохранить координаты
		mouseX = me.getX();
		mouseY = me.getY();
		msg = "Up"; // Кнопка мыши отпущена
	}

	// обработать событие перетаскивания курсора мыши
	public void mouseDragged(MouseEvent me) {
		int dx = me.getX() - mouseX;
		int dy = me.getY() - mouseY;

		print.POS_X = dx;
		print.POS_Y = dy;
		print.repWindow();
	}

	// обработать событие перемещения мыши
	public void mouseMoved(MouseEvent me) {
		// super.mouseMoved(me);
	}

	public void mouseWheelMoved(MouseWheelEvent me) {
		if (me.getWheelRotation() < 0) {
			print.COUNT_X_POINTS += 5;
			print.COUNT_Y_POINTS += 5;
			print.COUNT_Z_POINTS += 5;
		} else {
			if (print.COUNT_X_POINTS > 5 && print.COUNT_Y_POINTS > 5 && print.COUNT_Z_POINTS > 5) {
				print.COUNT_X_POINTS -= 5;
				print.COUNT_Y_POINTS -= 5;
				print.COUNT_Z_POINTS -= 5;
			}
		}

		print.repWindow();
	}

	
	public void paint(Graphics g) {
		g.drawString(msg, mouseX, mouseY);
	}

}