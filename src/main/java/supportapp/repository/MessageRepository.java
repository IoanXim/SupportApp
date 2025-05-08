package supportapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import supportapp.model.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySession_SessionIdOrderBySentAtAsc(Long sessionId);
}
