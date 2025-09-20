package hansanhha.chat.repository;


import hansanhha.chat.entity.ChatMessage;
import org.springframework.data.repository.CrudRepository;


public interface ChatMessageRepository extends CrudRepository<ChatMessage, Long>, ChatMessageCustomRepository {

}
