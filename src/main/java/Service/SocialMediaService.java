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
        Account registeredAccount = socialmediaDAO.getAccountByUserName(account.getUsername());

        if(registeredAccount == null && account.getUsername().length() != 0 && account.getPassword().length() >= 4){
            return socialmediaDAO.insertAccount(account);
        }

        return null;

    }

    public Account loginUser(Account account){
        Account registeredAccount = socialmediaDAO.getAccountByUserName(account.getUsername());

        
        if(registeredAccount != null && account.getUsername().equals(registeredAccount.getUsername()) && account.getPassword().equals(registeredAccount.getPassword())){
            return socialmediaDAO.loginAccount(registeredAccount);
        }
        
        return null;
    }

    public Message addMessage(Message message){

        if(message.getMessage_text().isEmpty() || message.getMessage_text().length() > 255 || socialmediaDAO.getAccountByID(message.getPosted_by()) == null){
            return null;
        }
        
        return socialmediaDAO.postMessage(message);
    }

    public List<Message> getAllMessages(){

        return socialmediaDAO.getAllMessages();
    }

    public Message getMessageByID(int message_id){
        
        return socialmediaDAO.getMessageByID(message_id);
    }
    public Message deleteMessageByID(int message_id){

        if(socialmediaDAO.getMessageByID(message_id) != null){
            Message message = socialmediaDAO.getMessageByID(message_id);
            socialmediaDAO.deleteMessageByID(message_id);
            return message;
        }
        
        return null;
    }
    
    public Message updateMessageByID(int message_id, Message message){
        
        if(socialmediaDAO.getMessageByID(message_id) == null || message.getMessage_text().isEmpty() || message.getMessage_text().length() > 255){
            return null;
        }

        socialmediaDAO.updateMessageByID(message_id, message);
        return socialmediaDAO.getMessageByID(message_id);
    }

    public List<Message> getAllUserMessages(int account_id){

        return socialmediaDAO.getAllUserMessages(account_id);
    }
}
