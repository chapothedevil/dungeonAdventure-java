import java.util.Scanner;

class Environment{
    String [][] grid = new String[5][5];
    int healthPositionX;
    int healthPositionY;
    int powerPositionX;
    int powerPositionY;

    public Environment(int healthPositionX, int healthPositionY, int powerPositionX, int powerPositionY){
        this.healthPositionX = healthPositionX;
        this.healthPositionY = healthPositionY;
        this.powerPositionX = powerPositionX;
        this.powerPositionY = powerPositionY;
    }

    // Initialize grid
    void initializeGrid() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = "-";  // Fill grid with empty cells
            }
        }
    }

    // Print the grid
    void printGrid() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }
}

class Character {
    int health;
    int power;
    int playerX;
    int playerY;

    public Character(int health, int power, int playerX, int playerY) {
        this.health = health;
        this.power = power;
        this.playerX = playerX;
        this.playerY = playerY;
    }
}

class Enemy extends Character{
    int enemyX;
    int enemyY;

    public Enemy(int health, int power, int enemyX, int enemyY) {
        super(health, power, enemyX, enemyY);
        this.enemyX = enemyX;
        this.enemyY = enemyY;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner key = new Scanner(System.in);
        Environment env = new Environment(3, 3, 4, 4);
        env.initializeGrid();  // Initializing the grid
        // Initialize player and enemy positions
        Character position = new Character(100, 50, 0, 0);  // Player starts at (0, 0)
        Enemy enemyPosition = new Enemy(100, 50, 3, 3);     // Enemy at (3, 3)
        while (true) {
            System.out.println("Player Health: " + position.health + "\t \t" + "Enemy Health: " + enemyPosition.health);
            System.out.println("Player Power: " + position.power + "\t \t" + "Enemy Power: " + enemyPosition.power);
            env.initializeGrid();
            env.grid[position.playerX][position.playerY] = "P";  // Setting the player 'P' in the grid at current position
            env.grid[enemyPosition.enemyX][enemyPosition.enemyY] = "E";     // Setting the enemy 'E' in the grid at current position
            env.grid[env.healthPositionX][env.powerPositionY] = "H";    // Setting the health 'H' in the grid at current position
            env.grid[env.powerPositionX][env.powerPositionY] = "+";     // Setting the power '+' in the grid at current position
            env.printGrid();
            System.out.print("Move Player W/A/S/D: ");
            String input = key.nextLine().toLowerCase();
            switch (input){
                case "w":
                    if (position.playerX > 0){
                        position.playerX--;
                        enemyPosition.enemyX++;
                    }
                    break;
                case "a":
                    if (position.playerY > 0){
                        position.playerY--;
                        enemyPosition.enemyY++;
                    }
                    break;
                case "s":
                    if (position.playerX <= 5){
                        position.playerX++;
                        enemyPosition.enemyX--;
                        if (enemyPosition.enemyX < 0){
                            enemyPosition.enemyX++;
                        }
                    }
                    break;
                case "d":
                    if (position.playerY <= 5){
                        position.playerY++;
                        enemyPosition.enemyY--;
                        if (enemyPosition.enemyY < 0){
                            enemyPosition.enemyY++;
                        }
                    }
                    break;
                default:
                    System.out.println("Invalid Move!... Please Select One of The Following (W/A/S/D)");
                    break;
            }
            if (position.playerX == enemyPosition.enemyX && position.playerY == enemyPosition.enemyY){
                position.health = position.health - 25;
                if (position.health == 0){
                    System.out.println("You Loose!");
                    System.out.println("Want to play again? (Y/N): ");
                    String playAgain = key.nextLine().toLowerCase();
                    if (playAgain.equals("y")){
                        position.playerX = 0;
                        position.playerY = 0;
                        position.power = 50;
                        position.health = 100;
                        enemyPosition.enemyX = 3;
                        enemyPosition.enemyY = 3;
                        continue;
                    }
                    else {
                        break;
                    }
                }
            }
            if (position.playerX == env.healthPositionX && position.playerY == env.healthPositionY && position.health <= 75){
                position.health = position.health + 25;
            }
            if (position.health == 50 && position.power > 0){
                position.health = position.health + 25;
                position.power = 0;
            }
            if (enemyPosition.enemyX == env.healthPositionX && enemyPosition.enemyY == env.healthPositionY){
                enemyPosition.health = enemyPosition.health -25;
                if (enemyPosition.health <= 75 && enemyPosition.power > 0){
                    enemyPosition.health = enemyPosition.health + 25;
                    enemyPosition.power = 0;
                }
                if (enemyPosition.health <= 0){
                    System.out.println("Enemy is dead, haha!... You Win!");
                    break;
                }
            }
            if (enemyPosition.enemyX == env.powerPositionX && enemyPosition.enemyY == env.powerPositionY){
                enemyPosition.power = 0;
            }
            if (enemyPosition.enemyX == env.powerPositionX && enemyPosition.enemyY == env.powerPositionY){
                enemyPosition.power = enemyPosition.power -25;
            }
        }
    }
}
