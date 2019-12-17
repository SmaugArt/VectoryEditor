/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassOfObjects;

import View.MainWindow;
import View.PropertyPanel;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author demin
 */
public class FillTypeButton extends JButton {
   boolean SecurityCloseOperation=false;
   public int type_Button;
   Model.Shape mOF=null;                                                              //Marker of fill
   protected FillTypeButton rootButton=null;                                            //ссылка на форму материнскую    в init component заносить буду
   private FillTypeButton button0;
   private FillTypeButton button1;
   private FillTypeButton button2;
   private FillTypeButton button3;
   private FillTypeButton button4;
   private JFrame mainFrame;
   private JPanel mainPanel;
   public FillTypeButton(int type_Button){                                      //0-нет текстуры , 1 - прямая по горизонту, 2-прямая по вертикале, 3 - наклонная от верхнего левого карая, 4 - наклонная от нижнего левого края
       this.type_Button=type_Button;
   } 
   public void addActionListenerForMainButton(){
       this.addActionListener(new NewActionListenerForApplyFillType());
   }
   public void setButton (Controller.ShapeArrayList change_Shape, int id_Shape){                                   //ссылка на шейп
        if (id_Shape != -1){
           this.mOF=change_Shape.get(id_Shape);                                    //во время обновления combo box
        }else{
           this.mOF=null;   
        }
    } 
   @Override protected void paintComponent(Graphics g) 
      { 
         if (rootButton != null){   //если главная кнопка 
            if (type_Button ==rootButton.type_Button ){ 
               g.setColor(Color.YELLOW); 
               setForeground(Color.YELLOW);
            }else{
               g.setColor(Color.BLACK); 
               setForeground(Color.BLACK); 
            }
         }                                                                      //else не нужен так как для главной кнопки по стандарту черный цвет стоит
         super.paintComponent(g); 
   } 
   @Override protected void paintBorder(Graphics g){ 
       if (type_Button == 0){
          g.drawLine(5, 5, 32, 23);
          g.drawLine(5, 23, 32, 5);
       }
       if (type_Button == 1){
          g.drawLine(5, 5, 32, 5);
          g.drawLine(5, 8, 32, 8);
          g.drawLine(5, 11, 32, 11);
          g.drawLine(5, 14, 32, 14);
          g.drawLine(5, 17, 32, 17);
          g.drawLine(5, 20, 32, 20);
          g.drawLine(5, 23, 32, 23);
       }
       if (type_Button == 2){
          g.drawLine(5, 5, 5, 23);
          g.drawLine(8, 5, 8, 23);
          g.drawLine(11, 5, 11, 23);
          g.drawLine(14, 5, 14, 23);
          g.drawLine(17, 5, 17, 23);
          g.drawLine(20, 5, 20, 23);
          g.drawLine(23, 5, 23, 23);
          g.drawLine(26, 5, 26, 23);
          g.drawLine(29, 5, 29, 23);
          g.drawLine(32, 5, 32, 23);
       }
       if (type_Button == 3){
          g.drawLine(5, 23, 5, 23);
          g.drawLine(5, 20, 8, 23);
          g.drawLine(5, 17, 11, 23);
          g.drawLine(5, 14, 14, 23);
          g.drawLine(5, 11, 17, 23);
          g.drawLine(5, 8, 20, 23);
          g.drawLine(5, 5, 23, 23); //end    
          g.drawLine(8, 5, 26, 23);
          g.drawLine(11, 5, 29, 23);
          g.drawLine(14, 5, 32, 23);
          g.drawLine(17, 5, 32, 20);
          g.drawLine(20, 5, 32, 17);
          g.drawLine(23, 5, 32, 14);
          g.drawLine(26, 5, 32, 11);
          g.drawLine(29, 5, 32, 8);
          g.drawLine(32, 5, 32, 5);
       }
       if (type_Button == 4){
          g.drawLine(32, 23, 32, 23);
          g.drawLine(29, 23, 32, 20);
          g.drawLine(26, 23, 32, 17);
          g.drawLine(23, 23, 32, 14);
          g.drawLine(20, 23, 32, 11);
          g.drawLine(17, 23, 32, 8);
          g.drawLine(14, 23, 32, 5);
          g.drawLine(11, 23, 29, 5);
          g.drawLine(8, 23, 26, 5);
          g.drawLine(5, 23, 23, 5);//end
          g.drawLine(5, 20, 20, 5); //смещение без y2 и x1
          g.drawLine(5, 17, 17, 5);
          g.drawLine(5, 14, 14, 5);
          g.drawLine(5, 11, 11, 5);
          g.drawLine(5, 8, 8, 5);
          g.drawLine(5, 5, 5, 5);
       }
   } 
   
