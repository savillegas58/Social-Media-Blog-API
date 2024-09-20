package DAO;

import static org.mockito.ArgumentMatchers.refEq;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jetty.websocket.core.internal.messages.MessageWriter;

import Model.Account;
import Model.Message;
import Util.ConnectionUtil;

public class SocialMediaDAO {
    

    public Account insertAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        
        try{
            String sql = "INSERT INTO account (username, password) VALUES (?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            preparedStatement.executeUpdate();

            ResultSet generatedAccountIDresultSet = preparedStatement.getGeneratedKeys();

            if(generatedAccountIDresultSet.next()){
                int account_id = generatedAccountIDresultSet.getInt(1);
                return new Account(account_id, account.getUsername(), account.getPassword());
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
        return null;
    }

    public Account loginAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();

        try{
            String sql = "INSERT INTO account (username, password) VALUES (?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                int account_id = resultSet.getInt(1);
                return new Account(account_id, account.getUsername(), account.getPassword());
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getAllMessages(){
        List<Message> messages = new ArrayList<>();
        Connection connection = ConnectionUtil.getConnection();

        try{
            String sql = "SELECT * FROM message;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Message message = new Message(resultSet.getInt("message_id"), resultSet.getInt("posted_by"), 
                    resultSet.getString("message_text"), resultSet.getLong("time_posted_epoch"));
                messages.add(message);
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return messages;


    }

    public Message getMessageByID(int message_id){
        Connection connection = ConnectionUtil.getConnection();

        try{
            String sql = "SELECT * FROM message WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, message_id);
            

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Message message = new Message(resultSet.getInt("message_id"),resultSet.getInt("posted_by"), 
                    resultSet.getString("message_text"), resultSet.getLong("time_posted_epoch"));
                return message;
            }


        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message deleteMessageByID(int message_id){
        Connection connection = ConnectionUtil.getConnection();

        try{
            String sql = "DELETE FROM message Where message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, message_id);
            preparedStatement.executeUpdate();

        } catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
        
    }

    public void updateMessageByID(int message_id, Message message){
        Connection connection = ConnectionUtil.getConnection();

        try{
            String sql = "UPDATE message SET message_id = ?, posted_by = ?, message_text = ?, time_posted_epoch = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
            preparedStatement.setInt(1, message_id);
            preparedStatement.setInt(2, message.getPosted_by());
            preparedStatement.setString(3, message.getMessage_text());
            preparedStatement.setLong(4, message.getTime_posted_epoch());
            preparedStatement.executeUpdate();

        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
    }

    public List<Message> getAllUserMessages(int account_id){
        List<Message> messages = new ArrayList<>();
        Connection connection = ConnectionUtil.getConnection();

        try{
            String sql = "SELECT * FROM message WHERE posted_by = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, account_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Message message = new Message(resultSet.getInt("message_id"),resultSet.getInt("posted_by"), 
                    resultSet.getString("message_text"), resultSet.getLong("time_posted_epoch"));
                messages.add(message);
            }

        } catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return messages;
    }

    public Account getAccountByUserName(String username){
        Connection connection = ConnectionUtil.getConnection();

        try{
            String sql = "SELECT * FROM account WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                Account account = new Account(resultSet.getInt("account_id"), resultSet.getString("username"), resultSet.getString("password"));
                return account;
            }

        } catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;


    }

}
