/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassOfObjects;

import Model.Shape;
import View.MainWindow;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import javax.swing.JButton;

/**
 *
 * @author demin
 */
public class SpecialButton extends JButton{
    private String type;
    public SpecialButton(String Type){
        this.type=Type;
        Dimension size = new Dimension(160,30);
        this.setSize(size);
        this.setPreferredSize(size);
        this.setMaximumSize(size);
        this.setMinimumSize(size);
        if (Type.equals("MirrorX")){
            this.addActionListener(new ActionListenerForMirrorX());
            this.setText("Отражение по оси X");
        }
        if (Type.equals("MirrorY")){
            this.addActionListener(new ActionListenerForMirrorY());
            this.setText("Отражение по оси Y");
        }
        if (Type.equals("Inversion")){
            this.addActionListener(new ActionListenerForInversion());
            this.setText("Инверсия объекта");
        }
    }
    
    private void MirrorX(Shape changeShape){
        if (!(changeShape.type.equals("Ellipse2"))){
           double XSimmetry=changeShape.MinY+(changeShape.MaxY-changeShape.MinY)/2; //точка симметрии
           for (int i=changeShape.MyArray.size()-1;i>=0;i--){
               double deltaY=-2*(changeShape.MyArray.get(i).getDotsY()-XSimmetry);
               changeShape.MyArray.get(i).setDotsY(changeShape.MyArray.get(i).getDotsY()+deltaY);
           }
           double deltaY=-2*(changeShape.PivotY-XSimmetry);
           changeShape.PivotY=changeShape.PivotY+deltaY;
        }
        changeShape.Phi=-changeShape.Phi;
    }                                                                            //процедурки для смещения точек относительно одной из координат    //внимательно с эллипсом (там угол)
    private void MirrorY(Shape changeShape){
       if (!(changeShape.type.equals("Ellipse2"))){
           double YSimmetry=changeShape.MinX+(changeShape.MaxX-changeShape.MinX)/2; //точка симметрии
           for (int i=changeShape.MyArray.size()-1;i>=0;i--){
               double deltaX=-2*(changeShape.MyArray.get(i).getDotsX()-YSimmetry);
               changeShape.MyArray.get(i).setDotsX(changeShape.MyArray.get(i).getDotsX()+deltaX);
           }
           double deltaX=-2*(changeShape.PivotX-YSimmetry);
           changeShape.PivotX=changeShape.PivotX+deltaX;
        }
        changeShape.Phi=-changeShape.Phi;
    }
    
    private class ActionListenerForMirrorX implements ActionListener {
        @Override public void actionPerformed(ActionEvent e) { 
            MainWindow Frame = MainWindow.getThisFrame();
            if (Frame.positionSelectedShape>-1 & Frame.positionSelectedShape<=Frame.ShapeAL.size()-1){
                MirrorX(Frame.ShapeAL.get(Frame.positionSelectedShape));
                Frame.typeOfOperation=0;
                Frame.drawPanel.drawBuffer(Frame.ShapeAL, Frame.ActiveShape);
                Frame.drawPanel.repaint();
            }
        } 
    }
    
    private class ActionListenerForMirrorY implements ActionListener {
        @Override public void actionPerformed(ActionEvent e) { 
           MainWindow Frame = MainWindow.getThisFrame();
            if (Frame.positionSelectedShape>-1 & Frame.positionSelectedShape<=Frame.ShapeAL.size()-1){
                MirrorY(Frame.ShapeAL.get(Frame.positionSelectedShape));
                Frame.typeOfOperation=0;
                Frame.drawPanel.drawBuffer(Frame.ShapeAL, Frame.ActiveShape);
                Frame.drawPanel.repaint();
            }
        } 
    }
    
    private class ActionListenerForInversion implements ActionListener {
        @Override public void actionPerformed(ActionEvent e) { 
            MainWindow Frame = MainWindow.getThisFrame();
            if (Frame.positionSelectedShape>-1 & Frame.positionSelectedShape<=Frame.ShapeAL.size()-1){
                MirrorX(Frame.ShapeAL.get(Frame.positionSelectedShape));
                MirrorY(Frame.ShapeAL.get(Frame.positionSelectedShape));
                Frame.typeOfOperation=0;
                Frame.drawPanel.drawBuffer(Frame.ShapeAL, Frame.ActiveShape);
                Frame.drawPanel.repaint();
            }
        } 
    }
}
