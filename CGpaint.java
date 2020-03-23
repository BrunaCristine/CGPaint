import javax.swing.JFrame;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;

class CGpaint extends JFrame{
	public CGpaint(){
		setTitle("CG Paint");
		setSize(1000,685);
		setLocationRelativeTo(null);
		setBackground(Color.WHITE);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args){
		new CGpaint().add(new Display());
	}
}

class Display extends JPanel{
	boolean pressionado=false,dda_abilitado=false,bresenhan_abilitado=false,circunferencia_abilitado=false,desenho_livre=true;
	JButton btn_dda = new JButton("DDA");
	JButton btn_bresenhan = new JButton("Bresenhan");
	JButton btn_circunferencia = new JButton("Circunferencia");
	JButton btn_livre = new JButton("Livre");

	public Display(){
		setBackground(Color.black);
		btn_dda.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dda_abilitado=true;
				bresenhan_abilitado=false;
				circunferencia_abilitado=false;
				desenho_livre=false;
			}
		});
		btn_bresenhan.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dda_abilitado=false;
				bresenhan_abilitado=true;
				circunferencia_abilitado=false;
				desenho_livre=false;
			}
		});
		btn_circunferencia.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dda_abilitado=false;
				bresenhan_abilitado=false;
				circunferencia_abilitado=true;
				desenho_livre=false;
			}
		});
		btn_livre.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dda_abilitado=false;
				bresenhan_abilitado=false;
				circunferencia_abilitado=false;
				desenho_livre=true;
			}
		});
		setLayout(null);
		btn_dda.setBounds(10,10,70,30);
		btn_bresenhan.setBounds(90,10,120,30);
		btn_circunferencia.setBounds(220,10,140,30);
		btn_livre.setBounds(370,10,70,30);
		add(btn_dda);
		add(btn_bresenhan);
		add(btn_circunferencia);
		add(btn_livre);
		addMouseListener(new MouseListener(){
			public void mousePressed (MouseEvent e){
				pressionado=true;
				desenhos.clear();
			}
			public void mouseReleased (MouseEvent e){
				pressionado=false;
			}
			public void mouseExited (MouseEvent e){
			}
			public void mouseEntered (MouseEvent e){
			}
			public void mouseClicked (MouseEvent e){
			}
		});
		new time().start();
	}

	ArrayList<desenho> desenhos = new ArrayList<>();

	public class desenho{
		int x,y;
		public desenho(int x,int y){
			this.x=x;
			this.y=y;
		}
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.WHITE);
		g.fillRect(0,50,1000,607);
		if((!dda_abilitado&&!bresenhan_abilitado&&!circunferencia_abilitado)||desenho_livre){
			for(int i=1;i<desenhos.size();i++){
				int x = desenhos.get(i).x;
				int y = desenhos.get(i).y;
				int x2 = desenhos.get(i-1).x;
				int y2 = desenhos.get(i-1).y;
				g.setColor(Color.BLACK);
				g.drawLine(x2,y2,x,y);
			}
		}		
		else if(!pressionado){
			g.setColor(Color.BLACK);
			int x1=desenhos.get(0).x, y1=desenhos.get(0).y,x2=desenhos.get(desenhos.size()-1).x,y2=desenhos.get(desenhos.size()-1).y,dx,dy;
			if(dda_abilitado){
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
				for(k=1;k<=passos;k++){
					x=x+x_incr;
					y=y+y_incr;
					g.drawLine((int)(Math.round(x)/1.0),(int)(Math.round(y)/1.0),(int)(Math.round(x)/1.0),(int)(Math.round(y)/1.0));
				}
			}
			else if(bresenhan_abilitado){
				int x,y,const1,const2,p;
				dx=Math.abs(x2-x1);
				dy=Math.abs(y2-y1);
				p=2*dy-dx;
				const1=2*dy;
				const2=2*(dy-dx);
				x=x1;
				y=y1;
				g.drawLine(x,y,x,y);
				while(x<x2){
					x=x+1;
					if(p<0){
						p+=const1;
					}
					else{
						p+=const2;
						y++;
					}
					g.drawLine(x,y,x,y);
				}
			}
			else if(circunferencia_abilitado){
				
			}
		}
	}

	public class time extends Thread{
		public void run(){
			while(true){
				if(pressionado){
					try{
						Point cursor = getMousePosition();
						desenhos.add(new desenho(cursor.x,cursor.y));
					}catch(Exception erro){}
				}
				repaint();
			}
		}
	}
}
