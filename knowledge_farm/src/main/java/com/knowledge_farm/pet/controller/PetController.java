package com.knowledge_farm.pet.controller;

import com.knowledge_farm.entity.PetVO;
import com.knowledge_farm.entity.Result;
import com.knowledge_farm.pet.service.PetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: knowledge_farm
 * @description: 商店里的宠物
 * @author: 景光赞
 * @create: 2020-04-27 11:34
 **/
@Api(description = "前台宠物接口")
@RestController
@RequestMapping("/pet")
public class PetController {
    @Resource
    private PetService petService;

    /**
     * @description: 商店展示所有宠物
     * @author :景光赞
     * @date :2020/4/27 11:56
     * @param :[pageNum]
     * @return :org.springframework.data.domain.Page<com.knowledge_farm.entity.Pet>
     */
    @ApiOperation(value = "商店展示所有宠物", notes = "返回值：List（PetVO）")
    @GetMapping("/showInStore")
    public List<PetVO> showInStore(HttpSession session, HttpServletResponse response){
        try {
            Integer userId = (Integer) session.getAttribute("userId");
            if(userId != null) {
                return petService.showAllPetInStore(userId);
            }
            response.sendError(401);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @ApiOperation(value = "切换使用的宠物", notes = "返回值：(String)true || (String)false")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "willUsingPetId", value = "将要使用的宠物Id", dataType = "int", paramType = "query", required = true)
    })
    @GetMapping("/changePet")
    public String changePet(@RequestParam("willUsingPetId") Integer willUsingPetId, HttpSession session, HttpServletResponse response, HttpServletRequest request){
        try {
            Integer userId = (Integer) session.getAttribute("userId");
            if(userId != null) {
                int result = this.petService.changePet(userId, willUsingPetId);
                if(result != 0) {
                    request.setAttribute("PetFunction", new Integer[]{userId, result});
                    return Result.TRUE;
                }
                return Result.FALSE;
            }
            response.sendError(401);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.FALSE;
    }

    @ApiOperation(value = "减少体力", notes = "返回值：(String)true：成功 ||(String)false：失败 || (String)not_enough_physical：体力不足 ")
    @GetMapping("decreasePhysical")
    public String decreasePhysical(HttpSession session, HttpServletResponse response){
        try {
            Integer userId = (Integer) session.getAttribute("userId");
            if(userId != null) {
                return this.petService.updatePhysical(userId);
            }
            response.sendError(401);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.FALSE;
    }

    @ApiOperation(value = "宠物对战结果", notes = "返回值：(String)true：成功 ||(String)up：宠物升级 || (String)false：失败|| (String)not_enough_physical：体力不足 ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "对方的账号", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "result", value = "输或赢", dataType = "int", paramType = "query", required = true)
    })
    @GetMapping("/fightResult")
    public String fightResult(@RequestParam("account") String account, @RequestParam("result") Integer result, HttpSession session, HttpServletResponse response, HttpServletRequest request){
        try {
            Integer userId = (Integer) session.getAttribute("userId");
            if(userId != null) {
                return this.petService.updateData(account, userId,result);
            }
            response.sendError(401);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.FALSE;
    }

}
