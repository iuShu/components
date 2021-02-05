package com.jobcn.actionmqpack.bean;

public class FilesTraceBean extends BaseJmsObject {
    private static final long serialVersionUID = 1L;
    
    private String accounr_id = "";    //用户id
    private String username = "";      //用户名
    private String mobile = "";        //手机号
    private String file_id = "";       //文件id
    private String cookies_mainfile = "";//实体文件
    private String file_createtime = "";//文件（照片）生成时间
    private String download_url = "";  //文件url
    private String file_name = "";     //文件名称
    private String file_type = "";     //文件类型
    private String file_size = "";     //文件大小
    private String file_md5 = "";      //文件特征值
    private String delete_status = ""; //删除状态
    private String delete_time = "";   //删除时间
    private String ip_address = "";    //ip
    private String port = "";          //端口号
    private String mac_address = "";   //mac
    private String imei = "";          //imei
    private String hardwarestring = "";//硬件特征串
    private String longitude = "";     //经度
    private String latitude = "";      //纬度
    private String action = "";        //动作类型 28:上传  29:下载
    private String regtime = "";       //动作时间

    /**
     * 用户id
     * @return
     */
    public String getAccounr_id() {
        return accounr_id;
    }
    /**
     * 用户id
     * @param accounr_id
     */
    public void setAccounr_id(String accounr_id) {
        this.accounr_id = accounr_id;
    }
    
    /**
     * 用户名
     * @return
     */
    public String getUsername() {
        return username;
    }
    /**用户名
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    /**手机号
     * @return
     */
    public String getMobile() {
        return mobile;
    }
    /**手机号
     * @param mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    
    /**文件id
     * @return
     */
    public String getFile_id() {
        return file_id;
    }
    /**文件id
     * @param file_id
     */
    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }
    
    /**实体文件
     * @return
     */
    public String getCookies_mainfile() {
        return cookies_mainfile;
    }
    /**实体文件
     * @param cookies_mainfile
     */
    public void setCookies_mainfile(String cookies_mainfile) {
        this.cookies_mainfile = cookies_mainfile;
    }
    
    /**文件（照片）生成时间 (绝对秒数)
     * @return
     */
    public String getFile_createtime() {
        return file_createtime;
    }
    /**文件（照片）生成时间 (绝对秒数)
     * @param file_createtime
     */
    public void setFile_createtime(String file_createtime) {
        this.file_createtime = file_createtime;
    }
    
    /**文件url
     * @return
     */
    public String getDownload_url() {
        return download_url;
    }
    /**文件url
     * @param download_url
     */
    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }
    
    /**文件名称
     * @return
     */
    public String getFile_name() {
        return file_name;
    }
    /**文件名称
     * @param file_name
     */
    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }
    
    /**文件类型
     * @return
     */
    public String getFile_type() {
        return file_type;
    }
    /**文件类型
     * @param file_type
     */
    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }
    
    /**文件大小
     * @return
     */
    public String getFile_size() {
        return file_size;
    }
    /**文件大小
     * @param file_size
     */
    public void setFile_size(String file_size) {
        this.file_size = file_size;
    }
    
    /**文件特征值
     * @return
     */
    public String getFile_md5() {
        return file_md5;
    }
    /**文件特征值
     * @param file_md5
     */
    public void setFile_md5(String file_md5) {
        this.file_md5 = file_md5;
    }
    
    /**删除状态
     * @return
     */
    public String getDelete_status() {
        return delete_status;
    }
    /**删除状态
     * @param delete_status
     */
    public void setDelete_status(String delete_status) {
        this.delete_status = delete_status;
    }
    
    /**删除时间 (绝对秒数)
     * @return
     */
    public String getDelete_time() {
        return delete_time;
    }
    /**删除时间 (绝对秒数)
     * @param delete_time
     */
    public void setDelete_time(String delete_time) {
        this.delete_time = delete_time;
    }
    
    /**ip 无符号整型主机序表示，如：288036141
     * @return
     */
    public String getIp_address() {
        return ip_address;
    }
    /**ip 无符号整型主机序表示，如：288036141
     * @param ip_address
     */
    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }
    
    /**端口号
     * @return
     */
    public String getPort() {
        return port;
    }
    /**端口号
     * @param port
     */
    public void setPort(String port) {
        this.port = port;
    }
    
    public String getMac_address() {
        return mac_address;
    }
    public void setMac_address(String mac_address) {
        this.mac_address = mac_address;
    }
    
    /**
     * 国际移动设备身份码（IMEI），由15位数字组成，主要用于标识GSM制式的手机。
     * 电子序列号（ESN），长度为32bits，
     * 移动终端标识号（MEID）,长度为56bits，常主要分配给CDMA制式的手机
     * @return
     */
    public String getImei() {
        return imei;
    }
    public void setImei(String imei) {
        this.imei = imei;
    }
    
    /**硬件特征串
     * @return
     */
    public String getHardwarestring() {
        return hardwarestring;
    }
    /**硬件特征串
     * @param hardwarestring
     */
    public void setHardwarestring(String hardwarestring) {
        this.hardwarestring = hardwarestring;
    }
    
    /**经度
     * @return
     */
    public String getLongitude() {
        return longitude;
    }
    /**经度
     * @param longitude
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    
    /**纬度
     * @return
     */
    public String getLatitude() {
        return latitude;
    }
    /**纬度
     * @param latitude
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    
    /**动作类型
     * 28 上传29 下载
     * @return
     */
    public String getAction() {
        return action;
    }
    /**动作类型
     * 28:上传  29:下载
     * @param action
     */
    public void setAction(String action) {
        this.action = action;
    }
    
    /**动作时间(绝对秒数)
     * @return
     */
    public String getRegtime() {
        return regtime;
    }
    /**动作时间(绝对秒数)
     * @param regtime
     */
    public void setRegtime(String regtime) {
        this.regtime = regtime;
    }

    @Override
    public String toString() {
        return "FilesTraceBean{" +
                "accounr_id='" + accounr_id + '\'' +
                ", username='" + username + '\'' +
                ", mobile='" + mobile + '\'' +
                ", file_id='" + file_id + '\'' +
                ", cookies_mainfile='" + cookies_mainfile + '\'' +
                ", file_createtime='" + file_createtime + '\'' +
                ", download_url='" + download_url + '\'' +
                ", file_name='" + file_name + '\'' +
                ", file_type='" + file_type + '\'' +
                ", file_size='" + file_size + '\'' +
                ", file_md5='" + file_md5 + '\'' +
                ", delete_status='" + delete_status + '\'' +
                ", delete_time='" + delete_time + '\'' +
                ", ip_address='" + ip_address + '\'' +
                ", port='" + port + '\'' +
                ", mac_address='" + mac_address + '\'' +
                ", imei='" + imei + '\'' +
                ", hardwarestring='" + hardwarestring + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", action='" + action + '\'' +
                ", regtime='" + regtime + '\'' +
                '}';
    }
}
