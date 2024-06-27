package io.getint.recruitment_task;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Application {

    public static void main(String[] args) {
        List<String> issueKeys = Arrays.stream(Arrays
                        .stream(args)
                        .filter(s -> s.startsWith("issues"))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Issue keys not found in arguments"))
                        .split("=")[1].split(",")).collect(Collectors.toList());

        JiraSynchronizer synchronizer = new JiraSynchronizer();
        synchronizer.moveTasksToOtherProject(issueKeys);
    }

}
