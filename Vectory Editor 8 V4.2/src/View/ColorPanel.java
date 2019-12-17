/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import ClassOfObjects.ColorButton2;
import ClassOfObjects.ColorChooser;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import static javax.swing.GroupLayout.Alignment.LEADING;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

/**
 *
 * @author demin
 */
public class ColorPanel extends JPanel {
    protected ArrayList<ColorButton2> colorArray = new ArrayList<ColorButton2>();
    protected ColorChooser chooser= new ColorChooser(this);    //выбор цвета
    public boolean visible=false;
//    public BufferedImage img;
//    public Graphics2D grap;
    public ColorPanel(){
        for (int i=1; i<=17; i++){       //создаю 10 кнопок цвета + 1 запасной для смещения
            colorArray.add(new ColorButton2());
        }
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);  
        this.setLayout(layout);
        layout.setAutoCreateGaps(true); 
        layout.setAutoCreateContainerGaps(true); 
        layout.setHorizontalGroup(
            layout.createSequentialGroup()
              .addComponent(colorArray.get(0))
              .addComponent(colorArray.get(1))
              .addComponent(colorArray.get(2))
              .addComponent(colorArray.get(3))
              .addComponent(colorArray.get(4))
              .addComponent(colorArray.get(5))
              .addComponent(colorArray.get(6))
              .addComponent(colorArray.get(7))
              .addComponent(colorArray.get(8))
              .addComponent(colorArray.get(9))
              .addComponent(colorArray.get(10))
              .addComponent(colorArray.get(11))
              .addComponent(colorArray.get(12))
              .addComponent(colorArray.get(13))
              .addComponent(colorArray.get(14))
              .addComponent(colorArray.get(15))
              .addComponent(this.chooser)
       );
       layout.setVerticalGroup(layout.createParallelGroup()
          .addComponent(colorArray.get(0))
          .addComponent(colorArray.get(1))
          .addComponent(colorArray.get(2))
          .addComponent(colorArray.get(3))
          .addComponent(colorArray.get(4))
          .addComponent(colorArray.get(5))
          .addComponent(colorArray.get(6))
          .addComponent(colorArray.get(7))
          .addComponent(colorArray.get(8))
          .addComponent(colorArray.get(9))
          .addComponent(colorArray.get(10))
          .addComponent(colorArray.get(11))
          .addComponent(colorArray.get(12))
          .addComponent(colorArray.get(13))
          .addComponent(colorArray.get(14))
          .addComponent(colorArray.get(15))
          .addComponent(this.chooser)
       );
       Border border = BorderFactory.createLineBorder(Color.yellow);
       this.setBorder(border);
       setEnabled(false);
    }
    
    @Override public void setEnabled (boolean type){
        for (int i=0; i<=16; i++){
           this.colorArray.get(i).setEnabled(type);
        }
        this.chooser.setEnabled(type);
        visible=type;
    }
    
    public Color getColor(){
        return colorArray.get(0).thisColor;
    }
    
    public void setColor(Color newColor){
        for (int i=15; i>=0;i--){
            colorArray.get(i+1).thisColor=colorArray.get(i).thisColor;
        }
        colorArray.get(0).thisColor=newColor;
    }
    //отрисовка квадратиков по заданным цветам repaint + отрисовка
    
//    @Override
//    public void paint(Graphics g){
//       g.drawImage(img, 0, 0, this);
//       super.paint(g);
//    }
//    
//    public void paintColorShape (Color fill, Color Border, Graphics g){
//        g.setColor(Color.WHITE);
//        g.fillRect(0, 0, 1400, 100);
//        g.setColor(Color.cyan);
//        g.fillRect(1000, 0, 500, 100);
//        this.repaint();
//    }
    
}
