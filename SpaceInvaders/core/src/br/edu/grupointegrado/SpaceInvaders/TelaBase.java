package br.edu.grupointegrado.SpaceInvaders;

import com.badlogic.gdx.Screen;

/**
 * Created by misael on 03/08/15.
 */
public abstract class TelaBase implements Screen {
    protected MainGame game;

    public TelaBase(MainGame game){
        this.game = game;
    }
    @Override
    public void hide() {
        dispose();
    }
}
