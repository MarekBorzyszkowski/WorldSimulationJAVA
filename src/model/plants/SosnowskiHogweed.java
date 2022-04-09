package model.plants;

import model.Action;
import model.ActionEnum;
import model.Organism;
import model.World;
import model.Position;
import model.animals.CyberSheep;

import java.util.ArrayList;

public class SosnowskiHogweed extends Plant {
    
    public SosnowskiHogweed(Position position, World world) {
        super(position, world);
    }

    public SosnowskiHogweed(Position position) {
        super(position, null);
    }

    public SosnowskiHogweed(SosnowskiHogweed sosnowskiHogweed){
        super(sosnowskiHogweed);
    }
    
    public SosnowskiHogweed clone() {
        return new SosnowskiHogweed(this);
    }
    
    @Override
    public void initParam() {
        this.setPower(10);
        this.setInitiative(0);
    }
    
    @Override
    public ArrayList<Action> move() {
        ArrayList<Action> result = new ArrayList<Action>();
        result.addAll(super.move());
        ArrayList<Position> neighboringPositions = world.getListOfNeighboringPositions(position, 1);
        Organism pomOrganism = null;
        for(Position pomPosition : neighboringPositions)
            if((pomOrganism = world.getOrganismFromPosition(pomPosition)) != null)
                 if(! (pomOrganism instanceof Plant || pomOrganism instanceof CyberSheep))
                        result.add(new Action(ActionEnum.A_REMOVE, new Position(-1, -1), 0, pomOrganism));
        return result;
    }
    
    @Override
    public ArrayList<Action> consequences(Organism atackingOrganism) {
        ArrayList<Action> result = new ArrayList<>();
        if(atackingOrganism instanceof CyberSheep){
            result.add(new Action(ActionEnum.A_REMOVE, new Position(-1, -1), 0, this));
        }
        else if (this.getPower() > atackingOrganism.getPower()) {
            result.add(new Action(ActionEnum.A_REMOVE, new Position(-1, -1), 0, atackingOrganism));
        } else {
            result.add(new Action(ActionEnum.A_REMOVE, new Position(-1, -1), 0, this));
            result.add(new Action(ActionEnum.A_REMOVE, new Position(-1, -1), 0, atackingOrganism));
        }
        return (result);
    }
}
