package com.example.tp3car.akka;
import akka.actor.UntypedActor;
import java.util.Map;

public class ReducerActor extends UntypedActor {
    private final Map<String, Integer> wordOccurrences;

    public ReducerActor(Map<String, Integer> wordOccurrences) {
        this.wordOccurrences = wordOccurrences;
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof WordsMessage wordMessage) {
            String word = wordMessage.word();
            processWord(word);
        } else {
            unhandled(message);
        }
    }

    private void processWord(String word) {
        int count = wordOccurrences.getOrDefault(word, 0);
        wordOccurrences.put(word, count + 1);
    }
}
