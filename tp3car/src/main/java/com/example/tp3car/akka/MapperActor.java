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
        if (message instanceof LinesMessage) {
            LinesMessage linesMessage = (LinesMessage) message;
            String[] words = linesMessage.line().split("\\s+"); 
            for (String word : words) {
                char firstLetter = Character.toLowerCase(word.charAt(0));
                ActorRef reducer = firstLetter >= 'a' && firstLetter <= 'm' ? reducer1 : reducer2;
                reducer.tell(new WordsMessage(word), getSelf());
            }
        }
    }


}
