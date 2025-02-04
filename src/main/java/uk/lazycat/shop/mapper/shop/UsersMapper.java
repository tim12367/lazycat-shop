package uk.lazycat.shop.mapper.shop;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import uk.lazycat.shop.entity.shop.Users;

@Mapper
public interface UsersMapper {
	int deleteByPrimaryKey(String username);

	int insert(Users row);

	Users selectByPrimaryKey(String username);

	List<Users> selectAll();

	int updateByPrimaryKey(Users row);
}