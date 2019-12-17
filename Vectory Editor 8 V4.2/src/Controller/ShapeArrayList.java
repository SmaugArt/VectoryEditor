/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Shape;
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class ShapeArrayList extends ArrayList<Shape> {
    private ArrayList<Shape> Queue = new ArrayList<Shape>();    
    public void addShape(Shape Object){  //добавляет обьект
       Queue.add(Object);
    }
    public void remove(Shape Object){  //удаляет схожий объект
        Queue.remove(Object);
    }
    public void removeOfIndex(int i){ //удаление по индексу
       Queue.remove(i);
    }    
}
