package net.jeebiz.admin.extras.filestore.strategy;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dozermapper.core.Mapper;
import com.github.hiwepy.ip2region.spring.boot.IP2regionTemplate;
import com.google.common.collect.Lists;

import hitool.core.collections.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import net.jeebiz.admin.extras.filestore.FilestoreRedisKey;
import net.jeebiz.admin.extras.filestore.bo.FileData;
import net.jeebiz.admin.extras.filestore.bo.FileDeleteBO;
import net.jeebiz.admin.extras.filestore.bo.FileDownloadResult;
import net.jeebiz.admin.extras.filestore.bo.FileReuploadBO;
import net.jeebiz.admin.extras.filestore.bo.FileReuploadResult;
import net.jeebiz.admin.extras.filestore.bo.FileStoreBO;
import net.jeebiz.admin.extras.filestore.bo.FileUploadBO;
import net.jeebiz.admin.extras.filestore.bo.FileUploadResult;
import net.jeebiz.admin.extras.filestore.bo.FilesUploadBO;
import net.jeebiz.admin.extras.filestore.bo.FilesUploadResult;
import net.jeebiz.admin.extras.filestore.dao.IFileMapper;
import net.jeebiz.admin.extras.filestore.dao.entities.FileEntity;
import net.jeebiz.admin.extras.filestore.enums.FilestoreChannel;
import net.jeebiz.boot.api.ApiCode;
import net.jeebiz.boot.api.exception.BizRuntimeException;
import net.jeebiz.boot.api.sequence.Sequence;
import net.jeebiz.boot.extras.redis.setup.RedisOperationTemplate;

@Slf4j
public abstract class AbstractFilestoreStrategy implements FilestoreStrategy, InitializingBean, ApplicationEventPublisherAware {
	
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
	private Mapper beanMapper;
	@Autowired
	private IFileMapper fileMapper;
	@Autowired
	private Sequence sequence;
    @Autowired
    private IP2regionTemplate ipTemplate;
    @Autowired
    private RedisOperationTemplate redisOperation;
    
	private ApplicationEventPublisher eventPublisher;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	@Transactional
	public <O extends FileUploadBO> FileUploadResult upload(O uploadBo) throws Exception {
		// 1、验证请求，如果有不满足有要求则应抛出异常
		boolean flag = this.preCheck(uploadBo);
		if (!flag) {
			return null;
		}
		// 2、钩子方法 用于扩展
		customizedMethod(uploadBo);
		// 3、通过检查则继续处理上传
		FileUploadResult uploadRt = this.handleUpload(uploadBo);
		// 4、记录上传流水；文件上传状态（ 0：上传失败、1：上传成功）
    	if(Objects.nonNull(uploadRt) && uploadRt.getStatus() == 1) {
    		recordUploadFlow(uploadBo, uploadRt);
    	}
		// 5、返回上传结果
		return uploadRt;
	}
	
	protected <O extends FileUploadBO> boolean preCheck(O uploadBo) throws Exception {
		// 2.4、黑名单
		String blacklistKey = FilestoreRedisKey.UPLOAD_BLACKLIST.getKey();
		if (getRedisOperation().sHasKey(blacklistKey, uploadBo.getUserId())) { 
			throw new BizRuntimeException(ApiCode.SC_FORBIDDEN, "file.upload.backlist.limit");
		}
		return true;
	}
	
	protected <O extends FilesUploadBO> void customizedMethod(O uploadBo) throws Exception {
		// TODO Auto-generated method stub
	};
	

	protected final <O extends FileUploadBO> FileUploadResult handleUpload(O uploadBo) throws Exception{
		
		FileData fileDto = this.handleFileUpload(uploadBo.getFile(), uploadBo.getWidth(), uploadBo.getHeight());
		
		FileUploadResult uploadRt = FileUploadResult.builder()
				.channel(uploadBo.getChannel())
				.file(fileDto)
				.status(1)
				.userId(uploadBo.getUserId())
				.build();
		return uploadRt;
	};
	
	protected <O extends FileUploadBO> void recordUploadFlow(O uploadBo, FileUploadResult uploadRt) throws Exception{
		
		// ip获取国家
        String countryByIp = getIpTemplate().getCountryByIp(uploadBo.getIpAddress());
        
    	this.recordFileEntity(countryByIp, uploadBo, uploadRt.getFile());
		
	};
	
	@Override
	@Transactional
	public <O extends FilesUploadBO> FilesUploadResult upload(O uploadBo) throws Exception {
		// 1、验证请求，如果有不满足有要求则应抛出异常
		boolean flag = this.preCheck(uploadBo);
		if (!flag) {
			return null;
		}
		// 2、钩子方法 用于扩展
		customizedMethod(uploadBo);
		// 3、通过检查则继续处理上传
		FilesUploadResult uploadRt = this.handleUpload(uploadBo);
		// 4、记录上传流水；文件上传状态（ 0：上传失败、1：上传成功）
    	if(Objects.nonNull(uploadRt) && uploadRt.getStatus() == 1) {
    		recordUploadFlow(uploadBo, uploadRt);
    	}
		// 5、返回上传结果
		return uploadRt;
	}
	
