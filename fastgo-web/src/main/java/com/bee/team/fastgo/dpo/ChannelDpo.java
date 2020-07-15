package com.bee.team.fastgo.dpo;

import com.spring.simple.development.core.component.data.process.annotation.external.Condition;
import com.spring.simple.development.core.component.data.process.annotation.external.SimpleDpo;
import com.spring.simple.development.core.component.data.process.enums.ConditionTypeEnum;
import com.spring.simple.development.core.component.data.process.enums.OperateTypeEnum;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author liko
 * @description
 * @date 2020/06/21
 */
@Data
@ApiModel(value = "accountVo", description = "accountVo描述")
@SimpleDpo(tableName = "t_channel",
        operateTypeEnum = OperateTypeEnum.SELECT,
        dataModelType = "simpleDataProcessExecutor")
public class ChannelDpo {
    /**
     * 用户名称
     */
    @Condition(ConditionType = ConditionTypeEnum.AND)
    private String channelname;

    /**
     * 渠道代码
     */
    private String channelcode;

    /**
     * 渠道类型 1-个人  2-企业
     */
    private Integer channeltype;

    /**
     * 社会信用代码
     */
    private String creditcode;

    /**
     * 法人
     */
    private String legalname;

    /**
     * 身份证号
     */
    private String legalcardno;

    /**
     * 联系人姓名
     */
    private String linkname;

    /**
     * 联系电话
     */
    private String linkphone;

    /**
     * 联系地址
     */
    private String linkaddress;

    /**
     * 业务区域
     */
    private String businessarea;

    /**
     * 银行卡号
     */
    private String bankno;

    /**
     * 银行
     */
    private String bankname;

    /**
     * 开户行
     */
    private String bankopening;

    /**
     * 业务经理
     */
    private Integer accountid;

    /**
     * 合同协议保存位置
     */
    private String agreementurl;

    /**
     * 渠道底价
     */
    private BigDecimal floorprice;

    /**
     * 登录账号
     */
    private String logincode;

    /**
     * 登录密码
     */
    private String loginpwd;

    /**
     * 最后一次登录时间
     */
    private Date lastlogintime;

    /**
     * 状态：1-正常;2-冻结;3-注销
     */
    private Integer status;
}