package net.bestwebart.game.entity.mob;

import net.bestwebart.game.gfx.Screen;
import net.bestwebart.game.input.KeyboardHandler;
import net.bestwebart.game.input.MouseHandler;
import net.bestwebart.game.level.tiles.AnimatedTile;
import net.bestwebart.game.level.tiles.Tile;

public class Player extends Mob {
    
    private KeyboardHandler key;
    private MouseHandler mouse;
   
 
    public Player(KeyboardHandler key, MouseHandler mouse) {
	super(400, 400, (AnimatedTile) Tile.PLAYER_UP);
	this.key = key;
	this.mouse = mouse;
	speed = 1;
	tileNr = 0;
	shoots = 0;
	flip = false;
    }

    public void update() {
	
	if (shoots > 0) shoots--;
	updateShooting();
	
	if (moving) animTile.update();
	tileNr = animTile.getCurrFrame();
	double nx = 0;
	double ny = 0;
	
	if (key.up) {
	    ny -= speed;
	    dir = 0;
	    animTile = (AnimatedTile) Tile.PLAYER_UP;
	} else if (key.down) {
	    ny += speed;
	    dir = 2;
	    animTile = (AnimatedTile) Tile.PLAYER_DOWN;
	}
	
	if (key.left) {
	    nx -= speed;
	    dir = 3;
	    animTile = (AnimatedTile) Tile.PLAYER_SIDE;
	    flip = false;
	} else if (key.right) {
	    nx += speed;
	    dir = 1;
	    animTile = (AnimatedTile) Tile.PLAYER_SIDE;
	    flip = true;
	}
	
	
	if (nx != 0 || ny != 0) {
	    moving = true;
	    move(nx, ny);
	} else {
	    moving = false;
	    animTile.setCurrFrameTo(0);
	}
	
    }
    
    private void updateShooting() {
	if (mouse.getClickedButton() == 1 && shoots <= 0) {
	    shoot(mouse);
	    shoots = projectile.getRate();
	}
    }
    
   
      
    public void render(Screen screen) {
	screen.renderAnimatedTiles((int) x, (int) y, animTile, flip, tileNr);
    } 
    
    

}
