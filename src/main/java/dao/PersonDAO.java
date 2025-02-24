package dao;

import clases.Address;
import clases.Person;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.Binary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PersonDAO {

    public static MongoDatabase getCollection() {
        String url = "mongodb://localhost:27017";
        MongoClient cliente = MongoClients.create(url);
        return cliente.getDatabase("appregistro");
    }

    public static boolean verifyUserByName(String username){
        //db.users.find({username:true})
        boolean ret = false;
        MongoDatabase database = getCollection();
        MongoCollection<Document> collection = database.getCollection("user");

        Bson name = Projections.include("username");
        Bson projection = Projections.fields(name);

        Iterable<Document> busqueda = collection.find().projection(projection);

        for (Document doc : busqueda) {
            if(doc.getString("username").equals(username)){
                ret = true;
            }
        }
        return ret;
    }

    public static void insertUser(Person persona){
        Document nueva = new Document();
        nueva.append("username", persona.getUsername());
        nueva.append("password", persona.getPassword());
        if(persona.getName()!= null){
            nueva.append("name",persona.getName());
        }
        if(persona.getAge()!= null){
            nueva.append("age",persona.getAge());
        }
        if(persona.getEmails()!= null){
            nueva.append("emails",persona.getEmails());
        }
        if(persona.getAddress()!=null){
            Document address = new Document();
            if(persona.getAddress().getStreet()!= null){
                address.append("street", persona.getAddress().getStreet());
            }
            if(persona.getAddress().getNumber()!= null){
                address.append("number", persona.getAddress().getNumber());
            }
            if(persona.getAddress().getPostalCode()!= null){
                address.append("postalCode", persona.getAddress().getPostalCode());
            }
            nueva.append("address",address);
        }

        MongoDatabase db = getCollection();
        MongoCollection<Document> coleccUsers = db.getCollection("user");
        InsertOneResult res = coleccUsers.insertOne(nueva);

    }

    public static boolean verifyUserByNamePass(String username, byte[] hashPass){
        //db.users.find({username:true,password:true})
        boolean ret = false;
        MongoDatabase database = getCollection();
        MongoCollection<Document> collection = database.getCollection("user");

        Bson campos = Projections.include("username","password");
        Bson projection = Projections.fields(campos);
        Iterable<Document> busqueda = collection.find().projection(projection);

        for (Document doc : busqueda) {
            byte[] contra = ((Binary) doc.get("password")).getData();
            if(doc.getString("username").equals(username) && Arrays.equals(contra, hashPass)){
                ret = true;
            }
        }
        return ret;
    }

    public static List<Person> readAll(){
        MongoDatabase database = getCollection();
        MongoCollection<Document> collection = database.getCollection("user");

        Bson noPass = Projections.exclude("password");
        Bson projection = Projections.fields(noPass);
        Iterable<Document> busqueda = collection.find().projection(projection);
        List<Person> users = new ArrayList<>();
        for (Document b : busqueda) {
            Person p = documentToPerson(b);
            users.add(p);
        }
        return users;
    }

    public static List<Person> readBeetween(Integer min, Integer max) {

        MongoDatabase database = getCollection();
        MongoCollection<Document> collection = database.getCollection("user");

        Bson minFilter = Filters.gte("age", min);
        Bson maxFilter = Filters.lte("age", max);
        Bson filter = Filters.and(minFilter, maxFilter);

        Iterable<Document> result = collection.find(filter);

        List<Person> users = new ArrayList<>();
        for(Document r: result){
            Person p = documentToPerson(r);
            users.add(p);
        }
        return users;
    }

    public static Person documentToPerson(Document doc){
            Person user = new Person();
            user.setUsername(doc.getString("username"));
            byte[] pass = ((Binary)doc.get("password")).getData();
            user.setPassword(pass);

            if (doc.containsKey("name")) {
                user.setName(doc.getString("name"));
            }
            if (doc.containsKey("age")) {
                user.setAge(doc.getInteger("age"));
            }
            if (doc.containsKey("emails")) {
                user.setEmails((List<String>) doc.get("emails"));
            }
            if (doc.containsKey("address")) {
                Document addressDocument = (Document) doc.get("address");
                Address address = new Address();
                if (addressDocument.containsKey("street")) {
                    address.setStreet(addressDocument.getString("street"));
                }
                if (addressDocument.containsKey("number")) {
                    address.setNumber(addressDocument.getInteger("number"));
                }
                if (addressDocument.containsKey("pc")) {
                    address.setPostalCode(addressDocument.getString("pc"));
                }
                user.setAddress(address);
            }
            return user;
    }

}
