package selab.desktop.resource_management.itemManagement.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import selab.desktop.resource_management.itemManagement.domain.Item;
import selab.desktop.resource_management.itemManagement.service.ItemService;
import selab.desktop.resource_management.itemManagement.utils.ItemPage;
import selab.desktop.resource_management.itemManagement.utils.Result;

import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

   @GetMapping()
    public Result<ItemPage> selectAllItem(@RequestParam(defaultValue = "1")int page,
                                            @RequestParam(defaultValue = "5")int size,
                                            @RequestParam(defaultValue = "") String search){
       Page<Item> itemPage = itemService.selectAllItem(page, size,search);
       long total = itemPage.getTotal();
       long pages = itemPage.getPages();
       List<Item> records = itemPage.getRecords();
       ItemPage itemPage1 = new ItemPage(total,pages,records);
       return new Result<>(20000,"查询成功",itemPage1);
   }
   @PostMapping()
   public Result<Long> addItem(@RequestBody Item item){
       return itemService.addItem(item);

   }
   @PutMapping()
    public Result<?> updateItem(Item item){
       itemService.updateItem(item);
       return new Result<>(20000,"修改成功",null);
   }
   @GetMapping("/{id}")
    public Result<Item> getItemById(Long id){
       Item item = itemService.getItemById(id);
       return new Result<>(20000,null,item);
   }
   @DeleteMapping()
    public Result<?> deleteItemById(Long id){
     itemService.deleteItemById(id);
     return new Result<>(20000,"删除成功",null);
   }
}
