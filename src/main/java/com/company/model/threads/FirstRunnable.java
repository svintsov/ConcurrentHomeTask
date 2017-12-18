package com.company.model.threads;

import static com.company.Controller.remainDocs;

import com.company.View;
import com.company.entity.Document;
import java.util.concurrent.BlockingQueue;

public class FirstRunnable implements Runnable {

  private BlockingQueue<Document> queue;
  private View view;

  public FirstRunnable(BlockingQueue<Document> queue,View view){
    this.queue=queue;
    this.view=view;
  }

  @Override
  public void run() {
    while(remainDocs!=0){
      try {
        read();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  synchronized private void read() throws InterruptedException{
    Thread.sleep(500);
    view.printDocument(queue.take());
    remainDocs--;
    System.out.println("Document has been taken. Remaining docs:" + remainDocs);
  }
}
