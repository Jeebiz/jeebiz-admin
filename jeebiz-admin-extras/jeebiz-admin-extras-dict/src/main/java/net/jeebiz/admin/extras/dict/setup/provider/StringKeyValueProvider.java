package net.jeebiz.admin.extras.dict.setup.provider;

import net.jeebiz.admin.extras.dict.service.IDictPairService;
import net.jeebiz.boot.api.provider.KeyValueProvider;

public class StringKeyValueProvider implements KeyValueProvider<String> {
	
	private IDictPairService keyValueService;
	
	public StringKeyValueProvider(IDictPairService keyValueService) {
		this.keyValueService = keyValueService;
	}

	@Override
	public String get(String key) {
		return getKeyValueService().getValue(key);
	}

	public IDictPairService getKeyValueService() {
		return keyValueService;
	}
	
}
