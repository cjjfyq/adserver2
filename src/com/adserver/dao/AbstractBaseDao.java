package com.adserver.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.adserver.util.HibernateSessionFactory;
import com.adserver.util.LogUtil;
import com.adserver.util.ReflectionUtils;

public abstract class AbstractBaseDao<T, PK extends Serializable> {

	protected Class<T> entityClass;
	private Logger log = LogUtil.log;
	
    protected SessionFactory sessionFactory;
    
	public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public AbstractBaseDao() {
		this.entityClass = ReflectionUtils.getSuperClassGenricType(getClass());
	}

	public AbstractBaseDao(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	public Class<T> getEntityClass() {
		return entityClass;
	}
	
	public Session getSession() {
//		return HibernateSessionFactory.openSession();
	    return sessionFactory.openSession();
	}

	public void closeSession(Session session) {
		try {
			if(session.isOpen()) {
				session.close();
			}
		} catch (Exception e ) {
		}
	}
	
	public Session openSession() {
//		return HibernateSessionFactory.openSession();
		return  sessionFactory.openSession();
	}

	
	public void delete(T t) {
		Session session = null;
		Transaction tran = null;
		try {
			session = getSession();
			tran = session.beginTransaction();
			session.delete(t);
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSession(session);
		}
	}

	public void delete(PK id) {
		Session session = null;
		Transaction tran = null;
		try {
			session = getSession();
			tran = session.beginTransaction();
			session.createQuery("Delete FROM "+entityClass.getName()+" Where id="+id).executeUpdate();
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSession(session);
		}
	}

	@SuppressWarnings("unchecked")
	public T get(PK id) {
		T t = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = getSession();
			tran = session.beginTransaction();
			t = (T) session.get(entityClass, id);
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSession(session);
		}
		return t;
	}
	
