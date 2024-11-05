package ru.awg.worldcounter.util;


public final class MessageUtil {

  public static final short MAX_LIMIT_SIZE = 10;

  public static final String DIRECTORY_NOT_FOUND = "Директория не найдена или не содержит текстовых файлов.";

  public static final String SET_DIRECTORY = "Введите путь к директории: ";

  public static final String SET_MINIMUM_WORD_LENGTH = "Введите минимальную длину слова: ";

  public static final String TOP_FREQUENCY_WORDS = "10 самых часто используемых слов:";

  public static final String FILE_FORMAT = ".txt";

  private MessageUtil() {
    throw new UnsupportedOperationException("Утилитный класс не может быть инстанцирован.");
  }
}
