/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
import Controller.Dots2;
import java.awt.*;
import java.util.ArrayList;
/**
 *
 * @author user
 */
abstract public class Shape {
   public ArrayList<Dots2> MyArray = new ArrayList<Dots2>();
   public String type = null;
   public double Phi=0;                                                         //Угол поворота точек вокруг центра (обновляется каждый раз при повороте фигуры)
   public boolean securityPoint=false;                                          //для полигона и полилинии
   protected double Phi2=0;                                                        //Значение, которое добавляется в Phi после прекращения поворота
   public double PivotX;
   public double PivotY;                                                        //Координата точки центра поворота
   protected double RotationX1;                                                    //Координата начальная для просчитывания Phi2
   protected double RotationX2;                                                    //Координата конечная для просчитывания Phi2
   protected double RotationY1;
   protected double RotationY2;
   protected double BeginShiftX=0;                                                //начальный Параметр перемещения точек
   protected double BeginShiftY=0;
   protected double ShiftX=0;//сдвиг по x                                         //Смещение точек по X
   protected double ShiftY=0;//сдвиг по Y
   public double MinX=0; //для рисования обводки
   public double MinY=0;
   public double MaxX=0;
   public double MaxY=0;
   protected double MinXPos=0;//для рисования обводки при деформации
   protected double MinYPos=0;
   protected double MaxXPos=0;
   protected double MaxYPos=0;
   public int typeDeformation=0;
   protected double MyScaleX=1;
   protected double MyScaleY=1;
   protected double faultX;
   protected double faultY;
   //protected int typeOfOperation=0;
   public Boolean Visible=true;                                                 // !!!  new properties for PropertyPanel   !!!
   public String Name;
   public Color BorderColor = Color.BLACK;                                      //потом сделать надо так, чтобы эти цвета менялись при создании при помощи MainWindow
   public Color FillColor = null;                                        //потом сделать надо так, чтобы эти цвета менялись при создании при помощи MainWindow
   public boolean TypeBorder=false; //false - линия
   public int WidthOfBorder = 1;
   public boolean TypeFill = false; //false - цвет, иначе - текстура
   public int typeOfTexture=1; //0-нет текстуры , 1 - прямая по горизонту, 2-прямая по вертикале, 3 - наклонная от верхнего левого карая, 4 - наклонная от нижнего левого края
   public ArrayList<Dots2> middleDotsArray=new ArrayList<Dots2>();
   public double ScaleWidth=0; //относительно какого масштаба применено масштабирование ширины линии
   public Shape (){
      this.type="Shape";
   }
   public void paint(Graphics g,double ShiftHolstX, double ShiftHolstY, double ScaleHolst){}
   public void firstclick(double X1,double Y1, double ScaleHolst){} 
   public void lastclick(double X2,double Y2){} 
   public void addPoint() {}
   public void backstep() {}
   public boolean isType(String type){
       if (this.type==type){
           return true;
       }else{
          return false;
       }
    }
   public void beginRotation(int X,int Y){
      this.RotationX1=X;
      this.RotationY1=Y;
   }
   public void endRotation(int X,int Y, double ShiftHolstX, double ShiftHolstY, double ScaleHolst){
      double Alpha;
      double Betta;
      double PX=this.PivotX*Math.exp(ScaleHolst)+ShiftHolstX;
      double PY=this.PivotY*Math.exp(ScaleHolst)+ShiftHolstY;
      Alpha=Math.atan2(this.RotationX1-PX,this.RotationY1-PY);
      Betta=Math.atan2(X-PX,Y-PY);
      this.Phi2=Alpha-Betta;
   }
   public void rebornBorderDots(){}  //для пересчета при повороте
   public void addPhi(){
      this.Phi+=Phi2;
      this.Phi2=0;
      rebornBorderDots();
   }
   public void beginMove(double X,double Y){
      this.BeginShiftX=X;
      this.BeginShiftY=Y;
   }   
   public void endMove(double X,double Y){
      this.ShiftX=X-BeginShiftX;
      this.ShiftY=Y-BeginShiftY;
      this.MinXPos=X-BeginShiftX;
      this.MaxXPos=X-BeginShiftX;
      this.MinYPos=Y-BeginShiftY;
      this.MaxYPos=Y-BeginShiftY;
   }
   public void addMove(double ShiftHolstX, double ShiftHolstY, double ScaleHolst){}//в каждом объекте свое;
   public void PaintBorder(Graphics g,double ShiftHolstX, double ShiftHolstY, double ScaleHolst){
       g.setColor(Color.RED); 
       double XMin=MinX*Math.exp(ScaleHolst)+ShiftHolstX-15+MinXPos; //MinXPos сдвиг
       double YMin=MinY*Math.exp(ScaleHolst)+ShiftHolstY-15+MinYPos;
       double XMax=MaxX*Math.exp(ScaleHolst)+ShiftHolstX+15+MaxXPos;
       double YMax=MaxY*Math.exp(ScaleHolst)+ShiftHolstY+15+MaxYPos;
       //System.out.println("XMin: "+(XMin+15)+", YMin: "+(YMin+15)+", XMax: "+(XMax-15)+", YMax: "+(YMax-15)+".");
       for (int i=(int)Math.floor(XMin);i<=(int)Math.floor(XMax)-5;i+=6){
          g.drawLine(i,(int)Math.floor(YMin),i+4,(int)Math.floor(YMin));
          g.drawLine(i,(int)Math.floor(YMax),i+4,(int)Math.floor(YMax));
          g.drawLine(i,(int)Math.floor((YMax-YMin)*0.3+YMin),i+4,(int)Math.floor((YMax-YMin)*0.3+YMin));
          g.drawLine(i,(int)Math.floor((YMax-YMin)*0.7+YMin),i+4,(int)Math.floor((YMax-YMin)*0.7+YMin));
       }
       for (int i=(int)Math.floor(YMin);i<=(int)Math.floor(YMax)-5;i+=6){
          g.drawLine((int)Math.floor(XMin),i,(int)Math.floor(XMin),i+4);
          g.drawLine((int)Math.floor(XMax),i,(int)Math.floor(XMax),i+4);
          g.drawLine((int)Math.floor((XMax-XMin)*0.3+XMin),i,(int)Math.floor((XMax-XMin)*0.3+XMin),i+4);
          g.drawLine((int)Math.floor((XMax-XMin)*0.7+XMin),i,(int)Math.floor((XMax-XMin)*0.7+XMin),i+4);
       } 
   }
   public void addBorder(){} //в момент добавления фигуры простоя а не сложной
   protected double MMMM(double object,double subject,int type){  //переменная, тип минимакса, тип операции type: 1 - minX, 1-MinY, 2-MaxX, 2-MaxY
       double Z=subject;
       if (1==type & object<subject){
           Z=object;
       }
       if (2==type &object>subject){
           Z=object;
       }
       return Z;
   } 
   public void setTypeDeformation(int Type){
       this.typeDeformation=Type;
   }
   public void startDeformation(double X, double Y){//начало смещения по X и Y в зависимости от типа операции
       this.faultX=X;
       this.faultY=Y;
   }
   public void endYUpEdgeDeformation (double X, double Y,double ShiftHolstX,double ShiftHolstY,double ScaleHolst){ //в драггеде для 2-й поверхности
       double Yp1;
       if (MaxY-MinY !=0){//чтобы нельзя было редактировать 2 точки в 1-й 
          if (MaxY*Math.exp(ScaleHolst)-(MinY*Math.exp(ScaleHolst)+(Y-this.faultY))<1){
             Yp1=1;    
          }else{
             Yp1=MaxY*Math.exp(ScaleHolst)-(MinY*Math.exp(ScaleHolst)+(Y-this.faultY)); //вычисление новой смещенной границы
          }
          this.MyScaleY=Yp1/(MaxY*Math.exp(ScaleHolst)-MinY*Math.exp(ScaleHolst));  //смежение точек
          if (MaxY*Math.exp(ScaleHolst)-(MinY*Math.exp(ScaleHolst)+(Y-this.faultY))<1){
              MinYPos=MaxY*Math.exp(ScaleHolst)-MinY*Math.exp(ScaleHolst)-1;
          }else{
              MinYPos=Y-this.faultY; //для границы смещение
          }
       }
       //System.out.println("Y-this.faultY: "+(Y-this.faultY)+", MaxY*Math.exp(ScaleHolst)-MinY*Math.exp(ScaleHolst): "+(MaxY*Math.exp(ScaleHolst)-MinY*Math.exp(ScaleHolst))+", Yp1: "+Yp1+", MinYPos: "+MinYPos+", MaxY: "+(MaxY*Math.exp(ScaleHolst))+", MinY: "+(MinY*Math.exp(ScaleHolst)) );                        
   }
   public void addYUpEdgeDeformation (double ShiftHolstX,double ShiftHolstY,double ScaleHolst){} 

