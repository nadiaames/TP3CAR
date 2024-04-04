package com.example.tp3car.akka;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.typesafe.config.ConfigFactory;

@Service
public class AkkaService {
	

    private ActorSystem host1, host2;
    private ActorRef reducer1, reducer2, mapper1, mapper2, mapper3;
	
    @Autowired
    public AkkaService() {
        initialize();
    }
    
    void initialize() {
        host1 = ActorSystem.create("host1", ConfigFactory.load("application1.conf"));
        host2 = ActorSystem.create("host2", ConfigFactory.load("application2.conf"));

        reducer1 = host1.actorOf(Props.create(ReducerActor.class), "reducer1");
        reducer2 = host1.actorOf(Props.create(ReducerActor.class), "reducer2");

        mapper1 = host2.actorOf(Props.create(MapperActor.class, reducer1, reducer2), "mapper1");
        mapper2 = host2.actorOf(Props.create(MapperActor.class, reducer1, reducer2), "mapper2");
        mapper3 = host2.actorOf(Props.create(MapperActor.class, reducer1, reducer2), "mapper3");

        mapper1.tell(new ReducerMessage(reducer1, reducer2), ActorRef.noSender());
        mapper2.tell(new ReducerMessage(reducer1, reducer2), ActorRef.noSender());
        mapper3.tell(new ReducerMessage(reducer1, reducer2), ActorRef.noSender());
    }

	
    public void processFileContent(String fileContent) {
        String[] lines = fileContent.split("\n");
        distributeLines(lines);
    }

    private void distributeLines(String[] lines) {
        for (String line : lines) {
            mapper1.tell(line, ActorRef.noSender());
            mapper2.tell(line, ActorRef.noSender());
            mapper3.tell(line, ActorRef.noSender());
        }
    }
    

    public Map<String, Integer> getWordOccurrences(String word) {
        Map<String, Integer> dummyOccurrences = new HashMap<>();
        dummyOccurrences.put("hello", 5);
        dummyOccurrences.put("world", 3);
        
        return dummyOccurrences;
    }
    
    
}

