package it.polimi.ingsw.model;

public class ThiefEffect implements Effect {

    public void applyEffect(Board board) {
        Color selectedColor = selectColor();
        //TODO: need method to convert the Color to an Integer through the map in Board
        //alternative: give to COLOR ENUM 2 attributes-->  String name, int indexInMap
        int indexSelectedColor = 0;   //TODO: value 0 is WRONG, need the aforementioned conversion method

        for(SchoolBoard s : board.getPlayerBoards()) {
            for(int i=0; i<3; i++) {
                if(s.getDiningRoom()[indexSelectedColor] > 0) {
                    s.removeFromDiningRoom(indexSelectedColor);
                    board.getBag().put(new Student(selectedColor));   /* is it really a new instance of Student? where should I take
                                                                         the student from? removeFromDiningRoom modify an Integer Value,
                                                                         but where did the instance of Student go the first time when
                                                                         I called addToDiningRoom() ? */
                } else {
                    break;
                }
            }
        }
    }

    //TODO: controller must intervene to set this variable
    public Color selectColor() {
        Color chosenColor = null; //controller should ask the user for the input
        return chosenColor;
    }

}