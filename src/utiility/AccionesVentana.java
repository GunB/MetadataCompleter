package utiility;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public final class AccionesVentana {

    int clicx = 0;
    int clicy = 0;
    boolean ex = true;

    public void MouseReleased(JFrame ventana) {
        MoverMouse.main(ventana.getLocation().x + clicx, ventana.getLocation().y + clicy);
        ex = true;
        try {
            Thread.sleep(100);
        } catch (@SuppressWarnings("LocalVariableHidesMemberVariable") InterruptedException ex) {

        }
        ventana.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    public void VentanaTransparente(JFrame ventana) {
        //AWTUtilities.setWindowOpaque(ventana, false);
    }

    public void MouseDragged(JFrame ventana, MouseEvent evt) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        if (ex) {
            ex = false;
            Dimension dim = toolkit.getBestCursorSize(1, 1);
            BufferedImage cursorImg = new BufferedImage(dim.width, dim.height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = cursorImg.createGraphics();
            g2d.setBackground(new Color(0.0f, 0.0f, 0.0f, 0.0f));
            g2d.clearRect(0, 0, dim.width, dim.height);
            g2d.dispose();
            Cursor hiddenCursor = toolkit.createCustomCursor(cursorImg, new Point(0, 0), "hiddenCursor");
            ventana.setCursor(hiddenCursor);
            MoverMouse.main(ventana.getLocation().x, ventana.getLocation().y);
            try {
                Thread.sleep(50);
            } catch (Exception e) {
            }
        } else {
            ventana.setLocation(evt.getXOnScreen(), evt.getYOnScreen());
        }
    }

    public void MousePressed(JFrame ventana, MouseEvent evt) {
        clicx = evt.getX();
        clicy = evt.getY();
    }

    public void CentrarVentana(JFrame ventana) {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        ventana.setLocation(d.width / 2 - ventana.getSize().width / 2, d.height / 2 - ventana.getSize().height / 2);
        //ventana.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/tecnovis_test/Icono.png"))); 
    }

    static public void LooknFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    public AccionesVentana(JFrame ventana, String Titulo) {
        ventana.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/favico.png")));
        CentrarVentana(ventana);
        ventana.setTitle(Titulo);
        ventana.setMinimumSize(ventana.getSize());
        //
    }
}
