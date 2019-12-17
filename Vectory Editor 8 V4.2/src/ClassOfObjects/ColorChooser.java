/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassOfObjects;

import View.ColorPanel;
import View.MainWindow;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author demin
 */
public class ColorChooser extends JButton implements ActionListener{
    private ColorPanel ColorPanel;
    private JFrame Frame;
    private JPanel Panel;
    private JPanel PanelCColor; 
    private JPanel PanelOfButtons;
    private JColorChooser CPanel;
    private JButton OK;
    private JButton Reset;
    public ColorChooser(ColorPanel Color){
        this.setText("Добавить цвет");
        this.ColorPanel=Color; //ссылка на панель для обновления цветов
        this.addActionListener(this);
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
        if (ColorPanel.getColor()==null){
            CPanel = new JColorChooser(Color.BLACK);
        }else{    
            CPanel = new JColorChooser(ColorPanel.getColor());
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
            ColorPanel.setColor(null);           
            MainWindow window = MainWindow.getThisFrame();
            window.cPanel.repaint();
            Frame.dispose();
        }    
    }
    public class NewActionListenerForApplyColor implements ActionListener {
        @Override public void actionPerformed(ActionEvent e) {
            ColorPanel.setColor(CPanel.getColor());
            MainWindow window = MainWindow.getThisFrame();
            window.cPanel.repaint();
            Frame.dispose();
        } 
    }
}
