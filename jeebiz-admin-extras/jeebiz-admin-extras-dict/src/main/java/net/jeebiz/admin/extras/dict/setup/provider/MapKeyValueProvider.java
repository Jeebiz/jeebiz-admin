package net.jeebiz.admin.extras.dict.setup.provider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.jeebiz.admin.extras.dict.service.IKeyValueService;
import net.jeebiz.boot.api.dao.entities.PairModel;
import net.jeebiz.boot.api.provider.KeyValueProvider;
import hitool.core.collections.CollectionUtils;

public class MapKeyValueProvider implements KeyValueProvider<Map<String, String>> {

	private IKeyValueService keyValueService;
	
	public MapKeyValueProvider(IKeyValueService keyValueService) {
		this.keyValueService = keyValueService;
	}

	@Override
	public Map<String, String> get(String key) {
		List<PairModel> pairList = getKeyValueService().getPairValues(key);
		// 返回的结果对象
		Map<String, String> rtMap = new HashMap<String, String>();
		if(!CollectionUtils.isEmpty(pairList)) {
			for (PairModel pairModel : pairList) {
				rtMap.put(pairModel.getKey(), pairModel.getValue());
			}
		}
		return rtMap;
	}

	public IKeyValueService getKeyValueService() {
		return keyValueService;
	}
	
}
