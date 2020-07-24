package com.bee.team.fastgo.project.constant;

/**
 * 验证token类型
 */
public enum TokenType {

    PRIVATE_TOKEN("private_token", "PRIVATE-TOKEN", "%s"),
    ACCESS_TOKEN("access_token", "Authorization", "Bearer %s");

    private final String tokenParamName;
    private final String tokenHeaderName;
    private final String tokenHeaderFormat;

    /**
     *
     * @param tokenParamName    验证时token名称
     * @param tokenHeaderName   验证时请求体里，token名称
     * @param tokenHeaderFormat 验证时请求头里，token名称
     */
    TokenType(String tokenParamName, String tokenHeaderName, String tokenHeaderFormat) {
        this.tokenParamName = tokenParamName;
        this.tokenHeaderName = tokenHeaderName;
        this.tokenHeaderFormat = tokenHeaderFormat;
    }

    public String getTokenParamName() {
        return tokenParamName;
    }

    public String getTokenHeaderName() {
        return tokenHeaderName;
    }

    public String getTokenHeaderFormat() {
        return tokenHeaderFormat;
    }


}
