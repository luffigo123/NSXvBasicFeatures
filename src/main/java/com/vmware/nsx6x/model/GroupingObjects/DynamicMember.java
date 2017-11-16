package com.vmware.nsx6x.model.GroupingObjects;

public class DynamicMember {
	protected String operator;
	protected String dynamicCriteria_operator;
	protected String key;
	protected String criteria;
	protected String value;
	
	/**
	 * 
	 */
	public DynamicMember() {
		super();
	}

	/**
	 * @param operator
	 * @param dynamicCriteria_operator
	 * @param key
	 * @param criteria
	 * @param value
	 */
	public DynamicMember(String operator, String dynamicCriteria_operator,
			String key, String criteria, String value) {
		super();
		this.operator = operator;
		this.dynamicCriteria_operator = dynamicCriteria_operator;
		this.key = key;
		this.criteria = criteria;
		this.value = value;
	}

	/**
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * @param operator the operator to set
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * @return the dynamicCriteria_operator
	 */
	public String getDynamicCriteria_operator() {
		return dynamicCriteria_operator;
	}

	/**
	 * @param dynamicCriteria_operator the dynamicCriteria_operator to set
	 */
	public void setDynamicCriteria_operator(String dynamicCriteria_operator) {
		this.dynamicCriteria_operator = dynamicCriteria_operator;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the criteria
	 */
	public String getCriteria() {
		return criteria;
	}

	/**
	 * @param criteria the criteria to set
	 */
	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
}
