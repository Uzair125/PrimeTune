package com.crio.jukebox.commands;

import java.util.List;
import com.crio.jukebox.services.IPlaylistService;

public class DeletePlaylistCommand implements ICommand{

    private final IPlaylistService playlistService;

    public DeletePlaylistCommand(IPlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @Override
    public void execute(List<String> tokens) {
        String userId = tokens.get(1);
        String playlistID = tokens.get(2);
        playlistService.delete(playlistID, userId);
        System.out.println("Delete Successful");
    }
    
}
