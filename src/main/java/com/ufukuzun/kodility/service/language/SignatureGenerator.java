package com.ufukuzun.kodility.service.language;

import com.ufukuzun.kodility.domain.challenge.Challenge;

public interface SignatureGenerator {

    public String generate(Challenge challenge);

}
