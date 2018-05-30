package com.bailuyiting.sso.service.controller.order;

import com.bailuyiting.commons.core.entity.communityparking.CommunityParkingParkInfo;
import com.bailuyiting.commons.core.entity.order.ParkingOrderBase;
import com.bailuyiting.commons.core.entity.publishparking.PublishParkingParkInfo;
import com.bailuyiting.commons.core.entity.sideparking.SideParkingParkInfo;
import com.bailuyiting.commons.core.entity.sso.SysUserRoles;
import com.bailuyiting.commons.until.*;
import com.bailuyiting.communityparking.inter.feign.CommunityParkingParkInfoFeign;
import com.bailuyiting.publishparking.inter.feign.PublishParkingParkInfoFeign;
import com.bailuyiting.sideparking.inter.feign.SideParkingParkInfoFeign;
import com.bailuyiting.sideparking.inter.feign.SideParkingParkSectionFeign;
import com.bailuyiting.sso.service.service.ParkingOrderBaseService;
import com.bailuyiting.sso.service.service.SysMyCarService;
import com.bailuyiting.sso.service.service.SysUserRolesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

@Api(value = "订单资源获取-API",description = "订单资源获取-API")
@RestController
@RequestMapping(value = "api/order/")
public class OrderGetController {
    @Autowired
    private SysMyCarService sysMyCarService;
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
    @ApiOperation(value = "管理员账户根据订单状态查询停车订单",httpMethod ="GET")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "orderStatus",value = "订单状态（1=已预约，2=进场，3=已进场已支付但是未确认，4=出场订单完成）",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(name = "page",value = "页数,第一页是0",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(name = "size",value = "行数",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(name = "x-token",value = "JWTToken",paramType = "header",dataType = "String",required = true),
    })
    @GetMapping(value = "admin")
    public Map<String,Object> findOrderByAdminAndOrderStatus(@RequestParam Integer orderStatus,@RequestParam Integer page,@RequestParam Integer size,HttpServletRequest req){
        //判断是否是管理员
        String ac = SQYRequestUntils.getAccount(req);
        //判断是哪种类型的管理员
        SysUserRoles account = this.sysUserRolesService.getBaseJapRepository().findBySysAccount(ac);
        if(account==null){
            return ResultUtils.errorByUserDefine("600","账户不存在");
        }
        if(account.getUserRole()!=11&&account.getUserRole()!=12&&account.getUserRole()!=13){
            return ResultUtils.errorByUserDefine("600","账户无权限");
        }
        PageRequest request = new PageRequest(page, size, Sort.Direction.DESC, "createTime");
        List<ParkingOrderBase> result = this.parkingOrderBaseService.getBaseJapRepository().findByOrderStatusAndParkId(orderStatus, account.getParkId(), request);
        return ResultUtils.success(result);
        //查询订单
    }
    /**a
     * 根据停车订单Id查找停车订单
     * @param orderId
     * @return
     */
    @ApiOperation(value = "根据订单Id查找停车订单",httpMethod ="GET",notes = "停车位信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "orderId",value = "路边停车订单Id",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "x-token",value = "JWTToken",paramType = "header",dataType = "String",required = true),
    })
    @GetMapping(value = "id/{orderId}")
    public Map<String,Object> findOrderBySideParkingOrderId(@PathVariable(value = "orderId") String orderId){
        //MVC自动判空
        ParkingOrderBase order = this.parkingOrderBaseService.getBaseJapRepository().findOne(orderId);
        return ResultUtils.success(order);
    }
    /**
     * 根据订单类型查询账户所有历史订单
     * @param orderType
     * @param page
     * @param size
     * @return
     */
    @ApiOperation(value = "根据订单类型查询账户所有历史订单",httpMethod ="GET",notes = "根据订单类型查询账户所有历史订单")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "page",value = "页数,第一页是0",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(name = "size",value = "行数",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(name = "x-token",value = "JWTToken",paramType = "header",dataType = "String",required = true),
    })
    @GetMapping(value = "orderType/{orderType}")
    public Map<String,Object> findOrderByOrderType(@PathVariable(value = "orderType") Integer orderType,@RequestParam Integer page,@RequestParam Integer size,HttpServletRequest req){
        //MVC自动判空
        List<ParkingOrderBase> result = this.parkingOrderBaseService.getBaseJapRepository().findByOrderTypeAndSysAccount(orderType, SQYRequestUntils.getAccount(req), new PageRequest(page, size,Sort.Direction.DESC, "orderTime"));
        return ResultUtils.success(result);
    }
    /**
     * 查询账户所有历史订单
     * @param page
     * @param size
     * @param req
     * @return
     */
    @ApiOperation(value = "查询账户所有历史订单",httpMethod ="GET",notes = "根据订单类型查询账户所有历史订单")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "page",value = "页数,第一页是0",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(name = "size",value = "行数",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(name = "x-token",value = "JWTToken",paramType = "header",dataType = "String",required = true),
    })
    @GetMapping(value = "orders")
    public Map<String,Object> findOrderByOrderType(@RequestParam Integer page,@RequestParam Integer size,HttpServletRequest req){
        List<ParkingOrderBase> result = this.parkingOrderBaseService.getBaseJapRepository().findBySysAccount(SQYRequestUntils.getAccount(req), new PageRequest(page, size, Sort.Direction.DESC, "orderTime"));
        return ResultUtils.success(result);
    }
    /**
     * 根据订单状态查询账户所有历史订单
     * @param orderStatus
     * @param page
     * @param size
     * @param req
     * @return
     */
    @ApiOperation(value = "根据订单状态查询账户所有历史订单",httpMethod ="GET",notes = "根据订单类型查询账户所有历史订单")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "page",value = "页数,第一页是0",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(name = "size",value = "行数",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(name = "x-token",value = "JWTToken",paramType = "header",dataType = "String",required = true),
    })
    @GetMapping(value = "orderStatus/{orderStatus}")
    public Map<String,Object> findOrderBySideParkingOrderId(@PathVariable(value = "orderStatus") Integer orderStatus,@RequestParam Integer page,@RequestParam Integer size,HttpServletRequest req){
        String account = SQYRequestUntils.getAccount(req);
        PageRequest request = new PageRequest(page, size, Sort.Direction.DESC, "createTime");
        List<ParkingOrderBase> result = this.parkingOrderBaseService.getBaseJapRepository().findByOrderStatusAndSysAccount(orderStatus,account,request);
        return ResultUtils.success(result);
    }
    /**
     *查询账户所有未成功确认的订单
     * @param page
     * @param size
     * @param req
     * @return
     */
    @ApiOperation(value = "查询账户所有未成功确认的订单",httpMethod ="GET",notes = "根据订单类型查询账户所有历史订单")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "page",value = "页数,第一页是0",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(name = "size",value = "行数",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(name = "x-token",value = "JWTToken",paramType = "header",dataType = "String",required = true),
    })
    @GetMapping(value = "confirm/no")
    public Map<String,Object> OrderNotConfirm(@RequestParam Integer page,@RequestParam Integer size,HttpServletRequest req){
        String account = SQYRequestUntils.getAccount(req);
        PageRequest request = new PageRequest(page, size, Sort.Direction.DESC, "createTime");
        List<ParkingOrderBase> result = this.parkingOrderBaseService.getBaseJapRepository().findBySysAccountWhereOrderIsNotConfirm(account, request);
        for(ParkingOrderBase base:result){
            base.setCurrentTime(DateUtils.formatNow());//添加当前系统当前时间
        }
        return ResultUtils.success(result);
    }
    /**
     * 根据路边停车订单Id获取当前应付停车费
     * @param orderId
     * @return
     */
    @ApiOperation(value = "根据路边停车订单Id获取当前应付停车费",httpMethod ="GET",notes = "停车位信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "orderId",value = "公共停车订单Id",paramType = "path",dataType = "String",required = true),
            @ApiImplicitParam(name = "x-token",value = "JWTToken",paramType = "header",dataType = "String",required = true),
    })
    @GetMapping(value = "sidePark/price/id/{orderId}")
    public Map<String,Object> getPriceBySideParkingOrderId(@PathVariable(value = "orderId") String orderId){
        //MVC自动判空
        //查找订单
        ParkingOrderBase order = this.parkingOrderBaseService.getBaseJapRepository().findOne(orderId);
        //判断订单状态
        if(order==null){
            return ResultUtils.errorByUserDefine("600","无此停车位订单");
        }
        if(order.getOrderType()!=2){
            return ResultUtils.errorByUserDefine("600","此停车位订单不是路边停车类型");
        }
        String parkId=order.getParkId();
        if(parkId==null){
            return ResultUtils.errorByUserDefine("600","此停车位订单有误");
        }
        //查找停车位
        SideParkingParkInfo park = this.sideParkingParkInfoFeign.findParkInfoByID(order.getParkId());
        FeignUntils.isSuccess(park,"sideParkingParkInfoFeign.findParkInfoByID(order.getParkId())");
        //判断停车位是否存在
        if(park.getId()==null){
            return ResultUtils.errorByUserDefine("600","此停车位不存在，停车位订单有误");
        }
        //判断停车位信息 不是正在使用
        if(park.getParkStatus()!=1){
            return ResultUtils.errorByUserDefine("600","此停车位没有在使用");
        }
        //计算停车时间  如果没有OrderTime就代表不是需要保安的订单
        String time;
        if(order.getOrderTime()==null){
            time=order.getCreateTime();
        }
        else{
            time=order.getOrderTime();
        }
        //计算价格
        BigDecimal minutes=new  BigDecimal(DateUtils.getBetweenDateMinutes(DateUtils.totDate(time), new Date()));
        BigDecimal hours=minutes.divide(new BigDecimal(60),2, BigDecimal.ROUND_HALF_UP);//换算成小时
        BigDecimal price=park.getPrice().multiply(hours);//停车时间乘以价格
        HashMap<String, BigDecimal> result = new HashMap<>(1);
        result.put("price",price);
        return ResultUtils.success(result);
    }
    /**
     * 根据小区停车订单Id获取当前应付停车费
     * @param orderId
     * @return
     */
    @ApiOperation(value = "根据小区停车订单Id获取当前应付停车费",httpMethod ="GET",notes = "停车位信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "orderId",value = "公共停车订单Id",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "x-token",value = "JWTToken",paramType = "header",dataType = "String",required = true),
    })
    @GetMapping(value = "communityPark/price/id/{orderId}")
    public Map<String,Object> getPriceByCommunityParkingOrderId(@PathVariable(value = "orderId") String orderId){
        //MVC自动判空
        //查找订单
        ParkingOrderBase order = this.parkingOrderBaseService.getBaseJapRepository().findOne(orderId);
        //判断订单状态
        if(order==null){
            return ResultUtils.errorByUserDefine("600","无此停车位订单");
        }
        if(order.getOrderType()!=3){
            return ResultUtils.errorByUserDefine("600","此停车位订单不是小区停车类型");
        }
        String parkId=order.getParkId();
        if(parkId==null){
            return ResultUtils.errorByUserDefine("600","此停车位订单有误");
        }
        //查找停车位
        CommunityParkingParkInfo park = this.communityParkingParkInfoFeign.findParkInfoByID(order.getParkId());
        FeignUntils.isSuccess(park,"communityParkingParkInfoFeign.findParkInfoByID(order.getParkId())");
        //判断停车位是否存在
        if(park.getId()==null){
            return ResultUtils.errorByUserDefine("600","此停车位不存在，停车位订单有误");
        }
        //判断停车位信息 不是正在使用
        if(park.getParkStatus()==2){
            return ResultUtils.errorByUserDefine("600","此停车位订单的车位没有被使用，车位有误");
        }
        //计算停车时间  如果没有OrderTime就代表不是需要保安的订单
        String time;
        if(order.getOrderTime()==null){
            time=order.getCreateTime();
        }
        else{
            time=order.getOrderTime();
        }
        //计算价格
        BigDecimal minutes=new  BigDecimal(DateUtils.getBetweenDateMinutes(DateUtils.totDate(time), new Date()));
        BigDecimal hours=minutes.divide(new BigDecimal(60),2, BigDecimal.ROUND_HALF_UP);//换算成小时
        BigDecimal price=park.getPrice().multiply(hours);//停车时间乘以价格
        HashMap<String, BigDecimal> result = new HashMap<>(1);
        result.put("price",price);
        return ResultUtils.success(result);
    }
    /**
     * 根据公共停车订单Id获取当前应付停车费
     * @param orderId
     * @return
     */
    @ApiOperation(value = "根据公共停车订单Id获取当前应付停车费",httpMethod ="GET",notes = "停车位信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "orderId",value = "公共停车订单Id",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "x-token",value = "JWTToken",paramType = "header",dataType = "String",required = true),
    })
    @GetMapping(value = "publishPark/price/id/{orderId}")
    public Map<String,Object> getPriceByPublishParkingOrderId(@PathVariable(value = "orderId") String orderId){
        //MVC自动判空
        //查找订单
        ParkingOrderBase order = this.parkingOrderBaseService.getBaseJapRepository().findOne(orderId);
        //判断订单状态
        if(order==null){
            return ResultUtils.errorByUserDefine("600","无此停车位订单");
        }
        if(order.getOrderType()!=1){
            return ResultUtils.errorByUserDefine("600","此停车位订单不是公共停车类型");
        }
        String parkId=order.getParkId();
        if(parkId==null){
            return ResultUtils.errorByUserDefine("600","此停车位订单有误");
        }
        //查找停车位
        PublishParkingParkInfo park = this.publishParkingParkInfoFeign.findParkInfoByID(parkId);
        FeignUntils.isSuccess(park,"publishParkingParkInfoFeign.findParkInfoByID(parkId)");
        //判断停车位是否存在
        if(park.getId()==null){
            return ResultUtils.errorByUserDefine("600","此停车位不存在，停车位订单有误");
        }
        //判断停车位信息
        if(park.getParkStatus()==3){
            return ResultUtils.errorByUserDefine("600","此停车位异常");
        }
        //计算持续停车时间 分钟
        //计算停车时间  如果没有OrderTime就代表不是需要保安的订单
        String time;
        if(order.getOrderTime()==null){
            time=order.getCreateTime();
        }
        else{
            time=order.getOrderTime();
        }
        BigDecimal minutes=new  BigDecimal(DateUtils.getBetweenDateMinutes(DateUtils.totDate(time), new Date()));
        BigDecimal hours=minutes.divide(new BigDecimal(60),2, BigDecimal.ROUND_HALF_UP);//换算成小时
        BigDecimal price=null;
        HashMap<String, BigDecimal> result = new HashMap<>(1);
        //是否免费时间内
        if(hours.compareTo(park.getFreeTime())==-1){
            price=new BigDecimal(0);
            result.put("price",price);
            return ResultUtils.success(result);
        }
        //是否到封顶
        if(hours.compareTo(new BigDecimal(24))==1){
             price=park.getMaximumPrice();
            result.put("price",price);
            return ResultUtils.success(result);
        }
        //不在免费时间段也不超过封顶封顶计算价格
        //计算正常价格 停车时间乘以单价
        price=hours.multiply(park.getUnitPrice());
        //是否使用优惠价格
        result.put("price",price);
        return ResultUtils.success(result);
    }
    /**
     * 出场订单申请补单金额查询(仅试用于需要保安的停车场)
     * @param id
     * @param req
     * @return
     */
    @ApiOperation(value = "出场订单申请补单(仅试用于需要保安的停车场)",httpMethod ="GET")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id",value = "订单Id",paramType = "path",dataType = "String",required = true),
            @ApiImplicitParam(name = "x-token",value = "JWTToken",paramType = "header",dataType = "String",required = true),
    })
    @GetMapping(value = "secondPay/id/{id}")
    public Map<String,Object> OrderSecondPay(@PathVariable(value = "id")String id, HttpServletRequest req){
        //判断订单
        ParkingOrderBase order = this.parkingOrderBaseService.getBaseJapRepository().findOne(id);
        if(order==null){
            return ResultUtils.errorByUserDefine("600","无此订单");
        }
        //订单状态
        if(order.getOrderStatus()==2){
            return ResultUtils.errorByUserDefine("600","此订单已经确认成功，无法申请补单");
        }
        //如果不是支付过的
        if(order.getPrice()==null){
            return ResultUtils.errorByUserDefine("600","此订单并未支付过，不能申请补单");
        }
        //判断停车位类型
        BigDecimal price=null;
        switch (order.getOrderType()){//状态 1.露天停车 2.路边停车 3.小区停车
            case 1:
                PublishParkingParkInfo ppark = this.publishParkingParkInfoFeign.findParkInfoByID(order.getParkId());
                FeignUntils.isSuccess(ppark,"this.publishParkingParkInfoFeign.findParkInfoByID(order.getParkId())");
                if(ppark.getId()==null){
                    return ResultUtils.errorByUserDefine("600","此停车位无效");
                }
                price=ppark.getUnitPrice();
                break;
            case 2:
                SideParkingParkInfo spark = this.sideParkingParkInfoFeign.findParkInfoByID(order.getParkId());
                FeignUntils.isSuccess(spark,"this.sideParkingParkInfoFeign.findParkInfoByID(order.getParkId())");
                if(spark.getId()==null){
                    return ResultUtils.errorByUserDefine("600","此停车位无效");
                }
                price=spark.getPrice();
                break;
            case 3:
                CommunityParkingParkInfo cpark = this.communityParkingParkInfoFeign.findParkInfoByID(order.getParkId());
                FeignUntils.isSuccess(cpark,"this.sideParkingParkInfoFeign.findParkInfoByID(order.getParkId())");
                if(cpark.getId()==null){
                    return ResultUtils.errorByUserDefine("600","此停车位无效");
                }
                price=cpark.getPrice();
                break;
        }
        if(price==null){
            return ResultUtils.errorByUserDefine("600","无法获取金额");
        }
        //计算超出的停车时间 使用modifyTime开始计算
        BigDecimal minutes=new  BigDecimal(DateUtils.getBetweenDateMinutes(DateUtils.totDate(order.getModifyTime()), new Date()));
        BigDecimal hours=minutes.divide(new BigDecimal(60),2, BigDecimal.ROUND_HALF_UP);//换算成小时
        //计算金额
        BigDecimal needPrice=price.multiply(hours);
        //再次支付
        HashMap<String, Object> map = new HashMap<>();
        map.put("price",needPrice);
        return ResultUtils.success(map);
    }




}
