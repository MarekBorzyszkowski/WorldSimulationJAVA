package model;

public class Action {

    private final ActionEnum action;
    private final Position position;
    private final int value;
    private Organism organism;

    public Action(ActionEnum action, Position position, int value, Organism organism) {
        this.action = action;
        this.position = position;
        this.value = value;
        this.organism = organism;
    }

    public ActionEnum getAction() {
        return action;
    }

    public Position getPosition() {
        return position;
    }

    public int getValue() {
        return value;
    }

    public Organism getOrganism() {
        return organism;
    }

    @Override
    public String toString() {
        switch(action){
            case A_ADD:
                return organism.getClass().getSimpleName() + ": add at: " + position;
            case A_INCREASEPOWER:
                return organism.getClass().getSimpleName() + ": increase power: " + value;
            case A_MOVE:
                return organism.getClass().getSimpleName() + ": move from: " + organism.getLastPosition() + " to: " +position;
            case A_REMOVE:
                return organism.getClass().getSimpleName() + ": remove from: " + position;
            default:
                return "";
        }
    }
    
}
