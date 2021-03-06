package com.bee.team.fastgo.model;

import com.alibaba.lava.base.LavaExample;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServerScriptDoExample extends LavaExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_server_script
     *
     * @mbg.generated Mon Jul 27 13:30:53 CST 2020
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_server_script
     *
     * @mbg.generated Mon Jul 27 13:30:53 CST 2020
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_server_script
     *
     * @mbg.generated Mon Jul 27 13:30:53 CST 2020
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_server_script
     *
     * @mbg.generated Mon Jul 27 13:30:53 CST 2020
     */
    public ServerScriptDoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_server_script
     *
     * @mbg.generated Mon Jul 27 13:30:53 CST 2020
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_server_script
     *
     * @mbg.generated Mon Jul 27 13:30:53 CST 2020
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_server_script
     *
     * @mbg.generated Mon Jul 27 13:30:53 CST 2020
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_server_script
     *
     * @mbg.generated Mon Jul 27 13:30:53 CST 2020
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_server_script
     *
     * @mbg.generated Mon Jul 27 13:30:53 CST 2020
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_server_script
     *
     * @mbg.generated Mon Jul 27 13:30:53 CST 2020
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_server_script
     *
     * @mbg.generated Mon Jul 27 13:30:53 CST 2020
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_server_script
     *
     * @mbg.generated Mon Jul 27 13:30:53 CST 2020
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
     * This method corresponds to the database table t_server_script
     *
     * @mbg.generated Mon Jul 27 13:30:53 CST 2020
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_server_script
     *
     * @mbg.generated Mon Jul 27 13:30:53 CST 2020
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_server_script
     *
     * @mbg.generated Mon Jul 27 13:30:53 CST 2020
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

        public Criteria andScriptKeyIsNull() {
            addCriterion("scriptKey is null");
            return (Criteria) this;
        }

        public Criteria andScriptKeyIsNotNull() {
            addCriterion("scriptKey is not null");
            return (Criteria) this;
        }

        public Criteria andScriptKeyEqualTo(String value) {
            addCriterion("scriptKey =", value, "scriptKey");
            return (Criteria) this;
        }

        public Criteria andScriptKeyNotEqualTo(String value) {
            addCriterion("scriptKey <>", value, "scriptKey");
            return (Criteria) this;
        }

        public Criteria andScriptKeyGreaterThan(String value) {
            addCriterion("scriptKey >", value, "scriptKey");
            return (Criteria) this;
        }

        public Criteria andScriptKeyGreaterThanOrEqualTo(String value) {
            addCriterion("scriptKey >=", value, "scriptKey");
            return (Criteria) this;
        }

        public Criteria andScriptKeyLessThan(String value) {
            addCriterion("scriptKey <", value, "scriptKey");
            return (Criteria) this;
        }

        public Criteria andScriptKeyLessThanOrEqualTo(String value) {
            addCriterion("scriptKey <=", value, "scriptKey");
            return (Criteria) this;
        }

        public Criteria andScriptKeyLike(String value) {
            addCriterion("scriptKey like", value, "scriptKey");
            return (Criteria) this;
        }

        public Criteria andScriptKeyNotLike(String value) {
            addCriterion("scriptKey not like", value, "scriptKey");
            return (Criteria) this;
        }

        public Criteria andScriptKeyIn(List<String> values) {
            addCriterion("scriptKey in", values, "scriptKey");
            return (Criteria) this;
        }

        public Criteria andScriptKeyNotIn(List<String> values) {
            addCriterion("scriptKey not in", values, "scriptKey");
            return (Criteria) this;
        }

        public Criteria andScriptKeyBetween(String value1, String value2) {
            addCriterion("scriptKey between", value1, value2, "scriptKey");
            return (Criteria) this;
        }

        public Criteria andScriptKeyNotBetween(String value1, String value2) {
            addCriterion("scriptKey not between", value1, value2, "scriptKey");
            return (Criteria) this;
        }

        public Criteria andScriptNameIsNull() {
            addCriterion("scriptName is null");
            return (Criteria) this;
        }

        public Criteria andScriptNameIsNotNull() {
            addCriterion("scriptName is not null");
            return (Criteria) this;
        }

        public Criteria andScriptNameEqualTo(String value) {
            addCriterion("scriptName =", value, "scriptName");
            return (Criteria) this;
        }

        public Criteria andScriptNameNotEqualTo(String value) {
            addCriterion("scriptName <>", value, "scriptName");
            return (Criteria) this;
        }

        public Criteria andScriptNameGreaterThan(String value) {
            addCriterion("scriptName >", value, "scriptName");
            return (Criteria) this;
        }

        public Criteria andScriptNameGreaterThanOrEqualTo(String value) {
            addCriterion("scriptName >=", value, "scriptName");
            return (Criteria) this;
        }

        public Criteria andScriptNameLessThan(String value) {
            addCriterion("scriptName <", value, "scriptName");
            return (Criteria) this;
        }

        public Criteria andScriptNameLessThanOrEqualTo(String value) {
            addCriterion("scriptName <=", value, "scriptName");
            return (Criteria) this;
        }

        public Criteria andScriptNameLike(String value) {
            addCriterion("scriptName like", value, "scriptName");
            return (Criteria) this;
        }

        public Criteria andScriptNameNotLike(String value) {
            addCriterion("scriptName not like", value, "scriptName");
            return (Criteria) this;
        }

        public Criteria andScriptNameIn(List<String> values) {
            addCriterion("scriptName in", values, "scriptName");
            return (Criteria) this;
        }

        public Criteria andScriptNameNotIn(List<String> values) {
            addCriterion("scriptName not in", values, "scriptName");
            return (Criteria) this;
        }

        public Criteria andScriptNameBetween(String value1, String value2) {
            addCriterion("scriptName between", value1, value2, "scriptName");
            return (Criteria) this;
        }

        public Criteria andScriptNameNotBetween(String value1, String value2) {
            addCriterion("scriptName not between", value1, value2, "scriptName");
            return (Criteria) this;
        }

        public Criteria andSoftwareNameIsNull() {
            addCriterion("softwareName is null");
            return (Criteria) this;
        }

        public Criteria andSoftwareNameIsNotNull() {
            addCriterion("softwareName is not null");
            return (Criteria) this;
        }

        public Criteria andSoftwareNameEqualTo(String value) {
            addCriterion("softwareName =", value, "softwareName");
            return (Criteria) this;
        }

        public Criteria andSoftwareNameNotEqualTo(String value) {
            addCriterion("softwareName <>", value, "softwareName");
            return (Criteria) this;
        }

        public Criteria andSoftwareNameGreaterThan(String value) {
            addCriterion("softwareName >", value, "softwareName");
            return (Criteria) this;
        }

        public Criteria andSoftwareNameGreaterThanOrEqualTo(String value) {
            addCriterion("softwareName >=", value, "softwareName");
            return (Criteria) this;
        }

        public Criteria andSoftwareNameLessThan(String value) {
            addCriterion("softwareName <", value, "softwareName");
            return (Criteria) this;
        }

        public Criteria andSoftwareNameLessThanOrEqualTo(String value) {
            addCriterion("softwareName <=", value, "softwareName");
            return (Criteria) this;
        }

        public Criteria andSoftwareNameLike(String value) {
            addCriterion("softwareName like", value, "softwareName");
            return (Criteria) this;
        }

        public Criteria andSoftwareNameNotLike(String value) {
            addCriterion("softwareName not like", value, "softwareName");
            return (Criteria) this;
        }

        public Criteria andSoftwareNameIn(List<String> values) {
            addCriterion("softwareName in", values, "softwareName");
            return (Criteria) this;
        }

        public Criteria andSoftwareNameNotIn(List<String> values) {
            addCriterion("softwareName not in", values, "softwareName");
            return (Criteria) this;
        }

        public Criteria andSoftwareNameBetween(String value1, String value2) {
            addCriterion("softwareName between", value1, value2, "softwareName");
            return (Criteria) this;
        }

        public Criteria andSoftwareNameNotBetween(String value1, String value2) {
            addCriterion("softwareName not between", value1, value2, "softwareName");
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

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(String value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("type like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("type not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andScriptIsNull() {
            addCriterion("script is null");
            return (Criteria) this;
        }

        public Criteria andScriptIsNotNull() {
            addCriterion("script is not null");
            return (Criteria) this;
        }

        public Criteria andScriptEqualTo(String value) {
            addCriterion("script =", value, "script");
            return (Criteria) this;
        }

        public Criteria andScriptNotEqualTo(String value) {
            addCriterion("script <>", value, "script");
            return (Criteria) this;
        }

        public Criteria andScriptGreaterThan(String value) {
            addCriterion("script >", value, "script");
            return (Criteria) this;
        }

        public Criteria andScriptGreaterThanOrEqualTo(String value) {
            addCriterion("script >=", value, "script");
            return (Criteria) this;
        }

        public Criteria andScriptLessThan(String value) {
            addCriterion("script <", value, "script");
            return (Criteria) this;
        }

        public Criteria andScriptLessThanOrEqualTo(String value) {
            addCriterion("script <=", value, "script");
            return (Criteria) this;
        }

        public Criteria andScriptLike(String value) {
            addCriterion("script like", value, "script");
            return (Criteria) this;
        }

        public Criteria andScriptNotLike(String value) {
            addCriterion("script not like", value, "script");
            return (Criteria) this;
        }

        public Criteria andScriptIn(List<String> values) {
            addCriterion("script in", values, "script");
            return (Criteria) this;
        }

        public Criteria andScriptNotIn(List<String> values) {
            addCriterion("script not in", values, "script");
            return (Criteria) this;
        }

        public Criteria andScriptBetween(String value1, String value2) {
            addCriterion("script between", value1, value2, "script");
            return (Criteria) this;
        }

        public Criteria andScriptNotBetween(String value1, String value2) {
            addCriterion("script not between", value1, value2, "script");
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
     * This class corresponds to the database table t_server_script
     *
     * @mbg.generated do_not_delete_during_merge Mon Jul 27 13:30:53 CST 2020
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_server_script
     *
     * @mbg.generated Mon Jul 27 13:30:53 CST 2020
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