package io.getint.recruitment_task;

import org.junit.Test;

import static org.junit.Assert.*;

public class EnvLoaderTest {

    @Test
    public void envLoader() {
        EnvLoader.loadEnv(".env");
        assertEquals(EnvLoader.get("testString"), "testValue");
    }
}
