/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.filestore.setup.provider;

public class AliyunOssStorePath {

    private String bucket;

    private String path;

    private String thumb;

    public AliyunOssStorePath() {
        super();
    }

    public AliyunOssStorePath(String bucket, String path) {
        super();
        this.bucket = bucket;
        this.path = path;
    }

    public AliyunOssStorePath(String bucket, String path, String thumb) {
        super();
        this.bucket = bucket;
        this.path = path;
        this.thumb = thumb;
    }
    
    /**
     * @return the bucket
     */
    public String getBucket() {
        return bucket;
    }

    /**
     * @param bucket the bucket to set
     */
    public void setBucket(String bucket) {
        this.bucket = bucket;
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
