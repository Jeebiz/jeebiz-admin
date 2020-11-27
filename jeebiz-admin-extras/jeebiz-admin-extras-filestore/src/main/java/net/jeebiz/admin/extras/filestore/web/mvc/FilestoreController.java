/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.filestore.web.mvc;

import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.jeebiz.admin.extras.filestore.service.IFilestoreService;
import net.jeebiz.admin.extras.filestore.web.dto.FilestoreConfig;
import net.jeebiz.admin.extras.filestore.web.dto.FilestoreDTO;
import net.jeebiz.admin.extras.filestore.web.dto.FilestoreDownloadDTO;
import net.jeebiz.boot.api.ApiCode;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.utils.CollectionUtils;
import net.jeebiz.boot.api.web.BaseApiController;

@Api(tags = "文件服务：文件上传、下载（Ok）")
@RestController
@RequestMapping("/file/store/")
@Validated
public class FilestoreController extends BaseApiController {
	
	@Autowired
	private IFilestoreService filestoreService;
	
	@ApiOperation(value = "文件服务配置信息", notes = "文件服务配置信息")
    @GetMapping("config")
	public ApiRestResponse<FilestoreConfig> endpoint(){
		return ApiRestResponse.success(getFilestoreService().getConfig());
	}
	
	@ApiOperation(value = "文件服务：单上传文件", notes = "将单个文件上传到指定的存储对象")
    @PostMapping(value = "upload", headers = "content-type=multipart/form-data")
	@RequiresAuthentication
    public ApiRestResponse<FilestoreDTO> upload(@ApiParam(value = "附件文件", required = true) @RequestParam(value = "file") @NotNull(message = "文件不能为空") MultipartFile file,
                                              @ApiParam(value = "缩放长度", required = false, defaultValue = "0") @RequestParam(value = "width", required = false, defaultValue = "0") int width,
                                              @ApiParam(value = "缩放高度", required = false, defaultValue = "0") @RequestParam(value = "height", required = false, defaultValue = "0") int height) throws Exception {
		if (null == file){
			return ApiRestResponse.of(ApiCode.SC_UNSATISFIED_PARAM);
		}
		FilestoreDTO result = getFilestoreService().upload(file, width, height);
		if (result == null) {
			return fail("file.upload.fail");
		}
		return ApiRestResponse.of(ApiCode.SC_SUCCESS, getMessage("file.upload.success"), result);
	}
	
	@ApiOperation(value = "文件服务：多上传文件", notes = "将多个文件上传到指定的存储对象")
    @PostMapping(value = "uploads", headers = "content-type=multipart/form-data")
	@RequiresAuthentication
    public ApiRestResponse<List<FilestoreDTO>> upload(@ApiParam(value = "附件文件", required = true) @RequestParam(value = "files")
                                                     @NotNull(message = "文件不能为空") MultipartFile[] files,
                                                     @ApiParam(value = "缩放长度", required = false, defaultValue = "0") @RequestParam(value = "width", required = false, defaultValue = "0") int width,
                                                     @ApiParam(value = "缩放高度", required = false, defaultValue = "0") @RequestParam(value = "height", required = false, defaultValue = "0") int height) throws Exception {
		if (null == files || files.length == 0){
			return ApiRestResponse.of(ApiCode.SC_UNSATISFIED_PARAM);
		}
		List<FilestoreDTO> result = getFilestoreService().upload(files, width, height);
		if (CollectionUtils.isEmpty(result)) {
			return fail("file.upload.fail");
		}
		return ApiRestResponse.of(ApiCode.SC_SUCCESS, getMessage("file.upload.success"), result);
	}
	
