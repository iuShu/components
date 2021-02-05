package com.jobcn.actionmqpack.bean;

/**
 * 互联网信息服务单位与平台进行数据交换时的注册实体Bean
 */
public class RegisterTraceBean extends BaseJmsObject {
    private static final long serialVersionUID = 1L;
    
    private String reg_account = "";    //用户名
    private String accounr_id = "";     //用户ID
    private String auth_type = "";      //实名认证方式
    private String certificate_type = "";//实名认证身份类型  
    private String certificate_code = "";//实名认证身份ID
    private String user_name = "";      //姓名 
    private String reg_nickname = "";   //昵称
    private String icon = "";           //头像
    private String email = "";          //电子邮箱   
    private String contactor_tel = "";  //手机号
    private String ip_address = "";     //注册IP   
    private String port = "";           //注册端口号 
    private String mac_address = "";    //注册MAC 
    private String imei = "";           //注册IMEI
    private String hardwarestring = ""; //注册硬件特征串
    private String longitude = "";      //注册经度  
    private String latitude = "";       //注册纬度  
    private String regtime = "";        //注册时间   
    private String last_time = "";      //最后更新时间 
    private String action_type = "";    //动作类型  93 新增，95 修改/更新
    private String action_time = "";    //动作时间 
    
    /**用户名 Username
     */
    public String getReg_account() {
        return reg_account;
    }
    /**用户名 Username
     */
    public void setReg_account(String reg_account) {
        this.reg_account = reg_account;
    }
    /**用户ID peraccountid
     */
    public String getAccounr_id() {
        return accounr_id;
    }
    /**用户ID peraccountid
     */
    public void setAccounr_id(String accounr_id) {
        this.accounr_id = accounr_id;
    }
    /**实名认证方式 
     * 代码表：01 管理类 02 监控类 03 管控类 04 侦控类 05 侦查类 06 特侦类 07 综合类 08 通用类 99 其他
     */
    public String getAuth_type() {
        return auth_type;
    }
    /**实名认证方式 
     * 代码表：01 管理类 02 监控类 03 管控类 04 侦控类 05 侦查类 06 特侦类 07 综合类 08 通用类 99 其他
     */
    public void setAuth_type(String auth_type) {
        this.auth_type = auth_type;
    }
    /**
     * 实名认证身份类型 
     * 代码表：采用GA/T 517－2004《常用证件代码》
     */
    public String getCertificate_type() {
        return certificate_type;
    }
    /**
     * 实名认证身份类型 
     * 代码表：采用GA/T 517－2004《常用证件代码》
     */
    public void setCertificate_type(String certificate_type) {
        this.certificate_type = certificate_type;
    }
    /**
     * 实名认证身份ID 
     */
    public String getCertificate_code() {
        return certificate_code;
    }
    /**
     * 实名认证身份ID 
     */
    public void setCertificate_code(String certificate_code) {
        this.certificate_code = certificate_code;
    }
    /**
     * 通用的网络身份帐号信息
     * [jobcn90_Jobseeker].[dbo].[Per_Resume_i18n].[PerName]
     */
    public String getUser_name() {
        return user_name;
    }
    /**
     * 通用的网络身份帐号信息
     * [jobcn90_Jobseeker].[dbo].[Per_Resume_i18n].[PerName]
     */
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
    /**
     * 昵称 (BASE64编码)
     */
    public String getReg_nickname() {
        return reg_nickname;
    }
    /**
     * 昵称 (BASE64编码)
     */
    public void setReg_nickname(String reg_nickname) {
        this.reg_nickname = reg_nickname;
    }
    /**
     * 头像URL
     */
    public String getIcon() {
        return icon;
    }
    /**
     * 头像URL
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }
    /**
     * 电子邮箱
     * [jobcn90_Jobseeker].[dbo].[per_Resume_BasicInfo].[Email]
     */
    public String getEmail() {
        return email;
    }
    /**
     * 电子邮箱
     * [jobcn90_Jobseeker].[dbo].[per_Resume_BasicInfo].[Email]
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * 手机号
     * [jobcn90_Jobseeker].[dbo].[per_Resume_BasicInfo].[Telephone]
     * MSISDN=CC+NDC+SN 例：86 + 139 + ********
     */
    public String getContactor_tel() {
        return contactor_tel;
    }
    /**
     * 手机号
     * [jobcn90_Jobseeker].[dbo].[per_Resume_BasicInfo].[Telephone]
     * MSISDN=CC+NDC+SN 例：86 + 139 + ********
     */
    public void setContactor_tel(String contactor_tel) {
        this.contactor_tel = contactor_tel;
    }
    /**
     * 注册IP  采用无符号整型主机序表示，如：288036141
     */
    public String getIp_address() {
        return ip_address;
    }
    /**
     * 注册IP  采用无符号整型主机序表示，如：288036141
     */
    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }
    /**
     * 注册端口号 (0~65535范围的整数)
     */
    public String getPort() {
        return port;
    }
    /**
     * 注册端口号 (0~65535范围的整数)
     */
    public void setPort(String port) {
        this.port = port;
    }
    /**
     * 注册MAC 类似00-E0-4C-3B-7D-2F
     */
    public String getMac_address() {
        return mac_address;
    }
    /**
     * 注册MAC 类似00-E0-4C-3B-7D-2F
     */
    public void setMac_address(String mac_address) {
        this.mac_address = mac_address;
    }
    /**
     * 注册IMEI 
     * 备注：国际移动设备身份码（IMEI：International Mobile Equipment Identify），由15位数字组成，主要用于标识GSM制式的手机
     */
    public String getImei() {
        return imei;
    }
    /**
     * 注册IMEI 
     * 备注：国际移动设备身份码（IMEI：International Mobile Equipment Identify），由15位数字组成，主要用于标识GSM制式的手机
     */
    public void setImei(String imei) {
        this.imei = imei;
    }
    /**
     * 注册硬件特征串 暂无对应字段
     */
    public String getHardwarestring() {
        return hardwarestring;
    }
    /**
     * 注册硬件特征串 暂无对应字段
     */
    public void setHardwarestring(String hardwarestring) {
        this.hardwarestring = hardwarestring;
    }
    /**
     * 注册经度 
     * 度数采用3位整数5位小数形式,小数位数不足补零，方位采用正负符号形式，使用正符号表示东经（正符号省略），负符号表示西经。
     * 例如123.23000 表示东经123.23000度;-133.00000表示西经133.00000度。
     */
    public String getLongitude() {
        return longitude;
    }
    /**
     * 注册经度 
     * 度数采用3位整数5位小数形式,小数位数不足补零，方位采用正负符号形式，使用正符号表示东经（正符号省略），负符号表示西经。
     * 例如123.23000 表示东经123.23000度;-133.00000表示西经133.00000度。
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    /**
     * 注册经度 
     * 度数采用3位整数5位小数形式,小数位数不足补零，方位采用正负符号形式，使用正符号表示东经（正符号省略），负符号表示西经。
     * 例如123.23000 表示东经123.23000度;-133.00000表示西经133.00000度。
     */
    public String getLatitude() {
        return latitude;
    }
    /**
     * 注册经度 
     * 度数采用3位整数5位小数形式,小数位数不足补零，方位采用正负符号形式，使用正符号表示东经（正符号省略），负符号表示西经。
     * 例如123.23000 表示东经123.23000度;-133.00000表示西经133.00000度。
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    /**
     * 注册时间 (绝对秒数)
     * [jobcn90].[dbo].[per_Account].[RegisterDate]
     */
    public String getRegtime() {
        return regtime;
    }
    /**
     * 注册时间 (绝对秒数)
     * [jobcn90].[dbo].[per_Account].[RegisterDate]
     */
    public void setRegtime(String regtime) {
        this.regtime = regtime;
    }
    /**
     * 最后更新时间 (当前时间的绝对秒数)
     */
    public String getLast_time() {
        return last_time;
    }
    /**
     * 最后更新时间 (当前时间的绝对秒数)
     */
    public void setLast_time(String last_time) {
        this.last_time = last_time;
    }
    /**
     * 动作类型 37注册, 93 新增，95 修改/更新
     */
    public String getAction_type() {
        return action_type;
    }
    /**
     * 动作类型 37注册, 93 新增，95 修改/更新
     */
    public void setAction_type(String action_type) {
        this.action_type = action_type;
    }
    /**
     * 动作时间 (绝对秒数)
     */
    public String getAction_time() {
        return action_time;
    }
    /**
     * 动作时间 (绝对秒数)
     */
    public void setAction_time(String action_time) {
        this.action_time = action_time;
    }

    @Override
    public String toString() {
        return "RegisterTraceBean{" +
                "reg_account='" + reg_account + '\'' +
                ", accounr_id='" + accounr_id + '\'' +
                ", auth_type='" + auth_type + '\'' +
                ", certificate_type='" + certificate_type + '\'' +
                ", certificate_code='" + certificate_code + '\'' +
                ", user_name='" + user_name + '\'' +
                ", reg_nickname='" + reg_nickname + '\'' +
                ", icon='" + icon + '\'' +
                ", email='" + email + '\'' +
                ", contactor_tel='" + contactor_tel + '\'' +
                ", ip_address='" + ip_address + '\'' +
                ", port='" + port + '\'' +
                ", mac_address='" + mac_address + '\'' +
                ", imei='" + imei + '\'' +
                ", hardwarestring='" + hardwarestring + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", regtime='" + regtime + '\'' +
                ", last_time='" + last_time + '\'' +
                ", action_type='" + action_type + '\'' +
                ", action_time='" + action_time + '\'' +
                '}';
    }
}
