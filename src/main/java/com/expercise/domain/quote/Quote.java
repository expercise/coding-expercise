package com.expercise.domain.quote;

import com.expercise.domain.AbstractEntity;
import com.expercise.enums.Lingo;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Quote extends AbstractEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ElementCollection
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "Lingo")
    @Column(name = "Text", nullable = false, length = 1024)
    private Map<Lingo, String> quoteInMultiLingo = new HashMap<>();

    private String author;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

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
