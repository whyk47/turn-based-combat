package control.mode.timed;

import java.util.Iterator;
import java.util.NoSuchElementException;

import control.mode.LevelGenerator;
import entity.level.Difficulty;
import entity.level.DifficultySpawner;
import entity.level.Level;

public class TimedLevelGenerator implements LevelGenerator {
    @Override
    public Iterator<Level> iterator() {
        return new Iterator<Level>() {
            private boolean delivered = false;

            @Override
            public boolean hasNext() {
                return !delivered;
            }

            @Override
            public Level next() {
                if (!hasNext()) throw new NoSuchElementException();
                delivered = true;
                return new Level(new DifficultySpawner(Difficulty.HARD));
            }
        };
    }
}
