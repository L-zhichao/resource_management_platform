package selab.desktop.resource_management.cloudresourceManagement.service;

import org.springframework.stereotype.Service;
import selab.desktop.resource_management.cloudresourceManagement.domain.vo.UserVo;
import selab.desktop.resource_management.cloudresourceManagement.mapper.UserMapper;

@Service
public interface IUserservice {


    /**
     *  注册用户
     * @param userVo
     */
    void register(UserVo userVo);


    /**
     *  用户登录
     * @param username  用户名
     * @param password  密码
     * @return   当前用户
     */

   UserVo login(String username,String password);

}
