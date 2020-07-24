package com.bee.team.fastgo.project.constant;

/**
 * 请求类型
 */
public enum RequestMethod {

    GET, POST;

    public static String prettyValues() {
        RequestMethod[] methods = RequestMethod.values();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < methods.length; i++) {
            RequestMethod method = methods[i];
            builder.append(method.toString());

            if (i != methods.length - 1) {
                builder.append(", ");
            }
        }
        return builder.toString();
    }

}
