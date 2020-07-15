package com.bee.team.fastgo.dpo;//package com.spring.simple.development.demo.dpo;
//
//import com.spring.simple.development.core.component.data.process.annotation.external.Condition;
//import com.spring.simple.development.core.component.data.process.annotation.external.SimpleDpo;
//import com.spring.simple.development.core.component.data.process.enums.ConditionTypeEnum;
//import com.spring.simple.development.core.component.data.process.enums.OperateTypeEnum;
//import lombok.Data;
//
///**
// * @author luke wang
// */
//@Data
//@SimpleDpo(tableName = "tt_sso_account", operateTypeEnum = OperateTypeEnum.SELECT, dataModelType = "simpleDataProcessExecutor")
//public class QueryOrderDpo{
//
//    /**
//     * 订单号
//     */
//    @Condition(ConditionType = ConditionTypeEnum.AND, field = "account_code")
//    private String orderNumber;
//
//    public String getOrderNumber() {
//        return orderNumber;
//    }
//
//    public void setOrderNumber(String orderNumber) {
//        this.orderNumber = orderNumber;
//    }
//}
