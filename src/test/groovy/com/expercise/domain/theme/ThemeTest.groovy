package com.expercise.domain.theme
import com.expercise.enums.Lingo
import com.expercise.testutils.builder.ThemeBuilder
import spock.lang.Specification

class ThemeTest extends Specification {

    def "should get value by lingo if available"() {
        given:
        Theme theme = new ThemeBuilder().addDescription(Lingo.English, "description in English").buildWithRandomId()

        expect:
        "description in English" == theme.getDescription()
    }

    def "should get empty value by lingo if description is not available in that Lingo"() {
        given:
        Theme theme = new ThemeBuilder().addDescription(Lingo.Turkish, "Turkce aciklama").buildWithRandomId()

        expect:
        "" == theme.getDescription()
    }

}
