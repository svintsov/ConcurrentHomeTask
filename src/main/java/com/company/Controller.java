package com.company;

import com.company.entity.BiologicalDocument;
import com.company.entity.Document;
import com.company.entity.MathematicalDocument;
import com.company.model.PopulatorThread;
import com.company.model.threads.FirstRunnable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Controller {

  private View view;
  private List<Document> bioDocuments;
  private List<Document> mathDocuments;
  private BlockingQueue<Document> queue;

  private final static int BIO_DOC_COUNTER=10;
  private final static int MATH_DOC_COUNTER=15;
  private final int QUEUE_CAPACITY=5;
  public static volatile int remainDocs = BIO_DOC_COUNTER+MATH_DOC_COUNTER;

  public Controller(View view){
    this.view = view;

    bioDocuments = new ArrayList<>(BIO_DOC_COUNTER);
    mathDocuments = new ArrayList<>(MATH_DOC_COUNTER);

    queue = new ArrayBlockingQueue<Document>(QUEUE_CAPACITY);
  }

  public void process() throws InterruptedException {
    populateBioListWithDocs();
    populateMathListWithDocs();

    PopulatorThread runnable = new PopulatorThread(queue,bioDocuments,mathDocuments);
    FirstRunnable runnable1 = new FirstRunnable(queue,view);


    Thread producer = new Thread(runnable);
    Thread consumer1 = new Thread(runnable1);
    producer.start();
    consumer1.start();

    producer.join();
    consumer1.join();

  }

  private void populateBioListWithDocs(){
    for(int i=0;i<BIO_DOC_COUNTER;i++){
      bioDocuments.add(new BiologicalDocument(i));
    }
  }

  private void populateMathListWithDocs(){
    for(int i=0;i<MATH_DOC_COUNTER;i++){
      mathDocuments.add(new MathematicalDocument(i));
    }
  }

}
