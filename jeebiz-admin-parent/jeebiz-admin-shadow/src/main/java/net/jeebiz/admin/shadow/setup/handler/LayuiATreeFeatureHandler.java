package net.jeebiz.admin.shadow.setup.handler;

import java.util.List;

import net.jeebiz.admin.extras.authz.feature.dao.entities.AuthzFeatureModel;
import net.jeebiz.admin.extras.authz.feature.dao.entities.AuthzFeatureOptModel;
import net.jeebiz.admin.extras.authz.feature.setup.handler.FeatureTreeDataHandler;
import net.jeebiz.admin.shadow.utils.FeatureTreeUtils;

public class LayuiATreeFeatureHandler extends FeatureTreeDataHandler {

	/*
		{
		  "code": 0,
		  "msg": "获取成功",
		  "data": {
		    "trees":[
		    	{"name": "用户管理", "value": "xsgl", "checked": true},
		    	{"name": "用户组管理", "value": "sbgl", "checked": true, "list": [
		    		{"name": "角色管理", "value": "sbgl-sbsjlb-1", "checked": true, "list":[
		    			{"name": "添加角色", "value": "sbgl-sbsjlb-dj-1", "checked": true},
		    			{"name": "角色列表", "value": "sbgl-sbsjlb-yl-1", "checked": false}
		    		]},
		        {"name": "管理员管理", "value": "sbgl-sbsjlb-2", "checked": true, "list":[
		          {"name": "添加管理员", "value": "sbgl-sbsjlb-dj-2", "checked": true},
		          {"name": "管理员列表", "value": "sbgl-sbsjlb-yl-2", "checked": false},
		          {"name": "管理员管理", "value": "sbgl-sbsjlb-3", "checked": true}
		        ]}
		      ]}
		    ]
		  }
		/}
	 */
	@Override
	public Object handle(List<AuthzFeatureModel> featureList, List<AuthzFeatureOptModel> featureOptList) {
		return FeatureTreeUtils.getAuthTreeList(featureList, featureOptList);
	}
	

}
