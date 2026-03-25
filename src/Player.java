public abstract  class Player {

    protected RoadNetwork roads;
    protected Inventory inventory;

    public Player(RoadNetwork roads) {
        this.roads = roads;
        inventory = new Inventory();
    }

    

    public void takeTurn() {
        
    }

    public void pay(int money) {
        
    }
    
}
