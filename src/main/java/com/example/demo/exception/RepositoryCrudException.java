package com.example.demo.exception;

public class RepositoryCrudException extends RuntimeException {
  public RepositoryCrudException(String message, Throwable e) {
    super(message, e);
  }
}
