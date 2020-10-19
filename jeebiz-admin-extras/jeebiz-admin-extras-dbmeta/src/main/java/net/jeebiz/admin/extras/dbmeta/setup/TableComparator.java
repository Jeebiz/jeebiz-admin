package net.jeebiz.admin.extras.dbmeta.setup;

import java.util.Comparator;

import net.jeebiz.admin.extras.dbmeta.web.vo.TableVo;

public class TableComparator  implements Comparator<TableVo>{

	@Override
	public int compare(TableVo o1, TableVo o2) {
		return o1.getRemark().compareTo(o2.getRemark());
	}
	
}
