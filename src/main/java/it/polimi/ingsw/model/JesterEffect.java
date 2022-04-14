package it.polimi.ingsw.model;

public class JesterEffect implements Effect {
    Student[] students = new Student[6];

    public void applyEffect(Board board, int archiIndex, int numOfStudents, Color...studColors) {
        //the user will choose how many students they want to move and this number will be saved in the variable numOfStudents
        //the first numOfStudents Colors in studColors[] will be the colors of the students chosen FROM THIS CARD, while the remaining
        //will be the colors of the students chosen FROM THE ENTRANCE

        int[] studentIndexesOnCard = new int[numOfStudents];
        Student[] studentsFromEntrance = new Student[numOfStudents];
        SchoolBoard currentPlayerSB = board.getCurrentPlayerSchoolBoard();

        //Finding the indexes of numOfStudents students with the specified color, in order to move them from the card to the entrance
        for (int i = 0; i < numOfStudents; i++) {
            for (int j = 0; j < students.length; j++) {
                for (int k = 0; k < numOfStudents; k++){
                    if (students[j].getColor() == studColors[k]) {
                        studentIndexesOnCard[i] = j;
                        break;
                    }
                }
            }
        }

        //Removing the students with the specified color from the entrance whilst adding the students chosen from the card
        for (int i = 0; i < numOfStudents; i++) {
            studentsFromEntrance[i] = currentPlayerSB.removeFromEntrance(studColors[i+numOfStudents]);
            currentPlayerSB.addToEntrance(students[studentIndexesOnCard[i]]);
        }

        //Substituting the students chosen from the card with the students chosen from the entrance
        for (int i = 0; i < numOfStudents; i++) {
            students[studentIndexesOnCard[i]] = studentsFromEntrance[i];
        }
    }

    public void setStudents(Student[] students){
        this.students = students;
    }

}