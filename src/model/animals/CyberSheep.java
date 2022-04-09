package model.animals;

import model.plants.SosnowskiHogweed;
import model.Action;
import model.ActionEnum;
import model.Organism;
import model.World;
import model.Position;
import java.util.ArrayList;
import java.lang.Math;

public class CyberSheep extends Animal {

    private boolean isThereHogweed;

    public CyberSheep(Position position, World world){
        super(position, world);
        isThereHogweed = true;
    }

    public CyberSheep(Position position){
        super(position, null);
        isThereHogweed = true;
    }

    public CyberSheep(CyberSheep cyberSheep){
        super(cyberSheep);
        this.isThereHogweed = cyberSheep.isThereHogweed;
    }

    @Override
    public void initParam() {
        this.setPower(11);
        this.setInitiative(4);
    }

    @Override
    public Animal clone() {
        return new CyberSheep(this);
    }

    @Override
    public ArrayList<Action> move() {
        ArrayList<Action> result = new ArrayList<Action>();
        if(isThereHogweed){
            ArrayList<Position> hogweeds = listOfHogweed();
            if(hogweeds.isEmpty()){
                isThereHogweed = false;
                result.addAll(super.move());
            }
            else {
                Position closestHogweed = findNearestPosition(position, hogweeds);
                ArrayList<Position> positionProposals = world.getListOfNeighboringPositions(position, 1);
                Position destination = findNearestPosition(closestHogweed, positionProposals);
                result.add(new Action(ActionEnum.A_MOVE, new Position(destination.getX(), destination.getY()), 0, this));
            }
        }
        else{
            result.addAll(super.move());
        }
        return result;
    }

    private Position findNearestPosition(Position start, ArrayList<Position> potentialDestinations){
        double minDistance, currDistance;
        minDistance = Double.MAX_VALUE;
        Position nearestPosition = new Position(-1, -1);
        for (Position positionToCheck : potentialDestinations) {
            currDistance = countDistance(positionToCheck, start);
            if(minDistance>currDistance){
                minDistance = currDistance;
                nearestPosition = positionToCheck;
            }
        }
        return nearestPosition;
    }

    private double countDistance(Position p1, Position p2) {
        return Math.sqrt(Math.pow(p1.getX()- p2.getX(),2) + Math.pow(p1.getY()-p2.getY(),2));
    }

    private ArrayList<Position> listOfHogweed() {
        Organism organism;
        ArrayList<Position> result = new ArrayList<Position>();
        for (int x = 0; x < world.getBoardX(); x++) {
            for(int y = 0; y <world.getBoardY(); y++){
                organism = world.getOrganismFromPosition(new Position(x, y));
                if(organism != null){
                    if(organism instanceof SosnowskiHogweed){
                        result.add(new Position(x, y));
                    }
                }
            }
        }
        return result;
    }
}
