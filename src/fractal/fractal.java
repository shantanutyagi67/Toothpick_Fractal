package fractal;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;

class toothpick {
	int ax,ay,bx,by;
	boolean newTP = true;
	public int getAx() {
		return ax;
	}
	public int getAy() {
		return ay;
	}
	public int getBx() {
		return bx;
	}
	public int getBy() {
		return by;
	}
	int dir;
	int len=30;
	toothpick(int x,int y,int d)
	{
		dir=d;
		if(dir==1)
		{
			ax=x-len/2;
			bx=x+len/2;
			ay=y;
			by=y;
		}
		else
		{
			ax=x;
			bx=x;
			ay=y-len/2;
			by=y+len/2;
		}	
	}
	boolean intersects(int x,int y)
	{
		if(ax==x&&ay==y)
			return true;
		else if(bx==x&&by==y)
			return true;
			else return false;
	}
	public toothpick createL(ArrayList<toothpick> others)
	{
		boolean available = true;
		for(toothpick other: others)
		{
			if(other!= this && other.intersects(ax,ay))
			{
				available = false;
			}
		}
		if(available)
			return new toothpick(ax,ay,dir*-1 );
		else return null;
	}
	public toothpick createR(ArrayList<toothpick> others)
	{
		boolean available = true;
		for(toothpick other: others)
		{
			if(other!= this && other.intersects(bx,by))
			{
				available = false;
			}
		}
		if(available)
			return new toothpick(bx,by,dir*-1 );
		else return null;
	}
}

public class fractal extends JComponent implements MouseListener, Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6349745891266085613L;
	static ArrayList<toothpick> picks = new ArrayList<toothpick>();
	static JFrame frame = new JFrame("Fractal");
	public static void main(String[] args) {
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(0,0,1920,1080);
		frame.getContentPane().setBackground(Color.BLACK);
		frame.getContentPane().add(new fractal());
		frame.setVisible(true);
		picks.add(new toothpick(0,0,1));
		//Graphics g = frame.getGraphics();
		//Graphics2D g2D = (Graphics2D)g;
	}
	
	double scale = 4;
		public void paint(Graphics g)
		{
			Graphics2D g2D = (Graphics2D) g;
			RenderingHints rh = new RenderingHints(
	                RenderingHints.KEY_ANTIALIASING,
	                RenderingHints.VALUE_ANTIALIAS_ON);

	        rh.put(RenderingHints.KEY_RENDERING,
	               RenderingHints.VALUE_RENDER_QUALITY);

	        g2D.setRenderingHints(rh);
	        g2D.translate(frame.getWidth()/2, frame.getHeight()/2);
	        if(scale>=0.3)
	        	g2D.scale(scale, scale);
	        else
	        	g2D.scale(0.3, 0.3);
	        scale/=1.013;
			addMouseListener(this);
			ArrayList<toothpick> next = new ArrayList<toothpick>();
			for(toothpick t: picks)
			{
				g2D.setColor(Color.WHITE);
				if(t.newTP) 
					g2D.setColor(Color.RED);
				//g2D.scale(1.1, 1.1);
				g2D.drawLine(t.getAx(), t.getAy(), t.getBx(), t.getBy());
			}
			for(toothpick t: picks)
			{
				if(t.newTP) {
				toothpick nextL = t.createL(picks);
				toothpick nextR = t.createR(picks);
				if(nextL != null)
					next.add(nextL);
				if(nextR != null)
					next.add(nextR);
				t.newTP = false;
				}
			}
			picks.addAll(next);
			//g2D.scale(2, 2);
			run();
			//repaint();
		}
		
		public void mousePressed(MouseEvent me)
		{
			//repaint();
		}
		public void mouseClicked(MouseEvent me) {
			//repaint();
		}
		public void mouseEntered(MouseEvent me) {
			//repaint();
		}
		public void mouseExited(MouseEvent me) {
			//repaint();
		}
		public void mouseReleased(MouseEvent me) {
			//repaint();
		}
		@Override
		public void run() {
			try {
				Thread.sleep(75);
				repaint();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
  