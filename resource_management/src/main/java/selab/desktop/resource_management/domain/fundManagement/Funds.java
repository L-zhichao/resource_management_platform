package selab.desktop.resource_management.domain.fundManagement;


import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;
@Data
@TableName("fundsmgm")
public class Funds {
    @Schema(description = "主键ID")
    private Long id;
    @Schema(description = "资产名称")
    private String asset;
    @Schema(description = "资产价值")
    private String assetValue;
    @Schema(description = "判断资产是否可支配（1/0）")
    private Integer judge;
    @Schema(description = "最后更新时间")
    private Date updateTime;
}
