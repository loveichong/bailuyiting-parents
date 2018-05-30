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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Map;

@Api(value = "公共停车停车位资源增加-API",description = "公共停车停车位资源增加-API")
@RestController
@RequestMapping(value = "api/publishParking/")
public class PublishParkingParkInfoPostController {
    @Autowired
    private SysUserRolesFeign sysUserRolesFeign;
    @Autowired
    private PublishParkingParkInfoService publishParkingParkInfoService;
    @ApiOperation(value = "保存路边停车，停车位信息",httpMethod ="POST",notes = "停车位信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "longitude",value = "精度",paramType = "query",dataType = "BigDecimal",required = true),
            @ApiImplicitParam(name = "latitude",value = "维度",paramType = "query",dataType = "BigDecimal",required = true),
            @ApiImplicitParam(name = "province",value = "省份",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "city",value = "城市",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "area",value = "区域",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "publishName",value = "停车位名称",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "parkInfo",value = "停车位信息",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "parkNum",value = "停车位数量",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(name = "freeTime",value = "免费时长",paramType = "query",dataType = "BigDecimal",required = true),
            @ApiImplicitParam(name = "discountTime",value = "优惠时长",paramType = "query",dataType = "BigDecimal",required = true),
            @ApiImplicitParam(name = "discountPrice",value = "优惠价格",paramType = "query",dataType = "BigDecimal",required = true),
            @ApiImplicitParam(name = "unitPrice",value = "单价/h",paramType = "query",dataType = "BigDecimal",required = true),
            @ApiImplicitParam(name = "maximumPrice",value = "24h封顶金额",paramType = "query",dataType = "BigDecimal",required = true),
            @ApiImplicitParam(name = "x-token",value = "JWTToken",paramType = "header",dataType = "String",required = true),
    })
    @PostMapping(value = "parkInfo")
    public Map<String,Object> saveParkInfo(@RequestParam BigDecimal latitude, @RequestParam BigDecimal longitude, @RequestParam String province,@RequestParam String publishName,
                                           @RequestParam String city, @RequestParam String area, @RequestParam String parkInfo, @RequestParam Integer parkNum,
                                           @RequestParam BigDecimal freeTime,@RequestParam BigDecimal discountTime,@RequestParam BigDecimal discountPrice,
                                           @RequestParam BigDecimal unitPrice,@RequestParam BigDecimal maximumPrice,HttpServletRequest req) {
        //查找是否具有权限
        String account = SQYRequestUntils.getAccount(req);
        SysUserRoles roles = this.sysUserRolesFeign.findByAccount(account);
        FeignUntils.isSuccess(roles,"sysUserRolesFeign.findByAccount(account)");
        if(roles.getId()==null||roles.getUserRole() != 4){
            return ResultUtils.errorByUserDefine("600","此账户没有录入权限");
        }
        //查找是否已经有此公共停车区域
        PublishParkingParkInfo info = this.publishParkingParkInfoService.getBaseJapRepository().findByPublishName(publishName);
        if (info != null) {
            return ResultUtils.errorByUserDefine("600", "此公共停车区域已被录入");
        }
        //新增公共停车信息
        PublishParkingParkInfo result = new PublishParkingParkInfo();
        result.setCreateTime(DateUtils.formatNow());//创建时间
        result.setCreater(account);//创建人
        result.setArea(area);//区
        result.setProvince(province);//省
        result.setCity(city);//市
        result.setPublishName(publishName);//公共停车名称
        result.setLatitude(latitude);//维度
        result.setLongitude(longitude);//精度
        result.setMaximumPrice(maximumPrice);//封顶价格
        result.setDiscountPrice(discountPrice);//优惠价格
        result.setDiscountTime(discountTime);//优惠时长
        result.setParkNum(parkNum);//停车位数量
        result.setParkResidualNum(parkNum);//剩余停车位数量
        result.setParkInfo(parkInfo);//停车位信息
        result.setFreeTime(freeTime);//免费停车时长
        result.setUnitPrice(unitPrice);//单价
        result.setParkStatus(1);//正常使用
        //保存公共停车信息
        PublishParkingParkInfo save = this.publishParkingParkInfoService.getBaseJapRepository().save(result);
        return ResultUtils.success(save);
    }



    }



