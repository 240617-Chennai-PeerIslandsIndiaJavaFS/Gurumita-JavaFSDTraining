import java.util.ArrayList;
import java.util.List;

   // Abstract base class for all creatures
abstract class Creature {
    protected String name;
    protected int health;

    public Creature(String name, int health) {
        this.name = name;
        this.health = health;
    }

    public abstract void attack(Creature other);
    public abstract void takeDamage(int damage);

    public boolean isAlive() {
        return health > 0;
    }

    public void interact(Player player) {
    }
}

    // Interface for interactable objects
interface Interactable {
    void interact(Player player);
}

// Player class
class Player extends Creature implements Interactable {
    protected int attackPower;
    protected List<Item> inventory;

    public Player(String name, int health, int attackPower) {
        super(name, health);
        this.attackPower = attackPower;
        this.inventory = new ArrayList<>();
    }

    @Override
    public void attack(Creature other) {
        System.out.println(name + " attacks " + other.name + " for " + attackPower + " damage.");
        other.takeDamage(attackPower);
    }

    @Override
    public void takeDamage(int damage) {
        health -= damage;
        System.out.println(name + " takes " + damage + " damage. Health is now " + health + ".");
    }

    @Override
    public void interact(Player player) {
        // Player interacting with itself, not necessary to implement
    }

    public void pickUpItem(Item item) {
        inventory.add(item);
        System.out.println(name + " picks up " + item.getName());
    }

    public void useItem(String itemName) {
        for (Item item : inventory) {
            if (item.getName().equals(itemName)) {
                item.use(this);
                inventory.remove(item);
                break;
            }
        }
    }
}

    // Enemy classes
class Goblin extends Creature implements Interactable {
    public Goblin() {
        super("Goblin", 30);
    }

    @Override
    public void attack(Creature other) {
        int damage = 5;
        System.out.println(name + " attacks " + other.name + " for " + damage + " damage.");
        other.takeDamage(damage);
    }

    @Override
    public void takeDamage(int damage) {
        health -= damage;
        System.out.println(name + " takes " + damage + " damage. Health is now " + health + ".");
    }

    @Override
    public void interact(Player player) {
        attack(player);
    }
}

class Dragon extends Creature implements Interactable {
    public Dragon() {
        super("Dragon", 100);
    }

    @Override
    public void attack(Creature other) {
        int damage = 20;
        System.out.println(name + " attacks " + other.name + " for " + damage + " damage.");
        other.takeDamage(damage);
    }

    @Override
    public void takeDamage(int damage) {
        health -= damage;
        System.out.println(name + " takes " + damage + " damage. Health is now " + health + ".");
    }

    @Override
    public void interact(Player player) {
        attack(player);
    }
}

// Abstract base class for items
abstract class Item {
    protected String name;

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void use(Player player);
}

   // Specific item classes
class Potion extends Item {
    private int healingAmount;

    public Potion(int healingAmount) {
        super("Potion");
        this.healingAmount = healingAmount;
    }

    @Override
    public void use(Player player) {
        player.health += healingAmount;
        System.out.println(player.name + " uses " + name + " and heals for " + healingAmount + " health. Health is now " + player.health + ".");
    }
}

class Weapon extends Item {
    private int attackBoost;

    public Weapon(String name, int attackBoost) {
        super(name);
        this.attackBoost = attackBoost;
    }

    @Override
    public void use(Player player) {
        player.attackPower += attackBoost;
        System.out.println(player.name + " uses " + name + " and increases attack power by " + attackBoost + ". Attack power is now " + player.attackPower + ".");
    }
}

   // Game mechanics and main game loop
class Room {
    private String description;
    private Creature creature;
    private Item item;

    public Room(String description, Creature creature, Item item) {
        this.description = description;
        this.creature = creature;
        this.item = item;
    }

    public void enter(Player player) {
        System.out.println(description);
        if (creature != null && creature.isAlive()) {
            creature.interact(player);
        }
        if (item != null) {
            player.pickUpItem(item);
        }
    }
}

public class DungeonAdventure {
    private Player player;
    private List<Room> rooms;

    public DungeonAdventure() {
        player = new Player("Hero", 100, 10);
        rooms = createDungeon();
    }

    private List<Room> createDungeon() {
        List<Room> rooms = new ArrayList<>();
        rooms.add(new Room("You are in a dark cave.", new Goblin(), null));
        rooms.add(new Room("You enter a grand hall.", null, new Potion(20)));
        rooms.add(new Room("You find yourself in a treasure room.", null, new Weapon("Sword", 5)));
        rooms.add(new Room("You step into a dragon's lair.", new Dragon(), null));
        return rooms;
    }

    public void start() {
        for (Room room : rooms) {
            if (!player.isAlive()) {
                System.out.println("You have been defeated. Game over!");
                return;
            }
            room.enter(player);
        }
        System.out.println("You have explored the dungeon and survived. Congratulations!");
    }

    public static void main(String[] args) {
        DungeonAdventure game = new DungeonAdventure();
        game.start();
    }
}
