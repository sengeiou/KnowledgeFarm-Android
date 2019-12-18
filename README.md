# Knowledge-Farm-Android
# 项目简介

> ​		知识农场这款软件是一款面向一到三年级的小学生群体的养成类游戏，旨在让孩子在娱乐的同时收获知识，提高英语单词水平以及算术水平。一至三年级孩子的英语水平与算术水平急需提高，而孩子们又对学习兴趣不大，《知识农场》可以将学习融入到娱乐中，可以有效地提高孩子们的学习兴趣。

# 技术栈

> 客户端：
>
> ​		图形框架：Glide
>
> ​		事件框架：EventBus
>
> ​		网络请求框架：okHttp
>
> 服务器系统：Ubuntu 18.04
>
> 后台开发框架：JFinal
>
> 数据库：MySQL

# 团队成员

李和谦

孙建旺

张帅华

景光赞

# 主要功能 

## 游戏功能

种植、浇水、施肥、收获

## 学习功能

语文、数学、英语

# 具体流程和功能

# **客户端**

## 注册方式

- QQ登录->完善信息
- 账号注册

## 登录方式

- QQ登录
- 账号登录

> 首次登录后，再次登录时
>
> - QQ登录会自动登录
> - 账号登录会自动完善账号信息，如果上次点了记住密码还会完善密码信息

> 忘记账号密码 ，通过邮箱找回

## 种植作物

1. 首先去商店购买喜欢的种子
2. 选择想中的土地
3. 点击土地，在弹出的背包里选择要种的种子
4. 种植完成

> 背包中可以查看拥有的种子

## 浇灌作物

1. 首先保证有足够的水，如果没水了需去学习中心答题获得
2. 选中下方工具里的水壶
3. 点击需要浇水的土地
4. 浇灌完成，作物成长值增加

> 需注意：植物会自然生长，但会随机发生干旱，这时无法进行施肥，需要浇水才能继续生长，所以虽然可以无限浇水，但是水用完了植物就不会自然生长了

## 作物施肥

1. 同上

## 收获作物

1. 选中下方工具里的手
2. 点击成熟的作物
3. 完成收获，获得金币和经验

## 扩建土地

1. 点击土地上的扩建标志
2. 支付相应金币
3. 完成扩建

## 访问好友农场

1. 点击我的好友
2. 点击或搜索要访问的好友
3. 进入对方的农场
4. 可以帮好友浇水或施肥可以获得额外金币奖励

## 访问陌生人农场

1. 点击我的好友
2. 选择所有人
3. 点击或搜索要访问的人
4. 同上

## 游戏设置

1. 点击设置
2. 可以进行昵称修改、年级修改、密码修改、头像修改、绑定或解绑QQ和邮箱、注销登录

## 学习中心

1. 数学
2. 英语
3. 语文

> 题目难度随用户设置的年级改变

# [后台管理系统](http://39.106.18.238:8080/FarmKnowledge/)

## 用户管理

- 可以修改用户的信息
- 可以删除用户（封号）

## 作物管理

- 可以上架作物
- 修改作物信息

## 管理员管理

- 可以添加新的管理员
- 可以删除和恢复管理员

## 土地管理

- 可以修改用户土地状态
