package com.kodility.service.user

import com.kodility.utils.Clock
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken
import spock.lang.Specification

class UserRememberMeTokenRepositorySpec extends Specification {

    UserRememberMeTokenRepository repository = new UserRememberMeTokenRepository()

    def "should create and cache a token for user"() {
        given:
        PersistentRememberMeToken token = new PersistentRememberMeToken("mehmet", "series0", "tokenValue2", Clock.getTime())
        repository.seriesTokens.put("series0", token)
        PersistentRememberMeToken newToken = new PersistentRememberMeToken("ahmet", "series1", "tokenValue1", Clock.getTime())

        when:
        repository.createNewToken(newToken)

        then:
        repository.seriesTokens.size() == 2
        repository.seriesTokens.get("series0") == token
        repository.seriesTokens.get("series1") == newToken
    }

    def "should throw exception if user has already a token"() {
        given:
        PersistentRememberMeToken token1 = new PersistentRememberMeToken("ahmet", "series1", "tokenValue1", Clock.getTime())
        PersistentRememberMeToken token2 = new PersistentRememberMeToken("mehmet", "series2", "tokenValue2", Clock.getTime())
        repository.seriesTokens.put("series1", token1)
        repository.seriesTokens.put("series2", token2)

        when:
        repository.createNewToken(token1)

        then:
        thrown(DataIntegrityViolationException)
    }

    def "should remove user's record from cache"() {
        given:
        PersistentRememberMeToken token1 = new PersistentRememberMeToken("ahmet", "series1", "tokenValue1", Clock.getTime())
        PersistentRememberMeToken token2 = new PersistentRememberMeToken("mehmet", "series2", "tokenValue2", Clock.getTime())
        repository.seriesTokens.put("series1", token1)
        repository.seriesTokens.put("series2", token2)

        when:
        repository.removeUserTokens("mehmet")

        then:
        repository.seriesTokens.size() == 1
        repository.seriesTokens.get("series1") == token1
        repository.seriesTokens.get("series1") != token2
    }

    def "should get current token by series id"() {
        given:
        PersistentRememberMeToken token1 = new PersistentRememberMeToken("ahmet", "series1", "tokenValue1", Clock.getTime())
        PersistentRememberMeToken token2 = new PersistentRememberMeToken("mehmet", "series2", "tokenValue2", Clock.getTime())
        repository.seriesTokens.put("series1", token1)
        repository.seriesTokens.put("series2", token2)

        when:
        PersistentRememberMeToken fetchedToken = repository.getTokenForSeries("series2")

        then:
        fetchedToken != token1
        fetchedToken == token2
    }
    
    def "should update token with new values"() {
        given:
        PersistentRememberMeToken token1 = new PersistentRememberMeToken("ahmet", "series1", "tokenValue1", Clock.getTime())
        PersistentRememberMeToken token2 = new PersistentRememberMeToken("mehmet", "series2", "tokenValue2", Clock.getTime())
        repository.seriesTokens.put("series1", token1)
        repository.seriesTokens.put("series2", token2)

        when:
        repository.updateToken("series2", "newTokenValue", Clock.getTime())

        then:
        repository.seriesTokens.size() == 2
        repository.seriesTokens.get("series1") == token1
        repository.seriesTokens.get("series2").getUsername() == "mehmet"
        repository.seriesTokens.get("series2").getSeries() == "series2"
        repository.seriesTokens.get("series2").getTokenValue() == "newTokenValue"
    }

}
