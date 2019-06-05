package android.epita.fr.gitandroidapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;



public class DummyActivity extends AppCompatActivity {

    final String CLASSNAME = getLocalClassName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);

//        // Use them like regular java objects
//        Dog dog = new Dog();
//        dog.setName("Rex");
//        dog.setAge(1);
//
//// Initialize Realm (just once per application)
//        Realm.init(getApplicationContext());
//
//// Get a Realm instance for this thread
//        Realm realm = Realm.getDefaultInstance();
//
//// Query Realm for all dogs younger than 2 years old
//        final RealmResults<Dog> puppies = realm.where(Dog.class).lessThan("age", 2).findAll();
//        Log.d(CLASSNAME,"Size before add : "+puppies.size()); // => 0 because no dogs have been added to the Realm yet
//
//// Persist your data in a transaction
//        realm.beginTransaction();
//        final Dog managedDog = realm.copyToRealm(dog); // Persist unmanaged objects
////        Person person = realm.createObject(Person.class); // Create managed objects directly
////        person.getDogs().add(managedDog);
//        realm.commitTransaction();
//
//// Listeners will be notified when data changes
//        puppies.addChangeListener(new OrderedRealmCollectionChangeListener<RealmResults<Dog>>() {
//            @Override
//            public void onChange(RealmResults<Dog> results, OrderedCollectionChangeSet changeSet) {
//                // Query results are updated in real time with fine grained notifications.
//                changeSet.getInsertions();
//                Log.d(CLASSNAME,changeSet.getInsertions().length+""); // => [0] is added.
//            }
//        });
//
//// Asynchronously update objects on a background thread
//        realm.executeTransactionAsync(new Realm.Transaction() {
//            @Override
//            public void execute(Realm bgRealm) {
//                Dog dog = bgRealm.where(Dog.class).equalTo("age", 1).findFirst();
//                dog.setAge(3);
//            }
//        }, new Realm.Transaction.OnSuccess() {
//            @Override
//            public void onSuccess() {
//                // Original queries and Realm objects are automatically updated.
//                Log.d(CLASSNAME,puppies.size()+""); // => 0 because there are no more puppies younger than 2 years old
//                Log.d(CLASSNAME,managedDog.getAge()+"");   // => 3 the dogs age is updated
//            }
//        });
    }
}

