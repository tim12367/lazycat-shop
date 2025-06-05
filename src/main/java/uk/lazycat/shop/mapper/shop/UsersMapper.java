package uk.lazycat.shop.mapper.shop;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import uk.lazycat.shop.entity.shop.Users;
import uk.lazycat.shop.entity.shop.UsersAndRoles;

@Mapper
public interface UsersMapper {
	int insert(Users row);

	Users selectByUserName(String username);

	BigInteger selectMaxUserId();

	List<UsersAndRoles> selectUsersAndRoles(BigInteger userId);
}