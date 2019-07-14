package com.wade.common;

public enum ExceptionEnum {

    NAME_CANNOT_BE_NULL(400, "姓名不能为空"),
    CATEGORY_NOT_FOUND(404, "商品分类没有查到"),
    BRAND_NOT_FOUND(404, "品牌不存在"),
    UPLOAD_ERROR(500, "文件上传失败"),
    FILE_TYPE_ERROR(400, "文件类型不匹配"),
    SPECIFICATION_NOT_FOUND(404, "商品规格不存在"),
    GOODS_NOT_FOUND(404, "商品不存在"),
    ERROR_TO_ADD_GOODS(500, "添加商品失败"),
    ERROR_CHECK_TYPE(400, "无效的检查类型"),
    USER_LOGIN_ERROR(400, "用户账号密码错误"),
    CART_NOT_FOUND(404, "购物车为空"),
    ;
    private int statusCode;
    private String message;

    ExceptionEnum(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