	protected <O extends FilesUploadBO> boolean preCheck(O uploadBo) throws Exception {
		// 2.4、黑名单
		String blacklistKey = FilestoreRedisKey.UPLOAD_BLACKLIST.getKey();
		if (getRedisOperation().sHasKey(blacklistKey, uploadBo.getUserId())) { 
			throw new BizRuntimeException(ApiCode.SC_FORBIDDEN, "file.upload.backlist.limit");
		}
		return true;
	}
	
	protected <O extends FileUploadBO> void customizedMethod(O uploadBo) throws Exception {
		// TODO Auto-generated method stub
	};
	
	protected <O extends FilesUploadBO> FilesUploadResult handleUpload(O uploadBo) throws Exception{
		
		List<FileData> fileList = Lists.newArrayList();
		for (MultipartFile file : uploadBo.getFiles()) {
			FileData fileDto = this.handleFileUpload(file, uploadBo.getWidth(), uploadBo.getHeight());
			fileList.add(fileDto);
		}
		
		FilesUploadResult uploadRt = FilesUploadResult.builder()
				.channel(uploadBo.getChannel())
				.files(fileList)
				.status(1)
				.userId(uploadBo.getUserId())
				.build();
		return uploadRt;
	}
	
	
	protected <O extends FilesUploadBO> void recordUploadFlow(O uploadBo, FilesUploadResult uploadRt) throws Exception{
		
		// ip获取国家
        String countryByIp = getIpTemplate().getCountryByIp(uploadBo.getIpAddress());
        
        for (FileData file : uploadRt.getFiles()) {
        	this.recordFileEntity(countryByIp, uploadBo, file);
		}
		
	};

	protected abstract FileData handleFileUpload(MultipartFile file, int width, int height) throws Exception;
	
	@Override
	@Transactional
	public <O extends FileDeleteBO> boolean delete(O deleteBo) throws Exception {
		List<FileEntity> fileList = null;
		if(CollectionUtils.isNotEmpty(deleteBo.getPaths())) {
			// 查询path对象的文件记录
			fileList = getFileMapper().selectList(new QueryWrapper<FileEntity>().in("f_path", deleteBo.getPaths()));
		}
		else if(CollectionUtils.isNotEmpty(deleteBo.getUuids())) {
			// 查询Uid对象的文件记录
			fileList = getFileMapper().selectList(new QueryWrapper<FileEntity>().in("f_uuid", deleteBo.getUuids()));
		}
		if(CollectionUtils.isNotEmpty(fileList)) {
			for (FileEntity fileEntity : fileList) {
				this.handleFileDelete(fileEntity);
				getFileMapper().deleteById(fileEntity.getId());
			}
		}
		return false;
	}
	
	protected abstract boolean handleFileDelete(FileEntity fileEntity) throws Exception;

	@Override
	@Transactional
	public <O extends FileReuploadBO> FileReuploadResult reupload(O uploadBo) throws Exception {

		// 查询旧文件信息
		FileEntity entity = getFileMapper().selectOne(new QueryWrapper<FileEntity>().eq("f_uuid", uploadBo.getUuid()));
		if(entity == null) {
			throw new BizRuntimeException(uploadBo.getUuid() + "指向的文件不存在");
		}
		// 1、验证请求，如果有不满足有要求则应抛出异常
		boolean flag = this.preCheck(uploadBo);
		if (!flag) {
			return null;
		}
		// 2、钩子方法 用于扩展
		customizedMethod(uploadBo);
		// 3、通过检查则继续处理上传
		FileReuploadResult uploadRt = this.handleReupload(uploadBo);
		// 4、记录上传流水；文件上传状态（ 0：上传失败、1：上传成功）
    	if(Objects.nonNull(uploadRt) && uploadRt.getStatus() == 1) {
    		recordReuploadFlow(uploadBo, uploadRt);
    	}
		// 5、返回上传结果
		return uploadRt;
	}
	
	protected <O extends FileReuploadBO> boolean preCheck(O uploadBo) throws Exception {
		// 2.4、黑名单
		String blacklistKey = FilestoreRedisKey.UPLOAD_BLACKLIST.getKey();
		if (getRedisOperation().sHasKey(blacklistKey, uploadBo.getUserId())) { 
			throw new BizRuntimeException(ApiCode.SC_FORBIDDEN, "file.upload.backlist.limit");
		}
		return true;
	}
	
	protected <O extends FileReuploadBO> void customizedMethod(O uploadBo) throws Exception {
		// TODO Auto-generated method stub
	};
	
	protected <O extends FileReuploadBO> FileReuploadResult handleReupload(O uploadBo) throws Exception{
		
		// 1、查询旧文件信息
		FileEntity fileEntity = getFileMapper().selectOne(new QueryWrapper<FileEntity>().eq("f_uuid", uploadBo.getUuid()));
		if(fileEntity == null) {
			throw new BizRuntimeException(uploadBo.getUuid() + "指向的文件不存在");
		}
		
		// 2、上传新文件
		FileData fileDto = this.handleFileUpload(uploadBo.getFile(), uploadBo.getWidth(), uploadBo.getHeight());
		
		// 3、删除旧的文件
		this.handleFileDelete(fileEntity); 
		getFileMapper().deleteById(fileEntity.getId());
		
		// 4、返回操作结果
		FileReuploadResult uploadRt = FileReuploadResult.builder()
				.channel(uploadBo.getChannel())
				.file(fileDto)
				.status(1)
				.userId(uploadBo.getUserId())
				.build();
		return uploadRt;
	}

