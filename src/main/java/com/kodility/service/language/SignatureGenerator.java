package com.kodility.service.language;

import com.kodility.domain.challenge.Challenge;
import com.kodility.enums.ProgrammingLanguage;

public interface SignatureGenerator {

    String[] LETTERS = new String[]{"a", "b", "c", "d", "e", "f", "g", "h"};

    boolean canGenerateFor(ProgrammingLanguage language);

    String generate(Challenge challenge);

}
