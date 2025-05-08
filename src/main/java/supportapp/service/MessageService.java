package supportapp.service;

import supportapp.model.Message;

import java.util.List;

public interface MessageService {
    List<Message> getBySessionId(Long sessionId);
    Message sendMessage(Message message);
}
