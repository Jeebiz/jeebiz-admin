/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.inform.setup.provider;

import net.jeebiz.admin.extras.inform.dao.IInformRecordDao;
import net.jeebiz.admin.extras.inform.dao.entities.InformRecordModel;
import net.jeebiz.admin.extras.inform.setup.InformProvider;

public class DefaultInformOutputProvider implements InformOutputProvider<InformRecordModel> {

    private IInformRecordDao informRecordDao;
	
    public DefaultInformOutputProvider(IInformRecordDao informRecordDao) {
    	this.informRecordDao = informRecordDao;
    }
	
	@Override
	public InformProvider getProvider() {
		return InformProvider.NOTICE;
	}
	
	@Override
	public boolean output(InformRecordModel inform) {
		getInformRecordDao().insert(inform);
		return false;
	}

	public IInformRecordDao getInformRecordDao() {
		return informRecordDao;
	}
	
}
