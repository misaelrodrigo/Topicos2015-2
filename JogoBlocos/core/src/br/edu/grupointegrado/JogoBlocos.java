package br.edu.grupointegrado;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FillViewport;

/**
 * Created by misael on 18/08/15.
 */
public class JogoBlocos extends TelaBase {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Stage palco;
    private Label lbQuantidade;
    private BitmapFont fonte;
    private Array<Elemento> listaElementos = new Array<Elemento>();

    /**
     * Contrutor
     * @param game
     */
    public JogoBlocos(MainJogo game) {
        super(game);
    }

    @Override
    public void show() {
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();

        //cria o balco no tamanho da camera e referencia a nosso camera ao balco.
        palco = new Stage(new FillViewport(camera.viewportWidth, camera.viewportHeight, camera));

        initFonte();
        initInformacoes();
    }

    /**
     * Inicializa informações na Tela.
     */
    private void initInformacoes() {
        Label.LabelStyle lbEstilo = new Label.LabelStyle();

        //cria estilo para fonte.
        lbEstilo.font = fonte;
        //cor da fonte
        lbEstilo.fontColor = Color.WHITE;

        lbQuantidade = new Label( listaElementos.size + "0 Elemento(s)", lbEstilo);

        //posiciona o label no topo esquerdo
        lbQuantidade.setPosition(10,camera.viewportHeight-20);

        //adiciona o label ao palco
        palco.addActor(lbQuantidade);
    }

    private void initFonte() {
        fonte = new BitmapFont();
    }

    @Override
    public void render(float delta) {

        atualizaBackground();

        //verifica dados de entrada e manipula a lista
        verificainput();

        //desenha os elementos
        desenhaElementos();

        //atualiza informações na tela
        atualizaInformacoes();

        //desenha o palco
        palco.act(delta);
        palco.draw();
    }

    private void atualizaInformacoes() {
        //atualiza quantidade de elementos na tela.
        lbQuantidade.setText(listaElementos.size+" Elemento(s)");
    }

    private void desenhaElementos() {

        //varre a lista de elementos e adiciona ao palco
        for(Elemento elemento: listaElementos){
            palco.addActor(elemento.getElemento());
        }
    }

    private void verificainput() {
        if (Gdx.input.justTouched()) {

            Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);

            float x = touch.x;
            float y = touch.y;

            if(atualizaElemento(x,y))
            listaElementos.add(new Elemento(x,y));


            // posição X e Y que foi tocada na tela
        }
    }

    /**
     * verifica se existe e atualiza seu estado.
     * @param x coordenada x
     * @param y coordenada y
     * @return se precisa criar um elemento.
     */
    private boolean atualizaElemento(float x, float y) {

        for(Elemento elemento : listaElementos){
            float minX = elemento.getX();
            float minY = elemento.getY();
            float maxX = elemento.getX() + elemento.getElemento().getWidth();
            float maxY = elemento.getY() + elemento.getElemento().getHeight();

            if(x >= minX && x <= maxX){
                if(y >= minY && y <= maxY){
                    elemento.atualizaElemento();
                    if(elemento.getState() == 4){
                        listaElementos.removeValue(elemento,true); //remove da lista
                        elemento.getElemento().remove(); //removo do palco
                    }
                    return false;
                }
            }

        }
        return true;
    }

    private void atualizaBackground() {
        Gdx.gl.glClearColor(.15f,.15f,.24f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        palco.dispose();
        fonte.dispose();
    }
}
