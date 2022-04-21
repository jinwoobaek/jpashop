package jpabook.jpashop;


package com.codility;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.Assertions.*;


public class VersionTest {
    // ... write your unit tests here ...

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void test_errorVersionMustNotBeNull() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage(errorVersionMustNotBeNull);

        new Version(null);
    }

    @Test
    public void test_errorVersionMustMatchPattern() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage(errorVersionMustMatchPattern);

        new Version("3.8.0-snapshot");
    }

    @Test
    public void test_versionName() {
        new Version("3.0.0");
        new Version("3.8.0");
        new Version("3.8.1");
        new Version("3.0.0-SNAPSHOT");
        new Version("3.8.0-SNAPSHOT");
        new Version("3.8.1-SNAPSHOT");
    }

    @Test
    public void test_isSnapshot() {
        assertThat(new Version("3.8.0-SNAPSHOT").isSnapshot()).isTrue();
        assertThat(new Version("3.8.0").isSnapshot()).isFalse();
    }

    @Test
    public void test_cannotCompareWithNull() {
        Version version = new Version("3.8.0-SNAPSHOT");

        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage(errorOtherMustNotBeNull);

        version.compareTo(null);
    }

    @Test
    public void test_compare() {
        assertThat(new Version("3.8.0").compareTo(new Version("3.8.0"))).isEqualTo(0);
        assertThat(new Version("3.8.0").compareTo(new Version("4.8.0"))).isEqualTo(-1);
        assertThat(new Version("3.8.0").compareTo(new Version("3.9.0"))).isEqualTo(-1);
        assertThat(new Version("3.8.0").compareTo(new Version("3.8.1"))).isEqualTo(-1);
        assertThat(new Version("3.8.0-SNAPSHOT").compareTo(new Version("3.8.0"))).isEqualTo(-1);
    }

    @Test
    public void test_versionNameWith3parts() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage(errorVersionMustMatchPattern);

        new Version("3.8.0.1");
    }

    @Test
    public void exampleTest() {
        Version version = new Version("3.8.0");
        // ...
    }

    @Test
    public void exampleTest2() {
        Version version = new Version("3.8.0-SNAPSHOT");
        // ...
    }

    // expected error messages:

    static final String errorVersionMustNotBeNull = "'version' must not be null!";
    static final String errorOtherMustNotBeNull = "'other' must not be null!";
    static final String errorVersionMustMatchPattern = "'version' must match: 'major.minor.patch(-SNAPSHOT)'!";
}
