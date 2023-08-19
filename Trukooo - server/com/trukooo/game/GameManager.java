package com.trukooo.game;

public class GameManager 
{
    private static Game[] games = {null, null, null, null, null, null, null, null, null, null};
    private static boolean running = false;

    public static void start()
    {
        for(int i = 0; i < games.length; i++)
        {
            games[i] = new Game();
        }

        running = true;
    }

    public static Game getRunningGame(int index) 
    {
        Game resultGame = null;
        if(index < 0 || index >= games.length)
            return resultGame;
        
        resultGame = games[index];
        return resultGame;
    }

    public static boolean isRunning() { return running; }
}