   public void endYDownEdgeDeformation (double X, double Y,double ShiftHolstX,double ShiftHolstY,double ScaleHolst){ //8-я деформация
       double Yp1;
       if (MaxY-MinY !=0){
          if ((MaxY*Math.exp(ScaleHolst)-MinY*Math.exp(ScaleHolst))-(this.faultY-Y)<1){
             Yp1=1;    
          }else{
             Yp1=(MaxY*Math.exp(ScaleHolst)+Y-this.faultY)-MinY*Math.exp(ScaleHolst); //вычисление новой смещенной границы
          }
          this.MyScaleY=Yp1/(MaxY*Math.exp(ScaleHolst)-MinY*Math.exp(ScaleHolst));  //смежение точек
          if ((MaxY*Math.exp(ScaleHolst)-MinY*Math.exp(ScaleHolst))-(this.faultY-Y)<1){
              MaxYPos=-(MaxY*Math.exp(ScaleHolst)-MinY*Math.exp(ScaleHolst)-1);
          }else{
              MaxYPos=Y-this.faultY; //для границы смещение
          } 
       }
   }
   public void addYDownEdgeDeformation (double ShiftHolstX,double ShiftHolstY,double ScaleHolst){}
   public void endXLeftEdgeDeformation (double X, double Y,double ShiftHolstX,double ShiftHolstY,double ScaleHolst){
      double Xp1;
      if (MaxX-MinX !=0){
          if (MaxX*Math.exp(ScaleHolst)-(MinX*Math.exp(ScaleHolst)+(X-this.faultX))<1){
             Xp1=1;    
          }else{
             Xp1=MaxX*Math.exp(ScaleHolst)-(MinX*Math.exp(ScaleHolst)+(X-this.faultX)); //вычисление новой смещенной границы
          }
          this.MyScaleX=Xp1/(MaxX*Math.exp(ScaleHolst)-MinX*Math.exp(ScaleHolst));  //смежение точек
          if (MaxX*Math.exp(ScaleHolst)-(MinX*Math.exp(ScaleHolst)+(X-this.faultX))<1){
              MinXPos=MaxX*Math.exp(ScaleHolst)-MinX*Math.exp(ScaleHolst)-1;
          }else{
              MinXPos=X-this.faultX; //для границы смещение
          }
      }
   }
   public void addXLeftEdgeDeformation (double ShiftHolstX,double ShiftHolstY,double ScaleHolst){}
   public void endXRightEdgeDeformation (double X, double Y,double ShiftHolstX,double ShiftHolstY,double ScaleHolst){
      double Xp1;
      if (MaxX-MinX !=0){
         if ((MaxX*Math.exp(ScaleHolst)-MinX*Math.exp(ScaleHolst))-(this.faultX-X)<1){
            Xp1=1;    
         }else{
            Xp1=(MaxX*Math.exp(ScaleHolst)+X-this.faultX)-MinX*Math.exp(ScaleHolst); //вычисление новой смещенной границы
         }
         this.MyScaleX=Xp1/(MaxX*Math.exp(ScaleHolst)-MinX*Math.exp(ScaleHolst));  //смежение точек
         if ((MaxX*Math.exp(ScaleHolst)-MinX*Math.exp(ScaleHolst))-(this.faultX-X)<1){
             MaxXPos=-(MaxX*Math.exp(ScaleHolst)-MinX*Math.exp(ScaleHolst)-1);
        }else{
             MaxXPos=X-this.faultX; //для границы смещение
         }
      }
   }
   public void addXRightEdgeDeformation (double ShiftHolstX,double ShiftHolstY,double ScaleHolst){}
   public void endXYLeftUpEdgeDeformation (double X, double Y,double ShiftHolstX,double ShiftHolstY,double ScaleHolst){
      endYUpEdgeDeformation(X,Y,ShiftHolstX,ShiftHolstY,ScaleHolst);
      endXLeftEdgeDeformation(X,Y,ShiftHolstX,ShiftHolstY,ScaleHolst);
   }
   public void addXYLeftUpEdgeDeformation (double ShiftHolstX,double ShiftHolstY,double ScaleHolst){
      addYUpEdgeDeformation(ShiftHolstX,ShiftHolstY,ScaleHolst);
      addXLeftEdgeDeformation(ShiftHolstX,ShiftHolstY,ScaleHolst);
   }
   public void endXYRightUpEdgeDeformation(double X, double Y,double ShiftHolstX,double ShiftHolstY,double ScaleHolst){
      endYUpEdgeDeformation(X,Y,ShiftHolstX,ShiftHolstY,ScaleHolst);
      endXRightEdgeDeformation(X,Y,ShiftHolstX,ShiftHolstY,ScaleHolst);
   }
   public void addXYRightUpEdgeDeformation(double ShiftHolstX,double ShiftHolstY,double ScaleHolst){
      addYUpEdgeDeformation(ShiftHolstX,ShiftHolstY,ScaleHolst);
      addXRightEdgeDeformation(ShiftHolstX,ShiftHolstY,ScaleHolst);
   }
   public void endXYLeftDownEdgeDeformation(double X, double Y,double ShiftHolstX,double ShiftHolstY,double ScaleHolst){
      endYDownEdgeDeformation(X,Y,ShiftHolstX,ShiftHolstY,ScaleHolst);
      endXRightEdgeDeformation(X,Y,ShiftHolstX,ShiftHolstY,ScaleHolst);
   }
   public void addXYLeftDownEdgeDeformation(double ShiftHolstX,double ShiftHolstY,double ScaleHolst){
      addYDownEdgeDeformation(ShiftHolstX,ShiftHolstY,ScaleHolst);
      addXRightEdgeDeformation(ShiftHolstX,ShiftHolstY,ScaleHolst); 
   }
   public void endXYRightDownEdgeDeformation(double X, double Y,double ShiftHolstX,double ShiftHolstY,double ScaleHolst){
      endYDownEdgeDeformation(X,Y,ShiftHolstX,ShiftHolstY,ScaleHolst);
      endXLeftEdgeDeformation(X,Y,ShiftHolstX,ShiftHolstY,ScaleHolst);
   }
   public void addXYRightDownEdgeDeformation(double ShiftHolstX,double ShiftHolstY,double ScaleHolst){
      addYDownEdgeDeformation(ShiftHolstX,ShiftHolstY,ScaleHolst);
      addXLeftEdgeDeformation(ShiftHolstX,ShiftHolstY,ScaleHolst);
   } 
   
   public Shape clonable(){
       return this;
   }
}
