package com.mmr.wordtalk.common.audit.handle;

import com.mmr.wordtalk.admin.api.entity.SysAuditLog;
import com.mmr.wordtalk.common.audit.annotation.Audit;
import org.javers.core.Changes;

import java.util.List;

/**
 * @author 张恩睿
 * @date 2023/2/26
 *
 * 审计日志处理器
 */
public interface IAuditLogHandle {

	void handle(Audit audit, Changes changes);

	void asyncSend(List<SysAuditLog> auditLogList);

}
