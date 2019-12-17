/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.Dots2;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

/**
 *
 * @author user
 */
public class Line2 extends Shape {
    public Line2(){
        this.type="Line2";
        this.Name="Линия";
    }
    @Override public void paint(Graphics g,double ShiftHolstX, double ShiftHolstY, double ScaleHolst){
        g.setColor(BorderColor); 
        double XD1=MyArray.get(0).getDotsX()*Math.exp(ScaleHolst)+ShiftHolstX;
        double YD1=MyArray.get(0).getDotsY()*Math.exp(ScaleHolst)+ShiftHolstY;
        double XD2=MyArray.get(1).getDotsX()*Math.exp(ScaleHolst)+ShiftHolstX;
        double YD2=MyArray.get(1).getDotsY()*Math.exp(ScaleHolst)+ShiftHolstY;   
        double PivotDX=this.PivotX*Math.exp(ScaleHolst)+ShiftHolstX; 
        double PivotDY=this.PivotY*Math.exp(ScaleHolst)+ShiftHolstY; 
        double X3=(XD1-PivotDX)*Math.cos(Phi+Phi2)-(YD1-PivotDY)*Math.sin(Phi+Phi2)+PivotDX+ShiftX; //Копия точки X1
        double Y3=(XD1-PivotDX)*Math.sin(Phi+Phi2)+(YD1-PivotDY)*Math.cos(Phi+Phi2)+PivotDY+ShiftY;
        double X4=(XD2-PivotDX)*Math.cos(Phi+Phi2)-(YD2-PivotDY)*Math.sin(Phi+Phi2)+PivotDX+ShiftX;  ////Копия точки X2
        double Y4=(XD2-PivotDX)*Math.sin(Phi+Phi2)+(YD2-PivotDY)*Math.cos(Phi+Phi2)+PivotDY+ShiftY;
        if (typeDeformation>0){//при изменении объема
           X3=X3*MyScaleX;  //вычисление координаты при изменении грани
           double MaxXScaleDots=X3;
           double MinXScaleDots=X3;
           Y3=Y3*MyScaleY;
           double MaxYScaleDots=Y3;
           double MinYScaleDots=Y3;
           X4=X4*MyScaleX;
           Y4=Y4*MyScaleY;
           MaxXScaleDots=MMMM(X4,MaxXScaleDots,2);
           MaxYScaleDots=MMMM(Y4,MaxYScaleDots,2);
           MinXScaleDots=MMMM(X4,MinXScaleDots,1);
           MinYScaleDots=MMMM(Y4,MinYScaleDots,1);
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
           X3+=floutX;  
           X4+=floutX;
           Y3+=floutY;  
           Y4+=floutY;  
        }  
        //g.drawLine((int)Math.floor(X3),(int)Math.floor(Y3),(int)Math.floor(X4),(int)Math.floor(Y4));
        if (middleDotsArray.size()>0){                                          //чтобы не вылезла ошибка null pointer exeption
           middleDotsArray.clear();
        }
        middleDotsArray.add(new Dots2(X3,Y3));                                  //добавляю точки для использования их при выделении объекта (они всегда в экранных координатах для мгновенного поиска)
        middleDotsArray.add(new Dots2(X4,Y4));
        if (BorderColor != null){
           g.setColor(BorderColor);
           BorderLayer border= new BorderLayer();
           border.fillBorder(middleDotsArray,this.type , this.TypeBorder, (int)Math.floor(WidthOfBorder*Math.exp(ScaleHolst)/Math.exp(ScaleWidth)), BorderColor, g,this);
        }
    } 
    
