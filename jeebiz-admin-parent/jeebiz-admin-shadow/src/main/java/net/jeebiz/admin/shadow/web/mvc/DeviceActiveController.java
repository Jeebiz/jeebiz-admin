package net.jeebiz.admin.shadow.web.mvc;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.jeebiz.admin.extras.device.setup.listener.DeviceActiveEvent;
import net.jeebiz.admin.extras.device.web.dto.DeviceActiveEventDTO;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.XHeaders;
import net.jeebiz.boot.api.web.BaseApiController;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "设备激活：数据记录（Ok）")
@RestController
@RequestMapping("/device/")
@Slf4j 
public class DeviceActiveController extends BaseApiController {

	@ApiOperation(value = "设备激活埋点接口", notes = "客户端第一次启动时调用此接口注册客户端设备（另外，在其他接口顺便把这3个HTTP头都带上，以备他用）")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "header", required = true, name = XHeaders.X_DEVICE_IMEI, value = "客户端设备唯一标识", dataType = "String"),
		@ApiImplicitParam(paramType = "header", required = true, name = XHeaders.X_DEVICE_MODEL, value = "客户端设备型号", dataType = "String"),
		@ApiImplicitParam(paramType = "header", required = true, name = XHeaders.X_APP_ID, value = "应用ID", dataType = "String"),
		@ApiImplicitParam(paramType = "header", required = true, name = XHeaders.X_APP_CHANNEL, value = "应用渠道编码", dataType = "String"),
		@ApiImplicitParam(paramType = "header", required = true, name = XHeaders.X_APP_VERSION, value = "应用客户端版本号", dataType = "String")
	})
	@PostMapping("active")
	public ApiRestResponse<String> active(@ApiIgnore HttpServletRequest request) {
		
		String deviceIMEI = request.getHeader(XHeaders.X_DEVICE_IMEI);
		String deviceModel = request.getHeader(XHeaders.X_DEVICE_MODEL);
		String appId = request.getHeader(XHeaders.X_APP_ID);
		String appChannel = request.getHeader(XHeaders.X_APP_CHANNEL);
		String appVersion = request.getHeader(XHeaders.X_APP_VERSION);
		log.info(XHeaders.X_DEVICE_IMEI + "：{}", deviceIMEI);
		log.info(XHeaders.X_DEVICE_MODEL + "：{}", deviceModel);
		
		DeviceActiveEventDTO eventDTO = new DeviceActiveEventDTO();
		eventDTO.setDeviceIMEI(deviceIMEI);
		eventDTO.setDeviceModel(deviceModel);
		eventDTO.setAppChannel(appChannel);
		eventDTO.setAppId(appId);
		eventDTO.setAppVersion(appVersion);
		getEventPublisher().publishEvent(new DeviceActiveEvent(this, eventDTO));
		
		return success("endpoint.device.active.success");
	}
	 

}