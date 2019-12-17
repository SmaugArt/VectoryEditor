/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.Dots2;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author user
 */
public class DrawPanel extends JPanel implements MouseListener,MouseMotionListener,MouseWheelListener{
    public BufferedImage img;
    public Graphics2D grap;
    public double ScaleHolst; //масштаб холста
    public double ShiftHolstX; //смещение по X холста
    public double ShiftHolstY;
    protected double SHX; //приращение перемещения холста
    protected double SHY;
    protected double X; //для нахождения смещения
    protected double Y;
    public PropertyPanel PanelMain;
    MainWindow mains;
    int width=856;
    int height=550;
    public DrawPanel(MainWindow ABC){
        img = new BufferedImage(856,550, BufferedImage.TYPE_INT_ARGB); //один буфер для всех слоев
        grap = img.createGraphics();
        grap.setColor(Color.WHITE);
        grap.fillRect(0,0, 856,550);
        mains=ABC;  //Чтобы передать переменной типа маин виндов сам маинвиндов !!!!!!!!!!!!!!!
        this.addMouseListener(this);  //добавляем все элементы слушателей реализованные ниже при создании DrawPanel
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);
        ScaleHolst=0;
        ShiftHolstX=0;
        ShiftHolstY=0;
        SHX=0;
        SHY=0;
        //this.setLayout(mgr);
    }
    public void drawBuffer(Controller.ShapeArrayList ShapeAL, Model.Shape ActiveShape){
       grap.setColor(Color.GRAY);
       grap.fillRect(0,0, 5000,5000);
       grap.setColor(Color.WHITE);
       //grap.fillRect(0,0, 856,422);
       grap.fillRect(0+(int)Math.floor(ShiftHolstX+SHX),0+(int)Math.floor(ShiftHolstY+SHY), (int)Math.floor(width*Math.exp(ScaleHolst)),(int)Math.floor(height*Math.exp(ScaleHolst))); //смещаться будет сам холст
       for (Model.Shape que1: ShapeAL){  //стандартный метод формата Shape для рисования из списка ShapeAL
               if (que1.Visible){ 
                  que1.paint(grap,ShiftHolstX+SHX,ShiftHolstY+SHY,ScaleHolst);
               }
            }
       if (mains.typeOfOperation==1){ 
          ActiveShape.paint(grap,ShiftHolstX+SHX,ShiftHolstY+SHY,ScaleHolst);
       }
       if (mains.typeOfOperation==5){
           if (ShapeAL.get(mains.positionSelectedShape).Visible){
              ShapeAL.get(mains.positionSelectedShape).PaintBorder(grap, ShiftHolstX, ShiftHolstY, ScaleHolst);
           }
       }
    }
    @Override
    public void paint(Graphics g){
       g.drawImage(img, 0, 0, this);
    }

    @Override public void mouseClicked(MouseEvent e) {}

    @Override public void mousePressed(MouseEvent e) {
        if (mains.typeOfOperation==2){
            if (mains.ShapeAL.size()>0){
               mains.ShapeAL.get(mains.positionSelectedShape).beginRotation(e.getX(),e.getY());
            }
        }  
        if (mains.typeOfOperation==3){
            Model.DotsInShapeAnalyzer analyzer=new Model.DotsInShapeAnalyzer(mains.ShapeAL);
            int nomberShape = analyzer.feelOutShape(e.getX(),e.getY(),true);
            if (nomberShape>=0){                                                //выбор фигуры
                mains.positionSelectedShape=nomberShape;
                mains.PanelFromPP.PositionSelectShape=nomberShape+1;
                mains.PanelFromPP.FalseVisibleObjects();
                mains.PanelFromPP.UpgradeComboBox();
            }
        }  
        if (mains.typeOfOperation==1){
               mains.ActiveShape.firstclick(worldCoordinatX(e.getX()),worldCoordinatY(e.getY()), ScaleHolst);      
        }
        if (mains.typeOfOperation==4){
           beginMoveHolst(e.getX(),e.getY());      
        }
        if (mains.typeOfOperation==5){
            if (mains.ShapeAL.size()>0){
               double YMaxViewCoordinat=mains.ShapeAL.get(mains.positionSelectedShape).MaxY*Math.exp(ScaleHolst)+ShiftHolstY+15;
               double YMinViewCoordinat=mains.ShapeAL.get(mains.positionSelectedShape).MinY*Math.exp(ScaleHolst)+ShiftHolstY-15;
               double XMaxViewCoordinat=mains.ShapeAL.get(mains.positionSelectedShape).MaxX*Math.exp(ScaleHolst)+ShiftHolstX+15;
               double XMinViewCoordinat=mains.ShapeAL.get(mains.positionSelectedShape).MinX*Math.exp(ScaleHolst)+ShiftHolstX-15;
               double deltaX=(XMaxViewCoordinat-XMinViewCoordinat)*0.3;
               double deltaY=(YMaxViewCoordinat-YMinViewCoordinat)*0.3;
               if (e.getY()<(int)Math.floor(YMaxViewCoordinat-deltaY) & e.getY()>(int)Math.floor(YMinViewCoordinat+deltaY) & e.getX()<(int)Math.floor(XMaxViewCoordinat-deltaX) & e.getX()>(int)Math.floor(XMinViewCoordinat+deltaX)){ //5  
                  mains.ShapeAL.get(mains.positionSelectedShape).setTypeDeformation(5);
                  mains.ShapeAL.get(mains.positionSelectedShape).beginMove(e.getX(),e.getY());
               }
               if (e.getY()<(int)Math.floor(YMinViewCoordinat+deltaY) & e.getY()>(int)Math.floor(YMinViewCoordinat) & e.getX()<(int)Math.floor(XMaxViewCoordinat-deltaX) & e.getX()>(int)Math.floor(XMinViewCoordinat+deltaX)){//2
                  mains.ShapeAL.get(mains.positionSelectedShape).setTypeDeformation(2);
                  mains.ShapeAL.get(mains.positionSelectedShape).startDeformation(e.getX(),e.getY());    
               }
               if (e.getY()>(int)Math.floor(YMaxViewCoordinat-deltaY) & e.getY()<(int)Math.floor(YMaxViewCoordinat) & e.getX()<(int)Math.floor(XMaxViewCoordinat-deltaX) & e.getX()>(int)Math.floor(XMinViewCoordinat+deltaX)){//8
                  mains.ShapeAL.get(mains.positionSelectedShape).setTypeDeformation(8);
                  mains.ShapeAL.get(mains.positionSelectedShape).startDeformation(e.getX(),e.getY());    
               }
               if (e.getY()<(int)Math.floor(YMaxViewCoordinat-deltaY) & e.getY()>(int)Math.floor(YMinViewCoordinat+deltaY) & e.getX()<(int)Math.floor(XMinViewCoordinat+deltaX) & e.getX()>(int)Math.floor(XMinViewCoordinat)){//4
                  mains.ShapeAL.get(mains.positionSelectedShape).setTypeDeformation(4);
                  mains.ShapeAL.get(mains.positionSelectedShape).startDeformation(e.getX(),e.getY());    
               }
               if (e.getY()<(int)Math.floor(YMaxViewCoordinat-deltaY) & e.getY()>(int)Math.floor(YMinViewCoordinat+deltaY) & e.getX()<(int)Math.floor(XMaxViewCoordinat) & e.getX()>(int)Math.floor(XMaxViewCoordinat-deltaX)){//6
                  mains.ShapeAL.get(mains.positionSelectedShape).setTypeDeformation(6);
                  mains.ShapeAL.get(mains.positionSelectedShape).startDeformation(e.getX(),e.getY());    
               }
               if (e.getY()<(int)Math.floor(YMinViewCoordinat+deltaY) & e.getY()>(int)Math.floor(YMinViewCoordinat) & e.getX()<(int)Math.floor(XMinViewCoordinat+deltaX) & e.getX()>(int)Math.floor(XMinViewCoordinat)){//1
                  mains.ShapeAL.get(mains.positionSelectedShape).setTypeDeformation(1);
                  mains.ShapeAL.get(mains.positionSelectedShape).startDeformation(e.getX(),e.getY());    
               }
               if (e.getY()<(int)Math.floor(YMinViewCoordinat+deltaY) & e.getY()>(int)Math.floor(YMinViewCoordinat) & e.getX()<(int)Math.floor(XMaxViewCoordinat) & e.getX()>(int)Math.floor(XMaxViewCoordinat-deltaX)){//3
                  mains.ShapeAL.get(mains.positionSelectedShape).setTypeDeformation(3);
                  mains.ShapeAL.get(mains.positionSelectedShape).startDeformation(e.getX(),e.getY());    
               }
               if (e.getY()<(int)Math.floor(YMaxViewCoordinat) & e.getY()>(int)Math.floor(YMaxViewCoordinat-deltaY) & e.getX()<(int)Math.floor(XMinViewCoordinat+deltaX) & e.getX()>(int)Math.floor(XMinViewCoordinat)){//7
                  mains.ShapeAL.get(mains.positionSelectedShape).setTypeDeformation(7);
                  mains.ShapeAL.get(mains.positionSelectedShape).startDeformation(e.getX(),e.getY());    
               }
               if (e.getY()<(int)Math.floor(YMaxViewCoordinat) & e.getY()>(int)Math.floor(YMaxViewCoordinat-deltaY) & e.getX()<(int)Math.floor(XMaxViewCoordinat) & e.getX()>(int)Math.floor(XMaxViewCoordinat-deltaX)){//9
                  mains.ShapeAL.get(mains.positionSelectedShape).setTypeDeformation(9);
                  mains.ShapeAL.get(mains.positionSelectedShape).startDeformation(e.getX(),e.getY());    
               }
            }
        }
    }

    @Override public void mouseReleased(MouseEvent e) {
        if (mains.typeOfOperation==1){
               if ("PolyLine".equals(mains.ActiveShape.type) | "Bezier2".equals(mains.ActiveShape.type) | "PolyGone".equals(mains.ActiveShape.type)) { 
                   if (e.getClickCount() == 2) {
                      mains.ActiveShape.backstep();
                      mouseDragged(e);
                      mains.ShapeAL.add(mains.ActiveShape);//ActiveShape); 
                      mains.ActiveShape = mains.SFactory.reborn((Model.Shape)mains.ActiveShape);
                      mains.PanelFromPP.setShapeArrayList(mains.ShapeAL);
                      mains.PanelFromPP.PositionSelectShape=mains.ShapeAL.size();
                      mains.PanelFromPP.FalseVisibleObjects();
                      mains.PanelFromPP.UpgradeComboBox();
                      mains.positionSelectedShape=mains.ShapeAL.size()-1;
                   }else{
                      mains.ActiveShape.addPoint();
                      mouseDragged(e);
                   }
               }else{
                  mains.ActiveShape.addBorder();
                  mains.ShapeAL.add(mains.ActiveShape);//ActiveShape); 
                  mains.ActiveShape = mains.SFactory.reborn((Model.Shape)mains.ActiveShape);
                  mains.PanelFromPP.setShapeArrayList(mains.ShapeAL);
                  mains.PanelFromPP.PositionSelectShape=mains.ShapeAL.size();
                  mains.PanelFromPP.FalseVisibleObjects();
                  mains.PanelFromPP.UpgradeComboBox();
                  mains.positionSelectedShape=mains.ShapeAL.size()-1;
               }
        }
        if (mains.typeOfOperation==2){
            if (mains.ShapeAL.size()>0){
               mains.ShapeAL.get(mains.positionSelectedShape).addPhi();
            }
        }
        if (mains.typeOfOperation==4){
               addMoveHolst();
        }
        if (mains.typeOfOperation==5){
            if (mains.ShapeAL.size()>0){
               if (mains.ShapeAL.get(mains.positionSelectedShape).typeDeformation==5){
                  mains.ShapeAL.get(mains.positionSelectedShape).addMove(ShiftHolstX, ShiftHolstY, ScaleHolst);
               }
               if (mains.ShapeAL.get(mains.positionSelectedShape).typeDeformation==2){
                   mains.ShapeAL.get(mains.positionSelectedShape).addYUpEdgeDeformation(ShiftHolstX, ShiftHolstY, ScaleHolst);
               }
               if (mains.ShapeAL.get(mains.positionSelectedShape).typeDeformation==8){
                   mains.ShapeAL.get(mains.positionSelectedShape).addYDownEdgeDeformation(ShiftHolstX, ShiftHolstY, ScaleHolst);
               }
               if (mains.ShapeAL.get(mains.positionSelectedShape).typeDeformation==4){
                   mains.ShapeAL.get(mains.positionSelectedShape).addXLeftEdgeDeformation(ShiftHolstX, ShiftHolstY, ScaleHolst);
               }
               if (mains.ShapeAL.get(mains.positionSelectedShape).typeDeformation==6){
                   mains.ShapeAL.get(mains.positionSelectedShape).addXRightEdgeDeformation(ShiftHolstX, ShiftHolstY, ScaleHolst);
               }
               if (mains.ShapeAL.get(mains.positionSelectedShape).typeDeformation==1){
                   mains.ShapeAL.get(mains.positionSelectedShape).addXYLeftUpEdgeDeformation(ShiftHolstX, ShiftHolstY, ScaleHolst);
               }
               if (mains.ShapeAL.get(mains.positionSelectedShape).typeDeformation==3){
                   mains.ShapeAL.get(mains.positionSelectedShape).addXYRightUpEdgeDeformation(ShiftHolstX, ShiftHolstY, ScaleHolst);
               }
               if (mains.ShapeAL.get(mains.positionSelectedShape).typeDeformation==7){
                   mains.ShapeAL.get(mains.positionSelectedShape).addXYRightDownEdgeDeformation(ShiftHolstX, ShiftHolstY, ScaleHolst);
               }
               if (mains.ShapeAL.get(mains.positionSelectedShape).typeDeformation==9){
                   mains.ShapeAL.get(mains.positionSelectedShape).addXYLeftDownEdgeDeformation(ShiftHolstX, ShiftHolstY, ScaleHolst);
               }
               mains.ShapeAL.get(mains.positionSelectedShape).setTypeDeformation(0);
              // mains.ShapeAL.get(mains.positionSelectedShape).setTypeOfOperation(0);//для эллипса
            }
        }
        if (mains.ShapeAL.size() !=0){
           mains.PanelFromPP.UpgradeComboBox();
        }
    }

    @Override public void mouseEntered(MouseEvent e) {
//       if (mains.typeOfOperation==5){
//           if (mains.ShapeAL.size()>0){
//              drawBuffer(mains.ShapeAL, mains.ActiveShape);
//              repaint();
//           }
//       }
    }

    @Override public void mouseExited(MouseEvent e) {}

    @Override public void mouseDragged(MouseEvent e) {
        if (mains.typeOfOperation==1){
                mains.ActiveShape.lastclick(worldCoordinatX(e.getX()),worldCoordinatY(e.getY()));  
                this.drawBuffer(mains.ShapeAL,mains.ActiveShape);
                repaint();
        } 
        if (mains.typeOfOperation==2){
            if (mains.ShapeAL.size()>0){
               mains.ShapeAL.get(mains.positionSelectedShape).endRotation(e.getX(),e.getY(),ShiftHolstX, ShiftHolstY, ScaleHolst);
               drawBuffer(mains.ShapeAL, mains.ActiveShape);
               repaint();
            }
        }
        if (mains.typeOfOperation==4){
               endMoveHolst(e.getX(),e.getY()); 
               drawBuffer(mains.ShapeAL, mains.ActiveShape);
               repaint();     
        }
        if (mains.typeOfOperation==5){
            if (mains.ShapeAL.size()>0){
               if (mains.ShapeAL.get(mains.positionSelectedShape).typeDeformation==5){
                  mains.ShapeAL.get(mains.positionSelectedShape).endMove(e.getX(),e.getY());
               }
              if (mains.ShapeAL.get(mains.positionSelectedShape).typeDeformation==2){
                  mains.ShapeAL.get(mains.positionSelectedShape).endYUpEdgeDeformation(e.getX(), e.getY(),ShiftHolstX,ShiftHolstY,ScaleHolst);
               }
              if (mains.ShapeAL.get(mains.positionSelectedShape).typeDeformation==8){
                  mains.ShapeAL.get(mains.positionSelectedShape).endYDownEdgeDeformation(e.getX(), e.getY(),ShiftHolstX,ShiftHolstY,ScaleHolst);
               }
              if (mains.ShapeAL.get(mains.positionSelectedShape).typeDeformation==4){
                  mains.ShapeAL.get(mains.positionSelectedShape).endXLeftEdgeDeformation(e.getX(), e.getY(),ShiftHolstX,ShiftHolstY,ScaleHolst);
               }
              if (mains.ShapeAL.get(mains.positionSelectedShape).typeDeformation==6){
                  mains.ShapeAL.get(mains.positionSelectedShape).endXRightEdgeDeformation(e.getX(), e.getY(),ShiftHolstX,ShiftHolstY,ScaleHolst);
               }
              if (mains.ShapeAL.get(mains.positionSelectedShape).typeDeformation==1){
                  mains.ShapeAL.get(mains.positionSelectedShape).endXYLeftUpEdgeDeformation(e.getX(), e.getY(),ShiftHolstX,ShiftHolstY,ScaleHolst);
               }
              if (mains.ShapeAL.get(mains.positionSelectedShape).typeDeformation==3){
                  mains.ShapeAL.get(mains.positionSelectedShape).endXYRightUpEdgeDeformation(e.getX(), e.getY(),ShiftHolstX,ShiftHolstY,ScaleHolst);
               }
              if (mains.ShapeAL.get(mains.positionSelectedShape).typeDeformation==7){
                  mains.ShapeAL.get(mains.positionSelectedShape).endXYRightDownEdgeDeformation(e.getX(), e.getY(),ShiftHolstX,ShiftHolstY,ScaleHolst);
               }
              if (mains.ShapeAL.get(mains.positionSelectedShape).typeDeformation==9){
                  mains.ShapeAL.get(mains.positionSelectedShape).endXYLeftDownEdgeDeformation(e.getX(), e.getY(),ShiftHolstX,ShiftHolstY,ScaleHolst);
               }
              drawBuffer(mains.ShapeAL, mains.ActiveShape);
              repaint();
            }
        }
    }

    @Override public void mouseMoved(MouseEvent e) {}
    
    public double worldCoordinatX(int coordinatX){
       double X=(coordinatX-this.ShiftHolstX)/Math.exp(this.ScaleHolst);    
        return X;
    }
    public double worldCoordinatY(int coordinatY){
        double Y=(coordinatY-this.ShiftHolstY)/Math.exp(this.ScaleHolst);
        return Y;
    }
    public void beginMoveHolst(int X,int Y){
        this.X=X;
        this.Y=Y;
    }
    public void endMoveHolst(int X,int Y){
        this.SHX=X-this.X;
        this.SHY=Y-this.Y;
    } 
    public void addMoveHolst(){
        ShiftHolstX+=SHX;
        ShiftHolstY+=SHY;
        SHX=0;
        SHY=0;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (mains.typeOfOperation==4){
            if (e.getUnitsToScroll()==-3){ //колесико вперед
                  ScaleHolst+=0.1;
        }
            if (e.getUnitsToScroll()==3){//колесико назад
                ScaleHolst-=0.1; 
            }
            drawBuffer(mains.ShapeAL, mains.ActiveShape);
            repaint();
        }
    }
}
