package net.jeebiz.admin.extras.dbmeta.setup;

import java.util.Comparator;

import net.jeebiz.admin.extras.dbmeta.web.dto.TableDTO;

public class TableComparator  implements Comparator<TableDTO>{

	@Override
	public int compare(TableDTO o1, TableDTO o2) {
		return o1.getRemark().compareTo(o2.getRemark());
	}
	
}
