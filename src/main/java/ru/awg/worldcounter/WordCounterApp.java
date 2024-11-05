package ru.awg.worldcounter;

import static ru.awg.worldcounter.util.MessageUtil.DIRECTORY_NOT_FOUND;
import static ru.awg.worldcounter.util.MessageUtil.FILE_FORMAT;
import static ru.awg.worldcounter.util.MessageUtil.MAX_LIMIT_SIZE;
import static ru.awg.worldcounter.util.MessageUtil.SET_DIRECTORY;
import static ru.awg.worldcounter.util.MessageUtil.SET_MINIMUM_WORD_LENGTH;
import static ru.awg.worldcounter.util.MessageUtil.TOP_FREQUENCY_WORDS;

import java.io.File;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import ru.awg.worldcounter.service.TextFileProcessor;

/**
  Класс WordCounterApp является основной точкой входа в приложение. Он отвечает за:
  Запрос у пользователя пути к директории и минимальной длины слова.
  Поиск всех текстовых файлов в указанной директории.
  Создание и запуск потоков для параллельной обработки файлов.
  Сбор и вывод 10 самых частых слов.
 */
public class WordCounterApp {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    System.out.print(SET_DIRECTORY);
    String directoryPath = scanner.nextLine();
    System.out.print(SET_MINIMUM_WORD_LENGTH);
    int minLength = scanner.nextInt();

    File directory = new File(directoryPath);
    File[] files = directory.listFiles((d, name) -> name.toLowerCase().endsWith(FILE_FORMAT));

    if (files != null) {
      List<Thread> threads = new ArrayList<>();
      Map<String, Integer> wordCountMap = new ConcurrentHashMap<>();

      for (File file : files) {
        Thread thread = new Thread(new TextFileProcessor(file, wordCountMap, minLength));
        threads.add(thread);
        thread.start();
      }

      for (Thread thread : threads) {
        try {
          thread.join();
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }

      List<Map.Entry<String, Integer>> topWords = getTopWords(wordCountMap);
      System.out.println(TOP_FREQUENCY_WORDS);
      for (Map.Entry<String, Integer> entry : topWords) {
        System.out.println(entry.getKey() + " : " + entry.getValue());
      }
    } else {
      System.out.println(DIRECTORY_NOT_FOUND);
    }
    scanner.close();
  }

  private static List<Map.Entry<String, Integer>> getTopWords(Map<String, Integer> wordCountMap) {
    return wordCountMap.entrySet().stream()
        .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
        .limit(MAX_LIMIT_SIZE)
        .toList();
  }
}