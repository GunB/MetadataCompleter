package utiility;

import java.awt.Robot;

public class MoverMouse {

    public static void main(int x, int y) {

        Robot robot = null;
        try {
            robot = new Robot();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
            robot.mouseMove(x, y);
        

    }
}
