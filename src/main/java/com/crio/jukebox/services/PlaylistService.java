package com.crio.jukebox.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import com.crio.jukebox.entities.User;
import com.crio.jukebox.exceptions.UserNotFoundException;
import com.crio.jukebox.repositories.UserRepository;
import com.crio.jukebox.repositories.IUserRepository;
import com.crio.jukebox.repositories.ISongRepository;
import com.crio.jukebox.entities.Playlist;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.entities.Playsong;
import com.crio.jukebox.entities.ModifyType;
import com.crio.jukebox.exceptions.EmptyPlaylistException;
import com.crio.jukebox.exceptions.InvalidOperation;
import com.crio.jukebox.exceptions.PlaylistDoesNotBelongToUser;
import com.crio.jukebox.exceptions.PlaylistNotFoundException;
import com.crio.jukebox.exceptions.SongNotFoundException;
import com.crio.jukebox.repositories.IPlaylistRepository;

public class PlaylistService implements IPlaylistService{

    private final IPlaylistRepository playlistRepository;
    private final IUserRepository userRepository;
    private final ISongRepository songRepository;

    public PlaylistService(IPlaylistRepository playlistRepository, IUserRepository userRepository, ISongRepository songRepository) {
        this.playlistRepository = playlistRepository;
        this.userRepository = userRepository;
        this.songRepository = songRepository;
    }

    @Override
    public Playlist create(String userId, String playlistName, List<String> songs) throws UserNotFoundException, SongNotFoundException {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User for given id:"+userId+" not found!"));
        for(String s : songs) {
            songRepository.findById(s).orElseThrow(() -> new SongNotFoundException("Song for given id:"+s+" not found!"));
        }
        final Playlist playlist = new Playlist(userId, playlistName, songs);
        return playlistRepository.save(playlist);
    }

    @Override
    public Playlist modifyPlaylist(String modifyType, String playlistId, String userId, List<String> songs) throws UserNotFoundException, SongNotFoundException, PlaylistNotFoundException, PlaylistDoesNotBelongToUser {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User for given id:"+userId+" not found!"));
        Playlist pl = playlistRepository.findById(playlistId).orElseThrow(() -> new PlaylistNotFoundException("Playlist for given id:"+ playlistId +" not found!"));
        if(!pl.getUserId().equals(userId)){
            throw new PlaylistDoesNotBelongToUser("Playlist does not belong to user ID : "+userId);
        }
        for(String s : songs) {
            songRepository.findById(s).orElseThrow(() -> new SongNotFoundException("Song for given id:"+s+" not found!"));
        }
        List<String> playlistSongs = pl.getSongs();
        if(isValidModifyEnum(modifyType.split("-")[0]) && (modifyType.split("-")[1].toUpperCase().equals("SONG"))) {
            if(modifyType.split("-")[0].toUpperCase().equals("ADD")) {
                for(String addSong: songs){
                    if(!playlistSongs.contains(addSong)) {
                        playlistSongs.add(addSong);
                    }
                }
            }
            else {
                for(String removeSong: songs){
                    if(playlistSongs.contains(removeSong)) {
                        playlistSongs.remove(removeSong);
                    } else {
                        throw new SongNotFoundException("Some Requested Songs for Deletion are not present in the playlist. Please try again.");
                    }
                }
            }
        }
        else {
            throw new InvalidOperation("Invalid Modify Operation");
        }
        final Playlist playlist = new Playlist(playlistId, userId, pl.getPlaylistName(), playlistSongs);
        return playlistRepository.save(playlist);
    }

    @Override
    public String delete(String Id, String userId) throws UserNotFoundException, PlaylistNotFoundException, PlaylistDoesNotBelongToUser {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User for given id:"+userId+" not found!"));
        Playlist pl = playlistRepository.findById(Id).orElseThrow(() -> new PlaylistNotFoundException("Playlist for given id:"+Id+" not found!"));
        if(!pl.getUserId().equals(userId)){
            throw new PlaylistDoesNotBelongToUser("Playlist does not belong to user ID : "+userId);
        }
        playlistRepository.deleteById(Id);
        return "Delete Successful";   
    }

