package fun.deepsky.docker.user.service;

import fun.deepsky.docker.thrift.user.UserInfo;
import fun.deepsky.docker.thrift.user.UserService;
import fun.deepsky.docker.user.mapper.UserMapper;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by deepsky on 2018/9/17.
 */
@Service
public class UserServiceImpl implements UserService.Iface{

    @Autowired
    private UserMapper userMapper;

    public UserInfo getUserById(int id) throws TException {
        return userMapper.getUserById(id);
    }

    public UserInfo getUserByName(String username) throws TException {
        return userMapper.getUserByName(username);
    }

    public void regiserUser(UserInfo userInfo) throws TException {
    		 userMapper.registerUser(userInfo);
    }

	@Override
	public UserInfo getTeacherById(int id) throws TException {
		return userMapper.getTeacherById(id);
	}
}
