package com.bailuyiting.communityparking.service.controller.CommunityParkingOwnerAuth;

import com.bailuyiting.commons.core.entity.communityparking.CommunityParkingOwnerAuth;
import com.bailuyiting.commons.until.DateUtils;
import com.bailuyiting.commons.until.ResultUtils;
import com.bailuyiting.commons.until.SQYRequestUntils;
import com.bailuyiting.communityparking.service.service.CommunityDataBaseService;
import com.bailuyiting.communityparking.service.service.CommunityParkingOwnerAuthService;
import com.bailuyiting.communityparking.service.service.CommunityParkingParkInfoService;
import com.bailuyiting.communityparking.service.service.CommunityParkingUserAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Api(value = "小区停车位出租功能增加-API",description = "小区停车位出租功能增加-API")
@RestController
@RequestMapping(value = "api/communityPark/")
public class OwnerAuthPostController {
    @Autowired
    private CommunityParkingUserAuthService communityParkingUserAuthService;
    @Autowired
    private CommunityDataBaseService communityDataBaseService;
    @Autowired
    private CommunityParkingOwnerAuthService communityParkingOwnerAuthService;
    @Autowired
    private CommunityParkingParkInfoService communityParkingParkInfoService;
    /**
     * 小区业主车位认证
     * @param realName
     * @param communityId
     * @param carNum
     * @param parkNum
     * @param type
     * @param idNum
     * @param idURL
     * @param req
     * @return
     */
    @ApiOperation(value = "小区业主车位认证",httpMethod ="POST",notes = "停车位信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "realName",value = "身份证姓名",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "communityId",value = "小区ID",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "carNum",value = "登记车牌",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "parkNum",value = "车位编号",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "type",value = "证件类型 1:二代身份证 2：户口本 3：房产证",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "idNum",value = "证件号码",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "idURL",value = "证件云服务器URL地址",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "x-token",value = "JWTToken",paramType = "header",dataType = "String",required = true),
    })
    @PostMapping(value = "ownerAuth")
    public Map<String,Object> verifyPackSpace(@RequestParam String realName, @RequestParam String communityId, @RequestParam String carNum, @RequestParam String parkNum,
                                              @RequestParam String type, @RequestParam String idNum, @RequestParam String idURL, HttpServletRequest req) {
        //MVC自动判空
        //验证此车辆是够已经申请过
        CommunityParkingOwnerAuth ownerAuth = this.communityParkingOwnerAuthService.getBaseJapRepository().findByCarNum(carNum);
        if(ownerAuth!=null){
            return ResultUtils.errorByUserDefine("600","此车辆已经申请开通过此功能，请误重新开通");
        }
        //保存车位开通信息
        CommunityParkingOwnerAuth owner = new CommunityParkingOwnerAuth();
        owner.setRealName(realName);//设置身份证姓名
        owner.setCreater(DateUtils.formatNow());//设置日期
        owner.setCarNum(carNum);//设置车牌
        owner.setParkNum(parkNum);//设置停车位编号
        owner.setAccount(SQYRequestUntils.getAccount(req));//设置账户名
        owner.setCertificateNum(idNum);//设置证件号码
        owner.setCertificateURL(idURL);//设置证件URL
        owner.setCertificateType(Integer.getInteger(type));//设置证件类型
        owner.setCommunityId(communityId);//设置小区ID
        owner.setAuthStatus(1);//设置状态  正在审核
        CommunityParkingOwnerAuth save = this.communityParkingOwnerAuthService.getBaseJapRepository().save(owner);
        return ResultUtils.success(save);
    }

}
