package net.jeebiz.admin.authz.rbac0.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import net.jeebiz.admin.authz.rbac0.setup.strategy.AuthChannel;

@ApiModel(value = "FilesUploadResult", description = "多文件上传结果")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResult {

	/**
	 * 发起文件存储的用户uid
	 */
	@ApiModelProperty(name = "userId", required = true, value = "发起文件存储的用户uid")
	private String userId;
	/**
	 * 文件存储渠道
	 */
	@ApiModelProperty(name = "channel", required = true, value = "文件存储渠道")
	private AuthChannel channel;
    /**
	 * 文件上传状态（ 0：上传失败、1：上传成功）
	 */
	@ApiModelProperty(name = "status", value = "文件上传送状态（ 0：上传失败、1：上传成功）")
	private Integer status;

}

