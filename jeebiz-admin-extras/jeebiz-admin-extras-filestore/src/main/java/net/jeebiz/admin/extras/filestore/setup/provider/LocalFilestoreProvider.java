/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.filestore.setup.provider;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.biz.utils.SubjectUtils;
import org.springframework.biz.utils.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;

import net.coobird.thumbnailator.Thumbnails;
import net.jeebiz.admin.extras.filestore.dao.IFilestoreDao;
import net.jeebiz.admin.extras.filestore.dao.entities.FilestoreModel;
import net.jeebiz.admin.extras.filestore.setup.config.JeebizFilestoreLocalProperties;
import net.jeebiz.admin.extras.filestore.utils.AttUtils;
import net.jeebiz.admin.extras.filestore.web.vo.FilestoreConfig;
import net.jeebiz.admin.extras.filestore.web.vo.FilestoreDownloadVo;
import net.jeebiz.admin.extras.filestore.web.vo.FilestoreVo;
import net.jeebiz.boot.api.exception.BizRuntimeException;
import net.jeebiz.boot.api.utils.CollectionUtils;

public class LocalFilestoreProvider implements FilestoreProvider {

	private IFilestoreDao filestoreDao;
	private JeebizFilestoreLocalProperties filestoreProperties;
	
	public LocalFilestoreProvider(IFilestoreDao filestoreDao, JeebizFilestoreLocalProperties filestoreProperties) {
		this.filestoreDao = filestoreDao;
		this.filestoreProperties = filestoreProperties;
	}

	@Override
	public FilestoreEnum getProvider() {
		return FilestoreEnum.LOCAL;
	}
	
	@Override
	public FilestoreConfig getConfig() {
		FilestoreConfig config = new FilestoreConfig();
		config.setEndpoint(filestoreProperties.getEndpoint());
		return config;
	}

