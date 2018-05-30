package com.bailuyiting.sso.service.controller.order;

import com.bailuyiting.commons.core.entity.communityparking.CommunityParkingParkInfo;
import com.bailuyiting.commons.core.entity.order.ParkingOrderBase;
import com.bailuyiting.commons.core.entity.publishparking.PublishParkingParkInfo;
import com.bailuyiting.commons.core.entity.sideparking.SideParkingParkInfo;
import com.bailuyiting.commons.core.entity.sideparking.SideParkingParkSection;
import com.bailuyiting.commons.core.entity.sso.SysMyCar;
import com.bailuyiting.commons.core.entity.sso.SysUser;
import com.bailuyiting.commons.until.DateUtils;
import com.bailuyiting.commons.until.FeignUntils;
import com.bailuyiting.commons.until.ResultUtils;
import com.bailuyiting.commons.until.SQYRequestUntils;
import com.bailuyiting.communityparking.inter.feign.CommunityParkingParkInfoFeign;
import com.bailuyiting.publishparking.inter.feign.PublishParkingParkInfoFeign;
import com.bailuyiting.sideparking.inter.feign.SideParkingParkInfoFeign;
import com.bailuyiting.sideparking.inter.feign.SideParkingParkSectionFeign;
import com.bailuyiting.sso.service.service.ParkingOrderBaseService;
import com.bailuyiting.sso.service.service.SysMyCarService;
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
import java.util.Map;

