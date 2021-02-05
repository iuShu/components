package com.jobcn.actionmqpack.bean;

/**
 * 互联网信息服务单位与平台进行数据交换时的登录实体Bean
 */
public class LoginTraceBean extends BaseJmsObject {
    private static final long serialVersionUID = 1L;
    
    private String reg_account = "";    //用户账号  Username
    private String account_id = "";     //用户ID  peraccountid
    private String corp_account_type = "";//合作账号类型
    private String corp_account_id = "";//合作账号
    /**
     * 登录平台
     * 分类与编码方法：10 浏览器 11 手机浏览器 12 PC浏览器 20 客户端 
     * 21 手机客户端 22 PC客户端 30 突网工具 31 手机突网工具 32 PC突网工具 99 其他类型
     */
    private String tooltype = "";
    private String terminal_model = ""; //登录终端型号（手机型号、操作系统版本）
    private String os_version = "";     //操作系统版本（IOS、android、微软等）
    private String log_type = "02";     //登录类型
    private String ip_address = "";     //登录IP
    private String port = "";           //登录端口号
    private String mac_address = "";    //登录MAC地址
    private String imei = "";           //登录IMEI
    private String imsi = "";           //登录IMSI
    private String hardwarestring = ""; //硬件特征串
    private String longitude = "";      //登录经度
    private String latitude = "";       //登录纬度
    private String login_time = "";     //登录时间
    
    
    /**用户账号  Username
     */
    public String getReg_account() {
        return reg_account;
    }

    /**用户账号  Username
     */
    public void setReg_account(String reg_account) {
        this.reg_account = reg_account;
    }

    /**
     * 用户ID  peraccountid
     * @return
     */
    public String getAccount_id() {
        return account_id;
    }

    /**
     * 用户ID  peraccountid
     * @param account_id
     */
    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    /**
     * 合作账号类型 暂无对应字段
     * @return
     */
    public String getCorp_account_type() {
        return corp_account_type;
    }

    /**
     * 合作账号类型 暂无对应字段
     * @param corp_account_type
     */
    public void setCorp_account_type(String corp_account_type) {
        this.corp_account_type = corp_account_type;
    }

    /**
     * 合作账号 暂无对应字段
     * @return
     */
    public String getCorp_account_id() {
        return corp_account_id;
    }

    /**
     * 合作账号 暂无对应字段
     * @param corp_account_id
     */
    public void setCorp_account_id(String corp_account_id) {
        this.corp_account_id = corp_account_id;
    }

    /**
     * 登录平台 
     *  表 jobcn90_log.dbo.per_loginLog 的 loginDevice 字段
     *  分类与编码方法：10 浏览器 11 手机浏览器 12 PC浏览器20 客户端 21 手机客户端 22 PC客户端 
     *  30 突网工具 31 手机突网工具 32 PC突网工具 99 其他类型
     */
    public String getTooltype() {
        return tooltype;
    }

    /**
     * 登录平台 
     *  表 jobcn90_log.dbo.per_loginLog 的 loginDevice 字段
     *  分类与编码方法：10 浏览器 11 手机浏览器 12 PC浏览器20 客户端 21 手机客户端 22 PC客户端 
     *  30 突网工具 31 手机突网工具 32 PC突网工具 99 其他类型
     */
    public void setTooltype(String tooltype) {
        this.tooltype = tooltype;
    }

    /**
     * 登录终端型号（手机型号、操作系统版本） 暂无对应字段
     * @return
     */
    public String getTerminal_model() {
        return terminal_model;
    }

    /**
     * 登录终端型号（手机型号、操作系统版本） 暂无对应字段
     * @param terminal_model
     */
    public void setTerminal_model(String terminal_model) {
        this.terminal_model = terminal_model;
    }

    /**
     * 操作系统版本（IOS、android、微软等） 暂无对应字段
     * @return
     */
    public String getOs_version() {
        return os_version;
    }

    /**
     * 操作系统版本（IOS、android、微软等） 暂无对应字段
     * @param os_version
     */
    public void setOs_version(String os_version) {
        this.os_version = os_version;
    }

    /**
     * 登录类型  02登录 或 99其他 或 空
     * @return
     */
    public String getLog_type() {
        return log_type;
    }

