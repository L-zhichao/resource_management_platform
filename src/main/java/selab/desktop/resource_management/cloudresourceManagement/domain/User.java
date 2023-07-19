package selab.desktop.resource_management.cloudresourceManagement.domain;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    public final static int ADMAIN_USER = 0;
    public final static int ORDINARY_USER = 1;
    private Long id;
    private String  username;
    private String password;
    private String salt;
    private String name;
    private Integer userStatus;
    private String email;
    private Date registrationTime;

}
