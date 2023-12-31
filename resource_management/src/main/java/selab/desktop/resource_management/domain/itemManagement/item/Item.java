package selab.desktop.resource_management.domain.itemManagement.item;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Schema(description = "主键物品id")
    @TableId
    private Long itemId;
    @Schema(description = "物品名")
    private String itemName;
    private String description;
    @Schema(description = "数量")
    private Integer number;
    @Schema(description = "价格")
    private Integer price;
    @Schema(description = "照片")
    private String imgs;
    @Schema(description = "视频")
    private  String videos;
}
