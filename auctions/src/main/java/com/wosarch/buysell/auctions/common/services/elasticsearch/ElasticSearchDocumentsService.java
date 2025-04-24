package com.wosarch.buysell.auctions.common.services.elasticsearch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ElasticSearchDocumentsService {

    Logger logger = LoggerFactory.getLogger(ElasticSearchDocumentsService.class);

    public List<Class<?>> getElasticSearchDocumentsClasses(String basePackage) {
        List<Class<?>> classes = new ArrayList<>();
        final ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(Document.class));
        final Set<BeanDefinition> documentDefinition = scanner.findCandidateComponents(basePackage);
        final Map<String, String> documentsInfo = new HashMap<>();
        documentDefinition.forEach(definition -> {
            try {
                final Class<?> documentClass = Class.forName(definition.getBeanClassName());
                classes.add(documentClass);
            } catch (ClassNotFoundException e) {
                logger.error("Error during searching for class {}", e.getMessage(), e);
            }
        });

        return classes;
    }

    public String extractIndexName(Class<?> clazz) {
        final Document annotation = clazz.getAnnotation(Document.class);
        if (Objects.isNull(annotation)) {
            return null;
        }

        return annotation.indexName();
    }

    public String extractMappingPath(Class<?> clazz) {
        final Mapping annotation = clazz.getAnnotation(Mapping.class);
        if (Objects.isNull(annotation)) {
            return null;
        }

        return annotation.mappingPath();
    }

    public String extractSettingsPath(Class<?> clazz) {
        final Setting annotation = clazz.getAnnotation(Setting.class);
        if (Objects.isNull(annotation)) {
            return null;
        }

        return annotation.settingPath();
    }
}
