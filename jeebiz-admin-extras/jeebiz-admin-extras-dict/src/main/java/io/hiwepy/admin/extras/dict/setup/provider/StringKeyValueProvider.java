package io.hiwepy.admin.extras.dict.setup.provider;

import io.hiwepy.admin.extras.dict.service.IDictPairService;
import io.hiwepy.boot.api.provider.KeyValueProvider;

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