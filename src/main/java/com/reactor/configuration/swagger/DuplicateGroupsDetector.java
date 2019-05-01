package com.reactor.configuration.swagger;

import springfox.documentation.spi.service.DocumentationPlugin;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public class DuplicateGroupsDetector {
    private DuplicateGroupsDetector() {
        throw new UnsupportedOperationException();
    }

    public static void ensureNoDuplicateGroups(List<DocumentationPlugin> allPlugins) throws IllegalStateException {
        Map<String, List<DocumentationPlugin>> plugins = allPlugins.stream()
                .collect(groupingBy(
                        input -> ofNullable(input.getGroupName()).orElse("default"),
                        LinkedHashMap::new,
                        toList()));


        Iterable<String> duplicateGroups = plugins.entrySet().stream()
                .filter(input -> input.getValue().size() > 1)
                .map(Map.Entry::getKey)
                .collect(toList());
        if (StreamSupport.stream(duplicateGroups.spliterator(), false).count() > 0) {
            throw new IllegalStateException(String.format("Multiple Dockets with the same group name are not supported. "
                    + "The following duplicate groups were discovered. %s", String.join(",", duplicateGroups)));
        }
    }


}
