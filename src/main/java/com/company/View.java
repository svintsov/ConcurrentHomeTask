package com.company;

import com.company.entity.Document;
import java.util.concurrent.BlockingQueue;

public class View {

  public void printQueue(BlockingQueue<Document> queue){
    queue.forEach(System.out::println);
  }

  public void printDocument(Document doc){
    System.out.println(doc.toString());
  }

}
