

public class Player {
    public int troops;
    private char intent;
    public Integer[] diceroll = {3,2,1};
    private int wincount;
    public int dieLen;


    public void troops(int tr) { troops = tr; }
    public void subTroops(int minus) { troops -= minus; }
    public int troops() { return troops; }

    public void intent(char intention) { intent = intention; }
    public char intent() { return intent; }

    public void rollDice(int num) { 
        for(int i = 0; i < 3; i++){
            diceroll[i] = 0;
        }
        for(int i = 0; i < num; i++) {
            while(diceroll[i] == 0)
                diceroll[i] = (int)Math.round((Math.random() * 6));
        }
    }
    public int getDice(int i) { return diceroll[i]; }

    public int getDieLen() { return dieLen; }
    public void setDieLen(int die) { dieLen = die; }

    public void addWin() { wincount++; }
    public int getWins() { return wincount; }
}
