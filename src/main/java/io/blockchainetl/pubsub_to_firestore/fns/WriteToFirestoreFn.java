package io.blockchainetl.pubsub_to_firestore.fns;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.WriteResult;
import io.blockchainetl.pubsub_to_firestore.utils.JsonUtils;
import org.codehaus.jackson.JsonNode;

import java.util.HashMap;
import java.util.Map;

public class WriteToFirestoreFn extends ErrorHandlingDoFn<String, String> {

    // Hack: make it static because only serializable can be in fn if not static!
    private static Firestore db = FirestoreOptions.getDefaultInstance().getService();
    
    private String collection;

    public WriteToFirestoreFn(String collection) {
        this.collection = collection;
    }

    @Override
    protected void doProcessElement(ProcessContext c) throws Exception {
        String element = c.element();

        DocumentReference docRef = db.collection(collection).document();

        Map<String, Object> data = new HashMap<>();

        JsonNode jsonNode = JsonUtils.parseJson(element);
        data.put("hash", jsonNode.get("hash") != null ? jsonNode.get("hash") : "null");
     
        //asynchronously write data
        ApiFuture<WriteResult> result = docRef.set(data);

        // result.get() blocks on response
        System.out.println("Update time : " + result.get().getUpdateTime());
        
        c.output(element);
    }
}
