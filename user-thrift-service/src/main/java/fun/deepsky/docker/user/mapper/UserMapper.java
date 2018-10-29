package fun.deepsky.docker.user.mapper;

import fun.deepsky.docker.thrift.user.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by deepsky on 2018/9/17.
 */
@Mapper
public interface UserMapper {
	
    @Select("select id,username,password,realname,mobile,email from pe_user where id=#{id}")
    UserInfo getUserById(@Param("id")int id);

    @Select("select id,username,password,realname,mobile,email from pe_user where username=#{username}")
    UserInfo getUserByName(@Param("username")String username);

    @Insert("insert into pe_user(username,password,realname,mobile,email)" +
            " values(#{u.username},#{u.password},#{u.realname},#{u.mobile},#{u.email});")
    void registerUser(@Param("u")UserInfo userInfo);
    
    @Select("select u.id,u.username,u.password,u.realname,u.mobile,u.email,t.intro,t.stars "
    		+ "from pe_user u,pe_teacher t "
    		+ "where u.id = #{id} and u.id = t.user_id")
    UserInfo getTeacherById(@Param("id") int id);
    
}
