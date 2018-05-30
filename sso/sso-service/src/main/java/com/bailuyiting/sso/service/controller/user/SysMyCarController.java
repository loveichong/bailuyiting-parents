package com.bailuyiting.sso.service.controller.user;

import com.bailuyiting.commons.core.entity.sso.SysMyCar;
import com.bailuyiting.commons.until.DateUtils;
import com.bailuyiting.commons.until.ResultUtils;
import com.bailuyiting.commons.until.SQYRequestUntils;
import com.bailuyiting.sso.service.service.SysMyCarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Api(value = "我的爱车-API",description = "我的爱车-API")
@RestController
@RequestMapping(value = "api/car")
public class SysMyCarController {

    @Autowired
    private SysMyCarService sysMyCarService;

    /**
     * 添加我的爱车
     * @param carNum
     * @param req
     * @return
     */
    @ApiOperation(value = "添加我的爱车",httpMethod ="POST",notes = "添加我的爱车")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "carNum",value = "车牌号",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "x-token",value = "JWTToken",paramType = "header",dataType = "String",required = true),
    })
    @PostMapping(value ="/carNum")
    public Map<String,Object> addMycar(@RequestParam String carNum,HttpServletRequest req) {
        //MVC判空
        //查找车牌号是否重复
        String account = SQYRequestUntils.getAccount(req);
        List<SysMyCar> myCars = this.sysMyCarService.getBaseJapRepository().findBySysAccount(account);
        //判断是否重复
        if(!myCars.isEmpty()){
            for(SysMyCar car:myCars){
                if(StringUtils.equals(carNum,car.getCarNum())){
                    return ResultUtils.errorByUserDefine("600","此车牌号您已经添加过，请勿重复添加");
                }
            }
        }
        //添加车牌号
        SysMyCar car = new SysMyCar();
        car.setCreateTime(DateUtils.formatNow());//创建时间
        car.setSysAccount(account);//账户名
        car.setCarNum(carNum);//爱车车牌
        car.setCarStatus(1);//还未使用
        this.sysMyCarService.getBaseJapRepository().save(car);
        return ResultUtils.success();
    }
    /**
     * 获取我的所有爱车
     * @param page
     * @param size
     * @param req
     * @return
     */
    @ApiOperation(value = "获取我的所有爱车",httpMethod ="GET",notes = "添加我的爱车")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "page",value = "页数,第一页是0",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(name = "size",value = "行数",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(name = "x-token",value = "JWTToken",paramType = "header",dataType = "String",required = true),
    })
    @GetMapping(value ="/cars")
    public Map<String,Object> getMyCars(@RequestParam Integer page,@RequestParam Integer size,HttpServletRequest req){
        //MVC判空
        String account = SQYRequestUntils.getAccount(req);
        List<SysMyCar> cars = this.sysMyCarService.getBaseJapRepository().findBySysAccount(account, new PageRequest(page, size));
        if(cars.isEmpty()){
            ResultUtils.success();
        }
        return ResultUtils.success(cars);
    }
    /**
     * 根据车辆ID获取爱车信息
     * @param id
     * @param req
     * @return
     */
    @ApiOperation(value = "根据车辆ID获取爱车信息",httpMethod ="GET",notes = "添加我的爱车")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id",value = "车辆Id",paramType = "path",dataType = "String",required = true),
            @ApiImplicitParam(name = "x-token",value = "JWTToken",paramType = "header",dataType = "String",required = true),
    })
    @GetMapping(value ="/id/{id}")
    public Map<String,Object> getMyCars(@PathVariable(value ="id")String id,HttpServletRequest req){
        //MVC判空
        SysMyCar car = this.sysMyCarService.getBaseJapRepository().findOne(id);
        if(car==null){
            return ResultUtils.errorByUserDefine("600","此车辆Id有误");
        }
        return ResultUtils.success(car);
    }

    /**
     * 根据车辆ID删除爱车信息
     * @param id
     * @param req
     * @return
     */
    @ApiOperation(value = "根据车辆ID删除爱车信息",httpMethod ="GET",notes = "添加我的爱车")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id",value = "车辆Id",paramType = "path",dataType = "String",required = true),
            @ApiImplicitParam(name = "x-token",value = "JWTToken",paramType = "header",dataType = "String",required = true),
    })
    @DeleteMapping (value ="/id/{id}")
    public Map<String,Object> deleteMyCars(@PathVariable(value ="id")String id,HttpServletRequest req){
        SysMyCar myCar = this.sysMyCarService.getBaseJapRepository().findOne(id);
        if(myCar==null){
            return ResultUtils.errorByUserDefine("600","此车辆不存在，不能删除");
        }
        //查看是否正在使用车位
        if(myCar.getCarStatus()==2){
            return ResultUtils.errorByUserDefine("600","此车辆有订单，不能删除，请先支付订单");
        }
        this.sysMyCarService.getBaseJapRepository().delete(id);
        return ResultUtils.success();
    }
}
