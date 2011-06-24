/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.session.category;

import java.util.Calendar;
import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.model.persist.EntityDao;

/**
 * @author chaostone
 * @version $Id: ClusterSessionStatMonitor.java Jun 20, 2011 9:28:11 PM chaostone $
 */
public class ClusterSessionStatMonitor {

	private EntityDao entityDao;
	private List<CategorySessionController> controllers = CollectUtils.newArrayList();

	public ClusterSessionStatMonitor(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	public void addController(CategorySessionController controller) {
		controllers.add(controller);
	}

	/**
	 * 比当前时间早十分钟的统计一律视为无效，将之清除
	 */
	public void gc(CategorySessionControllerFactory factory) {
		Calendar calendar = Calendar.getInstance();
		calendar.roll(Calendar.MINUTE, -10);
		entityDao
				.executeUpdateHql(
						"delete from org.beangle.security.core.session.category.CategorySessionStat css where css.statAt < ? and css.serverName<>?",
						calendar.getTime(), factory.getServerName());
	}

	public void report() {
		for (CategorySessionController controller : controllers) {
			CategorySessionStat stat = controller.getSessionStat().stat();
			entityDao
					.executeUpdateHql(
							"update org.beangle.security.core.session.category.CategorySessionStat css set css.statAt=?,css.online=? where css.serverName=? and css.category=?",
							stat.getStatAt(), stat.getOnline(), stat.getServerName(), stat.getCategory());
		}
	}

}
