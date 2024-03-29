package com.plf.druid;

import com.alibaba.druid.DbType;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.visitor.SchemaStatVisitor;
import com.alibaba.druid.util.JdbcConstants;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


/**
 * @author panlf
 * @date 2021/7/4
 */
public class DruidUtils {
    //创建数据源对象
    private static DataSource dataSource;

    static {

        //新建一个配置文件对象
        Properties properties = new Properties();

        //通过类加载器找到文件路径，读配置文件
        InputStream inputStream = DruidUtils.class.getResourceAsStream("/druid.properties");

        //加载属性文件
        try {
            properties.load(inputStream);
            //创建连接池对象
            dataSource = DruidDataSourceFactory.createDataSource(properties);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static List<Map<String, Object>> parseSql(String sql, DbType dbType){
        List<SQLStatement> list = SQLUtils.parseStatements(sql,dbType);
        List<Map<String, Object>> result = new ArrayList<>();
        if(!CollectionUtils.isEmpty(list)){
            for(SQLStatement sqlStatement:list){
                Map<String, Object> map = new HashMap<>();
                SchemaStatVisitor schemaStatVisitor = new SchemaStatVisitor();
                sqlStatement.accept(schemaStatVisitor);
                map.put("dbType",schemaStatVisitor.getDbType());
                map.put("columns",schemaStatVisitor.getColumns());
                map.put("tables",schemaStatVisitor.getTables());
                map.put("conditions",schemaStatVisitor.getConditions());
                map.put("group",schemaStatVisitor.getGroupByColumns());
                map.put("order",schemaStatVisitor.getOrderByColumns());
                result.add(map);
            }
        }
        return result;
    }


    /*
     * 从连接池中获取连接
     * */
    public static Connection getConnect(){
        Connection con = null;
        try {
            con = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }


    /*
     * 关闭资源
     * */
    public static void close(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet){

        if(connection!=null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(preparedStatement!=null){
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(resultSet!=null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
