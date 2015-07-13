/*    */ package utiility;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.Cursor;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Graphics2D;
/*    */ import java.awt.Image;
/*    */ import java.awt.Point;
/*    */ import java.awt.Toolkit;
/*    */ import java.awt.event.MouseEvent;
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.net.URL;
/*    */ import javax.swing.JFrame;
/*    */ import javax.swing.UIManager;
/*    */ import javax.swing.UnsupportedLookAndFeelException;
/*    */ 
/*    */ public final class AccionesVentana
/*    */ {
/* 19 */   int clicx = 0;
/* 20 */   int clicy = 0;
/* 21 */   boolean ex = true;
/*    */   
/*    */   public void MouseReleased(JFrame ventana) {
/* 24 */     MoverMouse.main(ventana.getLocation().x + this.clicx, ventana.getLocation().y + this.clicy);
/* 25 */     this.ex = true;
/*    */     try {
/* 27 */       Thread.sleep(100L);
/*    */     }
/*    */     catch (InterruptedException localInterruptedException) {}
/*    */     
/* 31 */     ventana.setCursor(Cursor.getPredefinedCursor(0));
/*    */   }
/*    */   
/*    */ 
/*    */   public void VentanaTransparente(JFrame ventana) {}
/*    */   
/*    */   public void MouseDragged(JFrame ventana, MouseEvent evt)
/*    */   {
/* 39 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/* 40 */     if (this.ex) {
/* 41 */       this.ex = false;
/* 42 */       Dimension dim = toolkit.getBestCursorSize(1, 1);
/* 43 */       BufferedImage cursorImg = new BufferedImage(dim.width, dim.height, 2);
/* 44 */       Graphics2D g2d = cursorImg.createGraphics();
/* 45 */       g2d.setBackground(new Color(0.0F, 0.0F, 0.0F, 0.0F));
/* 46 */       g2d.clearRect(0, 0, dim.width, dim.height);
/* 47 */       g2d.dispose();
/* 48 */       Cursor hiddenCursor = toolkit.createCustomCursor(cursorImg, new Point(0, 0), "hiddenCursor");
/* 49 */       ventana.setCursor(hiddenCursor);
/* 50 */       MoverMouse.main(ventana.getLocation().x, ventana.getLocation().y);
/*    */       try {
/* 52 */         Thread.sleep(50L);
/*    */       }
/*    */       catch (Exception localException) {}
/*    */     } else {
/* 56 */       ventana.setLocation(evt.getXOnScreen(), evt.getYOnScreen());
/*    */     }
/*    */   }
/*    */   
/*    */   public void MousePressed(JFrame ventana, MouseEvent evt) {
/* 61 */     this.clicx = evt.getX();
/* 62 */     this.clicy = evt.getY();
/*    */   }
/*    */   
/*    */   public void CentrarVentana(JFrame ventana) {
/* 66 */     Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
/* 67 */     ventana.setLocation(d.width / 2 - ventana.getSize().width / 2, d.height / 2 - ventana.getSize().height / 2);
/*    */   }
/*    */   
/*    */   public static void LooknFeel()
/*    */   {
/*    */     try {
/* 73 */       UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
/*    */     } catch (ClassNotFoundException|InstantiationException|IllegalAccessException|UnsupportedLookAndFeelException e) {
/* 75 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */   
/*    */   public AccionesVentana(JFrame ventana, String Titulo)
/*    */   {
/* 81 */     URL url = getClass().getResource("/img/favico.png");
/* 82 */     Toolkit kit = Toolkit.getDefaultToolkit();
/* 83 */     Image img = kit.createImage(url);
/* 84 */     ventana.setIconImage(img);
/*    */     
/* 86 */     CentrarVentana(ventana);
/* 87 */     ventana.setTitle(Titulo);
/* 88 */     ventana.setMinimumSize(ventana.getSize());
/*    */   }
/*    */ }

