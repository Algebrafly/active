package com.xiaoyun.active.aop;

/**
 * @author al
 * @date 2019/6/4 17:39
 * @description
 */
public enum WorkflowEnum {


    /*是否*/
    YES("是","Y"),
    NO("否","N"),

    /**
     * 审批状态定义
     */
    credit_query("待查征信","1"),
    app_getOrder("待申请领单","2"),
    materials_improved("待完善材料","3"),
    commissioner_getOrder("待风控专员领单","4"),
    commissioner_trial("待风控初审","5"),
    supervisor_getOrder("待风控主管领单","6"),
    supervisor_trial("待风控复审","7"),
    director_getOrder("待风控总监领单","8"),
    director_trial("待风控终审","9"),

    open_card("待开卡","10"),
    edit_card("待编辑开卡信息","11"),
    apply_staging("待申请分期","12"),
    edit_staging("待编辑分期信息","13"),
    face_sign("待上传面签","14"),
    materials_sign("待上传签约资料","15"),

    materials_advance("待上传垫资资料","16"),
    advance_getOrder("待垫资领单","17"),
    advance_trial("待垫资审核","18"),
    finance_getOrder("待打款领单","19"),
    finance_pay("待打款","20"),
    materials_mortgage("待上传抵押资料","21"),
    mortgage_trial("待审核抵押资料","22"),
    end("结束","9999"),


    /**
     * 审批人员定义
     */
    System("系统","0"),
    seller("销售专员","000"),
    Financial_commissioner("金融专员","001"),
    Risk_controller("风控专员","002"),
    Risk_control_supervisor("风控主管","003"),

    Risk_control_director("风控总监","004"),
    advance_group("垫资组","005"),
    finance_group("财务","006"),
    backer_group("银行专员","007"),


    ;

    public static String getName(String code){
        for (WorkflowEnum flow : WorkflowEnum.values()) {
            if(flow.getCode().equals(code)){
                return flow.getName();
            }
        }
        return null;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    WorkflowEnum(String name, String code) {
        this.name = name;
        this.code = code;
    }

    private String code;
    private String name;

}
