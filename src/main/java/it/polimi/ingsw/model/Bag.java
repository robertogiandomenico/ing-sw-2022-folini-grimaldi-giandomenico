package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bag {
    private final List<Student> bagContent;

    public Bag(){
        this.bagContent = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            bagContent.add(new Student(Color.GREEN));
            bagContent.add(new Student(Color.RED));
            bagContent.add(new Student(Color.YELLOW));
            bagContent.add(new Student(Color.PINK));
            bagContent.add(new Student(Color.BLUE));
        }
        Collections.shuffle(this.bagContent);
    }

    public Student draw(){
        return getSize()==0 ? null : bagContent.remove(getSize()-1);
    }

    public void put(Student student){
        bagContent.add(student);
        Collections.shuffle(this.bagContent);
    }

    public int getSize(){
        return bagContent.size();
    }
}
