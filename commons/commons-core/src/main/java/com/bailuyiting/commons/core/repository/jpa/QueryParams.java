package com.bailuyiting.commons.core.repository.jpa;

import java.util.HashMap;
import java.util.Map;

/**
 * Query parameter helper to create the querys
 * 
 */
public class QueryParams {

  public static final Map<String, Object> EMPTY = null;

  private final Map<String, Object> parameters = new HashMap<String, Object>();

  private QueryParams(String name, Object value) {
    this.parameters.put(name, value);
  }

  public static QueryParams with(String name, Object value) {
    return new QueryParams(name, value);
  }

  public QueryParams and(String name, Object value) {
    this.parameters.put(name, value);
    return this;
  }

  public Map<String, Object> parameters() {
    return this.parameters;
  }
}
