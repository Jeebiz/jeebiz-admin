package io.hiwepy.admin.extras.filestore.bo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@ApiModel(value = "FileDeleteBO", description = "文件删除BO")
@Data
@EqualsAndHashCode(callSuper=false)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FileDeleteBO extends FileStoreBO {
	
	/**
	 * 要删除文件的路径
	 */
	@ApiModelProperty(name = "paths", value = "要删除文件的路径")
	private List<String> paths;
	/**
	 * 要删除文件的uuid
	 */
	@ApiModelProperty(name = "uuids", value = "要删除文件的uuid")
	private List<String> uuids;

}