    @Override public void firstclick(double X1,double Y1, double ScaleHolst){
        MyArray.add(new Dots2(X1,Y1));//1-я точка
        MyArray.add(new Dots2(X1,Y1));//2-я точка, если не используется dragged
        this.MaxX=X1;
        this.MinX=X1;
        this.MaxY=Y1;
        this.MinY=Y1;
        PivotX=X1;//если без dragged'a
        PivotY=Y1;
        ScaleWidth=ScaleHolst;
    } 
    @Override public void lastclick(double X2,double Y2){
        MyArray.get(MyArray.size()-1).setDotsX(X2);
        MyArray.get(MyArray.size()-1).setDotsY(Y2);
        this.PivotX=(MyArray.get(MyArray.size()-1).getDotsX()+MyArray.get(MyArray.size()-2).getDotsX())/2;
        this.PivotY=(MyArray.get(MyArray.size()-1).getDotsY()+MyArray.get(MyArray.size()-2).getDotsY())/2;
    } 
    @Override public void addBorder(){
       MaxX=MMMM(MyArray.get(MyArray.size()-1).getDotsX(),MaxX,2);
       MinX=MMMM(MyArray.get(MyArray.size()-1).getDotsX(),MinX,1);
       MaxY=MMMM(MyArray.get(MyArray.size()-1).getDotsY(),MaxY,2);
       MinY=MMMM(MyArray.get(MyArray.size()-1).getDotsY(),MinY,1);
    }    
    @Override public void addMove(double ShiftHolstX, double ShiftHolstY, double ScaleHolst){
       this.ShiftX=this.ShiftX/Math.exp(ScaleHolst); //для большего перемещения относительно меньшего и наоборот
       this.ShiftY=this.ShiftY/Math.exp(ScaleHolst);
       for(int i=0;i<MyArray.size();i++){
           MyArray.get(i).setDotsX(MyArray.get(i).getDotsX()+ShiftX);
           MyArray.get(i).setDotsY(MyArray.get(i).getDotsY()+ShiftY);
       }
       this.PivotX+=this.ShiftX;
       this.PivotY+=this.ShiftY;
       this.MaxX+=this.ShiftX;
       this.MinX+=this.ShiftX;
       this.MaxY+=this.ShiftY;
       this.MinY+=this.ShiftY;
       this.ShiftX=0;
       this.ShiftY=0;
       this.MinXPos=0;
       this.MaxXPos=0;
       this.MinYPos=0;
       this.MaxYPos=0;
    }
    @Override public void rebornBorderDots(){
       MaxX=PivotX;
       MinX=PivotX;
       MaxY=PivotY;
       MinY=PivotY;
       for(int i=0;i<MyArray.size();i++){
          double X=(MyArray.get(i).getDotsX()-PivotX)*Math.cos(Phi)-(MyArray.get(i).getDotsY()-PivotY)*Math.sin(Phi)+PivotX; //Копия точки X1
          double Y=(MyArray.get(i).getDotsX()-PivotX)*Math.sin(Phi)+(MyArray.get(i).getDotsY()-PivotY)*Math.cos(Phi)+PivotY; 
          MaxX=MMMM(X,MaxX,2);
          MinX=MMMM(X,MinX,1);
          MaxY=MMMM(Y,MaxY,2);
          MinY=MMMM(Y,MinY,1);
       }
   }
    @Override public void addYUpEdgeDeformation (double ShiftHolstX,double ShiftHolstY,double ScaleHolst){  //2-й тип деформации
       double PivotDX=(PivotX*Math.exp(ScaleHolst)+ShiftHolstX); //так как смещения по x-су нету !
       double PivotDY=(PivotY*Math.exp(ScaleHolst)+ShiftHolstY);//*MyScaleY;
       for(int i=0;i<MyArray.size();i++){
           double Y=MyArray.get(i).getDotsY()*Math.exp(ScaleHolst)+ShiftHolstY;
           double X=MyArray.get(i).getDotsX()*Math.exp(ScaleHolst)+ShiftHolstX;
           double X2=(X-PivotDX)*Math.cos(Phi)-(Y-PivotDY)*Math.sin(Phi)+PivotDX;
           double Y2=(X-PivotDX)*Math.sin(Phi)+(Y-PivotDY)*Math.cos(Phi)+PivotDY;
           Y2=Y2*MyScaleY;
           MyArray.get(i).setDotsY((Y2-ShiftHolstY)/Math.exp(ScaleHolst));//это же после смещения
           MyArray.get(i).setDotsX((X2-ShiftHolstX)/Math.exp(ScaleHolst));
       }
       Phi=0;
       double MaxYScaleDots=MyArray.get(0).getDotsY();
       for(int i=1;i<MyArray.size();i++){
           MaxYScaleDots=MMMM(MyArray.get(i).getDotsY(),MaxYScaleDots,2);
       }
       double fault=MaxY-MaxYScaleDots;
       double P1=0;
       double P2=0;
       for (int i=0;i<MyArray.size();i++){
           MyArray.get(i).setDotsY(MyArray.get(i).getDotsY()+fault);
           P1+=MyArray.get(i).getDotsY();
           P2+=MyArray.get(i).getDotsX();
           
       }
       MyScaleY=1;
       PivotY=P1/MyArray.size();
       PivotX=P2/MyArray.size();
       MinY+=MinYPos/Math.exp(ScaleHolst);
       MinYPos=0;
    }
    public void addYDownEdgeDeformation (double ShiftHolstX,double ShiftHolstY,double ScaleHolst){
       double PivotDX=(PivotX*Math.exp(ScaleHolst)+ShiftHolstX); //так как смещения по x-су нету !
       double PivotDY=(PivotY*Math.exp(ScaleHolst)+ShiftHolstY);//*MyScaleY;
       for(int i=0;i<MyArray.size();i++){
           double Y=MyArray.get(i).getDotsY()*Math.exp(ScaleHolst)+ShiftHolstY;
           double X=MyArray.get(i).getDotsX()*Math.exp(ScaleHolst)+ShiftHolstX;
           double X2=(X-PivotDX)*Math.cos(Phi)-(Y-PivotDY)*Math.sin(Phi)+PivotDX;
           double Y2=(X-PivotDX)*Math.sin(Phi)+(Y-PivotDY)*Math.cos(Phi)+PivotDY;
           Y2=Y2*MyScaleY;
           MyArray.get(i).setDotsY((Y2-ShiftHolstY)/Math.exp(ScaleHolst));//это же после смещения
           MyArray.get(i).setDotsX((X2-ShiftHolstX)/Math.exp(ScaleHolst));
       }
       Phi=0;
       double MinYScaleDots=MyArray.get(0).getDotsY();
       for(int i=1;i<MyArray.size();i++){
           MinYScaleDots=MMMM(MyArray.get(i).getDotsY(),MinYScaleDots,1);
       }
       double fault=MinY-MinYScaleDots;
       double P1=0;
       double P2=0;
       for (int i=0;i<MyArray.size();i++){
           MyArray.get(i).setDotsY(MyArray.get(i).getDotsY()+fault);
           P1+=MyArray.get(i).getDotsY();
           P2+=MyArray.get(i).getDotsX();  
       }
       MyScaleY=1;
       PivotY=P1/MyArray.size();
       PivotX=P2/MyArray.size();
       MaxY+=MaxYPos/Math.exp(ScaleHolst);
       MaxYPos=0;
    }
    @Override public void addXLeftEdgeDeformation (double ShiftHolstX,double ShiftHolstY,double ScaleHolst){  //2-й тип деформации
       double PivotDX=(PivotX*Math.exp(ScaleHolst)+ShiftHolstX); //так как смещения по x-су нету !
       double PivotDY=(PivotY*Math.exp(ScaleHolst)+ShiftHolstY);//*MyScaleY;
       for(int i=0;i<MyArray.size();i++){
           double Y=MyArray.get(i).getDotsY()*Math.exp(ScaleHolst)+ShiftHolstY;
           double X=MyArray.get(i).getDotsX()*Math.exp(ScaleHolst)+ShiftHolstX;
           double X2=(X-PivotDX)*Math.cos(Phi)-(Y-PivotDY)*Math.sin(Phi)+PivotDX;
           double Y2=(X-PivotDX)*Math.sin(Phi)+(Y-PivotDY)*Math.cos(Phi)+PivotDY;
           X2=X2*MyScaleX;
           MyArray.get(i).setDotsY((Y2-ShiftHolstY)/Math.exp(ScaleHolst));//это же после смещения
           MyArray.get(i).setDotsX((X2-ShiftHolstX)/Math.exp(ScaleHolst));
       }
       Phi=0;
       double MaxXScaleDots=MyArray.get(0).getDotsX();
       for(int i=1;i<MyArray.size();i++){
           MaxXScaleDots=MMMM(MyArray.get(i).getDotsX(),MaxXScaleDots,2);
       }
       double fault=MaxX-MaxXScaleDots;
       double P1=0;
       double P2=0;
       for (int i=0;i<MyArray.size();i++){
           MyArray.get(i).setDotsX(MyArray.get(i).getDotsX()+fault);
           P1+=MyArray.get(i).getDotsY();
           P2+=MyArray.get(i).getDotsX();
           
       }
       MyScaleX=1;
       PivotY=P1/MyArray.size();
       PivotX=P2/MyArray.size();
       MinX+=MinXPos/Math.exp(ScaleHolst);
       MinXPos=0;
    }
    public void addXRightEdgeDeformation (double ShiftHolstX,double ShiftHolstY,double ScaleHolst){
       double PivotDX=(PivotX*Math.exp(ScaleHolst)+ShiftHolstX); //так как смещения по x-су нету !
       double PivotDY=(PivotY*Math.exp(ScaleHolst)+ShiftHolstY);//*MyScaleY;
       for(int i=0;i<MyArray.size();i++){
           double Y=MyArray.get(i).getDotsY()*Math.exp(ScaleHolst)+ShiftHolstY;
           double X=MyArray.get(i).getDotsX()*Math.exp(ScaleHolst)+ShiftHolstX;
           double X2=(X-PivotDX)*Math.cos(Phi)-(Y-PivotDY)*Math.sin(Phi)+PivotDX;
           double Y2=(X-PivotDX)*Math.sin(Phi)+(Y-PivotDY)*Math.cos(Phi)+PivotDY;
           X2=X2*MyScaleX;
           MyArray.get(i).setDotsY((Y2-ShiftHolstY)/Math.exp(ScaleHolst));//это же после смещения
           MyArray.get(i).setDotsX((X2-ShiftHolstX)/Math.exp(ScaleHolst));
       }
       Phi=0;
       double MinXScaleDots=MyArray.get(0).getDotsX();
       for(int i=1;i<MyArray.size();i++){
           MinXScaleDots=MMMM(MyArray.get(i).getDotsX(),MinXScaleDots,1);
       }
       double fault=MinX-MinXScaleDots;
       double P1=0;
       double P2=0;
       for (int i=0;i<MyArray.size();i++){
           MyArray.get(i).setDotsX(MyArray.get(i).getDotsX()+fault);
           P1+=MyArray.get(i).getDotsY();
           P2+=MyArray.get(i).getDotsX();  
       }
       MyScaleX=1;
       PivotY=P1/MyArray.size();
       PivotX=P2/MyArray.size();
       MaxX+=MaxXPos/Math.exp(ScaleHolst);
       MaxXPos=0;
    }
    
    @Override public Shape clonable(){
       Line2 newLine = new Line2();
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
       return newLine;
    }
}
