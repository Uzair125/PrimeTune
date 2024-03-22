package com.crio.jukebox.exceptions;

public class InvalidOperation extends RuntimeException {
    public InvalidOperation()
    {
     super();
    }
    public InvalidOperation(String msg)
    {
     super(msg);
    }
}
