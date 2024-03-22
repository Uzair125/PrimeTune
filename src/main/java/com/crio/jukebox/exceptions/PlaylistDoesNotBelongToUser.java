package com.crio.jukebox.exceptions;

public class PlaylistDoesNotBelongToUser extends RuntimeException{
    public PlaylistDoesNotBelongToUser()
    {
     super();
    }
    public PlaylistDoesNotBelongToUser(String msg)
    {
     super(msg);
    }
}
