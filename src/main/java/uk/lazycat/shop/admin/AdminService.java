package uk.lazycat.shop.admin;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.lazycat.shop.entity.shop.UsersAndRoles;
import uk.lazycat.shop.mapper.shop.UsersMapper;

@Service
public class AdminService {

	@Autowired
	private UsersMapper usersMapper;

	public UsersAndRoles getUser(BigInteger id) {
		List<UsersAndRoles> usersAndRoles = usersMapper.selectUsersAndRoles(id);
		// 若查有返回第一筆(id唯一)，若無則null
		return !usersAndRoles.isEmpty() ? usersAndRoles.getFirst() : null;
	}

	public List<UsersAndRoles> getUsers() {
		return usersMapper.selectUsersAndRoles(null);
	}

}
