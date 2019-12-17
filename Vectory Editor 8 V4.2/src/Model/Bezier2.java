/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.Dots2;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class Bezier2 extends PolyLine {
    protected double step=0.01;//0.001;
    protected boolean securityPoint2=false;
    protected ArrayList<Dots2> CourveArray=new ArrayList<Dots2>();
    public Bezier2 (){
      this.type="Bezier2";
      this.Name="Кривая Безье";
   }
    @Override public void paint(Graphics g,double ShiftHolstX, double ShiftHolstY, double ScaleHolst){
        g.setColor(BorderColor);
        if (middleDotsArray.size()>0){                                          //чтобы не вылезла ошибка null pointer exeption
           middleDotsArray.clear();
        }
        if (securityPoint2 != true){   //добавление элемента промежуточного если не конец отрисовки
           MyArray.add(new Dots2(X,Y));
        }
        if (MyArray.size()>1){ //рисование кривой безье
           int n=(int) Math.round(1/step);
           //ArrayList<Dots2> ExtractionPoint=new ArrayList<Dots2>();
           for (double t=0;t<=1;t+=step){
               for(int i=0;i<MyArray.size();i++){ //Заносим заного каждый раз в другой список, где и ищем
                   double XPos1=MyArray.get(i).getDotsX()*Math.exp(ScaleHolst)+ShiftHolstX;
                   double YPos1=MyArray.get(i).getDotsY()*Math.exp(ScaleHolst)+ShiftHolstY;
                   double PivotDX=PivotX*Math.exp(ScaleHolst)+ShiftHolstX;
                   double PivotDY=PivotY*Math.exp(ScaleHolst)+ShiftHolstY;              
                   CourveArray.add(new Dots2((XPos1-PivotDX)*Math.cos(Phi+Phi2)-(YPos1-PivotDY)*Math.sin(Phi+Phi2)+PivotDX+ShiftX,(XPos1-PivotDX)*Math.sin(Phi+Phi2)+(YPos1-PivotDY)*Math.cos(Phi+Phi2)+PivotDY+ShiftY));  //сюда поворот
               }
               if (typeDeformation>0){//при изменении объема
                   CourveArray.get(0).setDotsX(CourveArray.get(0).getDotsX()*MyScaleX);  //вычисление координаты при изменении грани
                   double MaxXScaleDots=CourveArray.get(0).getDotsX();
                   double MinXScaleDots=CourveArray.get(0).getDotsX();
                   CourveArray.get(0).setDotsY(CourveArray.get(0).getDotsY()*MyScaleY);
                   double MaxYScaleDots=CourveArray.get(0).getDotsY();
                   double MinYScaleDots=CourveArray.get(0).getDotsY();
                   for (int i=1;i<CourveArray.size();i++){
                      CourveArray.get(i).setDotsX(CourveArray.get(i).getDotsX()*MyScaleX);    
                      MaxXScaleDots=MMMM(CourveArray.get(i).getDotsX(),MaxXScaleDots,2);
                      MinXScaleDots=MMMM(CourveArray.get(i).getDotsX(),MinXScaleDots,1);
                      CourveArray.get(i).setDotsY(CourveArray.get(i).getDotsY()*MyScaleY); 
                      MaxYScaleDots=MMMM(CourveArray.get(i).getDotsY(),MaxYScaleDots,2);
                      MinYScaleDots=MMMM(CourveArray.get(i).getDotsY(),MinYScaleDots,1);
                  }
                  double floutX=0;
                  double floutY=0;
                  if (typeDeformation==2 | typeDeformation==1 | typeDeformation==3){ //| typeDeformation==2){      // ----------------тут буду прописывать paint в зависимости от типа деформации
                     floutY=MaxY*Math.exp(ScaleHolst)+ShiftHolstY-MaxYScaleDots;    
                  }
                  if (typeDeformation==8 | typeDeformation==7 | typeDeformation==9){ //| typeDeformation==2){      // ----------------тут буду прописывать paint в зависимости от типа деформации
                     floutY=MinY*Math.exp(ScaleHolst)+ShiftHolstY-MinYScaleDots;    
                  }
                  if (typeDeformation==4 | typeDeformation==1 | typeDeformation==7){ //| typeDeformation==2){      // ----------------тут буду прописывать paint в зависимости от типа деформации
                     floutX=MaxX*Math.exp(ScaleHolst)+ShiftHolstX-MaxXScaleDots; 
                  }
                  if (typeDeformation==6 | typeDeformation==3 | typeDeformation==9){ //| typeDeformation==2){      // ----------------тут буду прописывать paint в зависимости от типа деформации
                     floutX=MinX*Math.exp(ScaleHolst)+ShiftHolstX-MinXScaleDots;   
                  }
                  for (int i=0;i<CourveArray.size();i++){
                     CourveArray.get(i).setDotsX(CourveArray.get(i).getDotsX()+floutX);
                     CourveArray.get(i).setDotsY(CourveArray.get(i).getDotsY()+floutY);
                  }
               } 
               for(int k=CourveArray.size()-1;k>0;k--){ //от 999 до 1-й итерации
                  for(int i=0;i<k;i++){
                      //ищим x b y
                      double XPos=(CourveArray.get(i).getDotsX()*(1-t))+(CourveArray.get(i+1).getDotsX()*t);
                      double YPos=(CourveArray.get(i).getDotsY()*(1-t))+(CourveArray.get(i+1).getDotsY()*t);
                      CourveArray.remove(i);
                      CourveArray.add(i,new Dots2(XPos,YPos));
                  }   
               }
               middleDotsArray.add(new Dots2(CourveArray.get(0).getDotsX(),CourveArray.get(0).getDotsY()));
               CourveArray.clear();//отчистка списка для дальнейшего использования
           } 
           for (int i = middleDotsArray.size()-1; i>0;i-- ){   //Удаляю точки одинаковые при отрисовке для хорошего пунктира и нормальной оптимизации
               int X1=(int)Math.floor(middleDotsArray.get(i).getDotsX());
               int Y1=(int)Math.floor(middleDotsArray.get(i).getDotsY());
               int X2=(int)Math.floor(middleDotsArray.get(i-1).getDotsX());
               int Y2=(int)Math.floor(middleDotsArray.get(i-1).getDotsY());
               if (X1==X2 & Y1==Y2){
                  middleDotsArray.remove(i);
               } 
           } 
           if (BorderColor != null){
              g.setColor(BorderColor);
              BorderLayer border= new BorderLayer();
              border.fillBorder(middleDotsArray,this.type , this.TypeBorder, (int)Math.floor(WidthOfBorder*Math.exp(ScaleHolst)/Math.exp(ScaleWidth)), BorderColor, g,this);
           }
           if (securityPoint != true & securityPoint2 != true){  //удаление элемента промежуточного если не конец отрисовки
              MyArray.remove(MyArray.size()-1);
           }
           if (securityPoint2==true & securityPoint!=true){//возможность обновлять securityPoint2 если не конец отрисовки безье
               securityPoint2=false;
           }
        }else{
           if (MyArray.size()!=0){ //рисует точку первую
              g.drawLine((int)Math.floor(MyArray.get(MyArray.size()-1).getDotsX()),(int)Math.floor(MyArray.get(MyArray.size()-1).getDotsY()),(int)Math.floor(MyArray.get(MyArray.size()-1).getDotsX()),(int)Math.floor(MyArray.get(MyArray.size()-1).getDotsY()));
           }    
       }
    }

    @Override public void addPoint(){
       MyArray.add(new Dots2(X,Y));
       securityPoint2=true;  //делает так, чтобы не добавлялась точка при отрисовки безье во время отжимания кнопки мыши (для рисования без драггеда и с ним)
       if (MyArray.size()>1){
            double XPlus =0;
            double YPlus =0;
            for (int i=0;i<MyArray.size()-1;i++){
                XPlus+=MyArray.get(i).X;
                YPlus+=MyArray.get(i).Y;
            }
            this.PivotX=XPlus/MyArray.size();
            this.PivotY=YPlus/MyArray.size();
        }
       MaxX=MMMM(X,MaxX,2);
       MinX=MMMM(X,MinX,1);
       MaxY=MMMM(Y,MaxY,2);
       MinY=MMMM(Y,MinY,1);
    }
    
    @Override public void backstep(){                                           //применяю во время сиреализации для всех фигур !!!!!
        this.securityPoint=true;
        securityPoint2=true; //чтобы не добавлялись точки после добавления безье в список объектов и иго отрисовке
    }
    
    @Override public Shape clonable(){
       Bezier2 newLine = new Bezier2();
       for (int i=0; i<this.MyArray.size();i++){
           newLine.MyArray.add(new Dots2(this.MyArray.get(i).getDotsX(),this.MyArray.get(i).getDotsY()));
       }
       newLine.MaxX=this.MaxX;
       newLine.MaxY=this.MaxY;
       newLine.MinX=this.MinX;
       newLine.MinY=this.MinY;
       newLine.Phi=this.Phi;
       newLine.securityPoint=this.securityPoint;
       newLine.PivotX=this.PivotX;
       newLine.PivotY=this.PivotY;
       newLine.ShiftX=this.ShiftX;
       newLine.ShiftY=this.ShiftY;
       newLine.ScaleWidth=this.ScaleWidth;
       newLine.Visible=this.Visible;
       newLine.Name=this.Name;
       newLine.BorderColor=this.BorderColor;
       newLine.FillColor=this.FillColor;
       newLine.TypeBorder=this.TypeBorder;
       newLine.WidthOfBorder=this.WidthOfBorder;
       newLine.TypeFill=this.TypeFill;
       newLine.typeOfTexture=this.typeOfTexture;
       newLine.ScaleWidth=this.ScaleWidth;
       for (int i=0; i<this.middleDotsArray.size();i++){
           newLine.middleDotsArray.add(new Dots2(this.middleDotsArray.get(i).getDotsX(),this.middleDotsArray.get(i).getDotsY()));
       }
       newLine.X=this.X;
       newLine.Y=this.Y;
       newLine.securityPoint2=this.securityPoint2;
       return newLine;
    }

}
