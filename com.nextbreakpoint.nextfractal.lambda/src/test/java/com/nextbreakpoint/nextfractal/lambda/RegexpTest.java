package com.nextbreakpoint.nextfractal.lambda;

import org.junit.Test;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RegexpTest {
    @Test
    public void shouldMatch() {
        UUID uuid = UUID.randomUUID();

        String text = uuid.toString() + "/0/1/2.png";

        System.out.println(text);

        Matcher matcher = Pattern
                .compile("([0-9a-f\\-]+)/([0-9]+)/([0-9]+)/([0-9]+)[.]png")
                .matcher(text);

        System.out.println(matcher.groupCount());

        assertThat(matcher.matches(), is(true));

        System.out.println(matcher.group(1));
        assertThat(matcher.group(1), is(uuid.toString()));

        System.out.println(matcher.group(2));
        assertThat(matcher.group(2), is("0"));

        System.out.println(matcher.group(3));
        assertThat(matcher.group(3), is("1"));

        System.out.println(matcher.group(4));
        assertThat(matcher.group(4), is("2"));
    }
}
