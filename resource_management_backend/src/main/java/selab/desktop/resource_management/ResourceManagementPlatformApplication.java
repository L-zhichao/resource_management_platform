package selab.desktop.resource_management;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class ResourceManagementPlatformApplication{

	public static void main(String[] args) {
		SpringApplication.run(ResourceManagementPlatformApplication.class, args);
	}

}