    /**
     * 登录类型  02登录 或 99其他 或 空
     * @param log_type
     */
    public void setLog_type(String log_type) {
        this.log_type = log_type;
    }

    /**
     * 登录IP （必填）采用无符号整型主机序表示，如：288036141
     * @return
     */
    public String getIp_address() {
        return ip_address;
    }

    /**
     * 登录IP （必填）采用无符号整型主机序表示，如：288036141
     * @param ip_address
     */
    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    /**
     * 登录端口号 (0~65535范围的整数)
     * @return
     */
    public String getPort() {
        return port;
    }

    /**
     * 登录端口号 (0~65535范围的整数)
     * @param port
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     * 登录MAC地址  类似00-E0-4C-3B-7D-2F
     * @return
     */
    public String getMac_address() {
        return mac_address;
    }

    /**
     * 登录MAC地址  类似00-E0-4C-3B-7D-2F
     * @param mac_address
     */
    public void setMac_address(String mac_address) {
        this.mac_address = mac_address;
    }

    /**
     * 登录IMEI 暂无对应字段
     * @return
     */
    public String getImei() {
        return imei;
    }

    /**
     * 登录IMEI 暂无对应字段
     * @param imei
     */
    public void setImei(String imei) {
        this.imei = imei;
    }

    /**
     * 登录IMSI  IMSI=MCC+MNC+MSIN  暂无对应字段
     * @return
     */
    public String getImsi() {
        return imsi;
    }

    /**
     * 登录IMSI  IMSI=MCC+MNC+MSIN  暂无对应字段
     * @param imsi
     */
    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    /**
     * 硬件特征串 暂无对应字段
     * @return
     */
    public String getHardwarestring() {
        return hardwarestring;
    }

    /**
     * 硬件特征串 暂无对应字段
     * @param hardwarestring
     */
    public void setHardwarestring(String hardwarestring) {
        this.hardwarestring = hardwarestring;
    }

    /**
     * 登录经度 
     * 度数采用3位整数5位小数形式,小数位数不足补零，方位采用正负符号形式，使用正符号表示东经（正符号省略），负符号表示西经。
     * 例如123.23000 表示东经123.23000度;-133.00000表示西经133.00000度。
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * 登录经度 
     * 度数采用3位整数5位小数形式,小数位数不足补零，方位采用正负符号形式，使用正符号表示东经（正符号省略），负符号表示西经。
     * 例如123.23000 表示东经123.23000度;-133.00000表示西经133.00000度。
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * 登录纬度 
     * 度数采用3位整数5位小数形式,小数位数不足补零，方位采用正负符号形式，使用正符号表示北纬（正符号省略），负符号表示南纬。
     * 例如123.23000 表示北纬123.23000度;-133.00000表示南纬133.00000度。
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * 登录纬度 
     * 度数采用3位整数5位小数形式,小数位数不足补零，方位采用正负符号形式，使用正符号表示北纬（正符号省略），负符号表示南纬。
     * 例如123.23000 表示北纬123.23000度;-133.00000表示南纬133.00000度。
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * 登录时间 (绝对秒数)
     * @return
     */
    public String getLogin_time() {
        return login_time;
    }

    /**
     * 登录时间 (绝对秒数)
     * @param login_time
     */
    public void setLogin_time(String login_time) {
        this.login_time = login_time;
    }

    @Override
    public String toString() {
        return "LoginTraceBean{" +
                "reg_account='" + reg_account + '\'' +
                ", account_id='" + account_id + '\'' +
                ", corp_account_type='" + corp_account_type + '\'' +
                ", corp_account_id='" + corp_account_id + '\'' +
                ", tooltype='" + tooltype + '\'' +
                ", terminal_model='" + terminal_model + '\'' +
                ", os_version='" + os_version + '\'' +
                ", log_type='" + log_type + '\'' +
                ", ip_address='" + ip_address + '\'' +
                ", port='" + port + '\'' +
                ", mac_address='" + mac_address + '\'' +
                ", imei='" + imei + '\'' +
                ", imsi='" + imsi + '\'' +
                ", hardwarestring='" + hardwarestring + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", login_time='" + login_time + '\'' +
                '}';
    }
}
