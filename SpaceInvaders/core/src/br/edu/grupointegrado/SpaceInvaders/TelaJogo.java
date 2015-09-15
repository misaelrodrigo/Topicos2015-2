package br.edu.grupointegrado.SpaceInvaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FillViewport;



/**
 * Created by misael on 03/08/15.
 */
public class TelaJogo extends TelaBase {

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Stage palco;
    private Stage palcoInformacoes;
    private BitmapFont fonte;
    private Label lbPontuacao;
    private Label lbGamerOver;
    private Image jogador;
    private Texture texturaJogador;
    private Texture texturaJogadorDireita;
    private Texture texturaJogadorEsquerda;
    private Texture texturaTiro;
    private Texture texturaMeteoro1;
    private Texture texturaMeteoro2;
    private Array<Image> tiros = new Array<Image>();
    private Array<Image> meteoros1 = new Array<Image>();
    private Array<Image> meteoros2 = new Array<Image>();
    private boolean indoDireita;
    private boolean indoEsquerda;
    private boolean atirando;
    private Array<Texture> texturasExplosao = new Array<Texture>();
    private Array<Explosao> explosoes = new Array<Explosao>();

    private static int PONTUACAOMETEORO1 = 5;
    private static int PONTUACAOMETEORO2 = 15;
    private static float VELOCIDADEMETEORO1 = 100;
    private static float VELOCIDADEMETEORO2 = 150;

    private Sound somTiro;
    private Sound somExploracao;
    private Sound somGameOver;
    private Music musicaFundo;

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
        palcoInformacoes = new Stage(new FillViewport(camera.viewportWidth, camera.viewportHeight, camera));