   protected class NewActionListenerForApplyFillType implements ActionListener {
        @Override public void actionPerformed(ActionEvent e) {
            initComponents();                                                   //добавь его специальной процедурой при создании property Panel inits
        }    
    }
   protected void initComponents(){
        mainFrame = new JFrame();
        Dimension Size = new Dimension(235,80);
        mainFrame.setSize(Size);
        mainFrame.setPreferredSize(Size);
        mainFrame.setMinimumSize(Size);
        mainFrame.setMaximumSize(Size);
        mainPanel=new JPanel();                                                 //new GroupLayout());    //Card layout  //главная панелька
        mainPanel.setSize(Size);
        mainPanel.setPreferredSize(Size);
        mainPanel.setMinimumSize(Size);
        mainPanel.setMaximumSize(Size);
        Dimension Size2 = new Dimension(37,28);                                 //размер кнопок
        button0 = new FillTypeButton(0);
        button0.rootButton=this;
        button0.setPreferredSize(Size2);
        button0.addActionListener(new NewButton0Action());
        button1 = new FillTypeButton(1);
        button1.rootButton=this;
        button1.setPreferredSize(Size2);
        button1.addActionListener(new NewButton1Action());
        button2 = new FillTypeButton(2);
        button2.rootButton=this;
        button2.setPreferredSize(Size2);
        button2.addActionListener(new NewButton2Action());
        button3 = new FillTypeButton(3);
        button3.rootButton=this;
        button3.setPreferredSize(Size2);
        button3.addActionListener(new NewButton3Action());
        button4 = new FillTypeButton(4);
        button4.rootButton=this;
        button4.setPreferredSize(Size2);
        button4.addActionListener(new NewButton4Action());
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(layout);
        layout.setAutoCreateGaps(true); 
        layout.setAutoCreateContainerGaps(true); 
        layout.setHorizontalGroup(
           layout.createSequentialGroup()
              .addGroup(layout.createSequentialGroup()     
                 .addComponent(button0, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                 .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                 .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                 .addComponent(button3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                 .addComponent(button4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addComponent(button0, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addComponent(button3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addComponent(button4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
        mainFrame.setResizable(false);
   }
   private void closeOperation(Model.Shape mOF, int typeOfButton){
           if (mOF != null){
               mOF.typeOfTexture=typeOfButton;
               button0.rootButton.type_Button=typeOfButton; //Frame.P
           }
           MainWindow Frame = MainWindow.getThisFrame();
           Frame.drawPanel.drawBuffer(Frame.ShapeAL, Frame.ActiveShape);
           Frame.drawPanel.repaint();
           mainFrame.dispose(); 
   }
   protected class NewButton0Action implements ActionListener {
        @Override public void actionPerformed(ActionEvent e) {
           closeOperation(mOF,0);
        }
    }
   protected class NewButton1Action implements ActionListener {
        @Override public void actionPerformed(ActionEvent e) {
           closeOperation(mOF,1);
        }    
    }
   protected class NewButton2Action implements ActionListener {
        @Override public void actionPerformed(ActionEvent e) {
           closeOperation(mOF,2);
        }    
    }
   protected class NewButton3Action implements ActionListener {
        @Override public void actionPerformed(ActionEvent e) {
           closeOperation(mOF,3);
        }    
    }
   protected class NewButton4Action implements ActionListener {
        @Override public void actionPerformed(ActionEvent e) {
           closeOperation(mOF,4);
        }    
    }
}
