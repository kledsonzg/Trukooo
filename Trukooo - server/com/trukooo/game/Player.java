package com.trukooo.game;

import com.trukooo.util.Utility;

public class Player 
{
    private String name = "";
    private String ip = "";
    private Card[] cards = new Card[3];
    public int points = 0;

    public Player(String nick, String playerIP) throws Exception
    {
        setName(nick, true);
        if(setIP(playerIP) == false)
            throw new Exception("Falha ao instanciar a Classe 'Player' por conta de ip inválido.");

        for(int i = 0; i < cards.length; i++)
            cards[i] = null;
    }

    public Card[] getCards()
    {
        return cards;
    }

    public void setCard(int index, Card card) throws Exception
    {
        if(index < 0 || index >= 3)
            throw new Exception("Falha ao setar a carta do jogador. Index inválido: " + index + ".");

        cards[index] = card;
    }

    public void removeCard(int index) throws Exception
    {
        if(index < 0 || index >= 3)
            throw new Exception("Falha ao remover a carta do jogador. Index inválido: " + index + ".");
        
        cards[index] = null;
    }
    
    public String getIP()
    {
        return ip;
    }

    public boolean setIP(String ipString)
    {
        if(Utility.isValidIP(ipString) == false)
            return false;
        
        ip = ipString;
        return true;
    }

    public String getName(){
        return name;
    }

    public boolean setName(String nick)
    {
        if(nick.length() < 3 || nick.replace(" ", "").isEmpty() )
            return false;

        name = nick;
        return true;
    }

    private void setName(String nick, boolean byConstructor) throws Exception
    {
        if(setName(nick) == false && byConstructor == true)
            throw new Exception("Falha ao instanciar a Classe 'Player' por conta de nickname inválido.");
    }
}
