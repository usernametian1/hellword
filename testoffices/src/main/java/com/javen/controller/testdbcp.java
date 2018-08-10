package com.javen.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
 

public class testdbcp{
	
	public static void main(String[] args) {
		String sql_zs = "UPDATE account SET money=money-1000 WHERE accountName=?";
	    String sql_ls = "UPDATE account SET money=money+1000 WHERE accountName=?";
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    try {
	        Class.forName("com.mysql.jdbc.Driver");

	        try {
	                String url = "jdbc:mysql://127.0.0.1/test";
	                String user = "root";
	                String password = "root";

	                connection = DriverManager.getConnection(url, user, password);
	                //System.out.println(connection);
	                // 执行sql_zs


	                //实际上当不写时相当于下面的这行代码自动执行
	                //connection.setAutoCommit(true);

	                //一 设置事务手动提交，也就是将自动提交关闭
	                connection.setAutoCommit(false);

	                preparedStatement = connection.prepareStatement(sql_zs);
	                preparedStatement.setString(1, "zs");
	                int count_zs = preparedStatement.executeUpdate();
	                System.out.println("影响了"+count_zs+"行");

	                // 执行sql_ls
	                preparedStatement = connection.prepareStatement(sql_ls);
	                preparedStatement.setString(1, "ls");
	                int count_ls = preparedStatement.executeUpdate();
	                System.out.println("影响了"+count_ls+"行");

	        } catch (SQLException e) {
	            try {
	                //二 出现异常我希望回滚
	                connection.rollback();
	            } catch (SQLException e1) {
	                throw new RuntimeException(e);
	            }
	            throw new RuntimeException(e);
	        }
	    } catch (ClassNotFoundException e) {
	        throw new RuntimeException(e);
	    } finally {

	        //三 如果所有的操作执行成功
	        try {
	            connection.commit();
	        } catch (SQLException e1) {

	        }
	        try {
	            preparedStatement.close();
	            connection.close();
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }
	    }
	}
	}
    

    
