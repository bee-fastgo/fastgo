package com.bee.team.fastgo.model;

import com.alibaba.lava.base.LavaExample;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServerSystemInfoDoExample extends LavaExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_server_system_info
     *
     * @mbg.generated Sat Jul 25 17:21:03 CST 2020
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_server_system_info
     *
     * @mbg.generated Sat Jul 25 17:21:03 CST 2020
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_server_system_info
     *
     * @mbg.generated Sat Jul 25 17:21:03 CST 2020
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_server_system_info
     *
     * @mbg.generated Sat Jul 25 17:21:03 CST 2020
     */
    public ServerSystemInfoDoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_server_system_info
     *
     * @mbg.generated Sat Jul 25 17:21:03 CST 2020
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_server_system_info
     *
     * @mbg.generated Sat Jul 25 17:21:03 CST 2020
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_server_system_info
     *
     * @mbg.generated Sat Jul 25 17:21:03 CST 2020
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_server_system_info
     *
     * @mbg.generated Sat Jul 25 17:21:03 CST 2020
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_server_system_info
     *
     * @mbg.generated Sat Jul 25 17:21:03 CST 2020
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_server_system_info
     *
     * @mbg.generated Sat Jul 25 17:21:03 CST 2020
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_server_system_info
     *
     * @mbg.generated Sat Jul 25 17:21:03 CST 2020
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_server_system_info
     *
     * @mbg.generated Sat Jul 25 17:21:03 CST 2020
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_server_system_info
     *
     * @mbg.generated Sat Jul 25 17:21:03 CST 2020
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_server_system_info
     *
     * @mbg.generated Sat Jul 25 17:21:03 CST 2020
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_server_system_info
     *
     * @mbg.generated Sat Jul 25 17:21:03 CST 2020
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andServerIpIsNull() {
            addCriterion("serverIp is null");
            return (Criteria) this;
        }

        public Criteria andServerIpIsNotNull() {
            addCriterion("serverIp is not null");
            return (Criteria) this;
        }

        public Criteria andServerIpEqualTo(String value) {
            addCriterion("serverIp =", value, "serverIp");
            return (Criteria) this;
        }

        public Criteria andServerIpNotEqualTo(String value) {
            addCriterion("serverIp <>", value, "serverIp");
            return (Criteria) this;
        }

        public Criteria andServerIpGreaterThan(String value) {
            addCriterion("serverIp >", value, "serverIp");
            return (Criteria) this;
        }

        public Criteria andServerIpGreaterThanOrEqualTo(String value) {
            addCriterion("serverIp >=", value, "serverIp");
            return (Criteria) this;
        }

        public Criteria andServerIpLessThan(String value) {
            addCriterion("serverIp <", value, "serverIp");
            return (Criteria) this;
        }

        public Criteria andServerIpLessThanOrEqualTo(String value) {
            addCriterion("serverIp <=", value, "serverIp");
            return (Criteria) this;
        }

        public Criteria andServerIpLike(String value) {
            addCriterion("serverIp like", value, "serverIp");
            return (Criteria) this;
        }

        public Criteria andServerIpNotLike(String value) {
            addCriterion("serverIp not like", value, "serverIp");
            return (Criteria) this;
        }

        public Criteria andServerIpIn(List<String> values) {
            addCriterion("serverIp in", values, "serverIp");
            return (Criteria) this;
        }

        public Criteria andServerIpNotIn(List<String> values) {
            addCriterion("serverIp not in", values, "serverIp");
            return (Criteria) this;
        }

        public Criteria andServerIpBetween(String value1, String value2) {
            addCriterion("serverIp between", value1, value2, "serverIp");
            return (Criteria) this;
        }

        public Criteria andServerIpNotBetween(String value1, String value2) {
            addCriterion("serverIp not between", value1, value2, "serverIp");
            return (Criteria) this;
        }

        public Criteria andVersionIsNull() {
            addCriterion("version is null");
            return (Criteria) this;
        }

        public Criteria andVersionIsNotNull() {
            addCriterion("version is not null");
            return (Criteria) this;
        }

        public Criteria andVersionEqualTo(String value) {
            addCriterion("version =", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotEqualTo(String value) {
            addCriterion("version <>", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThan(String value) {
            addCriterion("version >", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThanOrEqualTo(String value) {
            addCriterion("version >=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThan(String value) {
            addCriterion("version <", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThanOrEqualTo(String value) {
            addCriterion("version <=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLike(String value) {
            addCriterion("version like", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotLike(String value) {
            addCriterion("version not like", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionIn(List<String> values) {
            addCriterion("version in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotIn(List<String> values) {
            addCriterion("version not in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionBetween(String value1, String value2) {
            addCriterion("version between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotBetween(String value1, String value2) {
            addCriterion("version not between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andVersionDetailIsNull() {
            addCriterion("versionDetail is null");
            return (Criteria) this;
        }

        public Criteria andVersionDetailIsNotNull() {
            addCriterion("versionDetail is not null");
            return (Criteria) this;
        }

        public Criteria andVersionDetailEqualTo(String value) {
            addCriterion("versionDetail =", value, "versionDetail");
            return (Criteria) this;
        }

        public Criteria andVersionDetailNotEqualTo(String value) {
            addCriterion("versionDetail <>", value, "versionDetail");
            return (Criteria) this;
        }

        public Criteria andVersionDetailGreaterThan(String value) {
            addCriterion("versionDetail >", value, "versionDetail");
            return (Criteria) this;
        }

        public Criteria andVersionDetailGreaterThanOrEqualTo(String value) {
            addCriterion("versionDetail >=", value, "versionDetail");
            return (Criteria) this;
        }

        public Criteria andVersionDetailLessThan(String value) {
            addCriterion("versionDetail <", value, "versionDetail");
            return (Criteria) this;
        }

        public Criteria andVersionDetailLessThanOrEqualTo(String value) {
            addCriterion("versionDetail <=", value, "versionDetail");
            return (Criteria) this;
        }

        public Criteria andVersionDetailLike(String value) {
            addCriterion("versionDetail like", value, "versionDetail");
            return (Criteria) this;
        }

        public Criteria andVersionDetailNotLike(String value) {
            addCriterion("versionDetail not like", value, "versionDetail");
            return (Criteria) this;
        }

        public Criteria andVersionDetailIn(List<String> values) {
            addCriterion("versionDetail in", values, "versionDetail");
            return (Criteria) this;
        }

        public Criteria andVersionDetailNotIn(List<String> values) {
            addCriterion("versionDetail not in", values, "versionDetail");
            return (Criteria) this;
        }

        public Criteria andVersionDetailBetween(String value1, String value2) {
            addCriterion("versionDetail between", value1, value2, "versionDetail");
            return (Criteria) this;
        }

        public Criteria andVersionDetailNotBetween(String value1, String value2) {
            addCriterion("versionDetail not between", value1, value2, "versionDetail");
            return (Criteria) this;
        }

        public Criteria andTotalMemIsNull() {
            addCriterion("totalMem is null");
            return (Criteria) this;
        }

        public Criteria andTotalMemIsNotNull() {
            addCriterion("totalMem is not null");
            return (Criteria) this;
        }

        public Criteria andTotalMemEqualTo(String value) {
            addCriterion("totalMem =", value, "totalMem");
            return (Criteria) this;
        }

        public Criteria andTotalMemNotEqualTo(String value) {
            addCriterion("totalMem <>", value, "totalMem");
            return (Criteria) this;
        }

        public Criteria andTotalMemGreaterThan(String value) {
            addCriterion("totalMem >", value, "totalMem");
            return (Criteria) this;
        }

        public Criteria andTotalMemGreaterThanOrEqualTo(String value) {
            addCriterion("totalMem >=", value, "totalMem");
            return (Criteria) this;
        }

        public Criteria andTotalMemLessThan(String value) {
            addCriterion("totalMem <", value, "totalMem");
            return (Criteria) this;
        }

        public Criteria andTotalMemLessThanOrEqualTo(String value) {
            addCriterion("totalMem <=", value, "totalMem");
            return (Criteria) this;
        }

        public Criteria andTotalMemLike(String value) {
            addCriterion("totalMem like", value, "totalMem");
            return (Criteria) this;
        }

        public Criteria andTotalMemNotLike(String value) {
            addCriterion("totalMem not like", value, "totalMem");
            return (Criteria) this;
        }

        public Criteria andTotalMemIn(List<String> values) {
            addCriterion("totalMem in", values, "totalMem");
            return (Criteria) this;
        }

        public Criteria andTotalMemNotIn(List<String> values) {
            addCriterion("totalMem not in", values, "totalMem");
            return (Criteria) this;
        }

        public Criteria andTotalMemBetween(String value1, String value2) {
            addCriterion("totalMem between", value1, value2, "totalMem");
            return (Criteria) this;
        }

        public Criteria andTotalMemNotBetween(String value1, String value2) {
            addCriterion("totalMem not between", value1, value2, "totalMem");
            return (Criteria) this;
        }

        public Criteria andCpuCoreNumIsNull() {
            addCriterion("cpuCoreNum is null");
            return (Criteria) this;
        }

        public Criteria andCpuCoreNumIsNotNull() {
            addCriterion("cpuCoreNum is not null");
            return (Criteria) this;
        }

        public Criteria andCpuCoreNumEqualTo(Integer value) {
            addCriterion("cpuCoreNum =", value, "cpuCoreNum");
            return (Criteria) this;
        }

        public Criteria andCpuCoreNumNotEqualTo(Integer value) {
            addCriterion("cpuCoreNum <>", value, "cpuCoreNum");
            return (Criteria) this;
        }

        public Criteria andCpuCoreNumGreaterThan(Integer value) {
            addCriterion("cpuCoreNum >", value, "cpuCoreNum");
            return (Criteria) this;
        }

        public Criteria andCpuCoreNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("cpuCoreNum >=", value, "cpuCoreNum");
            return (Criteria) this;
        }

        public Criteria andCpuCoreNumLessThan(Integer value) {
            addCriterion("cpuCoreNum <", value, "cpuCoreNum");
            return (Criteria) this;
        }

        public Criteria andCpuCoreNumLessThanOrEqualTo(Integer value) {
            addCriterion("cpuCoreNum <=", value, "cpuCoreNum");
            return (Criteria) this;
        }

        public Criteria andCpuCoreNumIn(List<Integer> values) {
            addCriterion("cpuCoreNum in", values, "cpuCoreNum");
            return (Criteria) this;
        }

        public Criteria andCpuCoreNumNotIn(List<Integer> values) {
            addCriterion("cpuCoreNum not in", values, "cpuCoreNum");
            return (Criteria) this;
        }

        public Criteria andCpuCoreNumBetween(Integer value1, Integer value2) {
            addCriterion("cpuCoreNum between", value1, value2, "cpuCoreNum");
            return (Criteria) this;
        }

        public Criteria andCpuCoreNumNotBetween(Integer value1, Integer value2) {
            addCriterion("cpuCoreNum not between", value1, value2, "cpuCoreNum");
            return (Criteria) this;
        }

        public Criteria andCpuModelIsNull() {
            addCriterion("cpuModel is null");
            return (Criteria) this;
        }

        public Criteria andCpuModelIsNotNull() {
            addCriterion("cpuModel is not null");
            return (Criteria) this;
        }

        public Criteria andCpuModelEqualTo(String value) {
            addCriterion("cpuModel =", value, "cpuModel");
            return (Criteria) this;
        }

        public Criteria andCpuModelNotEqualTo(String value) {
            addCriterion("cpuModel <>", value, "cpuModel");
            return (Criteria) this;
        }

        public Criteria andCpuModelGreaterThan(String value) {
            addCriterion("cpuModel >", value, "cpuModel");
            return (Criteria) this;
        }

        public Criteria andCpuModelGreaterThanOrEqualTo(String value) {
            addCriterion("cpuModel >=", value, "cpuModel");
            return (Criteria) this;
        }

        public Criteria andCpuModelLessThan(String value) {
            addCriterion("cpuModel <", value, "cpuModel");
            return (Criteria) this;
        }

        public Criteria andCpuModelLessThanOrEqualTo(String value) {
            addCriterion("cpuModel <=", value, "cpuModel");
            return (Criteria) this;
        }

        public Criteria andCpuModelLike(String value) {
            addCriterion("cpuModel like", value, "cpuModel");
            return (Criteria) this;
        }

        public Criteria andCpuModelNotLike(String value) {
            addCriterion("cpuModel not like", value, "cpuModel");
            return (Criteria) this;
        }

        public Criteria andCpuModelIn(List<String> values) {
            addCriterion("cpuModel in", values, "cpuModel");
            return (Criteria) this;
        }

        public Criteria andCpuModelNotIn(List<String> values) {
            addCriterion("cpuModel not in", values, "cpuModel");
            return (Criteria) this;
        }

        public Criteria andCpuModelBetween(String value1, String value2) {
            addCriterion("cpuModel between", value1, value2, "cpuModel");
            return (Criteria) this;
        }

        public Criteria andCpuModelNotBetween(String value1, String value2) {
            addCriterion("cpuModel not between", value1, value2, "cpuModel");
            return (Criteria) this;
        }

        public Criteria andStateIsNull() {
            addCriterion("state is null");
            return (Criteria) this;
        }

        public Criteria andStateIsNotNull() {
            addCriterion("state is not null");
            return (Criteria) this;
        }

        public Criteria andStateEqualTo(Integer value) {
            addCriterion("state =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(Integer value) {
            addCriterion("state <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(Integer value) {
            addCriterion("state >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("state >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(Integer value) {
            addCriterion("state <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(Integer value) {
            addCriterion("state <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<Integer> values) {
            addCriterion("state in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<Integer> values) {
            addCriterion("state not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(Integer value1, Integer value2) {
            addCriterion("state between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(Integer value1, Integer value2) {
            addCriterion("state not between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andIdIsNull() {
            addCriterion("ID is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("ID is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("ID =", value, "Id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("ID <>", value, "Id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("ID >", value, "Id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("ID >=", value, "Id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("ID <", value, "Id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("ID <=", value, "Id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("ID in", values, "Id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("ID not in", values, "Id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("ID between", value1, value2, "Id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("ID not between", value1, value2, "Id");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIsNull() {
            addCriterion("GMT_CREATE is null");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIsNotNull() {
            addCriterion("GMT_CREATE is not null");
            return (Criteria) this;
        }

        public Criteria andGmtCreateEqualTo(Date value) {
            addCriterion("GMT_CREATE =", value, "GmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotEqualTo(Date value) {
            addCriterion("GMT_CREATE <>", value, "GmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateGreaterThan(Date value) {
            addCriterion("GMT_CREATE >", value, "GmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateGreaterThanOrEqualTo(Date value) {
            addCriterion("GMT_CREATE >=", value, "GmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateLessThan(Date value) {
            addCriterion("GMT_CREATE <", value, "GmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateLessThanOrEqualTo(Date value) {
            addCriterion("GMT_CREATE <=", value, "GmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIn(List<Date> values) {
            addCriterion("GMT_CREATE in", values, "GmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotIn(List<Date> values) {
            addCriterion("GMT_CREATE not in", values, "GmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateBetween(Date value1, Date value2) {
            addCriterion("GMT_CREATE between", value1, value2, "GmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotBetween(Date value1, Date value2) {
            addCriterion("GMT_CREATE not between", value1, value2, "GmtCreate");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNull() {
            addCriterion("CREATOR is null");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNotNull() {
            addCriterion("CREATOR is not null");
            return (Criteria) this;
        }

        public Criteria andCreatorEqualTo(String value) {
            addCriterion("CREATOR =", value, "Creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotEqualTo(String value) {
            addCriterion("CREATOR <>", value, "Creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThan(String value) {
            addCriterion("CREATOR >", value, "Creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThanOrEqualTo(String value) {
            addCriterion("CREATOR >=", value, "Creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThan(String value) {
            addCriterion("CREATOR <", value, "Creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThanOrEqualTo(String value) {
            addCriterion("CREATOR <=", value, "Creator");
            return (Criteria) this;
        }

        public Criteria andCreatorIn(List<String> values) {
            addCriterion("CREATOR in", values, "Creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotIn(List<String> values) {
            addCriterion("CREATOR not in", values, "Creator");
            return (Criteria) this;
        }

        public Criteria andCreatorBetween(String value1, String value2) {
            addCriterion("CREATOR between", value1, value2, "Creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotBetween(String value1, String value2) {
            addCriterion("CREATOR not between", value1, value2, "Creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLike(String value) {
            addCriterion("CREATOR like", value, "Creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotLike(String value) {
            addCriterion("CREATOR not like", value, "Creator");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIsNull() {
            addCriterion("GMT_MODIFIED is null");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIsNotNull() {
            addCriterion("GMT_MODIFIED is not null");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedEqualTo(Date value) {
            addCriterion("GMT_MODIFIED =", value, "GmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotEqualTo(Date value) {
            addCriterion("GMT_MODIFIED <>", value, "GmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedGreaterThan(Date value) {
            addCriterion("GMT_MODIFIED >", value, "GmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedGreaterThanOrEqualTo(Date value) {
            addCriterion("GMT_MODIFIED >=", value, "GmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedLessThan(Date value) {
            addCriterion("GMT_MODIFIED <", value, "GmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedLessThanOrEqualTo(Date value) {
            addCriterion("GMT_MODIFIED <=", value, "GmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIn(List<Date> values) {
            addCriterion("GMT_MODIFIED in", values, "GmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotIn(List<Date> values) {
            addCriterion("GMT_MODIFIED not in", values, "GmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedBetween(Date value1, Date value2) {
            addCriterion("GMT_MODIFIED between", value1, value2, "GmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotBetween(Date value1, Date value2) {
            addCriterion("GMT_MODIFIED not between", value1, value2, "GmtModified");
            return (Criteria) this;
        }

        public Criteria andModifierIsNull() {
            addCriterion("MODIFIER is null");
            return (Criteria) this;
        }

        public Criteria andModifierIsNotNull() {
            addCriterion("MODIFIER is not null");
            return (Criteria) this;
        }

        public Criteria andModifierEqualTo(String value) {
            addCriterion("MODIFIER =", value, "Modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotEqualTo(String value) {
            addCriterion("MODIFIER <>", value, "Modifier");
            return (Criteria) this;
        }

        public Criteria andModifierGreaterThan(String value) {
            addCriterion("MODIFIER >", value, "Modifier");
            return (Criteria) this;
        }

        public Criteria andModifierGreaterThanOrEqualTo(String value) {
            addCriterion("MODIFIER >=", value, "Modifier");
            return (Criteria) this;
        }

        public Criteria andModifierLessThan(String value) {
            addCriterion("MODIFIER <", value, "Modifier");
            return (Criteria) this;
        }

        public Criteria andModifierLessThanOrEqualTo(String value) {
            addCriterion("MODIFIER <=", value, "Modifier");
            return (Criteria) this;
        }

        public Criteria andModifierIn(List<String> values) {
            addCriterion("MODIFIER in", values, "Modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotIn(List<String> values) {
            addCriterion("MODIFIER not in", values, "Modifier");
            return (Criteria) this;
        }

        public Criteria andModifierBetween(String value1, String value2) {
            addCriterion("MODIFIER between", value1, value2, "Modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotBetween(String value1, String value2) {
            addCriterion("MODIFIER not between", value1, value2, "Modifier");
            return (Criteria) this;
        }

        public Criteria andModifierLike(String value) {
            addCriterion("MODIFIER like", value, "Modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotLike(String value) {
            addCriterion("MODIFIER not like", value, "Modifier");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIsNull() {
            addCriterion("IS_DELETED is null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIsNotNull() {
            addCriterion("IS_DELETED is not null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedEqualTo(String value) {
            addCriterion("IS_DELETED =", value, "IsDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotEqualTo(String value) {
            addCriterion("IS_DELETED <>", value, "IsDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThan(String value) {
            addCriterion("IS_DELETED >", value, "IsDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThanOrEqualTo(String value) {
            addCriterion("IS_DELETED >=", value, "IsDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThan(String value) {
            addCriterion("IS_DELETED <", value, "IsDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThanOrEqualTo(String value) {
            addCriterion("IS_DELETED <=", value, "IsDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIn(List<String> values) {
            addCriterion("IS_DELETED in", values, "IsDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotIn(List<String> values) {
            addCriterion("IS_DELETED not in", values, "IsDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedBetween(String value1, String value2) {
            addCriterion("IS_DELETED between", value1, value2, "IsDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotBetween(String value1, String value2) {
            addCriterion("IS_DELETED not between", value1, value2, "IsDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLike(String value) {
            addCriterion("IS_DELETED like", value, "IsDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotLike(String value) {
            addCriterion("IS_DELETED not like", value, "IsDeleted");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_server_system_info
     *
     * @mbg.generated do_not_delete_during_merge Sat Jul 25 17:21:03 CST 2020
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_server_system_info
     *
     * @mbg.generated Sat Jul 25 17:21:03 CST 2020
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}