package com.jobcn.actionmqpack.bean;

public class ResumeTraceBean extends BaseJmsObject {
    private static final long serialVersionUID = 1L;
    
    private String user_id = "";    //用户id
    private String user_name = "";  //用户账号
    private String user_passord = ""; //密码
    private String status = "";     //状态
    private String create_time = "";//登记/修改时间
    private String ip_addr = "";    //登记/修改ip
    private String mac_addr = "";   //登记/修改mac
    private String mobile_imei = "";//登记/修改imei
    private String bind_email = ""; //电子邮件地址
    private String bind_tel = "";   //绑定手机号码
    private String contact_tel = "";//其他联系号码
    private String real_name = "";  //姓名
    private String sex = "";        //性别
    private String birthday = "";   //出生日期
    private String certificate_code = ""; //证件号码
    private String photo_file = ""; //照片附件名
    private String register_city = ""; //职位申请地区
    private String dwell_city = ""; //居住地（区县）
    private String residence = "";  //户口所在地
    private String live_city = "";  //成长地区
    private String job_post = "";   //期望职位
    private String graduate_school = ""; //毕业学校
    private String graduate_time = ""; //毕业时间
    private String qualification = ""; //学历
    private String major = "";      //专业名称
    private String last_company = ""; //最近就职企业名称
    
    /**
     * 简历编号
     * 该字段不会存到信息文件中，只在“去重”操作时用
     */
    private String resumeId = "";
    
    /**用户id
     * @return
     */
    public String getUser_id() {
        return user_id;
    }
    /**用户id
     * @param user_id
     */
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    
    /**用户账号
     * @return
     */
    public String getUser_name() {
        return user_name;
    }
    /**用户账号
     * @param user_name
     */
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
    
    /**用户密码
     * @return
     */
    public String getUser_passord() {
        return user_passord;
    }
    /**用户密码
     * @param user_passord
     */
    public void setUser_passord(String user_passord) {
        this.user_passord = user_passord;
    }
    
    /**状态
     * 93 新增（泛指所有新增动作）94 删除（泛指所有删除动作）95 修改/更新（泛指所有修改、更新动作）
     * @return
     */
    public String getStatus() {
        return status;
    }
    /**状态
     * 93 新增（泛指所有新增动作）94 删除（泛指所有删除动作）95 修改/更新（泛指所有修改、更新动作）
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
    /**登记/修改时间 绝对秒数
     * @return
     */
    public String getCreate_time() {
        return create_time;
    }
    /**登记/修改时间 绝对秒数
     * @param create_time
     */
    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
    
    /**IP
     * 采用无符号整型主机序表示，如：288036141
     * @return
     */
    public String getIp_addr() {
        return ip_addr;
    }
    /**IP
     * 采用无符号整型主机序表示，如：288036141
     * @param ip_addr
     */
    public void setIp_addr(String ip_addr) {
        this.ip_addr = ip_addr;
    }
    
    /**登记/修改mac地址
     * @return
     */
    public String getMac_addr() {
        return mac_addr;
    }
    /**登记/修改mac地址
     * @param mac_addr
     */
    public void setMac_addr(String mac_addr) {
        this.mac_addr = mac_addr;
    }
    
    /**
     * 登记/修改imei
     * @return
     */
    public String getMobile_imei() {
        return mobile_imei;
    }
    /**登记/修改imei
     * @param mobile_imei
     */
    public void setMobile_imei(String mobile_imei) {
        this.mobile_imei = mobile_imei;
    }
    
    /**电子邮件地址
     * @return
     */
    public String getBind_email() {
        return bind_email;
    }
    /**电子邮件地址
     * @param bind_email
     */
    public void setBind_email(String bind_email) {
        this.bind_email = bind_email;
    }
    
    /**绑定手机号码
     * 分类与编码方法：MSISDN=CC+NDC+SN 例：86 + 139 + ********
     * @return
     */
    public String getBind_tel() {
        return bind_tel;
    }
    /**绑定手机号码
     * 分类与编码方法：MSISDN=CC+NDC+SN 例：86 + 139 + ********
     * @param bind_tel
     */
    public void setBind_tel(String bind_tel) {
        this.bind_tel = bind_tel;
    }
    
    /**其他联系号码
     * 备注：多个号码之间用符号“|”分割。
     * @return
     */
    public String getContact_tel() {
        return contact_tel;
    }
    /**其他联系号码
     * 备注：多个号码之间用符号“|”分割。
     * @param contact_tel
     */
    public void setContact_tel(String contact_tel) {
        this.contact_tel = contact_tel;
    }
    
