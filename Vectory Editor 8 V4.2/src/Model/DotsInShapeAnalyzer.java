/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.Dots2;
import View.MainWindow;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 *
 * @author demin
 */
public class DotsInShapeAnalyzer {
   public int searchClickX=0;
   public int searchClickY=0;
   private MainWindow Frame = MainWindow.getThisFrame();
   private Controller.ShapeArrayList ShapeAL;
   public DotsInShapeAnalyzer (Controller.ShapeArrayList ShapeAL){              //передаем ссылку на нашь массив шейпов,чтобы их потом проссмотреть
       this.ShapeAL=ShapeAL;
   }
   public int feelOutShape(int clickX,int clickY ,boolean typeAnalyzer){  //true - зависит от ширины линии, //false - не зависит
       int nomberShape=-1;
       if (ShapeAL.size()>0){
               int ClickDotX=clickX;
               int ClickDotY=clickY;
               boolean sec=false;                                               //отвечает за то, что объект пересечения найден
               boolean Sec2=false;                                              //отвечает за то, что нужно ли поднимать точку клика вниз или отпускать обратно, если не нашло пересечений
               int posElement=ShapeAL.size()-1;
               ArrayList<Dots2> dots = new ArrayList<Dots2>();
               ArrayList<Dots2> dots2 = new ArrayList<Dots2>();
               while (sec != true & posElement>-1){
                   if (ShapeAL.get(posElement).Visible){
                     int numberOfIntersections=0;
                     boolean typeShape=false;
                     for (int i=0;i<ShapeAL.get(posElement).middleDotsArray.size()-1;i++){ //по каждой прямой
                         if (ShapeAL.get(posElement).type.equals("PolyGone") | ShapeAL.get(posElement).type.equals("Rectangle") | ShapeAL.get(posElement).type.equals("Triangle") | ShapeAL.get(posElement).type.equals("Ellipse2")){
                             typeShape=false;
                         }else{
                             typeShape=true;
                         }
                         if (typeAnalyzer){
                             if (isClickInSide2(ClickDotX, ClickDotY,ShapeAL.get(posElement).middleDotsArray.get(i).getDotsX(), ShapeAL.get(posElement).middleDotsArray.get(i).getDotsY(), ShapeAL.get(posElement).middleDotsArray.get(i+1).getDotsX(), ShapeAL.get(posElement).middleDotsArray.get(i+1).getDotsY(),typeShape, (int)Math.floor(ShapeAL.get(posElement).WidthOfBorder/Math.exp(ShapeAL.get(posElement).ScaleWidth)*Math.exp(Frame.drawPanel.ScaleHolst)))){
                               numberOfIntersections++;
                               dots.add(new Dots2(searchClickX,searchClickY));     //добавление точек в массив точек
                            }
                         }else{
                            if (isClickInSide(ClickDotX, ClickDotY,ShapeAL.get(posElement).middleDotsArray.get(i).getDotsX(), ShapeAL.get(posElement).middleDotsArray.get(i).getDotsY(), ShapeAL.get(posElement).middleDotsArray.get(i+1).getDotsX(), ShapeAL.get(posElement).middleDotsArray.get(i+1).getDotsY(),typeShape)){
                               numberOfIntersections++;
                               dots.add(new Dots2(searchClickX,searchClickY));     //добавление точек в массив точек
                            }
                         }
                     }
                     if (ShapeAL.get(posElement).type.equals("PolyGone") | ShapeAL.get(posElement).type.equals("Rectangle") | ShapeAL.get(posElement).type.equals("Triangle") | ShapeAL.get(posElement).type.equals("Ellipse2")){                           //чтобы закрыть фигуру
                         if (isClickInSide(ClickDotX, ClickDotY, ShapeAL.get(posElement).middleDotsArray.get(ShapeAL.get(posElement).middleDotsArray.size()-1).getDotsX(), ShapeAL.get(posElement).middleDotsArray.get(ShapeAL.get(posElement).middleDotsArray.size()-1).getDotsY(), ShapeAL.get(posElement).middleDotsArray.get(0).getDotsX(), ShapeAL.get(posElement).middleDotsArray.get(0).getDotsY(),typeShape)){
                            numberOfIntersections++;
                            dots.add(new Dots2(searchClickX,searchClickY));     //добавление точек в массив точек пересечения
                         }
                     }
                     if (dots.size()>1){                                        // проссматриваем массив точек и удаляем 2 найденных совпадения по X-m т.к. Y должны быть одинаковы
                         int positionDots=0;
                         int positionSimilarDots=1;
                         while (positionDots<dots.size()-1){
                            if (dots.get(positionDots).getDotsX()==dots.get(positionSimilarDots).getDotsX()){
                                dots2.add(new Dots2(dots.get(positionSimilarDots).getDotsX(),dots.get(positionSimilarDots).getDotsY()));
                                dots.remove(positionSimilarDots);
                                dots.remove(positionDots);
                                if ((positionDots-1)>=0){
                                    positionDots--;
                                }else{
                                    positionDots=0;
                                }
                                positionSimilarDots=positionDots+1;
                            }else{
                                if (positionSimilarDots+1<dots.size()){
                                    positionSimilarDots++;
                                }else{
                                    positionDots++;
                                    positionSimilarDots=positionDots+1;
                                }
                            }
                         }
                     }
                     if (dots.size()%2==1 & !(ShapeAL.get(posElement).type.equals("Ellipse2"))| dots2.size()%2==1 & ShapeAL.get(posElement).type.equals("Ellipse2") & dots.size()%2==0| ShapeAL.get(posElement).type.equals("Ellipse2") & dots2.size()%2==0 & dots.size()%2==1){//проверка
                         sec=true;
                     }else{
                         if (Sec2 != true){                                                                      //повторить вышеперечисленные действия для точки, которая ниже на 1 пиксель следовательно поставинь некоторый sec2 на такое событие
                             Sec2=true;
                             ClickDotY=ClickDotY-1;
                             posElement++;
                         }else{
                             Sec2=false;
                             ClickDotY=ClickDotY+1; 
                         }                                                      //System.out.println("size dots: "+dots.size()+",size dots2: "+dots2.size());
                     }
                  }
                  posElement--;
                  dots.clear();
                  dots2.clear();
               }
               if (sec){
                   nomberShape=posElement+1;                                    //System.out.println(ShapeAL.get(posElement+1).Name);
               }
            }
       return nomberShape;
   }
   public boolean isClickInSide (double XClickPos, double YClickPos, double X0, double Y0, double X1, double Y1, boolean typeObject){   //первые 2 параметра - место клика, 4 остальные - линия границы фигуры, последний - тип фигуры (с заливкой = 0, иначе - без)
        boolean chislo=false;
        double ResX=0;
        double ResY=0;
        boolean badException=false;       
        if (!(X0<XClickPos & X1<XClickPos)){
                double A1=0; //одинаковый Y у прямой от точки
                double B1=-5; //одинаковая разница x-в
                double C1=(5)*YClickPos;
                double A2=(Y1-Y0);
                double B2=(X0-X1);
                double C2=(X1-X0)*Y0-(Y1-Y0)*X0;
                double resX=0;
                double resY=0;
                try{
                   resX=(((B1*C2)-(B2*C1))/((A1*B2)-(A2*B1)));
                   resY=(((C1*A2)-(C2*A1))/((A1*B2)-(A2*B1)));
                }catch (Exception e) {
                    badException=true;
                }
                if (badException){                                              //проверka на пересечение при ошибке и добавление нужной точки по возможности
                    if ((int)Math.floor(Y0)==(int)Math.floor(YClickPos) & (int)Math.floor(Y1)==(int)Math.floor(YClickPos)){
                        chislo=true;
                        searchClickX=(int)Math.floor(X0);
                        searchClickY=(int)Math.floor(Y0);
                        if ((int)Math.floor(X0)>(int)Math.floor(X1) & (int)Math.floor(X1)>=XClickPos){          //проверка для эллипса, т.к. линия параллельная ожет идти разно последовательностью ей 2-х концов
                            searchClickX=(int)Math.floor(X1);
                        }
                    }
                }else{
                    if (resX>=X0 & resX<=X1 | resX>=X1 & resX<=X0){
                       if (resY>=Y0 & resY<=Y1 | resY>=Y1 & resY<=Y0){
                           if (typeObject != true){
                               if (resX>=XClickPos){
                                  chislo=true;
                                  searchClickX=(int)Math.floor(resX);
                                  searchClickY=(int)Math.floor(resY);
                               }
                           }else{
                               if (resX>=XClickPos-5 & resX<=XClickPos+5){        //для незамкнутых фигур, чтобы точка попадала в маленький отрезочек
                                  chislo=true; 
                                  searchClickX=(int)Math.floor(resX);
                                  searchClickY=(int)Math.floor(resY);
                               }
                           }
                       }    
                    }
                }
        }
        return chislo;
    }
   
