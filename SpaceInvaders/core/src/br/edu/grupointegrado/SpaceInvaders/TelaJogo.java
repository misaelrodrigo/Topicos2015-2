package br.edu.grupointegrado.SpaceInvaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
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
    private Image jogador;
    private Texture texturaJogador;
    private Texture texturaJogadorDireita;
    private Texture texturaJogadorEsquerda;
    private boolean indoDireita;
    private boolean indoEsquerda;

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
        initJogador();

    }

    private void initJogador() {

        texturaJogador = new Texture("sprites/player.png");
        texturaJogadorDireita = new Texture("sprites/player-right.png");
        texturaJogadorEsquerda = new Texture("sprites/player-left.png");

        jogador = new Image(texturaJogador);
        float x = camera.viewportWidth / 2 - jogador.getWidth() / 2;
        float y = 0;
        jogador.setPosition(x,y);
        palco.addActor(jogador);
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
        capturaTeclas();
        atualizarJogador(delta);
        palco.act(delta);
        palco.draw();


    }

    /**
     * Atualiza a posicção do jogador.
     * @param delta
     */

    private void atualizarJogador(float delta) {
        float velocidade = 1000; // velocidade de movimento do jogador.
        float x = jogador.getX();
        float y = jogador.getY();

        if(indoDireita && x < camera.viewportWidth - jogador.getWidth())
            x = jogador.getX()+200*delta;

        if(indoEsquerda && x > 0)
            x = jogador.getX()-200*delta;

        if(indoDireita){
           //trocar imagem direita
            jogador.setDrawable(new SpriteDrawable(new Sprite(texturaJogadorDireita)));
        }else if(indoEsquerda){
            //trocar imagen esquerda
            jogador.setDrawable(new SpriteDrawable(new Sprite(texturaJogadorEsquerda)));
        }else{
            //trocar image centro.
            jogador.setDrawable(new SpriteDrawable(new Sprite(texturaJogador)));
        }
        jogador.setPosition(x,y);
    }


    private void capturaTeclas() {
        indoDireita  = (Gdx.input.isKeyPressed(Input.Keys.RIGHT))? true : false;
        indoEsquerda = (Gdx.input.isKeyPressed(Input.Keys.LEFT))? true : false;
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
        texturaJogador.dispose();
        texturaJogadorDireita.dispose();
        texturaJogadorEsquerda.dispose();
    }
}
