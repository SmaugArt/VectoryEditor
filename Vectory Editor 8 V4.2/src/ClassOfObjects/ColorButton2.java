/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassOfObjects;

import View.MainWindow;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;

/**
 *
 * @author demin
 */
public class ColorButton2 extends JButton {
    public Color thisColor=Color.WHITE;
    public ColorButton2(){
        Dimension size = new Dimension(70,40);
        this.setSize(size);
        this.setPreferredSize(size);
        this.setMinimumSize(size);
        this.setMaximumSize(size);
        this.addMouseListener(new changeColor());
    }
    
    public class changeColor implements MouseListener {
        @Override public void mouseClicked(MouseEvent e) {}

        @Override public void mousePressed(MouseEvent e) {
            MainWindow Frame = MainWindow.getThisFrame();
            if (Frame.cPanel.visible){
               if (e.getButton() == e.BUTTON3){  
                  Frame.typeOfOperation=0; 
                  Frame.ShapeAL.get(Frame.positionSelectedShape).BorderColor=thisColor;
                  Frame.drawPanel.drawBuffer(Frame.ShapeAL, Frame.ActiveShape);
                  Frame.repaint();
               }
               if (e.getButton() == e.BUTTON1){
                  Frame.typeOfOperation=0; 
                  Frame.ShapeAL.get(Frame.positionSelectedShape).FillColor=thisColor;
                  Frame.drawPanel.drawBuffer(Frame.ShapeAL, Frame.ActiveShape);
                  Frame.repaint();
               }
            }
        }
        @Override public void mouseReleased(MouseEvent e) {}

        @Override public void mouseEntered(MouseEvent e) {}

        @Override public void mouseExited(MouseEvent e) {}
    }
    
    @Override protected void paintComponent(Graphics g) 
    { 
      g.setColor(thisColor); 
      setForeground(thisColor);
      super.paintComponent(g);   //вызывает старый метод рисования, где идет вызов всех подпроцедур, вместе с отрисовкой paintBorder 
   } 
   @Override protected void paintBorder(Graphics g){ 
       if (thisColor == null){
             g.drawLine(5, 5, 60, 30);
             g.drawLine(5, 30, 60, 5);
         }else{
            g.fillRect(5, 5, 60, 30); 
         }
   } 
}
