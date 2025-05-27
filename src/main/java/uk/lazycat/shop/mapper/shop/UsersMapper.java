package uk.lazycat.shop.mapper.shop;

import java.math.BigInteger;

import org.apache.ibatis.annotations.Mapper;

import uk.lazycat.shop.entity.shop.Users;

@Mapper
public interface UsersMapper {
	int insert(Users row);

	Users selectByUserName(String username);

	BigInteger selectMaxUserId();
}