    public boolean isClickInSide2 (double XClickPos, double YClickPos, double X0, double Y0, double X1, double Y1, boolean typeObject, int widthBorder){   //первые 2 параметра - место клика, 4 остальные - линия границы фигуры, последний - тип фигуры (с заливкой = 0, иначе - без)
        int width;
        if (widthBorder==0){
            width = 1;
        }else{
            width=widthBorder;
        }
        boolean chislo=false;
        boolean badException=false;
        double A1=0; //одинаковый Y у прямой от точки
        double B1=-5; //одинаковая разница x-в
        double C1=(5)*YClickPos;
        double A2=(Y1-Y0);
        double B2=(X0-X1);
        double C2=(X1-X0)*Y0-(Y1-Y0)*X0;
        double resX=0;
        double resY=0;
        try{
           resX=(((B1*C2)-(B2*C1))/((A1*B2)-(A2*B1)));
           resY=(((C1*A2)-(C2*A1))/((A1*B2)-(A2*B1)));
        }catch (Exception e) {
           badException=true;
        }
        if (!(X0<XClickPos & X1<XClickPos)){
                if (badException){                                              //проверka на пересечение при ошибке и добавление нужной точки по возможности
                    if ((int)Math.floor(Y0)==(int)Math.floor(YClickPos) & (int)Math.floor(Y1)==(int)Math.floor(YClickPos)){
                        chislo=true;
                        searchClickX=(int)Math.floor(X0);
                        searchClickY=(int)Math.floor(Y0);
                        if ((int)Math.floor(X0)>(int)Math.floor(X1) & (int)Math.floor(X1)>=XClickPos){          //проверка для эллипса, т.к. линия параллельная ожет идти разно последовательностью ей 2-х концов
                            searchClickX=(int)Math.floor(X1);
                        }
                    }
                }else{
                    if (resX>=X0 & resX<=X1 | resX>=X1 & resX<=X0){
                       if (resY>=Y0 & resY<=Y1 | resY>=Y1 & resY<=Y0){
                           if (typeObject != true){
                               if (resX>=XClickPos){
                                  chislo=true;
                                  searchClickX=(int)Math.floor(resX);
                                  searchClickY=(int)Math.floor(resY);
                               }
                           }else{
                               if (resX>=XClickPos-5 & resX<=XClickPos+5){        //для незамкнутых фигур, чтобы точка попадала в маленький отрезочек
                                  chislo=true; 
                                  searchClickX=(int)Math.floor(resX);
                                  searchClickY=(int)Math.floor(resY);
                               }
                           }
                       }    
                    }
                }
        }
        if (chislo==false & typeObject){
            double YMax;
            double YMin;
            double XMax;//x принадлежащий YMAx
            double XMin;
            if (Y0>Y1){
                YMax=Y0;
                XMax=X0;
                YMin=Y1;
                XMin=X1;
            }else{
                YMax=Y1;
                XMax=X1;
                YMin=Y0;
                XMin=X0;
            }
            double diagonal=Math.sqrt(2*Math.pow(widthBorder, 2))/2;
            if (Math.abs(YClickPos-YMax)<=diagonal & Math.abs(XClickPos-XMax)<=diagonal){
                chislo=true; 
                searchClickX=(int)Math.floor(resX);
                searchClickY=(int)Math.floor(resY);
            }
            if (Math.abs(YClickPos-YMin)<=diagonal & Math.abs(XClickPos-XMin)<=diagonal){
                chislo=true; 
                searchClickX=(int)Math.floor(resX);
                searchClickY=(int)Math.floor(resY);
            }
            if (YMax>=YClickPos & YMin<=YClickPos & Math.abs(XClickPos-resX)<=diagonal){
                chislo=true; 
                searchClickX=(int)Math.floor(resX);
                searchClickY=(int)Math.floor(resY);
            }
//            if (X0>X1){
//                XMax=X0;
//                XMin=X1;
//            }else{
//                XMax=X1;
//                XMin=X0;
//            }
//            if (XMax>=XClickPos & XMin<=XClickPos & Math.abs(YClickPos-resY)<=diagonal | XMax>=XClickPos & XMin<=XClickPos & Math.abs(YClickPos-resY)<=diagonal){
//                chislo=true; 
//                searchClickX=(int)Math.floor(resX);
//                searchClickY=(int)Math.floor(resY);
//            }
        }
        return chislo;
    }
   
}
