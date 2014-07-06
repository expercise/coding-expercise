package com.ufukuzun.kodility.service.language;

import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.enums.ProgrammingLanguage;

public interface SignatureGenerator {

    String[] LETTERS = new String[]{"a", "b", "c", "d", "e", "f", "g", "h"};

    boolean canGenerateFor(ProgrammingLanguage language);

    String generate(Challenge challenge);

}
