/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.Dots2;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author user
 */
public class PolyGone extends PolyLine {
   public PolyGone (){
      this.type="PolyGone";
      this.Name="Полигон";
   }
   @Override public void paint(Graphics g,double ShiftHolstX, double ShiftHolstY, double ScaleHolst){
        g.setColor(BorderColor);
        if (middleDotsArray.size()>0){                                          //чтобы не вылезла ошибка null pointer exeption
           middleDotsArray.clear();
        }
        for (int i=0;i<MyArray.size();i++){
            double XD1=MyArray.get(i).getDotsX()*Math.exp(ScaleHolst)+ShiftHolstX;
            double YD1=MyArray.get(i).getDotsY()*Math.exp(ScaleHolst)+ShiftHolstY;
            double PivotDX=PivotX*Math.exp(ScaleHolst)+ShiftHolstX;
            double PivotDY=PivotY*Math.exp(ScaleHolst)+ShiftHolstY;
            middleDotsArray.add(new Dots2((XD1-PivotDX)*Math.cos(Phi+Phi2)-(YD1-PivotDY)*Math.sin(Phi+Phi2)+PivotDX+ShiftX, (XD1-PivotDX)*Math.sin(Phi+Phi2)+(YD1-PivotDY)*Math.cos(Phi+Phi2)+PivotDY+ShiftY)); //добавление повернутых опорных точек
        }
        if (typeDeformation>0){//при изменении объема
           middleDotsArray.get(0).setDotsX(middleDotsArray.get(0).getDotsX()*MyScaleX);  //вычисление координаты при изменении грани
           double MaxXScaleDots=middleDotsArray.get(0).getDotsX();
           double MinXScaleDots=middleDotsArray.get(0).getDotsX();
           middleDotsArray.get(0).setDotsY(middleDotsArray.get(0).getDotsY()*MyScaleY);
           double MaxYScaleDots=middleDotsArray.get(0).getDotsY();
           double MinYScaleDots=middleDotsArray.get(0).getDotsY();
           for (int i=1;i<middleDotsArray.size();i++){
              middleDotsArray.get(i).setDotsX(middleDotsArray.get(i).getDotsX()*MyScaleX);    
              MaxXScaleDots=MMMM(middleDotsArray.get(i).getDotsX(),MaxXScaleDots,2);
              MinXScaleDots=MMMM(middleDotsArray.get(i).getDotsX(),MinXScaleDots,1);
              middleDotsArray.get(i).setDotsY(middleDotsArray.get(i).getDotsY()*MyScaleY); 
              MaxYScaleDots=MMMM(middleDotsArray.get(i).getDotsY(),MaxYScaleDots,2);
              MinYScaleDots=MMMM(middleDotsArray.get(i).getDotsY(),MinYScaleDots,1);
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
           for (int i=0;i<middleDotsArray.size();i++){
              middleDotsArray.get(i).setDotsX(middleDotsArray.get(i).getDotsX()+floutX);
              middleDotsArray.get(i).setDotsY(middleDotsArray.get(i).getDotsY()+floutY);
           }
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
        if (securityPoint){
           double XPos1=middleDotsArray.get(0).getDotsX();
           double YPos1=middleDotsArray.get(0).getDotsY(); 
           double XPos2=middleDotsArray.get(MyArray.size()-1).getDotsX();
           double YPos2=middleDotsArray.get(MyArray.size()-1).getDotsY();
           g.drawLine((int)Math.floor(XPos1), (int)Math.floor(YPos1), (int)Math.floor(XPos2), (int)Math.floor(YPos2));
        }
        if (MyArray.size() != 0){ //значит ошибка при обнулении
           if (securityPoint !=true){ 
              double XPos1=MyArray.get(MyArray.size()-1).getDotsX()*Math.exp(ScaleHolst)+ShiftHolstX;
              double YPos1=MyArray.get(MyArray.size()-1).getDotsY()*Math.exp(ScaleHolst)+ShiftHolstY;
              double XPos2=X*Math.exp(ScaleHolst)+ShiftHolstX;
              double YPos2=Y*Math.exp(ScaleHolst)+ShiftHolstY;
              double PivotDX=PivotX*Math.exp(ScaleHolst)+ShiftHolstX;
              double PivotDY=PivotY*Math.exp(ScaleHolst)+ShiftHolstY;//конец вычесления экранных координат
              double XPoint=(XPos1-PivotDX)*Math.cos(Phi+Phi2)-(YPos1-PivotDY)*Math.sin(Phi+Phi2)+PivotDX+ShiftX;
              double YPoint=(XPos1-PivotDX)*Math.sin(Phi+Phi2)+(YPos1-PivotDY)*Math.cos(Phi+Phi2)+PivotDY+ShiftY;
              double XPoint2=(XPos2-PivotDX)*Math.cos(Phi+Phi2)-(YPos2-PivotDY)*Math.sin(Phi+Phi2)+PivotDX+ShiftX;
              double YPoint2=(XPos2-PivotDX)*Math.sin(Phi+Phi2)+(YPos2-PivotDY)*Math.cos(Phi+Phi2)+PivotDY+ShiftY;
              g.drawLine((int)Math.floor(XPoint),(int)Math.floor(YPoint),(int)Math.floor(XPoint2),(int)Math.floor(YPoint2));
           }
        }    
   }
   
      @Override public Shape clonable(){
       PolyGone newLine = new PolyGone();
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
       return newLine;
    }
   
}
