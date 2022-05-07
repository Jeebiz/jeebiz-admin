/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.filestore.strategy;

public class FileStorePath {

    private String group;

    private String path;

	/**
	 * 缩略图访问地址（图片类型文件）
	 */
	private String thumb;

    /**
     * 存储文件路径
     */
    public FileStorePath() {
        super();
    }

    /**
     * 存储文件路径
     *
     * @param group
     * @param path
     */
    public FileStorePath(String group, String path) {
        super();
        this.group = group;
        this.path = path;
    }

    /**
     * 存储文件路径
     *
     * @param group
     * @param path
     * @param thum
     */
    public FileStorePath(String group, String path, String thumb) {
        super();
        this.group = group;
        this.path = path;
        this.thumb = thumb;
    }
    
    /**
     * @return the group
     */
    public String getGroup() {
        return group;
    }

    /**
     * @param group the group to set
     */
    public void setGroup(String group) {
        this.group = group;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}
	
}
