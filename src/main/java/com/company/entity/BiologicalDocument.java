package com.company.entity;

public class BiologicalDocument extends Document {

  public BiologicalDocument(int id) {
    super(id);
  }

  @Override
  public String toString() {
    return "BiologicalDocument with id = " + this.getId();
  }
}
