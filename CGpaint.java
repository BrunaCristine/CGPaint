import javax.swing.JFrame;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Color;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import java.awt.Image;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;

class CGpaint extends JFrame{
	public CGpaint(){
		setTitle("CG Paint");
		setSize(1030,700);
		setLocationRelativeTo(null);
		setBackground(Color.WHITE);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) throws IOException{
		BufferedImage bufferImage =new BufferedImage(50,50,BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = bufferImage.createGraphics();
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, 50, 50);
		g2d.setColor(Color.BLACK);
		g2d.drawRect(0, 0, 49, 49);
		g2d.dispose();
		File file = new File("borracha.png");
		ImageIO.write(bufferImage,"png",file);
		new CGpaint().add(new Display());
	}
}

class Display extends JPanel{
	int mouse=0,xMin,xMax,yMin,yMax,xInicio=0,xFim=0,yInicio=0,yFim=0,x01,y01,x02,y02;
	Celula k=null;
	double u1=0.0,u2=1.0;
	boolean umaVez=false,reflexao=false,escala=false,refletir=false,mais=false,menos=false;
	boolean cima=false,baixo=false,esquerda=false,direita=false,rotacionar=false;
	boolean pressionado=false,dda_abilitado=false,bresenhan_abilitado=false,circunferencia_abilitado=false,desenho_livre=true, borracha=false,clear=false,retangulo=false,recorte=false,cohen=false, liang=false,rasterizacao=false,posicaoRecorte=false,translacao=false,click=false,rotacao=false;
	JButton btn_dda = new JButton("DDA");
	JButton btn_bresenhan = new JButton("Bresenhan");
	JButton btn_circunferencia = new JButton("Circunferencia");
	JButton btn_livre = new JButton("Desenho Livre");
	JButton btn_borracha = new JButton("Borracha");
	JButton btn_retangulo = new JButton("Retangulos");
	JButton btn_recorte = new JButton("Recorte");
	JButton btn_clear = new JButton("Limpar Quadro");
	JButton btn_rasterizacao = new JButton("Rasterização de Retas");
	JButton btn_cohen = new JButton("Cohen-Sutherland");
	JButton btn_liang = new JButton("Liang-Barsky");
	JButton btn_translacao = new JButton("Translação");
	JButton btn_rotacao = new JButton("Rotação");
	JButton btn_escala = new JButton("Escala");
	JButton btn_cima = new JButton("Cima");
	JButton btn_baixo = new JButton("Baixo");
	JButton btn_esquerda = new JButton("Esquerda");
	JButton btn_direita = new JButton("Direita");
	JButton btn_rotacionar = new JButton("Rotacionar");
	JButton btn_reflexao = new JButton("Reflexão");
	JButton btn_okTransformacao = new JButton("OK");
	JButton btn_mais = new JButton("Mais");
	JButton btn_menos = new JButton("Menos");
	Lista quadro=new Lista();

