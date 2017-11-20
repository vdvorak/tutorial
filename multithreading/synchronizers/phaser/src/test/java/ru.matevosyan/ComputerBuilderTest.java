package ru.matevosyan;

import org.junit.Test;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Phaser;

/**
 * Created by Admin on 16.11.2017.
 */
public class ComputerBuilderTest {
    Phaser phaser = new Phaser(3);
    CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

    @Test
    public void whenThan() {
        list.add("Builder 1");
        list.add("Builder 2");
        list.add("Builder 3");
        ComputerBuilder builder = new ComputerBuilder(phaser, list);
        builder.build();
    }
}