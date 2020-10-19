package net.jeebiz.admin.extras.dict.setup.provider;

import net.jeebiz.admin.extras.dict.service.IKeyValueService;
import net.jeebiz.boot.api.provider.KeyValueProvider;

public class StringKeyValueProvider implements KeyValueProvider<String> {
	
	private IKeyValueService keyValueService;
	
	public StringKeyValueProvider(IKeyValueService keyValueService) {
		this.keyValueService = keyValueService;
	}

	@Override
	public String get(String key) {
		return getKeyValueService().getValue(key);
	}

	public IKeyValueService getKeyValueService() {
		return keyValueService;
	}
	
}
