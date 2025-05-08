package supportapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import supportapp.model.Message;
import supportapp.repository.MessageRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Override
    public List<Message> getBySessionId(Long sessionId) {
        return messageRepository.findBySession_SessionIdOrderBySentAtAsc(sessionId);
    }

    @Override
    public Message sendMessage(Message message){
        return messageRepository.save(message);
    }
}
