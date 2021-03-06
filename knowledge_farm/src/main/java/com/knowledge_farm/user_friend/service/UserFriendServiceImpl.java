package com.knowledge_farm.user_friend.service;

import com.knowledge_farm.annotation.Task;
import com.knowledge_farm.entity.*;
import com.knowledge_farm.notification.service.NotificationService;
import com.knowledge_farm.user.service.UserServiceImpl;
import com.knowledge_farm.user_crop.service.UserCropServiceImpl;
import com.knowledge_farm.user_friend.dao.UserFriendDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    @Resource
    private UserCropServiceImpl userCropService;
    @Resource
    private NotificationService notificationService;

    public Page<User> findUserFriendPageByAccount(Integer userId, String account, Integer pageNumber, Integer pageSize){
        if(account != null && !account.equals("")){
            return this.userFriendDao.findUserFriendPageByAccount(userId, account, PageRequest.of(pageNumber - 1, pageSize));
        }
        return this.userFriendDao.findUserFriendPage(userId, PageRequest.of(pageNumber - 1, pageSize));
    }

    public Page<User> findAllUserByAccount(Integer userId, String account, Integer pageNumber, Integer pageSize){
        if(account != null && !account.equals("")){
            return this.userFriendDao.findAllUserByAccountAndExcludeMeFriendUser(userId, account, PageRequest.of(pageNumber - 1, pageSize));
        }
        return this.userFriendDao.findAllUserAndExcludeMeFriendUser(userId, PageRequest.of(pageNumber - 1, pageSize));
    }

    public List<User> findStrangerByAccount(Integer userId, String account){
        List<Integer> idList = null;
        if(account != null && !account.equals("")){
            return null;
        }
        idList = this.userFriendDao.findRandom();
        return null;
    }

    @Transactional(readOnly = false)
    public String addUserFriend(String sendAccount, Integer userId){
        User user = this.userService.findUserByAccount(sendAccount);
        User friendUser = this.userService.findUserById(userId);
        if(user == null || friendUser == null){
            return Result.FALSE;
        }

        UserFriend userFriend = new UserFriend();
        userFriend.setUser(user);
        userFriend.setFriendUser(friendUser);

        UserFriend userFriend1 = new UserFriend();
        userFriend1.setUser(friendUser);
        userFriend1.setFriendUser(user);

        List<UserFriend> friends = new ArrayList<>();
        friends.add(userFriend);
        friends.add(userFriend1);
        this.userFriendDao.saveAll(friends);

        Notification notification = this.notificationService.findNotificationByUser(user.getId(), userId, 2);
        notification.setHaveRead(2);
        return Result.TRUE;
    }

    @Transactional(readOnly = false)
    public void refuseUserFriend(String sendAccount, Integer userId){
        User user = this.userService.findUserByAccount(sendAccount);
        Notification notification = this.notificationService.findNotificationByUser(user.getId(), userId, 2);
        notification.setHaveRead(-2);
    }

    @Transactional(readOnly = false)
    public void deleteUserFriend(Integer userId, String account){
        User user = this.userService.findUserById(userId);
        User friendUser = this.userService.findUserByAccount(account);
        List<UserFriend> userFriends2 = this.userFriendDao.findUserFriendByUserAndFriendUser(user.getId(), friendUser.getId());
        Notification notification = this.notificationService.findNotificationByUser2(user.getId(), userId, 2);
        if(notification != null){
            List<Integer> id = new ArrayList<>();
            id.add(notification.getId());
            this.notificationService.deleteNotification(id);
        }
        this.userFriendDao.deleteAll(userFriends2);
    }

    @Task(description = "help_water")
    @Transactional(readOnly = false)
    public Integer waterForFriend(Integer userId, Integer friendId, String landNumber){
        User user = this.userService.findUserById(userId);
        User friendUser = this.userService.findUserById(friendId);
        Land land = friendUser.getLand();
        UserCrop userCrop = this.userCropService.findUserCropByLand(land, landNumber);
        Crop crop = userCrop.getCrop();
        //修改剩余水的次数
        if(user.getWater() > 0){
            //修改作物进度
            int progress = userCrop.getProgress();
            int matureTime = crop.getMatureTime();
            if(progress < matureTime){
                String result =  this.notificationService.addWaterForFriendNotification(userId, friendId);;
                if(result == Result.TRUE){
                    if(progress+5 >= matureTime){
                        userCrop.setProgress(crop.getMatureTime());
                    }else{
                        userCrop.setProgress(progress + 5);
                    }
                    user.setWater(user.getWater() - 1);
                    user.setMoney(user.getMoney() + 10);
                    //修改作物干枯湿润状态
                    if(userCrop.getStatus() == 0){
                        userCrop.setStatus(1);
                        return userCrop.getId();
                    }
                    return 0;
                }
            }
        }
        return -1;
    }

    @Task(description = "help_fertilize")
    @Transactional(readOnly = false)
    public String fertilizerForFriend(Integer userId, Integer friendId, String landNumber){
        User user = this.userService.findUserById(userId);
        User friendUser = this.userService.findUserById(friendId);
        Land land = friendUser.getLand();
        UserCrop userCrop = this.userCropService.findUserCropByLand(land, landNumber);
        Crop crop = userCrop.getCrop();
        if(userCrop.getStatus() != 0){
            //修改剩余化肥的次数
            if(user.getFertilizer() > 0){
                //修改作物进度
                int progress = userCrop.getProgress();
                int matureTime = crop.getMatureTime();
                if(progress < matureTime) {
                    String result = this.notificationService.addFertilizerForFriendNotification(userId, friendId);
                    if(result == Result.TRUE){
                        if (progress+10 >= matureTime) {
                            userCrop.setProgress(crop.getMatureTime());
                        }else {
                            userCrop.setProgress(progress + 10);
                        }
                        user.setFertilizer(user.getFertilizer() - 1);
                        user.setMoney(user.getMoney() + 20);
                        return Result.TRUE;
                    }
                }
            }
        }
        return Result.FALSE;
    }

}
