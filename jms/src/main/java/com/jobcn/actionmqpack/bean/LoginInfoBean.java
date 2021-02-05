package com.jobcn.actionmqpack.bean;


/**
 * 互联网信息服务单位与平台进行数据交换时的登录实体Bean
 */
public class LoginInfoBean extends BaseJmsObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ICP_CODE;
	private String DATA_TYPE;
	private String SRC_IP;
	private String SRC_PORT;
	private String DST_IP;
	private String DST_PORT;
	private String USER_ID;
	private String USER_NAME;
	private String NICK_NAME;
	private String PASSWORD;
	private String MAC_ADDRESS;
	private String INNER_IP;
	private String ACTION_TIME;
	private String ACTION;
	public String getICP_CODE() {
		return ICP_CODE;
	}
	public void setICP_CODE(String iCP_CODE) {
		ICP_CODE = iCP_CODE;
	}
	public String getDATA_TYPE() {
		return DATA_TYPE;
	}
	public void setDATA_TYPE(String dATA_TYPE) {
		DATA_TYPE = dATA_TYPE;
	}
	public String getSRC_IP() {
		return SRC_IP;
	}
	public void setSRC_IP(String sRC_IP) {
		SRC_IP = sRC_IP;
	}
	public String getSRC_PORT() {
		return SRC_PORT;
	}
	public void setSRC_PORT(String sRC_PORT) {
		SRC_PORT = sRC_PORT;
	}
	public String getDST_IP() {
		return DST_IP;
	}
	public void setDST_IP(String dST_IP) {
		DST_IP = dST_IP;
	}
	public String getDST_PORT() {
		return DST_PORT;
	}
	public void setDST_PORT(String dST_PORT) {
		DST_PORT = dST_PORT;
	}
	public String getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
	}
	public String getUSER_NAME() {
		return USER_NAME;
	}
	public void setUSER_NAME(String uSER_NAME) {
		USER_NAME = uSER_NAME;
	}
	public String getNICK_NAME() {
		return NICK_NAME;
	}
	public void setNICK_NAME(String nICK_NAME) {
		NICK_NAME = nICK_NAME;
	}
	public String getPASSWORD() {
		return PASSWORD;
	}
	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}
	public String getMAC_ADDRESS() {
		return MAC_ADDRESS;
	}
	public void setMAC_ADDRESS(String mAC_ADDRESS) {
		MAC_ADDRESS = mAC_ADDRESS;
	}
	public String getINNER_IP() {
		return INNER_IP;
	}
	public void setINNER_IP(String iNNER_IP) {
		INNER_IP = iNNER_IP;
	}
	public String getACTION_TIME() {
		return ACTION_TIME;
	}
	public void setACTION_TIME(String aCTION_TIME) {
		ACTION_TIME = aCTION_TIME;
	}
	public String getACTION() {
		return ACTION;
	}
	public void setACTION(String aCTION) {
		ACTION = aCTION;
	}
	
}
