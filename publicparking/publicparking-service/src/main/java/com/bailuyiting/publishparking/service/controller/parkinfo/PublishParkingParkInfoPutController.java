package com.bailuyiting.publishparking.service.controller.parkinfo;

import com.bailuyiting.commons.core.entity.publishparking.PublishParkingParkInfo;
import com.bailuyiting.commons.core.entity.sso.SysUserRoles;
import com.bailuyiting.commons.until.DateUtils;
import com.bailuyiting.commons.until.FeignUntils;
import com.bailuyiting.commons.until.ResultUtils;
import com.bailuyiting.commons.until.SQYRequestUntils;
import com.bailuyiting.publishparking.service.service.PublishParkingParkInfoService;
import com.bailuyiting.sso.inter.feign.SysUserRolesFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Api(value = "公共停车停车位资源修改-API",description = "公共停车停车位资源修改-API")
@RestController
@RequestMapping(value = "api/publishParking/")
public class PublishParkingParkInfoPutController {
    @Autowired
    private PublishParkingParkInfoService publishParkingParkInfoService;
    @Autowired
    private SysUserRolesFeign sysUserRolesFeign;
    @ApiOperation(value = "公共停车场申请合作",httpMethod ="PUT")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id",value = "公共停车场ID",paramType = "query",dataType = "BigDecimal",required = true),
    })
    @PutMapping(value = "cooper/id/{id}")
    public Map<String,Object> cooperById(@PathVariable(value = "id") String id,HttpServletRequest req) {
        String ac = SQYRequestUntils.getAccount(req);
        SysUserRoles account = this.sysUserRolesFeign.findByAccount(ac);
        FeignUntils.isSuccess(account,"sysUserRolesFeign.findByAccount(ac)");
        if(account.getId()==null){
            return ResultUtils.errorByUserDefine("600","无此账户");
        }
        //是否有权限
        if(account.getUserRole()!=11&&account.getUserRole()!=4){
            return ResultUtils.errorByUserDefine("600","账户无权限");
        }
        //小区是否存在
        PublishParkingParkInfo parkInfo = this.publishParkingParkInfoService.getBaseJapRepository().findOne(id);
        if(parkInfo==null){
            return ResultUtils.errorByUserDefine("600","无此停车场");
        }
        if(parkInfo.getParkStatus()==2){
            return ResultUtils.errorByUserDefine("600","此停车场已经参与合作");
        }
        parkInfo.setParkStatus(2);//参与合作
        parkInfo.setModifyer(ac);//审核人
        parkInfo.setModifyTime(DateUtils.formatNow());//修改时间
        //保存
        this.publishParkingParkInfoService.getBaseJapRepository().save(parkInfo);
        return ResultUtils.success();
    }
}
