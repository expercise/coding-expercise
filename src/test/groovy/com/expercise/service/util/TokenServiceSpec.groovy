package com.expercise.service.util

import com.expercise.repository.user.TokenRepository
import com.expercise.domain.token.Token
import com.expercise.domain.token.TokenType
import com.expercise.domain.user.User
import spock.lang.Specification

class TokenServiceSpec extends Specification {

    TokenService service

    TokenRepository tokenRepository = Mock()

    Token tokenCaptor

    def setup() {
        service = new TokenService(tokenRepository: tokenRepository)
    }

    def "should create random token and persist for specific purpose"() {
        given:
        User user = new User(id: 1L, email: "user@expercise.com", firstName: "Ahmet", lastName: "Mehmet")
        tokenRepository.findOneBy("token", _ as String) >> null

        when:
        String tokenValue = service.createTokenFor(user, TokenType.FORGOT_MY_PASSWORD)

        then:
        tokenValue.size() == 32
        1 * tokenRepository.save({ tokenCaptor = it } as Token)
        tokenCaptor.token == tokenValue
        tokenCaptor.tokenType == TokenType.FORGOT_MY_PASSWORD
        tokenCaptor.user == user
    }

    def "should not delete if user has not a token before new token creation"() {
        given:
        User user = new User(id: 1L, email: "user@expercise.com", firstName: "Ahmet", lastName: "Mehmet")
        tokenRepository.findOneBy("user", user) >> null
        tokenRepository.findOneBy("token", _ as String) >> null

        when:
        String tokenValue = service.createTokenFor(user, TokenType.FORGOT_MY_PASSWORD)

        then:
        0 * tokenRepository.delete(_ as Token)
        tokenValue.size() == 32
        1 * tokenRepository.save({ tokenCaptor = it } as Token)
        tokenCaptor.token == tokenValue
        tokenCaptor.tokenType == TokenType.FORGOT_MY_PASSWORD
        tokenCaptor.user == user
    }

    def "should delete if user has a token before new token creation"() {
        given:
        User user = new User(id: 1L, email: "user@expercise.com", firstName: "Ahmet", lastName: "Mehmet")
        Token token = new Token(id: 2L, user: user, token: "123456")
        tokenRepository.findOneBy("user", user) >> token
        tokenRepository.findOneBy("token", _ as String) >> null

        when:
        String tokenValue = service.createTokenFor(user, TokenType.FORGOT_MY_PASSWORD)

        then: "delete before new token creation"
        1 * tokenRepository.delete(token)

        then: "create new token"
        tokenValue.size() == 32
        1 * tokenRepository.save({ tokenCaptor = it } as Token)
        tokenCaptor.token == tokenValue
        tokenCaptor.tokenType == TokenType.FORGOT_MY_PASSWORD
        tokenCaptor.user == user
    }

    def "should get token by token and token type"() {
        given:
        def tokenFromDB = new Token(id: 1L, token: "token_123", tokenType: TokenType.FORGOT_MY_PASSWORD)
        1 * tokenRepository.findToken("token_123", TokenType.FORGOT_MY_PASSWORD) >> tokenFromDB

        when:
        def foundToken = service.findBy("token_123", TokenType.FORGOT_MY_PASSWORD);

        then:
        foundToken == tokenFromDB
    }

    def "should delete token if available"() {
        given:
        def tokenFromDB = new Token(id: 1L, token: "token_123", tokenType: TokenType.FORGOT_MY_PASSWORD)
        1 * tokenRepository.findToken("token_123", TokenType.FORGOT_MY_PASSWORD) >> tokenFromDB

        when:
        service.deleteToken("token_123", TokenType.FORGOT_MY_PASSWORD);

        then:
        1 * tokenRepository.delete(tokenFromDB)
    }

    def "should not delete token if not available"() {
        given:
        1 * tokenRepository.findToken("token_123", TokenType.FORGOT_MY_PASSWORD) >> null

        when:
        service.deleteToken("token_123", TokenType.FORGOT_MY_PASSWORD);

        then:
        0 * tokenRepository.delete(_)
    }

}
