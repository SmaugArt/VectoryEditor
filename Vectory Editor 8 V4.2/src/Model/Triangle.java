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
public class Triangle extends Rectangle {
    public Triangle(){
        this.type="Triangle";
        this.Name="Треугольник";
    }
    @Override public void paint(Graphics g,double ShiftHolstX, double ShiftHolstY, double ScaleHolst){
        if (middleDotsArray.size()>0){                                          //чтобы не вылезла ошибка null pointer exeption
           middleDotsArray.clear();
        }
        ArrayList<Dots2> MyArray2=new ArrayList<Dots2>();
        for (int i=0;i<MyArray.size();i++){
            MyArray2.add(new Dots2(MyArray.get(i).getDotsX()*Math.exp(ScaleHolst)+ShiftHolstX,MyArray.get(i).getDotsY()*Math.exp(ScaleHolst)+ShiftHolstY));
        }
        double PivotDX=this.PivotX*Math.exp(ScaleHolst)+ShiftHolstX; 
        double PivotDY=this.PivotY*Math.exp(ScaleHolst)+ShiftHolstY;    //конец вычисления
        double X;
        double Y;
        for (int i=0;i<MyArray.size();i++){
            X=(MyArray2.get(i).getDotsX()-PivotDX)*Math.cos(Phi+Phi2)-(MyArray2.get(i).getDotsY()-PivotDY)*Math.sin(Phi+Phi2)+PivotDX+ShiftX;
            Y=(MyArray2.get(i).getDotsX()-PivotDX)*Math.sin(Phi+Phi2)+(MyArray2.get(i).getDotsY()-PivotDY)*Math.cos(Phi+Phi2)+PivotDY+ShiftY;
            middleDotsArray.add(new Dots2(X,Y));
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
    }   
    @Override public void lastclick(double X2,double Y2){
        for (int i=MyArray.size()-1;i>0;i--){
            MyArray.remove(i);
        }
        MyArray.add(new Dots2(MyArray.get(0).getDotsX(),Y2));
        MyArray.add(new Dots2(X2,Y2));
        double X=0;
        double Y=0;
        for(int i=0;i<MyArray.size();i++){
           X+=MyArray.get(i).getDotsX();
           Y+=MyArray.get(i).getDotsY();
        }
        this.PivotX=X/3;
        this.PivotY=Y/3;
    }
    
    @Override public Shape clonable(){
       Triangle newLine = new Triangle();
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
