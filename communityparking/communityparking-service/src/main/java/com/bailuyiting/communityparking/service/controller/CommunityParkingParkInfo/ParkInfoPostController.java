package com.bailuyiting.communityparking.service.controller.CommunityParkingParkInfo;

import com.bailuyiting.commons.core.entity.communityparking.CommunityParkingParkInfo;
import com.bailuyiting.commons.until.DateUtils;
import com.bailuyiting.commons.until.ResultUtils;
import com.bailuyiting.communityparking.service.service.CommunityDataBaseService;
import com.bailuyiting.communityparking.service.service.CommunityParkingOwnerAuthService;
import com.bailuyiting.communityparking.service.service.CommunityParkingParkInfoService;
import com.bailuyiting.communityparking.service.service.CommunityParkingUserAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Map;

@Api(value = "小区停车位资源增加-API",description = "小区停车位资源增加-API")
@RestController
@RequestMapping(value = "api/communityPark/")
public class ParkInfoPostController {
    @Autowired
    private CommunityParkingUserAuthService communityParkingUserAuthService;
    @Autowired
    private CommunityDataBaseService communityDataBaseService;
    @Autowired
    private CommunityParkingOwnerAuthService communityParkingOwnerAuthService;
    @Autowired
    private CommunityParkingParkInfoService communityParkingParkInfoService;
    /**
     * 小区业主车位出租发布
     * @param parkId
     * @param startTime
     * @param finishTime
     * @param price
     * @param req
     * @return
     */
    @ApiOperation(value = "小区业主车位出租发布",httpMethod ="POST",notes = "停车位信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "parkId",value = "车位ID",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "startTime",value = "起始时间",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "finishTime",value = "结束时间",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "price",value = "价格",paramType = "query",dataType = "BigDecimal",required = true),
            @ApiImplicitParam(name = "parkInfo",value = "停车位说明",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "x-token",value = "JWTToken",paramType = "header",dataType = "String",required = true),
    })
    @PostMapping(value = "parkInfo")
    public Map<String,Object> publishpackspace(@RequestParam String parkId, @RequestParam String startTime, @RequestParam String finishTime,
                                               @RequestParam BigDecimal price, @RequestParam String parkInfo, HttpServletRequest req) {
        //MVC自动判空
        //判断此车位是否存在
        CommunityParkingParkInfo base = this.communityParkingParkInfoService.getBaseJapRepository().findOne(parkId);
        if(base==null){
            return ResultUtils.errorByUserDefine("600","无此停车位");
        }
        //判断此停车位是否可以发布
        if(base.getParkStatus()==1){
            return ResultUtils.errorByUserDefine("600","此车位正在被使用,不能重新发布");
        }
        //保存发布信息
        base.setModifyTime(DateUtils.formatNow());//设置发布时间
        base.setParkStatus(2);//设置状态已发布未使用
        base.setStartTime(startTime);//设置起始时间
        base.setFinishTime(finishTime);//设置结束时间
        base.setPrice(price);//设置价格
        base.setParkInfo(parkInfo);//设置说明
        CommunityParkingParkInfo save = this.communityParkingParkInfoService.getBaseJapRepository().save(base);
        return ResultUtils.success(save);
    }

}
