package com.expercise.interpreter;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class OutputMessageAggregatorTest {

    @Test
    public void shouldAppendMessageToBuffer() {
        OutputMessageAggregator.append("message");

        assertThat(OutputMessageAggregator.getOutputMessage(), equalTo("message"));
    }

    @Test
    public void shouldDeleteMessageFromBufferAfterGet() {
        OutputMessageAggregator.append("message");
        OutputMessageAggregator.getOutputMessage();

        assertThat(OutputMessageAggregator.getOutputMessage(), equalTo(""));
    }

    @Test
    public void shouldNotAppendTooLongMessageToNewBuffer() {
        OutputMessageAggregator.append(
                "this is too long message... this is too long message..." +
                        "this is too long message... this is too long message..." +
                        "this is too long message... this is too long message..." +
                        "this is too long message... this is too long message..." +
                        "this is too long message... this is too long message..."
        );

        assertThat(OutputMessageAggregator.getOutputMessage(), equalTo(""));
    }

    @Test
    public void shouldNotAppendTooLongMessageToExistedBuffer() {
        OutputMessageAggregator.append("short message");
        OutputMessageAggregator.append(
                "this is too long message... this is too long message..." +
                        "this is too long message... this is too long message..." +
                        "this is too long message... this is too long message..." +
                        "this is too long message... this is too long message..." +
                        "this is too long message... this is too long message..."
        );

        assertThat(OutputMessageAggregator.getOutputMessage(), equalTo("short message"));
        assertThat(OutputMessageAggregator.getOutputMessage(), equalTo(""));
    }

}