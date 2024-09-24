package Controller;

import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.intThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.SocialMediaService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    SocialMediaService socialMediaService;

    public SocialMediaController(){
        socialMediaService = new SocialMediaService();
    }


    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::postRegisterHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postNewMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.patch("/messages/{message_id}", this::patchMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getAllUserMessagesHandler);
        
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void postRegisterHandler(Context context) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Account account = om.readValue(context.body(), Account.class);
        Account addedAccount = socialMediaService.registerUser(account);

        if(addedAccount == null){
            context.status(400);
        } else{
            context.json(om.writeValueAsString(addedAccount));
        }

    }

    private void postLoginHandler(Context context) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Account account = om.readValue(context.body(), Account.class);
        Account loggedInAccount = socialMediaService.loginUser(account);

        if(loggedInAccount == null){
            context.status(401);
        } else{
            context.json(om.writeValueAsString(loggedInAccount));
        }
        
    }

    private void postNewMessageHandler(Context context) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Message message = om.readValue(context.body(), Message.class);
        Message addedMessage = socialMediaService.addMessage(message);
        
        if(addedMessage == null){
            context.status(400);
        } else{
            context.json(om.writeValueAsString(addedMessage));
        }
    }

    private void getAllMessagesHandler(Context context){
        context.json(socialMediaService.getAllMessages());
    }
    private void getMessageHandler(Context context){
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        context.json(socialMediaService.getMessageByID(message_id));
        


    }
    private void deleteMessageHandler(Context context){
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        Message deletedMessage = socialMediaService.deleteMessageByID(message_id);
        if(deletedMessage != null){
            context.json(deletedMessage);
        } 
        
    }
    private void patchMessageHandler(Context context) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Message message = om.readValue(context.body(), Message.class);
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        Message patchedMessage = socialMediaService.updateMessageByID(message_id, message);

        if(patchedMessage == null){
            context.status(400);
        } else {
            context.json(om.writeValueAsString(patchedMessage));
        }
        

        

    }
    private void getAllUserMessagesHandler(Context context){
        int account_id = Integer.parseInt(context.pathParam("account_id"));
        context.json(socialMediaService.getAllUserMessages(account_id));

    }




}