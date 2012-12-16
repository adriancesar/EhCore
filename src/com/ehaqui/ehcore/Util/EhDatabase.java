package com.ehaqui.ehcore.Util;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.bukkit.plugin.Plugin;


public class EhDatabase
{
    
    private Connection                                conn    = null;
    private Statement                                 statement;
    //               rows      col_name       value
    private HashMap<Integer, HashMap<String, Object>> rows    = new HashMap<Integer, HashMap<String, Object>>();
    private HashMap<String, Object>                   column  = new HashMap<String, Object>();
    
    private int                                       numRows = 0;
    
    private Plugin                                    plugin;
    private DatabaseType                              type;
    
    private String                                    db_host;
    private String                                    db_port;
    
    private String                                    db_database;
    private String                                    db_user;
    private String                                    db_pass;
    private String                                    file_name;
    
    public String                                     lastQuery;
    
    public EhDatabase(Plugin pl, String host, String port, String database, String user, String pass)
    {
        this.plugin = pl;
        type = DatabaseType.MYSQL;
        
        
        if("".equals(host) || host == null)
            db_host = "localhost";
        else
            db_host = host;
        
        
        if("".equals(port) || port == null)
            db_port = "3306";
        else
            db_port = port;
        
        db_database = database;
        db_user = user;
        db_pass = pass;
    }
    
    public EhDatabase(Plugin pl, String fileName)
    {
        this.plugin = pl;
        type = DatabaseType.SQLITE;
        
        this.file_name = fileName;
    }
    
    public void setup() throws SQLException
    {
        String mysql_prefix = "[MySQL] ";
        
        plugin.getLogger().info(mysql_prefix + "Chegando conexao");
        
        if(getConnection() != null)
        {
            plugin.getLogger().info(mysql_prefix + "Conectado!");
        }
        else
            plugin.getLogger().severe("Erro na conexao com o Banco de dados");
    }
    
    public Connection getConnection()
    {
        if(conn == null)
        {
            return open();
        }
        
        return conn;
    }
    
    public Connection open()
    {
        String url;
        
        try
        {
            switch (type)
            {
                case MYSQL:
                    Class.forName("com.mysql.jdbc.Driver").newInstance();
                    url = "jdbc:mysql://" + db_host + ":" + db_port + "/" + db_database + "?autoReconnect=true&failOverReadOnly=false&maxReconnects=10";
                    
                    conn = DriverManager.getConnection(url, db_user, db_pass);
                    break;
                
                case SQLITE:
                    Class.forName("org.sqlite.JDBC");
                    url = "jdbc:sqlite:" + plugin.getDataFolder().getAbsolutePath() + File.separator + file_name + ".db";
                    conn = DriverManager.getConnection(url, "", "");
                    break;
            }
            return conn;
            
        } catch (Exception e)
        {
            plugin.getLogger().warning(e.getMessage());
        }
        return null;
    }
    
    public void close()
    {
        if(conn != null)
        {
            try
            {
                statement.close();
                conn.close();
            } catch (Exception e)
            {
                plugin.getLogger().warning(e.getMessage());
            }
        }
    }
    
    public boolean checkTable(String tableName)
    {
        DatabaseMetaData dbm = null;
        
        try
        {
            dbm = this.open().getMetaData();
            ResultSet tables = dbm.getTables(null, null, tableName, null);
            
            if(tables.next())
                return true;
            else
                return false;
            
        } catch (Exception e)
        {
            plugin.getLogger().warning(e.getMessage());
            return false;
        }
    }
    
    public boolean createTable(String tableName, String[] columns, String[] dims)
    {
        try
        {
            statement = conn.createStatement();
            String query = "CREATE TABLE " + tableName + "(";
            
            for (int i = 0; i < columns.length; i++)
            {
                if(i != 0)
                {
                    query += ",";
                }
                
                query += columns[i] + " " + dims[i];
            }
            
            query += ")";
            statement.execute(query);
            
        } catch (Exception e)
        {
            plugin.getLogger().warning(e.getMessage());
        }
        return true;
    }
    
    public ResultSet query(String query, Object... txt)
    {
        try
        {
            statement = conn.createStatement();
            ResultSet results = statement.executeQuery(String.format(query, txt));
            return results;
            
        } catch (Exception e)
        {
            if(!e.getMessage().contains("not return ResultSet") || (e.getMessage().contains("not return ResultSet") && query.startsWith("SELECT")))
            {
                plugin.getLogger().warning(e.getMessage());
            }
        }
        return null;
    }
    
    public HashMap<String, Object> selectRow(String query, Object... txt) throws SQLException
    {
        HashMap<String, Object> column = new HashMap<String, Object>();
        
        statement = conn.createStatement();
        ResultSet results = statement.executeQuery(String.format(query, txt));
        
        if(results != null)
        {
            int columns = results.getMetaData().getColumnCount();
            String columnNames = "";
            
            for (int i = 1; i <= columns; i++)
            {
                if(!"".equals(columnNames))
                {
                    columnNames += ",";
                }
                columnNames += results.getMetaData().getColumnName(i);
            }
            
            String[] columnArray = columnNames.split(",");
            
            while (results.next())
            {
                for (String columnName : columnArray)
                {
                    column.put(columnName, results.getObject(columnName));
                }
            }
            
            results.close();
            
            return column;
        }
        else
        {
            return null;
        }
        
    }
    
