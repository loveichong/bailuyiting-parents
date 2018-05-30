package com.bailuyiting.sso.service.controller.sys;

import com.bailuyiting.commons.core.entity.sso.ParkReport;
import com.bailuyiting.commons.until.DateUtils;
import com.bailuyiting.commons.until.ResultUtils;
import com.bailuyiting.commons.until.SQYRequestUntils;
import com.bailuyiting.sso.service.service.ParkReportService;
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
import java.util.Map;

@Api(value = "违章停车举报-API",description = "违章停车举报-API")
@RestController
@RequestMapping(value = "api/report/")
public class ParkReportController {
    @Autowired
    private ParkReportService parkReportService;
    /**
     * 举报路边违章停车
     * @param reportURL
     * @param reportAddress
     * @param carNum
     * @param req
     * @return
     */
    @ApiOperation(value = "举报路边违章停车",httpMethod ="POST",notes = "举报路边违章停车")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "reportURL",value = "举报照片URL",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "reportAddress",value = "违章停车地址",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "carNum",value = "违章停车车牌",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "x-token",value = "JWTToken",paramType = "header",dataType = "String",required = true),
    })
    @PostMapping(value = "sideParking")
    public Map<String,Object> AddSideParkingReport(@RequestParam String reportURL, @RequestParam String reportAddress,
                                                   @RequestParam String carNum,HttpServletRequest req){
        //MVC判空
        //直接记录
        ParkReport parkReport = new ParkReport();
        parkReport.setCreateTime(DateUtils.formatNow());
        parkReport.setSysAccount(SQYRequestUntils.getAccount(req));
        parkReport.setReportTpye(2);//路边违章停车
        parkReport.setReportAddress(reportAddress);
        parkReport.setReportURL(reportURL);
        parkReport.setCarNum(carNum);
        ParkReport result = this.parkReportService.getBaseJapRepository().save(parkReport);
        if(result==null){
            return ResultUtils.errorByUserDefine("600","举报失败，请联系客服处理");
        }
        return ResultUtils.success(result);
    }
}
