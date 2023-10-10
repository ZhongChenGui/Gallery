package com.learned.gallery;

/**
 * 网络图片数据实体
 */
public class Picture {
    public int id;

    public String name;//图片名称

    public String url;//图片url链接

    public Picture(int id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
