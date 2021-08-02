package net.jeebiz.admin.extras.filestore.strategy;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dozermapper.core.Mapper;
import com.github.hiwepy.ip2region.spring.boot.IP2regionTemplate;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;
import net.jeebiz.admin.extras.filestore.FilestoreRedisKey;
import net.jeebiz.admin.extras.filestore.bo.FileUploadBO;
import net.jeebiz.admin.extras.filestore.bo.FileUploadResult;
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
	
	protected boolean preCheck(FileUploadBO uploadBo) throws Exception {
		// 2.4、黑名单
		String blacklistKey = FilestoreRedisKey.UPLOAD_BLACKLIST.getKey();
		if (redisOperationTemplate.sHasKey(blacklistKey, uploadBo.getUserId())) {
			throw new BizRuntimeException(ApiCode.SC_BAD_REQUEST, "sms.send.backlist.limit");
		}
		return true;
	}
	
	protected void customizedMethod(FileUploadBO uploadBo) throws Exception {
		// TODO Auto-generated method stub
	};
 
	protected <O extends FileUploadBO> FileUploadResult handleUpload(O uploadBo) throws Exception {
		
		List<FileDTO> attList = Lists.newArrayList();
		for (MultipartFile file : uploadBo.getFiles()) {
			FileDTO attDTO = this.handleUpload(uid, file, width, height);
			attList.add(attDTO);
		}
		return attList;
		
		
	}
	
	protected abstract <O extends FileUploadBO> FileUploadResult handleUpload(String uid, MultipartFile file, int width, int height) throws Exception;
	
	protected <O extends FileUploadBO> void recordUpload(O uploadBo, FileUploadResult uploadRt) throws Exception {
		
		// ip获取国家
        String countryByIp = getIpTemplate().getCountryByIp(uploadBo.getIpAddress());
        /*
        FileEntity fileEntity = FileEntity.builder()
    			.appId(payBo.getAppId())
    			.appChannel(payBo.getAppChannel())
    			.appVer(payBo.getAppVer())
    			.country(countryByIp)
    			.ipAddress(payBo.getIpAddress())
    			.channel(this.getChannel())
    			.userId(payBo.getUserId())
    			.build();
        flowEntity.setCreateTime(new Date());
        flowEntity.setCreator(payBo.getUserId());
        getSmsFlowService().save(flowEntity);*/
     
	}
	
	protected <O extends FileUploadBO> void afterUpload(O uploadBo, FileUploadResult uploadRt) throws Exception{
		
		
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