	@ApiOperation(value = "文件服务：根据uuid删除文件", notes = "删除指定存储对象下的指定文件")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "uuid", value = "文件UID", required = true, dataType = "String"),
	})
	@GetMapping("delete")
	@RequiresAuthentication
	public ApiRestResponse<String> delete(@RequestParam("uuid") @NotEmpty(message = "文件UID不能为空") String uuid){
		try {
			getFilestoreService().deleteByUuid(Arrays.asList(uuid));
			return success("file.delete.success");
		} catch (Exception e) {
			return ApiRestResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation(value = "文件服务：根据path批量删除文件", notes = "批量删除指定存储对象下的指定文件")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "paths", value = "要删除的文件path集合", required = true, dataType = "java.util.List<String>")
	})
	@GetMapping("deleteByPath")
	@RequiresAuthentication
	public ApiRestResponse<String> deleteByPath( @NotNull(message = "文件路径不能为空") @RequestParam("paths") List<String> paths){
		try {
			getFilestoreService().deleteByPath(paths);
			return success("file.delete.success");
		} catch (Exception e) {
			logException(this, e);
			return ApiRestResponse.error(e.getMessage());
		}
	}

	@ApiOperation(value = "文件服务：根据uuid批量删除文件", notes = "批量删除指定存储对象下的指定文件")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "uuids", value = "要删除的文件Uuid集合", required = true, dataType = "java.util.List<String>")
	})
	@GetMapping("deleteByUuid")
	@RequiresAuthentication
	public ApiRestResponse<String> deleteByUuid(@NotNull(message = "文件UUID不能为空") @RequestParam("uuids") List<String> uuids){
		try {
			getFilestoreService().deleteByUuid(uuids);
			return success("file.delete.success");
		} catch (Exception e) {
			return ApiRestResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation(value = "文件服务：重新上传文件", notes = "重新上传指定的文件")
	@ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", value = "原文件UID", required = true, dataType = "String")
    })
    @PostMapping(value = "reupload", headers = "content-type=multipart/form-data")
	@RequiresAuthentication
    public ApiRestResponse<FilestoreDTO> reupload(@RequestParam("uuid")
			@NotEmpty(message = "原文件UUID不能为空") String uuid,
                                                 @ApiParam(value = "文件", required = true)
                                                 @RequestParam("file") @NotNull(message = "文件不能为空") MultipartFile file,
                                                 @ApiParam(value = "缩放长度", required = false, defaultValue = "0") @RequestParam(value = "width", required = false, defaultValue = "0") int width,
                                                 @ApiParam(value = "缩放高度", required = false, defaultValue = "0") @RequestParam(value = "height", required = false, defaultValue = "0") int height) throws Exception {
		if (null == file){
			return ApiRestResponse.of(ApiCode.SC_UNSATISFIED_PARAM);
		}
		FilestoreDTO result = getFilestoreService().reupload(uuid,file, width, height);
		if (result == null) {
			return fail("file.reupload.fail");
		}
		return success("file.reupload.success");
	}
	
	@ApiOperation(value = "文件服务：根据path查询文件信息", notes = "根据给出的文件相对路径获取文件信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "paths", value = "文件Path集合", required = true, dataType = "java.util.List<String>")
	})
	@GetMapping("listByPath")
	@RequiresAuthentication
    public ApiRestResponse<List<FilestoreDTO>> listByPath(@NotNull(message = "文件路径不能为空") @RequestParam("paths") List<String> paths){
		try {
			return ApiRestResponse.success(getFilestoreService().listByPath(paths));
		} catch (Exception e) {
			return ApiRestResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation(value = "文件服务：根据uuid查询文件信息", notes = "根据给出的文件Uuid获取文件信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "uuids", value = "文件Uuid集合", required = true, dataType = "java.util.List<String>")
	})
	@GetMapping("listByUuid")
	@RequiresAuthentication
    public ApiRestResponse<List<FilestoreDTO>> listByUuid(@NotNull(message = "文件UUID不能为空") @RequestParam("uuids") List<String> uuids){
		try {
			return ApiRestResponse.success(getFilestoreService().listByUuid(uuids));
		} catch (Exception e) {
			return ApiRestResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation(value = "文件服务：根据path下载文件", notes = "根据给出的文件相对路径下载文件")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "path", value = "要下载的文件path", required = true, dataType = "String")
	})
    @GetMapping("downloadByPath")
	@RequiresAuthentication
	public ResponseEntity<byte[]> downloadByPath(@NotNull(message = "文件路径不能为空") @RequestParam("path") String path){

		ResponseEntity<byte[]> entity = null;
		try {

			FilestoreDownloadDTO downloadDTO = getFilestoreService().downloadByPath(path);
			if (downloadDTO != null) {

				// 定义http头 ，状态
				HttpHeaders header = new HttpHeaders();
				header.add("Content-Disposition", "attchement;filename=" + downloadDTO.getName());
				header.setContentType(MediaTypeFactory.getMediaType(downloadDTO.getName()).get());

				// 定义ResponseEntity封装返回信息
				return new ResponseEntity<byte[]>(downloadDTO.getBytes(), header, HttpStatus.OK);

			}

		} catch (Exception ignored) {
			ignored.printStackTrace();
		}

		return entity;
	}

	@ApiOperation(value = "文件服务：根据uuid下载文件", notes = "根据给出的文件Uuid下载文件")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "uuid", value = "要下载的文件Uuid", required = true, dataType = "String")
	})
    @GetMapping("downloadByUuid")
	@RequiresAuthentication
	public ResponseEntity<byte[]> downloadByUuid( @NotNull(message = "文件Uuid不能为空") @RequestParam("uuid") String uuid) throws Exception {
		ResponseEntity<byte[]> entity = null;
		try {

			FilestoreDownloadDTO downloadDTO = getFilestoreService().downloadByUuid(uuid);
			if (downloadDTO != null) {

				// 定义http头 ，状态
				HttpHeaders header = new HttpHeaders();
				header.add("Content-Disposition", "attchement;filename=" + downloadDTO.getName());
				header.setContentType(MediaTypeFactory.getMediaType(downloadDTO.getName()).get());

				// 定义ResponseEntity封装返回信息
				return new ResponseEntity<byte[]>(downloadDTO.getBytes(), header, HttpStatus.OK);

			}

		} catch (Exception ignored) {
			ignored.printStackTrace();
		}

		return entity;
	}
	
	public IFilestoreService getFilestoreService() {
		return filestoreService;
	}
	
}
