package cn.xzxy.lewy.pojo;

import java.util.Date;

public class VideoComment {

    private int id;
    private String content;
    private int uid;
    private int vid;
    private Date createDate;

    /* 非数据库中数据 */
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getVid() {
        return vid;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "VideoComment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", uid=" + uid +
                ", vid=" + vid +
                ", createDate=" + createDate +
                '}';
    }
}
