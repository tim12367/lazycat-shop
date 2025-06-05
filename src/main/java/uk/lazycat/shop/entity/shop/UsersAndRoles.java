package uk.lazycat.shop.entity.shop;

import java.math.BigInteger;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsersAndRoles {

	private BigInteger userId;

	private String username;

	private boolean enabled;

	private String authoritys;

}
