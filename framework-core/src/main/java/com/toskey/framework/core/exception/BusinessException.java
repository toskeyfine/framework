package com.toskey.framework.core.exception;

public class BusinessException extends RuntimeException {

    //友好提示的code码
    protected int friendlyCode;

    //友好提示
    protected String friendlyMsg;

    //业务异常跳转的页面
    protected String urlPath;

    protected BusinessException(int friendlyCode, String friendlyMsg, String urlPath) {
        this.setValues(friendlyCode, friendlyMsg, urlPath);
    }

    public BusinessException(BusinessExceptionEnum businessExceptionEnum) {
        this.setValues(businessExceptionEnum.getCode(), businessExceptionEnum.getMessage(), businessExceptionEnum.getUrlPath());
    }

    private void setValues(int friendlyCode, String friendlyMsg, String urlPath) {
        this.friendlyCode = friendlyCode;
        this.friendlyMsg = friendlyMsg;
        this.urlPath = urlPath;
    }

    public int getCode() {
        return friendlyCode;
    }

    public void setCode(int code) {
        this.friendlyCode = code;
    }

    public String getMessage() {
        return friendlyMsg;
    }

    public void setMessage(String message) {
        this.friendlyMsg = message;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

}
