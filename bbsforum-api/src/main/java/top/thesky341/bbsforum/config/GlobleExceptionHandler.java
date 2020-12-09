package top.thesky341.bbsforum.config;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.thesky341.bbsforum.util.result.Result;
import top.thesky341.bbsforum.util.result.ResultCode;

import javax.servlet.http.HttpServletRequest;

/**
 * @author thesky
 * @date 2020/12/9
 */
@RestControllerAdvice
public class GlobleExceptionHandler {
    /**
     * 对全局抛出的异常进行了处理
     * 思考如何进行优化
     */
    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(HttpServletRequest request, Exception e) {
        System.out.println(e);
        if(e instanceof BindException) {             //参数校验错误
            BindException be = (BindException) e;
            FieldError error = be.getFieldError();
            String message = error.getDefaultMessage();
            Result result = new Result(ResultCode.BindException);
            result.setMessage(message);
            return result;
        } else if (e instanceof MethodArgumentNotValidException) {        //参数校验错误
            MethodArgumentNotValidException manve = (MethodArgumentNotValidException) e;
            String message = manve.getBindingResult().getAllErrors().get(0).getDefaultMessage();
            Result result = new Result(ResultCode.BindException);
            result.setMessage(message);
            return result;
        } else if(e instanceof IllegalArgumentException) {   //参数不合法   Assert.notNull
            Result result = new Result(ResultCode.IllegalArgumentException);
            result.setMessage(e.getMessage());
            return result;
        } else if(e instanceof UnknownAccountException) {
            return new Result(ResultCode.UnknownAccountException);
        } else if(e instanceof LockedAccountException) {
            return new Result(ResultCode.LockedAccountException);
        } else if(e instanceof IncorrectCredentialsException) {
            return new Result(ResultCode.IncorrectCredentialsException);
        } else if(e instanceof AuthenticationException) {
            return new Result(ResultCode.AuthenticationException);
        } else {
            return Result.error();
        }
    }
}
