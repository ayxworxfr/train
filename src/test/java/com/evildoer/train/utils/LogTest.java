package com.evildoer.train.utils;

import lombok.SneakyThrows;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@RunWith(PowerMockRunner.class)
public class LogTest {
    @Mock
    private Log log;

    @Ignore
    @Test
    @SneakyThrows
    public void test_print_should_successful() {
        assertThat(catchThrowable(() -> log.print("test {}, {}", 1, 2))).isNull();
    }
}