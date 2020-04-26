package com.knowledge_farm.user_friend.dao;

import com.knowledge_farm.entity.User;
import com.knowledge_farm.entity.UserFriend;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @ClassName UserFriendDao
 * @Description
 * @Author 张帅华
 * @Date 2020-04-10 22:44
 */
public interface UserFriendDao extends JpaRepository<UserFriend,Integer> {
//    @Query("from User fu where fu.id in (select uf.friendUser.id from UserFriend uf where uf.user.id = ?1)")
    @Query("select uf.friendUser from UserFriend uf where uf.user.id = ?1")
    public Page<User> findUserFriendPage(Integer userId, Pageable pageable);

//    @Query("from User fu where fu.id in (select uf.friendUser.id from UserFriend uf where uf.user.id = ?1) and fu.account = ?2")
    @Query("select uf.friendUser from UserFriend uf where uf.user.id = ?1 and uf.friendUser.account = ?2")
    public Page<User> findUserFriendPageByAccount(Integer userId, String account, Pageable pageable);

    @Query("select uf from UserFriend  uf where (uf.user.id = ?1 and uf.friendUser.id = ?2) or (uf.user.id = ?2 and uf.friendUser.id = ?1)")
    public List<UserFriend> findUserFriendByUserAndFriendUser(Integer userId, Integer friendUserId);

    @Query("select u " +
            "from User u " +
            "where u.id <> ?1 and u.id not in " +
                "(select uf.friendUser.id " +
                "from UserFriend uf " +
                "where uf.user.id = ?1)")
    public Page<User> findAllUserAndExcludeMeFriendUser(Integer userId, Pageable pageable);

    @Query("select u " +
            "from User u " +
            "where u.account = ?2 and u.id <> ?1 and u.id not in " +
            "(select uf.friendUser.id " +
            "from UserFriend uf " +
            "where uf.user.id = ?1)")
    public Page<User> findAllUserByAccountAndExcludeMeFriendUser(Integer userId, String account, Pageable pageable);
}
