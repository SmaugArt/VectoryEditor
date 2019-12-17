/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.Dots2;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 *
 * @author demin
 */
public class BorderLayer {
    private double phi=Math.toRadians(90);
    public void fillBorder(ArrayList<Dots2> lines,String typeShape ,boolean typeBorder, int WidthOfBorder,Color BorderColor ,Graphics g , Shape fillShape){ //Width border сразу должен быть помножен на ScaleHolst
        //Делим на точки
        ArrayList<Dots2> thisDots=new ArrayList<Dots2>();
        ArrayList<Dots2> thisDots2=new ArrayList<Dots2>();
        for (int i = 0; i<lines.size();i++){                                    //скопировали точки
            thisDots.add(new Dots2(lines.get(i).getDotsX(),lines.get(i).getDotsY()));
        }
        if (typeShape.equals("Triangle") | typeShape.equals("Rectangle") | typeShape.equals("Ellipse2") | typeShape.equals("PolyGone") & fillShape.securityPoint){
           thisDots.add(new Dots2(lines.get(0).getDotsX(),lines.get(0).getDotsY())); //замкнул точки-запяточки, если замкнутые объекты
        }
        for (int i=0; i<thisDots.size()-1;i++){                                    //скопировали точки
            boolean schet=false;
            if (thisDots.get(i).getDotsX()>1400 | thisDots.get(i).getDotsX()<-150 | thisDots.get(i).getDotsY()>570 | thisDots.get(i).getDotsX()<-150){
               schet=true;   
            }
            if (thisDots.get(i+1).getDotsX()>1400 | thisDots.get(i+1).getDotsX()<-150 | thisDots.get(i+1).getDotsY()>570 | thisDots.get(i+1).getDotsX()<-150){
               schet=true;  
            }
            if (schet){                                                         //какая-то точка не лежит в области экрана
               double X1=thisDots.get(i).getDotsX();
               double Y1=thisDots.get(i).getDotsY();
               double X2=thisDots.get(i+1).getDotsX();
               double Y2=thisDots.get(i+1).getDotsY();
               double YStap=Math.abs(Y2-Y1);
               double XStap=Math.abs(X2-X1);
               double Stap=Math.max(XStap, YStap);
               boolean doSec=true;
               boolean inversion=false;
               double stapIn=0;
               while (doSec){                                                   //находим такие точки, между которыми прямая лежит в области экрана
                  stapIn+=1/Stap;
                  double XPos=(X1*(1-stapIn))+(X2*stapIn);
                  double YPos=(Y1*(1-stapIn))+(Y2*stapIn);
                  if (inversion==false){
                      if (XPos<=1400 & XPos>=-150 & YPos<=570 & YPos>=0){
                          thisDots2.add(new Dots2(XPos, YPos));
                          inversion=true;
                      }
                  }else{
                      if (XPos>1400 | XPos<-150 | YPos>570 | YPos<0){
                          thisDots2.add(new Dots2(XPos, YPos));
                          doSec=false;
                      }
                  }
                  if (stapIn>1 & inversion){                                                //аварийное прекращение, если все точки линии не лежат на прямой
                      doSec=false;
                      thisDots2.add(new Dots2(X2, Y2));
                  }
                  if (stapIn>1 & inversion==false){
                      doSec=false;
                  }
               }
            }else{                                                              //точки лежат в области экрана
               thisDots2.add(new Dots2(thisDots.get(i).getDotsX(), thisDots.get(i).getDotsY()));
               thisDots2.add(new Dots2(thisDots.get(i+1).getDotsX(), thisDots.get(i+1).getDotsY()));
            }
        }                         //Занесли все точки в массив
        if (typeBorder){                            //если тип пунктирный //Дробим прямые на прямые
            ArrayList<Dots2> thisDots3=new ArrayList<Dots2>();
            int stap=WidthOfBorder*3;  //может быть на 2 выставлено
            if (WidthOfBorder==0){
                stap=3;
            }
            boolean inversion=false;                                //отвечает за то, чтобы делить прямую на пунктирные линии
            double stapPer=stap;                                                   //длина доступная для точек, которые будут отрисовываться
            double notPer=stap;                                                    //длина доступная для точек, которые не будут отрисовываться
            for (int i=0; i<thisDots2.size()/2;i++){
               double Length2=Math.sqrt(Math.pow(thisDots2.get(i*2).getDotsX()-thisDots2.get(i*2+1).getDotsX(),2)+Math.pow(thisDots2.get(i*2).getDotsY()-thisDots2.get(i*2+1).getDotsY(),2));
               int Length=(int)Math.floor(Length2);  //длина линии
               if (inversion==false){
                   if (Length<=stapPer){
                       thisDots3.add(new Dots2(thisDots2.get(i*2).getDotsX(),thisDots2.get(i*2).getDotsY()));
                       thisDots3.add(new Dots2(thisDots2.get(i*2+1).getDotsX(),thisDots2.get(i*2+1).getDotsY()));
                       stapPer-=Length;
                   }else{
                       double X1=thisDots2.get(i*2).getDotsX();
                       double Y1=thisDots2.get(i*2).getDotsY();
                       double X2=thisDots2.get(i*2+1).getDotsX();
                       double Y2=thisDots2.get(i*2+1).getDotsY();
                       double stapIn=stapPer/Length2;                            //какую часть от прямой следует взять
                       double XPos=(X1*(1-stapIn))+(X2*stapIn);                 //дробим прямую
                       double YPos=(Y1*(1-stapIn))+(Y2*stapIn);
                       thisDots3.add(new Dots2(X1,Y1));                         //добавляем линию отрисовываемую в массив
                       thisDots3.add(new Dots2(XPos,YPos));
                       thisDots2.get(i*2).setDotsX(XPos);                       //сократили прямую, но не перестали её рассматривать
                       thisDots2.get(i*2).setDotsY(YPos);
                       inversion=true;
                       stapPer=0;
                       notPer=stap;
                       i--;
                   }
               }else{
                   if (Length<=notPer){                                         //почти тоже самое как для SPer, но без добавления
                       notPer-=Length;
                   }else{
                       double X1=thisDots2.get(i*2).getDotsX();
                       double Y1=thisDots2.get(i*2).getDotsY();
                       double X2=thisDots2.get(i*2+1).getDotsX();
                       double Y2=thisDots2.get(i*2+1).getDotsY();
                       double stapIn=notPer/Length2;                              //какую часть от прямой следует взять
                       double XPos=(X1*(1-stapIn))+(X2*stapIn);                 //дробим прямую
                       double YPos=(Y1*(1-stapIn))+(Y2*stapIn);
                       thisDots2.get(i*2).setDotsX(XPos);                       //сократили прямую, но не перестали её рассматривать
                       thisDots2.get(i*2).setDotsY(YPos);
                       stapPer=stap;
                       notPer=0;
                       inversion=false;
                       i--;
                   } 
               }
            }
            for (int i=0; i<thisDots3.size()/2;i++){                             //отрисовка линий из thisDots3
                paintBorder((int)Math.floor(thisDots3.get(i*2).getDotsX()), (int)Math.floor(thisDots3.get(i*2).getDotsY()), (int)Math.floor(thisDots3.get(i*2+1).getDotsX()), (int)Math.floor(thisDots3.get(i*2+1).getDotsY()), WidthOfBorder, BorderColor,g);
            }
        }else{
            if (thisDots2.size()>1){
               for (int i=0; i<thisDots2.size()/2;i++){//отрисовка линий из thisDots2
                   paintBorder((int)Math.floor(thisDots2.get(i*2).getDotsX()), (int)Math.floor(thisDots2.get(i*2).getDotsY()), (int)Math.floor(thisDots2.get(i*2+1).getDotsX()), (int)Math.floor(thisDots2.get(i*2+1).getDotsY()), WidthOfBorder, BorderColor, g);
               }
            }
        }
    }
    
    private void paintBorder(int X1, int Y1, int X2, int Y2, int widthBorder ,Color BorderColor, Graphics g){                                                 //рисует линию заданного радиуса
        int width;                                                              //метод рисование толщины прямой линии  if radius <1 then radius = 1 !!!
        if (widthBorder==0){
            width=1;
        }else{
            width=widthBorder;
        }
        float widthFloat=(float)Math.floor(width);
        Graphics2D g2 = (Graphics2D) g; 
        g2.setStroke(new BasicStroke(widthFloat));  // толщина равна 10
        g2.drawLine(X1,Y1,X2,Y2);
        g2.setStroke(new BasicStroke(1));    //чтобы нормально отрисовать границу и    
    }
}
