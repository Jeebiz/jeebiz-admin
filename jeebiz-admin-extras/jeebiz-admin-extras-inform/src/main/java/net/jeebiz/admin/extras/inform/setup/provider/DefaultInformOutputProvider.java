/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.inform.setup.provider;

import net.jeebiz.admin.extras.inform.dao.InformRecordMapper;
import net.jeebiz.admin.extras.inform.dao.entities.InformRecordModel;
import net.jeebiz.admin.extras.inform.setup.InformProvider;

public class DefaultInformOutputProvider implements InformOutputProvider<InformRecordModel> {

    private InformRecordMapper informRecordMapper;
	
    public DefaultInformOutputProvider(InformRecordMapper informRecordMapper) {
    	this.informRecordMapper = informRecordMapper;
    }
	
	@Override
	public InformProvider getProvider() {
		return InformProvider.NOTICE;
	}
	
	@Override
	public boolean output(InformRecordModel inform) {
		getInformRecordMapper().insert(inform);
		return false;
	}

	public InformRecordMapper getInformRecordMapper() {
		return informRecordMapper;
	}
	
}
