package top.thesky341.bbsforum.util.result;

/**
 * @author thesky
 * @date 2020/12/9
 * 对常用的返回状态进行了封装
 */
public enum ResultCode {
    SUCCESS(0, "成功"),
    ERROR(1, "错误"),
    DuplicateKeyException(101, "插入的唯一性字段数据已存在"),
    BindException(111, "参数校验失败"),
    IllegalArgumentException(112, "参数不合法"),
    NeedLogin(121, "该操作需要先登录"),
    PermissionDenied(181, "用户没有权限"),
    IncorrectCredentialsException(131, "密码不正确"),
    UnknownAccountException(132, "此账号不存在"),
    LockedAccountException(133, "此账号已被锁定"),
    AuthenticationException(134, "认证异常"),
    UnauthenticatedException(141, "用户没有登录"),
    UsernameAlreadyExist(151, "用户名已存在"),
    EmailAlreadyExist(152, "邮箱已存在"),
    PictureFormatError(161, "图片格式应该为jpg, png 或 jpeg"),
    PictureSavingError(162, "保存图片出错"),
    RewardNotGreater0(171, "积分奖励必须大于 0"),
    ScoreNotEnough(172, "用户积分不足"),
    OperateNotExist(191, "操作不存在"),
    CategoryAlreadyExist(201, "分类已存在"),
    PostNotRewardRequest(211, "该帖子不是积分悬赏");
    /**
     * 状态码
     */
    private final int code;
    /**
     * 提示信息
     */
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
