/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.Dots2;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author demin
 */
public class FillLayer {
    public void fillShape(ArrayList<Dots2> lines,String typeShape, int typeFill ,Graphics g){                 //тип фигуры не нужен, т.к. заливка будет работать только для неразрывных фигур + точки вычеслены уже в экранном формате
       int thisMinX=(int)Math.floor(lines.get(0).getDotsX()); //высчитывай заного !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
       int thisMinY=(int)Math.floor(lines.get(0).getDotsY());
       int thisMaxX=(int)Math.floor(lines.get(0).getDotsX());
       int thisMaxY=(int)Math.floor(lines.get(0).getDotsY());
       for (int i=1; i<lines.size();i++){
           thisMinX=(int)Math.floor(MMMM(lines.get(i).getDotsX(),thisMinX,1));
           thisMinY=(int)Math.floor(MMMM(lines.get(i).getDotsY(),thisMinY,1));
           thisMaxX=(int)Math.floor(MMMM(lines.get(i).getDotsX(),thisMaxX,2));
           thisMaxY=(int)Math.floor(MMMM(lines.get(i).getDotsY(),thisMaxY,2));
       }
       if (thisMinY<0){                                                         //оптимизация процесса рисования лишь части объекта
           thisMinY=0;
       }
       if (thisMaxY>550){
           thisMaxY=550;
       }
       ArrayList<Dots2> allDots=new ArrayList<Dots2>();
       ArrayList<Dots2> layerDots=new ArrayList<Dots2>();                       //все пересечения на отрезке от по X-м на каждом Y (естественно каждый раз обнуляется и заносится в общий массив)
       View.MainWindow Frame = View.MainWindow.getThisFrame();
       DotsInShapeAnalyzer analyzer=new DotsInShapeAnalyzer(Frame.ShapeAL);
       for (int i=thisMinY; i<=thisMaxY;i++){                                   //находим по всем слоям точки закраски
          for (int nomberDots=0; nomberDots<lines.size()-1;nomberDots++){
              if (analyzer.isClickInSide(thisMinX, i, lines.get(nomberDots).getDotsX(), lines.get(nomberDots).getDotsY(), lines.get(nomberDots+1).getDotsX(), lines.get(nomberDots+1).getDotsY(), false)){
                  layerDots.add(new Dots2(analyzer.searchClickX,i));            //по каким-то неясным причинам, происходит погрешность вычисления, где analyzer.searchClickY<i
              }
          }
          if (analyzer.isClickInSide(thisMinX, i, lines.get(0).getDotsX(), lines.get(0).getDotsY(), lines.get(lines.size()-1).getDotsX(), lines.get(lines.size()-1).getDotsY(), false)){
              layerDots.add(new Dots2(analyzer.searchClickX,i));
          }                  
          if (layerDots.size()>2){                                              //смотрит между 2-3 4-5 й точкой и т.д. Закрашивать её или нет
              int posYkaz=0;
              while(posYkaz<=layerDots.size()-1){                                //сортировка точек !
                 if (posYkaz>0){
                     if(layerDots.get(posYkaz).getDotsX()<layerDots.get(posYkaz-1).getDotsX()){
                         double middleDot=layerDots.get(posYkaz).getDotsX();
                         layerDots.get(posYkaz).setDotsX(layerDots.get(posYkaz-1).getDotsX());
                         layerDots.get(posYkaz-1).setDotsX(middleDot);
                         posYkaz+=-1;
                     }else{
                         posYkaz+=1;
                     }
                 }else{
                     posYkaz+=1;
                 }
              }
              int nomberChooseDots=1;                                      //если была замена, тогда влияет на какое значение должно быть приплюсовано (1 или 2). 2 - текущая волна значений, 1, чтобы пришло в норму
              while (nomberChooseDots<layerDots.size()-1){
                 Model.DotsInShapeAnalyzer analyzer2=new Model.DotsInShapeAnalyzer(Frame.ShapeAL);
                 ArrayList<Dots2> dots = new ArrayList<Dots2>();
                 ArrayList<Dots2> dots2 = new ArrayList<Dots2>();
                 int ClickDotX=(int)Math.floor((layerDots.get(nomberChooseDots).getDotsX()+layerDots.get(nomberChooseDots+1).getDotsX())/2);          //средняя точка пространства
                 int ClickDotY=i;
                 boolean sec=false;                                               //отвечает за то, что объект пересечения найден
                 boolean Sec2=false;                                              //отвечает за то, что нужно прекратить действие
                 boolean SecDots=false;
                     int numberOfIntersections=0;
                     for (int i2=0;i2<lines.size()-1;i2++){ //по каждой прямой                         
                         if (analyzer2.isClickInSide(ClickDotX, ClickDotY,lines.get(i2).getDotsX(), lines.get(i2).getDotsY(), lines.get(i2+1).getDotsX(), lines.get(i2+1).getDotsY(),false)){
                            numberOfIntersections++;
                            dots.add(new Dots2(analyzer2.searchClickX,ClickDotY));     //добавление точек в массив точек
                         }
                     }
                     if (analyzer2.isClickInSide(ClickDotX, ClickDotY, lines.get(0).getDotsX(), lines.get(0).getDotsY(), lines.get(lines.size()-1).getDotsX(), lines.get(lines.size()-1).getDotsY(),false)){
                            numberOfIntersections++;
                            dots.add(new Dots2(analyzer2.searchClickX,ClickDotY));     //добавление точек в массив точек пересечения
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
                  if (dots.size()%2==1 & !(typeShape.equals("Ellipse2"))| dots2.size()%2==1 & typeShape.equals("Ellipse2") & dots.size()%2==0| typeShape.equals("Ellipse2") & dots2.size()%2==0 & dots.size()%2==1){//проверка
                      SecDots=true;
                  }
               sec=true;
               dots.clear();
               dots2.clear();
               if (Sec2){                                                       //возвращаем обратно
                  ClickDotY=ClickDotY+1; 
               }
               if (SecDots){                                                    //возможны ошибки при нечетном layersDots
                   if (nomberChooseDots != layerDots.size()-2){
                      layerDots.remove(nomberChooseDots+1);
                      layerDots.remove(nomberChooseDots);
                   }else{
                      layerDots.remove(nomberChooseDots);  
                   }
               }else{
                  nomberChooseDots+=2;
               }
             }
          }
          for (int posLayerDots=0; posLayerDots<layerDots.size();posLayerDots++){                  
             allDots.add(new Dots2(layerDots.get(posLayerDots).getDotsX(),layerDots.get(posLayerDots).getDotsY()));
          }
          layerDots.clear();
       }
       for (int i=0;i<(allDots.size()/2);i++){
           if (typeFill==0){
              g.drawLine((int)Math.floor(allDots.get(i*2).getDotsX()), (int)Math.floor(allDots.get(i*2).getDotsY()), (int)Math.floor(allDots.get(i*2+1).getDotsX()), (int)Math.floor(allDots.get(i*2+1).getDotsY()));           //Просто происходит перескок
           }
           if (typeFill==1){
               if (((int)Math.floor(allDots.get(i*2).getDotsY())-thisMinY)%3==0){
                  g.drawLine((int)Math.floor(allDots.get(i*2).getDotsX()), (int)Math.floor(allDots.get(i*2).getDotsY()), (int)Math.floor(allDots.get(i*2+1).getDotsX()), (int)Math.floor(allDots.get(i*2+1).getDotsY()));
               }
           }
           if (typeFill==2){
              int Y= (int)Math.floor(allDots.get(i*2).getDotsY());
              int X1=(int)Math.floor(allDots.get(i*2).getDotsX());
              int X2=(int)Math.floor(allDots.get(i*2+1).getDotsX());
              if (X2<X1){
                  int middleDot=X1;
                  X1=X2;
                  X2=middleDot;
              }
              int pos=X1;
              while (pos<=X2){
                  if ((pos-thisMinX)%3==0){
                     g.drawLine(pos, Y, pos, Y); 
                  }
                  pos++;
              }
           }
           if (typeFill==4){
              int Y= (int)Math.floor(allDots.get(i*2).getDotsY());
              int X1=(int)Math.floor(allDots.get(i*2).getDotsX());
              int X2=(int)Math.floor(allDots.get(i*2+1).getDotsX());
              if (X2<X1){
                  int middleDot=X1;
                  X1=X2;
                  X2=middleDot;
              }
              int pos=X1;
              while (pos<=X2){
                  if ((pos-thisMinX+(Y-thisMinY))%3==0){
                     g.drawLine(pos, Y, pos, Y); 
                  }
                  pos++;
              }
           }
           if (typeFill==3){
              int Y= (int)Math.floor(allDots.get(i*2).getDotsY());
              int X1=(int)Math.floor(allDots.get(i*2).getDotsX());
              int X2=(int)Math.floor(allDots.get(i*2+1).getDotsX());
              if (X2<X1){
                  int middleDot=X1;
                  X1=X2;
                  X2=middleDot;
              }
              int pos=X1;
              while (pos<=X2){
                  if ((pos-thisMinX-(Y-thisMinY))%3==0){
                     g.drawLine(pos, Y, pos, Y); 
                  }
                  pos++;
              }
           }
       }
       allDots.clear();
    }
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
   
}
