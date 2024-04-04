package com.example.tp3car.akka;
import akka.actor.ActorRef;
import akka.actor.UntypedActor;

public class MapperActor extends UntypedActor {
    private final ActorRef reducer1;
    private final ActorRef reducer2;

    public MapperActor(ActorRef reducer1, ActorRef reducer2) {
        this.reducer1 = reducer1;
        this.reducer2 = reducer2;
    }



    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof ReducerMessage) {
            this.reducer1.tell(new WordsMessage(""), getSelf());
            this.reducer2.tell(new WordsMessage(""), getSelf());
        } else if (message instanceof LinesMessage lineMessage ) {
            String[] mots = lineMessage.line().split(" "); 
            for (String mot : mots) {
                partition(mot).tell(new WordsMessage(mot), getSelf());
            }
        }
    }

    private ActorRef partition(String word) {
        char firstLetter = Character.toLowerCase(word.charAt(0));
        if (firstLetter >= 'a' && firstLetter <= 'l') {
            return reducer1;
        } else {
            return reducer2;
        }
    }
}
