package com.crio.jukebox.repositories;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.crio.jukebox.entities.User;

public class UserRepository implements IUserRepository{

    private final Map<String,User> userMap;
    private Integer autoIncrement = 0;

    public UserRepository(){
        userMap = new HashMap<String,User>();
    }

    public UserRepository(Map<String, User> userMap) {
        this.userMap = userMap;
        this.autoIncrement = userMap.size();
    }

    @Override
    public User save(User entity) {
        if( entity.getId() == null ){
            autoIncrement++;
            User u = new User(Integer.toString(autoIncrement),entity.getName());
            userMap.put(u.getId(),u);
            return u;
        }
        userMap.put(entity.getId(),entity);
        return entity;
    }

    @Override
    public List<User> findAll() {
        return userMap.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(userMap.get(id));
    }

    @Override
    public boolean existsById(String id) {
        Optional user = Optional.ofNullable(userMap.get(id));
        if(user != null)
            return true;
        else
            return false;
    }

    @Override
    public void delete(User entity) {
        //Auto-generated method stub
    }

    @Override
    public void deleteById(String id) {
        //Auto-generated method stub
    }

    @Override
    public long count() {
        //Auto-generated method stub
        return 0;
    }

    @Override
    public Optional<User> findByName(String name) {
        List<User> userList= userMap.entrySet().stream().filter(e -> e.getValue().getName() == name)
        .map(Map.Entry::getValue).collect(Collectors.toList());
        if(userList.isEmpty()){
            return Optional.empty();
        }
        return Optional.ofNullable(userList.get(0));
    }
}
