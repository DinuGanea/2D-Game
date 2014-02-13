package net.bestwebart.game.spawner;

import net.bestwebart.game.Game;
import net.bestwebart.game.entity.particle.Particle;
import net.bestwebart.game.entity.particle.Step;

public class Spawner {

    private int x, y;
    private int nr;
    private Type type;

    public enum Type {
	PARTICLE, STEP
    }

    public Spawner(int x, int y, int nr, Type type) {
	this.x = x;
	this.y = y;
	this.nr = nr;
	this.type = type;

	spawn();
    }

    private void spawn() {
	for (int i = 0; i < nr; i++) {
	    if (type == Type.PARTICLE) {
		Game.level.addEntity(new Particle(x, y));
	    } else if (type == Type.STEP) {
		Game.level.addEntity(new Step(x, y));
	    }
	}
    }

}
