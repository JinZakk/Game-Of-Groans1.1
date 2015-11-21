import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class DungeonGame implements Game {
    private Collection<Room> rooms;
    public final String GAME_BORDER = "========================";
    private Scanner myScan;
    private int roomsSearched = 0;
    public DungeonGame(){
        rooms = new ArrayList<>();
        myScan = new Scanner(System.in);
    }
    private void initializeRooms(int size){
        for (int i = 0; i < size; i++) {
            rooms.add(new Room());
        }
    }
    private void printGameBorder(){
        System.out.println(GAME_BORDER);
    }
    private Player getChosenClass(){
        int classChoose = 0;
        while(classChoose == 0){
            System.out.println("Choose a Class: ");
            System.out.println("1) Warrior");
            System.out.println("2) Thief");
            System.out.println("3) Wizard");
            int classChoice =  myScan.nextInt();
            if (classChoice == 1) {
                return new Warrior();
            } else if (classChoice == 2) {
                return new Thief();
            }else if(classChoice == 3){
               return new Wizard();
            } else {
                System.out.println("not a valid input");
            }

        }
        return null;
    }
    private void printIntroText(){
        System.out.println("You approach an old stone structure, moss covers the large rocks cobbled together with a single wooden door serving as an entrance.");
        System.out.println("You pull the large door open and enter into a dimly lit room. The door slams shut behind you and a voice echoes through the halls.");
        System.out.println("'Welcome to my dungeon, fool! You will never escape! Not without 100 Gold Pieces! '");
        System.out.println("You are trapped inside this stone prison, can you survive the dungeon, or will you perish like those who came before you?");
    }
    public String getHeroName(){
    		printGameBorder();
    	  System.out.println("What is your name? ");
          String name = myScan.nextLine();
          name = myScan.nextLine();
          return name;
    }
    public int numberOfRooms(){
    	printGameBorder();
        System.out.println("Enter Number of Rooms in the Dungeon(At Least 20): ");
    	int roomNumber = myScan.nextInt();
    	while(roomNumber < 20){
    		printGameBorder();
    		System.out.println("Invalid Input.");
            System.out.println("Enter Number of Rooms(At Least 20): ");
            roomNumber = myScan.nextInt();
    	}
    	return roomNumber;
    }
    public void play() {
    	int roomNumber = numberOfRooms();
        initializeRooms(roomNumber);
        String name = getHeroName();
        printGameBorder();
        Player hero = getChosenClass();
        printGameBorder();
        hero.setName(name);
        printIntroText();

        while(hero.getGold() < 100 && hero.getHealth() > 0){
            printGameBorder();
            System.out.println("Hp: " + hero.getHealth());
            System.out.println("Gp: " + hero.getGold());
            System.out.println("Choose a Room [1-" + roomNumber + "]: ");
            int chosenRoomNumber = myScan.nextInt();
            Room chosenRoom = getRoom(chosenRoomNumber);

            if(roomsSearched >= roomNumber){
            	System.out.println("You are lost in the Dungeon foerver...");
            	System.exit(0);
            }
            else if(chosenRoom == null){
                System.out.println("Invalid room number!");
                continue;
            }
            else if(chosenRoom.hasVisited()){
                System.out.println("Visited room already!");
                continue;
            }
           
            
            ++roomsSearched;
            chosenRoom.enter(hero);
            


        }
        if(hero.getGold() >= 100 && hero.getHealth() >= 50){
            printGameBorder();
            System.out.println("'You have done well " + hero.getName() + " perhaps too well.");
            System.out.println("Face me now, and face your utter demise!'");
            bossBattle boss = new bossBattle();
            Boss Wes = new Boss();
            boss.BossBattle(hero,Wes);
        }else if(hero.getGold() >= 100){
        printGameBorder();
        System.out.println("Congratulations! You have survived the Algorithm and Implementation II Dungeon!");
        System.out.println("You hear a voice from behind you...");
        System.out.println("'This isn't the last you will see of me... MUAHAHAHAHAHAHA......!'");
        System.out.println("The End?");
        System.exit(0);

        }else if(hero.getHealth() <= 0){
            printGameBorder();
            System.out.println("You have failed your quest...");
            System.out.println("You are trapped in the debugging prison by Prof Wes until your final days.");
            System.out.println("The End, for you.");
            System.exit(0);
        }
    }
    private Room getRoom(int roomNumber){
        int roomCount = 0;

        for(Room current : rooms){
            if(++roomCount == roomNumber){
                return current;
            }
        }

        return null;
    }
}
