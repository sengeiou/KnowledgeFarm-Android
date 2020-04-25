package com.knowledge_farm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName User
 * @Description
 * @Author 张帅华
 * @Date 2020-04-06 13:35
 */
@Entity
@Table(name = "user")
//@NamedEntityGraph(name = "Category.Graph", attributeNodes = {@NamedAttributeNode("land"), @NamedAttributeNode("userAuthority"), @NamedAttributeNode("userBags"), @NamedAttributeNode("task")})
public class User {
    private Integer id;
    private String account;
    private String password;
    private String nickName;
    private String photo;
    private String email;
    private Integer level;
    private Integer experience;
    private Integer grade;
    private Integer money;
    private Integer mathRewardCount;
    private Integer englishRewardCount;
    private Integer chineseRewardCount;
    private Integer water;
    private Integer fertilizer;
    private Integer online;
    private Integer exist;
    private Land land;
    private UserAuthority userAuthority;
    private Set<UserBag> userBags = new HashSet<>();
    private Task task;
    private Set<UserFriend> userFriends = new HashSet<>();
    private Set<Notification> sendNotifications = new HashSet<>();
    private Set<Notification> ReceiveNotifications = new HashSet<>();

    @Id
    @GeneratedValue(generator="identity")
    @GenericGenerator(name="identity", strategy="identity")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "nick_name")
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(insertable = false, columnDefinition = "int default 1")
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Column(insertable = false, columnDefinition = "int default 0")
    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    @Column(columnDefinition = "int default 1")
    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    @Column(insertable = false, columnDefinition = "int default 1000")
    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    @Column(name = "math_reward_count", insertable = false, columnDefinition = "int default 3")
    public Integer getMathRewardCount() {
        return mathRewardCount;
    }

    public void setMathRewardCount(Integer mathRewardCount) {
        this.mathRewardCount = mathRewardCount;
    }

    @Column(name = "english_reward_count", insertable = false, columnDefinition = "int default 3")
    public Integer getEnglishRewardCount() {
        return englishRewardCount;
    }

    public void setEnglishRewardCount(Integer englishRewardCount) {
        this.englishRewardCount = englishRewardCount;
    }

    @Column(name = "chinese_reward_count", insertable = false, columnDefinition = "int default 3")
    public Integer getChineseRewardCount() {
        return chineseRewardCount;
    }

    public void setChineseRewardCount(Integer chineseRewardCount) {
        this.chineseRewardCount = chineseRewardCount;
    }

    @Column(insertable = false, columnDefinition = "int default 0")
    public Integer getWater() {
        return water;
    }

    public void setWater(Integer water) {
        this.water = water;
    }

    @Column(insertable = false, columnDefinition = "int default 0")
    public Integer getFertilizer() {
        return fertilizer;
    }

    public void setFertilizer(Integer fertilizer) {
        this.fertilizer = fertilizer;
    }

    @Column(insertable = false, columnDefinition = "int default 1")
    public Integer getOnline() {
        return online;
    }

    public void setOnline(Integer online) {
        this.online = online;
    }

    @Column(insertable = false, columnDefinition = "int default 1")
    public Integer getExist() {
        return exist;
    }

    public void setExist(Integer exist) {
        this.exist = exist;
    }

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @org.hibernate.annotations.ForeignKey(name = "none")
    @JsonIgnore
    public Land getLand() {
        return land;
    }

    public void setLand(Land land) {
        this.land = land;
    }

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    @org.hibernate.annotations.ForeignKey(name = "none")
    @JsonIgnore
    public UserAuthority getUserAuthority() {
        return userAuthority;
    }

    public void setUserAuthority(UserAuthority userAuthority) {
        this.userAuthority = userAuthority;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @org.hibernate.annotations.ForeignKey(name = "none")
    @JsonIgnore
    public Set<UserBag> getUserBags() {
        return userBags;
    }

    public void setUserBags(Set<UserBag> userBags) {
        this.userBags = userBags;
    }

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "task_id",foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    @JsonIgnore
    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @org.hibernate.annotations.ForeignKey(name = "none")
    @JsonIgnore
    public Set<UserFriend> getUserFriends() {
        return userFriends;
    }

    public void setUserFriends(Set<UserFriend> userFriends) {
        this.userFriends = userFriends;
    }

    @OneToMany(mappedBy = "from", cascade = CascadeType.ALL)
    @org.hibernate.annotations.ForeignKey(name = "none")
    @JsonIgnore
    public Set<Notification> getSendNotifications() {
        return sendNotifications;
    }

    public void setSendNotifications(Set<Notification> sendNotifications) {
        this.sendNotifications = sendNotifications;
    }

    @OneToMany(mappedBy = "to", cascade = CascadeType.ALL)
    @org.hibernate.annotations.ForeignKey(name = "none")
    @JsonIgnore
    public Set<Notification> getReceiveNotifications() {
        return ReceiveNotifications;
    }

    public void setReceiveNotifications(Set<Notification> receiveNotifications) {
        ReceiveNotifications = receiveNotifications;
    }

}
