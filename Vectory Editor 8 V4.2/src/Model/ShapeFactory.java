/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author user
 */
public class ShapeFactory {
   //static Shape Element;
   static public ArrayList<Shape> Elements=new ArrayList<Shape>();
   static{
       //Elements.add(new Line());
       Elements.add(new Rectangle());
       //Elements.add(new Ellipse());
       Elements.add(new Ellipse2());
       Elements.add(new Triangle());
       Elements.add(new PolyLine());
       Elements.add(new Bezier2());
       Elements.add(new PolyGone());
       Elements.add(new Line2());
   }
   public static Shape venom(String Button){  //обязательно с маленькой буквы название
       for(int i=0;i<Elements.size();i++){
           if(Elements.get(i).isType(Button)){
               try {    
                   return Elements.get(i).getClass().newInstance();
                           } catch (InstantiationException ex) {
                   Logger.getLogger(ShapeFactory.class.getName()).log(Level.SEVERE, null, ex);
               } catch (IllegalAccessException ex) {
                   Logger.getLogger(ShapeFactory.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
       }
       return null;
   }
   public Shape reborn (Shape ActiveShape){
       for(int i=0;i<Elements.size();i++){
           if(Elements.get(i).isType(ActiveShape.type)){
               try {    
                   return Elements.get(i).getClass().newInstance();
                           } catch (InstantiationException ex) {
                   Logger.getLogger(ShapeFactory.class.getName()).log(Level.SEVERE, null, ex);
               } catch (IllegalAccessException ex) {
                   Logger.getLogger(ShapeFactory.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
       }
       return null;
   } 
}
