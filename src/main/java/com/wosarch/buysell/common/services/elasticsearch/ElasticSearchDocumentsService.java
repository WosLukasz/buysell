package com.wosarch.buysell.common.services.elasticsearch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Service
public class ElasticSearchDocumentsService {

    Logger logger = LoggerFactory.getLogger(ElasticSearchDocumentsService.class);

    public Map<String, String> getElasticSearchDocumentsInfo(String basePackage) {
        final ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(Document.class));
        final Set<BeanDefinition> documentDefinition = scanner.findCandidateComponents(basePackage);
        final Map<String, String> documentsInfo = new HashMap<>();
        documentDefinition.forEach(definition -> {
            try {
                final Class<?> documentClass = Class.forName(definition.getBeanClassName());
                String indexName = extractIndexName(documentClass);
                String mappingPath = extractMappingPath(documentClass);
                if (Objects.nonNull(indexName) && Objects.nonNull(mappingPath)) {
                    documentsInfo.put(indexName, mappingPath);
                }

            } catch (ClassNotFoundException e) {
                logger.error("Error during searching for class {}", e.getMessage(), e);
            }
        });

        return documentsInfo;
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
}
