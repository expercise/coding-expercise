package com.expercise.service.language;

import com.expercise.domain.challenge.Challenge;
import com.expercise.enums.ProgrammingLanguage;

public interface SignatureGenerator {

    String[] LETTERS = new String[]{"a", "b", "c", "d", "e", "f", "g", "h"};

    boolean canGenerateFor(ProgrammingLanguage language);

    String generate(Challenge challenge);

}
