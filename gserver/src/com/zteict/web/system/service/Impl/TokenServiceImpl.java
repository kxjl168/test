package com.zteict.web.system.service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zteict.web.system.dao.TokenDao;
import com.zteict.web.system.model.TokenBean;
import com.zteict.web.system.service.TokenService;



@Service
public class TokenServiceImpl implements TokenService {
	@Autowired
	private TokenDao tokenDao;

	@Override
	public int insertTokenInfo(TokenBean bean) {
		return tokenDao.insertTokenInfo(bean);
	}

	@Override
	public int deleteTokenInfo(String token) {
		return tokenDao.deleteTokenInfo(token);
	}

	@Override
	public TokenBean hasTokenbyUserId(String userid) {
		return tokenDao.hasTokenbyUserId(userid);
	}

	@Override
	public TokenBean hasTokenbyTokenId(String tokenId) {
		return tokenDao.hasTokenbyTokenId(tokenId);
	}

	@Override
	public int updateTokenInfo(TokenBean bean) {
		return tokenDao.updateTokenInfo(bean);
	}

}
