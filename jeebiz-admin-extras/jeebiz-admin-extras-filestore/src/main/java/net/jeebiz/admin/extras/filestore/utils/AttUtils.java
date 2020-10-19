/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.filestore.utils;

import java.io.File;
import java.io.FileNotFoundException;

import org.springframework.util.ResourceUtils;

public class AttUtils {

	public static File getTargetDir(String localStorage, String dir) {
		File storeDir = new File(localStorage);
		if (!storeDir.exists()) {
			storeDir.mkdirs();
		}
		File target = new File(storeDir, dir);
		if (!target.exists()) {
			target.mkdirs();
		}
		return target;
	}

	public static File getStorePath(String root) throws FileNotFoundException {
		File path = new File(ResourceUtils.getURL("classpath:").getPath());
		if (!path.exists()) {
			path = new File("");
		}
		File store = new File(path.getAbsolutePath(), root);
		if (!store.exists()) {
			store.mkdirs();
		}
		// 在开发测试模式时，得到的地址为：{项目跟目录}/target/{root}
		// 在打包成jar正式发布时，得到的地址为：{发布jar包目录}/{root}
		return store;
	}

	//上传文件
	/*
	public static Map<String,Object> uploadFile(MultipartFile file){
		Map<String,Object> result = new HashMap<>();
		String savePath = null;
		if(file!=null){
			try {
				
				String dateString = DateFormatUtils.format(new Date(), "yyyy-MM-dd");
				
				if (ToolUtil.isNotEmpty(file.getOriginalFilename())) {
					String uploadPath = GlobalConstant.SERVER_IMAGE_FILE_PATH
							+ dateString;
					String originFile = file.getOriginalFilename();//得到上传时的文件名
					String extendName = FilenameUtils.getExtension(originFile);//获取扩展名
					String fileName = BaseCommonUtil.getUUID() + "." + extendName;
//						uploadPath = uploadPath + "/" + fileName;//上传文件路径，存放路径
					String uploadFtpPath =  gunsProperties.getFtpUploadAbsPath()+ dateString;
					savePath = GlobalConstant.SERVER_IMAGE_FILE_PATH_PREFIX
							+ dateString+ "/"
							+ fileName;//项目中文件访问路径
					boolean ftpFlag = gunsProperties.getFtpOpenFlag();
					if(ftpFlag){
						FtpUtils ftpUtils = new FtpUtils();
						ftpUtils.open();
						Boolean uploadFileFlag = ftpUtils.upload(uploadFtpPath,fileName,file.getInputStream());
						if(uploadFileFlag){
							result.put("code",0);
						}else{
							result.put("code",1);
						}
						ftpUtils.close();
					}else{
						File dir = new File(uploadPath);
						if (!dir.exists()) {
							dir.mkdirs();
						}
						OutputStream output = new FileOutputStream(new File(dir, fileName));
						InputStream input = file.getInputStream();
						IOUtils.copy(input, output);
						input.close();
						output.close();
						result.put("code",0);
					}


				}
			}catch(Exception e) {
				e.printStackTrace();
//					logger.error(e.toString(), e);
				result.put("code",1);
			}
		}else{
			result.put("code",1);
		}
		result.put("imgPath",savePath);
		return result;
	}*/
	
}
