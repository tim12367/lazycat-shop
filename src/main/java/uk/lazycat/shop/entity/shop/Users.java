package uk.lazycat.shop.entity.shop;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Users {
	private BigInteger userId;
	
	private String username;

	private String password;

	private Boolean enabled;
}