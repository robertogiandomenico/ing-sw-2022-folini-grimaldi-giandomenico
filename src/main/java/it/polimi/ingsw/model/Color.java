package it.polimi.ingsw.model;

public enum Color {
    GREEN("Green"), RED("Red"), YELLOW("Yellow"), PINK("Pink"), BLUE("Blue");

    private final String name;

    Color(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}