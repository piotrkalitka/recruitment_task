package io.getint.recruitment_task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EnvLoader {

    private static final Map<String, String> config = new HashMap<>();

    public static void loadEnv(String fileName) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(EnvLoader.class.getClassLoader().getResourceAsStream(fileName))))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] keyValue = line.split("=", 2);
                if (keyValue.length == 2) {
                    config.put(keyValue[0], keyValue[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return config.get(key);
    }

}
