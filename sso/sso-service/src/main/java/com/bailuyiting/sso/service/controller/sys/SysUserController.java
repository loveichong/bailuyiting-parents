package com.bailuyiting.sso.service.controller.sys;

import com.bailuyiting.commons.core.entity.communityparking.CommunityParkingParkInfo;
import com.bailuyiting.commons.core.entity.order.ParkingOrderBase;
import com.bailuyiting.commons.core.entity.publishparking.PublishParkingParkInfo;
import com.bailuyiting.commons.core.entity.sso.SysUser;
import com.bailuyiting.commons.core.entity.sso.SysUserRoles;
import com.bailuyiting.commons.until.*;
import com.bailuyiting.communityparking.inter.feign.CommunityParkingParkInfoFeign;
import com.bailuyiting.publishparking.inter.feign.PublishParkingParkInfoFeign;
import com.bailuyiting.sideparking.inter.feign.SideParkingParkInfoFeign;
import com.bailuyiting.sideparking.inter.feign.SideParkingParkSectionFeign;
import com.bailuyiting.sso.service.service.ParkingOrderBaseService;
import com.bailuyiting.sso.service.service.SysUserRolesService;
import com.bailuyiting.sso.service.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Api(value = "系统账户-API",description = "系统账户-API")
@RestController
@RequestMapping(value = "api/account/")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRolesService sysUserRolesService;
    @Autowired
    private ParkingOrderBaseService parkingOrderBaseService;
    @Autowired
    private SideParkingParkInfoFeign sideParkingParkInfoFeign;
    @Autowired
    private SideParkingParkSectionFeign sideParkingParkSectionFeign;
    @Autowired
    private PublishParkingParkInfoFeign publishParkingParkInfoFeign;
    @Autowired
    private CommunityParkingParkInfoFeign communityParkingParkInfoFeign;
    @ApiOperation(value = "获取系统账户信息",httpMethod ="GET",notes = "举报路边违章停车")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "x-token",value = "JWTToken",paramType = "header",dataType = "String",required = true),
    })
    @GetMapping(value = "")
    public Map<String,Object> getAccountInformation( HttpServletRequest req){
        String account = SQYRequestUntils.getAccount(req);
        if(account==null){
            return ResultUtils.errorByUserDefine("600","系统无此账户");
        }
        return ResultUtils.success(sysUserService.getBaseJapRepository().findByAccount(account));
    }
    /**
     *管理员账号生成
     * @param admin
     * @param password
     * @param userRoles
     * @param req
     * @return
     */
    @ApiOperation(value = "管理员账号生成",httpMethod ="POST",notes = "管理员账号生成")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "x-token",value = "JWTToken",paramType = "header",dataType = "String",required = true),
            @ApiImplicitParam(name = "admin",value = "账户名",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "password",value = "密码",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "userRoles",value = "11.露天停车管理人员 12.路边停车管理人员 13.小区停车管理人员",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(name = "parkId",value = "停车场ID绑定账号",paramType = "query",dataType = "String",required = true),
    })
    @Transactional
    @PostMapping(value ="/admin")
    public Map<String,Object> creatAdmin(@RequestParam String admin, @RequestParam String password,@RequestParam String parkId,
                                         @RequestParam Integer userRoles, HttpServletRequest req) {
        //判断是否具有管理员权限
        String ac = SQYRequestUntils.getAccount(req);
        SysUserRoles account =this.sysUserRolesService.getBaseJapRepository().findBySysAccount(ac);
        if(account==null||account.getUserRole()!=4){
            return ResultUtils.errorByUserDefine("600","账户无权限");
        }
        //查看账户是否重复
        SysUser user1 = this.sysUserService.getBaseJapRepository().findByAccount(admin);
        if(user1!=null){
            return ResultUtils.errorByUserDefine("600","账户重复，请更账户名");
        }
        //生成账户密码和管理员名单
        SysUser user = new SysUser();
        user.setCreateTime(DateUtils.formatNow());//生成时间
        user.setState(userRoles);//管理员账户
        user.setCreater(ac);//创建者
        user.setAccount(admin);//账户
        user.setPasswd(password);//密码
        SysUser save = this.sysUserService.getBaseJapRepository().save(user);
        //生成账户密码
        SysUserRoles roles = new SysUserRoles();
        roles.setCreateTime(DateUtils.formatNow());
        roles.setUserId(save.getId());
        roles.setUserRole(userRoles);
        roles.setParkId(parkId);
        roles.setSysAccount(user.getAccount());
        this.sysUserRolesService.getBaseJapRepository().save(roles);
        return ResultUtils.success();
    }

    /**
     * 停车场管理员获取停车场信息
     * @param req
     * @return
     */
    @ApiOperation(value = "停车场管理员获取停车场信息",httpMethod ="GET",notes = "管理员账号生成")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "x-token",value = "JWTToken",paramType = "header",dataType = "String",required = true),
    })
    @GetMapping(value ="/admin/park")
    public Map<String,Object> getParkInfoByAdmin( HttpServletRequest req) {
        String ac = SQYRequestUntils.getAccount(req);
        SysUserRoles account = this.sysUserRolesService.getBaseJapRepository().findBySysAccount(ac);
        if(account==null||account.getParkId()==null){
            return ResultUtils.errorByUserDefine("600","账户不是管理员");
        }
        switch (account.getUserRole()){
            case 11:
                PublishParkingParkInfo ppark = this.publishParkingParkInfoFeign.findParkInfoByID(account.getParkId());
                FeignUntils.isSuccess(ppark,"this.publishParkingParkInfoFeign.findParkInfoByID(account.getParkId())");
                return ResultUtils.success(ppark);
            case 12://目前没有做路边停车
                return ResultUtils.errorByUserDefine("600","路边停车不需要管理员");
            case 13:
                CommunityParkingParkInfo cpark = this.communityParkingParkInfoFeign.findParkInfoByID(account.getParkId());
                FeignUntils.isSuccess(cpark,"this.communityParkingParkInfoFeign.findParkInfoByID(account.getParkId())");
                return ResultUtils.success(cpark);
        }
        return ResultUtils.errorByUserDefine("600","账户不是小区管理员");
    }

    /**
     * 停车场管理员通过车牌号获取车辆信息
     * @param carNum
     * @param req
     * @return
     */
    @ApiOperation(value = "停车场管理员通过车牌号获取车辆信息",httpMethod ="GET",notes = "管理员账号生成")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "carNum",value = "车牌号",paramType = "path",dataType = "String",required = true),
            @ApiImplicitParam(name = "x-token",value = "JWTToken",paramType = "header",dataType = "String",required = true),
    })
    @GetMapping(value ="/admin/park/car/{carNum}")
    public Map<String,Object> getParkInfoByCar(@PathVariable(value = "carNum") String carNum,HttpServletRequest req) {
        String ac = SQYRequestUntils.getAccount(req);
        SysUserRoles account = this.sysUserRolesService.getBaseJapRepository().findBySysAccount(ac);
        if(account==null||account.getParkId()==null){
            return ResultUtils.errorByUserDefine("600","账户不是管理员");
        }
        List<ParkingOrderBase> result = this.parkingOrderBaseService.getBaseJapRepository().findByCarNumberAndParkId(carNum, account.getParkId());
        return ResultUtils.success(result);
    }
}
