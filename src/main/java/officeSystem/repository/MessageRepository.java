package officeSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import officeSystem.model.Message;

@Repository
@Transactional
public interface MessageRepository extends JpaRepository<Message, Integer> {
	//全連絡事項取得
	@Query(value = "SELECT * FROM message ORDER BY contact_date DESC", nativeQuery = true)
	public List<Message> allMessage();
	
	//最新の連絡事項取得
	@Query(value = "SELECT * FROM message ORDER BY contact_date DESC LIMIT 3", nativeQuery = true)
	public List<Message> getNewMessage();
	
	//連絡詳細取得
	@Query(value = "SELECT * FROM message WHERE id = :id", nativeQuery = true)
	public List<Message> getMessage(int id);
	
	//連絡投稿
	@Modifying
	@Query(value = "INSERT INTO message (contact_name, message) VALUES (:contact_name, :message)", nativeQuery = true)
	public void postMessage(String contact_name, String message);
	
	//連絡確認
	@Modifying
	@Query(value = "UPDATE message SET confirm = '確認済' WHERE id = :id", nativeQuery = true)
	public void messageConfirm(int id);
}
