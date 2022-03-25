package it.polimi.ingsw.model;

public class Cloud {
    private final Student[] cloudContent;

    public Cloud(Student[] cloudContent) {
        this.cloudContent = new Student[cloudContent.length];
        System.arraycopy(cloudContent, 0, this.cloudContent, 0, this.cloudContent.length);
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
