package com.trukooo.subinterface;

public class IPlayer 
{
    public String Name;
    public String Hash;
    public int Port;

    public IPlayer(String name, String hash, int port)
    {
        Name = name;
        Hash = hash;
        Port = port;
    }

    public IPlayer()
    {
        Name = null;
        Hash = null;
        Port = -1;
    }
}
