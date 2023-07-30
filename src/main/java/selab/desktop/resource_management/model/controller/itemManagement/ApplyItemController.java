package selab.desktop.resource_management.model.controller.itemManagement;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import selab.desktop.resource_management.model.domain.itemManagement.applynews.Vo.ApplyItemUpload;
import selab.desktop.resource_management.model.domain.itemManagement.applynews.Vo.ApplyItemVo;
import selab.desktop.resource_management.model.service.itemManagement.ApplyItemService;
import selab.desktop.resource_management.utils.JsonResult;

import java.util.List;

//@Tag(name="物品申请回应",description = "完成物品申请和回应业务")
@RequiredArgsConstructor
@RestController
@RequestMapping("/item")
public class ApplyItemController {
    private final ApplyItemService applyItemService;


    @Operation(summary = "物品申请上传模块")
    @PostMapping("/apply")
    public JsonResult<Void> apply(@RequestBody ApplyItemUpload applyItemUpload, HttpServletRequest request){
        String username = (String)request.getSession().getAttribute("username");
         applyItemService.saveApply(applyItemUpload,username);
         return new JsonResult<>(JsonResult.SUCCESS,null,null);
    }

    @Operation(summary = "所有物品申请信息展示模块")
    @PostMapping("/showApply")
    public JsonResult<List<ApplyItemVo>> showApply(){
        List<ApplyItemVo> applyItemVos = applyItemService.selectAllApply();
        JsonResult<List<ApplyItemVo>> applyItemVosJsonResult = new JsonResult<>(JsonResult.SUCCESS,null,applyItemVos);
        return applyItemVosJsonResult;
    }

   @Operation(summary = "所有未处理申请信息提醒")
   @PostMapping("/readApply")
    public JsonResult<List<ApplyItemVo>> applyRemind(HttpServletRequest request){
       List<ApplyItemVo> applyItemVos = applyItemService.selectAllUnreadApply();
       return new JsonResult<>(JsonResult.SUCCESS,null,applyItemVos);
   }

}