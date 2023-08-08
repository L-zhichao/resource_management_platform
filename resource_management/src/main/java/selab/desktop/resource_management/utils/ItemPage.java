package selab.desktop.resource_management.utils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import selab.desktop.resource_management.domain.itemManagement.item.Item;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemPage {

    @Schema(description = "条数")
    private Long total;

    @Schema(description = "起始页")
    private Long totalPage;

    @Schema(description = "物品")
    private List<Item> items;

}