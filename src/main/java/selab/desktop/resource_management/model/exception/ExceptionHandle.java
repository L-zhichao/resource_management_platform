package selab.desktop.resource_management.model.exception;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import selab.desktop.resource_management.utils.JsonResult;

import org.springframework.validation.BindException;

@RestControllerAdvice
public class ExceptionHandle {


    /**
     *   解决注册用户参数验证异常
     *   * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    public JsonResult<String> validExceptionHandle(BindException e){
       JsonResult<String> jsonResult = new JsonResult<>();
       jsonResult.setStatus(40001);
        FieldError fieldError = e.getBindingResult().getFieldErrors().get(0);
        // e.getBindingResult().getAllErrors().get(0).getDefaultMessage()
        jsonResult.setMessage(fieldError.getDefaultMessage());
       jsonResult.setData(fieldError.getField());
        return jsonResult;
    }

   @ExceptionHandler(ServiceException.class)
    public JsonResult<Void> serviceExceptionHandle(ServiceException e){
       if(e instanceof UsernameDuplicatedException){
           return new JsonResult<>(40002,"当前用户名已存在",null);
       } else if (e instanceof UserNotFundException) {
           return new JsonResult<>(40003,"当前用户不存在",null);
       } else if (e instanceof PasswordNotMatchException) {
           return new JsonResult<>(40004,"密码错误",null);
       } else if (e instanceof InsertException) {
           return new JsonResult<>(50001,"增加用户未知异常",null);
       }

       return new JsonResult<>(50005,"未知异常",null);
   }


    }