package it.polimi.ingsw.model;

public class Cloud {
    private Student[] cloudContent;

    public Cloud(int size) {
        this.cloudContent = new Student[size];
    }

    public boolean isEmpty() {
        return cloudContent[0] == null;
    }

    public Student[] get(){
        Student[] tmp = cloudContent;
        cloudContent = new Student[tmp.length];
        return tmp;
    }

    public void fill(Student[] cloudContent){
        System.arraycopy(cloudContent, 0, this.cloudContent, 0, cloudContent.length);
    }
}
