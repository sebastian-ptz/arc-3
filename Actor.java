import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Iterator;
/**
 * Beschreiben Sie hier die Klasse Actor.
 * 
 * @author Aaron Winter 
 * @version 2018.01.21
 */
public class Actor
{
    private static int WEIGHT_MAX = 40000;
    private static int CALORIES_MAX = 2500;
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private String firstName;
    private String lastName;
    
    private int weight;
    private int calories;
    
    private ArrayList<String> responses;
    private HashMap<String, Item> inventory;

    public Actor(String firstName, String lastName)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.responses = new ArrayList<>();
        this.inventory = new HashMap<>();

        this.weight = 0;
        this.calories = CALORIES_MAX;
    }
    
    public boolean step() {
        calories -= 10;
        
        if (calories <= 0) {
            System.out.println("You starved! Game Over!");
            return true;
        }
        return false;
    }
    
    public String getDetails() {
        String s = "Details:";
        s += "\n" + getName();
        s += "\nWeight: " + weight + "/" + WEIGHT_MAX;
        s += "\nKalories: " + calories + "/" + CALORIES_MAX;
        s += "\nList of ";
        s += getItemStrings();
        return s;
    }
    
    public String getItemStrings(){
        String s = "Items:";
        for(Item item: inventory.values()){
            s += "\n" + item.getDetails();
        }
        return s;
    }
    
    public void eatItem(String itemName) {
        if (inventory.get(itemName) != null) {
            if(inventory.get(itemName).isEatable()) {
                weight -= inventory.get(itemName).getWeight();
                inventory.remove(itemName);        
                System.out.println("You ate " + itemName);
            }
            else {
                System.out.println("You can't eat " + itemName);
            }
            if (itemName.toLowerCase().equals("cookie")) {
                WEIGHT_MAX += 1000;
                System.out.println("Your max weight increased by 1000!");
            }
        }
        else {
            System.out.println("There is no such item in your inventory");
        }
    }

    public void takeItem(String itemName, HashMap<String, Item> roomItems) {
        Item item = roomItems.get(itemName);
        if (item != null) {
            if ((this.weight + item.getWeight()) <= WEIGHT_MAX) {
                this.weight += (item.getWeight());
                inventory.put(item.getName(), item);
                roomItems.remove(item.getName());
                System.out.println("You took " + item.getName());
            }
            else {
                System.out.println("You can't carry any more items!");
            }
        }
        else {
            System.out.println("There is no such item. Note upper and lower case writing");
        }
    }

    public Item dropItem(String itemName, HashMap<String, Item> roomItems) {
        Item item = inventory.get(itemName);
        if (item != null) {
            item = inventory.get(itemName);
            weight -= item.getWeight();
            inventory.remove(itemName);
            roomItems.put(itemName, item);
            System.out.println("You dropped " + itemName);
            return item;
        }
        else {
            System.out.println(itemName + " is not in your inventory. Note upper and lower case writing");
        }
        return null;
    }
    
    // Not working yet
    public void dropAllItems(HashMap<String, Item> roomItems)
    {
        Iterator<Item> it = inventory.values().iterator();
        while (it.hasNext()) {
            Item item = it.next();
            dropItem(item.getName(), roomItems);
        }
    }
    
    // Not working yet
    public void takeAllItems(HashMap<String, Item> roomItems)
    {
        Iterator<Item> it = inventory.values().iterator();
        while (it.hasNext()) {
            Item item = it.next();
            dropItem(item.getName(), roomItems);
        }
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getName() { return firstName + " " + lastName; }
}
