package com.wangff.learn.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_user")
public class User implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 机构ID
     */
    @Column(name = "org_id")
    private Long orgId;

    /**
     * 机构tree_id
     */
    @Column(name = "org_tree_id")
    private String orgTreeId;

    /**
     * 登录名
     */
    private String username;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 昵称，用于讨论、消息、评论等
     */
    private String nickname;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    /**
     * 用户全称，用于评分展示(由 first_name及 last_name拼接)
     */
    @Column(name = "full_name")
    private String fullName;

    /**
     * 性别, 0: 未知, 1: 男, 2: 女
     */
    private Integer sex;

    /**
     * SIS ID
     */
    @Column(name = "sis_id")
    private String sisId;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 头像文件：关联sys_user_file表主键
     */
    @Column(name = "avatar_user_file_id")
    private Long avatarUserFileId;

    /**
     * 头像文件
     */
    @Column(name = "avatar_file_id")
    private String avatarFileId;

    /**
     * 是否注册中用户,注册中用户为课程用户添加时的临时用户,不能正常使用
     */
    @Column(name = "is_registering")
    private Integer isRegistering;

    /**
     * 使用语言（预留）
     */
    private String language;

    /**
     * 时区（预留）
     */
    @Column(name = "time_zone")
    private String timeZone;

    /**
     * 最近登录时间
     */
    @Column(name = "last_login_time")
    private Date lastLoginTime;

    /**
     * 头衔
     */
    private String title;

    /**
     * 启用状态
     */
    private Integer status;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "update_user_id")
    private Long updateUserId;

    /**
     * 简介
     */
    private String description;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取机构ID
     *
     * @return org_id - 机构ID
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * 设置机构ID
     *
     * @param orgId 机构ID
     */
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    /**
     * 获取机构tree_id
     *
     * @return org_tree_id - 机构tree_id
     */
    public String getOrgTreeId() {
        return orgTreeId;
    }

    /**
     * 设置机构tree_id
     *
     * @param orgTreeId 机构tree_id
     */
    public void setOrgTreeId(String orgTreeId) {
        this.orgTreeId = orgTreeId;
    }

    /**
     * 获取登录名
     *
     * @return username - 登录名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置登录名
     *
     * @param username 登录名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取登录密码
     *
     * @return password - 登录密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置登录密码
     *
     * @param password 登录密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取昵称，用于讨论、消息、评论等
     *
     * @return nickname - 昵称，用于讨论、消息、评论等
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 设置昵称，用于讨论、消息、评论等
     *
     * @param nickname 昵称，用于讨论、消息、评论等
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return first_name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return last_name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * 获取用户全称，用于评分展示(由 first_name及 last_name拼接)
     *
     * @return full_name - 用户全称，用于评分展示(由 first_name及 last_name拼接)
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * 设置用户全称，用于评分展示(由 first_name及 last_name拼接)
     *
     * @param fullName 用户全称，用于评分展示(由 first_name及 last_name拼接)
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * 获取性别, 0: 未知, 1: 男, 2: 女
     *
     * @return sex - 性别, 0: 未知, 1: 男, 2: 女
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * 设置性别, 0: 未知, 1: 男, 2: 女
     *
     * @param sex 性别, 0: 未知, 1: 男, 2: 女
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * 获取SIS ID
     *
     * @return sis_id - SIS ID
     */
    public String getSisId() {
        return sisId;
    }

    /**
     * 设置SIS ID
     *
     * @param sisId SIS ID
     */
    public void setSisId(String sisId) {
        this.sisId = sisId;
    }

    /**
     * 获取邮箱
     *
     * @return email - 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     *
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取联系电话
     *
     * @return phone - 联系电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置联系电话
     *
     * @param phone 联系电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取头像文件：关联sys_user_file表主键
     *
     * @return avatar_user_file_id - 头像文件：关联sys_user_file表主键
     */
    public Long getAvatarUserFileId() {
        return avatarUserFileId;
    }

    /**
     * 设置头像文件：关联sys_user_file表主键
     *
     * @param avatarUserFileId 头像文件：关联sys_user_file表主键
     */
    public void setAvatarUserFileId(Long avatarUserFileId) {
        this.avatarUserFileId = avatarUserFileId;
    }

    /**
     * 获取头像文件
     *
     * @return avatar_file_id - 头像文件
     */
    public String getAvatarFileId() {
        return avatarFileId;
    }

    /**
     * 设置头像文件
     *
     * @param avatarFileId 头像文件
     */
    public void setAvatarFileId(String avatarFileId) {
        this.avatarFileId = avatarFileId;
    }

    /**
     * 获取是否注册中用户,注册中用户为课程用户添加时的临时用户,不能正常使用
     *
     * @return is_registering - 是否注册中用户,注册中用户为课程用户添加时的临时用户,不能正常使用
     */
    public Integer getIsRegistering() {
        return isRegistering;
    }

    /**
     * 设置是否注册中用户,注册中用户为课程用户添加时的临时用户,不能正常使用
     *
     * @param isRegistering 是否注册中用户,注册中用户为课程用户添加时的临时用户,不能正常使用
     */
    public void setIsRegistering(Integer isRegistering) {
        this.isRegistering = isRegistering;
    }

    /**
     * 获取使用语言（预留）
     *
     * @return language - 使用语言（预留）
     */
    public String getLanguage() {
        return language;
    }

    /**
     * 设置使用语言（预留）
     *
     * @param language 使用语言（预留）
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * 获取时区（预留）
     *
     * @return time_zone - 时区（预留）
     */
    public String getTimeZone() {
        return timeZone;
    }

    /**
     * 设置时区（预留）
     *
     * @param timeZone 时区（预留）
     */
    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    /**
     * 获取最近登录时间
     *
     * @return last_login_time - 最近登录时间
     */
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * 设置最近登录时间
     *
     * @param lastLoginTime 最近登录时间
     */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * 获取头衔
     *
     * @return title - 头衔
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置头衔
     *
     * @param title 头衔
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取启用状态
     *
     * @return status - 启用状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置启用状态
     *
     * @param status 启用状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return create_user_id
     */
    public Long getCreateUserId() {
        return createUserId;
    }

    /**
     * @param createUserId
     */
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * @return update_user_id
     */
    public Long getUpdateUserId() {
        return updateUserId;
    }

    /**
     * @param updateUserId
     */
    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    /**
     * 获取简介
     *
     * @return description - 简介
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置简介
     *
     * @param description 简介
     */
    public void setDescription(String description) {
        this.description = description;
    }
}