package co.kr.sppartners.service;

import co.kr.sppartners.entitiy.User;
import co.kr.sppartners.repository.UserDAO;
import co.kr.sppartners.repository.UserRepository;
import co.kr.sppartners.vo.UserVo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
   private final UserRepository userRepository;
   private final UserDAO userDAO;
   private final UserBatisService userBatisService;

   public CustomUserDetailsService(UserRepository userRepository, UserDAO userDAO, UserBatisService userBatisService) {
      this.userRepository = userRepository;
      this.userDAO = userDAO;
      this.userBatisService = userBatisService;
   }
/**
 * Jpa
 */
//   @Override
//   @Transactional
//   public UserDetails loadUserByUsername(final String username) {
//      Optional<User> oneWithAuthoritiesByUsername = userRepository.findOneWithAuthoritiesByUsername(username);
//      System.out.println("oneWithAuthoritiesByUsername = " + oneWithAuthoritiesByUsername.get());
//      return userRepository.findOneWithAuthoritiesByUsername(username)
//         .map(user -> createUser(username, user))
//         .orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));
//   }
//
//private org.springframework.security.core.userdetails.User createUser(String username, User user) {
//   if (!user.isActivated()) {
//      throw new RuntimeException(username + " -> 활성화되어 있지 않습니다.");
//   }
//   List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
//           .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
//           .collect(Collectors.toList());
//   return new org.springframework.security.core.userdetails.User(user.getUsername(),
//           user.getPassword(),
//           grantedAuthorities);
//}

   /**
    * MyBatis
    * @param username
    * @return
    */
   @Override
   @Transactional
   public UserDetails loadUserByUsername(final String username) {
      return userDAO.findOneWithAuthoritiesByUsername(username)
              .map(user -> createUser(username, user))
              .orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));
   }

   private org.springframework.security.core.userdetails.User createUser(String username, UserVo user) {
      if (!user.isActivated()) {
         throw new RuntimeException(username + " -> 활성화되어 있지 않습니다.");
      }
      List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
              .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
              .collect(Collectors.toList());
      return new org.springframework.security.core.userdetails.User(user.getUsername(),
              user.getPassword(),
              grantedAuthorities);
   }
}
