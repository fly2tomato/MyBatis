package com.yihaomen.test;

import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.yihaomen.mybatis.inter.IUserOperation;
import com.yihaomen.mybatis.model.User;

public class Test {
    private static SqlSessionFactory sqlSessionFactory;
    private static Reader reader; 

    static{
        try{
            reader    = Resources.getResourceAsReader("Configuration.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static SqlSessionFactory getSession(){
        return sqlSessionFactory;
    }
    
    public void getUserList(String userName) {
    	 SqlSession session = sqlSessionFactory.openSession();
         try {
         	//User user = (User) session.selectOne("com.yihaomen.mybatis.models.UserMapper.selectUserByID", 4);
         	IUserOperation userOperation = session.getMapper(IUserOperation.class);
         	//User user = userOperation.selectUserByID(1);
         	List<User> users = userOperation.selectUsers("%");
         	
         	for(User user:users){
         		System.out.println(user.getId()+":"+user.getUserName()+":"+user.getUserAddress());
         	}
         	
         	//System.out.println(user.getUserAddress());
         	//System.out.println(user.getUserName());

         } finally {
         session.close();
         }
	}
    
    public void addUser(){
    	User user = new User();
    	user.setUserAddress("人民广场");
    	user.setUserName("飞鸟");
    	user.setUserAge("24");
    	SqlSession session = sqlSessionFactory.openSession();
    	try {
			IUserOperation userOperation = session.getMapper(IUserOperation.class);
			userOperation.addUser(user);
			session.commit();
			System.out.println("当前增加的用户id为："+user.getId());
		} finally {
			// TODO: handle finally clause
			session.close();
		}
    }
    
    public void deleteUser(int id){
    	SqlSession session = sqlSessionFactory.openSession();
    	try {
			IUserOperation userOperation = session.getMapper(IUserOperation.class);
			userOperation.deleteUser(id);
			session.commit();
		} finally {
			// TODO: handle finally clause
			session.close();
		}
    }
    
    public static void main(String[] args) {
    	Test testUser=new Test();
        testUser.getUserList("%");
        //testUser.addUser();
        testUser.deleteUser(9);
    }
}