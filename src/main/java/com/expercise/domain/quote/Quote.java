package com.expercise.domain.quote;

import com.expercise.domain.BaseEntity;
import com.expercise.enums.Lingo;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@SequenceGenerator(name = "ID_GENERATOR", sequenceName = "SEQ_QUOTE")
public class Quote extends BaseEntity {

    @ElementCollection
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "LINGO")
    @Column(name = "TEXT", nullable = false, length = 1024)
    private Map<Lingo, String> quoteInMultiLingo = new HashMap<>();

    private String author;

    public Map<Lingo, String> getQuoteInMultiLingo() {
        return quoteInMultiLingo;
    }

    public void setQuoteInMultiLingo(Map<Lingo, String> quoteInMultiLingo) {
        this.quoteInMultiLingo = quoteInMultiLingo;
    }

    public String getQuoteFor(String lingoShortName) {
        Lingo lingo = Lingo.getLingo(lingoShortName).get();
        return quoteInMultiLingo.get(lingo);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}
