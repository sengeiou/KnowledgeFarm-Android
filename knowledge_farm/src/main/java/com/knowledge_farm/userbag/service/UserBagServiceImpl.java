package com.knowledge_farm.userbag.service;

import com.knowledge_farm.entity.BagItem;
import com.knowledge_farm.entity.Crop;
import com.knowledge_farm.entity.User;
import com.knowledge_farm.entity.UserBag;
import com.knowledge_farm.user.service.UserServiceImpl;
import com.knowledge_farm.userbag.dao.UserBagDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @ClassName UserBagServiceImpl
 * @Description
 * @Author 张帅华
 * @Date 2020-04-10 13:52
 */
@Service
@Transactional(readOnly = true)
@PropertySource(value = {"classpath:photo.properties"})
public class UserBagServiceImpl {
    @Resource
    private UserBagDao userBagDao;
    @Resource
    private UserServiceImpl userService;
    @Value("${file.photoUrl}")
    private String photoUrl;

    public List<BagItem> initUserBag(Integer userId){
        User user = this.userService.findUserById(userId);
        Set<UserBag> userBags = user.getUserBags();
        List<BagItem> bagItems = new ArrayList<>();
        for(UserBag userBag : userBags){
            BagItem bagItem = new BagItem();
            Crop crop = userBag.getCrop();
            crop.setImg1(photoUrl + crop.getImg1());
            crop.setImg2(photoUrl + crop.getImg2());
            crop.setImg3(photoUrl + crop.getImg3());
            crop.setImg4(photoUrl + crop.getImg4());
            bagItem.setCrop(crop);
            bagItem.setNumber(userBag.getNumber());
            bagItems.add(bagItem);
        }
        return bagItems;
    }

    @Transactional(readOnly = false)
    public void deleteById(Integer id){
        this.userBagDao.deleteById(id);
    }

}