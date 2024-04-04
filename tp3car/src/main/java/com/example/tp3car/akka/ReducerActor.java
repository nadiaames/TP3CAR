package com.example.tp3car.akka;
import akka.actor.UntypedActor;
import java.util.HashMap;
import java.util.Map;

public class ReducerActor extends UntypedActor {
    private final Map<String, Integer> wordCounts = new HashMap<>();

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
        int count = wordCounts.getOrDefault(word, 0);
        wordCounts.put(word, count + 1);
    }

    public Map<String, Integer> getWordCounts() {
        return new HashMap<>(wordCounts);
    }
}