	public void save(T t) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = getSession();
			tran = session.beginTransaction();
			session.save(t);
			tran.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			closeSession(session);
		}
	}

	public void saveOrUpdate(T t) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = getSession();
			tran = session.beginTransaction();
			session.saveOrUpdate(t);
			tran.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			closeSession(session);
		}
	}
	
	public void update(T t) {
		Session session = null;
		Transaction tran = null;
		try {
			session = getSession();
			tran = session.beginTransaction();
			session.update(t);
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSession(session);
		}
	}
	
	/**
	 * 用sql分页查询.
	 * @param page  分页参数.
	 * @param fields	要查询的列名
	 * @param fromSql	如："from t_product", "from t_product where a=b group by c"
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Page<Object[]> findByPageSQL(final Page<Object[]> page, String[] fields, String fromSql) {
		Session session = getSession();
		Transaction tran = session.beginTransaction();
		if(page==null){
			log.info("page不能为空");
			return null;
		}
		if(fields == null) {
			log.info("fields is null!");
			return null;
		}
		if(fields.length < 1) {
			log.info("fields is empty!");
			return null;
		}
		
		String countSql = "select count(*) cc " + 
			(fromSql.indexOf("order by") > 0 
			? fromSql.substring(0, fromSql.indexOf("order by")) 
			: fromSql);
//		if (countSql.contains("group")) countSql = "select case when sum(cc)>0 then sum(cc) else 0 end from (" +countSql+ ") temp";
			
		try {
			try {
				List<Long> totalCount = session.createSQLQuery(countSql).list();
				page.setTotalCount(totalCount.get(0));
				log.info("totalCount:["+totalCount.get(0).toString()+"条记录] "+countSql);
			} catch(Exception e) {
				log.info("查询记录数出错\t"+countSql);
				log.error(e, e);
				return null;
			}
			
			StringBuilder sb = new StringBuilder();
			// 查询的列
			sb.append("select");
			int i = 0;
			for(i=0; i<fields.length-1; i++) {
				sb.append(" ").append(fields[i]).append(",");
			}
			sb.append(" ").append(fields[i]).append(" ");

			// from语句
			sb.append(fromSql);
			
			//	排序
			if(!StringUtils.isEmpty(page.getOrder())) {
				
				try {
					
					String[] orders = StringUtils.split(page.getOrder(), ',');
					String[] orderBys = StringUtils.split(page.getOrderBy(), ',');
					
					for(i = 0; i<orders.length && i<orderBys.length; i++) {
						fromSql = sb.toString();
						if(fromSql.toUpperCase().contains("ORDER") && fromSql.toUpperCase().contains("BY")) {
							sb.append(",");
						} else {
							sb.append(" order by ");
						}
						sb	
						.append(orderBys[i])
						.append(" ")
						.append(orders[i]);
					}
				} catch (Exception e) {
					sb = new StringBuilder(fromSql);
					log.error("BaseDaoImpl.findByPageSQL:", e);
				}
			}
			// 分页 
			sb.append(" limit ").append(page.getFirst()).append(",").append(page.getPageSize());
			
			fromSql = sb.toString();
			
			List<Object[]> result = session.createSQLQuery(fromSql).list();
			page.setResult(result);
			tran.commit();
			log.info("PageSize:["+result.size()+"条结果] "+fromSql);
		} catch (Exception e) {
			log.info("查询出错：sql="+fromSql);
			log.error(e, e);
		} finally {
			closeSession(session);
		}
		return page;
	}
	
	/**
	 * 用hql分页查询.
	 * 
	 * @param page
	 *            分页参数.
	 * 
	 * @return 分页查询结果.附带结果列表及所有查询时的参数.
	 */
	@SuppressWarnings("unchecked")
	public Page<T> findByPage(final Page<T> page, String hql) {
		Session session = null;
		Transaction tran = null;
		try {
			session = getSession();
			tran = session.beginTransaction();
			
			if(page==null)log.info("page不能为空");
			
			String countHql = "select count(id) " + hql;
			try {
				long totalCount = (Long)session.createQuery(countHql).uniqueResult();
				
				page.setTotalCount(totalCount);
			
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
	//		 	排序
				StringBuilder sb = new StringBuilder(hql);
				if(!StringUtils.isEmpty(page.getOrder())) {
					
					try {
					
						String[] orders = StringUtils.split(page.getOrder(), ',');
						String[] orderBys = StringUtils.split(page.getOrderBy(), ',');
						
						for(int i = 0; i<orders.length && i<orderBys.length; i++) {
							hql = sb.toString();
							if(hql.toUpperCase().contains("ORDER") && hql.toUpperCase().contains("BY")) {
								sb.append(",");
							} else {
								sb.append(" order by ");
							}
							sb	
								.append(orderBys[i])
								.append(" ")
								.append(orders[i]);
						}
					} catch (Exception e) {
						
						sb = new StringBuilder(hql);
						
						log.error("BaseDaoImpl.findByPage:", e);
					}
					
				}
				hql = sb.toString();
				
				List<T> result = session.createQuery(hql)
											.setFirstResult(page.getFirst())
											.setMaxResults(page.getPageSize())
											.list();
				page.setResult(result);
				
				tran.commit();
			}
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
			closeSession(session);
		}
		return page;
	}
	/**
	 * 用hql分页查询.
	 * 
	 * @param page
	 *            分页参数.
	 * 
	 * @return 分页查询结果.附带结果列表及所有查询时的参数.
	 */
	@SuppressWarnings("unchecked")
	public Page<T> findByPage2(final Page<T> page, String hql,String countHql) {
		Session session = null;
		Transaction tran = null;
		try {
			session = getSession();
			tran = session.beginTransaction();
			
			if(page==null)log.info("page不能为空");
			
			//String countHql = "select count(id) " + hql;
			try {
				long totalCount = (Long)session.createQuery(countHql).uniqueResult();
				
				page.setTotalCount(totalCount);
			
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
	//		 	排序
				StringBuilder sb = new StringBuilder(hql);
				if(!StringUtils.isEmpty(page.getOrder())) {
					
					try {
					
						String[] orders = StringUtils.split(page.getOrder(), ',');
						String[] orderBys = StringUtils.split(page.getOrderBy(), ',');
						
						for(int i = 0; i<orders.length && i<orderBys.length; i++) {
							hql = sb.toString();
							if(hql.toUpperCase().contains("ORDER") && hql.toUpperCase().contains("BY")) {
								sb.append(",");
							} else {
								sb.append(" order by ");
							}
							sb	
								.append(orderBys[i])
								.append(" ")
								.append(orders[i]);
						}
					} catch (Exception e) {
						
						sb = new StringBuilder(hql);
						
						log.error("BaseDaoImpl.findByPage:", e);
					}
					
				}
				hql = sb.toString();
				
				List<T> result = session.createQuery(hql)
											.setFirstResult(page.getFirst())
											.setMaxResults(page.getPageSize())
											.list();
				page.setResult(result);
				
				tran.commit();
			}
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
			closeSession(session);
		}
		return page;
	}
	public long getTotalCounts(String hql) {
		long totalCount = 0;
		Session session = null;
		Transaction tran = null;
		try {
			session = getSession();
			tran = session.beginTransaction();
			String queryString = "select count(id) " + hql;
			totalCount = (Long)session.createQuery(queryString).uniqueResult();
			tran.commit();
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
			closeSession(session);
		}
		return totalCount;
	}
	
//	public 
	
	
	/**
	 * 批量执行sql
	 * @param sqls
	 * @throws Exception
	 */
	public void executeUpdate(Vector<String> sqls, int batchSize) throws Exception {
		/*if(sqls == null) {
			return ;
		}
		Session session = null;
		Transaction transaction = null;
		Connection conn = null;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			conn = session.connection();

			Statement stmt = conn.createStatement();
			for(int i=0; i<sqls.size() ; i++) {
				stmt.addBatch(sqls.elementAt(i));
			}
			stmt.executeBatch();
			transaction.commit();
		} catch(Exception e) {
			throw e;
		} finally {
			if(conn != null)
				conn.close();
			closeSession(session);
		}*/
	}
	
	/**
	 * 不分页查所有
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll(String hql){
		List<T> list = new ArrayList<T>();
		Session session = null;
		Transaction tran = null;
		try {
			session = getSession();
			tran = session.beginTransaction();
            list = session.createQuery(hql).list();
            tran.commit();
        } catch (Exception e) {
		    e.printStackTrace();
		} finally {
			closeSession(session);
		}

		return list;
	}
	
	/**
	 * @Description 通过sql查找列表
	 * @author Tim
	 * @Date 2014-1-21下午04:44:52
	 * @param sql
	 * @return
	 */
	public List getListBySQL(String sql) {
		Session session = openSession();
		List list = null;
		try {
			Transaction tran = session.beginTransaction();
			tran.begin();
			list = session.createSQLQuery(sql).list();
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSession(session);
		}
		return list;
	}

}
