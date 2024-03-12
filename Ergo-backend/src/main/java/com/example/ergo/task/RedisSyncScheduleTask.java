package com.example.ergo.task;

import com.example.ergo.config.ListenHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RedisSyncScheduleTask {

	@Autowired
	private ListenHandler listenHandler;

	/**
	 * 从Redis同步博客文章浏览量到数据库
	 */
	public void syncBlogViewsToDatabase() {
		listenHandler.afterDestroy();

	}
}