package com.kodility.service.util
import com.kodility.dao.user.TokenDao
import com.kodility.domain.token.Token
import com.kodility.domain.token.TokenType
import com.kodility.domain.user.User
import spock.lang.Specification

class TokenServiceSpec extends Specification {

    private TokenDao tokenDao = Mock()

    private TokenService service

    Token tokenCaptor

    def setup() {
        service = new TokenService(tokenDao: tokenDao)
    }

    def "should create random token and persist for specific purpose"() {
        given:
        User user = new User(id: 1L, email: "user@kodility.com", firstName: "Ahmet", lastName: "Mehmet")
        tokenDao.findOneBy("token", _ as String) >> null
        when:
        String tokenValue = service.createTokenFor(user, TokenType.FORGOT_MY_PASSWORD)
        then:
        tokenValue.size() == 32
        1 * tokenDao.save({ tokenCaptor = it } as Token)
        tokenCaptor.token == tokenValue
        tokenCaptor.tokenType == TokenType.FORGOT_MY_PASSWORD
        tokenCaptor.user == user
    }

    def "should not delete if user has not a token before new token creation"() {
        given:
        User user = new User(id: 1L, email: "user@kodility.com", firstName: "Ahmet", lastName: "Mehmet")
        tokenDao.findOneBy("user", user) >> null
        tokenDao.findOneBy("token", _ as String) >> null
        when:
        String tokenValue = service.createTokenFor(user, TokenType.FORGOT_MY_PASSWORD)
        then:
        0 * tokenDao.delete(_ as Token)
        tokenValue.size() == 32
        1 * tokenDao.save({ tokenCaptor = it } as Token)
        tokenCaptor.token == tokenValue
        tokenCaptor.tokenType == TokenType.FORGOT_MY_PASSWORD
        tokenCaptor.user == user
    }

    def "should delete if user has a token before new token creation"() {
        given:
        User user = new User(id: 1L, email: "user@kodility.com", firstName: "Ahmet", lastName: "Mehmet")
        Token token = new Token(id:2L, user: user, token: "123456")
        tokenDao.findOneBy("user", user) >> token
        tokenDao.findOneBy("token", _ as String) >> null
        when:
        String tokenValue = service.createTokenFor(user, TokenType.FORGOT_MY_PASSWORD)
        then: "delete before new token creation"
        1 * tokenDao.delete(token)
        then: "create new token"
        tokenValue.size() == 32
        1 * tokenDao.save({ tokenCaptor = it } as Token)
        tokenCaptor.token == tokenValue
        tokenCaptor.tokenType == TokenType.FORGOT_MY_PASSWORD
        tokenCaptor.user == user
    }

}
