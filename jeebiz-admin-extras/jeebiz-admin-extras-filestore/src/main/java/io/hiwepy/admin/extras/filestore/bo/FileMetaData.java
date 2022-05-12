package io.hiwepy.admin.extras.filestore.bo;

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
public class FileMetaData {

	private String name;

	private String value;

}
