 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.Dots2;
import View.MainWindow;
import java.awt.Color;
import java.awt.Graphics;
import static java.lang.Math.cos;
import static java.lang.Math.exp;
import static java.lang.Math.floor;
import static java.lang.Math.sin;
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class Ellipse2 extends Shape{
   public Ellipse2(){
       this.type="Ellipse2";
       this.Name="Эллипс";
   }
   protected double a;
   protected double b;
//   private double floutX=0;
//   private double floutY=0;
   private double floutToXForDeformation=0;                                     //храню в экранных координатах для смещения главной точки при добавлении деформации
   private double floutToYForDeformation=0;
   
   @Override public void firstclick(double X1,double Y1, double ScaleHolst){
      MyArray.add(new Dots2(X1,Y1));
      this.PivotX=X1;
      this.PivotY=Y1;
      ScaleWidth=ScaleHolst;
   }
   @Override public void lastclick(double X2,double Y2){
      MainWindow Frame = MainWindow.getThisFrame();
      this.a=Math.abs(MyArray.get(0).getDotsX()-X2);
      this.b=Math.abs(MyArray.get(0).getDotsY()-Y2);
      
      double aD=a*Math.exp(Frame.drawPanel.ScaleHolst);                         //смотрит условия, чтобы a и b >=1
      double bD=b*Math.exp(Frame.drawPanel.ScaleHolst);
      if ((int)Math.floor(aD)==0){
          a=1/Math.exp(Frame.drawPanel.ScaleHolst);
      }
      if ((int)Math.floor(bD)==0){
          b=1/Math.exp(Frame.drawPanel.ScaleHolst);
      }                                                                         //занесли если неправильно
      this.MinX=MyArray.get(0).getDotsX()-a;
      this.MaxX=MyArray.get(0).getDotsX()+a;
      this.MinY=MyArray.get(0).getDotsY()-b;
      this.MaxY=MyArray.get(0).getDotsY()+b;
   }
   @Override public void paint(Graphics g,double ShiftHolstX, double ShiftHolstY, double ScaleHolst){
      double thisScale = 1; 
      if (middleDotsArray.size()>0){                                          //чтобы не вылезла ошибка null pointer exeption
           middleDotsArray.clear();
        }
      MainWindow Frame = MainWindow.getThisFrame();
      ArrayList<Dots2> MyArray2= new ArrayList<Dots2>();
      ArrayList<Dots2> MyArray3= new ArrayList<Dots2>();
      MyArray2.add(new Dots2(MyArray.get(0).getDotsX()*Math.exp(ScaleHolst)+ShiftHolstX,MyArray.get(0).getDotsY()*Math.exp(ScaleHolst)+ShiftHolstY));
      double PivotDX=MyArray2.get(0).getDotsX();
      double PivotDY=MyArray2.get(0).getDotsY();
      double aD=a*Math.exp(ScaleHolst);
      double bD=b*Math.exp(ScaleHolst);
      if (Frame.typeOfOperation==5){                                            //задаем нужные размеры радиуса
          int degree=(int)Math.floor(Math.toDegrees(Phi)/90);
          if (degree==-1 | degree==-3 | degree==1 | degree==3){                 //чтобы была пропорциональная зависимость при повороте на 90, -90 , 270 , -270 градусов
              double middleScale=MyScaleX;
              MyScaleX=MyScaleY;
              MyScaleY=middleScale;
          }
          aD=aD*MyScaleX;
          bD=bD*MyScaleY;
      }
      if (aD>1000 | bD >1000){                                               //если требуется ограничить рисование точек
          if (aD>1000){
              thisScale=1000/aD;
          }else{
             thisScale=1000/bD;
          }
          aD=aD*thisScale;
          bD=bD*thisScale;
      }
      if (aD<1){
          aD=1;
      }
      if (bD<1){
          bD=1;
      }
      Dots2 Point=new Dots2(0,bD);
      g.setColor(BorderColor);
      double X2;
      double Y2;
      while (Point.getDotsY()>=0 ){// ( пока не достигли точки (X+а,Y))
         //добавляем точки
         double DotX=Point.getDotsX()/thisScale;
         double DotY=Point.getDotsY()/thisScale;
         double X1=(DotX+MyArray2.get(0).getDotsX()-PivotDX)*Math.cos(Phi+Phi2)-(DotY+MyArray2.get(0).getDotsY()-PivotDY)*Math.sin(Phi+Phi2)+PivotDX;
         double Y1=(DotX+MyArray2.get(0).getDotsX()-PivotDX)*Math.sin(Phi+Phi2)+(DotY+MyArray2.get(0).getDotsY()-PivotDY)*Math.cos(Phi+Phi2)+PivotDY;   
         MyArray3.add(new Dots2(X1,Y1));
         X1=((-DotX)+MyArray2.get(0).getDotsX()-PivotDX)*Math.cos(Phi+Phi2)-(DotY+MyArray2.get(0).getDotsY()-PivotDY)*Math.sin(Phi+Phi2)+PivotDX;
         Y1=((-DotX)+MyArray2.get(0).getDotsX()-PivotDX)*Math.sin(Phi+Phi2)+(DotY+MyArray2.get(0).getDotsY()-PivotDY)*Math.cos(Phi+Phi2)+PivotDY;
         MyArray3.add(new Dots2(X1,Y1)); 
         X1=(DotX+MyArray2.get(0).getDotsX()-PivotDX)*Math.cos(Phi+Phi2)-((-DotY)+MyArray2.get(0).getDotsY()-PivotDY)*Math.sin(Phi+Phi2)+PivotDX;
         Y1=(DotX+MyArray2.get(0).getDotsX()-PivotDX)*Math.sin(Phi+Phi2)+((-DotY)+MyArray2.get(0).getDotsY()-PivotDY)*Math.cos(Phi+Phi2)+PivotDY;
         MyArray3.add(new Dots2(X1,Y1)); 
         X1=((-DotX)+MyArray2.get(0).getDotsX()-PivotDX)*Math.cos(Phi+Phi2)-((-DotY)+MyArray2.get(0).getDotsY()-PivotDY)*Math.sin(Phi+Phi2)+PivotDX;
         Y1=((-DotX)+MyArray2.get(0).getDotsX()-PivotDX)*Math.sin(Phi+Phi2)+((-DotY)+MyArray2.get(0).getDotsY()-PivotDY)*Math.cos(Phi+Phi2)+PivotDY;
         MyArray3.add(new Dots2(X1,Y1));
         int Ta=(int)Math.floor(Math.pow(aD,2)*(2*Point.getDotsY()-1)); //Левая часть условного оператора
         int Tb=(int)Math.floor(2*Math.pow(bD, 2)*(Point.getDotsX()+1));
         if (Ta>Tb){ //?
            int Delta1=(int)Math.floor((4*Math.pow(bD, 2)*Math.pow((Point.getDotsX()+1), 2))+( Math.pow(aD, 2)*Math.pow((2*Point.getDotsY()-1),2) ) -( 4*Math.pow(aD,2)*Math.pow(bD, 2) ));
            if (Delta1>0){
                Point.setDotsY(Point.getDotsY()-1);
            }
            Point.setDotsX(Point.getDotsX()+1);
         }else{
            int Delta2=(int)Math.floor( (Math.pow(bD, 2)*Math.pow((2*Point.getDotsX()+1), 2)) +(4*Math.pow(aD, 2)*Math.pow((Point.getDotsY()-1), 2)) -(4*Math.pow(aD, 2)*Math.pow(bD, 2)) );
            if (Delta2<=0){
               Point.setDotsX(Point.getDotsX()+1);
            }
            Point.setDotsY(Point.getDotsY()-1);
         }    
      } //закрываем while  //вычислили новые координаты вместе с поворотом
      for (int i=0; i<MyArray3.size();i++){                              //смещение при сдвиге за курсором
          MyArray3.get(i).setDotsX(MyArray3.get(i).getDotsX()+ShiftX);
          MyArray3.get(i).setDotsY(MyArray3.get(i).getDotsY()+ShiftY);
      }
      if (Frame.typeOfOperation==5){
          double MiddleFloutToMaxX=MyArray3.get(0).getDotsX(); //другое число
          double MiddleFloutToMinX=MyArray3.get(0).getDotsX();
          double MiddleFloutToMaxY=MyArray3.get(0).getDotsY();
          double MiddleFloutToMinY=MyArray3.get(0).getDotsY();
          for (int i=1;i<MyArray3.size()-1;i++){
             MiddleFloutToMaxX=MMMM(MyArray3.get(i).getDotsX(),MiddleFloutToMaxX,2);
             MiddleFloutToMinX=MMMM(MyArray3.get(i).getDotsX(),MiddleFloutToMinX,1);
             MiddleFloutToMaxY=MMMM(MyArray3.get(i).getDotsY(),MiddleFloutToMaxY,2);
             MiddleFloutToMinY=MMMM(MyArray3.get(i).getDotsY(),MiddleFloutToMinY,1);
         }
          if (typeDeformation == 2| typeDeformation == 3| typeDeformation == 6){
             floutToXForDeformation=MinX*Math.exp(ScaleHolst)+ShiftHolstX-MiddleFloutToMinX;                       //смещение, которое храним
             floutToYForDeformation=MaxY*Math.exp(ScaleHolst)+ShiftHolstY-MiddleFloutToMaxY;
          }
          if (typeDeformation == 4| typeDeformation == 7| typeDeformation == 8){
             floutToXForDeformation=MaxX*Math.exp(ScaleHolst)+ShiftHolstX-MiddleFloutToMaxX;                       //смещение, которое храним
             floutToYForDeformation=MinY*Math.exp(ScaleHolst)+ShiftHolstY-MiddleFloutToMinY;
          }
          if (typeDeformation == 1){
             floutToXForDeformation=MaxX*Math.exp(ScaleHolst)+ShiftHolstX-MiddleFloutToMaxX;                       //смещение, которое храним
             floutToYForDeformation=MaxY*Math.exp(ScaleHolst)+ShiftHolstY-MiddleFloutToMaxY;
          }
          if (typeDeformation == 9){
             floutToXForDeformation=MinX*Math.exp(ScaleHolst)+ShiftHolstX-MiddleFloutToMinX;                       //смещение, которое храним
             floutToYForDeformation=MinY*Math.exp(ScaleHolst)+ShiftHolstY-MiddleFloutToMinY;
          }
          for (int i=0; i<MyArray3.size();i++){
              MyArray3.get(i).setDotsX(MyArray3.get(i).getDotsX()+floutToXForDeformation);
              MyArray3.get(i).setDotsY(MyArray3.get(i).getDotsY()+floutToYForDeformation);
          }
          MiddleFloutToMaxX=MyArray3.get(0).getDotsX();                  //перерасчет для того, чтобы сместить границы в нужную сторону, после смещения главных координат
          MiddleFloutToMinX=MyArray3.get(0).getDotsX();
          MiddleFloutToMaxY=MyArray3.get(0).getDotsY();
          MiddleFloutToMinY=MyArray3.get(0).getDotsY();
          for (int i=1;i<MyArray3.size()-1;i++){
             MiddleFloutToMaxX=MMMM(MyArray3.get(i).getDotsX(),MiddleFloutToMaxX,2);
             MiddleFloutToMinX=MMMM(MyArray3.get(i).getDotsX(),MiddleFloutToMinX,1);
             MiddleFloutToMaxY=MMMM(MyArray3.get(i).getDotsY(),MiddleFloutToMaxY,2);
             MiddleFloutToMinY=MMMM(MyArray3.get(i).getDotsY(),MiddleFloutToMinY,1);
         }
          if (typeDeformation == 2| typeDeformation == 3| typeDeformation == 6){
             MinYPos=MiddleFloutToMinY-(MinY*Math.exp(ScaleHolst)+ShiftHolstY);    //смещение границ
             MaxXPos=MiddleFloutToMaxX-(MaxX*Math.exp(ScaleHolst)+ShiftHolstX);
          }
          if (typeDeformation == 4| typeDeformation == 7| typeDeformation == 8){
             MaxYPos=MiddleFloutToMaxY-(MaxY*Math.exp(ScaleHolst)+ShiftHolstY);    //смещение границ
             MinXPos=MiddleFloutToMinX-(MinX*Math.exp(ScaleHolst)+ShiftHolstX);
          }
          if (typeDeformation == 9){
             MaxYPos=MiddleFloutToMaxY-(MaxY*Math.exp(ScaleHolst)+ShiftHolstY);    //смещение границ
             MaxXPos=MiddleFloutToMaxX-(MaxX*Math.exp(ScaleHolst)+ShiftHolstX);
          }
          if (typeDeformation == 1){
             MinYPos=MiddleFloutToMinY-(MinY*Math.exp(ScaleHolst)+ShiftHolstY);    //смещение границ
             MinXPos=MiddleFloutToMinX-(MinX*Math.exp(ScaleHolst)+ShiftHolstX);
          }
      }        
      if (Frame.typeOfOperation==2){                                            //только при повороте обновляем max и min
         MaxX=MyArray3.get(0).getDotsX(); //при повороте и не во время деформаций но надо отсортировать по сетке
         MinX=MyArray3.get(0).getDotsX();
         MaxY=MyArray3.get(0).getDotsY();
         MinY=MyArray3.get(0).getDotsY();
         for (int i=1;i<MyArray3.size()-1;i++){
             MaxX=MMMM(MyArray3.get(i).getDotsX(),MaxX,2);
             MinX=MMMM(MyArray3.get(i).getDotsX(),MinX,1);
             MaxY=MMMM(MyArray3.get(i).getDotsY(),MaxY,2);
             MinY=MMMM(MyArray3.get(i).getDotsY(),MinY,1);
         }
         MaxX=(MaxX-ShiftHolstX)/Math.exp(ScaleHolst);
         MinX=(MinX-ShiftHolstX)/Math.exp(ScaleHolst);
         MaxY=(MaxY-ShiftHolstY)/Math.exp(ScaleHolst);
         MinY=(MinY-ShiftHolstY)/Math.exp(ScaleHolst);
      }
      for(int i2=1; i2<=(MyArray3.size()/4);i2++){                              //сортировка точек
         middleDotsArray.add(new Dots2(MyArray3.get(1+4*(i2-1)-1).getDotsX(),MyArray3.get(1+4*(i2-1)-1).getDotsY()));
      }
      for(int i2=MyArray3.size()/4; i2>0;i2--){
         middleDotsArray.add(new Dots2(MyArray3.get(3+4*(i2-1)-1).getDotsX(),MyArray3.get(3+4*(i2-1)-1).getDotsY()));
      }
      for(int i2=1; i2<=(MyArray3.size()/4);i2++){
         middleDotsArray.add(new Dots2(MyArray3.get(4+4*(i2-1)-1).getDotsX(),MyArray3.get(4+4*(i2-1)-1).getDotsY()));
      }
      for(int i2=MyArray3.size()/4; i2>0;i2--){
         middleDotsArray.add(new Dots2(MyArray3.get(2+4*(i2-1)-1).getDotsX(),MyArray3.get(2+4*(i2-1)-1).getDotsY()));
      }
      FillLayer layer=new FillLayer();
        if (TypeFill){  //текстура
            if (FillColor != null){
                g.setColor(FillColor);
                layer.fillShape(middleDotsArray,this.type, typeOfTexture, g);//заливка с параметрами , где без текстуры = цветная заливка
            }
        }else{
            if (FillColor != null){
                g.setColor(FillColor);
                layer.fillShape(middleDotsArray,this.type, 0, g);
            }
        }
        if (BorderColor != null){
           g.setColor(BorderColor);
           BorderLayer border= new BorderLayer();
           border.fillBorder(middleDotsArray,this.type , this.TypeBorder, (int)Math.floor(WidthOfBorder*Math.exp(ScaleHolst)/Math.exp(ScaleWidth)), BorderColor, g,this);
        }
   }
   @Override public void addMove(double ShiftHolstX, double ShiftHolstY, double ScaleHolst){
       MyArray.get(0).setDotsX(MyArray.get(0).getDotsX()+(this.ShiftX/Math.exp(ScaleHolst)));
       MyArray.get(0).setDotsY(MyArray.get(0).getDotsY()+(this.ShiftY/Math.exp(ScaleHolst)));
       this.MaxX+=this.ShiftX/Math.exp(ScaleHolst); //для регулирования сетки
       this.MinX+=this.ShiftX/Math.exp(ScaleHolst);
       this.MaxY+=this.ShiftY/Math.exp(ScaleHolst);
       this.MinY+=this.ShiftY/Math.exp(ScaleHolst);
       this.ShiftX=0;
       this.ShiftY=0;
       this.MinXPos=0;
       this.MaxXPos=0;
       this.MinYPos=0;
       this.MaxYPos=0;
       this.PivotX=MyArray.get(0).getDotsX();
       this.PivotY=MyArray.get(0).getDotsY();
    }
   @Override public void addYUpEdgeDeformation (double ShiftHolstX,double ShiftHolstY,double ScaleHolst){  //2-й тип деформации
       a=a*MyScaleX;  //если по нескольким осям ->2 раза
       b=b*MyScaleY;
       MyScaleX=1;
       MyScaleY=1;
       MinY=(MinY*Math.exp(ScaleHolst)+MinYPos)/Math.exp(ScaleHolst);
       MaxX=(MaxX*Math.exp(ScaleHolst)+MaxXPos)/Math.exp(ScaleHolst);
       MinYPos=0;
       MaxXPos=0;
       MyArray.get(0).setDotsX(((MyArray.get(0).getDotsX()*Math.exp(ScaleHolst)+ShiftHolstX+floutToXForDeformation)-ShiftHolstX)/Math.exp(ScaleHolst));
       MyArray.get(0).setDotsY(((MyArray.get(0).getDotsY()*Math.exp(ScaleHolst)+ShiftHolstY+floutToYForDeformation)-ShiftHolstY)/Math.exp(ScaleHolst));
       floutToXForDeformation=0;
       floutToYForDeformation=0;
       this.PivotX=MyArray.get(0).getDotsX();
       this.PivotY=MyArray.get(0).getDotsY();
       if (b <2){
           b=2;
       }
       if (a<2){
           a=2;
       }
    }
   @Override public void endYUpEdgeDeformation (double X, double Y,double ShiftHolstX,double ShiftHolstY,double ScaleHolst){ //в драггеде для 2-й поверхности
       double Yp1;
       if (Math.abs(MaxY-MinY) >=2){//чтобы нельзя было редактировать 2 точки в 1-й и не схлопывать ellips
          if (MaxY*Math.exp(ScaleHolst)-(MinY*Math.exp(ScaleHolst)+(Y-this.faultY))<2){    //повторная проверка
             Yp1=2;    
          }else{
             Yp1=MaxY*Math.exp(ScaleHolst)-(MinY*Math.exp(ScaleHolst)+(Y-this.faultY)); //вычисление новой смещенной границы
          }
          this.MyScaleY=Yp1/(MaxY*Math.exp(ScaleHolst)-MinY*Math.exp(ScaleHolst));  //смежение точек
       }
   }
   @Override public void endYDownEdgeDeformation (double X, double Y,double ShiftHolstX,double ShiftHolstY,double ScaleHolst){ //8-я деформация
       double Yp1;
       if (Math.abs(MaxY-MinY) >=2){
          if ((MaxY*Math.exp(ScaleHolst)-MinY*Math.exp(ScaleHolst))-(this.faultY-Y)<2){
             Yp1=2;    
          }else{
             Yp1=(MaxY*Math.exp(ScaleHolst)+Y-this.faultY)-MinY*Math.exp(ScaleHolst); //вычисление новой смещенной границы
          }
          this.MyScaleY=Yp1/(MaxY*Math.exp(ScaleHolst)-MinY*Math.exp(ScaleHolst));  //смежение точек
       }
   }
   @Override public void endXLeftEdgeDeformation (double X, double Y,double ShiftHolstX,double ShiftHolstY,double ScaleHolst){
      double Xp1;
      if (Math.abs(MaxY-MinY) >=2){
          if (MaxX*Math.exp(ScaleHolst)-(MinX*Math.exp(ScaleHolst)+(X-this.faultX))<2){
             Xp1=2;    
          }else{
             Xp1=MaxX*Math.exp(ScaleHolst)-(MinX*Math.exp(ScaleHolst)+(X-this.faultX)); //вычисление новой смещенной границы
          }
          this.MyScaleX=Xp1/(MaxX*Math.exp(ScaleHolst)-MinX*Math.exp(ScaleHolst));  //смежение точек
      }
   }
   @Override public void endXRightEdgeDeformation (double X, double Y,double ShiftHolstX,double ShiftHolstY,double ScaleHolst){
      double Xp1;
      if (Math.abs(MaxY-MinY) >=2){
         if ((MaxX*Math.exp(ScaleHolst)-MinX*Math.exp(ScaleHolst))-(this.faultX-X)<2){
            Xp1=2;    
         }else{
            Xp1=(MaxX*Math.exp(ScaleHolst)+X-this.faultX)-MinX*Math.exp(ScaleHolst); //вычисление новой смещенной границы
         }
         this.MyScaleX=Xp1/(MaxX*Math.exp(ScaleHolst)-MinX*Math.exp(ScaleHolst));  //смежение точек
      }
   }  
   @Override public void addXRightEdgeDeformation (double ShiftHolstX,double ShiftHolstY,double ScaleHolst){
       addYUpEdgeDeformation(ShiftHolstX, ShiftHolstY, ScaleHolst);
   }
   @Override public void addXYRightUpEdgeDeformation(double ShiftHolstX,double ShiftHolstY,double ScaleHolst){
      addYUpEdgeDeformation(ShiftHolstX, ShiftHolstY, ScaleHolst);
   }
   @Override public void addYDownEdgeDeformation (double ShiftHolstX,double ShiftHolstY,double ScaleHolst){  //2-й тип деформации
       a=a*MyScaleX;  //если по нескольким осям ->2 раза
       b=b*MyScaleY;
       MyScaleX=1;
       MyScaleY=1;
       MaxY=(MaxY*Math.exp(ScaleHolst)+MaxYPos)/Math.exp(ScaleHolst);
       MinX=(MinX*Math.exp(ScaleHolst)+MinXPos)/Math.exp(ScaleHolst);
       MinXPos=0;
       MaxYPos=0;
       MyArray.get(0).setDotsX(((MyArray.get(0).getDotsX()*Math.exp(ScaleHolst)+ShiftHolstX+floutToXForDeformation)-ShiftHolstX)/Math.exp(ScaleHolst));
       MyArray.get(0).setDotsY(((MyArray.get(0).getDotsY()*Math.exp(ScaleHolst)+ShiftHolstY+floutToYForDeformation)-ShiftHolstY)/Math.exp(ScaleHolst));
       floutToXForDeformation=0;
       floutToYForDeformation=0;
       this.PivotX=MyArray.get(0).getDotsX();
       this.PivotY=MyArray.get(0).getDotsY();
       if (b <2){
           b=2;
       }
       if (a<2){
           a=2;
       }
    }
   @Override public void addXLeftEdgeDeformation (double ShiftHolstX,double ShiftHolstY,double ScaleHolst){  //2-й тип деформации
       addYDownEdgeDeformation(ShiftHolstX, ShiftHolstY, ScaleHolst);
   }
   @Override public void addXYLeftDownEdgeDeformation (double ShiftHolstX,double ShiftHolstY,double ScaleHolst){  //2-й тип деформации
       a=a*MyScaleX;  //если по нескольким осям ->2 раза
       b=b*MyScaleY;
       MyScaleX=1;
       MyScaleY=1;
       MaxY=(MaxY*Math.exp(ScaleHolst)+MaxYPos)/Math.exp(ScaleHolst);
       MaxX=(MaxX*Math.exp(ScaleHolst)+MaxXPos)/Math.exp(ScaleHolst);
       MaxXPos=0;
       MaxYPos=0;
       MyArray.get(0).setDotsX(((MyArray.get(0).getDotsX()*Math.exp(ScaleHolst)+ShiftHolstX+floutToXForDeformation)-ShiftHolstX)/Math.exp(ScaleHolst));
       MyArray.get(0).setDotsY(((MyArray.get(0).getDotsY()*Math.exp(ScaleHolst)+ShiftHolstY+floutToYForDeformation)-ShiftHolstY)/Math.exp(ScaleHolst));
       floutToXForDeformation=0;
       floutToYForDeformation=0;
       this.PivotX=MyArray.get(0).getDotsX();
       this.PivotY=MyArray.get(0).getDotsY();
       if (b <2){
           b=2;
       }
       if (a<2){
           a=2;
       }
   }
   @Override public void addXYLeftUpEdgeDeformation (double ShiftHolstX,double ShiftHolstY,double ScaleHolst){  //2-й тип деформации
       a=a*MyScaleX;  //если по нескольким осям ->2 раза
       b=b*MyScaleY;
       MyScaleX=1;
       MyScaleY=1;
       MinY=(MinY*Math.exp(ScaleHolst)+MinYPos)/Math.exp(ScaleHolst);
       MinX=(MinX*Math.exp(ScaleHolst)+MinXPos)/Math.exp(ScaleHolst);
       MinXPos=0;
       MinYPos=0;
       MyArray.get(0).setDotsX(((MyArray.get(0).getDotsX()*Math.exp(ScaleHolst)+ShiftHolstX+floutToXForDeformation)-ShiftHolstX)/Math.exp(ScaleHolst));
       MyArray.get(0).setDotsY(((MyArray.get(0).getDotsY()*Math.exp(ScaleHolst)+ShiftHolstY+floutToYForDeformation)-ShiftHolstY)/Math.exp(ScaleHolst));
       floutToXForDeformation=0;
       floutToYForDeformation=0;
       this.PivotX=MyArray.get(0).getDotsX();
       this.PivotY=MyArray.get(0).getDotsY();
       if (b <2){
           b=2;
       }
       if (a<2){
           a=2;
       }
   }
   @Override public void addXYRightDownEdgeDeformation (double ShiftHolstX,double ShiftHolstY,double ScaleHolst){  //2-й тип деформации
       addYDownEdgeDeformation(ShiftHolstX, ShiftHolstY, ScaleHolst);
   }
   
   @Override public Shape clonable(){
       Ellipse2 newLine = new Ellipse2();
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
       newLine.a=this.a;
       newLine.b=this.b;
       return newLine;
    }
   
}
