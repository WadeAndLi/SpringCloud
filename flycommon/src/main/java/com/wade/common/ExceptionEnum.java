package com.wade.common;

public enum ExceptionEnum {

    NAME_CANNOT_BE_NULL(400, "姓名不能为空"),
    CATEGORY_NOT_FOUND(404, "商品分类没有查到"),
    BRAND_NOT_FOUND(404, "品牌不存在"),
    UPLOAD_ERROR(500, "文件上传失败"),
    FILE_TYPE_ERROR(400, "文件类型不匹配"),
    SPECIFICATION_NOT_FOUND(404, "商品规格不存在"),
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
