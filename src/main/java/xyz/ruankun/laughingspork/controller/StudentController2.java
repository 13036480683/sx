package xyz.ruankun.laughingspork.controller;
//                            _ooOoo_
//                           o8888888o
//                           88" . "88
//                           (| -_- |)
//                           O\  =  /O
//                        ____/`---'\____
//                      .'  \\|     |//  `.
//                     /  \\|||  :  |||//  \
//                    /  _||||| -:- |||||-  \
//                    |   | \\\  -  /// |   |
//                    | \_|  ''\---/''  |   |
//                    \  .-\__  `-`  ___/-. /
//                  ___`. .'  /--.--\  `. . __
//               ."" '<  `.___\_<|>_/___.'  >'"".
//              | | :  `- \`.;`\ _ /`;.`/ - ` : | |
//              \  \ `-.   \_ __\ /__ _/   .-` /  /
//         ======`-.____`-.___\_____/___.-`____.-'======
//                            `=---='
//        ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
//                      Buddha Bless, No Bug !

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.ruankun.laughingspork.entity.SxReport;
import xyz.ruankun.laughingspork.entity.SxStudent;
import xyz.ruankun.laughingspork.service.SxReportService;
import xyz.ruankun.laughingspork.util.ControllerUtil;
import xyz.ruankun.laughingspork.util.DateUtil;
import xyz.ruankun.laughingspork.util.EntityUtil;
import xyz.ruankun.laughingspork.util.constant.RoleCode;
import xyz.ruankun.laughingspork.vo.ResponseVO;

@RestController
@CrossOrigin
@RequestMapping("/student2")
@Api(tags = {"ѧ������2"})
public class StudentController2 {

    private static final Logger logger = LoggerFactory.getLogger(StudentController2.class);

    @Autowired
    SxReportService sxReportService;

    @ApiOperation(value = "����ʵϰ��ʼ����", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gmtStart", value = "ʵϰ��ʼʱ��", required = true),
            @ApiImplicitParam(name = "gmtEnd", value = "ʵϰ����ʱ��", required = true)
    })
    @PostMapping("/report/date")
    @RequiresRoles(RoleCode.STUDENT)
    public ResponseVO bindSxDate(@RequestParam(required = true) String gmtStart,@RequestParam(required = true) String gmtEnd){
        boolean dateInputError = gmtEnd == null || gmtStart == null || gmtEnd.equals("") || gmtStart.equals("");
        boolean parseError = DateUtil.getDateByStr(gmtStart) == null || DateUtil.getDateByStr(gmtEnd) == null;
        if (dateInputError){
            return ControllerUtil.getFalseResultMsgBySelf("�봫��ʱ��gmtStart �� gmtEnd");
        }else if(parseError){
            return ControllerUtil.getFalseResultMsgBySelf("ʱ���ʽ����,��ȷ��ʱ���ʽ:2020-11-21 12:12:12(ʱ�����ʡ��)");
        }

        SxStudent sxStudent = (SxStudent) SecurityUtils.getSubject().getPrincipal();
        SxReport sxReport = sxReportService.getReportInfo(sxStudent.getStuNo());
        if (sxReport == null){
            return ControllerUtil.getFalseResultMsgBySelf("��ѯʵϰ����ʱ���ִ�����鿴��̨��־");
        }
        SxReport sxReportFromFront = new SxReport();
        sxReportFromFront.setGmtEnd(DateUtil.getSqlDateByStr(gmtEnd));
        sxReportFromFront.setGmtStart(DateUtil.getSqlDateByStr(gmtStart));
        EntityUtil.update(sxReportFromFront,sxReport);
        logger.info("sxReportδ����ǰ: " + sxReport.toString());
        logger.info("sxReportʵϰ��ʼ����ʱ�伴��������: " + sxReportFromFront.toString());
        try {
            sxReportService.saveReport(sxReportFromFront);
            return ControllerUtil.getSuccessResultBySelf("���³ɹ�");
        }catch (Exception e){
            return ControllerUtil.getFalseResultMsgBySelf("����ʧ�ܣ�" + e.getMessage());
        }
    }
}
