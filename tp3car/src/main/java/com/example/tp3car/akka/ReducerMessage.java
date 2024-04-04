package com.example.tp3car.akka;

import akka.actor.ActorRef;

public record ReducerMessage (ActorRef reducer1,ActorRef reducer2) {
}
