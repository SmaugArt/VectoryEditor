/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import ClassOfObjects.ColorButton;
import ClassOfObjects.FillTypeButton;
import Controller.Dots2;
import Controller.ShapeArrayList;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import static javax.swing.GroupLayout.Alignment.LEADING;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

/**
 *
 * @author demin
 */
public class PropertyPanel extends JScrollPane implements Properties{
   private static volatile PropertyPanel thisPanel;
   private JPanel MainPanel; //главная панель элементов
   private JLabel PP;
   private JLabel TypeShape;
   private JComboBox list_Shape; //элемент выбора шейпов
   private JLabel LabelMaxX;    //метки с текстом
   private JLabel LabelMinX;
   private JLabel LabelMaxY;
   private JLabel LabelMinY;
   private JLabel NameShape;
   private JLabel thisID;
   private JLabel shapeVisible;
   private JLabel WidthOfBorder;
   private JLabel BorderColor;
   private JLabel TypeBorder;
   private JLabel Fill;
   private JLabel FillColor;
   private JLabel TypeFill;
   private JTextField MaxX;                         //место для ввода значений
   private JTextField MinX;
   private JTextField MaxY;  
   private JTextField MinY;
   private JTextField NShape;
   private JTextField tID;
   private JCheckBox sVisible;
   private JButton ApplyChange; //применить изменения
   private JButton DeleteShape; //удалить фигуру
   private JTextField WidthBorder;
   private ColorButton BColor; //border color  изменять заливку кнопки. Наследовать её от другого класса ColorButton
   private JComboBox TBorder; //type border
   private JComboBox TFill;   //type fill
   private ColorButton FColor; //fill color  изменять заливку кнопки. Наследовать её от другого класса ColorButton
   private FillTypeButton FillTextureType;
   private JButton visiblePP;
   public int PositionSelectShape = 0;
   private boolean Visible=true;
   
   
   private Controller.ShapeArrayList ShapeALPP = null; //ссылка на массив шейпов
   public PropertyPanel(){//конструктор (ABC-указатель на нашь arraylist)
       MainPanel=new JPanel();
       visiblePP = new JButton ("↔");
       visiblePP.addActionListener(new resizePP());
       PP= new JLabel("СВОЙСТВА ОБЪЕКТА");
       TypeShape = new JLabel("Имя объекта:");
       String[] itemComboBox = {"(Нет)"};
       list_Shape = new JComboBox(itemComboBox);
       ItemListener MyItemListener = new NewItemListener();
       list_Shape.addItemListener(MyItemListener);
       LabelMaxX=new JLabel("Правая вершина:");
       MaxX = new JTextField();
       Border border = BorderFactory.createLineBorder(Color.red);               //Цвет границы текстового поля
       MaxX.setBorder(border);
       MaxX.addKeyListener(new NewKyyListenerForMMMMPosition(this.MaxX));
       LabelMaxY=new JLabel("Нижняя вершина:");
       MaxY = new JTextField();
       MaxY.setBorder(border);
       MaxY.addKeyListener(new NewKyyListenerForMMMMPosition(this.MaxY));
       LabelMinX=new JLabel("Левая вершина:");
       MinY = new JTextField();
       MinY.setBorder(border);
       MinY.addKeyListener(new NewKyyListenerForMMMMPosition(this.MinY));
       LabelMinY=new JLabel("Верхняя вершина:");
       MinX = new JTextField();
       MinX.setBorder(border);
       MinX.addKeyListener(new NewKyyListenerForMMMMPosition(this.MinX));
       NameShape = new JLabel("Имя объекта:");
       NShape = new JTextField();
       NShape.setBorder(border);
       NShape.addKeyListener(new NewKyyListenerForName());
       thisID=new JLabel("Номер слоя объекта:");
       tID=new JTextField();
       tID.setBorder(border);
       tID.addKeyListener(new NewKeyListenerForID());
       shapeVisible=new JLabel("Видимость объекта:");
       sVisible=new JCheckBox("Виден");
       sVisible.setSelected(true);
       ApplyChange = new JButton("Применить изменения");
       ApplyChange.addActionListener(new NewActionListenerForApplyhange());
       DeleteShape = new JButton ("Удалить объект"); 
       DeleteShape.addActionListener(new NewActionListenerForDeleteShape());
       WidthOfBorder = new JLabel("Длина пунктирной линии:");
       WidthBorder = new JTextField();
       WidthBorder.setBorder(border);
       WidthBorder.addKeyListener(new NewKeyListenerForWidthOfBorder());
       BorderColor = new JLabel("Цвет границы:");
       BColor = new ColorButton("   ",true);       
       String[] items = {
          "Линия",
          "Пунктирная линия"
       };
       TypeBorder = new JLabel("Тип границы: ");
       TBorder = new JComboBox (items);
       TBorder.addItemListener(new NewItemListenerForTypeBorder());
       TypeFill = new JLabel("Тип заливки: ");
       String[] items2 = {
          "Заливка цветом",
          "Заливка текстурой"
                  }; 
       TFill=new JComboBox(items2);
       TFill.addItemListener(new NewItemListenerForTypeFill());
       FillColor = new JLabel("Цвет заливки: "); 
       FColor = new ColorButton ("   ", false);
       Fill = new JLabel("Текстура заливки: ");
       FillTextureType = new FillTypeButton (0);
       FillTextureType.setPreferredSize(new Dimension(37,28));
       FillTextureType.addActionListenerForMainButton();
       javax.swing.GroupLayout layout = new javax.swing.GroupLayout(MainPanel);  
       MainPanel.setLayout(layout);
       layout.setAutoCreateGaps(true); 
       layout.setAutoCreateContainerGaps(true); 
       layout.setHorizontalGroup(
            layout.createSequentialGroup()
            .addComponent(visiblePP,javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createParallelGroup(LEADING)
                .addComponent(PP)
                .addComponent(TypeShape)
                .addComponent(LabelMaxX)
                .addComponent(LabelMaxY)
                .addComponent(LabelMinX)
                .addComponent(LabelMinY)
                .addComponent(NameShape)
                .addComponent(thisID)
                .addComponent(shapeVisible)    
                .addComponent(ApplyChange)
                .addComponent(DeleteShape)
                .addComponent(WidthOfBorder)
                .addComponent(BorderColor)
                .addComponent(TypeBorder)
                .addComponent(TypeFill)
                .addComponent(FillColor)
                .addComponent(Fill)
            ) 
              .addGroup(layout.createParallelGroup(LEADING)     
                 .addComponent(list_Shape)
                 .addComponent(MaxX)
                 .addComponent(MaxY)
                 .addComponent(MinX)
                 .addComponent(MinY)
                 .addComponent(NShape)
                 .addComponent(tID)
                 .addComponent(sVisible)    
                 .addComponent(WidthBorder)
                 .addComponent(BColor)
                 .addComponent(TBorder)
                 .addComponent(TFill)
                 .addComponent(FColor)
                 .addComponent(FillTextureType,javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)       
            )
        );
       layout.linkSize(SwingConstants.HORIZONTAL, PP, list_Shape);
       layout.setVerticalGroup(layout.createSequentialGroup()
          .addGroup(layout.createParallelGroup(LEADING)
             .addComponent(visiblePP, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
             .addComponent(PP)
          )
          .addGroup(layout.createParallelGroup(LEADING)
             .addComponent(TypeShape)
             .addComponent(list_Shape)
          )
          .addGroup(layout.createParallelGroup(LEADING)
             .addComponent(LabelMaxX)
             .addComponent(MaxX)
          )
          .addGroup(layout.createParallelGroup(LEADING)
             .addComponent(LabelMaxY)
             .addComponent(MaxY)
          )
          .addGroup(layout.createParallelGroup(LEADING)
             .addComponent(LabelMinX)
             .addComponent(MinX)
          )
          .addGroup(layout.createParallelGroup(LEADING)
             .addComponent(LabelMinY)
             .addComponent(MinY)
          )
          .addGroup(layout.createParallelGroup(LEADING)
             .addComponent(NameShape)
             .addComponent(NShape)
          )
          .addGroup(layout.createParallelGroup(LEADING)
             .addComponent(thisID)
             .addComponent(tID)
          ) 
          .addGroup(layout.createParallelGroup(LEADING)
             .addComponent(shapeVisible)
             .addComponent(sVisible)
          )
          .addComponent(ApplyChange)
          .addComponent(DeleteShape)
          .addGroup(layout.createParallelGroup(LEADING)
             .addComponent(TypeBorder)
             .addComponent(TBorder)
          )
          .addGroup(layout.createParallelGroup(LEADING)
             .addComponent(BorderColor)
             .addComponent(BColor)
          )
          .addGroup(layout.createParallelGroup(LEADING)
             .addComponent(WidthOfBorder)
             .addComponent(WidthBorder)
          )
          .addGroup(layout.createParallelGroup(LEADING)
             .addComponent(TypeFill)
             .addComponent(TFill)
          )
          .addGroup(layout.createParallelGroup(LEADING)
             .addComponent(FillColor)
             .addComponent(FColor)
          )
          .addGroup(layout.createParallelGroup(LEADING)
             .addComponent(Fill)
             .addComponent(FillTextureType, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
          )
       );
       this.setBorder(border);
       this.add(MainPanel);
       this.setViewportView(MainPanel); //выбирает слои;
       FalseVisibleObjects();
   } //конструктор (ABC-указатель на нашь arraylist)
   
   public static PropertyPanel getThisPanel(){
       if (thisPanel==null){
           synchronized (PropertyPanel.class){
               if (thisPanel==null){
                   thisPanel=new PropertyPanel();  //один лишь раз создаем, чтобы возвращать значение нашей панели в любом месте
               }
           }
       }
       return thisPanel;
   }
    public void FalseVisibleObjects(){ //вся видимость объектов кроме ComboBox'a = false
       MaxX.setEnabled(false);
       MaxY.setEnabled(false);
       MinX.setEnabled(false);
       MinY.setEnabled(false);
       NShape.setEnabled(false);
       tID.setEnabled(false);
       sVisible.setEnabled(false);
       ApplyChange.setEnabled(false);
       DeleteShape.setEnabled(false);
       WidthBorder.setEnabled(false);
       BColor.setEnabled(false);
       BColor.setButton(ShapeALPP, -1);
       TBorder.setEnabled(false);
       TFill.setEnabled(false);
       FColor.setEnabled(false);
       FColor.setButton(ShapeALPP, -1);
       FillTextureType.setButton(ShapeALPP, -1);
       FillTextureType.type_Button=0;
       FillTextureType.setEnabled(false);
    }
    public void setShapeArrayList (ShapeArrayList ABC){                         //запоминает новое значение + обновляет checkbox
       ShapeALPP = ABC;
       UpgradeComboBox();
    }
    
    @Override public void UpgradeComboBox() {
       //создаем массив имен объектов + составляем список Combo Box'a затем нужно добавить ему слушателя и реализовать в нем выбор элемента и загрузку метода LoadProperties
       int copyPositionSelectShape=PositionSelectShape;
       list_Shape.removeAllItems();
       list_Shape.addItem("(Нет)");
       ArrayList<String> Names = new ArrayList<String>();
       ArrayList<Dots2> ChisloSimilarNames = new ArrayList<Dots2>();
       String[] item = new String[ShapeALPP.size()]; //заносим туда нужный текст
       for (int i=0; i< ShapeALPP.size();i++){  //до конца списка элементов
           int Pos = 1;
           boolean Sec= true; //заносить надо
           for (int i2=0; i2<Names.size();i2++){
               if (ShapeALPP.get(i).Name.equals(Names.get(i2))){
                   Sec=false;
                   ChisloSimilarNames.get(i2).setDotsX(ChisloSimilarNames.get(i2).getDotsX()+1);
                   Pos=(int)Math.floor(ChisloSimilarNames.get(i2).getDotsX());
               }
           }
           if (Sec){
              Names.add(ShapeALPP.get(i).Name);
              ChisloSimilarNames.add(new Dots2(1,0));
           }
           item[i]=ShapeALPP.get(i).Name+" "+Pos;
       }
       for (int i=0; i< ShapeALPP.size();i++){
          list_Shape.addItem(item[i]);
       }
       PositionSelectShape=copyPositionSelectShape;
       if (PositionSelectShape>ShapeALPP.size()){ //В случае удаления верхнего слоя следует выбрать предыдущий
          list_Shape.setSelectedIndex(ShapeALPP.size());
          PositionSelectShape=ShapeALPP.size();
       }else{
          list_Shape.setSelectedIndex(PositionSelectShape);
       }
       loadProperties(PositionSelectShape-1);
    }
    
    
    @Override public void loadProperties(int id_Shape) {//хагрузить настройки выбранного элемента !!! убери ShapeAL везде.
        MainWindow Frame = MainWindow.getThisFrame();
        if (id_Shape>=0){
           MaxX.setEnabled(true);
           MaxX.setText(String.valueOf(ShapeALPP.get(id_Shape).MaxX)); //переводит double в String
           MaxY.setEnabled(true);
           MaxY.setText(String.valueOf(ShapeALPP.get(id_Shape).MaxY)); //переводит double в String
           MinX.setEnabled(true);
           MinX.setText(String.valueOf(ShapeALPP.get(id_Shape).MinX)); //переводит double в String
           MinY.setEnabled(true);
           MinY.setText(String.valueOf(ShapeALPP.get(id_Shape).MinY)); //переводит double в String
           NShape.setEnabled(true);
           NShape.setText(ShapeALPP.get(id_Shape).Name);
           tID.setEnabled(true);
           tID.setText(String.valueOf(id_Shape+1));
           sVisible.setEnabled(true);
           sVisible.setSelected(ShapeALPP.get(id_Shape).Visible);
           ApplyChange.setEnabled(true);
           DeleteShape.setEnabled(true);
           BColor.setEnabled(true);
           BColor.setButton(ShapeALPP, id_Shape);
           TBorder.setEnabled(true);
           WidthBorder.setEnabled(true);
           WidthBorder.setText(String.valueOf(ShapeALPP.get(id_Shape).WidthOfBorder));
           if (ShapeALPP.get(id_Shape).TypeBorder){
              TBorder.setSelectedIndex(1);
           }else{
              TBorder.setSelectedIndex(0); 
           }
           if (ShapeALPP.get(id_Shape).type=="Triangle" | ShapeALPP.get(id_Shape).type=="Rectangle" | ShapeALPP.get(id_Shape).type=="PolyGone" | ShapeALPP.get(id_Shape).type=="Ellipse2"){ //настройки заливки
               TFill.setEnabled(true);
               if (ShapeALPP.get(id_Shape).TypeFill){
                  TFill.setSelectedIndex(1);
               }else{
                  TFill.setSelectedIndex(0);  
               }
               FColor.setEnabled(true);
               FColor.setButton(ShapeALPP, id_Shape);
               if (ShapeALPP.get(id_Shape).TypeFill){
                  FillTextureType.setEnabled(true);                             //почему не редактируется ?
                  FillTextureType.setButton(ShapeALPP, id_Shape);
                  FillTextureType.type_Button=ShapeALPP.get(id_Shape).typeOfTexture;
               }
           }
           Frame.cPanel.setEnabled(true);
        }else{
           Frame.cPanel.setEnabled(false);
        }
    } 
    
    public void setVisibleObjects (Boolean visible){
        list_Shape.setVisible(visible);
        MaxX.setVisible(visible);
        MaxY.setVisible(visible);
        MinX.setVisible(visible);
        MinY.setVisible(visible);
        NShape.setVisible(visible);
        tID.setVisible(visible);
        sVisible.setVisible(visible);
        ApplyChange.setVisible(visible);
        DeleteShape.setVisible(visible);
        WidthBorder.setVisible(visible);
        BColor.setVisible(visible);
        TBorder.setVisible(visible);
        TFill.setVisible(visible);
        FColor.setVisible(visible);
        FillTextureType.setVisible(visible);
        PP.setVisible(visible);
        TypeShape.setVisible(visible);
        LabelMaxX.setVisible(visible);
        LabelMaxY.setVisible(visible);
        LabelMinX.setVisible(visible);
        LabelMinY.setVisible(visible);
        NameShape.setVisible(visible);
        thisID.setVisible(visible);
        shapeVisible.setVisible(visible);
        WidthOfBorder.setVisible(visible);
        BorderColor.setVisible(visible);
        TypeBorder.setVisible(visible);
        TypeFill.setVisible(visible);
        FillColor.setVisible(visible);
        Fill.setVisible(visible);
    }
    @Override public void deleteShape(int id_Shape) {
       MainWindow Frame = MainWindow.getThisFrame();
       ShapeALPP.remove(id_Shape);
       Frame.PanelFromPP.UpgradeComboBox();
       Frame.typeOfOperation=0;
       if (Frame.PanelFromPP.PositionSelectShape != -1){
           Frame.positionSelectedShape=Frame.PanelFromPP.PositionSelectShape-1;
       }else{
           Frame.positionSelectedShape=0;
       }         
       Frame.drawPanel.drawBuffer(ShapeALPP, Frame.ActiveShape);
       Frame.drawPanel.repaint();
    }

    @Override public void changeMaxX(int id_Shape, double NewMaxX) {
        MainWindow Frame = MainWindow.getThisFrame();
        ShapeALPP.get(id_Shape).startDeformation(ShapeALPP.get(id_Shape).MaxX,ShapeALPP.get(id_Shape).MaxY);
        ShapeALPP.get(id_Shape).endXRightEdgeDeformation(NewMaxX, ShapeALPP.get(id_Shape).MaxY ,0 ,0 ,0);
        Frame.typeOfOperation=5;                                                //для перерасчета координат эллипса при отрисовке
        ShapeALPP.get(id_Shape).typeDeformation=6;
        Frame.drawPanel.drawBuffer(Frame.ShapeAL, Frame.ActiveShape);
        Frame.drawPanel.repaint();
        ShapeALPP.get(id_Shape).addXRightEdgeDeformation(0 ,0 ,0);
        Frame.typeOfOperation=0;
        ShapeALPP.get(id_Shape).typeDeformation=0;
        Frame.typeOfOperation=0;
        ShapeALPP.get(id_Shape).typeDeformation=0;
    }

    @Override public void changeMinX(int id_Shape, double NewMinX) {
        MainWindow Frame = MainWindow.getThisFrame();
        ShapeALPP.get(id_Shape).startDeformation(ShapeALPP.get(id_Shape).MinX,ShapeALPP.get(id_Shape).MinY);
        ShapeALPP.get(id_Shape).endXLeftEdgeDeformation(NewMinX, ShapeALPP.get(id_Shape).MinY ,0 ,0 ,0);
        Frame.typeOfOperation=5;                                                //для перерасчета координат эллипса при отрисовке
        ShapeALPP.get(id_Shape).typeDeformation=4;
        Frame.drawPanel.drawBuffer(Frame.ShapeAL, Frame.ActiveShape);
        Frame.drawPanel.repaint();
        ShapeALPP.get(id_Shape).addXLeftEdgeDeformation(0 ,0 ,0);
        Frame.typeOfOperation=0;
        ShapeALPP.get(id_Shape).typeDeformation=0;
    }

    @Override public void changeMinY(int id_Shape, double NewMinY) {
        MainWindow Frame = MainWindow.getThisFrame();
        ShapeALPP.get(id_Shape).startDeformation(ShapeALPP.get(id_Shape).MinX,ShapeALPP.get(id_Shape).MinY);
        ShapeALPP.get(id_Shape).endYUpEdgeDeformation(ShapeALPP.get(id_Shape).MinX, NewMinY ,0 ,0 ,0);
        Frame.typeOfOperation=5;                                                //для перерасчета координат эллипса при отрисовке
        ShapeALPP.get(id_Shape).typeDeformation=2;
        Frame.drawPanel.drawBuffer(Frame.ShapeAL, Frame.ActiveShape);
        Frame.drawPanel.repaint();
        ShapeALPP.get(id_Shape).addYUpEdgeDeformation(0 ,0 ,0);
        Frame.typeOfOperation=0;
        ShapeALPP.get(id_Shape).typeDeformation=0;
    }

    @Override public void changeMaxY(int id_Shape, double NewMaxY) {
        MainWindow Frame = MainWindow.getThisFrame();
        ShapeALPP.get(id_Shape).startDeformation(ShapeALPP.get(id_Shape).MaxX,ShapeALPP.get(id_Shape).MaxY);
        ShapeALPP.get(id_Shape).endYDownEdgeDeformation(ShapeALPP.get(id_Shape).MaxX, NewMaxY ,0 ,0 ,0);
        Frame.typeOfOperation=5;                                                //для перерасчета координат эллипса при отрисовке
        ShapeALPP.get(id_Shape).typeDeformation=8;
        Frame.drawPanel.drawBuffer(Frame.ShapeAL, Frame.ActiveShape);
        Frame.drawPanel.repaint();
        ShapeALPP.get(id_Shape).addYDownEdgeDeformation(0 ,0 ,0);
        Frame.typeOfOperation=0;
        ShapeALPP.get(id_Shape).typeDeformation=0;
    }

    @Override public void changeName(int id_Shape, String NewName) {
       boolean Sec = false;
       int Similar=0;
       for (int i=0; i<NewName.length();i++){
           char simvol = NewName.charAt(i);
           if (simvol==' '){
              Similar+=1;
           }
       }
       if (Similar==NewName.length()){
           Sec=true;
       }
       if (Sec != true){
          ShapeALPP.get(id_Shape).Name=NewName;
       }
    }   //Изменить имя 
    

    @Override public void changeID(int id_Shape ,int new_id) {
       Model.Shape intermediateShape = ShapeALPP.get(id_Shape); //промежуточный шейп
       Model.Shape S= ShapeALPP.get(id_Shape);  //(Controller.ShapeArrayList) ShapeALPP.clone();
       ShapeALPP.remove(id_Shape);
       //System.out.println(S);
       ShapeALPP.add(new_id-1,S);
       PositionSelectShape=new_id;
    }
    
    private String deleteSimvol (String Text, ArrayList<Integer> indexs){
        if (Text.length()>0){  //Алгоритм удаления символов в строке при помощи массива удаляемых символов, в который записывают индексы символов
              for (int i=indexs.size()-1; i>=0; i--){
                 Boolean sec=true;
                 if (indexs.get(i)-1<0 & Text.length() ==1){
                     Text="";
                     sec=false;
                 }
                 if (indexs.get(i)-1<0 & sec){ //удалить надо единственный символ //если первый символ
                     Text=Text.substring(indexs.get(i)+1);
                     sec=false;
                 }
                 if (indexs.get(i)+1>Text.length()-1 & Text.length()==1 & sec){
                     Text="";
                     sec=false;
                 }
                 if (indexs.get(i)+1>Text.length()-1 & sec){
                     Text=Text.substring(0,indexs.get(i));  //Почему с -1 отнимает вначале целых 2 буквы а затем по 1-й ?
                     sec=false;
                 }
                 if (indexs.get(i)+1<=Text.length()-1 & sec & indexs.get(i)-1>=0){
                     Text=Text.substring(0,indexs.get(i))+Text.substring(indexs.get(i)+1);
                 }
              }
           }
        return Text;
    }

    @Override
    public void applyTextFieldChange() {                                        //применяем все настройки до кнопки Применить настройки
        MainWindow Frame = MainWindow.getThisFrame();
        double newChislo = Double.valueOf(Frame.PanelFromPP.MaxX.getText());
        Frame.PanelFromPP.changeMaxX(PositionSelectShape-1, newChislo);
        newChislo = Double.valueOf(Frame.PanelFromPP.MaxY.getText());
        Frame.PanelFromPP.changeMaxY(PositionSelectShape-1, newChislo);
        newChislo = Double.valueOf(Frame.PanelFromPP.MinX.getText());
        Frame.PanelFromPP.changeMinX(PositionSelectShape-1, newChislo);
        newChislo = Double.valueOf(Frame.PanelFromPP.MinY.getText());
        Frame.PanelFromPP.changeMinY(PositionSelectShape-1, newChislo);
        Frame.PanelFromPP.changeName(PositionSelectShape-1, Frame.PanelFromPP.NShape.getText());
        int newChisloFromInteger=Integer.valueOf(Frame.PanelFromPP.tID.getText());
        Frame.PanelFromPP.changeID (PositionSelectShape-1,newChisloFromInteger);  //Почини замену и клонирование
        Frame.PanelFromPP.changeVisible(PositionSelectShape-1);
        Frame.PanelFromPP.FalseVisibleObjects();
        Frame.PanelFromPP.UpgradeComboBox();  
        Frame.typeOfOperation=0;
        Frame.drawPanel.drawBuffer(ShapeALPP, Frame.ActiveShape);
        Frame.drawPanel.repaint();
    }

    @Override public void changeVisible(int id_Shape) {
        ShapeALPP.get(id_Shape).Visible=this.sVisible.isSelected();
    }
    public class NewKyyListenerForMMMMPosition implements KeyListener {  ///
        private JTextField Component;
        private NewKyyListenerForMMMMPosition (JTextField Component){
           this.Component=Component;
        }
        @Override public void keyTyped(KeyEvent e) {}
        @Override public void keyPressed(KeyEvent e) {}
        @Override public void keyReleased(KeyEvent e) {
           boolean secMinus=false;
           boolean secDot=false;
           String Text = Component.getText();
           int lengthText=Text.length();
           ArrayList<Integer> delete_Simvol=new ArrayList<Integer>();
           for (int i=0;i<Text.length();i++){  //длина строки -1 ?
               char codeSimvol=Text.charAt(i);
               if (i==0){//(Text.length()==1){
                  if (codeSimvol<'0' & codeSimvol != '-'| codeSimvol>'9'){
                     delete_Simvol.add(Integer.valueOf(i));//Text = Text.substring(0, Text.length() - 1); //копирует текст от 0 до длины строки-1 
                  }
                  if (codeSimvol=='-'){
                     secMinus=true; 
                  }
               }   
               if (i==1){
                   if (secMinus){
                      if (codeSimvol<'0' | codeSimvol>'9'){
                         delete_Simvol.add(Integer.valueOf(i)); 
                      }
                   }else{
                      if (codeSimvol<'0' & codeSimvol != '.'| codeSimvol>'9'){
                         delete_Simvol.add(Integer.valueOf(i));
                      }
                      if (codeSimvol=='.'){
                         secDot=true; 
                      }
                   }
               }
               if (i>1){
                  if (secDot){
                     if (codeSimvol<'0' | codeSimvol>'9'){
                        delete_Simvol.add(Integer.valueOf(i));  
                     } 
                  }else{
                     if (codeSimvol<'0' & codeSimvol != '.'| codeSimvol>'9'){
                        delete_Simvol.add(Integer.valueOf(i)); 
                     }
                     if (codeSimvol=='.'){
                         secDot=true; 
                      }
                  }    
               }
           }
           Text=deleteSimvol(Text, delete_Simvol);
           MainWindow Frame = MainWindow.getThisFrame();
           if (lengthText != Text.length()){  //чтобы стрелками можно было перемещаться !
              if (this.Component==Frame.PanelFromPP.MaxX){
                 Frame.PanelFromPP.MaxX.setText(Text);
              }
              if (this.Component==Frame.PanelFromPP.MaxY){
                 Frame.PanelFromPP.MaxY.setText(Text);
              }
              if (this.Component==Frame.PanelFromPP.MinX){
                 Frame.PanelFromPP.MinX.setText(Text);
              }
              if (this.Component==Frame.PanelFromPP.MinY){
                 Frame.PanelFromPP.MinY.setText(Text);
              }
           }
           if (e.getKeyCode()==e.VK_ENTER){
               Frame.PanelFromPP.applyTextFieldChange();
           }
        }  
    }
    public class NewKyyListenerForName implements KeyListener {
        @Override public void keyTyped(KeyEvent e) {}
        @Override public void keyPressed(KeyEvent e) {}
        @Override public void keyReleased(KeyEvent e) {
           String Text = NShape.getText();
           int lengthText=Text.length();
           ArrayList<Integer> delete_Simvol=new ArrayList<Integer>();
           for (int i=0;i<Text.length();i++){  //длина строки -1 ?
              char codeSimvol=Text.charAt(i);
              boolean Sec=true;
              if (codeSimvol>='A' & codeSimvol <='Z'| codeSimvol>='a' & codeSimvol <='z'| codeSimvol>='А' & codeSimvol <='Я'| codeSimvol>='а' & codeSimvol <='я'){ //символы
                 Sec=false;
              }
              if (codeSimvol=='.' | codeSimvol=='-' | codeSimvol=='/' | codeSimvol==' ' | codeSimvol=='*' | codeSimvol=='+' | codeSimvol=='_' | codeSimvol>='0' & codeSimvol<='9'){ //символы
                 Sec=false;
              }
              if (Sec){
                 delete_Simvol.add(Integer.valueOf(i));
              }
           }
           Text=deleteSimvol(Text, delete_Simvol);
           MainWindow Frame = MainWindow.getThisFrame();
           if (lengthText != Text.length()){  //чтобы стрелками можно было перемещаться !
              Frame.PanelFromPP.NShape.setText(Text);
           }
           if (e.getKeyCode()==e.VK_ENTER){
               Frame.PanelFromPP.applyTextFieldChange();
           }
        }       
    }
    public class NewKeyListenerForID implements KeyListener {
        @Override public void keyTyped(KeyEvent e) {}
        @Override public void keyPressed(KeyEvent e) {}
        @Override public void keyReleased(KeyEvent e) {
           String Text = tID.getText();
           int lengthText=Text.length();
           ArrayList<Integer> delete_Simvol=new ArrayList<Integer>();
           for (int i=0;i<Text.length();i++){  //длина строки -1 ?
              char codeSimvol=Text.charAt(i);
              if (i==0){
                  if (codeSimvol<'1' | codeSimvol>'9'){
                      delete_Simvol.add(i);
                  }
              }
              if (i>0){
                  if (codeSimvol<'0' | codeSimvol>'9'){
                      delete_Simvol.add(i);
                  }
              }
           }
           Text=deleteSimvol(Text, delete_Simvol);
           int Chislo; 
           MainWindow Frame = MainWindow.getThisFrame();
           if (Text.length() !=0){
              Chislo = Integer.valueOf(Text);
           }else{
              Chislo = 1;  
           }
           if (lengthText != Text.length()){  //чтобы стрелками можно было перемещаться ! 
              if (Chislo>Frame.PanelFromPP.ShapeALPP.size()){
                  Frame.PanelFromPP.tID.setText(String.valueOf(Frame.PanelFromPP.ShapeALPP.size()));
              }else{
                  Frame.PanelFromPP.tID.setText(Text);
              }
           }else{
               if (Chislo>Frame.PanelFromPP.ShapeALPP.size()){  //если не удален ни один символ
                  Frame.PanelFromPP.tID.setText(String.valueOf(Frame.PanelFromPP.ShapeALPP.size()));
               }
           }
           if (e.getKeyCode()==e.VK_ENTER){
               Frame.PanelFromPP.applyTextFieldChange();
           }
        }       
    }
    public class NewItemListener implements ItemListener {
        @Override public void itemStateChanged(ItemEvent e) {
           PositionSelectShape=list_Shape.getSelectedIndex();
           MainWindow Frame = MainWindow.getThisFrame();
           Frame.positionSelectedShape=PositionSelectShape-1;
           Frame.PanelFromPP.FalseVisibleObjects();
           Frame.PanelFromPP.loadProperties(PositionSelectShape-1);//PositionSelectShape-1);
        }
    }
    public class NewActionListenerForDeleteShape implements ActionListener {
        @Override public void actionPerformed(ActionEvent e) {
            MainWindow Frame = MainWindow.getThisFrame();
            int delete_Index=Frame.PanelFromPP.list_Shape.getSelectedIndex()-1;
            Frame.PanelFromPP.deleteShape(delete_Index);            
        }    
    }
    public class NewActionListenerForApplyhange implements ActionListener {
        @Override public void actionPerformed(ActionEvent e) {
            MainWindow Frame = MainWindow.getThisFrame();
            Frame.PanelFromPP.applyTextFieldChange();      
        }    
    }
    public class NewItemListenerForTypeBorder implements ItemListener {
        @Override public void itemStateChanged(ItemEvent e) {
           int PositionSelectType=TBorder.getSelectedIndex();    //получить номер выборки
           if (PositionSelectType==0){
               ShapeALPP.get(PositionSelectShape-1).TypeBorder=false;//убираем видимость
           }else{
               ShapeALPP.get(PositionSelectShape-1).TypeBorder=true;
           }
           MainWindow Frame = MainWindow.getThisFrame();
           Frame.typeOfOperation=0;
           Frame.drawPanel.drawBuffer(Frame.ShapeAL, Frame.ActiveShape);
           Frame.drawPanel.repaint();
        }
    }
    public class NewKeyListenerForWidthOfBorder implements KeyListener {
        @Override public void keyTyped(KeyEvent e) {}
        @Override public void keyPressed(KeyEvent e) {}
        @Override public void keyReleased(KeyEvent e) {
           String Text = WidthBorder.getText();
           int lengthText=Text.length();
           ArrayList<Integer> delete_Simvol=new ArrayList<Integer>();
           for (int i=0;i<Text.length();i++){  //длина строки -1 ?
              char codeSimvol=Text.charAt(i);
              if (codeSimvol<'0' | codeSimvol>'9'){
                  delete_Simvol.add(i);
              }  
           }
           Text=deleteSimvol(Text, delete_Simvol);
           int Chislo=1; 
           MainWindow Frame = MainWindow.getThisFrame();
           if (Text.length() !=0){
              Chislo = Integer.valueOf(Text);
              if (Chislo>100){
                  Chislo=100;
              }
              if (Chislo<1){
                  Chislo=1;
              }
           }
           if (lengthText != Text.length()){  //чтобы стрелками можно было перемещаться !  
                  Frame.PanelFromPP.WidthBorder.setText(String.valueOf(Chislo));
           }else{
               if (Text.length() !=0){
                  int Chislo2=Integer.valueOf(Frame.PanelFromPP.WidthBorder.getText());
                  if (Chislo2 != Chislo){
                     Frame.PanelFromPP.WidthBorder.setText(String.valueOf(Chislo));
                  }
               }
           }
           if (e.getKeyCode()==e.VK_ENTER){
               ShapeALPP.get(PositionSelectShape-1).WidthOfBorder=Chislo;
               Frame.typeOfOperation=0;
               Frame.drawPanel.drawBuffer(Frame.ShapeAL, Frame.ActiveShape);
               Frame.drawPanel.repaint();
           }
        }       
    }
    
    public class NewItemListenerForTypeFill implements ItemListener {
        @Override public void itemStateChanged(ItemEvent e) {
           int PositionSelectType=TFill.getSelectedIndex();    //получить номер выборки
           MainWindow Frame = MainWindow.getThisFrame();
           if (PositionSelectType==0){
               ShapeALPP.get(PositionSelectShape-1).TypeFill=false;//убираем видимость
               Frame.PanelFromPP.FillTextureType.setEnabled(false);
               Frame.PanelFromPP.FillTextureType.setButton(ShapeALPP, -1);
               Frame.PanelFromPP.FillTextureType.type_Button=0;
           }else{
               ShapeALPP.get(PositionSelectShape-1).TypeFill=true;
               Frame.PanelFromPP.FillTextureType.setEnabled(true);
               Frame.PanelFromPP.FillTextureType.setButton(ShapeALPP, Frame.PanelFromPP.PositionSelectShape-1);
               Frame.PanelFromPP.FillTextureType.type_Button=ShapeALPP.get(Frame.PanelFromPP.PositionSelectShape-1).typeOfTexture;
           }
           Frame.typeOfOperation=0;
           Frame.drawPanel.drawBuffer(Frame.ShapeAL, Frame.ActiveShape);
           Frame.drawPanel.repaint();
        }
    }
    public class resizePP implements ActionListener {
        @Override public void actionPerformed(ActionEvent e) {
            MainWindow Frame = MainWindow.getThisFrame();
            //System.out.println(Frame.drawPanel.getBounds().x+" "+Frame.drawPanel.getBounds().y+" "+Frame.drawPanel.getBounds().width+" "+Frame.drawPanel.getBounds().height);
            Frame.typeOfOperation=0;
            if (Frame.PanelFromPP.Visible){
               Frame.PanelFromPP.Visible=false;
               setVisibleObjects(false);
               Frame.PanelFromPP.setBounds(1357,52,57,503);    //(1044)+(370-43)
               Frame.drawPanel.setBounds(188, 52, 1159,503);//1159 , 503);
               Frame.drawPanel.img = new BufferedImage(1159,550, BufferedImage.TYPE_INT_ARGB);
               Frame.drawPanel.height=550;
               Frame.drawPanel.width=1159;
               Frame.drawPanel.grap = Frame.drawPanel.img.createGraphics();
            }else{
               Frame.PanelFromPP.Visible=true;
               setVisibleObjects(true);
               Frame.PanelFromPP.setBounds(1044,52,370,503);                     //!!!размеры и положение панели свойств
               Frame.PanelFromPP.visiblePP.setSize(new Dimension(43,22));
               Frame.drawPanel.setBounds(188, 52, 846, 503); 
               Frame.drawPanel.img = new BufferedImage(856,550, BufferedImage.TYPE_INT_ARGB);
               Frame.drawPanel.height=550;
               Frame.drawPanel.width=1159;
               Frame.drawPanel.grap = Frame.drawPanel.img.createGraphics();
            } 
            Frame.drawPanel.drawBuffer(Frame.ShapeAL, Frame.ActiveShape);
            Frame.drawPanel.repaint();
            //System.out.println(Frame.drawPanel.getBounds().x+" "+Frame.drawPanel.getBounds().y+" "+Frame.drawPanel.getBounds().width+" "+Frame.drawPanel.getBounds().height);
        }
    }
}
