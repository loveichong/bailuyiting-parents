package com.bailuyiting.sso.service.controller.order;

import com.bailuyiting.commons.core.entity.camera.AlarmInfoPlate;
import com.bailuyiting.commons.core.entity.camera.result;
import com.bailuyiting.commons.core.entity.order.ParkingOrderBase;
import com.bailuyiting.commons.core.entity.sso.SysBalanceLog;
import com.bailuyiting.commons.core.entity.sso.SysUser;
import com.bailuyiting.commons.core.entity.sso.SysUserRoles;
import com.bailuyiting.commons.until.*;
import com.bailuyiting.module.wechat.controller.WeChatPayController;
import com.bailuyiting.module.wechat.config.bean.WeChatPayPropertyBean;
import com.bailuyiting.module.wechat.entity.WeChatPayLog;
import com.bailuyiting.module.wechat.entity.WechatTradePagePayRequest;
import com.bailuyiting.module.wechat.service.WeChatPayLogService;
import com.bailuyiting.module.wechat.until.WeChatUtils;
import com.bailuyiting.sso.service.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;

@Api(value = "订单资源更新-API",description = "订单资源更新-API")
@RestController
@RequestMapping(value = "api/order/")
public class OrderPutController {
    @Autowired
    private WeChatPayController weChatPay;
    @Autowired
    private SysMyCarService sysMyCarService;
    @Autowired
    private ParkingOrderBaseService parkingOrderBaseService;
    @Autowired
    private WeChatPayPropertyBean weChatPayPropertyBean;
    @Autowired
    private WeChatPayLogService weChatPayLogService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysBalanceLogService sysBalanceLogService;
    @Autowired
    private SysUserRolesService sysUserRolesService;
    /**
     * 前端通知后台停车订单已成功支付
     * 防止后台未收到微信通知，出现不能自动确认订单的状况
     *
     * @param id
     * @param price
     * @return
     */
    @ApiOperation(value = "前端通知后台停车订单已成功支付，请于支付成功3秒后再调用此接口",httpMethod ="PUT",notes = "停车位信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id",value = "停车订单Id",paramType = "path",dataType = "String",required = true),
            @ApiImplicitParam(name = "price",value = "支付金额",paramType = "query",dataType = "BigDecimal",required = true),
            @ApiImplicitParam(name = "x-token",value = "JWTToken",paramType = "header",dataType = "String",required = true),
    })
    @PutMapping(value ="id/{id}")
    public Map<String,Object> confirmSideParkOrderSuccess(@PathVariable(value = "id")String id,@RequestParam BigDecimal price) {
        //MVC判空
        ParkingOrderBase one = this.parkingOrderBaseService.getBaseJapRepository().findOne(id);
        if(one==null){
            return  ResultUtils.errorByUserDefine("600","没有这个订单");
        }
        switch (one.getOrderStatus()){//1=已预约，2=进场，3=已进场已支付但是未确认，4=出场订单完成
            case 4:return  ResultUtils.success();//后台已经成功接收到付款成功信息
        }
        //这里查看是否已经有微信支付订单，如果没有就是错的
        WeChatPayLog payLog = this.weChatPayLogService.getBaseJapRepository().findByOrderId(one.getId());
        if(payLog==null){
            ResultUtils.errorByUserDefine("600","此订单没有申请过微信支付，请勿调用此接口");
        }
        //如果后台未成功接收到信息，先手动确认订单成功
        one.setModifyTime(DateUtils.formatNow());//修改时间
        one.setOrderStatus(4);//1=已预约，2=进场，3=已进场已支付但是未确认，4=出场订单完成
        one.setPrice(price);//价格
        //计算停车时间  如果没有OrderTime就代表不是需要保安的订单
        String time;
        if(one.getOrderTime()==null){
            time=one.getCreateTime();
        }
        else{
            time=one.getOrderTime();
        }
        int minutes = DateUtils.getBetweenDateMinutes(DateUtils.totDate(time), new Date());
        one.setKeepTime(Integer.toString(minutes));//停车时间
        one.setFinishTime(DateUtils.formatNow());//结束时间
        //保存订单并且修改停车位状态
        switch (one.getOrderType()){//状态 1.露天停车 2.路边停车 3.小区停车
            case 1:one=this.parkingOrderBaseService.confirmPublishParkingOrderSuccess(one);break;
            case 2:one=this.parkingOrderBaseService.confirmSideParkingOrderSuccess(one);break;
            case 3:one=this.parkingOrderBaseService.confirmCommunityParkingOrderSuccess(one);break;
        }
        return ResultUtils.success(one);
    }
    /**
     * 根据订单ID，申请微信付款(付款成功自动完成订单，不适合出场需要人工审核的场景)
     * @param orderId
     * @param price
     * @param req
     * @return
     */
    @ApiOperation(value = "根据订单ID，申请微信付款(付款成功自动完成订单，不适合出场需要人工审核的场景)",httpMethod ="PUT")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "orderId",value = "订单Id",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "price",value = "价格",paramType = "query",dataType = "BigDecimal",required = true),
            @ApiImplicitParam(name = "tradeType",value = "交易类型(前端自己此查找微信支付文档填写交易类型，保证无误)",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "x-token",value = "JWTToken",paramType = "header",dataType = "String",required = true),
    })
    @PutMapping(value = "/pay/weChat/confirmOrder")
    public Map<String,Object> PayOrderByWeChatAndConfirmOrder(@RequestParam String orderId,@RequestParam BigDecimal price,@RequestParam String tradeType,HttpServletRequest req) {
        //MVC判空
        //判断订单状态
        ParkingOrderBase order = this.parkingOrderBaseService.getBaseJapRepository().findOne(orderId);
        if (order == null) {
            return ResultUtils.errorByUserDefine("600", "无此订单");
        }
        if (order.getOrderStatus() != 1) {
            return ResultUtils.errorByUserDefine("600", "此订单不能支付");
        }
        WechatTradePagePayRequest payRequest = new WechatTradePagePayRequest();
        payRequest.setBody("白鹭易停-停车费支付");
        payRequest.setNeedWechatFee(price);//设置需要支付的金额 元
        payRequest.setTradeNo(order.getId());//设置订单ID
        payRequest.setTradeType(tradeType);//填写交易类型
        payRequest.setNotifyUrl("/notify/weChat/order/confirm");//回调地址 后缀
        //申请微信支付
        Map<String, Object> resultMap = this.weChatPay.unifiedOrder(payRequest);
        //返回申请结果
        return WeChatUtils.weChatPayReturn(resultMap,this.weChatPayPropertyBean);
    }
    /**
     * 根据订单ID，申请微信付款(付款成功不代表订单确认成功，适合出场需要人工审核的场景)
     * @param orderId
     * @param price
     * @param req
     * @return
     */
    @ApiOperation(value = "根据订单ID，申请微信付款(付款成功不代表订单确认成功，适合出场需要人工审核的场景)",httpMethod ="PUT")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "orderId",value = "订单Id",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "price",value = "价格",paramType = "query",dataType = "BigDecimal",required = true),
            @ApiImplicitParam(name = "tradeType",value = "交易类型(前端自己此查找微信支付文档填写交易类型，保证无误)",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "x-token",value = "JWTToken",paramType = "header",dataType = "String",required = true),
    })
    @PutMapping(value = "/pay/weChat")
    public Map<String,Object> PayOrderByWeChat(@RequestParam String orderId,@RequestParam BigDecimal price,@RequestParam String tradeType,HttpServletRequest req) {
        //MVC判空
        //判断订单状态
        ParkingOrderBase order = this.parkingOrderBaseService.getBaseJapRepository().findOne(orderId);
        if (order == null) {
            return ResultUtils.errorByUserDefine("600", "无此订单");
        }
        if (order.getOrderStatus() ==4) {//状态（1=已预约，2=进场，3=已进场未支付，3=已进场已支付但是未确认，4=出场订单完成）
            return ResultUtils.errorByUserDefine("600", "此订单已经确认完成，不能支付");
        }
        WechatTradePagePayRequest payRequest = new WechatTradePagePayRequest();
        payRequest.setBody("白鹭易停-停车费支付");
        payRequest.setNeedWechatFee(price);//设置需要支付的金额 元
        payRequest.setTradeNo(order.getId());//设置订单ID
        payRequest.setTradeType(tradeType);//填写交易类型
        payRequest.setNotifyUrl("/notify/weChat/order");//回调地址 后缀
        //申请微信支付
        Map<String, Object> resultMap = this.weChatPay.unifiedOrder(payRequest);
        //返回申请结果
        return WeChatUtils.weChatPayReturn(resultMap,this.weChatPayPropertyBean);
    }
    /**
     * 钱包订单付款 直接开启事务 (付款成功自动完成订单，不适合出场需要人工审核的场景)
     * @param orderId
     * @param price
     * @param req
     * @return
     */
    @ApiOperation(value = "订单申请钱包付款(付款成功自动完成订单，不适合出场需要人工审核的场景)",httpMethod ="PUT")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "orderId",value = "订单Id",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "price",value = "价格",paramType = "query",dataType = "BigDecimal",required = true),
            @ApiImplicitParam(name = "x-token",value = "JWTToken",paramType = "header",dataType = "String",required = true),
    })
    @Transactional
    @PutMapping(value = "/pay/balance/confirmOrder")
    public Map<String,Object> PayOrderByBalanceAndConfirmOrder(@RequestParam String orderId,@RequestParam BigDecimal price,HttpServletRequest req) {
        //判断金额是否大于0
        if(price.compareTo(new BigDecimal(0))!=1){
            return ResultUtils.errorByUserDefine("600","金额数目非法，请校验");
        }
        //MVC判空
        //判断订单状态
        String ac = SQYRequestUntils.getAccount(req);
        ParkingOrderBase order = this.parkingOrderBaseService.getBaseJapRepository().findOne(orderId);
        if (order == null) {
            return ResultUtils.errorByUserDefine("600", "无此订单");
        }
        //1=已预约，2=进场，3=已进场已支付但是未确认，4=出场订单完成
        if (order.getOrderStatus() ==4) {
            return ResultUtils.errorByUserDefine("600", "此订单已经确认完成，不能支付");
        }
        //查询钱包余额
        SysUser account = this.sysUserService.getBaseJapRepository().findByAccount(ac);
        BigDecimal b=account.getBalance();
        //判断是否足以支付
        if(b==null||b.compareTo(price)==-1){
            return ResultUtils.errorByUserDefine("600","用户钱包余额不足，不能支付");
        }
        //生成钱包记录
        SysBalanceLog log = new SysBalanceLog();
        log.setCreateTime(DateUtils.formatNow());//时间
        log.setSysAccount(ac);//账户
        log.setBalanceStatus(1);//成功
        log.setBalanceType(10);//钱包付款
        log.setParkOrder(order.getId());//订单ID
        log.setOldPrice(b);//未扣款钱余额
        log.setUsePrice(price);//扣款余额
        log.setCurrentPrice(b.subtract(price));//扣款后余额
        this.sysBalanceLogService.getBaseJapRepository().save(log);
        //设置账户余额
        account.setBalance(log.getCurrentPrice());
        //由于这里开启所以事务 所以预先设定账户停车位使用状态解锁
        account.setState(5);//解锁
        this.sysUserService.getBaseJapRepository().save(account);
        //确认订单完成
        order.setModifyTime(DateUtils.formatNow());//修改时间
        order.setOrderStatus(4);//1=已预约，2=进场，3=已进场已支付但是未确认，4=出场订单完成
        order.setPrice(price);//价格
        //计算停车时间  如果没有OrderTime就代表不是需要保安的订单
        String time;
        if(order.getOrderTime()==null){
            time=order.getCreateTime();
        }
        else{
            time=order.getOrderTime();
        }
        int minutes = DateUtils.getBetweenDateMinutes(DateUtils.totDate(time), new Date());
        order.setKeepTime(Integer.toString(minutes));//停车时间
        order.setFinishTime(DateUtils.formatNow());//结束时间
        //保存订单并且修改停车位状态
        switch (order.getOrderType()){//状态 1.露天停车 2.路边停车 3.小区停车
            case 1:this.parkingOrderBaseService.confirmPublishParkingOrderSuccess(order);break;
            case 2:this.parkingOrderBaseService.confirmSideParkingOrderSuccess(order);break;
            case 3:this.parkingOrderBaseService.confirmCommunityParkingOrderSuccess(order);break;
        }
        return ResultUtils.success();
    }
    /**
     * 钱包订单付款 直接开启事务 (付款成功不代表订单确认成功，适合出场需要人工审核的场景)
     * @param orderId
     * @param price
     * @param req
     * @return
     */
    @ApiOperation(value = "订单申请钱包付款(付款成功不代表订单确认成功，适合出场需要人工审核的场景)",httpMethod ="PUT")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "orderId",value = "订单Id",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "price",value = "价格",paramType = "query",dataType = "BigDecimal",required = true),
            @ApiImplicitParam(name = "x-token",value = "JWTToken",paramType = "header",dataType = "String",required = true),
    })
    @Transactional
    @PutMapping(value = "/pay/balance")
    public Map<String,Object> PayOrderByBalance(@RequestParam String orderId,@RequestParam BigDecimal price,HttpServletRequest req) {
        //判断金额是否大于0
        if(price.compareTo(new BigDecimal(0))!=1){
            return ResultUtils.errorByUserDefine("600","金额数目非法，请校验");
        }
        //MVC判空
        //判断订单状态
        String ac = SQYRequestUntils.getAccount(req);
        ParkingOrderBase order = this.parkingOrderBaseService.getBaseJapRepository().findOne(orderId);
        if (order == null) {
            return ResultUtils.errorByUserDefine("600", "无此订单");
        }
        //1=已预约，2=进场，3=已进场已支付但是未确认，4=出场订单完成
        if (order.getOrderStatus() ==4) {
            return ResultUtils.errorByUserDefine("600", "此订单已经确认完成，不能支付");
        }
//        //判断是否支付过，如果已经支付不能再次支付
//        if(order.getPrice()!=null){
//            return ResultUtils.errorByUserDefine("600", "此订单已经支付过一次，确实需要再次支付？");
//        }
        //查询钱包余额
        SysUser account = this.sysUserService.getBaseJapRepository().findByAccount(ac);
        BigDecimal b=account.getBalance();
        //判断是否足以支付
        if(b==null||b.compareTo(price)==-1){
            return ResultUtils.errorByUserDefine("600","用户钱包余额不足，不能支付");
        }
        //生成钱包记录
        SysBalanceLog log = new SysBalanceLog();
        log.setCreateTime(DateUtils.formatNow());//时间
        log.setSysAccount(ac);//账户
        log.setBalanceStatus(1);//成功
        log.setBalanceType(10);//钱包付款
        log.setParkOrder(order.getId());//订单ID
        log.setOldPrice(b);//未扣款钱余额
        log.setUsePrice(price);//扣款余额
        log.setCurrentPrice(b.subtract(price));//扣款后余额
        this.sysBalanceLogService.getBaseJapRepository().save(log);
        //设置账户余额
        account.setBalance(log.getCurrentPrice());
        this.sysUserService.getBaseJapRepository().save(account);
       //保存支付订单
        order.setModifyTime(DateUtils.formatNow());//设置支付时间
        BigDecimal oldPrice=order.getPrice();
        if(oldPrice==null){
            order.setPrice(price);//设置支付金额
        }
        else {
            order.setPrice(price.add(oldPrice));//设置支付金额 相加
        }
        order.setPrice(price);//设置支付金额
        order.setOrderStatus(3);//1=已预约，2=进场，3=已进场已支付但是未确认，4=出场订单完成
        //保存订单
        this.parkingOrderBaseService.getBaseJapRepository().save(order);
        return ResultUtils.success();
    }

    /**
     * 保安人员确认车辆进场(用于需要人工确认进场的场景)
     * @param id
     * @param req
     * @return
     */
    @ApiOperation(value = "保安人员确认车辆进场(用于需要人工确认进场的场景)",httpMethod ="PUT")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id",value = "订单Id",paramType = "path",dataType = "String",required = true),
            @ApiImplicitParam(name = "x-token",value = "JWTToken",paramType = "header",dataType = "String",required = true),
    })
    @PutMapping(value = "confirmIn/id/{id}")
    public Map<String,Object> UsePublishParkingById(@PathVariable(value = "id")String id, HttpServletRequest req){
        //MVC判空
        //判断是否是停车管理员
        String ac = SQYRequestUntils.getAccount(req);
        SysUserRoles account = this.sysUserRolesService.getBaseJapRepository().findBySysAccount(ac);
        if(account==null){
            return ResultUtils.errorByUserDefine("600","无此账户");
        }
        int role=account.getUserRole();
        //判断是否有订单
        ParkingOrderBase order = this.parkingOrderBaseService.getBaseJapRepository().findOne(id);
        if(order==null){
            return ResultUtils.errorByUserDefine("600","无此订单");
        }
        switch (order.getOrderType()){//状态 1.露天停车 2.路边停车 3.小区停车
            case 1:if(role!=11&&role!=4){
                return ResultUtils.errorByUserDefine("600","此账户无权限");
            }break;
            case 2:if(role!=12&&role!=4){
                return ResultUtils.errorByUserDefine("600","此账户无权限");
            }break;
            case 3:if(role!=13&&role!=4){
                return ResultUtils.errorByUserDefine("600","此账户无权限");
            }break;
        }
        //1=已预约，2=进场，3=已进场已支付但是未确认，4=出场订单完成
        order.setOrderTime(DateUtils.formatNow());//设置进场时间
        order.setModifyer(ac);//设置保安账户
        order.setOrderStatus(2);//设置状态已经进场
       this.parkingOrderBaseService.getBaseJapRepository().save(order);
       return ResultUtils.success();
    }

    /**
     * 保安人员确认车辆出场
     * @param id
     * @param req
     * @return
     */
    @ApiOperation(value = "保安人员确认车辆出场",httpMethod ="PUT")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id",value = "订单Id",paramType = "path",dataType = "String",required = true),
            @ApiImplicitParam(name = "x-token",value = "JWTToken",paramType = "header",dataType = "String",required = true),
    })
    @PutMapping(value = "confirmOut/id/{id}")
    public Map<String,Object> ConfirmOutByManual(@PathVariable(value = "id")String id, HttpServletRequest req){
        //MVC判空
        //判断是否是停车管理员
        String ac = SQYRequestUntils.getAccount(req);
        SysUserRoles account = this.sysUserRolesService.getBaseJapRepository().findBySysAccount(ac);
        if(account==null){
            return ResultUtils.errorByUserDefine("600","无此账户");
        }
        int role=account.getUserRole();
        //判断是否有订单
        ParkingOrderBase order = this.parkingOrderBaseService.getBaseJapRepository().findOne(id);
        if(order==null){
            return ResultUtils.errorByUserDefine("600","无此订单");
        }
        if(order.getOrderStatus()==2){
            return ResultUtils.errorByUserDefine("600","此订单已经确实成功，请勿重新确认");
        }
        switch (order.getOrderType()){//状态 1.露天停车 2.路边停车 3.小区停车
            case 1:if(role!=11&&role!=4){
                return ResultUtils.errorByUserDefine("600","此账户无权限");
            }break;
            case 2:if(role!=12&&role!=4){
                return ResultUtils.errorByUserDefine("600","此账户无权限");
            }break;
            case 3:if(role!=13&&role!=4){
                return ResultUtils.errorByUserDefine("600","此账户无权限");
            }break;
        }
        //确认订单完成解锁资源
        //1=已预约，2=进场，3=已进场已支付但是未确认，4=出场订单完成
        order.setModifyTime(DateUtils.formatNow()); //修改确认出场时间
        order.setModifyer(ac); //修改确认出场人
        order.setTotalPrice(order.getPrice());//设置金额
        order.setOrderStatus(4);//订单完成
        //设置停车时间
        if(order.getOrderTime()!=null){
            int minutes = DateUtils.getBetweenDateMinutes(DateUtils.totDate(order.getOrderTime()), new Date());
            order.setKeepTime(String.valueOf(minutes));
        }
        order.setFinishTime(DateUtils.formatNow());//确认订单完成
        //保存订单并且修改停车位状态
        switch (order.getOrderType()){//状态 1.露天停车 2.路边停车 3.小区停车
            case 1:this.parkingOrderBaseService.confirmPublishParkingOrderSuccess(order);break;
            case 2:this.parkingOrderBaseService.confirmSideParkingOrderSuccess(order);break;
            case 3:this.parkingOrderBaseService.confirmCommunityParkingOrderSuccess(order);break;
        }
        return ResultUtils.success();
    }
    /**
     * 自动识别车辆进场-系统内部使用不对外开放(只能使用POST)
     * @param alarmInfoPlate
     * @return
     */
    @ApiOperation(value = "自动识别车辆进场-系统内部使用不对外开放",httpMethod ="POST")
    @PostMapping(value = "camera/in")
    public Map<String,Object> confirmInByCamera(@RequestBody String alarmInfoPlate) {
        //解析成JSON
        AlarmInfoPlate plate = CameraUntils.getAlarmInfoPlate(alarmInfoPlate);
        //获取信息
        String parkId=plate.getParkName();//这里使用设备名称 因为摄像头可以填的ID不能有字符串
        //判断出入口
        if(!StringUtils.equals(plate.getChannel(),"1")){
            throw new RuntimeException("此摄像头不是入口摄像头");
        }
        if(parkId==null){
            throw new RuntimeException("摄像头信息不对，没有停车场Id,请检查");
        }
        String car=plate.getResult().getPlateResult().getLicense();
        if(car==null){
            throw new RuntimeException("没有车牌号,请检查");
        }
        ParkingOrderBase order = this.parkingOrderBaseService.getBaseJapRepository().findByOrderStatusAndParkIdAndCarNumber(1, parkId, car);
        if(order==null){
            throw new RuntimeException("无订单号");
        }
        //判断是否已经入场
        //确认入场计算时间
        //1=已预约，2=进场，3=已进场已支付但是未确认，4=出场订单完成
        order.setOrderTime(DateUtils.formatNow());//设置进场时间
        order.setModifyer("camera"); //修改确认出场人
        order.setOrderStatus(2);//设置进场
        this.parkingOrderBaseService.getBaseJapRepository().save(order);
        //
        return CameraUntils.resultSuccess();

    }
    /**
     * 自动识别车辆出场-系统内部使用不对外开放(只能使用POST)
     * @param alarmInfoPlate
     * @return
     */
    @ApiOperation(value = "自动识别车辆出场-系统内部使用不对外开放",httpMethod ="POST")
    @PostMapping(value = "camera/out")
    public Map<String,Object> confirmOutByCamera(@RequestBody String alarmInfoPlate) {
        //解析成JSON
        AlarmInfoPlate plate = CameraUntils.getAlarmInfoPlate(alarmInfoPlate);
        //获取信息
        String parkId=plate.getParkName();//这里使用设备名称 因为摄像头可以填的ID不能有字符串//这里使用设备名称 因为摄像头可以填的ID不能有字符串
        //判断出入口
        if(!StringUtils.equals(plate.getChannel(),"0")){
            throw new RuntimeException("此摄像头不是出口摄像头");
        }
        if(parkId==null){
            throw new RuntimeException("摄像头信息不对，没有停车场Id,请检查");
        }
        String car=plate.getResult().getPlateResult().getLicense();
        if(car==null){
            throw new RuntimeException("没有车牌号,请检查");
        }
        ParkingOrderBase order = this.parkingOrderBaseService.getBaseJapRepository().findByOrderStatusAndParkIdAndCarNumber(3, parkId, car);
        if(order==null){
            //如果没有，看是不是没付钱
            ParkingOrderBase s = this.parkingOrderBaseService.getBaseJapRepository().findByOrderStatusAndParkIdAndCarNumber(1, parkId, car);
            if(s==null){
                //压根没订单，逻辑出问题
                throw new RuntimeException("程序有bug,请检查");
            }
            //如果没付钱，提醒付钱
            throw new RuntimeException("未付钱,请检查");
        }
        //判断是否需要补钱（现在先不做了）
        //确认车辆出场
        //确认订单完成解锁资源
        //1=已预约，2=进场，3=已进场已支付但是未确认，4=出场订单完成
        order.setModifyTime(DateUtils.formatNow()); //修改确认出场时间
        order.setModifyer("camera"); //修改确认出场人
        order.setTotalPrice(order.getPrice());//设置金额
        //设置停车时间
        if(order.getOrderTime()!=null){
            int minutes = DateUtils.getBetweenDateMinutes(DateUtils.totDate(order.getOrderTime()), new Date());
            order.setKeepTime(String.valueOf(minutes));
        }
        order.setOrderStatus(4);//订单完成
        order.setFinishTime(DateUtils.formatNow());//确认订单完成
        //保存订单并且修改停车位状态
        switch (order.getOrderType()){//状态 1.露天停车 2.路边停车 3.小区停车
            case 1:this.parkingOrderBaseService.confirmPublishParkingOrderSuccess(order);break;
            case 2:this.parkingOrderBaseService.confirmSideParkingOrderSuccess(order);break;
            case 3:this.parkingOrderBaseService.confirmCommunityParkingOrderSuccess(order);break;
        }
        return CameraUntils.resultSuccess();
    }

}
