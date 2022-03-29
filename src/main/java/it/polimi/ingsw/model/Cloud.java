package it.polimi.ingsw.model;

public class Cloud {
    private final Student[] cloudContent;

    public Cloud(int size) {
        this.cloudContent = new Student[size];
    }

    public boolean isEmpty() {
        return cloudContent.length == 0;
    }

    public Student[] get() {
        return cloudContent;
    }

    public void fill(Student[] cloudContent){
        System.arraycopy(cloudContent, 0, this.cloudContent, 0, cloudContent.length);
    }
}
