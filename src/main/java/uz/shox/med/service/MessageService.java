package uz.shox.med.service;

public interface MessageService {

    String get(String key);

    String get(String key, Object... args);
}