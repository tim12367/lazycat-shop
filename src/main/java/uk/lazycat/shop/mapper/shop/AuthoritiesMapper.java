package uk.lazycat.shop.mapper.shop;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import uk.lazycat.shop.entity.shop.Authorities;

@Mapper
public interface AuthoritiesMapper {
	int insert(Authorities row);

	List<Authorities> selectAll();

	List<Authorities> selectByUsername(String username);
}