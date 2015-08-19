package br.edu.grupointegrado;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;


/**
 * Created by misael on 18/08/15.
 */
public class Elemento {
    //private Texture quadrado = new Texture("square.png");
    //private Texture triangulo = new Texture("triangle.png");
    //private Texture circulo = new Texture("circle.png");
    private float x;
    private float y;
    private Image elemento;
    private int state;


    /**
     * Contrutor Elemento
     * @param x coordenada X do elemento
     * @param y coordenada Y do elemento
     */
    public Elemento(float x, float y) {
        this.x = x;
        this.y = y;

        //Cria o elemento com a textura quadrado
        this.elemento = new Image(new Texture("square.png")) ;

        //Seta as coordernadas no objeto
        this.elemento.setPosition(x,y);

        this.state = 1;
    }

    public int getState() {
        return state;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Image getElemento() {
        return elemento;
    }

    /**
     * atualiza a textura e state do elemento.
     */
    public void atualizaElemento(){
        switch (state){
            case 1:
                state++;
                elemento.setDrawable(new SpriteDrawable(new Sprite(new Texture("triangle.png"))));
                break;
            case 2:
                state++;
                elemento.setDrawable(new SpriteDrawable(new Sprite(new Texture("circle.png"))));
                break;
            case 3:
                state++;
                break;
        }
    }
}
