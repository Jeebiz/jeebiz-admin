package net.jeebiz.admin.extras.filestore.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件元数据(MetaData)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileMetaDataDTO {

	private String name;

	private String value;

}
