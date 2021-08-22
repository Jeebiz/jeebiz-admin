package net.jeebiz.admin.extras.filestore.bo;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@ApiModel(value = "FilesUploadBO", description = "多文件上传BO")
@Data
@EqualsAndHashCode(callSuper=false)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FilesUploadBO extends FileStoreBO {
	
	/**
	 * 文件对象
	 */
	@ApiModelProperty(name = "file", required = true, value = "文件对象")
	private MultipartFile file;
	/**
	 * 缩放长度
	 */
	@ApiModelProperty(name = "width", value = "缩放长度")
	private Integer width;
	/**
	 * 缩放高度
	 */
	@ApiModelProperty(name = "height", value = "缩放高度")
	private Integer height;

}
