/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.filestore.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;
import net.jeebiz.boot.api.utils.ByteUnitFormat;

public class FilestoreUtils {

	private static String[] exts = new String[] {"JPG", "JPEG", "PNG", "GIF", "BMP", "WBMP"};
	
	public static boolean isImage(MultipartFile file) {
		Optional<MediaType> mOptional = MediaTypeFactory.getMediaType(file.getOriginalFilename());
		if(mOptional.isPresent() && StringUtils.equalsIgnoreCase(mOptional.get().getType(), "image")) {
			return true;
		}
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		return Stream.of(exts).anyMatch(item -> StringUtils.equalsIgnoreCase(item, extension));
	}
	
	public static boolean thumbable(MultipartFile file) {
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		return Stream.of(exts).anyMatch(item -> StringUtils.equalsIgnoreCase(item, extension));
	}
	
	public static double thumbScale(MultipartFile file) throws IOException{
		double imageSize = ByteUnitFormat.B.toDouble(ByteUnitFormat.M, file.getSize()); 
		// 图片大于2M,则进行压缩
		if(imageSize >= 2) {
			double scale = 1D / imageSize;
			if(imageSize >= 15) {
				scale = 5 / imageSize;
			} else if(imageSize >= 10) {
				scale = 4D / imageSize;
			} else if(imageSize >= 4) {
				scale = 2D / imageSize;
			} else {
				scale = 1D / imageSize;
			}
			return scale;
		}
		return 1D;
	}
	
	public static InputStream thumbStream(MultipartFile file) throws IOException{
		InputStream inputStream = null;
		if(isImage(file)) {
			double scale = thumbScale(file);
			if(scale < 1) {
				try(ByteArrayInputStream input = new ByteArrayInputStream(file.getBytes());
					ByteArrayOutputStream output = new ByteArrayOutputStream();){
					Thumbnails.of(input).scale(thumbScale(file)).toOutputStream(output);
					inputStream = new ByteArrayInputStream(output.toByteArray());
				}
			}
		} else {
			inputStream = file.getInputStream();
		}
		return inputStream;
	}
	
}
