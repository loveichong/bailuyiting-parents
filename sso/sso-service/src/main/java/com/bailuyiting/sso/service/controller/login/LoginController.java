package com.bailuyiting.sso.service.controller.login;

import com.alibaba.fastjson.JSONObject;
import com.bailuyiting.commons.core.constants.LoginConstans;
import com.bailuyiting.commons.core.entity.module.sms.SMSVerifyCode;
import com.bailuyiting.commons.core.entity.sso.SysLoginLog;
import com.bailuyiting.commons.core.entity.sso.SysUser;
import com.bailuyiting.commons.core.property.OAuth2Property;
import com.bailuyiting.commons.until.DateUtils;
import com.bailuyiting.commons.until.HttpsUtils;
import com.bailuyiting.commons.until.ResultUtils;
import com.bailuyiting.commons.until.SQYRequestUntils;
import com.bailuyiting.module.sms.service.SMSVerifyCodeService;
import com.bailuyiting.sso.service.service.SysLoginLogService;
import com.bailuyiting.sso.service.service.SysUserService;
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
import java.util.HashMap;
import java.util.Map;

@Api(value = "账户登入-API",description = "账户登入-API")
@RestController
@RequestMapping(value = "api/login/")
public class LoginController {


    @Autowired
    private SMSVerifyCodeService smsVerifyCodeService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysLoginLogService sysLoginLogService;
    /**
     * 手机验证码登入
     * @param vertifyCore
     * @param tel
     * @return
     */
    @ApiOperation(value = "手机验证码登入",httpMethod ="POST",notes = "停车位信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "vertifyCore",value = "短信验证码",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "tel",value = "手机号",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "userRoles",value = "账户类型，普通用户填1",paramType = "query",dataType = "String",required = true),
    })
    @PostMapping(value ="/tel/vertify")
    public Map<String,Object> loginByTel(@RequestParam String vertifyCore, @RequestParam String tel,
                                         @RequestParam Integer userRoles, HttpServletRequest req) {
         //MVC判空
        //判断验证码是否正确
        //判断验证码是否有效(是否存在，是否匹配手机号，是否过期)
        SMSVerifyCode vertify = this.smsVerifyCodeService.getBaseJapRepository().findByPhone(tel);
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
        //查询是否注册
        SysUser account = this.sysUserService.getBaseJapRepository().findByAccount(tel);
        //没注册直接注册
        if(account==null){
            SysUser user = new SysUser();
            user.setCreateTime(DateUtils.formatNow());
            user.setCreater("SQY");
            user.setAccount(tel);
            user.setSysAccount(tel);//系统账户
            user.setPasswd(tel);
            user.setState(1);//启用
            this.sysUserService.saveUserAndRoles(user,userRoles);
        }
        //申请TOKEN并处理结果
        return this.applyTokenAndSaveLogin(tel,req);
    }
    @ApiOperation(value = "管理员账户密码登入",httpMethod ="POST",notes = "管理员账户密码登入")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "admin",value = "账户名",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "password",value = "密码",paramType = "query",dataType = "String",required = true),
    })
    @PostMapping(value ="/password")
    public Map<String,Object> loginByPassword(@RequestParam String admin, @RequestParam String password, HttpServletRequest req) {
        //MVC判空
        //查询是否注册
        SysUser account = this.sysUserService.getBaseJapRepository().findByAccount(admin);
        if(account==null||account.getPasswd()==null){
            return ResultUtils.errorByUserDefine("600","账户密码无效");
        }
        if(!StringUtils.equals(password,account.getPasswd())){
            return ResultUtils.errorByUserDefine("600","账户密码无效");
        }
        //申请TOKEN并处理结果
        return this.applyTokenAndSaveLogin(admin,req);
    }
    public Map<String,Object> applyTokenAndSaveLogin(String account, HttpServletRequest req){
        //登入日志 不管成功或者失败
        SysLoginLog loginLog = new SysLoginLog();
        loginLog.setCreateTime(DateUtils.formatNow());
        loginLog.setIp(SQYRequestUntils.getIpAddress(req));
        loginLog.setAccount(account);
        //申请Token
        HashMap<String, Object> map = new HashMap<>();
        map.put("grant_type","password");
        map.put("password",OAuth2Property.userDetailPassword);
        map.put("username",account);
        map.put("client_id",OAuth2Property.cliendID);
        JSONObject token=null;
        try {
            token = HttpsUtils.doPost("http://localhost/sso/oauth/token", map);//申请Token-
            //保存成功登入日志
            loginLog.setLoginStatus(1);
            loginLog.setMessage(LoginConstans.TOKENSUCCESS);
            this.sysLoginLogService.getBaseJapRepository().save(loginLog);
            //返回Token
            return ResultUtils.success(token);
        }
        catch (Exception e){
            e.getMessage();
            loginLog.setLoginStatus(2);
            loginLog.setMessage(e.getMessage());
            this.sysLoginLogService.getBaseJapRepository().save(loginLog);
            return ResultUtils.errorByUserDefine("600","登入失败");
        }
    }
}
