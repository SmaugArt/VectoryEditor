/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassOfObjects;
import Model.Bezier2;
import Model.Shape;
import Model.ShapeFactory;
import View.MainWindow;
import java.awt.Color; 
import java.awt.Graphics; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*; 


public class ShapeButton extends JButton implements ActionListener
{ 
   MainWindow base; 
   public String type = "Shape"; 
   public ShapeButton (String type, MainWindow base)//MainWindow - наше рабочее окно, его мы передаем для работы и отрисовке на нем кнопок.
   { 
      this.base = base; 
      this.type = type;
      this.addActionListener(this);
   } 
   @Override protected void paintComponent(Graphics g) 
   { 
      if(getModel().isArmed()) 
      { 
         g.setColor(Color.BLUE); 
         setForeground(Color.BLUE);
      } 
      else 
      { 
         g.setColor(getBackground()); 
         setForeground(Color.black); //если не нажата кнопка, то фигура в ней черная
      } 

      super.paintComponent(g); 
   } 
   @Override protected void paintBorder(Graphics g) 
   { 
      if ("Line2".equals(type)) 
      { 
         g.setColor(getForeground()); 
         g.drawLine(25, 14, 80, 19); 
         g.drawLine(25, 15, 80, 20); 
      } 
      if ("Rectangle".equals(type)) 
      { 
         g.setColor(getForeground()); 
         g.drawRect(25, 9, 55, 20); 
         g.drawRect(24, 8, 57, 22); 
      } 
      if ("Triangle".equals(type)) 
      { 
         g.setColor(getForeground()); 
         g.drawLine(25, 9, 25, 29); 
         g.drawLine(26, 9, 26, 29); 
         g.drawLine(25, 29, 80, 29); 
         g.drawLine(25, 28, 80, 28); 
         g.drawLine(26, 9, 80, 29); 
         g.drawLine(25, 8, 80, 28); 

      } 
      if ("Ellipse2".equals(type)) 
      { 
         g.setColor(getForeground()); 
         g.drawOval(25, 9, 55, 20); 
         g.drawOval(24, 8, 57, 22); 
      } 
      if ("PolyLine".equals(type)) 
      { 
         g.setColor(getForeground()); 
         g.drawLine(10, 30, 25, 14); 
         g.drawLine(11, 30, 26, 14);
         g.drawLine(9, 30, 24, 14);
         g.drawLine(25, 14, 34, 28); 
         g.drawLine(26, 14, 35, 28); 
         g.drawLine(24, 14, 33, 28);
         g.drawLine(34, 28, 50, 9);
         g.drawLine(35, 28, 51, 9);
         g.drawLine(33, 28, 49, 9);
         g.drawLine(50, 9, 94, 25);
         g.drawLine(51, 9, 95, 25); 
         g.drawLine(49, 9, 93, 25);
         g.drawLine(50, 8, 96, 25);
         g.drawLine(51, 8, 97, 25);
      } 
      if ("Bezier2".equals(type)) 
      { 
         Bezier2 Element=new Bezier2();
         Element.firstclick(10, 30,0);
         Element.lastclick(40, 8);
         Element.addPoint();
         Element.lastclick(60, 8);
         Element.addPoint();
         Element.lastclick(95, 30);
         Element.addPoint();
         Element.backstep();
         Element.paint(g,0,0,0);
      } 
      if ("PolyGone".equals(type)) 
      { 
         g.setColor(getForeground()); 
         g.drawLine(10, 30, 25, 14); 
         g.drawLine(11, 30, 26, 14);
         g.drawLine(9, 30, 24, 14);
         
         g.drawLine(25, 14, 34, 8); 
         g.drawLine(26, 14, 35, 8); 
         g.drawLine(24, 14, 33, 8);
         
         g.drawLine(34, 7, 50, 8);
         g.drawLine(35, 8, 51, 9);
         g.drawLine(33, 7, 49, 8);
         
         g.drawLine(50, 9, 94, 25);
         g.drawLine(51, 9, 95, 25); 
         
         g.drawLine(49, 9, 93, 25);
         g.drawLine(50, 8, 96, 25);
         g.drawLine(51, 8, 97, 25);
         
         //g.drawLine(95, 23,8,28);
         g.drawLine(96, 24,10,29);
         g.drawLine(97, 25,9,30);
      } 
   } 

    @Override
    public void actionPerformed(ActionEvent e) {
        if (base.typeOfOperation != 1){                                         //обновление рисунка если выбрана деформация
           base.typeOfOperation=0;
           base.drawPanel.drawBuffer(base.ShapeAL, base.ActiveShape);
           base.drawPanel.repaint();
        }
        base.typeOfOperation=1;
        base.ActiveShape=ShapeFactory.venom(this.type);
    }
} //10 95  38=8