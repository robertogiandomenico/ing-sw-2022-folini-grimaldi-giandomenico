package it.polimi.ingsw.model;

/**
 * This class represents the clouds on the board.
 * The number of instances of this class varies between two and three depending on
 * the number of players.
 */
public class Cloud {
    private Student[] cloudContent;

    /**
     * Class constructor specifying cloud size.
     *
     * @param size           the number of Students that can be put on this Cloud.
     */
    public Cloud(int size) {
        this.cloudContent = new Student[size];
    }

    /**
     * Returns a boolean that states whether there are students on this cloud or not.
     *
     * @return               a boolean whose value is:
     *                       <p>
     *                       -{@code true} if (at least) the first position of the Student Array on the Cloud is occupied;
     *                       </p> <p>
     *                       -{@code false} if the first position of the Student Array on the Cloud is {@code null}.
     *                       </p>
     */
    public boolean isEmpty() {
        return cloudContent[0] == null;
    }

    /**
     * Draws the students present on this cloud and leaves it empty.
     *
     * @return               the Student Array of this Cloud.
     */
    public Student[] get(){
        Student[] tmp = cloudContent;
        cloudContent = new Student[tmp.length];
        return tmp;
    }

    /**
     * Fills this cloud with the given students.
     *
     * @param cloudContent   the Array of Students to be added to this Cloud.
     */
    public void fill(Student[] cloudContent){
        System.arraycopy(cloudContent, 0, this.cloudContent, 0, cloudContent.length);
    }

    /**
     * Returns the students present on this cloud.
     */
    public Student[] getCloudContent(){
        return cloudContent;
    }
}
