package co.kr.sppartners.repository;

import co.kr.sppartners.dto.UserDto;
import co.kr.sppartners.vo.UserVo;
import org.egovframe.rte.psl.dataaccess.EgovAbstractDAO;
import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDAO extends EgovAbstractMapper {

    private static final String NAME_SPACE = "SampleDAO.";

    public List<UserVo> selectUserDto() throws Exception{
        System.out.println("NAME_SPACE = " + NAME_SPACE);
        return selectList(NAME_SPACE + "selectUser");
    }

    public Optional<UserVo>  findUserByUsername(String username) throws Exception {
        UserVo userVo = selectOne(NAME_SPACE + "findUserByUsername", username);
        return Optional.ofNullable(userVo);
    }

    public int save(UserVo user) throws Exception {
        return insert(NAME_SPACE + "insertUser", user);
    }

    public int insertUserAuthority(UserVo user) throws Exception {
        return insert(NAME_SPACE + "insertUserAuthority", user);
    }

    public List<UserVo> findOneWithAuthoritiesByUsername(String username) {
        return selectList(NAME_SPACE + "findOneWithAuthoritiesByUsername", username);
    }
}
