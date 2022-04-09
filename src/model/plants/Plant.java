package model.plants;

import java.util.ArrayList;
import model.Action;
import model.ActionEnum;
import model.Organism;
import model.Position;
import model.World;

public abstract class Plant extends Organism {


    public Plant(Position position, World world) {
        super(position, world);
    }

    public Plant(Plant plant) {
        super(plant);
    }

    public abstract Plant clone();

    @Override
    public ArrayList<Action> move() {
        ArrayList<Action> result = new ArrayList<Action>();
        Position newPosition;
        Plant newPlant;
        newPosition = world.getFreeNeighboringPosition(this.position);
        if (world.positionOnBoard(newPosition)) {
            newPlant = this.clone();
            newPlant.initParam();
            newPlant.position = newPosition;
            result.add(new Action(ActionEnum.A_ADD, newPosition, 0, newPlant));
        }
        return result;
    }

    @Override
    public ArrayList<Action> collision(Organism CollisionOrganism) {
        ArrayList<Action> result = new ArrayList<>();
        return (result);
    }

    @Override
    public Position getLastPosition() {
        return this.position;
    }

    @Override
    public void setPosition(Position position, boolean back) {
        this.position = position;
    }

}
