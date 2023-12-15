package edu.hw10.task2;


import edu.hw10.task2.fib_calculator.FibCalculator;
import edu.hw10.task2.fib_calculator.FibCalculatorImpl;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.assertj.core.api.Assertions.assertThat;

public class CacheProxyTest {

    @TempDir
    private Path path;

    @BeforeEach
    @SneakyThrows
    public void initPath() {
        Files.createDirectory(path.resolve("storage"));
    }

//    @Test
//    @DisplayName("#create with @Cache(persist = true) test")
//    public void create_shouldCreateProxyOfObjectWithCachingValuesInDiskStorage() {
//        Path storage = path.resolve("storage");
//        PalindromeChecker checker =
//            CacheProxy.create(new PalindromeCheckerImpl(), PalindromeCheckerImpl.class, storage);
//        checker.isPalindrome("aboba");
//        boolean cachedValue = checker.isPalindrome("aboba");
//        assertThat(storage.resolve("isPalindrome_cache")).hasContent("[\"aboba\"];" + cachedValue);
//    }

    @Test
    @DisplayName("#create with @Cache(persist = false) test")
    public void create_shouldCreateProxyOfObjectWithCachingValuesInMemory() {
        Path storage = path.resolve("storage");
        FibCalculator fibCalculator = CacheProxy.create(new FibCalculatorImpl(), FibCalculatorImpl.class, storage);
        long result = fibCalculator.getFib(10);
        long cachedResult = fibCalculator.getFib(10);
        assertThat(cachedResult).isEqualTo(result);
    }
}
