package com.evildoer.train.agent;

import lombok.SneakyThrows;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;
import org.junit.Test;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class ByteBuddyDemoTest {
    @Test
    @SneakyThrows
    public void test() {
        Class<?> dynamicType = new ByteBuddy()
                .subclass(Object.class)
                .method(named("toString"))
                .intercept(FixedValue.value("Hello World"))
                .make()
                .load(ByteBuddyDemo.class.getClassLoader())
                .getLoaded();

        Object instance = dynamicType.newInstance();
        String toString = instance.toString();
        System.out.println(toString);
        System.out.println(instance.getClass().getCanonicalName());
    }
}