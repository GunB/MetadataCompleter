/*    */ package utiility;
/*    */ 
/*    */ import java.awt.Robot;
/*    */ 
/*    */ public class MoverMouse
/*    */ {
/*    */   public static void main(int x, int y)
/*    */   {
/*  9 */     Robot robot = null;
/*    */     try {
/* 11 */       robot = new Robot();
/*    */     } catch (Exception e) {
/* 13 */       System.out.println(e.toString());
/*    */     }
/* 15 */     robot.mouseMove(x, y);
/*    */   }
/*    */ }
