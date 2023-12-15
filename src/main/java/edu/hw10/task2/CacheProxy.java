package edu.hw10.task2;

import java.lang.reflect.Proxy;
import java.nio.file.Path;

public final class CacheProxy {

    private CacheProxy() {

    }

    public static <T> T create(T object, Class<T> className, Path persistPath) {
        return (T) Proxy.newProxyInstance(
            className.getClassLoader(),
            className.getInterfaces(),
            new CacheInvocationHandler(
                object,
                persistPath
            )
        );
    }
}
