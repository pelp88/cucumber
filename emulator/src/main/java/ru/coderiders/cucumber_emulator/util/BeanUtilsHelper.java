package ru.coderiders.cucumber_emulator.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.FeatureDescriptor;
import java.util.Arrays;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Slf4j
public class BeanUtilsHelper {

    private BeanUtilsHelper() {
    }

    public static String[] getNullPropertyNames(Object source, String... ignoredFields) {
        var wrappedSource = new BeanWrapperImpl(source);
        var foundFields = Stream.of(wrappedSource.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
                .collect(toList());
        foundFields.addAll(Arrays.asList(ignoredFields));
        return foundFields.toArray(new String[0]);
    }
}
