package co.kr.sppartners.service;

import co.kr.sppartners.dto.UserDto;
import co.kr.sppartners.entitiy.Authority;
import co.kr.sppartners.entitiy.User;
import co.kr.sppartners.repository.UserDAO;
import co.kr.sppartners.repository.UserRepository;
import co.kr.sppartners.util.SecurityUtil;
import co.kr.sppartners.vo.AuthorityVo;
import co.kr.sppartners.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Mybatis로 변경하기 위한 service 로직
 */
@Service
@RequiredArgsConstructor
public class UserBatisService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDAO userDAO;

    @Transactional
    public UserVo signup(UserVo userVo) throws Exception {
        if (userDAO.findUserByUsername(userVo.getUsername()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }

        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@");
//        //빌더 패턴의 장점
//        AuthorityVo authority = AuthorityVo.builder()
//                .authorityName("ROLE_USER")
//                .build();

        UserVo user = UserVo.builder()
                .username(userVo.getUsername())
                .password(passwordEncoder.encode(userVo.getPassword()))
                .nickname(userVo.getNickname())
                .authorityName("ROLE_USER")
                .activated(true)
                .build();

//        return userRepository.save(user);
        int insertResult = userDAO.save(user);
        if(insertResult > 0) {
            userDAO.insertUserAuthority(user);
        }
        return user;
    }

    /**
     * 현재 로그인 유저 정보 조회
     * @return
     */
    @Transactional(readOnly = true)
    public Optional<UserVo> getMyUserWithAuthorities() {
        Optional<String> currentUsername = SecurityUtil.getCurrentUsername();
        return userDAO.findOneWithAuthoritiesByUsername(currentUsername.get());
    }

    /**
     * param에 넘어온 username으로 조회
     * @param username
     * @return
     */
    @Transactional(readOnly = true)
    public Optional<UserVo> getUserWithAuthorities(String username) {
        return userDAO.findOneWithAuthoritiesByUsername(username);
    }

//    public Optional<UserVo> getOptionalUserVo(String currentUsername) {
//        List<UserVo> resultList = userDAO.findOneWithAuthoritiesByUsername(currentUsername);
//        UserVo resultUser = null;
//        if(resultList.size() == 0) {
//            throw new RuntimeException("조회 데이터가 없습니다.");
//        } else {
//            resultUser = UserVo.builder()
//                    .username(resultList.get(0).getUsername())
//                    .nickname(resultList.get(0).getNickname())
//                    .activated(resultList.get(0).isActivated())
//                    .build();
//        }
//        List<AuthorityVo> authorityVoList = new ArrayList<>();
//        for (UserVo userVo : resultList) {
//            AuthorityVo authorityVo = new AuthorityVo();
//            authorityVo.setAuthorityName(userVo.getAuthorityName());
//            authorityVoList.add(authorityVo);
//        }
//        resultUser.setAuthorities(authorityVoList);
//        return Optional.ofNullable(resultUser);
//    }

    public Optional<UserVo> findOneWithAuthoritiesByUsername(String currentUsername) {
        Optional<UserVo> result = userDAO.findOneWithAuthoritiesByUsername(currentUsername);
//        UserVo resultUser = null;
//        if(resultList.size() == 0) {
//            throw new RuntimeException("조회 데이터가 없습니다.");
//        } else {
//            resultUser = UserVo.builder()
//                    .username(resultList.get(0).getUsername())
//                    .nickname(resultList.get(0).getNickname())
//                    .activated(resultList.get(0).isActivated())
//                    .password(resultList.get(0).getPassword())
//                    .userId(resultList.get(0).getUserId())
//                    .build();
//        }
//        List<AuthorityVo> authorityVoList = new ArrayList<>();
//        for (UserVo userVo : resultList) {
//            AuthorityVo authorityVo = new AuthorityVo();
//            authorityVo.setAuthorityName(userVo.getAuthorityName());
//            authorityVoList.add(authorityVo);
//        }
//        resultUser.setAuthorities(authorityVoList);
//        return Optional.ofNullable(resultUser);
        return result;
    }
}
