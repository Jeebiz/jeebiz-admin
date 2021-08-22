/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.filestore.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.web.multipart.MultipartFile;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.google.common.collect.Sets;

import hitool.core.format.ByteUnitFormat;
import net.coobird.thumbnailator.Thumbnails;
import net.jeebiz.admin.extras.filestore.bo.FileMetaData;

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
	
	public static Set<FileMetaData> metaDataSet(File file) {
		Set<FileMetaData> metaDataSet = Sets.newHashSet();
		try (InputStream inputStream = FileUtils.openInputStream(file);) {
			Metadata metadata = ImageMetadataReader.readMetadata(inputStream);
			for (Directory directory : metadata.getDirectories()) {
			    for (Tag tag : directory.getTags()) {
			        //格式化输出[directory.getName()] - tag.getTagName() = tag.getDescription()
			        System.out.format("[%s] - %s = %s%n",  directory.getName(), tag.getTagName(), tag.getDescription());
			        metaDataSet.add(new FileMetaData(directory.getName(), tag.getTagName()));
			    }
			    if (directory.hasErrors()) {
			        for (String error : directory.getErrors()) {
			            System.err.format("ERROR: %s", error);
			        }
			    }
			}
		} catch (Exception e) {
		}
		return metaDataSet;
	}
}
