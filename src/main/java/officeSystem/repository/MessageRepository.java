package officeSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import officeSystem.model.Message;

@Repository
@Transactional
public interface MessageRepository extends JpaRepository<Message, Integer> {
	//全連絡事項取得
	@Query(value = "SELECT * FROM message", nativeQuery = true)
	public List<Message> allMessage();
}
