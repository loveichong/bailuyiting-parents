package com.bailuyiting.communityparking.service.controller.CommunityDataBase;

import com.bailuyiting.commons.core.entity.communityparking.CommunityDataBase;
import com.bailuyiting.commons.core.entity.sso.SysUserRoles;
import com.bailuyiting.commons.until.DateUtils;
import com.bailuyiting.commons.until.FeignUntils;
import com.bailuyiting.commons.until.ResultUtils;
import com.bailuyiting.commons.until.SQYRequestUntils;
import com.bailuyiting.communityparking.service.service.CommunityDataBaseService;
import com.bailuyiting.sso.inter.feign.SysUserRolesFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Api(value = "小区信息资源修改-API",description = "小区信息资源修改-API")
@RestController
@RequestMapping(value = "api/community")
public class DataBasePutController {
    @Autowired
    private CommunityDataBaseService communityDataBaseService;
    @Autowired
    private SysUserRolesFeign sysUserRolesFeign;
    @ApiOperation(value = "小区申请开通合作",httpMethod ="POST",notes = "小区申请开通合作")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id",value = "小区Id",paramType = "path",dataType = "String",required = true),
            @ApiImplicitParam(name = "x-token",value = "JWTToken",paramType = "header",dataType = "String",required = true),
    })
    @PutMapping(value = "baseData/cooper/id/{id}")
    public Map<String,Object> forCooperation(@PathVariable(value = "id") String id,HttpServletRequest req) {
        //MVC判空
        //判断是否有权限
        String ac = SQYRequestUntils.getAccount(req);
        SysUserRoles account =sysUserRolesFeign.findByAccount(ac);
        FeignUntils.isSuccess(account,"sysUserRolesFeign.findByAccount(ac)");
        if(account.getId()==null){
            return ResultUtils.errorByUserDefine("600","账户不存在");
        }
        if(account.getUserRole()!=13){
            return ResultUtils.errorByUserDefine("600","账户无权限");
        }
        //判断是否有小区
        CommunityDataBase base = this.communityDataBaseService.getBaseJapRepository().findOne(id);
        if(base==null){
            return ResultUtils.errorByUserDefine("600","无此小区");
        }
        //判断是否已经开通
        if(base.getBaseStatus()==2){
            return ResultUtils.errorByUserDefine("600","已经开通合作，请勿重新开通");
        }
        //开通
        base.setModifyer(ac);//开通人
        base.setModifyTime(DateUtils.formatNow());//时间
        base.setBaseStatus(2);//开通
        this.communityDataBaseService.getBaseJapRepository().save(base);
        return ResultUtils.success("申请合作成功");
    }
}
