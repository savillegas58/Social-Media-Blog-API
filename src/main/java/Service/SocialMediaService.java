package Service;

import java.util.List;

import DAO.SocialMediaDAO;
import Model.Account;
import Model.Message;

public class SocialMediaService {
    SocialMediaDAO socialmediaDAO;


    public SocialMediaService(){
        socialmediaDAO = new SocialMediaDAO();
    }

    public SocialMediaService(SocialMediaDAO socialMediaDAO){
        this.socialmediaDAO = socialMediaDAO;
    }

    public Account registerUser(Account account){

        return socialmediaDAO.insertAccount(account);

    }

    public Account loginUser(Account account){

        
        return socialmediaDAO.loginAccount(account);
    }

    public List<Message> getAllMessages(){

        return socialmediaDAO.getAllMessages();
    }

    public Message getMessageByID(int message_id){
        
        return socialmediaDAO.getMessageByID(message_id);
    }
    public Message deleteMessageByID(int message_id){

        if(socialmediaDAO.getMessageByID(message_id) == null){
            return null;
        }

        return socialmediaDAO.deleteMessageByID(message_id);
    }
    
    public Message updateMessageByID(int message_id, Message message){
        
        if(socialmediaDAO.getMessageByID(message_id) == null){
            return null;
        }

        socialmediaDAO.updateMessageByID(message_id, message);
        return socialmediaDAO.getMessageByID(message_id);
    }

    public List<Message> getAllUserMessages(int account_id){

        return socialmediaDAO.getAllUserMessages(account_id);
    }
}
