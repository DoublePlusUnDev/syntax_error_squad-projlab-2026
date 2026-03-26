public class Buyable {
    private int price;

    boolean buy(Inventory inventory){
        TestUtil.enterFunction("Buyable:buy(inventory)");

        boolean hasMoney = TestUtil.askUserYesNo("Does the player have enough money to buy the product?");

        TestUtil.exitFunction(String.valueOf(hasMoney));
        return hasMoney;
    }
}
