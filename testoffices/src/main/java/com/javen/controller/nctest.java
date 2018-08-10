package com.javen.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class nctest {
  public static void main(String[] args) {
	  String sql_zs = "UPDATE account SET money=money-1000 WHERE accountName=?";
	   String sql_ls = "UPDATE account SET money=money+1000 WHERE accountName=?";
    
	   Connection connec = null;//
	   PreparedStatement prepare = null;
	   try{
		Class.forName("com.mysql.jdbc.Driver");//连接驱动
	http://you3.youyifuke.com/
		try{
		    String url = "jdbc:mysql://127.0.0.1/test";
			String username="root";
		    String password="root";
		  connec=DriverManager.getConnection(url,username,password);
		  
		  //设置事务 需要手动提交
		  connec.setAutoCommit(false);
		  prepare = connec.prepareStatement(sql_zs);
		  prepare.setString(1, "zs");
		  int count_zs = prepare.executeUpdate();
		  System.out.println("zs"+count_zs);

		  //执行 ls
		 prepare =connec.prepareStatement(sql_ls);
		 prepare.setString(1, "ls");
		 int count = prepare.executeUpdate();
		 System.out.println(count);
		 
		}catch(Exception e){
			  
			try{
              connec.rollback();
			}catch(SQLException ex){
				throw new RuntimeException(ex);
			}
		 }finally {
			try{
				connec.commit();
			}catch(SQLException e1){
				
			}
			
			try{
              prepare.close();
              connec.close();
				
			}catch(SQLException e2){
				
			}
			
		}
		 
	   
	   }catch(Exception e){
		   e.printStackTrace();
	   }
       
	   
	   
	   
  }
}