	@Override
	public FilestoreVo upload(MultipartFile file, int width, int height) throws Exception {
		try {
			
			// 文件存储目录
			File fileDir = AttUtils.getTargetDir(getFilestoreProperties().getUserDir(), "files");
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			};
			
			String uuid = UUID.randomUUID().toString();
			String basename = DateFormatUtils.format(System.currentTimeMillis(), "YYYYMMDD") + File.separator + uuid;
			String path = basename + FilenameUtils.getFullExtension(file.getOriginalFilename());
			
			file.transferTo(new File(fileDir, path));
			
			// 保存缩略图
			String thumbPath =  basename + new LocalThumbImage(width, height).getPrefixName() + FilenameUtils.getFullExtension(file.getOriginalFilename());
			try(FileOutputStream output = new FileOutputStream(new File(fileDir, thumbPath))){
				Thumbnails.of(file.getInputStream()).scale(width, height).toOutputStream(output);
			}
			
			// 文件存储记录对象
			FilestoreModel model = new FilestoreModel();

			ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
			
			model.setUuid(uuid);
			model.setUid(principal.getUserkey());
			model.setName(file.getOriginalFilename());
			model.setExt(FilenameUtils.getExtension(file.getOriginalFilename()));
			model.setTo(FilestoreEnum.LOCAL.getKey());
			model.setPath(path);
			getFilestoreDao().insert(model);
			
			// 文件存储信息
			FilestoreVo attVo = new FilestoreVo();
			attVo.setUuid(uuid);
			attVo.setName(file.getOriginalFilename());
			attVo.setPath(path);
			
			return attVo;
			
		} catch (Exception e) {
			throw new BizRuntimeException("测试报告附件存储IO异常");
		}
	}
	
	@Override
	public List<FilestoreVo> upload(MultipartFile[] files, int width, int height) throws Exception {
		List<FilestoreVo> attList = Lists.newArrayList();
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		for (MultipartFile file : files) {
			
			try {
				
				// 文件存储目录
				File fileDir = AttUtils.getTargetDir(getFilestoreProperties().getUserDir(), "files");
				if (!fileDir.exists()) {
					fileDir.mkdirs();
				};
				
				String uuid = UUID.randomUUID().toString();
				String path =  DateFormatUtils.format(System.currentTimeMillis(), "YYYYMMDD") + File.separator + uuid + FilenameUtils.getFullExtension(file.getOriginalFilename());
				
				file.transferTo(new File(fileDir, path));
				
				// 文件存储记录对象
				FilestoreModel model = new FilestoreModel();
				
				model.setUuid(uuid);
				model.setUid(principal.getUserkey());
				model.setName(file.getOriginalFilename());
				model.setExt(FilenameUtils.getExtension(file.getOriginalFilename()));
				model.setTo(FilestoreEnum.LOCAL.getKey());
				model.setPath(path);
				getFilestoreDao().insert(model);
				
				// 文件存储信息
				FilestoreVo attVo = new FilestoreVo();
				attVo.setUuid(uuid);
				attVo.setName(file.getOriginalFilename());
				attVo.setPath(path);
				
				attList.add(attVo);
				
			} catch (Exception e) {
				throw new BizRuntimeException("测试报告附件存储IO异常");
			}
		}
		return attList;
	}
	
	@Override
	public boolean deleteByPath(List<String> paths) throws Exception {
		
		if(CollectionUtils.isEmpty(paths)) {
			return false;
		}
		
		// 文件存储目录
		File fileDir = AttUtils.getTargetDir(getFilestoreProperties().getUserDir(), "files");
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		};
		
		// 查询UID对象的文件记录
		List<FilestoreModel> files = getFilestoreDao().getPaths(paths);
		// 删除文件记录
		getFilestoreDao().deleteByPaths(paths);
		// 删除服务器文件，如果出现异常将会回滚前面的操作
		for (FilestoreModel model : files) {
			// 删除旧的文件
			File oldFile = new File(fileDir, model.getPath()); 
			if(oldFile.exists()) {
				oldFile.delete();
			}
			getFilestoreDao().delete(model.getUuid());
		}
		
		return true;
	}
	
	@Override
	public boolean deleteByUuid(List<String> uuids) throws IOException {
		
		if(CollectionUtils.isEmpty(uuids)) {
			return false;
		}
		
		// 文件存储目录
		File fileDir = AttUtils.getTargetDir(getFilestoreProperties().getUserDir(), "files");
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		};
		
		// 查询UID对象的文件记录
		List<FilestoreModel> files = getFilestoreDao().getFiles(uuids);
		// 删除文件记录
		getFilestoreDao().deleteByUuids(uuids);
		// 删除服务器文件，如果出现异常将会回滚前面的操作
		for (FilestoreModel model : files) {
			
			// 删除旧的文件
			File oldFile = new File(fileDir, model.getPath()); 
			if(oldFile.exists()) {
				oldFile.delete();
			}
			getFilestoreDao().delete(model.getUuid());
			
		}
		
		return true;
		
	}


	@Override
	public FilestoreVo reupload(String uuid, MultipartFile file, int width, int height) throws IOException {
		// 查询文件信息
		FilestoreModel model = getFilestoreDao().getByUuid(uuid);
		if(model == null) {
			throw new BizRuntimeException(uuid + "指向的文件不存在");
		}
		
		// 上传新文件
		
		// 文件存储目录
		File fileDir = AttUtils.getTargetDir(getFilestoreProperties().getUserDir(), "files");
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		};

		String uuid1 = UUID.randomUUID().toString();
		String path =  DateFormatUtils.format(System.currentTimeMillis(), "YYYYMMDD") + File.separator + uuid + FilenameUtils.getFullExtension(file.getOriginalFilename());
		file.transferTo(new File(fileDir, path));
		
		// 文件存储信息
		FilestoreVo attVo = new FilestoreVo();
		attVo.setUuid(uuid1);
		attVo.setName(file.getOriginalFilename());
		attVo.setPath(path);
		
		// 文件存储记录对象
		model.setUid(uuid1);
		model.setName(file.getOriginalFilename());
		model.setExt(FilenameUtils.getExtension(file.getOriginalFilename()));
		model.setTo(FilestoreEnum.LOCAL.getKey());
		model.setPath(path);
		getFilestoreDao().insert(model);
		
		// 删除旧的文件
		File oldFile = new File(fileDir, model.getPath()); 
		if(oldFile.exists()) {
			oldFile.delete();
		}
		getFilestoreDao().delete(uuid);
		
		return attVo;
	}
 
	@Override
	public List<FilestoreVo> listByPath(List<String> paths) throws Exception {
		
		List<FilestoreVo> attList = Lists.newArrayList();
		
		for (String path : paths) {
			
			// 文件存储信息
			FilestoreVo attVo = new FilestoreVo();
			
			attVo.setPath(path);
			//attVo.setUrl(getFdfsTemplate().getAccsssURL(Constants.GROUP_NAME, path));
			
			attList.add(attVo);

		} 
		
		return attList;
	}

	@Override
	public List<FilestoreVo> listByUuid(List<String> uuids) throws Exception {
		
		List<FilestoreVo> attList = Lists.newArrayList();
		
		// 查询文件信息
		List<FilestoreModel> fileList = getFilestoreDao().getFiles(uuids);
				
		for (FilestoreModel model : fileList) {
			
			// 文件存储信息
			FilestoreVo attVo = new FilestoreVo();
			
			attVo.setUuid(model.getUuid());
			attVo.setName(model.getName());
			attVo.setPath(model.getPath());
			//attVo.setUrl(getFdfsTemplate().getAccsssURL(model.getGroup(), model.getPath()));
			
			attList.add(attVo);

		} 
		
		return attList;
	}
	

	@Override
	public FilestoreDownloadVo downloadByPath(String path) throws Exception {
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FilestoreDownloadVo downloadByUuid(String uuid) throws Exception {

		// 查询文件信息
		FilestoreModel model = getFilestoreDao().getModel(uuid);
		if(model == null) {
			throw new BizRuntimeException(uuid + "指向的文件不存在");
		}
		
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public IFilestoreDao getFilestoreDao() {
		return filestoreDao;
	}

	public JeebizFilestoreLocalProperties getFilestoreProperties() {
		return filestoreProperties;
	}
	
}
