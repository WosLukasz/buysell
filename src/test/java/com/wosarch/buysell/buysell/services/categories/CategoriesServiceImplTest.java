package com.wosarch.buysell.buysell.services.categories;

import com.wosarch.buysell.buysell.model.categories.Category;
import com.wosarch.buysell.buysell.repositories.posgresql.categories.CategoriesRepository;
import com.wosarch.buysell.common.model.exception.BuysellException;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoriesServiceImplTest {

    private static final String CATEGORIES_JSON_FILE_PATH = "/categories.json";

    @InjectMocks
    private CategoriesServiceImpl categoriesService;

    @Mock
    private CategoriesRepository categoriesRepository;

    @Captor
    private ArgumentCaptor<Category> captor;

    @Test
    public void shouldNotParseStructureWhenJsonEmpty() {
        Assertions.assertThrows(BuysellException.class, () -> categoriesService.parseStructure(null));
    }

    @Test
    public void shouldParseStructureAndAddCategories() throws IOException {
        AtomicLong sequence = new AtomicLong();
        String categoriesStructure = IOUtils.toString(this.getClass().getResourceAsStream(CATEGORIES_JSON_FILE_PATH), "UTF-8");
        when(categoriesRepository.save(any(Category.class)))
                .thenAnswer(arg -> {
                    Category savedCategory = arg.getArgument(0);
                    savedCategory.setId(sequence.incrementAndGet());

                    return savedCategory;
                });

        categoriesService.parseStructure(categoriesStructure);

        verify(categoriesRepository, times(4)).save(captor.capture());

        List<Category> allValues = captor.getAllValues();
        assertFalse(CollectionUtils.isEmpty(allValues));
        assertEquals(4, allValues.size());
        assertEquals(1L, allValues.getFirst().getId());
        assertNull(allValues.getFirst().getParentId());
        assertEquals("ROOT", allValues.getFirst().getCode());
        assertEquals(2L, allValues.get(1).getId());
        assertEquals(1L, allValues.get(1).getParentId());
        assertEquals("MOTORING", allValues.get(1).getCode());
        assertEquals(3L, allValues.get(2).getId());
        assertEquals(2L, allValues.get(2).getParentId());
        assertEquals("CARS", allValues.get(2).getCode());
        assertEquals(4L, allValues.get(3).getId());
        assertEquals(1L, allValues.get(3).getParentId());
        assertEquals("JOBS", allValues.get(3).getCode());
    }
}
