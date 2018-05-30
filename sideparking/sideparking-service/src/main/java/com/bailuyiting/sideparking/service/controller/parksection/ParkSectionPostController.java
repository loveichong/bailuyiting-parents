package com.bailuyiting.sideparking.service.controller.parksection;

import com.bailuyiting.commons.until.FeignUntils;
import com.bailuyiting.sideparking.service.service.SideParkingParkInfoService;
import com.bailuyiting.sideparking.service.service.SideParkingParkingSectionService;
import com.bailuyiting.commons.core.entity.sideparking.SideParkingParkInfo;
import com.bailuyiting.commons.core.entity.sideparking.SideParkingParkSection;
import com.bailuyiting.commons.core.entity.sso.SysUserRoles;
import com.bailuyiting.commons.until.DateUtils;
import com.bailuyiting.commons.until.ResultUtils;
import com.bailuyiting.commons.until.SQYRequestUntils;
import com.bailuyiting.sso.inter.feign.SysUserRolesFeign;
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

@Api(value = "路边停车停车位资源增加-API",description = "路边停车停车位资源增加-API")
@RestController
@RequestMapping(value = "api/")
public class ParkSectionPostController {
    @Autowired
    private SysUserRolesFeign sysUserRolesFeign;

    @Autowired
    private SideParkingParkInfoService sideParkingParkInfoService;

    @Autowired
    private SideParkingParkingSectionService sideParkingParkingSectionService;

    @ApiOperation(value = "保存路边停车，停车位信息",httpMethod ="POST",notes = "停车位信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "longitude",value = "精度",paramType = "query",dataType = "BigDecimal",required = true),
            @ApiImplicitParam(name = "latitude",value = "维度",paramType = "query",dataType = "BigDecimal",required = true),
            @ApiImplicitParam(name = "province",value = "省份",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "city",value = "城市",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "area",value = "区域",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "street",value = "街道",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "section",value = "段落",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "parkInfo",value = "停车位信息",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "price",value = "价格",paramType = "query",dataType = "BigDecimal",required = true),
            @ApiImplicitParam(name = "parkNum",value = "停车位编号",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "x-token",value = "JWTToken",paramType = "header",dataType = "String",required = true),
    })
    @PostMapping(value = "parkInfo")
    public Map<String,Object> saveParkInfo(@RequestParam BigDecimal latitude, @RequestParam BigDecimal longitude, @RequestParam String province,
                                              @RequestParam String city, @RequestParam String area, @RequestParam String street, @RequestParam String section,
                                              @RequestParam String parkInfo, @RequestParam BigDecimal price, @RequestParam String parkNum, HttpServletRequest req){
        //查找是否具有权限
        String account = SQYRequestUntils.getAccount(req);
        SysUserRoles role = this.sysUserRolesFeign.findByAccount(account);
        FeignUntils.isSuccess(role,"sysUserRolesFeign.findByAccount(account)");
        if(role.getId()==null||role.getUserRole()!=12){
            return ResultUtils.errorByUserDefine("600","此账户无权限");
        }
        //检查是否有此车位编号
        SideParkingParkInfo byNum = this.sideParkingParkInfoService.getBaseJapRepository().findByParkNum(parkNum);
        if (byNum!=null){
            return ResultUtils.errorByUserDefine("600","此停车位编号已经存在");
        }
        //查找是否有此城市此区域此街道（效率低）
        SideParkingParkSection s1 = this.sideParkingParkingSectionService.getBaseJapRepository().findByProvinceAndCityAndAreaAndStreetAndSection(province, city, area, street, section);
        if(s1==null){
            //无街道直接新建区域信息（包含街道和段落）
            //添加街道经纬度
            SideParkingParkSection sec = new SideParkingParkSection();
            sec.setProvince(province);
            sec.setArea(area);
            sec.setCity(city);
            sec.setStreet(street);
            sec.setCreateTime(DateUtils.formatNow());
            sec.setSection(section);
            sec.setLatitude(latitude);
            sec.setLongitude(longitude);
            sec.setCreater(account);//设置创建人
            sec.setPrice(price);
            s1=this.sideParkingParkingSectionService.getBaseJapRepository().save(sec);
        }
        //保存车位信息
        SideParkingParkInfo base = new SideParkingParkInfo();
        base.setCreateTime(DateUtils.formatNow());
        base.setLatitude(latitude);
        base.setLongitude(longitude);
        base.setSectionId(s1.getId());
        base.setParkInfo(parkInfo);
        base.setPrice(price);
        base.setParkStatus(2);
        base.setParkNum(parkNum);
        base.setCreater(account);//设置创建人
        //保存base并把停车位数量加一
        boolean success = this.sideParkingParkInfoService.saveInfoAndSectionNumAddOne(base, s1.getId());
        if(!success){
            return ResultUtils.error500();
        }
        return ResultUtils.success();
    }
}
