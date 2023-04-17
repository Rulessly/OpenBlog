package com.blog.enums;

public enum AppHttpCodeEnum {
    // 成功
    SUCCESS(200,"操作成功"),
    // 登录
    NEED_LOGIN(401,"需要登录后操作"),
    NO_OPERATOR_AUTH(403,"无权限操作"),
    SYSTEM_ERROR(500,"出现错误"),
    USERNAME_EXIST(501,"用户名已存在"),
     PHONENUMBER_EXIST(502,"手机号已存在"), EMAIL_EXIST(503, "邮箱已存在"),
    REQUIRE_USERNAME(504, "必需填写用户名"),
    LOGIN_ERROR(505,"用户名或密码错误"),

    USERNAME_NOT_NULL(507,"用户名不能为空"),
    PASSWORD_NOT_NULL(508,"密码不能为空"),
    EMAIL_NOT_NULL(509,"邮箱不能为空"),
    NICKNAME_NOT_NULL(510,"昵称不能为空"),
    NICKNAME_EXIST(511,"昵称已存在"),
    TAG_EXIST(512,"该标签已存在"),
    TAGID_NOEXIST(508,"该标签不存在"),
    TAGID_NONULL(509,"标签不能为空"),

    MENUNAME_EXIST(515,"菜单名已存在"),
    ROLE_EXIST(520,"角色名已存在"),
    ROLEKEY_EXIST(521,"角色KEY已存在"),
    MENUNAME_NO_HAS_SAME(515,"修改菜单'写博文'失败，上级菜单不能选择自己"),
    DELETE_MENU_FALSE(515,"当前菜单有子菜单，无法删除"),


    ARTICLE_DELETE_ERROR(513,"文章删除失败"),
    DELETE_USER_ISUSE(523,"不能删除当前操作的用户"),
    CATEGORY_EXIST(524,"该分类已存在"),
    CONTENT_NOT_NULL(506,"内容不能为空");
    int code;
    String msg;

    AppHttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
