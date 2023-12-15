package edu.hw10.task2;

import java.lang.reflect.InvocationHandler;
import java.nio.file.Path;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.hw10.task2.annotation.Cache;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class CacheInvocationHandler implements InvocationHandler {

    private static final String DISC_CACHE_DELIMITER = ";";
    private static final String CACHE_HIT_LOG = "Cache hit: Found result in cache for args: {}";
    private static final String CACHE_MISS_LOG = "Cache miss: No result found in cache for args: {}";
    private static final String CACHE_SUFFIX = "_cache";
    private final Map<Method, Map<List<Object>, Object>> inMemoryCachedValues = new HashMap<>();
    private final Gson gson = new GsonBuilder().create();
    private final Object proxiedObject;
    private final Path diskCachePath;

    public CacheInvocationHandler(Object proxiedObject, Path diskCachePath) {
        this.proxiedObject = proxiedObject;
        this.diskCachePath = diskCachePath;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        return processAnnotation(method, args);
    }

    @SneakyThrows
    private Object processAnnotation(Method method, Object[] args) {
        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof Cache cacheAnnotation) {
                if (cacheAnnotation.persist()) {
                    return processDiskCache(method, args);
                } else {
                    return processInMemoryCache(method, args);
                }
            }
        }
        return method.invoke(proxiedObject, args);
    }

    private Object processInMemoryCache(Method method, Object[] args) {
        List<Object> argsList = Arrays.stream(args).toList();
        if (inMemoryCachedValues.containsKey(method)) {
            if (inMemoryCachedValues.get(method).containsKey(argsList)) {
                log.info(CACHE_HIT_LOG, args);
                return inMemoryCachedValues.get(method).get(argsList);
            }
            log.info(CACHE_MISS_LOG, args);
            return calculateResultAndAddToCache(method, argsList, args);
        }
        log.info(CACHE_MISS_LOG, args);
        inMemoryCachedValues.put(method, new HashMap<>());
        return calculateResultAndAddToCache(method, argsList, args);
    }

    @SneakyThrows
    private Object processDiskCache(Method method, Object[] args) {
        String fileName = method.getName() + CACHE_SUFFIX;
        Path methodFile = diskCachePath.resolve(fileName);
        if (!Files.exists(methodFile)) {
            createMethodFile(fileName);
        }
        Object result = findCacheForArgs(methodFile, args, method);
        if (result == null) {
            result = method.invoke(proxiedObject, args);
            writeResultToDiskCache(args, result, methodFile);
        }
        return result;
    }

    @SneakyThrows
    private void writeResultToDiskCache(Object[] args, Object result, Path methodFile) {
        String argsJson = gson.toJson(args);
        String resultJson = gson.toJson(result);
        Files.writeString(methodFile, argsJson + DISC_CACHE_DELIMITER + resultJson + "\n", StandardOpenOption.APPEND);
    }

    @SneakyThrows
    private Object findCacheForArgs(Path methodFilePath, Object[] args, Method method) {
        try (Stream<String> lines = Files.lines(methodFilePath)) {
            return lines
                .map(line -> line.split(DISC_CACHE_DELIMITER))
                .filter(splitLine -> areArgumentsFound(splitLine[0], args))
                .findFirst()
                .map(splitLine -> {
                    Object result = gson.fromJson(splitLine[1], method.getReturnType());
                    log.info(CACHE_HIT_LOG, args);
                    return result;
                })
                .orElseGet(() -> {
                    log.info(CACHE_MISS_LOG, args);
                    return null;
                });
        }
    }

    private boolean areArgumentsFound(String serializedArgs, Object[] originArgs) {
        return serializedArgs.equals(gson.toJson(originArgs));
    }

    @SneakyThrows
    private void createMethodFile(String methodName) {
        Path methodFile = diskCachePath.resolve(methodName);
        Files.createFile(methodFile);
    }

    @SneakyThrows
    private Object calculateResultAndAddToCache(Method method, List<Object> objArgs, Object[] args) {
        Object result = method.invoke(proxiedObject, args);
        inMemoryCachedValues.get(method).put(objArgs, result);
        return result;
    }
}
