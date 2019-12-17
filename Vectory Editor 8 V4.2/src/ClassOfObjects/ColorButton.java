/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassOfObjects;

import Model.Bezier2;
import View.MainWindow;
import View.PropertyPanel;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import static javax.swing.GroupLayout.Alignment.LEADING;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author demin
 */
public class ColorButton extends JButton implements ActionListener{
    private Model.Shape cCS;                                                    //ссылка на объект изменяемый
    private boolean type;
    private JFrame Frame;
    private JPanel Panel;
    private JPanel PanelCColor; 
    private JPanel PanelOfButtons;
    private JColorChooser CPanel;
    private JButton OK;
    private JButton Reset;
    public ColorButton(String text, boolean type){
       this.setText(text);
       this.type=type;
       this.addActionListener(this);
    }
    public void setButton (Controller.ShapeArrayList change_Shape, int id_Shape){                                   //цвет отображения кнопки
        if (id_Shape != -1){
           this.cCS=change_Shape.get(id_Shape);                                    //во время обновления combo box
        }else{
           this.cCS=null;   
        }
    }
    private void initComponents(){  //в момент нажатия на кнопку   //true=border
        Frame = new JFrame();
        Dimension Size = new Dimension(700,550);
        Frame.setSize(Size);
        Frame.setPreferredSize(Size);
        Frame.setMinimumSize(Size);
        Frame.setMaximumSize(Size);
        Panel=new JPanel(new CardLayout());    //Card layout  //главная панелька
        Panel.setSize(Size);
        Panel.setPreferredSize(Size);
        Panel.setMinimumSize(Size);
        Panel.setMaximumSize(Size);
        PanelCColor=new JPanel();                                               //создание контейнера для jFrame
        PanelCColor.setPreferredSize(new Dimension (650,400));
        if (type){
           if (cCS.BorderColor != null){ 
               CPanel = new JColorChooser(cCS.BorderColor);                            //пока именно для границы   потом выбери для заливки
           }else{
               CPanel = new JColorChooser(Color.white);   
           }
        }else{
            if (cCS.FillColor != null){ 
               CPanel = new JColorChooser(cCS.FillColor); 
            }else{
               CPanel = new JColorChooser(Color.white);  
            }
        }
        CPanel.setPreferredSize(new Dimension (650,400));
        CPanel.setSize(new Dimension (650,400));
        CPanel.setMinimumSize(new Dimension (650,400));
        CPanel.setMaximumSize(new Dimension (650,400));
        PanelCColor.add(CPanel);                                                //конец с. к. для JFrame
        PanelOfButtons=new JPanel();                                            //начало создания панели с двумя кнопками
        PanelOfButtons.setPreferredSize(new Dimension (700,50)); 
        PanelOfButtons.setSize(new Dimension (700,50)); 
        PanelOfButtons.setMinimumSize(new Dimension (700,50)); 
        PanelOfButtons.setMaximumSize(new Dimension (700,50)); 
        OK=new JButton("Применить выбранный цвет");
        OK.setPreferredSize(new Dimension (325,50));
        OK.addActionListener(new NewActionListenerForApplyColor());
        Reset = new JButton("Удалить цвет элемента");
        Reset.setPreferredSize(new Dimension (325,50));
        Reset.addActionListener(new NewActionListenerForDeleteColor());
        javax.swing.GroupLayout superLayout = new javax.swing.GroupLayout(PanelOfButtons);
        PanelOfButtons.setLayout(superLayout);
        superLayout.setAutoCreateGaps(true); 
        superLayout.setAutoCreateContainerGaps(true); 
        superLayout.setHorizontalGroup(
           superLayout.createSequentialGroup()
              .addGroup(superLayout.createSequentialGroup()     
                 .addComponent(Reset, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                 .addComponent(OK, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        superLayout.setVerticalGroup(
            superLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addComponent(OK)
               .addComponent(Reset)
        );                                                                      //Конец создания панели с двумя кнопками
       javax.swing.GroupLayout layout = new javax.swing.GroupLayout(Panel);     // 
       Panel.setLayout(layout);
       layout.setAutoCreateGaps(true); 
       layout.setAutoCreateContainerGaps(true); 
       layout.setHorizontalGroup(
            layout.createParallelGroup()
               .addComponent(PanelCColor) 
               .addComponent(PanelOfButtons) 
        );
       layout.setVerticalGroup(layout.createSequentialGroup()
          .addComponent(PanelCColor) 
          .addComponent(PanelOfButtons) 
          
       );
        Frame.add(Panel);
        Frame.setVisible(true);
        Frame.setResizable(false);
    }
    @Override public void actionPerformed(ActionEvent e) {
        initComponents();
    }
    public class NewActionListenerForDeleteColor implements ActionListener {    // удаление цвета
        @Override public void actionPerformed(ActionEvent e) {
            if (cCS !=null){
               if (type){
                  cCS.BorderColor=null; 
               }else{
                  cCS.FillColor=null;  
               }
            }
            MainWindow window = MainWindow.getThisFrame();
            window.typeOfOperation=0;
            window.drawPanel.drawBuffer(window.ShapeAL, window.ActiveShape);
            window.drawPanel.repaint();
            Frame.dispose();
        }    
    }
    public class NewActionListenerForApplyColor implements ActionListener {
        @Override public void actionPerformed(ActionEvent e) {
            if (cCS !=null){
               if (type){
                  cCS.BorderColor=CPanel.getColor();     //выставить цвет согласно rgb палитре 
               }else{
                  cCS.FillColor=CPanel.getColor();  
               }
            }
            MainWindow window = MainWindow.getThisFrame();
            window.typeOfOperation=0;
            window.drawPanel.drawBuffer(window.ShapeAL, window.ActiveShape);
            window.drawPanel.repaint();
            if (cCS !=null){                                                    //здесь начинается магия, т.к. при создании сложного полигона возникают деффекты и требуется сменить угол дабы их избежать
               cCS.Phi+=0.001;
            }
            window.drawPanel.drawBuffer(window.ShapeAL, window.ActiveShape);
            window.drawPanel.repaint();
            Frame.dispose();
        }    
    }
    
    @Override protected void paintComponent(Graphics g) 
   { 
      if(getModel().isArmed()) 
      { 
         g.setColor(Color.BLACK); 
         setForeground(Color.BLACK);
      } 
      else 
      {
         if (cCS != null){ 
            if (type){ 
               g.setColor(this.cCS.BorderColor); 
               setForeground(this.cCS.BorderColor); //если не нажата кнопка, то фигура в ней черная
            }else{
               g.setColor(this.cCS.FillColor); 
               setForeground(this.cCS.FillColor); //если не нажата кнопка, то фигура в ней черная 
            }
         }
      } 
      super.paintComponent(g); 
   } 
   @Override protected void paintBorder(Graphics g){ 
       if (cCS != null){
          if (type & cCS.BorderColor == null | type ==false & cCS.FillColor == null){
             g.drawLine(5, 5, 32, 23);
             g.drawLine(5, 23, 32, 5);
         }else{
            g.fillRect(5, 5, 27, 18); 
         }
      }else{
         g.drawLine(5, 5, 32, 23);
         g.drawLine(5, 23, 32, 5);     
      }
   } 
}
