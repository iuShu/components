package com.jobcn.actionmqpack.bean;

public class SearchInfoBean  extends BaseJmsObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * 搜索ID
	 */
	private String search_id;
	
	/**
	 * 搜索关键字
	 * 传输时是否采用BASE64编码：是
	 */
	private String search_key;
	
	/**
	 * 例：http://www.jobcn.com/search/result.xhtml?p.keyword=%CF%FA%CA%DB
	 */
	private String url;	
	
	/**
	 * 代码表：101 网页102 新闻103 图片104 音乐105 
	 */
	private String board = "101";
	
	/**
	 * 代码表：01 聊天/私聊/私信（发送信息） 02 上线/登录03 下线/离线/退出04 在线05 隐身上线06 隐身07 发贴/发微博/发微信08 回帖09 修改帖子/微博/微信10 删除帖子/微博/微信11 转载/分享/转发12 更新好友列表13 群聊/群操作/发微群消息14 更新群成员列表15 查看资料16 更新帐号信息17 新增好友18 删除好友19 接受好友邀请20 语音视频通信21 开始语音视频22 停止语音视频23 文件传输24 播放25 评论26 关注/跟随27 撤销关注/跟随28 上传29 下载30 更新/切换（认证数据）31 收邮件/收彩信32 发邮件/发彩信33 投注34 发信息35 收文件36 发文件37 注册38 注销39 定位40 签到41 备份42 恢复43 增加联系人44 删除联系人45 提及相关人（@）46 加微群47 投票48 关键词搜索49 WebMail存草稿箱50 失败登录51 网上值机（航班）52 下订单（如：订票、购物、充值等）53 订单查询（如：快递单号查询、订票和购物订单查询等）54 访问操作（如：QQ空间访问、突防工具访问、第三方支付访问等）55 备注操作（如：好友备注、地图标注等）56 接单（如：打车软件，司机端的接单动作）
		57 命令操作（如：FTP、TELNET的命令操作）58 报名59 支付60 改签61 退款91 发送（泛指所有发送动作）92 接收（泛指所有接收动作）93 新增（泛指所有新增动作）94 删除（泛指所有删除动作）95 修改/更新（泛指所有修改、更新动作）96 取消（（泛指所有取消动作）99 其他

		48 关键词搜索
	 */
	private String action = "48";
	
	/**
	 * 代码表：01 文本02 图片03 音频04 视频99 其他
	 */
	private String media_type = "01";
	
	/**
	 * 用户ID
	 */
	private String account_id = "";
	
	/**
	 * 用户账户
	 */
	private String username = "";
	
	
	/**
	 * 搜索时间
	 */
	private String search_time = "";
	
	/**
	 * 搜索IP
	 */
	private String ip_address = "";
	
	/**
	 * 搜索端口号
	 */
	private String port = "";
	
	/**
	 * 搜索经度
	 */
	private String longitude = "";

    /**
     * 搜索ID
     * @return
     */
    public String getSearch_id() {
        return search_id;
    }

    /**
     * 搜索ID
     * @param search_id
     */
    public void setSearch_id(String search_id) {
        this.search_id = search_id;
    }

    /**
     * 搜索关键字
     * @return
     */
    public String getSearch_key() {
        return search_key;
    }

    /**
     * 搜索关键字
     * @param search_key
     */
    public void setSearch_key(String search_key) {
        this.search_key = search_key;
    }

    /**
     * 搜索结果URL，例：http://www.jobcn.com/search/result.xhtml?p.keyword=%CF%FA%CA%DB
     * @return
     */
    public String getUrl() {
        return url;
    }

    /**
     * 搜索结果URL，例：http://www.jobcn.com/search/result.xhtml?p.keyword=%CF%FA%CA%DB
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 搜索版块(默认101)
     * @return
     */
    public String getBoard() {
        return board;
    }

    /**
     * 搜索版块(默认101)
     * @param board
     */
    public void setBoard(String board) {
        this.board = board;
    }

    /**
     * 动作类型(默认48:关键词搜索)
     * @return
     */
    public String getAction() {
        return action;
    }

    /**
     * 动作类型(默认48:关键词搜索)
     * @param action
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * 媒体类型（默认01）
     * 代码表：01 文本02 图片03 音频04 视频99 其他
     * @return
     */
    public String getMedia_type() {
        return media_type;
    }

    /**
     * 媒体类型（默认01）
     * 代码表：01 文本02 图片03 音频04 视频99 其他
     * @param media_type
     */
    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    /**
     * 用户ID
     * @return
     */
    public String getAccount_id() {
        return account_id;
    }

    /**
     * 用户ID
     * @param account_id
     */
    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    /**
     * 用户账户
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * 用户账户
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 搜索时间(绝对秒数)
     * @return
     */
    public String getSearch_time() {
        return search_time;
    }

    /**
     * 搜索时间(绝对秒数)
     * @param search_time
     */
    public void setSearch_time(String search_time) {
        this.search_time = search_time;
    }

    /**
     * 搜索IP 长整型,如：288036141
     * @return
     */
    public String getIp_address() {
        return ip_address;
    }

    /**
     * 搜索IP 长整型,如：288036141
     * @param ip_address
     */
    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    /**
     * 搜索端口号
     * @return
     */
    public String getPort() {
        return port;
    }

    /**
     * 搜索端口号
     * @param port
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     * 搜索经度 例如123.23000 表示东经123.23000度;-133.00000表示西经133.00000度。
     * @return
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * 搜索经度 例如123.23000 表示东经123.23000度;-133.00000表示西经133.00000度。
     * @param longitude
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "SearchInfoBean{" +
                "search_id='" + search_id + '\'' +
                ", search_key='" + search_key + '\'' +
                ", url='" + url + '\'' +
                ", board='" + board + '\'' +
                ", action='" + action + '\'' +
                ", media_type='" + media_type + '\'' +
                ", account_id='" + account_id + '\'' +
                ", username='" + username + '\'' +
                ", search_time='" + search_time + '\'' +
                ", ip_address='" + ip_address + '\'' +
                ", port='" + port + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}