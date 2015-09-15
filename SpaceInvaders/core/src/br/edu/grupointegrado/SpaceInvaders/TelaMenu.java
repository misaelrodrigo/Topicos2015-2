package br.edu.grupointegrado.SpaceInvaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FillViewport;

/**
 * Created by Misael-Ticiane on 14/09/2015.
 */
public class TelaMenu extends TelaBase {

    private OrthographicCamera camera;
    private Stage palco;
    private ImageTextButton btnIniciar;
    private Label lbTitulo;
    private Label lbPontuacao;

    private BitmapFont fontTitulo;
    private BitmapFont fontBtns;

    private Texture textBtn;
    private Texture textBtnPress;

    public TelaMenu(MainGame game) {
        super(game);
    }

    @Override
    public void show() {
        // cria a camera do tamanho da tela
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //cria um palco no tamanho da camera
        palco = new Stage(new FillViewport(camera.viewportWidth, camera.viewportHeight,camera));

        //define o palco como processador de entrada, para utiliza o onclick do botao e outroas acoes
        Gdx.input.setInputProcessor(palco);

        initFontes();
        initLabels();
    }

    private void initLabels() {
        //configura o fonte com estilo
        Label.LabelStyle estilo = new Label.LabelStyle();
        estilo.font = fontTitulo;

        //estancia o label
        lbTitulo = new Label("Space Invaders", estilo);

        //add no palco
        palco.addActor(lbTitulo);
    }

    private void initFontes() {
        //Gerador de Fontes
        FreeTypeFontGenerator gerador = new FreeTypeFontGenerator(Gdx.files.internal("fonts/roboto.ttf"));

        //Paramentos de fonte
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.color = new Color(.25f,.25f,.85f,1);
        param.size = 48;
        param.shadowOffsetX = 2;
        param.shadowOffsetY = 2;
        param.shadowColor = Color.BLACK;

        fontTitulo = gerador.generateFont(param);

        gerador.dispose();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        atualizarLabels();


        palco.act(delta);
        palco.draw();

    }

    private void atualizarLabels() {
        float xTitulo = camera.viewportWidth /2 - lbTitulo.getPrefWidth()/2;
        float yTitulo = camera.viewportHeight - 110;
        lbTitulo.setPosition(xTitulo,yTitulo);
    }

    @Override
    public void resize(int width, int height) {
        // atualiza o tamanho da camera ao mudar de tamanho ou girar a tela
        camera.setToOrtho(false,width,height);



    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        palco.dispose();
        //fontBtns.dispose();
        fontTitulo.dispose();
        //textBtn.dispose();
        //textBtnPress.dispose();
    }
}
