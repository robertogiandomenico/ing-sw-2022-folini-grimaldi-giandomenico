package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bag {
    private final List<Student> bagContent;

    public Bag(List<Student> bagContent){
        this.bagContent = new ArrayList<Student>(bagContent);
        Collections.shuffle(this.bagContent);
    }

    public Student draw(){
        return bagContent.isEmpty() ? null : bagContent.remove(bagContent.size()-1);
    }

    public void put(Student student){
        bagContent.add(student);
        Collections.shuffle(this.bagContent);
    }
}