	protected <O extends FileReuploadBO> void recordReuploadFlow(O uploadBo, FileReuploadResult uploadRt) throws Exception{
		// ip获取国家
        String countryByIp = getIpTemplate().getCountryByIp(uploadBo.getIpAddress());
        this.recordFileEntity(countryByIp, uploadBo, uploadRt.getFile());
	};
	
	protected void recordFileEntity(String countryByIp, FileStoreBO uploadBo, FileData file) {
		FileEntity fileEntity = FileEntity.builder()
    			.appId(uploadBo.getAppId())
    			.appChannel(uploadBo.getAppChannel())
    			.appVer(uploadBo.getAppVer())
    			.channel(this.getChannel())
    			.country(countryByIp)
    			.ext(file.getExt())
    			.name(file.getName())
    			.store(uploadBo.getChannel().getKey())
    			.group1(uploadBo.getBucketName())
    			.path(file.getPath())
    			.thumb(file.getThumb())
    			.ipAddress(uploadBo.getIpAddress())
    			.userId(uploadBo.getUserId())
    			.uuid(file.getUuid())
    			.build();
        fileEntity.setCreateTime(new Date());
        fileEntity.setCreator(uploadBo.getUserId());
        getFileMapper().insert(fileEntity);
        log.info(" Upload File : {}" , JSONObject.toJSONString(fileEntity));
	}
	
	
	@Override
	public List<FileData> listByPath(List<String> paths) throws Exception {
		if (CollectionUtils.isEmpty(paths)) {
            return Lists.newArrayList();
        }
		// 查询文件信息
        List<FileEntity> fileList = getFileMapper().selectList(new QueryWrapper<FileEntity>().in("f_path", paths));
		if (CollectionUtils.isEmpty(fileList)) {
            return Lists.newArrayList();
        }
		return fileList.stream().sorted().map(entity -> {
			return this.handleFileMetadata(entity);
		}).collect(Collectors.toList());
	}
	
	@Override
	public List<FileData> listByUuid(List<String> uuids) throws Exception {
		if (CollectionUtils.isEmpty(uuids)) {
            return Lists.newArrayList();
        }
		// 查询文件信息
		List<FileEntity> fileList = getFileMapper().selectList(new QueryWrapper<FileEntity>().in("f_uuid", uuids));
		if (CollectionUtils.isEmpty(fileList)) {
            return Lists.newArrayList();
        }
		return fileList.stream().sorted().map(entity -> {
			return this.handleFileMetadata(entity);
		}).collect(Collectors.toList());
	}
	
	protected abstract FileData handleFileMetadata(FileEntity entity);
	
	@Override
	public FileDownloadResult downloadByPath(String path) throws Exception {
		// 查询文件信息
		FileEntity entity = getFileMapper().selectOne(new QueryWrapper<FileEntity>().eq("f_path", path));
		if(entity == null) {
			throw new BizRuntimeException(path + "指向的文件不存在");
		}
		FileDownloadResult downloadRt = FileDownloadResult.builder()
				.channel(FilestoreChannel.valueOf(entity.getStore()))
				.file(this.handleFileMetadata(entity))
				.status(1)
				.userId(entity.getUserId())
				.build();
		this.handleFileDownload(entity, downloadRt);
		return downloadRt;
	}
	
	@Override
	public FileDownloadResult downloadByUuid(String uuid) throws Exception {
		// 查询文件信息
		FileEntity entity = getFileMapper().selectOne(new QueryWrapper<FileEntity>().eq("f_uuid", uuid));
		if(entity == null) {
			throw new BizRuntimeException(uuid + "指向的文件不存在");
		}
		FileDownloadResult downloadRt = FileDownloadResult.builder()
				.channel(FilestoreChannel.valueOf(entity.getStore()))
				.file(this.handleFileMetadata(entity))
				.status(1)
				.userId(entity.getUserId())
				.build();
		this.handleFileDownload(entity, downloadRt);
		return downloadRt;
	}
	
	protected abstract void handleFileDownload(FileEntity entity, FileDownloadResult downloadRt) throws Exception;
	
	public ApplicationEventPublisher getEventPublisher() {
		return eventPublisher;
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}
	
	public IFileMapper getFileMapper() {
		return fileMapper;
	}

	protected ObjectMapper getObjectMapper() {
		return objectMapper;
	}

	protected Mapper getBeanMapper() {
		return beanMapper;
	}

	protected Sequence getSequence() {
		return sequence;
	}
	
	public IP2regionTemplate getIpTemplate() {
		return ipTemplate;
	}
	
	public RedisOperationTemplate getRedisOperation() {
		return redisOperation;
	}

}
