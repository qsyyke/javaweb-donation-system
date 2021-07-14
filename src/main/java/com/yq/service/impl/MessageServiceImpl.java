package com.yq.service.impl;

import com.yq.dao.MessageDao;
import com.yq.dao.impl.MessageDaoImpl;
import com.yq.domain.Message;
import com.yq.service.MessageService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MessageServiceImpl implements MessageService {

    private MessageDao messageDao = new MessageDaoImpl();
    @Override
    public List<Map<String, Object>> getMessage () {
        return messageDao.getMessage();
    }

    @Override
    public List<Map<String, Object>> messageAllPageSer (String username,String startTime,String endTime,int startRow, int pageSize) {
        String sql = "select * from message where 1=1 ";
        String sqlAll = "select * from message where 1=1 ";
        if (!("".equals(startTime)) && !("".equals(username))) {
            //用户名和时间都不为空
            sql = sql + "and username like '%"+username+"%' and date between '"+startTime + "' and '"+endTime+"' limit ?,?";
            sqlAll = sqlAll + "and username like '%"+username+"%' and date between '"+startTime + "' and '"+endTime+"'";
            return messageDao.messageAllPageDao(startRow,pageSize,sql,sqlAll);
        }

        if ("".equals(startTime) && "".equals(username)) {
            //用户名和时间都为空
            sql = sql +"limit ?,?";
            return messageDao.messageAllPageDao(startRow,pageSize,sql,sqlAll);
        }

        if ("".equals(username)) {
            //用户名为空
            sql = sql+ " and date between '"+startTime+"' and '"+endTime+"' limit ?,?";
            sqlAll = sqlAll+ " and date between '"+startTime+"' and '"+endTime+"'";
            return messageDao.messageAllPageDao(startRow,pageSize,sql,sqlAll);
        }

        if ("".equals(startTime)) {
            //用户名为空
            sql = sql+ " and username like '%"+username+"%' limit ?,?";
            sqlAll = sqlAll+" and username like '%"+username+"%'";
            return messageDao.messageAllPageDao(startRow,pageSize,sql,sqlAll);
        }

        return new ArrayList<>();
    }

    @Override
    public int insertMessageService (String message,String date,String province,String uname) {
        return messageDao.insertMessageDao(message,date,province,uname);
    }

    @Override
    public List<Map<String, Object>> randomFromSystemMessageService () {
        return messageDao.randomFromSystemMessageDao();
    }

    @Override
    public List<Map<String, Object>> messageAllInfoSer () {
        return messageDao.messageAllInfoDao();
    }

    @Override
    public int updateMessageInfoSer (Message message) {
        return messageDao.updateMessageDao(message.getMessage(),message.getProvince(),message.getUsername());
    }

    @Override
    public int deleteMessageInfoSer (Message message) {
        return messageDao.deleteMessageDao(message.getUsername());
    }
}
