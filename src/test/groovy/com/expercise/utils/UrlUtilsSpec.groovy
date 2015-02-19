package com.expercise.utils

import spock.lang.Specification
import spock.lang.Unroll

class UrlUtilsSpec extends Specification {

    @Unroll
    def "should make '#notBookmarkableText' bookmarkable '#bookmarkableText'"() {
        expect:
        UrlUtils.makeBookmarkable(notBookmarkableText) == bookmarkableText;

        where:
        notBookmarkableText                                 | bookmarkableText
        "Not Bookmarkable"                                  | "not-bookmarkable"
        "     Trim Spaces     "                             | "trim-spaces"
        "Add       only one       hyphen for middle spaces" | "add-only-one-hyphen-for-middle-spaces"
        "Remove & special \" * -- ' chars"                  | "remove-special-chars"
        "Türkçe Not Bookmarkable Bir İsim İıŞşÇçÖöÜüĞğ"     | "turkce-not-bookmarkable-bir-isim-iissccoouugg"
    }

}
