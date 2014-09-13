package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//Dan-learning: Game provides implementation of ApplicationListener. Plus screen stuff
public class FlowerPrototype extends Game {
	SpriteBatch batch;
	Texture img;

    public BitmapFont font;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
        font = new BitmapFont();
        this.setScreen(new MainMenuScreen(this));
		//img = new Texture("badlogic.jpg");
	}

	@Override
	public void render () { //Essentially also game logic loop
		/*Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 50);
		batch.end();*/
        super.render(); //This renders the screen set in Create (MUCH IMPORTANT)
	}
}
