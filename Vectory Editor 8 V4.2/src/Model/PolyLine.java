/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

//import Controller.Dots;
import Controller.Dots2;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class PolyLine extends Line2 {
    //protected ArrayList<Dots2> MyArray2 = new ArrayList<Dots2>();
    protected double X;
    protected double Y;
    public PolyLine (){
      this.type="PolyLine";
      this.Name="Ломаная линия";
   }
    @Override public void paint(Graphics g,double ShiftHolstX, double ShiftHolstY, double ScaleHolst){                                                 //убрать выбор цвента здесь. Его определяют в процедуре отрисовки заливки и границы
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
           middleDotsArray.get(0).setDotsX(middleDotsArray.get(0).getDotsX()*MyScaleX);  //вычисление координаты при деформации
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
        if (BorderColor != null){
           g.setColor(BorderColor);
           BorderLayer border= new BorderLayer();
           border.fillBorder(middleDotsArray,this.type , this.TypeBorder, (int)Math.floor(WidthOfBorder*Math.exp(ScaleHolst)/Math.exp(ScaleWidth)), BorderColor, g,this);
        }
        if (MyArray.size() !=0){                                                //значит ошибка при обнулении + Нужно для того, чтобы добавилась точка при первом клике без драггеда !
           if (securityPoint != true){                                          //заполняю сам при десиреализации
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
   @Override public void firstclick(double X1,double Y1, double ScaleHolst){
        int i = MyArray.size();
        if (i==0){
           MyArray.add(new Dots2(X1,Y1)); //если первый клик
           this.MaxX=X1;
           this.MinX=X1;
           this.MaxY=Y1;
           this.MinY=Y1;
        } 
        this.X=X1;
        this.Y=Y1;
        ScaleWidth=ScaleHolst;
    } 
    @Override public void lastclick(double X2,double Y2){
        this.X=X2;//+127;
        this.Y=Y2;//+30;
    
    }        
    @Override public void addPoint(){
       MyArray.add(new Dots2(X,Y)); 
       if (MyArray.size()>1){ //обновление точки поворота в момент добавления в список точки
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
    @Override public void backstep(){
        this.securityPoint=true;
    }
    @Override public void addMove(double ShiftHolstX, double ShiftHolstY, double ScaleHolst){
       this.ShiftX=this.ShiftX/Math.exp(ScaleHolst); //для большего перемещения относительно меньшего и наоборот
       this.ShiftY=this.ShiftY/Math.exp(ScaleHolst);
       for (int i=0;i<MyArray.size();i++){
           MyArray.get(i).setDotsX(MyArray.get(i).getDotsX()+ShiftX);
           MyArray.get(i).setDotsY(MyArray.get(i).getDotsY()+ShiftY);
       }
       this.X+=this.ShiftX;
       this.Y+=this.ShiftY;
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
    @Override public void rebornBorderDots(){ //пересчет точек !!!!!!!!!!
       double X1=(MyArray.get(0).getDotsX()-PivotX)*Math.cos(Phi)-(MyArray.get(0).getDotsY()-PivotY)*Math.sin(Phi)+PivotX; //Копия точки X1
       double Y1=(MyArray.get(0).getDotsX()-PivotX)*Math.sin(Phi)+(MyArray.get(0).getDotsY()-PivotY)*Math.cos(Phi)+PivotY;
       MaxX=X1;
       MinX=X1;
       MaxY=Y1;
       MinY=Y1;
       for (int i=1; i<MyArray.size();i++){
          X1=(MyArray.get(i).getDotsX()-PivotX)*Math.cos(Phi)-(MyArray.get(i).getDotsY()-PivotY)*Math.sin(Phi)+PivotX; //Копия точки X1
          Y1=(MyArray.get(i).getDotsX()-PivotX)*Math.sin(Phi)+(MyArray.get(i).getDotsY()-PivotY)*Math.cos(Phi)+PivotY;    
          MaxX=MMMM(X1,MaxX,2);
          MinX=MMMM(X1,MinX,1);
          MaxY=MMMM(Y1,MaxY,2);
          MinY=MMMM(Y1,MinY,1);
       }
   }
    
   @Override public Shape clonable(){
       PolyLine newLine = new PolyLine();
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
