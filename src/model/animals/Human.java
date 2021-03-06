package model.animals;

import java.util.ArrayList;
import model.Action;
import model.ActionEnum;
import model.Position;
import model.World;
import model.DirectionEnum;

public class Human extends Animal {

    private int powerToSubstract = 0;
    private int counterTurnOfSkill = 0;
    private boolean isASkill = false;
    private DirectionEnum direction;
    private boolean runSkill = false;

    public Human(Position position, World world) {
        super(position, world);
    }

    public Human(Position position) {
        super(position, null);
    }

    public Human(Human human) {
        super(human);
        this.counterTurnOfSkill = human.counterTurnOfSkill;
        this.isASkill = human.isASkill;
        this.direction = human.direction;
        this.runSkill = human.runSkill;
    }

    @Override
    public Human clone() {
        return new Human(this);
    }

    @Override
    public void initParam() {
        this.power = 5;
        this.initiative = 4;
        this.direction = DirectionEnum.STOP;
    }

    @Override
    public boolean isInteractive() {
        return true;
    }

    public int getCounterTurnOfSkill() {
        return counterTurnOfSkill;
    }

    public void setCounterTurnOfSkill(int counterTurnOfSkill) {
        this.counterTurnOfSkill = counterTurnOfSkill;
    }

    public boolean isIsASkill() {
        return isASkill;
    }

    public void setIsASkill(boolean isASkill) {
        this.isASkill = isASkill;
    }

    public DirectionEnum getDirection() {
        return direction;
    }

    public void setDirection(DirectionEnum direction) {
        this.direction = direction;
    }

    public boolean isRunSkill() {
        return runSkill;
    }

    public void setRunSkill(boolean runSkill) {
        this.runSkill = runSkill;
    }

    @Override
    public ArrayList<Action> move() {
        ArrayList<Action> result = new ArrayList<Action>();
        Position newPosition = null;
        int moveDistance = 1;

        if (this.counterTurnOfSkill == 0) {
            if (this.runSkill) {
                this.runSkill = false;
                this.isASkill = true;
                this.counterTurnOfSkill++;
                if(this.power-10<0){
                    this.powerToSubstract = 10-this.power + 1;
                    this.power = 11;
                }
            }
        } else if (this.counterTurnOfSkill < 5) {
            if (this.isASkill) {
                this.counterTurnOfSkill++;
            } else {
                this.counterTurnOfSkill--;
            }
        } else // == 5
        {
            this.isASkill = false;
            this.counterTurnOfSkill--;
        }

        if (this.isASkill) {
            if(this.powerToSubstract>0){
                this.power--;
                this.powerToSubstract--;
            }
        }

        ArrayList<Position> positionProposals = world.getListOfNeighboringPositions(position, 1);
        switch (this.direction) {
            case FOUR_DIR_UP:
            case SIX_DIR_UP: //gora
                newPosition = new Position(this.position.getX(), this.position.getY() - 1);
                break;
            case FOUR_DIR_RIGHT:
            case SIX_DIR_UP_RIGHT: //prawo gora // prawo
                if (world.getCompasRose() == 8) { // kwadrat
                    newPosition = new Position(this.position.getX() + 1, this.position.getY());
                }
                if (world.getCompasRose() == 6) { // hex
                    if (this.position.getX() % 2 == 0) //dolny w lancuchu hex
                    {
                        newPosition = new Position(this.position.getX() + 1, this.position.getY());
                    } else // gorny w lancuchu hex
                    {
                        newPosition = new Position(this.position.getX() + 1, this.position.getY() - 1);
                    }
                }
                break;
            case SIX_DIR_DOWN_RIGHT: //prawo dol // prawo
                if (world.getCompasRose() == 6) { // hex
                    if (this.position.getX() % 2 == 0) //dolny w lancuchu hex
                    {
                        newPosition = new Position(this.position.getX() + 1, this.position.getY() + 1);
                    } else // gorny w lancuchu hex
                    {
                        newPosition = new Position(this.position.getX() + 1, this.position.getY());
                    }
                }
                break;
            case FOUR_DIR_DOWN:
            case SIX_DIR_DOWN://dol
                newPosition = new Position(this.position.getX(), this.position.getY() + 1);
                break;
            case FOUR_DIR_LEFT:
            case SIX_DIR_DOWN_LEFT://lewo dol // lewo
                if (world.getCompasRose() == 8) { // kwadrat
                    newPosition = new Position(this.position.getX() - 1, this.position.getY());
                }
                if (world.getCompasRose() == 6) { // hex
                    if (this.position.getX() % 2 == 0) //dolny w lancuchu hex
                    {
                        newPosition = new Position(this.position.getX() - 1, this.position.getY() + 1);
                    } else // gorny w lancuchu hex
                    {
                        newPosition = new Position(this.position.getX() - 1, this.position.getY());
                    }
                }
                break;
            case SIX_DIR_UP_LEFT: //lewo gora // lewo
                if (world.getCompasRose() == 6) { // hex
                    if (this.position.getX() % 2 == 0) //dolny w lancuchu hex
                    {
                        newPosition = new Position(this.position.getX() - 1, this.position.getY());
                    } else // gorny w lancuchu hex
                    {
                        newPosition = new Position(this.position.getX() - 1, this.position.getY() - 1);
                    }
                }
                break;
            default:
                return result;
        }
        if (positionProposals.contains(newPosition)) {
            result.add(new Action(ActionEnum.A_MOVE, newPosition, 0, this));
        }
        return result;
    }

    @Override
    public String toString() {
        return super.toString() + 
                " skillTurnCnt: " + counterTurnOfSkill + 
                " skillIsOn: " + isASkill;
    }
    
}