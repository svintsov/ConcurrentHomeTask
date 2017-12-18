package com.company.model;

import static com.company.Controller.remainDocs;

import com.company.entity.Document;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class PopulatorThread implements Runnable {

  private BlockingQueue<Document> queue;
  private List<Document> bioDocuments;
  private List<Document> mathDocuments;
  private boolean firstInit = false;

  public PopulatorThread(BlockingQueue<Document> queue, List<Document> bioDocuments,
      List<Document> mathDocuments) {
    this.queue = queue;
    this.bioDocuments = bioDocuments;
    this.mathDocuments = mathDocuments;
  }

  public void run() {

    try {
      while (remainDocs != 0) {
        if (!firstInit) {
          firstPopulationOfQueue();
        } else {
          populationProcess();
        }
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }

  synchronized private void populationProcess() throws InterruptedException {
    if (queue.remainingCapacity() == 2 && (!bioDocuments.isEmpty() || !mathDocuments.isEmpty())) {
      while (queue.remainingCapacity() != 0) {
        populateQueue();
      }
    }
  }

  private void firstPopulationOfQueue() throws InterruptedException {
    System.out.println("Population first queue started. Remain docs: " + remainDocs);
    while (queue.remainingCapacity() != 0) {
      populateQueue();
    }
    System.out.println("Population first queue ended. Remain docs:" + remainDocs);
    firstInit = true;
  }

  private void populateQueue() throws InterruptedException {
    Random random = new Random();

    if (random.nextBoolean()) {
      populateIfBioEmpty();

    } else {
      populateIfMathEmpty();
    }
    System.out.println("Population queue. Remain docs:" + remainDocs);

  }

  private void populateIfBioEmpty() throws InterruptedException {
    if (bioDocuments.isEmpty()) {
      queue.put(getDocumentFromMathList());
    } else {
      queue.put(getDocumentFromBioList());
    }
  }

  private void populateIfMathEmpty() throws InterruptedException {
    if (mathDocuments.isEmpty()) {
      queue.put(getDocumentFromBioList());
    } else {
      queue.put(getDocumentFromMathList());
    }
  }

  private Document getDocumentFromBioList() {
    Random random = new Random();
    System.out.println("Bio size: " + bioDocuments.size());
    return bioDocuments.remove(random.nextInt(bioDocuments.size()));
  }

  private Document getDocumentFromMathList() {
    Random random = new Random();
    System.out.println("Math size: " + mathDocuments.size());
    return mathDocuments.remove(random.nextInt(mathDocuments.size()));
  }
}
