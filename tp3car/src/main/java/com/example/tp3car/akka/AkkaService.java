package com.example.tp3car.akka;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AkkaService {

    private final ActorSystem actorSystem;

    @Autowired
    public AkkaService(ActorSystem actorSystem) {
        this.actorSystem = actorSystem;
    }

    public void initializeArchitecture() {
        ActorRef mapper1 = actorSystem.actorOf(MapperActor.props(), "mapper1");
        ActorRef mapper2 = actorSystem.actorOf(MapperActor.props(), "mapper2");
        ActorRef mapper3 = actorSystem.actorOf(MapperActor.props(), "mapper3");

        ActorRef reducer1 = actorSystem.actorOf(ReducerActor.props(), "reducer1");
        ActorRef reducer2 = actorSystem.actorOf(ReducerActor.props(), "reducer2");

    }
}
