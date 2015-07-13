/*    */ package model;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XMLTag
/*    */ {
/*    */   String strName;
/*    */   ArrayList<String> arrAttr;
/*    */   
/*    */   public XMLTag(String strName)
/*    */   {
/* 20 */     this.strName = strName;
/*    */   }
/*    */   
/*    */   public String getStrName() {
/* 24 */     return this.strName;
/*    */   }
/*    */   
/*    */   public void setStrName(String strName) {
/* 28 */     this.strName = strName;
/*    */   }
/*    */   
/*    */   public ArrayList<String> getArrAttr() {
/* 32 */     return this.arrAttr;
/*    */   }
/*    */   
/*    */   public void setArrAttr(ArrayList<String> arrAttr) {
/* 36 */     this.arrAttr = arrAttr;
/*    */   }
/*    */   
/*    */   public String returnFullTagData(String strData) {
/* 40 */     return "<" + this.strName + ">" + strData + "</" + this.strName + ">";
/*    */   }
/*    */ }


/* Location:              C:\Users\hangarita\Desktop\eFixer.jar!\model\XMLTag.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */