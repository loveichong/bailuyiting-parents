package com.bailuyiting.communityparking.service.controller.CommunityParkingUserAuth;

import com.bailuyiting.commons.core.entity.communityparking.CommunityParkingUserAuth;
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
@Api(value = "小区停车位功能更新-API",description = "小区停车位功能更新-API")
@RestController
@RequestMapping(value = "api/communityPark/")
public class UserAuthPutController {
    @Autowired
    private CommunityParkingUserAuthService communityParkingUserAuthService;
    @Autowired
    private CommunityDataBaseService communityDataBaseService;
    @Autowired
    private CommunityParkingOwnerAuthService communityParkingOwnerAuthService;
    @Autowired
    private CommunityParkingParkInfoService communityParkingParkInfoService;
    /**
     * 后台确认小区开通认证通过
     * @param req
     * @return
     */
    @ApiOperation(value = "后台确认小区开通认证通过",httpMethod ="PUT",notes = "停车位信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "x-token",value = "JWTToken",paramType = "header",dataType = "String",required = true),
    })
    @PutMapping(value = "userOuth")
    public Map<String,Object> confirmPackUser(HttpServletRequest req){
        //MVC自动判空
        //确实是否有此车位认证

        CommunityParkingUserAuth user = this.communityParkingUserAuthService.getBaseJapRepository().findByAccount(SQYRequestUntils.getAccount(req));
        if(user==null){
            return ResultUtils.error404();
        }
        switch (user.getAuthStatus()){//s身份验证状态 0：未开启  1：申请审核  2：审核失败  3：审核通过 4.功能冻结
            case 0:return ResultUtils.errorByUserDefine("600","未开启认证");
            case 2:return ResultUtils.errorByUserDefine("600","审核失败");
            case 3:return ResultUtils.errorByUserDefine("600","已审核通过，请问重新审核");
            case 4:return ResultUtils.errorByUserDefine("600","功能冻结");
        }
        //认证通过
        user.setAuthStatus(3);
        CommunityParkingUserAuth save = this.communityParkingUserAuthService.getBaseJapRepository().save(user);
        return ResultUtils.success(save);
    }

}
