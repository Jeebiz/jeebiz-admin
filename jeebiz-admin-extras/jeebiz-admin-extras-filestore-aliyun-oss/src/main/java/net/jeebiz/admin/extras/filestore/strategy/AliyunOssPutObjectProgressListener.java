/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package net.jeebiz.admin.extras.filestore.strategy;

import com.aliyun.oss.event.ProgressEvent;
import com.aliyun.oss.event.ProgressEventType;
import com.aliyun.oss.event.ProgressListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AliyunOssPutObjectProgressListener implements ProgressListener {

    private long bytesRead = 0;
    private long totalBytes = -1;
    private boolean succeed = false;

	@Override
    public void progressChanged(ProgressEvent progressEvent) {
        long bytes = progressEvent.getBytes();
        ProgressEventType eventType = progressEvent.getEventType();

        switch (eventType) {
            case TRANSFER_STARTED_EVENT:
            	log.info("Start to upload......");
                break;
            case REQUEST_CONTENT_LENGTH_EVENT:
                this.totalBytes = bytes;
                log.info(this.totalBytes + " bytes in total will be upload to a oss");
                break;
            case REQUEST_BYTE_TRANSFER_EVENT:
                this.bytesRead += bytes;
                if (this.totalBytes != -1) {
                    int percent = (int)(this.bytesRead * 100.0 / this.totalBytes);
                    log.info(bytes + " bytes have been write at this time, upload progress: " +
                            percent + "%(" + this.bytesRead + "/" + this.totalBytes + ")");
                } else {
                    log.info(bytes + " bytes have been write at this time, upload ratio: unknown" +
                            "(" + this.bytesRead + "/...)");
                }
                break;
            case TRANSFER_COMPLETED_EVENT:
                this.succeed = true;
                log.info("Succeed to upload, " + this.bytesRead + " bytes have been transferred in total");
                break;
            case TRANSFER_FAILED_EVENT:
                log.info("Failed to upload, " + this.bytesRead + " bytes have been transferred");
                break;
            default:
            	break;
        }
    }

    public boolean isSucceed() {
        return succeed;
    }

}
