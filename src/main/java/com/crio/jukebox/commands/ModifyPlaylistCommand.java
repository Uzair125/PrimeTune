package com.crio.jukebox.commands;

import java.util.ArrayList;
import java.util.List;
import com.crio.jukebox.entities.Playlist;
import com.crio.jukebox.services.IPlaylistService;
import com.crio.jukebox.services.PlaylistService;

public class ModifyPlaylistCommand implements ICommand {

    private final IPlaylistService playlistService;

    public ModifyPlaylistCommand(IPlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @Override
    public void execute(List<String> tokens) {
        String modifyType = tokens.get(1);
        String userId = tokens.get(2);
        String playlistId = tokens.get(3);
        List<String> songsList = new ArrayList<String>();
        for(int i=4; i<tokens.size(); i++) {
            songsList.add(tokens.get(i));
        }
        Playlist newPlaylist = playlistService.modifyPlaylist(modifyType, playlistId, userId, songsList);
        System.out.println("Playlist ID - " + newPlaylist.getId());
        System.out.println("Playlist Name - " + newPlaylist.getPlaylistName());
        System.out.println("Song IDs - " + newPlaylist.getSongs());
    }
    
}
