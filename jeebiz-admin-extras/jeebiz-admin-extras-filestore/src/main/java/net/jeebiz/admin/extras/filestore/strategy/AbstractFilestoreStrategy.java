package net.jeebiz.admin.extras.filestore.strategy;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dozermapper.core.Mapper;
import com.github.hiwepy.ip2region.spring.boot.IP2regionTemplate;

import lombok.extern.slf4j.Slf4j;
import net.jeebiz.admin.extras.filestore.FilestoreRedisKey;
import net.jeebiz.admin.extras.filestore.bo.FileUploadBO;
import net.jeebiz.admin.extras.filestore.bo.FileUploadResult;
import net.jeebiz.admin.extras.filestore.dao.entities.FileEntity;
import net.jeebiz.admin.extras.filestore.service.IFilestoreService;
import net.jeebiz.admin.extras.filestore.web.dto.FileDTO;
import net.jeebiz.boot.api.ApiCode;
import net.jeebiz.boot.api.exception.BizRuntimeException;
import net.jeebiz.boot.api.sequence.Sequence;
import net.jeebiz.boot.extras.redis.setup.RedisOperationTemplate;

@Slf4j
public abstract class AbstractFilestoreStrategy implements FilestoreStrategy, InitializingBean, ApplicationEventPublisherAware {
	
	@Autowired
	private IFilestoreService filestoreService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
	private Mapper beanMapper;
	@Autowired
	private Sequence sequence;
    @Autowired
    private IP2regionTemplate ipTemplate;
    @Autowired
    private RedisOperationTemplate redisOperationTemplate;
    
	private ApplicationEventPublisher eventPublisher;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	@Transactional
	public FileUploadResult upload(FileUploadBO uploadBo) throws Exception {
		// 1、验证请求，如果有不满足有要求则应抛出异常
		boolean flag = this.preCheck(uploadBo);
		if (!flag) {
			return null;
		}
		// 2、钩子方法 用于扩展
		customizedMethod(uploadBo);
		// 3、通过检查则继续处理预订单创建
		FileUploadResult uploadRt = this.handleUpload(uploadBo);
		// 4、记录支付流水；短信发送状态（ 0：发送失败、1：发送成功）
    	if(Objects.nonNull(uploadRt) && uploadRt.getStatus() == 1) {
    		recordUpload(uploadBo, uploadRt);
    	}
    	afterUpload(uploadBo, uploadRt);
		// 5、返回支付结果
		return uploadRt;
	}
	
	protected <O extends FileUploadBO> boolean preCheck(O uploadBo) throws Exception {
		// 2.4、黑名单
		String blacklistKey = FilestoreRedisKey.UPLOAD_BLACKLIST.getKey();
		if (redisOperationTemplate.sHasKey(blacklistKey, uploadBo.getUserId())) {
			throw new BizRuntimeException(ApiCode.SC_BAD_REQUEST, "sms.send.backlist.limit");
		}
		return true;
	}
	
	protected <O extends FileUploadBO> void customizedMethod(O uploadBo) throws Exception {
		// TODO Auto-generated method stub
	};
 
	protected final <O extends FileUploadBO> FileUploadResult handleUpload(O uploadBo) throws Exception {
		
		FileUploadResult uploadRt = FileUploadResult.builder()
				.channel(uploadBo.getChannel())
				.files(this.handleFileUpload(uploadBo))
				.status(1)
				.userId(uploadBo.getUserId())
				.build();
		
		return uploadRt;
		 
	}
	
	protected abstract <O extends FileUploadBO> List<FileDTO> handleFileUpload(O uploadBo) throws Exception;
	
	protected <O extends FileUploadBO> void recordUpload(O uploadBo, FileUploadResult uploadRt) throws Exception {
		
		// ip获取国家
        String countryByIp = getIpTemplate().getCountryByIp(uploadBo.getIpAddress());
       
        FileEntity fileEntity = FileEntity.builder()
    			.appId(uploadBo.getAppId())
    			.appChannel(uploadBo.getAppChannel())
    			.appVer(uploadBo.getAppVer())
    			.country(countryByIp)
    			.ipAddress(uploadBo.getIpAddress())
    			.channel(this.getChannel())
    			.userId(uploadBo.getUserId())
    			.build();
        fileEntity.setCreateTime(new Date());
        fileEntity.setCreator(uploadBo.getUserId());
        getFilestoreService().save(fileEntity);
     
	}
	
	protected <O extends FileUploadBO> void afterUpload(O uploadBo, FileUploadResult uploadRt) throws Exception{
		
		log.debug("afterUpload");
		
	};
	 
	
	public ApplicationEventPublisher getEventPublisher() {
		return eventPublisher;
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}
	
	public IFilestoreService getFilestoreService() {
		return filestoreService;
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
	
	public RedisOperationTemplate getRedisOperationTemplate() {
		return redisOperationTemplate;
	}

}
