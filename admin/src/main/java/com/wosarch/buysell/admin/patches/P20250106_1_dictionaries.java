package com.wosarch.buysell.admin.patches;

import com.wosarch.buysell.admin.model.dictionaries.Dictionary;
import com.wosarch.buysell.admin.model.dictionaries.DictionaryService;
import com.wosarch.buysell.admin.model.patches.Patch;
import com.wosarch.buysell.admin.services.dictionaries.DictionaryBuilderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class P20250106_1_dictionaries implements Patch {

    Logger logger = LoggerFactory.getLogger(P20250106_1_dictionaries.class);

    @Autowired
    private DictionaryService dictionaryService;

    @Override
    public String getPatchId() {
        return "P20250106_1_dictionaries";
    }

    @Override
    public void run() {
        logger.info("[{}] Starts...", getPatchId());

        Dictionary dictionary = new DictionaryBuilderImpl()
                .create("CATEGORIES")
                .setValueType(Dictionary.DictionaryElementValueTypes.STRING)
                .addElement("MOTORING", "buysell.dictionaries.MOTORING.name") // just for test. To remove later
                .addElement("CARS", "buysell.dictionaries.CARS.name")
                .addElement("BIKES", "buysell.dictionaries.BIKES.name")
                .addElement("PROPERTIES", "buysell.dictionaries.PROPERTIES.name")
                .addElement("FLATS", "buysell.dictionaries.FLATS.name")
                .addElement("HOUSES", "buysell.dictionaries.HOUSES.name")
                .addElement("LANDS", "buysell.dictionaries.LANDS.name")
                .addElement("OTHER_PROPERTIES", "buysell.dictionaries.OTHER_PROPERTIES.name")
                .addElement("JOBS", "buysell.dictionaries.JOBS.name")
                .addElement("HOME", "buysell.dictionaries.HOME.name")
                .addElement("ELECTRONICS", "buysell.dictionaries.ELECTRONICS.name")
                .addElement("CLOTHES", "buysell.dictionaries.CLOTHES.name")
                .addElement("ANIMALS", "buysell.dictionaries.ANIMALS.name")
                .addElement("KIDS", "buysell.dictionaries.KIDS.name")
                .addElement("SPORT_HOBBY", "buysell.dictionaries.SPORT_HOBBY.name")
                .build();

        dictionaryService.saveDictionary(dictionary);

        logger.info("[{}] Finished...", getPatchId());
    }
}