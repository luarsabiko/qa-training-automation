package com.epam.training.services;

import java.util.List;
import java.util.stream.Collectors;

public class SearchResultValidator {

  public List<String> findItemsNotContainingSubstring(List<String> items, String substring) {
    String normalizedSubstring = substring.toLowerCase();
    return items.stream()
        .filter(item -> !item.toLowerCase().contains(normalizedSubstring))
        .collect(Collectors.toList());
  }
}
