package DAO;



import java.sql.*;
import java.util.ArrayList;
import java.util.List;


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

    // public Account loginAccount(Account account){
    //     Connection connection = ConnectionUtil.getConnection();

    //     try{
    //         String sql = "INSERT INTO account (username, password) VALUES (?, ?);";
    //         PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    //         preparedStatement.setString(1, account.getUsername());
    //         preparedStatement.setString(2, account.getPassword());
    //         preparedStatement.executeUpdate();

    //         ResultSet resultSet = preparedStatement.getGeneratedKeys();
    //         if(resultSet.next()){
    //             return new Account(account.getAccount_id(),account.getUsername(),account.getPassword());
    //         }

            

    //     } catch(SQLException e){
    //         System.out.println(e.getMessage());
    //     }
    //     return null;
    // }

    public Message postMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();

        try{
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());
            preparedStatement.executeUpdate();
            
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if(resultSet.next()){
                int message_id = resultSet.getInt(1);
                return new Message(message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
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

    public void deleteMessageByID(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        
        try{
            String sql = "DELETE FROM message WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, message_id);
            preparedStatement.executeUpdate();

        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
    }

    public void updateMessageByID(int message_id, Message message){
        Connection connection = ConnectionUtil.getConnection();

        try{
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
            preparedStatement.setString(1, message.getMessage_text());
            preparedStatement.setInt(2, message_id);
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

    public Account getAccountByID(int account_id){
        Connection connection = ConnectionUtil.getConnection();

        try{
            String sql = "SELECT * FROM account WHERE account_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, account_id);
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