        initFonte();
        initInformacoes();
        initJogador();
        initTextura();
        initSons();


    }

    private void initSons() {
        somTiro = Gdx.audio.newSound(Gdx.files.internal("sounds/shoot.mp3"));
        somGameOver = Gdx.audio.newSound(Gdx.files.internal("sounds/gameover.mp3"));
        somExploracao = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.mp3"));
        musicaFundo= Gdx.audio.newMusic(Gdx.files.internal("sounds/background.mp3"));
        musicaFundo.setLooping(true);
    }

    private void initTextura() {
        texturaTiro = new Texture("sprites/shot.png");
        texturaMeteoro1 = new Texture("sprites/enemie-1.png");
        texturaMeteoro2 = new Texture("sprites/enemie-2.png");

        for(int i = 1; i <= 17;i++){
            Texture text = new Texture("sprites/explosion-"+ i +".png");
            texturasExplosao.add(text);
        }
    }

    private void initJogador() {

        texturaJogador = new Texture("sprites/player.png");
        texturaJogadorDireita = new Texture("sprites/player-right.png");
        texturaJogadorEsquerda = new Texture("sprites/player-left.png");

        jogador = new Image(texturaJogador);
        float x = camera.viewportWidth / 2 - jogador.getWidth() / 2;
        float y = 0;
        jogador.setPosition(x, y);
        palco.addActor(jogador);
    }

    private void initInformacoes() {
        Label.LabelStyle lbEstilo = new Label.LabelStyle();

        //cria estilo para fonte.
        lbEstilo.font = fonte;
        lbEstilo.fontColor = Color.WHITE;

        lbPontuacao = new Label(" 0 pontos", lbEstilo);
        lbPontuacao.setPosition(10,camera.viewportHeight-20);

        palcoInformacoes.addActor(lbPontuacao);

        lbGamerOver = new Label("GAME OVER", lbEstilo);
        lbGamerOver.setVisible(false);

        palcoInformacoes.addActor(lbGamerOver);

    }

    /**
     * Inicia configurações de fontes
     */
    private void initFonte() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/roboto.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.color = Color.WHITE;
        param.size = 24;
        param.shadowOffsetX = 2;
        param.shadowOffsetY = 2;
        param.shadowColor = Color.BLUE;

        fonte = generator.generateFont(param);
        generator.dispose();
        //fonte = new BitmapFont();

    }

    /**
     * Chamado a todo quadro de atualização do jogo(FPS)
     * @param delta tempo entre um quadro e outro (em segundos)
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.15f, .15f, .24f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        lbPontuacao.setPosition(10, camera.viewportHeight - lbPontuacao.getPrefHeight() - 20);
        lbPontuacao.setText(pontuacao + " Pontos");

        lbGamerOver.setPosition(camera.viewportWidth / 2 - lbGamerOver.getPrefWidth() / 2, camera.viewportHeight / 2);
        lbGamerOver.setVisible(gameOver);

        atualizarExplosoes(delta);

        if(!gameOver) {
            if(!musicaFundo.isPlaying())musicaFundo.play();

            capturaTeclas();
            atualizarJogador(delta);

            atualizarTiros(delta);

            atualizarMeteoros(delta);

            detectarColisoes(meteoros1, PONTUACAOMETEORO1);
            detectarColisoes(meteoros2, PONTUACAOMETEORO2);
        }else{
            if(musicaFundo.isPlaying())musicaFundo.stop();
        }
        palco.act(delta);
        palcoInformacoes.act(delta);
        palco.draw();
        palcoInformacoes.act(delta);
        palcoInformacoes.draw();


    }

    private void atualizarExplosoes(float delta) {
        for(Explosao explosao: explosoes){
            //verifica se explosao chegou ao fim
            if(explosao.getEstagio() >= 16){
                explosoes.removeValue(explosao,true);
                explosao.getAtor().remove();
            }else{
                explosao.atualizar(delta);
            }
        }
    }

    private Rectangle recJogador = new Rectangle();
    private Rectangle recTiro = new Rectangle();
    private Rectangle recMeteoro = new Rectangle();
    private int pontuacao = 0;
    private boolean gameOver = false;

    private void detectarColisoes(Array<Image> meteoros, int valorPonto) {

        recJogador.set(jogador.getX(),jogador.getY(), jogador.getWidth(), jogador.getHeight());

                // detecta colisoes com os tiros
        for(Image meteoro : meteoros) {
            recMeteoro.set(meteoro.getX(), meteoro.getY(), meteoro.getWidth(), meteoro.getHeight());
            for (Image tiro : tiros) {
                recTiro.set(tiro.getX(), tiro.getY(), tiro.getWidth(), tiro.getHeight());
                //colisao
                if (recMeteoro.overlaps(recTiro)) {
                    pontuacao += valorPonto; // incrementa a pontuacao
                    meteoro.remove(); // remove do palco
                    meteoros.removeValue(meteoro, true); //remove da lista
                    criarExplosao(meteoro.getX() +( meteoro.getWidth()/2), meteoro.getY()+(meteoro.getHeight()/2));
                }

            }
            //detecta colissao com o player.
            if(recJogador.overlaps(recMeteoro)){
                gameOver = true;
                somGameOver.play();

                //jogador.setVisible(false);
            }
        }

    }

    /**
     * Cria explosao na posicao X e Y
     * @param x
     * @param y
     */
    private void criarExplosao(float x, float y) {
        Image ator = new Image(texturasExplosao.get(0));
        ator.setPosition(x - (ator.getWidth()/2), y-(ator.getHeight()/2));
        palco.addActor(ator);
        somExploracao.play();
        Explosao explosao = new Explosao(ator, texturasExplosao);
        explosoes.add(explosao);

    }

    private void atualizarMeteoros(float delta) {
        int qtdMeteoro = meteoros1.size + meteoros2.size; //retorna a quantidade de meteoros criados

        if(qtdMeteoro < 10) {
            int tipo = MathUtils.random(1,4);
            if (tipo == 1) {
                //cria meteoro 1
                Image meteoro = new Image(texturaMeteoro1);
                float x = MathUtils.random(0, camera.viewportWidth - meteoro.getWidth());
                float y = MathUtils.random(camera.viewportHeight, camera.viewportHeight * 2);
                meteoro.setPosition(x, y);
                meteoros1.add(meteoro);
                palco.addActor(meteoro);

            } else if(tipo == 2){
                //cria meteoro 2
                //cria meteoro 1
                Image meteoro = new Image(texturaMeteoro2);
                float x = MathUtils.random(0, camera.viewportWidth - meteoro.getWidth());
                float y = MathUtils.random(camera.viewportHeight, camera.viewportHeight * 2);
                meteoro.setPosition(x, y);
                meteoros2.add(meteoro);
                palco.addActor(meteoro);

            }
        }
        float velocidade = VELOCIDADEMETEORO1; //velocidade de movimentacao do tiro - pixel por segundos
        //percorre a todos os tiros e atualiza a sua posicao
        for (Image meteoro : meteoros1) {
            float x = meteoro.getX();
            float y = meteoro.getY() - velocidade * delta;

            //atualiza a posicao do meteoro
            meteoro.setPosition(x, y);

            //remove tiro que sairam da tela
            if(meteoro.getY()+ meteoro.getHeight() < 0){
                meteoro.remove(); // remove do palco
                meteoros1.removeValue(meteoro, true); //remove da lista
            }
        }
        float velocidade2 = VELOCIDADEMETEORO2; //velocidade de movimentacao do tiro - pixel por segundos
        for (Image meteoro : meteoros2) {
            float x = meteoro.getX();
            float y = meteoro.getY() - velocidade2 * delta;

            //atualiza a posicao do meteoro
            meteoro.setPosition(x, y);

            //remove tiro que sairam da tela
            if(meteoro.getY()+ meteoro.getHeight() < 0){
                meteoro.remove(); // remove do palco
                meteoros2.removeValue(meteoro, true); //remove da lista
            }
        }

    }

    private final float MIN_INTERVALO_TIROS = 0.4f; //minimo de tempo entre os tiros
    private float intervaloTiros = 0; //tempo acumulado entre tiros.

    private void atualizarTiros(float delta) {
        intervaloTiros += delta; // acumula o tempo percorrido

        if(intervaloTiros >= MIN_INTERVALO_TIROS) { //verifica se o tempo minimo foi atingido
            if (atirando) {// cria um novo tiro se necessario
                Image tiro = new Image(texturaTiro);
                float x = jogador.getX() + jogador.getWidth() / 2 -tiro.getWidth() / 2;
                float y = jogador.getY() + jogador.getHeight();

                somTiro.play();

                tiro.setPosition(x, y);
                tiros.add(tiro);
                palco.addActor(tiro);
                intervaloTiros = 0;
            }
        }

        float velocidade = 200; //velocidade de movimentacao do tiro
        //percorre a todos os tiros e atualiza a sua posicao
        for (Image tiro : tiros) {
            float x = tiro.getX();
            float y = tiro.getY() + velocidade * delta;
            tiro.setPosition(x, y);

            //remove tiro que sairam da tela
            if(tiro.getY() > camera.viewportHeight){
                tiros.removeValue(tiro, true); //remove da lista
                tiro.remove(); // remove do palco
            }
        }

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
        atirando = (Gdx.input.isKeyPressed(Input.Keys.SPACE))? true : false;


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
        palcoInformacoes.dispose();
        fonte.dispose();
        texturaJogador.dispose();
        texturaJogadorDireita.dispose();
        texturaJogadorEsquerda.dispose();
        texturaMeteoro1.dispose();
        texturaMeteoro2.dispose();
        for (Texture text : texturasExplosao){
            text.dispose();
        }

        somExploracao.dispose();
        somGameOver.dispose();
        somTiro.dispose();
        musicaFundo.dispose();

    }
}
