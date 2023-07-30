package selab.desktop.resource_management.userManagement.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import selab.desktop.resource_management.model.domain.userManagement.User;
import selab.desktop.resource_management.model.mapper.userManagement.UserMapper;

import java.util.Date;

@SpringBootTest
public class TestMapper {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testInsert(){
        User user = new User();
        user.setUsername("admian");
        user.setName("zhangsan");
        user.setPassword("12456");
        user.setEmail("123@email.com");
        user.setRegistrationTime(new Date());


    }
    @Test
    public void testSelect(){



    }

}