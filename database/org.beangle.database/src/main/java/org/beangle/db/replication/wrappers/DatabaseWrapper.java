/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.replication.wrappers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.db.dialect.Dialect;
import org.beangle.db.meta.ColumnMetadata;
import org.beangle.db.meta.DatabaseMetadata;
import org.beangle.db.meta.TableMetadata;
import org.beangle.db.meta.TypeUtils;
import org.beangle.db.replication.DataWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class DatabaseWrapper extends JdbcTemplate implements DataWrapper {

	protected static final Logger logger = LoggerFactory.getLogger(DatabaseWrapper.class.getName());

	protected DatabaseMetadata meta;
	protected String catalog;
	protected String productName;
	protected String schema;

	protected List<String> tableNames = CollectUtils.newArrayList();
	protected List<String> viewNames = CollectUtils.newArrayList();
	protected List<String> sequenceNames = CollectUtils.newArrayList();

	public DatabaseWrapper() {
		super();
	}

	public DatabaseWrapper(String schema) {
		super();
		this.schema = schema;
	}

	public List<Object> getData(String tableName) {
		TableMetadata tableMeta = meta.getTables().get(tableName);
		if (null == tableMeta) {
			return Collections.emptyList();
		} else {
			return getData(tableMeta);
		}
	}

	public int count(TableMetadata table) {
		String sql = getQueryString(table);
		String countStr = "select count(*) from (" + sql + ")";
		return queryForInt(countStr);
	}

	public List<Object> getData(TableMetadata table, PageLimit limit) {
		String sql = getQueryString(table);
		String limitSql = meta.getDialect().getLimitString(sql, limit.getPageNo() > 1);
		if (limit.getPageNo() == 1) {
			return query(limitSql, new Object[] { limit.getPageSize() });
		} else {
			return query(limitSql,
					new Object[] { limit.getPageNo() * limit.getPageSize(),
							(limit.getPageNo() - 1) * limit.getPageSize() });
		}
	}

	public List<Object> getData(TableMetadata table) {
		return query(getQueryString(table));
	}

	private String getQueryString(TableMetadata table) {
		StringBuilder sb = new StringBuilder();
		sb.append("select ");
		String[] columnNames = table.getColumnNames();
		for (String columnName : columnNames) {
			sb.append(columnName).append(',');
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(" from ").append(table.getName());
		return sb.toString();
	}

	public int pushData(final TableMetadata table, List<Object> datas) {
		if (null == meta.getTableMetadata(table.identifier())) {
			try {
				execute(table.sqlCreateString(meta.getDialect()));
			} catch (Exception e) {
				logger.warn("cannot create table {}", table.identifier());
				e.printStackTrace();
			}
		}
		final String[] columnNames = table.getColumnNames();
		String insertSql = table.sqlInsertString();
		int successed = 0;
		try {
			Connection conn = getDataSource().getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(insertSql);
			for (Object item : datas) {
				try {
					final Object[] data = (Object[]) item;
					for (int i = 0; i < columnNames.length; i++) {
						ColumnMetadata cm = table.getColumnMetadata(columnNames[i]);
						TypeUtils.setValue(ps, i + 1, data[i], cm.getTypeCode());
					}
					ps.execute();
					successed++;
				} catch (Exception e) {

				}
			}
			ps.close();
			conn.commit();
			conn.close();
			return successed;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public void close() {
	}

	public List<Object> query(String sql) {
		return query(sql, (Object[]) null);
	}

	public List<Object> query(String sql, Object[] params) {
		return query(sql, params, new RowMapper<Object>() {
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				int columnCount = rs.getMetaData().getColumnCount();
				if (columnCount == 1) {
					return rs.getObject(1);
				} else {
					Object[] row = new Object[columnCount];
					for (int i = 0; i < columnCount; i++) {
						row[i] = rs.getObject(i + 1);
					}
					return row;
				}
			}
		});
	}

	public void setMetadata(DatabaseMetadata metadata) {
		this.meta = metadata;
	}

	public DatabaseMetadata getMetadata() {
		return meta;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public Dialect getDialect() {
		return meta.getDialect();
	}

	/**
	 * conntect to data source
	 * 
	 * @param targetDB
	 * @param dialect
	 */
	public void connect(DataSource dataSource, Dialect dialect) {
		try {
			setDataSource(dataSource);
			meta = new DatabaseMetadata(dataSource.getConnection(), dialect);
			meta.loadAllMetadata(getSchema(), null, false);
		} catch (SQLException e) {
			logger.error("cannot build connection using:{} under dialect {}", dataSource, dialect);
			throw new RuntimeException(e);
		}
	}
}
