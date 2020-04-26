package com.knowledge_farm.notification.dao;

import com.knowledge_farm.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @ClassName NotificationDao
 * @Description
 * @Author 张帅华
 * @Date 2020-04-21 09:44
 */
public interface NotificationDao extends JpaRepository<Notification, Integer> {
    @Query("select n from Notification n where n.from.id = ?1 and n.notificationType.id = ?2")
    public Page<Notification> findSendByNotificationType(Integer userId, Integer typeId, Pageable pageable);

    @Query("select n from Notification n where n.to.id = ?1 and n.notificationType.id = ?2")
    public Page<Notification> findReceivedByNotificationType(Integer userId, Integer typeId, Pageable pageable);

    @Query("select n from Notification n where (n.to.id = ?1 or n.to.id is null) and n.notificationType.id = ?2")
    public Page<Notification> findReceivedByNotificationType2(Integer userId, Integer typeId, Pageable pageable);

//    @Query("select n from Notification n where n.from.id = ?1 and n.notificationType.id = ?2")
//    public List<Notification> findSendByNotificationType(Integer userId, Integer typeId, Sort sort);
//
//    @Query("select n from Notification n where n.to.id = ?1 and n.notificationType.id = ?2")
//    public List<Notification> findReceivedByNotificationType(Integer userId, Integer typeId, Sort sort);
//
//    @Query("select n from Notification n where (n.to.id = ?1 or n.to.id is null) and n.notificationType.id = ?2")
//    public List<Notification> findReceivedByNotificationType2(Integer userId, Integer typeId, Sort sort);

    public Notification findNotificationById(Integer id);
}