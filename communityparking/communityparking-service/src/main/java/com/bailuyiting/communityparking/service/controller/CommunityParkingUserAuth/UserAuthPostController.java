package com.bailuyiting.communityparking.service.controller.CommunityParkingUserAuth;

import com.bailuyiting.commons.core.entity.communityparking.CommunityParkingUserAuth;
import com.bailuyiting.commons.core.entity.module.sms.SMSVerifyCode;
import com.bailuyiting.commons.until.DateUtils;
import com.bailuyiting.commons.until.ResultUtils;
import com.bailuyiting.commons.until.SQYRequestUntils;
import com.bailuyiting.communityparking.service.service.CommunityDataBaseService;
import com.bailuyiting.communityparking.service.service.CommunityParkingOwnerAuthService;
import com.bailuyiting.communityparking.service.service.CommunityParkingParkInfoService;
import com.bailuyiting.communityparking.service.service.CommunityParkingUserAuthService;
import com.bailuyiting.module.sms.service.SMSVerifyCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
@Api(value = "小区停车位功能增加-API",description = "小区停车位功能增加-API")
@RestController
@RequestMapping(value = "api/communityPark/")
public class UserAuthPostController {
    @Autowired
    private CommunityParkingUserAuthService communityParkingUserAuthService;
    @Autowired
    private CommunityDataBaseService communityDataBaseService;
    @Autowired
    private CommunityParkingOwnerAuthService communityParkingOwnerAuthService;
    @Autowired
    private CommunityParkingParkInfoService communityParkingParkInfoService;
    @Autowired
    private SMSVerifyCodeService smsVerifyCodeService;
    /**
     * 业主开通小区停车功能
     * @param realName
     * @param communityId
     * @param type
     * @param idNum
     * @param idURL
     * @param req
     * @return
     */
    @ApiOperation(value = "业主开通小区停车功能",httpMethod ="POST",notes = "停车位信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "realName",value = "身份证姓名",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "communityId",value = "小区ID",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "type",value = "证件类型 1:二代身份证 2：户口本 3：房产证",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "idNum",value = "证件号码",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "idURL",value = "证件云服务器URL地址",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "x-token",value = "JWTToken",paramType = "header",dataType = "String",required = true),
    })
    @PostMapping(value = "userOauth/owner")
    public Map<String,Object> verifyUseByOwner(@RequestParam String realName, @RequestParam String communityId, @RequestParam String type,
                                               @RequestParam String idNum, @RequestParam String idURL, HttpServletRequest req) {
        //MVC自动判空
        //验证此姓名是否已经被使用
        if(this.communityParkingUserAuthService.getBaseJapRepository().findByRealName(realName)!=null){
            return ResultUtils.errorByUserDefine("600","此姓名已经申请开通过小区停车功能");
        }
        //验证此证件号码是否已存在
        if(this.communityParkingUserAuthService.getBaseJapRepository().findByCertificateNum(idNum)!=null){
            return ResultUtils.errorByUserDefine("500","此证件号已经申请开通过小区停车功能");
        }
        //保存开通信息
        CommunityParkingUserAuth auth = new CommunityParkingUserAuth();
        auth.setCreateTime(DateUtils.formatNow());//设置创建时间
        auth.setUserType(1);//使用者类型
        auth.setCertificateNum(idNum);//证件号码
        auth.setCertificateURL(idURL);//图片地址
        auth.setCommunityId(communityId);//小区ID
        auth.setRealName(realName);//身份证姓名
        auth.setAccount(SQYRequestUntils.getAccount(req));//设置使用者账户
        auth.setAuthStatus(1);//申请审核状态
        CommunityParkingUserAuth save = this.communityParkingUserAuthService.getBaseJapRepository().save(auth);
        return ResultUtils.success(save);
    }

    /**
     * 小区租户开通小区停车功能
     * @param realName
     * @param tel
     * @param ownerName
     * @param ownerTel
     * @param vertifyCore
     * @param communityId
     * @param req
     * @return
     */
    @ApiOperation(value = "小区租户开通小区停车功能",httpMethod ="POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "realName",value = "姓名",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "tel",value = "电话号码",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "ownerName",value = "业主姓名",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "ownerTel",value = "业主电话号码",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "vertifyCore",value = "短信验证码",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "communityId",value = "小区ID",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "x-token",value = "JWTToken",paramType = "header",dataType = "String",required = true),
    })
    @PostMapping(value = "userOauth/user")
    public Map<String,Object> verifyUseByUser(@RequestParam String realName,@RequestParam String tel,@RequestParam String ownerName,
                                              @RequestParam String ownerTel,@RequestParam String vertifyCore,@RequestParam String communityId,HttpServletRequest req) {
        //MVC判空
        //判断验证码是否正确
        //判断验证码是否有效(是否存在，是否匹配手机号，是否过期)
        SMSVerifyCode vertify = this.smsVerifyCodeService.getBaseJapRepository().findByPhone(ownerTel);
        //是否存在
        if(vertify==null){
            return ResultUtils.errorByUserDefine("600","此验证码无效");
        }
        //是否匹配验证码
        if(!StringUtils.equals(vertifyCore,vertify.getVerifyCode())){
            return ResultUtils.errorByUserDefine("600","此验证码无效");
        }
        //判断是否过期
        if(DateUtils.totDate(vertify.getExpireTime()).compareTo(new Date())<=0){
            return ResultUtils.errorByUserDefine("600","此验证码已过期，请重新申请");
        }
        //判断此业主是否已经开通小区功能
        CommunityParkingUserAuth au = this.communityParkingUserAuthService.getBaseJapRepository().findByRealName(ownerName);
        if(au==null){
            return ResultUtils.error500("开通失败，此业主不存在，请填写真实姓名");
        }
        if(au.getAuthStatus()!=3){
            return ResultUtils.error500("开通失败，此业主还未开通此项功能");
        }
        //保存开通信息
        CommunityParkingUserAuth auth = new CommunityParkingUserAuth();
        auth.setCreateTime(DateUtils.formatNow());//设置创建时间
        auth.setUserType(2);//使用者类型，租户
        auth.setCommunityId(communityId);//小区ID
        auth.setRealName(realName);//身份证姓名
        auth.setAccount(SQYRequestUntils.getAccount(req));//设置使用者账户
        auth.setAuthStatus(1);//申请审核状态
        CommunityParkingUserAuth save = this.communityParkingUserAuthService.getBaseJapRepository().save(auth);
        return ResultUtils.success(save);
    }
}
