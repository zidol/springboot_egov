package co.kr.sppartners.repository;


import co.kr.sppartners.entitiy.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
   @EntityGraph(attributePaths = "authorities")//Lazy 강 아닌 Eager 조회로  authorities정보도 같이 가져옴
   Optional<User> findOneWithAuthoritiesByUsername(String username);
}
