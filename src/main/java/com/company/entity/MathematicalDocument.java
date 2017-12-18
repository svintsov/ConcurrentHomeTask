package com.company.entity;

public class MathematicalDocument extends Document {

  public MathematicalDocument(int id) {
    super(id);
  }

  @Override
  public String toString() {
    return "MathematicalDocument with id = " + this.getId();
  }
}
