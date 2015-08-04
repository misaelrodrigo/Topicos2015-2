package br.edu.grupointegrado.SpaceInvaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FillViewport;



/**
 * Created by misael on 03/08/15.
 */
public class TelaJogo extends TelaBase {

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Stage palco;
    private BitmapFont fonte;
    private Label lbPontuacao;

    /**
     * Construtor padrão de tela de Jogo
      * @param game Referência para a classe principal
     */
    public TelaJogo(MainGame game){
        super(game);
    }

    /**
     * Chamado quando a tela é exibida
     */
    @Override
    public void show() {
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();

        //cria o balco no tamanho da camera e referencia a nosso camera ao balco.
        palco = new Stage(new FillViewport(camera.viewportWidth, camera.viewportHeight, camera));

        initFonte();
        initInformacoes();

    }

    private void initInformacoes() {
        Label.LabelStyle lbEstilo = new Label.LabelStyle();

        //cria estilo para fonte.
        lbEstilo.font = fonte;
        lbEstilo.fontColor = Color.WHITE;

        lbPontuacao = new Label(" 0 pontos", lbEstilo);
        lbPontuacao.setPosition(10,camera.viewportHeight-20);

        palco.addActor(lbPontuacao);

    }

    /**
     * Inicia configurações de fontes
     */
    private void initFonte() {
        fonte = new BitmapFont();

    }

    /**
     * Chamado a todo quadro de atualização do jogo(FPS)
     * @param delta tempo entre um quadro e outro (em segundos)
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.15f,.15f,.24f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        palco.act(delta);
        palco.draw();


    }

    /**
     * Chamado sempre que há uma alteração no tamanho da tela.
     * @param width novo valor de largura da tela
     * @param height novo valor de altura da tela
     */
    @Override
    public void resize(int width, int height) {
        //atualiza a camera ao ser redimencionado o tamanho da tela
        camera.setToOrtho(false, width, height);
        camera.update();


    }

    /**
     * Chamado sempre que o jogo for minimizado(2º plano)
     */
    @Override
    public void pause() {

    }

    /**
     * Chamado sempre que o jogo voltar para o primeiro plano
     */
    @Override
    public void resume() {

    }

    /**
     * Chamado quando a tela for destruida
     */
    @Override
    public void dispose() {
        batch.dispose();
        palco.dispose();
        fonte.dispose();

    }
}
