package com.infogen.cluster_limit.group_dao;

import java.time.Clock;

import org.apache.log4j.Logger;

import com.infogen.configuration.InfoGen_Configuration;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 
 * @author larry/larrylv@outlook.com/创建时间 2015年5月13日 下午5:06:34
 * @since 1.0
 * @version 1.0
 */
public class Redis_Group_DAO extends Group_DAO {
	private static final Logger LOGGER = Logger.getLogger(Redis_Group_DAO.class.getName());
	private JedisPool pool = null;

	public Redis_Group_DAO(JedisPool pool) {
		super();
		this.pool = pool;
	}

	public Jedis take() {
		if (pool == null) {
			LOGGER.error("Redis 没有初始化");
			return null;
		}
		return pool.getResource();
	}

	public void returnResource(Jedis redis) {
		pool.returnResourceObject(redis);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.infogen.limit.dao.Group_DAO#increment_and_get(java.lang.String)
	 */
	private Integer expire = 60 * 60 * 12;

	@Override
	public Long increment_and_get(String group_by, Integer timeslice) {
		Jedis take = take();
		Long incr;
		try {
			long millis = Clock.system(InfoGen_Configuration.zoneid).millis();
			timeslice = timeslice * 1000;
			group_by = new StringBuilder(group_by).append("_").append(millis - (millis % timeslice)).toString();
			incr = take.incr(group_by);
			take.expire(group_by, expire);
			System.out.println(group_by + "-----------------------");
		} finally {
			returnResource(take);
		}
		return incr;
	}

}
