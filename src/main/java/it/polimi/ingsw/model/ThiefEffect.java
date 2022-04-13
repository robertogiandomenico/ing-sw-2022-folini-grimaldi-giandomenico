package it.polimi.ingsw.model;

public class ThiefEffect implements Effect {

    public void applyEffect(Board board, int archiIndex, int numOfStudents, Color...studColors) {
        int indexSelectedColor = board.mapToIndex(studColors[numOfStudents-1]);

        for(SchoolBoard s : board.getPlayerBoards()) {
            for(int i=0; i<3; i++) {
                if(s.getDiningRoom()[indexSelectedColor] > 0) {
                    s.removeFromDiningRoom(indexSelectedColor);
                    board.getBag().put(new Student(studColors[numOfStudents-1]));
                } else {
                    break;
                }
            }
        }
    }

}