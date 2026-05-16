package gamelogic;

import utils.Logger;

/**
 * A vehicle that is meant to be piloted by the game.
 * Has a home(from which it spawns) and a workplace(it's first target, where it'll rest for a few turns),
 *  then return to it's home and despawn.
 * Cannot enter blocked, snowy or debris filled roads.
 * Can slip, on crash it'll be destroyed and will block the lane. 
 */
public class Car extends Vehicle {
    private Apartment home;
    private Workplace workplace;
    private boolean headedHome = false;

     
    public Car(String id) {
        super(id);
    }

    /**
     * Moves the car towards its current target (workplace or home) using the provided road network.
     * @param road
     */
    public void move(RoadNetwork road) {

        Node target = headedHome ? home : workplace;

        if (target == null)
            return; // No target to move towards

        road.tryMoveTowardsNode(this, target);
    }

    // Setters

    /**
     * Sets the home apartment for this car.
     *
     * @param apartment the home apartment
     */
    public void setApartment(Apartment apartment) {
        this.home = apartment;
    }

    /**
     * Sets the workplace destination for this car.
     *
     * @param workplace the workplace to work at
     */
    public void setWorkplace(Workplace workplace) {
        this.workplace = workplace;
    }

    // Overridden Methods

    @Override
    public boolean canSlip() {
        return true;
    }

    

    @Override
    public void crash(Lane lane) {
        super.crash(lane);
        lane.crashOccured();
        GameLogic.getInstance().removeCar(this);
    }

    /**
     * Handles the event when the car enters a workplace.
     *
     * @param workplace the workplace to enter
     * 
     * If the car is already headed home, it cannot enter the workplace again. 
     * If the workplace is the car's assigned workplace, it will park there and set itself to head home after working. 
     * The car is then removed from the game logic.
     */
    @Override
    public void enterWorkPlace(Workplace workplace) {
        if (headedHome) {
            return; // Already at home, cannot enter workplace
        }

        if (workplace != this.workplace) {
            return;
        }

        Logger.logLine("Car " + id + " has entered its workplace.");
        workplace.parkCar(this);
        headedHome = true; // Set to head home after working
        GameLogic.getInstance().removeCar(this);
    }

    
    /**
     * Handles the event when the car enters an apartment. 
     * 
     * @param apartment the apartment to enter
     * 
     * If the car is not headed home, it cannot enter the apartment.
     * If the apartment is not the car's assigned home, it cannot enter.
     * If the car successfully enters its home apartment, it is removed from the game logic.
     */
    @Override
    public void enterApartment(Apartment apartment) {
        if (!headedHome) {
            return; // Not headed home yet, cannot enter apartment
        }

        if (apartment != this.home) {
            return;
        }

        Logger.logLine("Car " + id + " has returned to its home apartment.");
        GameLogic.getInstance().removeCar(this);
    }

    @Override
    public void inspect() {
        Logger.logLine("Car " + id + " details:");
        Logger.logLine("Location: " + (location != null ? location.id : "none"));
    }
}
