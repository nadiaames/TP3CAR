package com.example.tp3car.akka;

import akka.actor.AbstractActor;
import akka.actor.Props;


public class MapperActor extends AbstractActor {
	
    public static Props props() {
        return Props.create(MapperActor.class);
    }


}