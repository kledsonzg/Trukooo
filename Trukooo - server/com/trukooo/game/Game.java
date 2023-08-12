package com.trukooo.game;

import java.util.Random;

public class Game 
{
    private Player[] players = {null, null, null, null};
    
    private Player playerTurn = null;

    public GameStatus status = GameStatus.GAME_STATUS_WAITING_FOR_PLAYERS;


    public boolean addPlayer(Player player)
    {
        boolean result = false;

        for(int i = 0; i < players.length; i++)
        {
            if(players[i] != null)
                continue;
            
                players[i] = player;
                result = true;
                break;
        }

        return result;
    }

    public boolean removePlayer(Player player)
    {
        boolean result = false;
        
        for(int i = 0; i < players.length; i++)
        {
            if(players[i] != player)
                continue;
            
            players[i] = null;
            result = true;
            break;
        }

        return result;
    }

    public void giveCardsToPlayer(Player player) throws Exception
    {
        int index = -1;
        for(int i = 0; i < players.length; i++)
        {
            if(player == players[i] )
            {
                index = i;
                break;
            }
        }

        if(index == -1)
            return;
        
        giveCardsToPlayer(index);
    }

    public void giveCardsToPlayer(int playerIndex) throws Exception
    {
        if(playerIndex >= players.length || playerIndex < 0)
            return;
        
        if(players[playerIndex] == null)
            return;

        int[] symbolIndex = {new Random().nextInt(4), new Random().nextInt(4), new Random().nextInt(4) };
        int[] caractereIndex = {0, 0, 0};

        for(int i = 0; i < 3; i++)
        {
            while(caractereIndex[i] == 0)
            {
                caractereIndex[i] = new Random().nextInt(11);
            }

            players[i].setCard(i, new Card(CardSymbol.getCardSymbolByIndex(symbolIndex[i] ), CardCharacter.getCardCharacterByIndex(caractereIndex[i] ) ) );
        }
    }
}
