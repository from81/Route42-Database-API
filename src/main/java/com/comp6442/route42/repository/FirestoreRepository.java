package com.comp6442.route42.repository;

import com.comp6442.route42.model.Model;
import com.comp6442.route42.model.Post;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public abstract class FirestoreRepository<T extends Model> {
  protected static Firestore firestore;
  protected FirebaseAuth auth;
  protected CollectionReference collection;
  protected Class<T> classType;

  public FirestoreRepository(String collectionPath, Class<T> cType) {
    this.classType = cType;

    try {
      GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
      FirebaseOptions options =
          FirebaseOptions.builder()
              .setCredentials(credentials)
              .setDatabaseUrl("https://.firebaseio.com/")
              .build();

      // Initialize the default app
      FirebaseApp defaultApp = FirebaseApp.initializeApp(options);

      System.out.println(defaultApp.getName()); // "[DEFAULT]"

      // Retrieve services by passing the defaultApp variable...
      auth = FirebaseAuth.getInstance();
      firestore = FirestoreClient.getFirestore();
      this.collection = firestore.collection(collectionPath);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  abstract Post getOne(String id) throws ExecutionException, InterruptedException;
}
