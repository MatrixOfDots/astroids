import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



public class roid extends JFrame{
    GamePanel game = new GamePanel();

    public roid(){
        super("Move the Box");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(game);
		pack();  // set the size of my Frame exactly big enough to hold the contents
		setVisible(true);
    }
    public static void main(String[] args){
        new roid();
    }
}

class SpaceShip{     
    public int[] xpoints, ypoints;
    public int npoints;
    public static int midx, midy;

    public SpaceShip(int[] x, int[] y, int n){
        xpoints = x;
        ypoints = y;
        npoints = n;
        midx = xpoints[2];
        midy = ypoints[0];
        
    }

    public void translate(int movex, int movey){
        midx += movex;
        midy += movey;
        for(int i =0; i < npoints; i++){
            xpoints[i] += movex;
            ypoints[i] += movey;
        }
    }
}

class GamePanel extends JPanel implements KeyListener, ActionListener, MouseListener{
    private int ballx, bally;
    private int mousex, mousey;
    private int posx = 400, posy = 300;
    private SpaceShip ship = new SpaceShip(new int[] {posx - 10, posx + 10, posx, posx +15, posx, posx - 15},
     new int[] {posy, posy, posy - 25, posy + 10, posy - 25, posy + 10},
      6);

    private int tip, rCorner, lCorner;
    private boolean []keys;
	Timer timer;
	Image back;

    public GamePanel(){
		keys = new boolean[KeyEvent.KEY_LAST+1];
		setPreferredSize(new Dimension(800, 600));
		setFocusable(true);
		requestFocus();
		addKeyListener(this);
		addMouseListener(this);
		timer = new Timer(20, this);
		timer.start();
	}

    public void move(){
        int movex =0;
        int movey=0;
        movex += (keys[KeyEvent.VK_LEFT] ? -5 : keys[KeyEvent.VK_RIGHT] ? 5 : 0);
        movey += (keys[KeyEvent.VK_UP] ? -5 : keys[KeyEvent.VK_DOWN] ? 5 : 0);
        ship.translate(movex, movey);
        ship.translate((SpaceShip.midx >= 800 ? -800 : SpaceShip.midx <=0 ? 800 : 0), (SpaceShip.midy >=600 ? -600 : SpaceShip.midy <= 0 ? 600 : 0));

    }


    @Override
	public void actionPerformed(ActionEvent e){
		move(); // never draw in move
		repaint(); // only draw
	}
	
	@Override
	public void keyReleased(KeyEvent ke){
		int key = ke.getKeyCode();
		keys[key] = false;
	}	
	
	@Override
	public void keyPressed(KeyEvent ke){
		int key = ke.getKeyCode();
		keys[key] = true;
	}
	
	@Override
	public void keyTyped(KeyEvent ke){}
	@Override
	public void	mouseClicked(MouseEvent e){}

	@Override
	public void	mouseEntered(MouseEvent e){}

	@Override
	public void	mouseExited(MouseEvent e){}

	@Override
	public void	mousePressed(MouseEvent e){
		System.out.println(e.getButton()+" "+ MouseEvent.BUTTON1);
        mousex = e.getX();
        mousey = e.getY();
	}

	@Override
	public void	mouseReleased(MouseEvent e){}
 
	@Override
	public void paint(Graphics g){
        
        g.setColor(Color.BLACK);
        g.fillRect(0,0,getWidth(), getHeight());
        g.setColor(Color.WHITE);
        //g.fillOval(ballx-20,bally-20,40,40);
        g.drawPolyline(ship.xpoints, ship.ypoints, ship.npoints);
    }

}
