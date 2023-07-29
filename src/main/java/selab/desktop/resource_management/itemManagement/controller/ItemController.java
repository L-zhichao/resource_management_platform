package selab.desktop.resource_management.itemManagement.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import selab.desktop.resource_management.itemManagement.domain.Item;
import selab.desktop.resource_management.itemManagement.service.ItemService;
import selab.desktop.resource_management.itemManagement.utils.ItemPage;
import selab.desktop.resource_management.itemManagement.utils.Result;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    //分页查询所有的物品
   @GetMapping()
    public Result<ItemPage> all(@RequestParam(defaultValue = "1")int page,
                                            @RequestParam(defaultValue = "5")int size,
                                            @RequestParam(defaultValue = "") String search) throws UnsupportedEncodingException {
       search = new String(search.getBytes("ISO-8859-1"), "UTF-8");
       Page<Item> itemPage = itemService.selectAllItem(page, size,search);
       long total = itemPage.getTotal();
       long pages = itemPage.getPages();
       List<Item> records = itemPage.getRecords();
       ItemPage itemPage1 = new ItemPage(total,pages,records);
       return new Result<>(200,"success",itemPage1);
   }
   //添加物品
   @PostMapping()
   public Result<Long> add(@RequestBody Item item){
       return itemService.addItem(item);

   }
   //修改物品
   @PutMapping()
    public Result<?> update(Item item){
       itemService.updateItem(item);
       return new Result<>(200,"success",null);
   }
   //通过id查询物品
   @GetMapping("/{id}")
    public Result<Item> select(@PathVariable Long id){
       Item item = itemService.getItemById(id);
       return new Result<>(200,null,item);
   }
   //通过id删除物品
   @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id){
     itemService.deleteItemById(id);
     return new Result<>(200,"success",null);
   }
}
