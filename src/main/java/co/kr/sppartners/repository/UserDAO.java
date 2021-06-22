package co.kr.sppartners.repository;

import co.kr.sppartners.dto.UserDto;
import org.egovframe.rte.psl.dataaccess.EgovAbstractDAO;
import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAO extends EgovAbstractMapper {

    private static final String NAME_SPACE = "SampleDAO.";

    public List<UserDto> selectUserDto() throws Exception{
        System.out.println("NAME_SPACE = " + NAME_SPACE);
        return selectList(NAME_SPACE + "selectUser");
    }
}
