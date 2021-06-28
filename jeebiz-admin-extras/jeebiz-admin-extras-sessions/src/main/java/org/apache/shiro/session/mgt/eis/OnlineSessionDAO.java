package org.apache.shiro.session.mgt.eis;

import java.io.Serializable;
import java.util.Date;

import org.apache.shiro.biz.utils.StringUtils;
import org.apache.shiro.biz.web.Constants;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.OnlineSession;

import net.jeebiz.admin.extras.monitor.dao.entities.SessionEntity;
import net.jeebiz.admin.extras.monitor.service.IOnlineSessionService;
import net.jeebiz.admin.extras.monitor.utils.OnlineSessionUtils;

public class OnlineSessionDAO extends EnterpriseCacheSessionDAO {

	/**
     * 上次同步数据库的时间戳
     */
    private static final String LAST_SYNC_DB_TIMESTAMP = OnlineSessionDAO.class.getName() + "LAST_SYNC_DB_TIMESTAMP";
    /**
     * 同步session到数据库的周期 单位为毫秒（默认5分钟）
     */
    private long dbSyncPeriod = 5 * 60 * 1000;
    
    private IOnlineSessionService onlineSessionService;
    
    
    public void setDbSyncPeriod(long dbSyncPeriod) {
        this.dbSyncPeriod = dbSyncPeriod;
    }
    

    @Override
    protected Session doReadSession(Serializable sessionId) {
        SessionEntity sessionModel = getOnlineSessionService().getModel(String.valueOf(sessionId));
        if (sessionModel == null) {
            return null;
        }
        return sessionModel.getSession();
    }

    /**
     * 将会话同步到DB
     * @param session
     */
    public void syncToDb(OnlineSession session) {

        Date lastSyncTimestamp = (Date) session.getAttribute(LAST_SYNC_DB_TIMESTAMP);

        //当会话中的属性改变时-->强制同步
        //如果对于属性丢失影响不大 可以考虑把这块功能去掉
        if (lastSyncTimestamp != null) {
            boolean needSync = true;
            long deltaTime = session.getLastAccessTime().getTime() - lastSyncTimestamp.getTime();
            if (deltaTime < dbSyncPeriod) { //时间差不足  无需同步
                needSync = false;
            }
            
            boolean isGuest = StringUtils.hasText(session.getUserid());
            //如果不是游客 且session 数据变更了 同步
            if (isGuest == false && session.isAttributeChanged()) {
                needSync = true;
            }
            if (needSync == false) {
                return;
            }
        }

        session.setAttribute(LAST_SYNC_DB_TIMESTAMP, session.getLastAccessTime());

        //更新完后 重置标识
        if (session.isAttributeChanged()) {
            session.resetAttributeChanged();
        }

        getOnlineSessionService().online(OnlineSessionUtils.toOnlineSession(session));

    }

    /**
     * 会话过期时 离线处理
     * @param session
     */
    @Override
    protected void doDelete(Session session) {
        OnlineSession onlineSession = (OnlineSession) session;
        //定时任务删除的此时就不删除了
        if (onlineSession.getAttribute(Constants.ONLY_CLEAR_CACHE) == null) {
            try {
            	getOnlineSessionService().offline(String.valueOf(onlineSession.getId()));
            } catch (Exception e) {
                //即使删除失败也无所谓
            }
        }
    }

	public IOnlineSessionService getOnlineSessionService() {
		return onlineSessionService;
	}

	public void setOnlineSessionService(IOnlineSessionService onlineSessionService) {
		this.onlineSessionService = onlineSessionService;
	}
    
}
