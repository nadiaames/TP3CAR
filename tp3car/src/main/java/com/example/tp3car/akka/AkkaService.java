package com.example.tp3car.akka;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import com.typesafe.config.ConfigFactory;
import org.springframework.stereotype.Service;

@Service
public class AkkaService {
	
    private ActorSystem host1, host2;
    private ActorRef reducer1, reducer2, mapper1, mapper2, mapper3;
    
    private final Map<String, Integer> wordOccurrences = new ConcurrentHashMap<>();
	
    void initialize() {
        host1 = ActorSystem.create("host1", ConfigFactory.load("application1.conf"));
        host2 = ActorSystem.create("host2", ConfigFactory.load("application2.conf"));

        reducer1 = host1.actorOf(Props.create(ReducerActor.class, wordOccurrences), "reducer1");
        reducer2 = host1.actorOf(Props.create(ReducerActor.class, wordOccurrences), "reducer2");
        
        mapper1 = host2.actorOf(Props.create(MapperActor.class, reducer1, reducer2), "mapper1");
        mapper2 = host2.actorOf(Props.create(MapperActor.class, reducer1, reducer2), "mapper2");
        mapper3 = host2.actorOf(Props.create(MapperActor.class, reducer1, reducer2), "mapper3");

        mapper1.tell(new ReducerMessage(reducer1, reducer2), ActorRef.noSender());
        mapper2.tell(new ReducerMessage(reducer1, reducer2), ActorRef.noSender());
        mapper3.tell(new ReducerMessage(reducer1, reducer2), ActorRef.noSender());
    }

    public void processFileContent(String fileContent) {
        String[] lines = fileContent.split("\n");
        for (String line : lines) {
            String[] words = line.split("\\s+");
            for (String word : words) {
                wordOccurrences.merge(word, 1, Integer::sum); // the increment count for each word
            }
        }
    }

    public Map<String, Integer> getWordOccurrences(String word) {
        return new HashMap<>(wordOccurrences);
    }

    public void updateWordOccurrences(String word) {
        wordOccurrences.merge(word, 1, Integer::sum);
    }
    
}
   

