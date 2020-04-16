import javax.swing.JFrame;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
		setSize(1015,700);
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
	int mouse=0,xMin,xMax,yMin,yMax,xInicio=0,xFim=0,yInicio=0,yFim=0,x01,y01,x02,y02,cor=1;
	Celula k=null;
	double u1=0.0,u2=1.0;
	boolean salvar=false,load=true,reflexao=false,escala=false,refletir=false,mais=false,menos=false;
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
	JButton btn_preencher = new JButton("Preenchimento");
	JButton btn_vermelho = new JButton("");
	JButton btn_verde = new JButton("");
	JButton btn_preto = new JButton("");
	JButton btn_azul = new JButton("");
	JButton btn_amarelo = new JButton("");
	JButton btn_salvar = new JButton("Salvar");
	Lista quadro=new Lista();

	public Display(){
		//Ação realizada ao clicar no botão DDA
		btn_dda.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				load=false;
				desenhos.clear();
				dda_abilitado=true;
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
		//Ação realizada ao clicar no botão Bresenhan
		btn_bresenhan.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				load=false;
				desenhos.clear();
				dda_abilitado=false;
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
		//Ação realizada ao clicar no botão Circunferencia
		btn_circunferencia.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				load=false;
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
		//Ação realizada ao clicar no botão Desenho Livre
		btn_livre.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				load=false;
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
		//Ação realizada ao clicar no botão Borracha
		btn_borracha.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				load=false;
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
		//Ação realizada ao clicar no botão Retangulos
		btn_retangulo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				load=false;
				desenhos.clear();
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
		//Ação realizada ao clicar no botão Recorte
		btn_recorte.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				load=false;
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
		//Ação realizada ao clicar no botão Limpar Quadro
		btn_clear.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				load=false;
				quadro.clear();
				desenhos.clear();
				k=null;
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
		//Ação realizada ao clicar no botão Cohen-Sutherland
		btn_cohen.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				load=false;
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
		//Ação realizada ao clicar no botão Liang-Barsky
		btn_liang.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				load=false;
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
		//Ação realizada ao clicar no botão Rasterização de Retas
		btn_rasterizacao.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				load=false;
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
		//Ação realizada ao clicar no botão Translação
		btn_translacao.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				load=false;
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
		//Ação realizada ao clicar no botão OK
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
		//Ação realizada ao clicar no botão Rotação
		btn_rotacao.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				load=false;
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
		//Ação realizada ao clicar no botão Reflexão
		btn_reflexao.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				load=false;
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
		//Ação realizada ao clicar no botão Escala
		btn_escala.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				load=false;
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
		//Ação realizada ao clicar no botão Cima
		btn_cima.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				cima=true;
			}
		});
		//Ação realizada ao clicar no botão Baixo
		btn_baixo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				baixo=true;
			}
		});
		//Ação realizada ao clicar no botão Esquerda
		btn_esquerda.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				esquerda=true;
			}
		});
		//Ação realizada ao clicar no botão Direita
		btn_direita.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				direita=true;
			}
		});
		//Ação realizada ao clicar no botão Rotacionar
		btn_rotacionar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				rotacionar=true;
			}
		});
		//Ação realizada ao clicar no botão Mais
		btn_mais.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				mais=true;
			}
		});
		//Ação realizada ao clicar no botão Menos
		btn_menos.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				menos=true;
			}
		});
		//Ação realizada ao clicar no botão Salvar
		btn_salvar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				load=false;
				salvar=true;
			}
		});
		//Ação realizada ao clicar no botão vermelho
		btn_vermelho.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				load=false;
				cor=0;
			}
		});
		//Ação realizada ao clicar no botão preto
		btn_preto.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				load=false;
				cor=1;
			}
		});
		//Ação realizada ao clicar no botão verde
		btn_verde.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				load=false;
				cor=2;
			}
		});
		//Ação realizada ao clicar no botão azul
		btn_azul.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				load=false;
				cor=3;
			}
		});
		//Ação realizada ao clicar no botão amarelo
		btn_amarelo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				load=false;
				cor=4;
			}
		});
		setLayout(null);
		//Posicionamento dos botões
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
		btn_vermelho.setBounds(10,70,25,20);
		btn_preto.setBounds(45,70,25,20);
		btn_verde.setBounds(80,70,25,20);
		btn_azul.setBounds(115,70,25,20);
		btn_amarelo.setBounds(150,70,25,20);
		btn_cima.setBounds(185,70,70,20);
		btn_baixo.setBounds(265,70,75,20);
		btn_esquerda.setBounds(350,70,105,20);
		btn_direita.setBounds(465,70,85,20);
		btn_rotacionar.setBounds(560,70,115,20);
		btn_rotacao.setBounds(665,40,95,20);
		btn_escala.setBounds(770,40,80,20);
		btn_reflexao.setBounds(860,40,95,20);
		btn_mais.setBounds(685,70,70,20);
		btn_menos.setBounds(765,70,85,20);
		btn_okTransformacao.setBounds(860,70,55,20);
		btn_salvar.setBounds(925,70,80,20);
		//Inserindo os botões na janela
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
		add(btn_salvar);
		add(btn_vermelho);
		add(btn_preto);
		add(btn_verde);
		add(btn_azul);
		add(btn_amarelo);
		btn_vermelho.setBackground(Color.RED);
		btn_preto.setBackground(Color.BLACK);
		btn_verde.setBackground(Color.GREEN);
		btn_azul.setBackground(Color.BLUE);
		btn_amarelo.setBackground(Color.YELLOW);
		btn_cima.setVisible(false);
		btn_baixo.setVisible(false);
		btn_esquerda.setVisible(false);
		btn_direita.setVisible(false);
		btn_rotacionar.setVisible(false);
		btn_mais.setVisible(false);
		btn_menos.setVisible(false);
		btn_okTransformacao.setVisible(false);
		addMouseListener(new MouseListener(){
			/*
			Caso o mouse for pressionado, apagamos as referências do desenho anterior
			para que um não se conecte ao outro, e habilitamos a variável pressionado
			que irá fazer com que uma figura seja desenhada, apenas se o mouse for pressionado 
			*/
			public void mousePressed (MouseEvent e){
				pressionado=true;
				desenhos.clear();
			}
			/*
			Armazenamos o desenho livre apenas quando o mouse não está mais sendo pressionado,
			pois o desenho livre é o único tipo de desenho neste software que plota os pontos
			em tempo real. Ou seja, os outros métodos apenas irão plotar os desenhos
			depois que o mouse for pressionado e liberado
			*/
			public void mouseReleased (MouseEvent e){
				if(desenho_livre){
					quadro.inserir(desenhos);
					desenhos.clear();
				}
				pressionado=false;
			}
			public void mouseExited (MouseEvent e){
			}
			public void mouseEntered (MouseEvent e){
			}
			public void mouseClicked (MouseEvent e){
				Point p=getMousePosition();
				/*Métodos que utilizam a variável click para encontrar qual objeto
				o usuário deseja manipular
				*/
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
		new time().start();
	}
	/*
	Método usado para trocar o cursor do mouse por uma imagem. Usado para a borracha
	*/
	public void MudaCursor(String nomeImagem){
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage(nomeImagem);
		Point point =new Point(0,0);
		Cursor cursor = toolkit.createCustomCursor(img, point, "borracha");
		setCursor(cursor);
	}

	/*
	Método onde é realizada a troca do ícone do mouse, dependendo da opcao selecionada pelo usuário
	*/
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

	/*
	Classe usada para armazenar os pontos de cada desenho
	*/
	public class desenho{
		int x,y;
		public desenho(int x,int y){
			this.x=x;
			this.y=y;
		}
	}
	/*
	Método onde é feito os plots dos desenhos
	*/
	public void paintComponent(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1015, 100);
		/*
		Lê o arquivo quadro.txt e recupera os desenhos que foram salvos
		*/
		if(load){
			File file=new File("quadro.txt");
			String line=null;
			try{
				if(!file.exists()){
					file.createNewFile();
				}
				FileReader fr=new FileReader(file);
				BufferedReader br=new BufferedReader(fr);
				while((line=br.readLine())!=null){
					ArrayList<desenho> tmp = new ArrayList<>();
					quadro.inserir(tmp);
					quadro.ultimo.cor=Integer.parseInt(line);
					line=br.readLine();
					String coordenadas[]=line.split(" ");
					for(int i=0;i<coordenadas.length;i++){
						String pontos[]=coordenadas[i].split(",");
						quadro.ultimo.elemento.add(new desenho(Integer.parseInt(pontos[0]),Integer.parseInt(pontos[1])));
						if(quadro.ultimo.cor==0){
							g.setColor(Color.RED);
						}
						else if(quadro.ultimo.cor==1){
							g.setColor(Color.BLACK);
						}
						else if(quadro.ultimo.cor==2){
							g.setColor(Color.GREEN);
						}
						else if(quadro.ultimo.cor==3){
							g.setColor(Color.BLUE);
						}
						else if(quadro.ultimo.cor==4){
							g.setColor(Color.YELLOW);
						}
						g.drawLine(Integer.parseInt(pontos[0]),Integer.parseInt(pontos[1]),Integer.parseInt(pontos[0]),Integer.parseInt(pontos[1]));
					}
				}
				br.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		/*
		Caso o botão limpar quadro for selecionado, a opção clear irá limpar o quadro
		*/
		else if(clear){
			g.clearRect(0, 100, 1015, 600);
			btn_translacao.setBounds(540,40,115,20);
			btn_rotacao.setBounds(665,40,95,20);
			btn_escala.setBounds(770,40,80,20);
			btn_reflexao.setBounds(860,40,95,20);
			load=false;
			clear=false;
		}
		/*
		Caso o botão translação for selecionado, executaremos o if abaixo
		*/
		else if(translacao){
			int passos=5;
			/*
			Caso o usuário clique na tela (dentro da região válida), ou seja,
			dentro da janela de desenho e abaixo da região dos botões
			*/
			if(click){
				Point pointer = getMousePosition();
				/*
				Buscamos o objeto selecionado e que será transladado
				*/
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
				/*
				Move o objeto para cima
				*/
				if(cima){
					/*Colorimos os pontos do objeto em questão de branco
					antes de caminharmos com eles*/
					for(int i=0;i<k.elemento.size();i++){
						g.setColor(Color.WHITE);
						g.drawLine(k.elemento.get(i).x,k.elemento.get(i).y,k.elemento.get(i).x,k.elemento.get(i).y);
					}
					for(int i=0;i<k.elemento.size();i++){
						k.elemento.get(i).y=k.elemento.get(i).y-passos;
						//o desenho é plotado de acordo com a cor do objeto
						if(k.cor==0){
							g.setColor(Color.RED);
						}
						else if(k.cor==1){
							g.setColor(Color.BLACK);
						}
						else if(k.cor==2){
							g.setColor(Color.GREEN);
						}
						else if(k.cor==3){
							g.setColor(Color.BLUE);
						}
						else if(k.cor==4){
							g.setColor(Color.YELLOW);
						}
						g.drawLine(k.elemento.get(i).x,k.elemento.get(i).y,k.elemento.get(i).x,k.elemento.get(i).y);
					}
					cima=false;
				}
				/*
				Move o objeto para baixo
				*/
				else if(baixo){
					/*Colorimos os pontos do objeto em questão de branco
					antes de caminharmos com eles*/
					for(int i=0;i<k.elemento.size();i++){
						g.setColor(Color.WHITE);
						g.drawLine(k.elemento.get(i).x,k.elemento.get(i).y,k.elemento.get(i).x,k.elemento.get(i).y);
					}
					for(int i=0;i<k.elemento.size();i++){
						k.elemento.get(i).y=k.elemento.get(i).y+passos;
						//o desenho é plotado de acordo com a cor do objeto
						if(k.cor==0){
							g.setColor(Color.RED);
						}
						else if(k.cor==1){
							g.setColor(Color.BLACK);
						}
						else if(k.cor==2){
							g.setColor(Color.GREEN);
						}
						else if(k.cor==3){
							g.setColor(Color.BLUE);
						}
						else if(k.cor==4){
							g.setColor(Color.YELLOW);
						}
						g.drawLine(k.elemento.get(i).x,k.elemento.get(i).y,k.elemento.get(i).x,k.elemento.get(i).y);
					}
					baixo=false;
				}
				/*
				Move o objeto para direita
				*/
				else if(direita){
					/*Colorimos os pontos do objeto em questão de branco
					antes de caminharmos com eles*/
					for(int i=0;i<k.elemento.size();i++){
						g.setColor(Color.WHITE);
						g.drawLine(k.elemento.get(i).x,k.elemento.get(i).y,k.elemento.get(i).x,k.elemento.get(i).y);
					}
					for(int i=0;i<k.elemento.size();i++){
						k.elemento.get(i).x=k.elemento.get(i).x+passos;
						//o desenho é plotado de acordo com a cor do objeto
						if(k.cor==0){
							g.setColor(Color.RED);
						}
						else if(k.cor==1){
							g.setColor(Color.BLACK);
						}
						else if(k.cor==2){
							g.setColor(Color.GREEN);
						}
						else if(k.cor==3){
							g.setColor(Color.BLUE);
						}
						else if(k.cor==4){
							g.setColor(Color.YELLOW);
						}
						g.drawLine(k.elemento.get(i).x,k.elemento.get(i).y,k.elemento.get(i).x,k.elemento.get(i).y);
					}
					direita=false;
				}
				/*
				Move o objeto para esquerda
				*/
				else if(esquerda){
					/*Colorimos os pontos do objeto em questão de branco
					antes de caminharmos com eles*/
					for(int i=0;i<k.elemento.size();i++){
						g.setColor(Color.WHITE);
						g.drawLine(k.elemento.get(i).x,k.elemento.get(i).y,k.elemento.get(i).x,k.elemento.get(i).y);
					}
					for(int i=0;i<k.elemento.size();i++){
						k.elemento.get(i).x=k.elemento.get(i).x-passos;
						//o desenho é plotado de acordo com a cor do objeto
						if(k.cor==0){
							g.setColor(Color.RED);
						}
						else if(k.cor==1){
							g.setColor(Color.BLACK);
						}
						else if(k.cor==2){
							g.setColor(Color.GREEN);
						}
						else if(k.cor==3){
							g.setColor(Color.BLUE);
						}
						else if(k.cor==4){
							g.setColor(Color.YELLOW);
						}
						g.drawLine(k.elemento.get(i).x,k.elemento.get(i).y,k.elemento.get(i).x,k.elemento.get(i).y);
					}
					esquerda=false;
				}
			}
		}
		/*
		Caso o botão rotação for selecionado, executaremos o if abaixo
		*/
		else if(rotacao){
			/*
			Caso o usuário clique na tela (dentro da região válida), ou seja,
			dentro da janela de desenho e abaixo da região dos botões
			*/
			if(click){
				Point pointer = getMousePosition();
				/*
				Buscamos o objeto selecionado e que será rotacionado
				*/
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
				/*
				Rotaciona o objeto
				*/
				if(rotacionar){
					/*Colorimos os pontos do objeto em questão de branco
					antes de rotacionarmos eles*/
					for(int i=0;i<k.elemento.size();i++){
						g.setColor(Color.WHITE);
						g.drawLine(k.elemento.get(i).x,k.elemento.get(i).y,k.elemento.get(i).x,k.elemento.get(i).y);
					}
					//Rotação em 15 graus
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
						//o desenho é plotado de acordo com a cor do objeto
						if(k.cor==0){
							g.setColor(Color.RED);
						}
						else if(k.cor==1){
							g.setColor(Color.BLACK);
						}
						else if(k.cor==2){
							g.setColor(Color.GREEN);
						}
						else if(k.cor==3){
							g.setColor(Color.BLUE);
						}
						else if(k.cor==4){
							g.setColor(Color.YELLOW);
						}
						g.drawLine(k.elemento.get(i).x,k.elemento.get(i).y,k.elemento.get(i).x,k.elemento.get(i).y);
					}
					rotacionar=false;
				}
			}
		}
		/*
		Caso o botão reflexão for selecionado, executaremos o if abaixo
		*/
		else if(reflexao){
			/*
			Caso o usuário clique na tela (dentro da região válida), ou seja,
			dentro da janela de desenho e abaixo da região dos botões
			*/
			if(click){
				Point pointer = getMousePosition();
				/*
				Buscamos o objeto selecionado e que será refletido
				*/
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
			/*
			Reflete o objeto
			*/
			else if(refletir){
				xInicio=k.elemento.get(0).x;
				yInicio=k.elemento.get(0).y;
				xFim=k.elemento.get(k.elemento.size()-1).x;
				yFim=k.elemento.get(k.elemento.size()-1).y;
				/*Colorimos os pontos do objeto em questão de branco
				antes de refletirmos eles*/
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
					//o desenho é plotado de acordo com a cor do objeto
					if(k.cor==0){
						g.setColor(Color.RED);
					}
					else if(k.cor==1){
						g.setColor(Color.BLACK);
					}
					else if(k.cor==2){
						g.setColor(Color.GREEN);
					}
					else if(k.cor==3){
						g.setColor(Color.BLUE);
					}
					else if(k.cor==4){
						g.setColor(Color.YELLOW);
					}
					g.drawLine(k.elemento.get(i).x,k.elemento.get(i).y,k.elemento.get(i).x,k.elemento.get(i).y);
				}
				refletir=false;
			}
		}
		/*
		Caso o botão escala for selecionado, executaremos o if abaixo
		*/
		else if(escala){
			int xInicio=0,yInicio=0;
			/*
			Caso o usuário clique na tela (dentro da região válida), ou seja,
			dentro da janela de desenho e abaixo da região dos botões
			*/
			if(click){
				Point pointer = getMousePosition();
				/*
				Buscamos o objeto desejado
				*/
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
				/*
				Aumenta o tamanho do objeto
				*/
				if(mais){
					/*Colorimos os pontos do objeto em questão de branco
					antes de aumentarmos eles*/
					g.setColor(Color.WHITE);
					for(int i=0;i<k.elemento.size();i++){
						g.drawLine(k.elemento.get(i).x,k.elemento.get(i).y,k.elemento.get(i).x,k.elemento.get(i).y);
					}
					//O objeto é aumentado em mais 50% de seu tamanho inicial
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
				/*
				Diminui o tamanho do objeto
				*/
				else if(menos){
					/*Colorimos os pontos do objeto em questão de branco
					antes de diminuirmos eles*/
					g.setColor(Color.WHITE);
					for(int i=0;i<k.elemento.size();i++){
						g.drawLine(k.elemento.get(i).x,k.elemento.get(i).y,k.elemento.get(i).x,k.elemento.get(i).y);
					}
					//Reduzimos o objeto pela metade
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
				/*
				Plot dos pontos
				*/
				for(int i=0;i<k.elemento.size();i++){
					//o desenho é plotado de acordo com a cor do objeto
					if(k.cor==0){
						g.setColor(Color.RED);
					}
					else if(k.cor==1){
						g.setColor(Color.BLACK);
					}
					else if(k.cor==2){
						g.setColor(Color.GREEN);
					}
					else if(k.cor==3){
						g.setColor(Color.BLUE);
					}
					else if(k.cor==4){
						g.setColor(Color.YELLOW);
					}
					g.drawLine(k.elemento.get(i).x,k.elemento.get(i).y,k.elemento.get(i).x,k.elemento.get(i).y);
				}
			}
		}
		/*
		O algoritmo inicia executando este método, e caso o botão desenho livre
		for selecionado, executaremos o if abaixo
		*/
		else if(desenho_livre){
			for(int i=1;i<desenhos.size();i++){
				int x = desenhos.get(i).x;
				int y = desenhos.get(i).y;
				int x2 = desenhos.get(i-1).x;
				int y2 = desenhos.get(i-1).y;
				//o desenho é plotado de acordo com a cor desejada
				if(cor==0){
					g.setColor(Color.RED);
				}
				else if(cor==1){
					g.setColor(Color.BLACK);
				}
				else if(cor==2){
					g.setColor(Color.GREEN);
				}
				else if(cor==3){
					g.setColor(Color.BLUE);
				}
				else if(cor==4){
					g.setColor(Color.YELLOW);
				}
				g.drawLine(x2,y2,x,y);
			}
		}
		/*
		Caso o botão borracha for selecionado, executaremos o if abaixo
		*/
		else if(borracha){
			for(int i=1;i<desenhos.size();i++){
				int x = desenhos.get(i).x;
				int y = desenhos.get(i).y;
				g.clearRect(x, y, 50, 50);
			}
		}
		/*
		Executamos os if's abaixo somente se o botão do mouse não estiver pressionado,
		pois coletamo os pontos que serão usados, e o tamanho das retas (DDA e Bresenhan)
		além de coletarmos o tamanho desejado para a circunferencia, retangulo e área para
		recorte
		*/
		else if(!pressionado){
			int x1=desenhos.get(0).x, y1=desenhos.get(0).y,x2=desenhos.get(desenhos.size()-1).x,y2=desenhos.get(desenhos.size()-1).y,dx,dy;
			/*
			Caso o botão DDA for selecionado, executaremos o if abaixo
			*/
			if(dda_abilitado){
				/*Com o intuito de evitar alocar inúmeros desenhos vazios na lista,
				verificamos se o array list desenhos encontra-se preenchido
				e alocamos um novo elemento na lista de desenhos*/
				if(!desenhos.isEmpty()){
					ArrayList<desenho> tmp = new ArrayList<>();
					quadro.inserir(tmp);
					quadro.ultimo.cor=cor;
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
				//o desenho é plotado de acordo com a cor desejada
				if(cor==0){
					g.setColor(Color.RED);
				}
				else if(cor==1){
					g.setColor(Color.BLACK);
				}
				else if(cor==2){
					g.setColor(Color.GREEN);
				}
				else if(cor==3){
					g.setColor(Color.BLUE);
				}
				else if(cor==4){
					g.setColor(Color.YELLOW);
				}
				//plot do ponto
				g.drawLine((int)(Math.round(x)/1.0),(int)(Math.round(y)/1.0),(int)(Math.round(x)/1.0),(int)(Math.round(y)/1.0));
				//armazenamos o ponto na lista de desenhos
				quadro.ultimo.elemento.add(new desenho((int)(Math.round(x)/1.0),(int)(Math.round(y)/1.0)));
				for(k=1;k<=passos;k++){
					x=x+x_incr;
					y=y+y_incr;
					//armazenamos o ponto na lista de desenhos
					quadro.ultimo.elemento.add(new desenho((int)(Math.round(x)/1.0),(int)(Math.round(y)/1.0)));
					//plot do ponto
					g.drawLine((int)(Math.round(x)/1.0),(int)(Math.round(y)/1.0),(int)(Math.round(x)/1.0),(int)(Math.round(y)/1.0));
				}
			}
			/*
			Caso o botão Bresenhan for selecionado, executaremos o if abaixo
			*/
			else if(bresenhan_abilitado){
				/*Com o intuito de evitar alocar inúmeros desenhos vazios na lista,
				verificamos se o array list desenhos encontra-se preenchido
				e alocamos um novo elemento na lista de desenhos*/
				if(!desenhos.isEmpty()){
					ArrayList<desenho> tmp = new ArrayList<>();
					quadro.inserir(tmp);
					quadro.ultimo.cor=cor;
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
				//o desenho é plotado de acordo com a cor desejada
				if(cor==0){
					g.setColor(Color.RED);
				}
				else if(cor==1){
					g.setColor(Color.BLACK);
				}
				else if(cor==2){
					g.setColor(Color.GREEN);
				}
				else if(cor==3){
					g.setColor(Color.BLUE);
				}
				else if(cor==4){
					g.setColor(Color.YELLOW);
				}
				//plot do ponto
				g.drawLine(x,y,x,y);
				//armazenamos o ponto na lista de desenhos
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
						//armazenamos o ponto na lista de desenhos
						quadro.ultimo.elemento.add(new desenho(x,y));
						//plot do ponto
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
						//armazenamos o ponto na lista de desenhos
						quadro.ultimo.elemento.add(new desenho(x,y));
						//plot do ponto
						g.drawLine(x,y,x,y);
					}
				}
			}
			/*
			Caso o botão Circunferencia for selecionado, executaremos o if abaixo
			*/
			else if(circunferencia_abilitado){
				/*Com o intuito de evitar alocar inúmeros desenhos vazios na lista,
				verificamos se o array list desenhos encontra-se preenchido
				e alocamos um novo elemento na lista de desenhos*/
				if(!desenhos.isEmpty()){
					ArrayList<desenho> tmp = new ArrayList<>();
					quadro.inserir(tmp);
					quadro.ultimo.cor=cor;
				}
				int xc=desenhos.get(0).x,yc=desenhos.get(0).y,p,x=desenhos.get(desenhos.size()-1).x,y=desenhos.get(desenhos.size()-1).y,r;
				dx=x2-x1;
				dy=y2-y1;
				r=(int)(Math.sqrt((Math.pow(dx,2)+Math.pow(dy,2))));
				if(!desenhos.isEmpty()){
					//armazenamos os pontos na lista de desenhos
					inserePontos(xc,yc,x,y);
				}
				//o desenho é plotado de acordo com a cor desejada
				if(cor==0){
					g.setColor(Color.RED);
				}
				else if(cor==1){
					g.setColor(Color.BLACK);
				}
				else if(cor==2){
					g.setColor(Color.GREEN);
				}
				else if(cor==3){
					g.setColor(Color.BLUE);
				}
				else if(cor==4){
					g.setColor(Color.YELLOW);
				}
				//plot dos pontos
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
					//armazenamos os pontos na lista de desenhos
					inserePontos(xc,yc,x,y);
				}
				//plot dos pontos
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
						//armazenamos os pontos na lista de desenhos
						inserePontos(xc,yc,x,y);
					}
					//plot dos pontos
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
			/*
			Caso o botão Retangulos for selecionado, executaremos o if abaixo
			*/
			else if(retangulo){
				/*Com o intuito de evitar alocar inúmeros desenhos vazios na lista,
				verificamos se o array list desenhos encontra-se preenchido
				e alocamos um novo elemento na lista de desenhos*/
				if(!desenhos.isEmpty()){
					ArrayList<desenho> tmp = new ArrayList<>();
					quadro.inserir(tmp);
					quadro.ultimo.cor=cor;
					desenhos.clear();
				}
				//o desenho é plotado de acordo com a cor desejada
				if(cor==0){
					g.setColor(Color.RED);
				}
				else if(cor==1){
					g.setColor(Color.BLACK);
				}
				else if(cor==2){
					g.setColor(Color.GREEN);
				}
				else if(cor==3){
					g.setColor(Color.BLUE);
				}
				else if(cor==4){
					g.setColor(Color.YELLOW);
				}
				for(int i=0;i<Maior(x1,x2)-Menor(x1,x2);i++){
					//plot do ponto
					g.drawLine(Menor(x1,x2)+i,Menor(y1,y2),Menor(x1,x2)+i,Menor(y1,y2));
					//armazenamos o ponto da lista de desenhos
					quadro.ultimo.elemento.add(new desenho(Menor(x1,x2)+i,Menor(y1,y2)));
				}
				for(int i=0;i<Maior(y1,y2)-Menor(y1,y2);i++){
					//plot do ponto
					g.drawLine(Menor(x1,x2),Menor(y1,y2)+i,Menor(x1,x2),Menor(y1,y2)+i);
					//armazenamos o ponto na lista de desenhos
					quadro.ultimo.elemento.add(new desenho(Menor(x1,x2),Menor(y1,y2)+i));
				}
				for(int i=0;i<Maior(y1,y2)-Menor(y1,y2);i++){
					//plot do ponto
					g.drawLine(Maior(x1,x2),Menor(y1,y2)+i,Maior(x1,x2),Menor(y1,y2)+i);
					//armazenamos o ponto na lista de desnhos
					quadro.ultimo.elemento.add(new desenho(Maior(x1,x2),Menor(y1,y2)+i));
				}
				for(int i=0;i<Maior(x1,x2)-Menor(x1,x2);i++){
					//plot do ponto
					g.drawLine(Menor(x1,x2)+i,Maior(y1,y2),Menor(x1,x2)+i,Maior(y1,y2));
					//armazenamos o ponto na lista de desenhos
					quadro.ultimo.elemento.add(new desenho(Menor(x1,x2)+i,Maior(y1,y2)));
				}
			}
			/*
			Caso o botão Recorte for selecionado, executaremos o if abaixo
			*/
			else if(recorte){
				/*Em cada desenho no quadro, os pontos que estiverem fora
				da área selecionada são removidos*/
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
				//apaga região acima da área selecionada
				g.clearRect(0, 100, 1015, (Menor(y1,y2)-100));
				//apaga a região abaixo da área selecionada
				g.clearRect(0, Maior(y1,y2), 1015, (700-Maior(y1,y2)));
				//apaga a região à direita da área selecionada
				g.clearRect(Maior(x1,x2), 100, (1015-Maior(x1,x2)), 600);
				//apaga a região à esquerda da área selecionada
				g.clearRect(0, 100, Menor(x1,x2), 600);
			}
			/*
			Caso o botão Cohen-Sutherland for selecionado, executaremos o if abaixo
			*/
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
						//colore de branco, o segmento de reta que deve ser retirado
						g.setColor(Color.WHITE);
						g.drawLine(i.elemento.get(0).x,i.elemento.get(0).y,x01,y01);
						g.drawLine(x02,y02,i.elemento.get(i.elemento.size()-1).x,i.elemento.get(i.elemento.size()-1).y);
						//o desenho é plotado de acordo com a cor do objeto
						if(i.cor==0){
							g.setColor(Color.RED);
						}
						else if(i.cor==1){
							g.setColor(Color.BLACK);
						}
						else if(i.cor==2){
							g.setColor(Color.GREEN);
						}
						else if(i.cor==3){
							g.setColor(Color.BLUE);
						}
						else if(i.cor==4){
							g.setColor(Color.YELLOW);
						}
						//plot da reta
						g.drawLine(Math.round(x01),Math.round(y01),Math.round(x02),Math.round(y02));
					}
				}
			}
			/*
			Caso o botão Liang-Barsky for selecionado, executaremos o if abaixo
			*/
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
			/*
			Caso o botão Rasterização de Retas for selecionado, executaremos o if abaixo
			*/
			else if(rasterizacao){
				/*Com o intuito de evitar alocar inúmeros desenhos vazios na lista,
				verificamos se o array list desenhos encontra-se preenchido
				e alocamos um novo elemento na lista de desenhos*/
				if(!desenhos.isEmpty()){
					ArrayList<desenho> tmp = new ArrayList<>();
					quadro.inserir(tmp);
					quadro.ultimo.cor=cor;
					desenhos.clear();
				}
				float m=(y1-y2)/(x1-x2);
				float b=y1-(m*x1);
				float y;
				//o desenho é plotado de acordo com a cor desejada
				if(cor==0){
					g.setColor(Color.RED);
				}
				else if(cor==1){
					g.setColor(Color.BLACK);
				}
				else if(cor==2){
					g.setColor(Color.GREEN);
				}
				else if(cor==3){
					g.setColor(Color.BLUE);
				}
				else if(cor==4){
					g.setColor(Color.YELLOW);
				}
				for(int x=Menor(x1,x2);x<Maior(x1,x2);x++){
					y=(m*x)+b;
					//armazena o ponto na lista de desenhos
					quadro.ultimo.elemento.add(new desenho(x,(int)(Math.round(y))));
					//plot do ponto
					g.drawLine(x,(int)(Math.round(y)),x,(int)(Math.round(y)));
				}
			}
		}
	}

	/*
	Método usado para armazenar os pontos da circunferencia na lista de desenhos
	*/
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

	/*
	Método usado pelo Cohen-Sutherland para verificar a região onde se encontra um
	determinado ponto do desenho
	*/
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

	/*
	Método usado para extrairmos o maior valor entre dois números
	*/
	public int Maior(int x, int y){
		if(x>y){
			return x;
		}
		return y;
	}

	/*
	Método usado para extrairmos o menor valor entre dois números
	*/
	public int Menor(int x, int y){
		if(x<y){
			return x;
		}
		return y;
	}

	/*
	Método usado pelo Liang-Barsky para o recorte do desenho
	*/
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

	/*
	Método usado para armazenarmos todos os pontos de todos os desenhos num arquivo
	que será lido pelo load, toda vez que o paint for executado
	*/
	public void save(){
		File file=new File("quadro.txt");
		try{
			if(!file.exists()){
				file.delete();
			}
			FileWriter fw=new FileWriter(file);
			BufferedWriter bw=new BufferedWriter(fw);
			for(Celula i=quadro.primeiro;i!=null;i=i.prox){
				//A primeira linha refere-se à cor do objeto
				bw.write(i.cor+"\n");
				for(int j=0;j<i.elemento.size();j++){
					//Armazena-se a seguir, todos os pontos do objeto 
					bw.write(i.elemento.get(j).x+","+i.elemento.get(j).y+" ");
				}
				bw.write("\n");
			}
			bw.close();
		}catch(Exception erro){}
	}

	public class time extends Thread{
		public void run(){
			while(true){
				/*
				Caso o botão Salvar for selecionado, executaremos o if abaixo
				*/
				if(salvar){
					save();
					salvar=false;
				}
				try{
					Point pointer = getMousePosition();
					/*Somente salvamos os pontos válidos para um desenho. Ou seja, se o
					ponto onde o usuário quer desenhar, estiver abaixo da região dos botões
					*/
					if(pointer.y>=100){
						setMouse(mouse);
						//O desenho é feito apenas se o botão do mouse estiver pressionado
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
	/*
	Estrutura de dados utilizada para armazenamento dos desenhos: Lista encadeada
	*/
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

		/*A lista perde a referência de seus objetos para "deletar" os objetos
		até o coletor de lixo do java realmente deletá-los*/
		void clear(){
			primeiro=ultimo=null;
		}
		
		/*Retorna o tamanho da lista (quantidade de elementos armazenados)*/
		int tamanho(){
		   int resp=0;
		   for(Celula i=primeiro;i!=null;i=i.prox){
			  resp++;
		   }
		   return resp;
		}
	 }
	 
	 /*
	 Estrutura utilizada como se fosse o desenho propriamente dito, aqui são armazenados
	 os pontos de um determinado desenho e sua cor. No caso da circunferência, armazenamos
	 o ponto do centro da circunferência e seu tipo (representando ser um circunferência).
	 */
	 class Celula{
		ArrayList<desenho> elemento;
		Celula prox;
		int tipo,xc,yc,cor;
	 
		public Celula(ArrayList <desenho> elemento){
			this.elemento=elemento;
			this.prox=null;
			tipo=0;
			xc=0;
			yc=0;
			cor=1;
		}
	 }
}
