package br.edu.grupointegrado;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class JogoVelha extends ApplicationAdapter {
    ShapeRenderer shape;
    int verticalY;
    int horizontalX;
    int sizeX;
    int sizeY;
    float maxArea;

    public void boli(int linha, int coluna, ShapeRenderer objeto, Color cor){
        //calcula coordenadas do circulo conforme linha e coluna.
        int x = (horizontalX*linha)+(int)(horizontalX/2);
        int y = (verticalY*coluna)+(int)(verticalY/2);

        //seleciona cor fornecida
        objeto.setColor(cor);
        //desenha o circulo, na Área da grade.
        objeto.circle(x,y, maxArea);
    }

    public void cruz(int linha, int coluna, ShapeRenderer objeto, Color cor){
        //calcula coordenadas da cruz conforme linha e coluna.
        int x = (horizontalX*linha)+(int)(horizontalX/2);
        int y = (verticalY*coluna)+(int)(verticalY/2);

        //seleciona a cor fornecida.
        objeto.setColor(cor);

        //desenhas as linhas do X.
        objeto.line(x-maxArea,y-maxArea,x+maxArea,y+maxArea);
        objeto.line(x+maxArea,y-maxArea,x-maxArea,y+maxArea);
    }

    @Override
	public void create () {
        shape = new ShapeRenderer();

        //Adquire os tamanhos da tela.
        sizeX = Gdx.graphics.getWidth();
        sizeY = Gdx.graphics.getHeight();

        //calcula o tamanho de cada grade.
        horizontalX = (int)( sizeX / 3);
        verticalY = (int)( sizeY / 3);

        //calcula a menor área maxima.
        maxArea = (horizontalX < verticalY)? horizontalX : verticalY;
        maxArea *= 0.40; //calcula um raio de 80% da area max

	}

	@Override
	public void render () {
        Gdx.gl.glClearColor(130,130,130,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shape.begin(ShapeRenderer.ShapeType.Line);

        //cor da Linha das grades
        shape.setColor(Color.BLACK);

        //desenha as grades.
        for (int b = 1;b < 3;b++){
            //Linha horizontal
            shape.line(horizontalX * b,sizeY,horizontalX * b,0);
            
            //Linha vertical
            shape.line(sizeX,verticalY * b, 0,verticalY * b);
        }

        //muda a espessura da linha
        Gdx.gl.glLineWidth(50);

        //Bolinhas e Quadradinhos
        cruz(0, 0,shape,Color.BLACK);
        boli(0, 1, shape, Color.BLACK);
        boli(0, 2, shape, Color.GREEN);
        cruz(1, 0,shape,Color.BLACK);
        boli(1, 1, shape, Color.GREEN);
        boli(1, 2, shape, Color.BLACK);
        boli(2, 0, shape, Color.GREEN);
        cruz(2, 1, shape,Color.BLACK);
        cruz(2, 2, shape,Color.BLACK);

        shape.end();
	}
    }
