package ru.awg.worldcounter.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс TextFileProcessor реализует интерфейс Runnable и отвечает за чтение и обработку отдельных текстовых файлов.
 */
public class TextFileProcessor implements Runnable {
  private final File file;
  private final Map<String, Integer> wordCountMap;
  private final int minLength;

  public TextFileProcessor(File file, Map<String, Integer> wordCountMap, int minLength) {
    this.file = file;
    this.wordCountMap = wordCountMap;
    this.minLength = minLength;
  }

  @Override
  public void run() {
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      String line;
      while ((line = br.readLine()) != null) {
        processLine(line);
      }
    } catch (IOException e) {
      throw new RuntimeException("Утилитный класс не может быть инстанцирован: ", e);
    }
  }

  private void processLine(String line) {
    String regex = "\\b\\w{" + (minLength + 1) + ",}\\b";
    Matcher matcher = Pattern.compile(regex).matcher(line);
    while (matcher.find()) {
      String word = matcher.group();
      wordCountMap.merge(word.toLowerCase(), 1, Integer::sum);
    }
  }
}