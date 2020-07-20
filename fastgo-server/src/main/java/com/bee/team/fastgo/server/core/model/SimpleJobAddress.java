package com.bee.team.fastgo.server.core.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author luke
 * @date 16/9/30
 */
public class SimpleJobAddress {
    /**
     * 执行器地址类型：0=自动注册、1=手动录入
     **/
    private Integer addressType;
    /**
     * 执行器地址列表，多地址逗号分隔(手动录入)
     **/
    private String addressList;

    /**
     * 执行器地址列表(系统注册)
     **/
    private List<String> registryList;

    public List<String> getRegistryList() {
        if (addressList != null && addressList.trim().length() > 0) {
            registryList = new ArrayList<String>(Arrays.asList(addressList.split(",")));
        }
        return registryList;
    }

    public Integer getAddressType() {
        return addressType;
    }

    public void setAddressType(Integer addressType) {
        this.addressType = addressType;
    }

    public String getAddressList() {
        return addressList;
    }

    public void setAddressList(String addressList) {
        this.addressList = addressList;
    }

}
