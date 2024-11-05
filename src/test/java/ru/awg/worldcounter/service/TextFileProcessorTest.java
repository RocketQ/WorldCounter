package ru.awg.worldcounter.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextFileProcessorTest {
  private File file;
  private Map<String, Integer> wordCountMap;

  @BeforeEach
  public void setUp() {
    file = new File("/Users/dzhavid/IdeaProjects/WorldCounter/WorldCounter/src/test/resources/wordcountertest.txt");
    wordCountMap = new HashMap<>();
  }

  @Test
  public void testWordCount() throws InterruptedException {
    int minLength = 2;
    TextFileProcessor textFileProcessor = new TextFileProcessor(file, wordCountMap, minLength);

    //act
    Thread thread = new Thread(textFileProcessor);
    thread.start();
    thread.join();

    //assert
    assertEquals(10, wordCountMap.get("fff"));
    assertEquals(4, wordCountMap.get("ddd"));
    assertEquals(3, wordCountMap.get("tes"));
    assertEquals(4, wordCountMap.get("pov"));
    assertEquals(5, wordCountMap.get("set"));
    assertEquals(3, wordCountMap.get("map"));
    assertEquals(4, wordCountMap.get("test"));
    assertEquals(2, wordCountMap.get("jut"));
    assertEquals(1, wordCountMap.get("jok"));
    assertEquals(1, wordCountMap.get("pol"));
  }
}
