package br.edu.grupointegrado;

import com.badlogic.gdx.Screen;

/**
 * Created by misael on 18/08/15.
 */
public abstract class TelaBase implements Screen {
    private MainJogo game;

    public TelaBase(MainJogo game) {
        this.game = game;
    }

    @Override
    public void hide() {
        dispose();
    }
}
