import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class App {

    private static Player Attacker = new Player();
    private static Player Defender = new Player();
    private int ATT; //Attacking Troops (total)
    private int DTT; //Defending Troops (total)
    private int ATL = 0; //Attacking Troops left AFTER win
    private int DTL = 0; //Defender Troops left AFTER win
    private int NOR = 10000; //Number of runs

    private int ATLO = 0; //Average of Attacking troops left over
    private int DTLO = 0; //Average of Defending troops left over

    private double win; //Win Percentage

    Scanner getNum = new Scanner(System.in); //Object for recieving input

    public static void main(String[] args) throws Exception {
        App game = new App();
        game.start();
    }

    public void start(){
        Attacker.setDieLen(3);
        Defender.setDieLen(2);
        Attacker.intent('a');
        Defender.intent('d');

        System.out.println("Attackers: ");
        
        String num = getNum.nextLine();
        ATT = Integer.parseInt(num);
        System.out.println("Defenders: ");
        num = getNum.nextLine();
        DTT = Integer.parseInt(num);

        

        runBattle();

        
        for(int i = 0; i < 5; i++) System.out.println(); //Create blank lines

        System.out.println("Attackers: " + ATT);
        System.out.println("Defenders: " + DTT);

        System.out.print("Attacker Wins: ");
        System.out.println(Attacker.getWins());
        System.out.print("Defender Wins: ");
        System.out.println(Defender.getWins());
        System.out.print("Attacker Win Precent: ");        
        System.out.print(win);
        System.out.println("%");
        System.out.print("Defender Win Precent: ");        
        System.out.print(100 - win);
        System.out.println("%");
        System.out.print("Average Attacker Troops Remaining: ");
        System.out.println(ATLO);
        System.out.print("Average Defender Troops Remaining: ");
        System.out.println(DTLO);
    }

    public void runBattle(){
        
        if(ATT <= 0 || DTT <= 0) // Make Sure that there isn't less troops
            return;

        Attacker.troops(ATT);
        Defender.troops(DTT);

        ATL = 0;
        DTL = 0;

        

        for (int i = 0; i < NOR; i++){
            blitz();
            if(Attacker.troops() > 0){
                ATL += Attacker.troops(); // Add remaining attacker troops (For average)
            }

            if(Defender.troops() > 0){
                DTL += Defender.troops(); // Add remaining defender troops (For average)
            }

            Attacker.troops(ATT); // Reset the starting amount of troops (For next simulation)
            Defender.troops(DTT);
        }

        if(Attacker.getWins() > 0)
            ATLO = (int)Math.round(ATL / Attacker.getWins()); // Get the average amount of troops remaining
        else 
            ATLO = 0;

        
        if(Defender.getWins() > 0)
            DTLO = (int)Math.round(DTL / Defender.getWins()); // Get the average amount of troops remaining
        else 
            DTLO = 0;

        win = (double)Attacker.getWins() / ((double)Defender.getWins() + (double)Attacker.getWins()); // Win percentage
        win = (int)Math.round(win * 10000) / 100.00;
    }

    public void blitz(){
        while(Attacker.troops() > 0 && Defender.troops() > 0)
            fightOnce();

        
        if (Attacker.troops() <= 0){
            Defender.addWin();
        } else if(Defender.troops() <= 0){
            Attacker.addWin();
            
        }
    }

    public void fightOnce(){
        int at = numToFight(Attacker); // The number to fight during this round (0-3)
        int dt = numToFight(Defender); // The number to fight during this round (0-2)

        if(at <= 0 || dt <= 0)
            return;

        Defender.rollDice(dt);
        Attacker.rollDice(at);
        
        Arrays.sort(Attacker.diceroll, Collections.reverseOrder()); // Sort the dice rolls highest to lowest for comparisions.
        Arrays.sort(Defender.diceroll, Collections.reverseOrder());
        

        Attacker.dieLen = 0;
        Defender.dieLen = 0;
        while (Attacker.dieLen < at && Defender.dieLen < dt) {
            if(Attacker.diceroll[Attacker.dieLen] <= Defender.diceroll[Defender.dieLen]){
                Attacker.troops--;
            } else {
                Defender.troops--;              
            }

            Attacker.dieLen++;
            Defender.dieLen++;
        }
    }

    public int numToFight(Player army){
        if(army.intent() == 'a'){
            if(army.troops() > 2)
                return 3;
            else   
                return army.troops();
        }

        if(army.intent() == 'd'){
            if (army.troops() > 1)
                return 2;
            else
                return army.troops();
        }

        return 0;
    }
}