@Api(value = "订单资源增加-API",description = "订单资源增加-API")
@RestController
@RequestMapping(value = "api/order/")
public class OrderPostController {
    @Autowired
    private SysMyCarService sysMyCarService;
    @Autowired
    private ParkingOrderBaseService parkingOrderBaseService;
    @Autowired
    private SideParkingParkInfoFeign sideParkingParkInfoFeign;
    @Autowired
    private SideParkingParkSectionFeign sideParkingParkSectionFeign;
    @Autowired
    private CommunityParkingParkInfoFeign communityParkingParkInfoFeign;
    @Autowired
    private PublishParkingParkInfoFeign publishParkingParkInfoFeign;
    @Autowired
    private SysUserService sysUserService;
    /**
     *路边停车根据车辆Id和车位Id抢车位
     * @param carId
     * @param parkInfoId
     * @param req
     * @return
     */
    @ApiOperation(value = "路边停车根据车辆Id和车位Id抢车位",httpMethod ="POST",notes = "停车位信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "carId",value = "车辆Id",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "parkInfoId",value = "路边停车位Id",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "x-token",value = "JWTToken",paramType = "header",dataType = "String",required = true),
    })
    @PostMapping(value = "sidePark/carId/parkId")
    public Map<String,Object> UseSideParkingById(@RequestParam String carId,@RequestParam String parkInfoId,HttpServletRequest req){
        //MVC自动判空
        String account = SQYRequestUntils.getAccount(req);
        //查看是否有此车辆
        SysMyCar car = this.sysMyCarService.getBaseJapRepository().findOne(carId);
        if(car==null){
            return ResultUtils.errorByUserDefine("600","此车辆不存在");
        }
        //判断账户
        SysUser ac = this.sysUserService.getBaseJapRepository().findByAccount(SQYRequestUntils.getAccount(req));
        if(account==null){
            return ResultUtils.errorByUserDefine("600","此账户不存在");
        }
        if(!StringUtils.equals(account,car.getSysAccount())){
            return ResultUtils.errorByUserDefine("600","此车辆所有人不是你本人，不能抢车位");
        }
        //判断是否处于订单状态
        if(ac.getState()==4){
            return ResultUtils.errorByUserDefine("600","您正在使用别的停车位，不能再次申请停车");
        }
        //查找是否有此车位
        SideParkingParkInfo base = this.sideParkingParkInfoFeign.findParkInfoByID(parkInfoId);
        if(!base.getFeignSuccess()){
            return ResultUtils.errorByUserDefine("600","feign/parkinfo/id/{id}出错");
        }
        if(base.getId()==null){
            return ResultUtils.errorByUserDefine("600","此停车位无效，请检查");
        }
        //判断此车位是否可以抢
        if(base.getParkStatus()!=2){
            return ResultUtils.errorByUserDefine("500","此停车位不能预约");
        }
        //查找停车位信息
        SideParkingParkSection section = this.sideParkingParkSectionFeign.findParkSectionByID(base.getSectionId());
        if(!section.getFeignSuccess()){
            return ResultUtils.errorByUserDefine("600","feign/parksection/id/{id}出错");
        }
        if(section==null){
            return ResultUtils.errorByUserDefine("500","此停车位无效，请检查");
        }
        //生成具体订单
        ParkingOrderBase info = new ParkingOrderBase();
        info.setCreateTime(DateUtils.formatNow());//设置创建时间
        info.setOrderTime(DateUtils.formatNow());//下单时间
        info.setSysAccount(account);//设置使用者账户
        info.setOrderType(2);//路边停车
        info.setAddress(section.getAddress());//详细地址
        info.setCarNumber(car.getCarNum());//车牌号
        info.setParkId(parkInfoId);//设置停车位ID
        info.setOrderStatus(1);//已预约
        info.setPackInfo(base.getParkInfo());//停车位信息
        info.setOrderType(2);//状态 1.露天停车 2.路边停车 3.小区停车
        //生成订单并且修改车位状态
        base.setParkStatus(1);
        ParkingOrderBase result = this.parkingOrderBaseService.addSideParkingOrderAndModifyParkInfoStatus(info,base);
        if(result==null){
            return ResultUtils.errorByUserDefine("600","抢车位失败");
        };
        //返回订单ID
        return ResultUtils.success(result);
    }
    /**
     *
     *小区停车-根据车辆Id和车位Id抢小区停车位
     * @param carId
     * @param parkId
     * @param req
     * @return
     */
    @ApiOperation(value = "小区停车-根据车辆Id和车位Id抢小区停车位",httpMethod ="POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "carId",value = "车辆Id",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "parkId",value = "小区停车位Id",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "x-token",value = "JWTToken",paramType = "header",dataType = "String",required = true),
    })
    @PostMapping(value = "communityPark/carId/parkId")
    public Map<String,Object> UseCommunityParkingById(@RequestParam String carId,@RequestParam String parkId,HttpServletRequest req){
        //MVC自动判空；
        String account = SQYRequestUntils.getAccount(req);
        //查看是否有此车辆
        SysMyCar car = this.sysMyCarService.getBaseJapRepository().findOne(carId);
        if(car==null){
            return ResultUtils.errorByUserDefine("600","此车辆不存在");
        }
        SysUser ac = this.sysUserService.getBaseJapRepository().findByAccount(SQYRequestUntils.getAccount(req));
        if(account==null){
            return ResultUtils.errorByUserDefine("600","此账户不存在");
        }
        if(!StringUtils.equals(account,car.getSysAccount())){
            return ResultUtils.errorByUserDefine("600","此车辆所有人不是你本人，不能抢车位");
        }
        if(ac.getState()==4){
            return ResultUtils.errorByUserDefine("600","您正在使用别的停车位，不能再次申请停车");
        }
        //查看是否有此停车位
        CommunityParkingParkInfo park = this.communityParkingParkInfoFeign.findParkInfoByID(parkId);
        FeignUntils.isSuccess(park,"sideParkingParkInfoFeign.findParkInfoByID(parkId)");
        if(park.getId()==null){
            return ResultUtils.errorByUserDefine("600","无此停车位");
        }
        //查看停车位状态
        if(park.getParkStatus()!=2){
            return ResultUtils.errorByUserDefine("600","此停车位不能预约");
        }
        //生成订单并且更改停车位状态
        ParkingOrderBase order = new ParkingOrderBase();
        order.setCreateTime(DateUtils.formatNow());//设置开始创建时间
        order.setOrderTime(DateUtils.formatNow());//设置订单生成时间
        order.setOrderType(3);//小区停车停车
        order.setParkId(parkId);//设置停车位ID
        order.setSysAccount(SQYRequestUntils.getAccount(req));//设置使用者账户
        order.setAddress(park.getParkAddress());
        order.setCarNumber(car.getCarNum());
        order.setPackInfo(park.getParkInfo());
        order.setOrderStatus(1);//已预约
        order.setOrderType(3);//状态 1.露天停车 2.路边停车 3.小区停车
        park.setParkStatus(1);//设置停车位已被预约
        ParkingOrderBase result = this.parkingOrderBaseService.addCommunityParkingOrderAndModifyParkInfoStatus(order,park);
        if(result==null){
            return ResultUtils.errorByUserDefine("600","抢车位失败");
        }
        return ResultUtils.success(result);
    }

    /**
     * 公共停车-根据车辆Id和停车车位Id预约车位
     * @param carId
     * @param parkId
     * @param req
     * @return
     */
    @ApiOperation(value = "公共停车-根据车辆Id和停车车位Id预约车位",httpMethod ="POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "carId",value = "车辆Id",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "parkId",value = "公共停车位Id",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "x-token",value = "JWTToken",paramType = "header",dataType = "String",required = true),
    })
    @PostMapping(value = "publishPark/carId/parkId")
    public Map<String,Object> UsePublishParkingById(@RequestParam String carId,@RequestParam String parkId,HttpServletRequest req){
        //MVC自动判空；
        String account = SQYRequestUntils.getAccount(req);
        //查看是否有此车辆
        SysMyCar car = this.sysMyCarService.getBaseJapRepository().findOne(carId);
        if(car==null){
            return ResultUtils.errorByUserDefine("600","此车辆不存在");
        }
        SysUser ac = this.sysUserService.getBaseJapRepository().findByAccount(SQYRequestUntils.getAccount(req));
        if(account==null){
            return ResultUtils.errorByUserDefine("600","此账户不存在");
        }
        if(!StringUtils.equals(account,car.getSysAccount())){
            return ResultUtils.errorByUserDefine("600","此车辆所有人不是你本人，不能预约车位");
        }
        if(ac.getState()==4){
            return ResultUtils.errorByUserDefine("600","您正在使用别的停车位，不能再次申请停车");
        }
        //查询是否有此公共停车位
        PublishParkingParkInfo park = this.publishParkingParkInfoFeign.findParkInfoByID(parkId);
        FeignUntils.isSuccess(park,"publishParkingParkInfoFeign.findParkInfoByID(parkId)");
        if(park.getId()==null){
            return ResultUtils.errorByUserDefine("600","无此停车位信息");
        }
        //查询停车位状态
        if(park.getParkStatus()!=2){
            return ResultUtils.errorByUserDefine("600","此公共停车没有参与合作");
        }
        //查询还有没有剩余车位
        if(park.getParkResidualNum()<=0){
            return ResultUtils.errorByUserDefine("600","此公共停车区域没有足够的停车位");
        }
        //新建订单
        ParkingOrderBase order = new ParkingOrderBase();
        order.setCreateTime(DateUtils.formatNow());//设置订单生产时间
        order.setOrderTime(null);//设置未确认入场
        order.setParkId(parkId);//设置停车位ID
        order.setOrderType(1);//公共停车
        order.setSysAccount(account);//设置使用者账户
        order.setAddress(park.getAddress());//设置停车地址
        order.setCarNumber(car.getCarNum());//设置车牌号
        order.setPackInfo(park.getParkInfo());//设置停车位信息
        order.setOrderStatus(1);//已预约
        //可用停车位减1
        park.setParkResidualNum(park.getParkResidualNum()-1);
        //保存订单和公共停车停车位
        ParkingOrderBase result = this.parkingOrderBaseService.addPublishParkingOrderAndModifyParkInfoStatus(order, park);
        if(result==null){
            return ResultUtils.errorByUserDefine("600","抢车位失败");
        }
        return ResultUtils.success(result);
    }


}