	public Display(){
		btn_dda.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				desenhos.clear();
				dda_abilitado=true;
				umaVez=true;
				desenho_livre=false;
				borracha=false;
				translacao=false;
				rotacao=false;
				escala=false;
				reflexao=false;
				k=null;
				btn_cima.setVisible(false);
				btn_baixo.setVisible(false);
				btn_esquerda.setVisible(false);
				btn_direita.setVisible(false);
				btn_rotacionar.setVisible(false);
				btn_mais.setVisible(false);
				btn_menos.setVisible(false);
				btn_okTransformacao.setVisible(false);
				setMouse(1);
			}
		});
		btn_bresenhan.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				desenhos.clear();
				dda_abilitado=false;
				umaVez=true;
				bresenhan_abilitado=true;
				desenho_livre=false;
				borracha=false;
				translacao=false;
				rotacao=false;
				escala=false;
				reflexao=false;
				k=null;
				btn_cima.setVisible(false);
				btn_baixo.setVisible(false);
				btn_esquerda.setVisible(false);
				btn_direita.setVisible(false);
				btn_rotacionar.setVisible(false);
				btn_mais.setVisible(false);
				btn_menos.setVisible(false);
				btn_okTransformacao.setVisible(false);
				setMouse(1);
			}
		});
		btn_circunferencia.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				desenhos.clear();
				dda_abilitado=false;
				bresenhan_abilitado=false;
				circunferencia_abilitado=true;
				desenho_livre=false;
				borracha=false;
				translacao=false;
				rotacao=false;
				escala=false;
				reflexao=false;
				k=null;
				btn_cima.setVisible(false);
				btn_baixo.setVisible(false);
				btn_esquerda.setVisible(false);
				btn_direita.setVisible(false);
				btn_rotacionar.setVisible(false);
				btn_mais.setVisible(false);
				btn_menos.setVisible(false);
				btn_okTransformacao.setVisible(false);
				setMouse(1);
			}
		});
		btn_livre.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				desenhos.clear();
				desenho_livre=true;
				translacao=false;
				rotacao=false;
				escala=false;
				reflexao=false;
				k=null;
				btn_cima.setVisible(false);
				btn_baixo.setVisible(false);
				btn_esquerda.setVisible(false);
				btn_direita.setVisible(false);
				btn_rotacionar.setVisible(false);
				btn_mais.setVisible(false);
				btn_menos.setVisible(false);
				btn_okTransformacao.setVisible(false);
				setMouse(2);
			}
		});
		btn_borracha.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				quadro.clear();
				desenhos.clear();
				desenho_livre=false;
				borracha=true;
				translacao=false;
				rotacao=false;
				escala=false;
				reflexao=false;
				k=null;
				btn_cima.setVisible(false);
				btn_baixo.setVisible(false);
				btn_esquerda.setVisible(false);
				btn_direita.setVisible(false);
				btn_rotacionar.setVisible(false);
				btn_mais.setVisible(false);
				btn_menos.setVisible(false);
				btn_okTransformacao.setVisible(false);
				setMouse(3);
			}
		});
		btn_retangulo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				desenhos.clear();
				umaVez=true;
				dda_abilitado=false;
				bresenhan_abilitado=false;
				circunferencia_abilitado=false;
				desenho_livre=false;
				borracha=false;
				retangulo=true;
				translacao=false;
				rotacao=false;
				escala=false;
				reflexao=false;
				k=null;
				btn_cima.setVisible(false);
				btn_baixo.setVisible(false);
				btn_esquerda.setVisible(false);
				btn_direita.setVisible(false);
				btn_rotacionar.setVisible(false);
				btn_mais.setVisible(false);
				btn_menos.setVisible(false);
				btn_okTransformacao.setVisible(false);
				setMouse(1);
			}
		});
		btn_recorte.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				desenhos.clear();
				dda_abilitado=false;
				bresenhan_abilitado=false;
				circunferencia_abilitado=false;
				desenho_livre=false;
				borracha=false;
				retangulo=false;
				recorte=true;
				translacao=false;
				rotacao=false;
				escala=false;
				reflexao=false;
				k=null;
				btn_cima.setVisible(false);
				btn_baixo.setVisible(false);
				btn_esquerda.setVisible(false);
				btn_direita.setVisible(false);
				btn_rotacionar.setVisible(false);
				btn_mais.setVisible(false);
				btn_menos.setVisible(false);
				btn_okTransformacao.setVisible(false);
				setMouse(1);
			}
		});
		btn_clear.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				quadro.clear();
				desenhos.clear();
				k=null;
				desenhosPermanentes.clear();
				clear=true;
				translacao=false;
				rotacao=false;
				escala=false;
				reflexao=false;
				btn_cohen.setVisible(true);
				btn_liang.setVisible(true);
				btn_cima.setVisible(false);
				btn_baixo.setVisible(false);
				btn_esquerda.setVisible(false);
				btn_direita.setVisible(false);
				btn_rotacionar.setVisible(false);
				btn_mais.setVisible(false);
				btn_menos.setVisible(false);
				btn_okTransformacao.setVisible(false);
			}
		});
		btn_cohen.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				desenhos.clear();
				dda_abilitado=false;
				bresenhan_abilitado=false;
				circunferencia_abilitado=false;
				desenho_livre=false;
				borracha=false;
				retangulo=false;
				recorte=false;
				clear=false;
				cohen=true;
				translacao=false;
				rotacao=false;
				escala=false;
				reflexao=false;
				k=null;
				btn_cima.setVisible(false);
				btn_baixo.setVisible(false);
				btn_esquerda.setVisible(false);
				btn_direita.setVisible(false);
				btn_rotacionar.setVisible(false);
				btn_mais.setVisible(false);
				btn_menos.setVisible(false);
				btn_okTransformacao.setVisible(false);
				setMouse(1);
			}
		});
		btn_liang.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				desenhos.clear();
				dda_abilitado=false;
				bresenhan_abilitado=false;
				circunferencia_abilitado=false;
				desenho_livre=false;
				borracha=false;
				retangulo=false;
				recorte=false;
				clear=false;
				cohen=false;
				liang=true;
				translacao=false;
				rotacao=false;
				escala=false;
				reflexao=false;
				k=null;
				btn_cima.setVisible(false);
				btn_baixo.setVisible(false);
				btn_esquerda.setVisible(false);
				btn_direita.setVisible(false);
				btn_rotacionar.setVisible(false);
				btn_mais.setVisible(false);
				btn_menos.setVisible(false);
				btn_okTransformacao.setVisible(false);
				setMouse(1);
			}
		});
		btn_rasterizacao.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				desenhos.clear();
				dda_abilitado=false;
				bresenhan_abilitado=false;
				circunferencia_abilitado=false;
				desenho_livre=false;
				borracha=false;
				retangulo=false;
				recorte=false;
				clear=false;
				cohen=false;
				liang=false;
				rasterizacao=true;
				translacao=false;
				rotacao=false;
				escala=false;
				reflexao=false;
				k=null;
				btn_cima.setVisible(false);
				btn_baixo.setVisible(false);
				btn_esquerda.setVisible(false);
				btn_direita.setVisible(false);
				btn_rotacionar.setVisible(false);
				btn_mais.setVisible(false);
				btn_menos.setVisible(false);
				btn_okTransformacao.setVisible(false);
				setMouse(1);
			}
		});
		btn_translacao.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				desenhos.clear();
				translacao=true;
				cima=false;
				baixo=false;
				esquerda=false;
				direita=false;
				k=null;
				btn_cima.setVisible(true);
				btn_baixo.setVisible(true);
				btn_esquerda.setVisible(true);
				btn_direita.setVisible(true);
				btn_rotacionar.setVisible(false);
				btn_mais.setVisible(false);
				btn_menos.setVisible(false);
				btn_okTransformacao.setVisible(true);
				setMouse(1);
			}
		});
		btn_okTransformacao.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				translacao=false;
				rotacao=false;
				reflexao=false;
				recorte=false;
				k=null;
				btn_cima.setVisible(false);
				btn_baixo.setVisible(false);
				btn_esquerda.setVisible(false);
				btn_direita.setVisible(false);
				btn_rotacionar.setVisible(false);
				btn_mais.setVisible(false);
				btn_menos.setVisible(false);
				btn_okTransformacao.setVisible(false);
			}
		});
		btn_rotacao.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				desenhos.clear();
				rotacao=true;
				k=null;
				btn_cima.setVisible(false);
				btn_baixo.setVisible(false);
				btn_esquerda.setVisible(false);
				btn_direita.setVisible(false);
				btn_rotacionar.setVisible(true);
				btn_mais.setVisible(false);
				btn_menos.setVisible(false);
				btn_okTransformacao.setVisible(true);
				setMouse(1);
			}
		});
		btn_reflexao.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				desenhos.clear();
				reflexao=true;
				k=null;
				btn_cima.setVisible(false);
				btn_baixo.setVisible(false);
				btn_esquerda.setVisible(false);
				btn_direita.setVisible(false);
				btn_rotacionar.setVisible(false);
				btn_mais.setVisible(false);
				btn_menos.setVisible(false);
				btn_okTransformacao.setVisible(true);
				setMouse(1);
			}
		});
		btn_escala.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				desenhos.clear();
				escala=true;
				mais=false;
				menos=false;
				k=null;
				btn_cima.setVisible(false);
				btn_baixo.setVisible(false);
				btn_esquerda.setVisible(false);
				btn_direita.setVisible(false);
				btn_rotacionar.setVisible(false);
				btn_mais.setVisible(true);
				btn_menos.setVisible(true);
				btn_okTransformacao.setVisible(true);
				setMouse(1);
			}
		});
		btn_cima.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				cima=true;
			}
		});
		btn_baixo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				baixo=true;
			}
		});
		btn_esquerda.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				esquerda=true;
			}
		});
		btn_direita.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				direita=true;
			}
		});
		btn_rotacionar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				rotacionar=true;
			}
		});
		btn_mais.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				mais=true;
			}
		});
		btn_menos.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				menos=true;
			}
		});
		setLayout(null);
		btn_dda.setBounds(10,10,65,20);
		btn_bresenhan.setBounds(90,10,115,20);
		btn_circunferencia.setBounds(220,10,140,20);
		btn_livre.setBounds(370,10,135,20);
		btn_borracha.setBounds(515,10,100,20);
		btn_retangulo.setBounds(625,10,115,20);
		btn_recorte.setBounds(750,10,90,20);
		btn_clear.setBounds(850,10,140,20);
		btn_rasterizacao.setBounds(10,40,195,20);
		btn_cohen.setBounds(215,40,165,20);
		btn_liang.setBounds(390,40,140,20);
		btn_translacao.setBounds(540,40,115,20);
		btn_cima.setBounds(10,70,70,20);
		btn_baixo.setBounds(90,70,75,20);
		btn_esquerda.setBounds(175,70,105,20);
		btn_direita.setBounds(290,70,85,20);
		btn_rotacionar.setBounds(385,70,115,20);
		btn_rotacao.setBounds(665,40,95,20);
		btn_escala.setBounds(770,40,80,20);
		btn_reflexao.setBounds(860,40,95,20);
		btn_mais.setBounds(510,70,70,20);
		btn_menos.setBounds(590,70,85,20);
		btn_okTransformacao.setBounds(685,70,55,20);
		add(btn_dda);
		add(btn_bresenhan);
		add(btn_circunferencia);
		add(btn_livre);
		add(btn_borracha);
		add(btn_retangulo);
		add(btn_recorte);
		add(btn_clear);
		add(btn_cohen);
		add(btn_liang);
		add(btn_rasterizacao);
		add(btn_translacao);
		add(btn_okTransformacao);
		add(btn_rotacao);
		add(btn_escala);
		add(btn_cima);
		add(btn_baixo);
		add(btn_esquerda);
		add(btn_direita);
		add(btn_rotacionar);
		add(btn_reflexao);
		add(btn_mais);
		add(btn_menos);
		btn_cima.setVisible(false);
		btn_baixo.setVisible(false);
		btn_esquerda.setVisible(false);
		btn_direita.setVisible(false);
		btn_rotacionar.setVisible(false);
		btn_mais.setVisible(false);
		btn_menos.setVisible(false);
		btn_okTransformacao.setVisible(false);
		addMouseListener(new MouseListener(){
			public void mousePressed (MouseEvent e){
				pressionado=true;
				desenhos.clear();
			}
			public void mouseReleased (MouseEvent e){
				if(desenho_livre){
					quadro.inserir(desenhos);
				}
				pressionado=false;
				posicaoRecorte=true; //variavel auxiliar utilizada apenas para salvar a posicao inicial e final de uma reta para o arraylist de desenhos permanetes, o qual sera usado para os metodos de recorte (cohen-sutherland e liang-barsky)
			}
			public void mouseExited (MouseEvent e){
			}
			public void mouseEntered (MouseEvent e){
			}
			public void mouseClicked (MouseEvent e){
				Point p=getMousePosition();
				if(p.y>=100 && (translacao||rotacao||reflexao||escala)){
					click=true;
				}
				else{
					click=false;
				}
			}
		});
		addKeyListener(new KeyAdapter(){
			public void keyTyped(KeyEvent e){

			}
			public void keyReleased(KeyEvent e){
				cima=false;
				baixo=false;
				esquerda=false;
				direita=false;
			}
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_LEFT){
					esquerda=true;
				}
				else if(e.getKeyCode()==KeyEvent.VK_UP){
					cima=true;
				}
				else if(e.getKeyCode()==KeyEvent.VK_RIGHT){
					direita=true;
				}
				else if(e.getKeyCode()==KeyEvent.VK_DOWN){
					baixo=true;
				}

			}
		});
		setMouse(2);
		new time().start();
	}

	public void MudaCursor(String nomeImagem){
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage(nomeImagem);
		Point point =new Point(0,0);
		Cursor cursor = toolkit.createCustomCursor(img, point, "borracha");
		setCursor(cursor);
	}

	void setMouse(int mouse){
		Cursor cursor;
		if(mouse==1){
			cursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
			setCursor(cursor);
			this.mouse=1;
		}
		else if(mouse==2){
			cursor = new Cursor(Cursor.SW_RESIZE_CURSOR);
			setCursor(cursor);
			this.mouse=2;
		}
		else if(mouse==3){
			MudaCursor("borracha.png");
			this.mouse=3;
		}
		else{
			setCursor(null);
		}
	}

	ArrayList<desenho> desenhos = new ArrayList<>();
	ArrayList<posicoes> desenhosPermanentes = new ArrayList<>();

	public class desenho{
		int x,y;
		public desenho(int x,int y){
			this.x=x;
			this.y=y;
		}
	}

	public class posicoes{
		desenho inicio,fim;
		public posicoes(desenho inicio,desenho fim){
			this.inicio=inicio;
			this.fim=fim;
		}
	} 
	
	public void paintComponent(Graphics g){
		g.setColor(Color.black);
		g.fillRect(0, 0, 1030, 100);
		if(clear){
			g.clearRect(0, 100, 1030, 600);
			btn_translacao.setBounds(540,40,115,20);
			btn_rotacao.setBounds(665,40,95,20);
			btn_escala.setBounds(770,40,80,20);
			btn_reflexao.setBounds(860,40,95,20);
			clear=false;
		}
		else if(translacao){
			int passos=5;
			if(click){
				Point pointer = getMousePosition();
				for(Celula i=quadro.primeiro;i!=null;i=i.prox){
					for(int j=0;j<i.elemento.size();j++){
						if((i.elemento.get(j).x<=pointer.x+2&&i.elemento.get(j).x>=pointer.x-2)&&(i.elemento.get(j).y<=pointer.y+2&&i.elemento.get(j).y>=pointer.y-2)){
							j=i.elemento.size();
							k=i;
							i=null;
							click=false;
						}
					}
				}
			}
			else{
				xInicio=k.elemento.get(0).x;
				yInicio=k.elemento.get(0).y;
				xFim=k.elemento.get(k.elemento.size()-1).x;
				yFim=k.elemento.get(k.elemento.size()-1).y;
				if(cima){
					for(int i=0;i<k.elemento.size();i++){
						g.setColor(Color.WHITE);
						g.drawLine(k.elemento.get(i).x,k.elemento.get(i).y,k.elemento.get(i).x,k.elemento.get(i).y);
					}
					for(int i=0;i<k.elemento.size();i++){
						k.elemento.get(i).y=k.elemento.get(i).y-passos;
						g.setColor(Color.BLACK);
						g.drawLine(k.elemento.get(i).x,k.elemento.get(i).y,k.elemento.get(i).x,k.elemento.get(i).y);
					}
					cima=false;
				}
				else if(baixo){
					for(int i=0;i<k.elemento.size();i++){
						g.setColor(Color.WHITE);
						g.drawLine(k.elemento.get(i).x,k.elemento.get(i).y,k.elemento.get(i).x,k.elemento.get(i).y);
					}
					for(int i=0;i<k.elemento.size();i++){
						k.elemento.get(i).y=k.elemento.get(i).y+passos;
						g.setColor(Color.BLACK);
						g.drawLine(k.elemento.get(i).x,k.elemento.get(i).y,k.elemento.get(i).x,k.elemento.get(i).y);
					}
					baixo=false;
				}
				else if(direita){
					for(int i=0;i<k.elemento.size();i++){
						g.setColor(Color.WHITE);
						g.drawLine(k.elemento.get(i).x,k.elemento.get(i).y,k.elemento.get(i).x,k.elemento.get(i).y);
					}
					for(int i=0;i<k.elemento.size();i++){
						k.elemento.get(i).x=k.elemento.get(i).x+passos;
						g.setColor(Color.BLACK);
						g.drawLine(k.elemento.get(i).x,k.elemento.get(i).y,k.elemento.get(i).x,k.elemento.get(i).y);
					}
					direita=false;
				}
				else if(esquerda){
					for(int i=0;i<k.elemento.size();i++){
						g.setColor(Color.WHITE);
						g.drawLine(k.elemento.get(i).x,k.elemento.get(i).y,k.elemento.get(i).x,k.elemento.get(i).y);
					}
					for(int i=0;i<k.elemento.size();i++){
						k.elemento.get(i).x=k.elemento.get(i).x-passos;
						g.setColor(Color.BLACK);
						g.drawLine(k.elemento.get(i).x,k.elemento.get(i).y,k.elemento.get(i).x,k.elemento.get(i).y);
					}
					esquerda=false;
				}
			}
		}
		else if(rotacao){
			if(click){
				Point pointer = getMousePosition();
				for(Celula i=quadro.primeiro;i!=null;i=i.prox){
					for(int j=0;j<i.elemento.size();j++){
						if((i.elemento.get(j).x<=pointer.x+2&&i.elemento.get(j).x>=pointer.x-2)&&(i.elemento.get(j).y<=pointer.y+2&&i.elemento.get(j).y>=pointer.y-2)){
							j=i.elemento.size();
							k=i;
							i=null;
							click=false;
							xInicio=k.elemento.get(0).x;
							yInicio=k.elemento.get(0).y;
							xFim=k.elemento.get(k.elemento.size()-1).x;
							yFim=k.elemento.get(k.elemento.size()-1).y;
						}
					}
				}
			}
			else{
				if(rotacionar){
				for(int i=0;i<k.elemento.size();i++){
					g.setColor(Color.WHITE);
					g.drawLine(k.elemento.get(i).x,k.elemento.get(i).y,k.elemento.get(i).x,k.elemento.get(i).y);
				}
				for(int i=0;i<k.elemento.size();i++){
					int xAux=(int)((k.elemento.get(i).x*Math.cos(Math.PI/12))+(k.elemento.get(i).y*-(Math.sin(Math.PI/12))));
					int yAux=(int)((k.elemento.get(i).x*Math.sin(Math.PI/12))+(k.elemento.get(i).y*Math.cos(Math.PI/12)));
					k.elemento.get(i).x=xAux;
					k.elemento.get(i).y=yAux;
				}
				/*
				Reposicionameno do desenho
				*/
				while(k.elemento.get(0).x<xInicio){
					for(int i=0;i<k.elemento.size();i++){
						k.elemento.get(i).x++;
					}
				}
				while(k.elemento.get(0).x>xInicio){
					for(int i=0;i<k.elemento.size();i++){
						k.elemento.get(i).x--;
					}
				}
				while(k.elemento.get(0).y<yInicio){
					for(int i=0;i<k.elemento.size();i++){
						k.elemento.get(i).y++;
					}
				}
				while(k.elemento.get(0).y>yInicio){
					for(int i=0;i<k.elemento.size();i++){
						k.elemento.get(i).y--;
					}
				}
				/*
				Plot dos pontos
				*/
				for(int i=0;i<k.elemento.size();i++){
					g.setColor(Color.BLACK);
					g.drawLine(k.elemento.get(i).x,k.elemento.get(i).y,k.elemento.get(i).x,k.elemento.get(i).y);
				}
				rotacionar=false;
				}
			}
		}
		else if(reflexao){
			if(click){
				Point pointer = getMousePosition();
				for(Celula i=quadro.primeiro;i!=null;i=i.prox){
					for(int j=0;j<i.elemento.size();j++){
						if((i.elemento.get(j).x<=pointer.x+2&&i.elemento.get(j).x>=pointer.x-2)&&(i.elemento.get(j).y<=pointer.y+2&&i.elemento.get(j).y>=pointer.y-2)){
							j=i.elemento.size();
							k=i;
							i=null;
							click=false;
							refletir=true;
						}
					}
				}
			}
			else if(refletir){
				xInicio=k.elemento.get(0).x;
				yInicio=k.elemento.get(0).y;
				xFim=k.elemento.get(k.elemento.size()-1).x;
				yFim=k.elemento.get(k.elemento.size()-1).y;
				for(int i=0;i<k.elemento.size();i++){
					g.setColor(Color.WHITE);
					g.drawLine(k.elemento.get(i).x,k.elemento.get(i).y,k.elemento.get(i).x,k.elemento.get(i).y);
				}
				for(int i=0;i<k.elemento.size();i++){
					int xAux=(int)((k.elemento.get(i).x*Math.cos(Math.PI))+(k.elemento.get(i).y*-(Math.sin(Math.PI))));
					int yAux=(int)((k.elemento.get(i).x*Math.sin(Math.PI))+(k.elemento.get(i).y*Math.cos(Math.PI)));
					k.elemento.get(i).x=xAux;
					k.elemento.get(i).y=yAux;
				}
				/*
				Reposicionameno do desenho
				*/
				while(k.elemento.get(0).x<xInicio){
					for(int i=0;i<k.elemento.size();i++){
						k.elemento.get(i).x++;
					}
				}
				while(k.elemento.get(0).x>xInicio){
					for(int i=0;i<k.elemento.size();i++){
						k.elemento.get(i).x--;
					}
				}
				while(k.elemento.get(0).y<yInicio){
					for(int i=0;i<k.elemento.size();i++){
						k.elemento.get(i).y++;
					}
				}
				while(k.elemento.get(0).y>yInicio){
					for(int i=0;i<k.elemento.size();i++){
						k.elemento.get(i).y--;
					}
				}
				/*
				Plot dos pontos
				*/
				for(int i=0;i<k.elemento.size();i++){
					g.setColor(Color.BLACK);
					g.drawLine(k.elemento.get(i).x,k.elemento.get(i).y,k.elemento.get(i).x,k.elemento.get(i).y);
				}
				refletir=false;
			}
		}
		else if(escala){
			int xInicio=0,yInicio=0;
			if(click){
				Point pointer = getMousePosition();
				for(Celula i=quadro.primeiro;i!=null;i=i.prox){
					for(int j=0;j<i.elemento.size();j++){
						if((i.elemento.get(j).x<=pointer.x+2&&i.elemento.get(j).x>=pointer.x-2)&&(i.elemento.get(j).y<=pointer.y+2&&i.elemento.get(j).y>=pointer.y-2)){
							j=i.elemento.size();
							k=i;
							i=null;
							click=false;
						}
					}
				}
			}
			else{
				xInicio=k.elemento.get(0).x;
				yInicio=k.elemento.get(0).y;
				if(mais){
					for(int i=0;i<k.elemento.size();i++){
						g.setColor(Color.WHITE);
						g.drawLine(k.elemento.get(i).x,k.elemento.get(i).y,k.elemento.get(i).x,k.elemento.get(i).y);
					}
					for(int i=0;i<k.elemento.size();i++){
						k.elemento.get(i).x=(int)(k.elemento.get(i).x*1.5);
						k.elemento.get(i).y=(int)(k.elemento.get(i).y*1.5);
						if(k.tipo==1){
							k.xc=(int)(k.xc*1.5);
							k.yc=(int)(k.yc*1.5);
						}
					}
					mais=false;
				}
				else if(menos){
					for(int i=0;i<k.elemento.size();i++){
						g.setColor(Color.WHITE);
						g.drawLine(k.elemento.get(i).x,k.elemento.get(i).y,k.elemento.get(i).x,k.elemento.get(i).y);
					}
					for(int i=0;i<k.elemento.size();i++){
						k.elemento.get(i).x=(int)(k.elemento.get(i).x*0.5);
						k.elemento.get(i).y=(int)(k.elemento.get(i).y*0.5);
						if(k.tipo==1){
							k.xc=(int)(k.xc*0.5);
							k.yc=(int)(k.yc*0.5);
						}
					}
					menos=false;
				}
				/*
				Reposicionameno do desenho
				*/
				if(k.tipo!=1){
					while(k.elemento.get(0).x<xInicio){
						for(int i=0;i<k.elemento.size();i++){
							k.elemento.get(i).x++;
						}
					}
					while(k.elemento.get(0).x>xInicio){
						for(int i=0;i<k.elemento.size();i++){
							k.elemento.get(i).x--;
						}
					}
					while(k.elemento.get(0).y<yInicio){
						for(int i=0;i<k.elemento.size();i++){
							k.elemento.get(i).y++;
						}
					}
					while(k.elemento.get(0).y>yInicio){
						for(int i=0;i<k.elemento.size();i++){
							k.elemento.get(i).y--;
						}
					}
				}
				for(int i=0;i<k.elemento.size();i++){
					g.setColor(Color.BLACK);
					g.drawLine(k.elemento.get(i).x,k.elemento.get(i).y,k.elemento.get(i).x,k.elemento.get(i).y);
				}
			}
		}
		else if(desenho_livre){
			for(int i=1;i<desenhos.size();i++){
				int x = desenhos.get(i).x;
				int y = desenhos.get(i).y;
				int x2 = desenhos.get(i-1).x;
				int y2 = desenhos.get(i-1).y;
				g.drawLine(x2,y2,x,y);
			}
		}
		else if(borracha){
			for(int i=1;i<desenhos.size();i++){
				int x = desenhos.get(i).x;
				int y = desenhos.get(i).y;
				g.clearRect(x, y, 50, 50);
			}
		}
		else if(!pressionado){
			if(posicaoRecorte){
				desenhosPermanentes.add(new posicoes(desenhos.get(0),desenhos.get(desenhos.size()-1)));
				posicaoRecorte = false;
			}
			int x1=desenhos.get(0).x, y1=desenhos.get(0).y,x2=desenhos.get(desenhos.size()-1).x,y2=desenhos.get(desenhos.size()-1).y,dx,dy;
			if(dda_abilitado){
				if(!desenhos.isEmpty()){
					ArrayList<desenho> tmp = new ArrayList<>();
					quadro.inserir(tmp);
					desenhos.clear();
				}
				int passos,k;
				float x_incr,y_incr,x,y;
				dx=x2-x1;
				dy=y2-y1;
				if(Math.abs(dx)>Math.abs(dy)){
					passos=Math.abs(dx);
				}
				else{
					passos=Math.abs(dy);
				}
				x_incr=dx/passos;
				y_incr=dy/passos;
				x=x1;
				y=y1;
				g.drawLine((int)(Math.round(x)/1.0),(int)(Math.round(y)/1.0),(int)(Math.round(x)/1.0),(int)(Math.round(y)/1.0));
				quadro.ultimo.elemento.add(new desenho((int)(Math.round(x)/1.0),(int)(Math.round(y)/1.0)));
				for(k=1;k<=passos;k++){
					x=x+x_incr;
					y=y+y_incr;
					quadro.ultimo.elemento.add(new desenho((int)(Math.round(x)/1.0),(int)(Math.round(y)/1.0)));
					g.drawLine((int)(Math.round(x)/1.0),(int)(Math.round(y)/1.0),(int)(Math.round(x)/1.0),(int)(Math.round(y)/1.0));
				}
			}
			else if(bresenhan_abilitado){
				if(!desenhos.isEmpty()){
					ArrayList<desenho> tmp = new ArrayList<>();
					quadro.inserir(tmp);
					desenhos.clear();
				}
				int x,y,const1,const2,p,incrx,incry,i;
				dx=x2-x1;
				dy=y2-y1;
				if(dx>=0){
					incrx=1;
				}
				else{
					incrx=-1;
					dx=-dx;
				}
				if(dy>=0){
					incry=1;
				}
				else{
					incry=-1;
					dy=-dy;
				}
				x=x1;
				y=y1;
				g.drawLine(x,y,x,y);
				quadro.ultimo.elemento.add(new desenho(x,y));
				if(dy<dx){
					p=2*dy-dx;
					const1=2*dy;
					const2=2*(dy-dx);
					for(i=0;i<dx;i++){
						x+=incrx;
						if(p<0){
							p+=const1;
						}
						else{
							y+=incry;
							p+=const2;
						}
						quadro.ultimo.elemento.add(new desenho(x,y));
						g.drawLine(x,y,x,y);
					}
				}
				else{
					p=2*dx-dy;
					const1=2*dx;
					const2=2*(dx-dy);
					for(i=0;i<dy;i++){
						y+=incry;
						if(p<0){
							p+=const1;
						}
						else{
							x+=incrx;
							p+=const2;
						}
						quadro.ultimo.elemento.add(new desenho(x,y));
						g.drawLine(x,y,x,y);
					}
				}
			}
			else if(circunferencia_abilitado){
				if(!desenhos.isEmpty()){
					ArrayList<desenho> tmp = new ArrayList<>();
					quadro.inserir(tmp);
				}
				int xc=desenhos.get(0).x,yc=desenhos.get(0).y,p,x=desenhos.get(desenhos.size()-1).x,y=desenhos.get(desenhos.size()-1).y,r;
				dx=x2-x1;
				dy=y2-y1;
				r=(int)(Math.sqrt((Math.pow(dx,2)+Math.pow(dy,2))));
				if(!desenhos.isEmpty()){
					inserePontos(xc,yc,x,y);
				}
				g.drawLine(xc+x,yc+y,xc+x,yc+y);
				g.drawLine(xc-x,yc+y,xc-x,yc+y);
				g.drawLine(xc+x,yc-y,xc+x,yc-y);
				g.drawLine(xc-x,yc-y,xc-x,yc-y);
				g.drawLine(xc+y,yc+x,xc+y,yc+x);
				g.drawLine(xc-y,yc+x,xc-y,yc+x);
				g.drawLine(xc+y,yc-x,xc+y,yc-x);
				g.drawLine(xc-y,yc-x,xc-y,yc-x);
				x=0;
				y=r;
				p=3-2*r;
				if(!desenhos.isEmpty()){
					inserePontos(xc,yc,x,y);
				}
				g.drawLine(xc+x,yc+y,xc+x,yc+y);
				g.drawLine(xc-x,yc+y,xc-x,yc+y);
				g.drawLine(xc+x,yc-y,xc+x,yc-y);
				g.drawLine(xc-x,yc-y,xc-x,yc-y);
				g.drawLine(xc+y,yc+x,xc+y,yc+x);
				g.drawLine(xc-y,yc+x,xc-y,yc+x);
				g.drawLine(xc+y,yc-x,xc+y,yc-x);
				g.drawLine(xc-y,yc-x,xc-y,yc-x);
				while(x<y){
					if(p<0){
						p=p+4*x+6;
					}
					else{
						p=p+4*(x-y)+10;
						y=y-1;
					}
					x=x+1;
					if(!desenhos.isEmpty()){
						inserePontos(xc,yc,x,y);
					}
					g.drawLine(xc+x,yc+y,xc+x,yc+y);
					g.drawLine(xc-x,yc+y,xc-x,yc+y);
					g.drawLine(xc+x,yc-y,xc+x,yc-y);
					g.drawLine(xc-x,yc-y,xc-x,yc-y);
					g.drawLine(xc+y,yc+x,xc+y,yc+x);
					g.drawLine(xc-y,yc+x,xc-y,yc+x);
					g.drawLine(xc+y,yc-x,xc+y,yc-x);
					g.drawLine(xc-y,yc-x,xc-y,yc-x);
				}
				if(!desenhos.isEmpty()){
					quadro.ultimo.tipo=1;
					quadro.ultimo.xc=xc;
					quadro.ultimo.yc=yc;
					desenhos.clear();
				}
			}
			else if(retangulo){
				if(!desenhos.isEmpty()){
					ArrayList<desenho> tmp = new ArrayList<>();
					quadro.inserir(tmp);
					desenhos.clear();
				}
				for(int i=0;i<Maior(x1,x2)-Menor(x1,x2);i++){
					g.drawLine(Menor(x1,x2)+i,Menor(y1,y2),Menor(x1,x2)+i,Menor(y1,y2));
					quadro.ultimo.elemento.add(new desenho(Menor(x1,x2)+i,Menor(y1,y2)));
				}
				for(int i=0;i<Maior(y1,y2)-Menor(y1,y2);i++){
					g.drawLine(Menor(x1,x2),Menor(y1,y2)+i,Menor(x1,x2),Menor(y1,y2)+i);
					quadro.ultimo.elemento.add(new desenho(Menor(x1,x2),Menor(y1,y2)+i));
				}
				for(int i=0;i<Maior(y1,y2)-Menor(y1,y2);i++){
					g.drawLine(Maior(x1,x2),Menor(y1,y2)+i,Maior(x1,x2),Menor(y1,y2)+i);
					quadro.ultimo.elemento.add(new desenho(Maior(x1,x2),Menor(y1,y2)+i));
				}
				for(int i=0;i<Maior(x1,x2)-Menor(x1,x2);i++){
					g.drawLine(Menor(x1,x2)+i,Maior(y1,y2),Menor(x1,x2)+i,Maior(y1,y2));
					quadro.ultimo.elemento.add(new desenho(Menor(x1,x2)+i,Maior(y1,y2)));
				}
				//g.drawRect(Menor(x1,x2), Menor(y1,y2), (Maior(x1,x2)-Menor(x1,x2)), (Maior(y1,y2)-Menor(y1,y2)));
			}
			else if(recorte){
				//Em cada desenho no quadro, os pontos que estiverem fora da área selecionada são removidos
				for(Celula c=quadro.primeiro;c!=null;c=c.prox){
					for(int i=0;i<c.elemento.size();i++){
						if(c.elemento.get(i).x<Menor(x1,x2)){
							c.elemento.remove(i);
						}
						else if(c.elemento.get(i).x>Maior(x1,x2)){
							c.elemento.remove(i);
						}
						if(c.elemento.get(i).y<Menor(y1,y2)){
							c.elemento.remove(i);
						}
						else if(c.elemento.get(i).y>Maior(y1,y2)){
							c.elemento.remove(i);
						}
					}
				}
				g.clearRect(0, 100, 1030, (Menor(y1,y2)-100)); //apaga região acima da área selecionada
				g.clearRect(0, Maior(y1,y2), 1030, (700-Maior(y1,y2))); //apaga a região abaixo da área selecionada
				g.clearRect(Maior(x1,x2), 100, (1030-Maior(x1,x2)), 600); //apaga a região à direita da área selecionada
				g.clearRect(0, 100, Menor(x1,x2), 600); //apaga a região à esquerda da área selecionada
			}
			else if(cohen){
				int cfora, xInt=0,yInt=0;
				xMin=Menor(x1,x2);
				yMin=Menor(y1,y2);
				xMax=Maior(x1,x2);
				yMax=Maior(y1,y2);
				for(Celula i=quadro.primeiro;i!=null;i=i.prox){
					x01=i.elemento.get(0).x;
					y01=i.elemento.get(0).y;
					x02=i.elemento.get(i.elemento.size()-1).x;
					y02=i.elemento.get(i.elemento.size()-1).y;
					boolean aceite=false, feito=false;
					while(!feito){
						int c1=regionCode(x01,y01);
						int c2=regionCode(x02,y02);
						if(c1==0 && c2==0){
							aceite=true;
							feito=true;
						}
						else if(c1!=0 && c2!=0){
							feito=true;
						}
						else{
							if(c1!=0){
								cfora=c1;
							}
							else{
								cfora=c2;
							}
							if(cfora==1||cfora==5||cfora==9){
								xInt=xMin;
								yInt=y01+(y02-y01)*(xMin-x01)/(x02-x01);
							}
							else if(cfora==2||cfora==10||cfora==6){
								xInt=xMax;
								yInt=y01+(y02-y01)*(xMax-x01)/(x02-x01);
							}
							else if(cfora==4||cfora==5||cfora==6){
								yInt=yMin;
								xInt=x01+(x02-x01)*(yMin-y01)/(y02-y01);
							}
							else if(cfora==8||cfora==9||cfora==10){
								yInt=yMax;
								xInt=x01+(x02-x01)*(yMax-y01)/(y02-y01);
							}
							if(c1==cfora){
								x01=xInt;
								y01=yInt;
							}
							else{
								x02=xInt;
								y02=yInt;
							}
						}
					}
					if(aceite){
						for(int j=0;j<i.elemento.size();j++){
							if(i.elemento.get(j).x<xMin){
								i.elemento.remove(j);
							}
							else if(i.elemento.get(j).x>xMax){
								i.elemento.remove(j);
							}
							if(i.elemento.get(j).y<yMin){
								i.elemento.remove(j);
							}
							else if(i.elemento.get(j).y>yMax){
								i.elemento.remove(j);
							}
						}
						g.setColor(Color.WHITE);
						g.drawLine(i.elemento.get(0).x,i.elemento.get(0).y,x01,y01);
						g.drawLine(x02,y02,i.elemento.get(i.elemento.size()-1).x,i.elemento.get(i.elemento.size()-1).y);
						g.setColor(Color.BLACK);
						g.drawLine(Math.round(x01),Math.round(y01),Math.round(x02),Math.round(y02));
					}
				}
			}
			else if(liang){
				xMin=Menor(x1,x2);
				yMin=Menor(y1,y2);
				xMax=Maior(x1,x2);
				yMax=Maior(y1,y2);
				u1=0.0;
				u2=1.0;
				for(Celula i=quadro.primeiro;i!=null;i=i.prox){
					dx=i.elemento.get(i.elemento.size()-1).x-i.elemento.get(0).x;
					dy=i.elemento.get(i.elemento.size()-1).y-i.elemento.get(0).y;
					if(cliptest(-dx,i.elemento.get(0).x-xMin,u1,u2)){
						if(cliptest(dx,xMax-i.elemento.get(0).x,u1,u2)){
							if(cliptest(-dy,i.elemento.get(0).y-yMin,u1,u2)){
								if(cliptest(dy,yMax-i.elemento.get(0).y,u1,u2)){
									if(u2<1.0){
										i.elemento.get(i.elemento.size()-1).x=(int)(i.elemento.get(0).x+u2*dx);
										i.elemento.get(i.elemento.size()-1).y=(int)(i.elemento.get(0).y+u2*dy);
									}
									if(u1>0.0){
										i.elemento.get(0).x=(int)(i.elemento.get(0).x+u1*dx);
										i.elemento.get(0).y=(int)(i.elemento.get(0).y+u1*dy);
									}
									g.drawLine(Math.round(i.elemento.get(0).x),Math.round(i.elemento.get(0).y),Math.round(i.elemento.get(i.elemento.size()-1).x),Math.round(i.elemento.get(i.elemento.size()-1).y));
								}
							}
						}
					}
				}
			}
			else if(rasterizacao){
				if(!desenhos.isEmpty()){
					ArrayList<desenho> tmp = new ArrayList<>();
					quadro.inserir(tmp);
					desenhos.clear();
				}
				float m=(y1-y2)/(x1-x2);
				float b=y1-(m*x1);
				float y;
				for(int x=Menor(x1,x2);x<Maior(x1,x2);x++){
					y=(m*x)+b;
					quadro.ultimo.elemento.add(new desenho(x,(int)(Math.round(y))));
					g.drawLine(x,(int)(Math.round(y)),x,(int)(Math.round(y)));
				}
			}
		}
	}

	public void inserePontos(int xc, int yc, int x, int y){
		quadro.ultimo.elemento.add(new desenho((xc+x),(yc+y)));
		quadro.ultimo.elemento.add(new desenho((xc-x),(yc+y)));
		quadro.ultimo.elemento.add(new desenho((xc+x),(yc-y)));
		quadro.ultimo.elemento.add(new desenho((xc-x),(yc-y)));
		quadro.ultimo.elemento.add(new desenho((xc+y),(yc+x)));
		quadro.ultimo.elemento.add(new desenho((xc-y),(yc+x)));
		quadro.ultimo.elemento.add(new desenho((xc+y),(yc-x)));
		quadro.ultimo.elemento.add(new desenho((xc-y),(yc-x)));
	}

	public int regionCode(int x, int y){
		int codigo=0;
		if(x<xMin){
			codigo=codigo+1;
		}
		if(x>xMax){
			codigo=codigo+2;
		}
		if(y<yMin){
			codigo=codigo+4;
		}
		if(y>yMax){
			codigo=codigo+8;
		}
		return codigo;
	}

	public int Maior(int x, int y){
		if(x>y){
			return x;
		}
		return y;
	}

	public int Menor(int x, int y){
		if(x<y){
			return x;
		}
		return y;
	}

	public boolean cliptest(int p,int q, double u1,double u2){
		boolean result=true;
		double r;
		if(p<0.0){
			r=q/p;
			if(r>u2){
				result=false;
			}
			else if(r>u1){
				u1=r;
			}
		}
		else if(p>0.0){
			r=q/p;
			if(r<u1){
				result=false;
			}
			else if(r<u2){
				u2=r;
			}
		}
		else if(q<0.0){
			result=false;
		}
		return result;
	}

	public class time extends Thread{
		public void run(){
			while(true){
				try{
					Point pointer = getMousePosition();
					if(pointer.y>=100){
						setMouse(mouse);
						if(pressionado){
							desenhos.add(new desenho(pointer.x,pointer.y));
							if(desenho_livre){
								btn_cohen.setVisible(false);
								btn_liang.setVisible(false);
								btn_translacao.setBounds(215,40,115,20);
								btn_rotacao.setBounds(340,40,95,20);
								btn_escala.setBounds(445,40,80,20);
								btn_reflexao.setBounds(535,40,95,20);
							}
						}
					}
					else{
						setMouse(0);
					}
				} catch(Exception erro){}
				repaint();
			}
		}
	}
	class Lista{
		Celula primeiro,ultimo;
	 
		public Lista(){
		   primeiro=ultimo=null;
		}
	 
		void inserir(ArrayList<desenho> x){
		   if(ultimo==null){
			  primeiro=ultimo=new Celula(x);
		   }
		   else{
			  ultimo.prox=new Celula(x);
			  ultimo=ultimo.prox;
		   }
		}

		void clear(){
			primeiro=ultimo=null;
		}
	 
		int tamanho(){
		   int resp=0;
		   for(Celula i=primeiro;i!=null;i=i.prox){
			  resp++;
		   }
		   return resp;
		}
	 }
	 
	 class Celula{
		ArrayList<desenho> elemento;
		Celula prox;
		int tipo,xc,yc;
	 
		public Celula(ArrayList <desenho> elemento){
			this.elemento=elemento;
			this.prox=null;
			tipo=0;
			xc=0;
			yc=0;
		}
	 }
}
