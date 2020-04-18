package com.knowledge_farm.userfriend.service;

import com.knowledge_farm.entity.User;
import com.knowledge_farm.entity.UserFriend;
import com.knowledge_farm.user.service.UserServiceImpl;
import com.knowledge_farm.userfriend.dao.UserFriendDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @ClassName UserFriendServiceImpl
 * @Description
 * @Author 张帅华
 * @Date 2020-04-10 22:44
 */
@Service
@Transactional(readOnly = true)
public class UserFriendServiceImpl {
    @Resource
    private UserFriendDao userFriendDao;
    @Resource
    private UserServiceImpl userService;

    public Page<User> findUserFriendPageByAccount(Integer userId, String account, Integer pageNumber, Integer pageSize){
        if(account != null && !account.equals("")){
            return this.userFriendDao.findUserFriendPageByAccount(userId, account, PageRequest.of(pageNumber - 1, pageSize));
        }
        return this.userFriendDao.findUserFriendPage(userId, PageRequest.of(pageNumber - 1, pageSize));
    }

    @Transactional(readOnly = false)
    public String addUserFriend(Integer userId, String account){
        User user = this.userService.findUserById(userId);
        User friendUser = this.userService.findUserByAccount(account);
        UserFriend userFriend = new UserFriend();
        try {
            userFriend.setUser(user);
            userFriend.setFriendUser(friendUser);
            this.userFriendDao.save(userFriend);
            return "true";
        }catch (Exception e){
            return "false";
        }
    }

    @Transactional(readOnly = false)
    public String deleteUserFriend(Integer userId, String account){
        User user = this.userService.findUserById(userId);
        User friendUser = this.userService.findUserByAccount(account);
        try {
            UserFriend userFriend = this.userFriendDao.findUserFriendByUserAndFriendUser(user.getId(), friendUser.getId());
            this.userFriendDao.delete(userFriend);
            return "true";
        }catch (NullPointerException e){
            return null;
        }catch (Exception e){
            return "false";
        }
    }

}