    /**姓名
     * @return
     */
    public String getReal_name() {
        return real_name;
    }
    /**姓名
     * @param real_name
     */
    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }
    
    /**性别 1:男 2:女
     * @return
     */
    public String getSex() {
        return sex;
    }
    /**性别 1:男 2:女
     * @param sex
     */
    public void setSex(String sex) {
        this.sex = sex;
    }
    
    /**出生日期
     * @return
     */
    public String getBirthday() {
        return birthday;
    }
    /**出生日期
     * @param birthday
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    
    /**证件号码
     * @return
     */
    public String getCertificate_code() {
        return certificate_code;
    }
    /**证件号码
     * @param certificate_code
     */
    public void setCertificate_code(String certificate_code) {
        this.certificate_code = certificate_code;
    }
    
    /**照片附件名
     * @return
     */
    public String getPhoto_file() {
        return photo_file;
    }
    /**照片附件名
     * @param photo_file
     */
    public void setPhoto_file(String photo_file) {
        this.photo_file = photo_file;
    }
    
    /**职位申请地区
     * @return
     */
    public String getRegister_city() {
        return register_city;
    }
    /**职位申请地区
     * @param register_city
     */
    public void setRegister_city(String register_city) {
        this.register_city = register_city;
    }
    
    /**居住地（区县名）
     * @return
     */
    public String getDwell_city() {
        return dwell_city;
    }
    /**居住地（区县名）
     * @param dwell_city
     */
    public void setDwell_city(String dwell_city) {
        this.dwell_city = dwell_city;
    }
    
    /**户口所在地
     * @return
     */
    public String getResidence() {
        return residence;
    }
    /**户口所在地
     * @param residence
     */
    public void setResidence(String residence) {
        this.residence = residence;
    }
    
    /**成长地区（区县名）
     * @return
     */
    public String getLive_city() {
        return live_city;
    }
    /**成长地区（区县名）
     * @param live_city
     */
    public void setLive_city(String live_city) {
        this.live_city = live_city;
    }
    
    /**期望职位
     * @return
     */
    public String getJob_post() {
        return job_post;
    }
    /**期望职位
     * @param job_post
     */
    public void setJob_post(String job_post) {
        this.job_post = job_post;
    }
    
    /**毕业学校
     * @return
     */
    public String getGraduate_school() {
        return graduate_school;
    }
    /**毕业学校
     * @param graduate_school
     */
    public void setGraduate_school(String graduate_school) {
        this.graduate_school = graduate_school;
    }
    
    /**毕业时间 (20170701)
     * @return
     */
    public String getGraduate_time() {
        return graduate_time;
    }
    /**毕业时间 (20170701)
     * @param graduate_time
     */
    public void setGraduate_time(String graduate_time) {
        this.graduate_time = graduate_time;
    }
    
    /**学历
     * "10":"初中及以下", 
     * "20":"高中", 
     * "30":"中专", 
     * "40":"大专", 
     * "50":"本科", 
     * "60":"硕士", 
     * "70":"博士"
     * @return
     */
    public String getQualification() {
        return qualification;
    }
    /**学历
     * "10":"初中及以下", 
     * "20":"高中", 
     * "30":"中专", 
     * "40":"大专", 
     * "50":"本科", 
     * "60":"硕士", 
     * "70":"博士"
     * @param qualification
     */
    public void setQualification(String qualification) {
        this.qualification = qualification;
    }
    
    /**专业名称
     * @return
     */
    public String getMajor() {
        return major;
    }
    /**
     * 专业名称
     * @param major
     */
    public void setMajor(String major) {
        this.major = major;
    }
    
    /**最近就职企业名称
     * @return
     */
    public String getLast_company() {
        return last_company;
    }
    /**最近就职企业名称
     * @param last_company
     */
    public void setLast_company(String last_company) {
        this.last_company = last_company;
    }
    
    /**
     * 简历编号
     * 该字段不会存到信息文件中，只在“去重”操作
     * @return
     */
    public String getResumeId() {
        return resumeId;
    }
    /**
     * 简历编号
     * 该字段不会存到信息文件中，只在“去重”操作
     * @param resumeId
     */
    public void setResumeId(String resumeId) {
        this.resumeId = resumeId;
    }

    @Override
    public String toString() {
        return "ResumeTraceBean{" +
                "user_id='" + user_id + '\'' +
                ", user_name='" + user_name + '\'' +
                ", user_passord='" + user_passord + '\'' +
                ", status='" + status + '\'' +
                ", create_time='" + create_time + '\'' +
                ", ip_addr='" + ip_addr + '\'' +
                ", mac_addr='" + mac_addr + '\'' +
                ", mobile_imei='" + mobile_imei + '\'' +
                ", bind_email='" + bind_email + '\'' +
                ", bind_tel='" + bind_tel + '\'' +
                ", contact_tel='" + contact_tel + '\'' +
                ", real_name='" + real_name + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday='" + birthday + '\'' +
                ", certificate_code='" + certificate_code + '\'' +
                ", photo_file='" + photo_file + '\'' +
                ", register_city='" + register_city + '\'' +
                ", dwell_city='" + dwell_city + '\'' +
                ", residence='" + residence + '\'' +
                ", live_city='" + live_city + '\'' +
                ", job_post='" + job_post + '\'' +
                ", graduate_school='" + graduate_school + '\'' +
                ", graduate_time='" + graduate_time + '\'' +
                ", qualification='" + qualification + '\'' +
                ", major='" + major + '\'' +
                ", last_company='" + last_company + '\'' +
                ", resumeId='" + resumeId + '\'' +
                '}';
    }
}
