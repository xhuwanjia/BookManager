package com.wj.bookmanager.service;

import com.wj.bookmanager.model.User;
import com.wj.bookmanager.utils.ConcurrentUtils;
import org.springframework.stereotype.Service;

@Service
public class HostHolder {
    public User getUser(){
        return ConcurrentUtils.getHost();
    }

    public void setUser(User user){
        ConcurrentUtils.setHost(user);
    }

    private void clearUser(){
        ConcurrentUtils.clearHost();
    }
}