    @Override
    public Song playPlaylist(String id, String userId) throws UserNotFoundException, PlaylistNotFoundException, PlaylistDoesNotBelongToUser, EmptyPlaylistException, SongNotFoundException {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User for given id : " + userId + " not found!"));
        Playlist pl = playlistRepository.findById(id).orElseThrow(() -> new PlaylistNotFoundException("Playlist for given id : " + id + " not found!"));
        if(!pl.getUserId().equals(userId)) {
            throw new PlaylistDoesNotBelongToUser("Playlist does not belong to user ID : "+userId);
        }
        if(pl.getSongs() == null) {
            throw new EmptyPlaylistException("Empty Playlist");
        }
        playlistRepository.setActivePlaylistOfUser(userId, id);
        List<String> plSongs = pl.getSongs();
        return songRepository.findById(plSongs.get(playlistRepository.getCurrentSong())).orElseThrow(() -> new SongNotFoundException("Song for given id : " + plSongs.get(playlistRepository.getCurrentSong()) + " not found!"));
    }

    @Override
    public Song playSong(String userId, String play) throws UserNotFoundException, PlaylistNotFoundException, EmptyPlaylistException, SongNotFoundException, InvalidOperation {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User for given id : " + userId + " not found!"));
        String activePlaylist = playlistRepository.getActivePlaylistOfUser(userId);
        Playlist pl = playlistRepository.findById(activePlaylist).orElseThrow(() -> new PlaylistNotFoundException("Playlist for given id : " + activePlaylist + " not found!"));
        if(pl.getSongs() == null) {
            throw new EmptyPlaylistException("Empty Playlist");
        }
        if(isNumeric(play)){
            if(pl.getSongs().contains(play)){
                playlistRepository.setCurrentSong(pl.getSongs().indexOf(play));
                return songRepository.findById(play).orElseThrow(() -> new SongNotFoundException("Song for given id : " + play + " not found!"));
            }
            else {
                throw new SongNotFoundException("Given song id is not a part of the active playlist");
            }
        }
        else if(isValidEnum(play)){
            Integer currentSong = playlistRepository.getCurrentSong();
            Integer songNumber;
            if(Playsong.valueOf(play) == Playsong.BACK){
                if(currentSong == 0){
                    songNumber = pl.getSongs().size() - 1;
                    playlistRepository.setCurrentSong(songNumber);
                    return songRepository.findById(pl.getSongs().get(songNumber)).orElseThrow(() -> new SongNotFoundException("Song not found!"));
                }
                else {
                    playlistRepository.setCurrentSong(currentSong - 1);
                    return songRepository.findById(pl.getSongs().get(currentSong - 1)).orElseThrow(() -> new SongNotFoundException("Song not found!"));
                }
            }
            else {
                if(currentSong == pl.getSongs().size() - 1){
                    songNumber = 0;
                    playlistRepository.setCurrentSong(songNumber);
                    return songRepository.findById(pl.getSongs().get(songNumber)).orElseThrow(() -> new SongNotFoundException("Song not found!"));
                }
                else {
                    playlistRepository.setCurrentSong(currentSong + 1);
                    return songRepository.findById(pl.getSongs().get(currentSong + 1)).orElseThrow(() -> new SongNotFoundException("Song not found!"));
                }
            }
        } else {
            throw new InvalidOperation("Invalid play Operation");
        }
    }

    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Integer i = Integer.parseInt(strNum);
        } catch (NumberFormatException notNum) {
            return false;
        }
        return true;
    }

    public boolean isValidEnum(String checkEnum) {
        HashSet<String> playEnums = new HashSet<String>();

        for (Playsong p : Playsong.values()) {
            playEnums.add(p.name());
        }
        return playEnums.contains(checkEnum.toUpperCase());
    }

    public boolean isValidModifyEnum(String enumValue) {
        HashSet<String> modifyEnums = new HashSet<String>();

        for (ModifyType p : ModifyType.values()) {
            modifyEnums.add(p.name());
        }
        return modifyEnums.contains(enumValue.toUpperCase());
    }
    
}