    public HashMap<String, Object> selectRowFast(String query, Object... txt)
    {
        try
        {
            statement = conn.createStatement();
            
            ResultSet results = statement.executeQuery(String.format(query, txt));
            
            if(results != null)
            {
                int columns = results.getMetaData().getColumnCount();
                String columnNames = "";
                
                for (int i = 1; i <= columns; i++)
                {
                    if(!"".equals(columnNames))
                    {
                        columnNames += ",";
                    }
                    columnNames += results.getMetaData().getColumnName(i);
                }
                
                String[] columnArray = columnNames.split(",");
                
                while (results.next())
                {
                    for (String columnName : columnArray)
                    {
                        column.put(columnName, results.getObject(columnName));
                    }
                }
                
                results.close();
                
                return column;
            }
            else
            {
                return null;
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
        
    }
    
    public HashMap<Integer, HashMap<String, Object>> selectCol(String query, Object... txt) throws SQLException
    {
        rows.clear();
        numRows = 0;
        
        statement = conn.createStatement();
        ResultSet results = statement.executeQuery(String.format(query, txt));
        
        if(results != null)
        {
            int columns = results.getMetaData().getColumnCount();
            String columnNames = "";
            
            for (int i = 1; i <= columns; i++)
            {
                if(!"".equals(columnNames))
                {
                    columnNames += ",";
                }
                columnNames += results.getMetaData().getColumnName(i);
            }
            
            String[] columnArray = columnNames.split(",");
            numRows = 0;
            
            while (results.next())
            {
                HashMap<String, Object> thisColumn = new HashMap<String, Object>();
                
                for (String columnName : columnArray)
                {
                    thisColumn.put(columnName, results.getObject(columnName));
                }
                rows.put(numRows, thisColumn);
                numRows++;
            }
            
            results.close();
            
            return rows;
        }
        else
        {
            return null;
        }
    }
    
    public HashMap<Integer, HashMap<String, Object>> selectColFast(String query, Object... txt)
    {
        rows.clear();
        numRows = 0;
        
        
        try
        {
            statement = conn.createStatement();
            
            ResultSet results = statement.executeQuery(String.format(query, txt));
            
            if(results != null)
            {
                int columns = results.getMetaData().getColumnCount();
                String columnNames = "";
                
                for (int i = 1; i <= columns; i++)
                {
                    if(!"".equals(columnNames))
                    {
                        columnNames += ",";
                    }
                    columnNames += results.getMetaData().getColumnName(i);
                }
                
                String[] columnArray = columnNames.split(",");
                numRows = 0;
                
                while (results.next())
                {
                    HashMap<String, Object> thisColumn = new HashMap<String, Object>();
                    
                    for (String columnName : columnArray)
                    {
                        thisColumn.put(columnName, results.getObject(columnName));
                    }
                    rows.put(numRows, thisColumn);
                    numRows++;
                }
                
                results.close();
                
                return rows;
            }
            else
            {
                return null;
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
    public int insert(String query, Object... txt) throws SQLException
    {
        int id = 0;
        
        statement = conn.createStatement();
        statement.executeUpdate(String.format(query, txt), Statement.RETURN_GENERATED_KEYS);
        
        ResultSet rs = statement.getGeneratedKeys();
        
        while (rs.next())
            id = rs.getInt(1);
        
        rs.close();
        
        return id;
    }
    
    public int insertFast(String query, Object... txt)
    {
        int id = 0;
        
        try
        {
            statement = conn.createStatement();
            statement.executeUpdate(String.format(query, txt), Statement.RETURN_GENERATED_KEYS);
            
            ResultSet rs = statement.getGeneratedKeys();
            
            while (rs.next())
                id = rs.getInt(1);
            
            rs.close();
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return id;
    }
    
    public void update(String query, Object... txt) throws SQLException
    {
        statement = conn.createStatement();
        statement.executeUpdate(String.format(query, txt));
    }
    
    public void updateFast(String query, Object... txt)
    {
        try
        {
            statement = conn.createStatement();
            statement.executeUpdate(String.format(query, txt));
            
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        
    }
    
    
    public String sql(String database, HashMap<String, Object> data)
    {
        StringBuilder builder = new StringBuilder();
        
        builder.append("INSERT INTO `" + database + "` (");
        
        int size = 1;
        
        for (String key : data.keySet())
        {
            builder.append("`");
            builder.append(key);
            builder.append("`");
            
            if(size < data.size())
            {
                builder.append(", ");
                size++;
            }
        }
        
        builder.append(") VALUES (");
        
        size = 1;
        for (Object key : data.values())
        {
            builder.append("'");
            builder.append(key.toString());
            builder.append("'");
            
            if(size < data.size())
            {
                builder.append(", ");
                size++;
            }
        }
        
        builder.append(")");
        
        return builder.toString();
    }
}


enum DatabaseType
{
    MYSQL,
    
    SQLITE
}
