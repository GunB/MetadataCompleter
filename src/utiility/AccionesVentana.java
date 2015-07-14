package utiility;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public final class AccionesVentana {

    int clicx = 0;
    int clicy = 0;
    boolean ex = true;

    public void MouseReleased(JFrame ventana) {
        MoverMouse.main(ventana.getLocation().x + this.clicx, ventana.getLocation().y + this.clicy);
        this.ex = true;
        try {
            Thread.sleep(100L);
        } catch (InterruptedException localInterruptedException) {
        }

        ventana.setCursor(Cursor.getPredefinedCursor(0));
    }

    public void VentanaTransparente(JFrame ventana) {
    }

    public void MouseDragged(JFrame ventana, MouseEvent evt) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        if (this.ex) {
            this.ex = false;
            Dimension dim = toolkit.getBestCursorSize(1, 1);
            BufferedImage cursorImg = new BufferedImage(dim.width, dim.height, 2);
            Graphics2D g2d = cursorImg.createGraphics();
            g2d.setBackground(new Color(0.0F, 0.0F, 0.0F, 0.0F));
            g2d.clearRect(0, 0, dim.width, dim.height);
            g2d.dispose();
            Cursor hiddenCursor = toolkit.createCustomCursor(cursorImg, new Point(0, 0), "hiddenCursor");
            ventana.setCursor(hiddenCursor);
            MoverMouse.main(ventana.getLocation().x, ventana.getLocation().y);
            try {
                Thread.sleep(50L);
            } catch (Exception localException) {
            }
        } else {
            ventana.setLocation(evt.getXOnScreen(), evt.getYOnScreen());
        }
    }

    public void MousePressed(JFrame ventana, MouseEvent evt) {
        this.clicx = evt.getX();
        this.clicy = evt.getY();
    }

    public void CentrarVentana(JFrame ventana) {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        ventana.setLocation(d.width / 2 - ventana.getSize().width / 2, d.height / 2 - ventana.getSize().height / 2);
    }

    public static void LooknFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    public AccionesVentana(JFrame ventana, String Titulo) {
        URL url = getClass().getResource("/img/favico.png");
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image img = kit.createImage(url);
        ventana.setIconImage(img);

        CentrarVentana(ventana);
        ventana.setTitle(Titulo);
        ventana.setMinimumSize(ventana.getSize());
    }
}
