package tetris_main;
//Group 3 Tetris
import java.awt.Color;  //Sets up preset colours 
import java.awt.Graphics;
import java.awt.Point;//sets up the dimensions to be manipulated
import java.awt.event.KeyEvent; //allows interactivity with keyboard
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JFrame; // The main window where button and textfield are added 
//for a GUI
import javax.swing.JPanel;//simple container class however doesnt have a title bar 

public class TetrisGame extends JPanel { // Inherits from JPanel in order to make the GUI
	//since its initially serialised it is a marker interface (has no data member and method)
	private static final long serialVersionUID = -8715353373678321308L; // is an identifier that is used to serialise/deserialise an object of a Serialisable class
	 public boolean isgameOn = false;//to be used more for making game over
	 public boolean isFalling = true;
	 public boolean ismodePaused = false; // boolean expressions for the state of the mode being played
	 public boolean isSplashScreen = true;
	
	
	private final Color[] TetrominoColors = { //this shows all available colours 
		Color.cyan, Color.blue, Color.orange, Color.yellow, 
		Color.green, Color.pink, Color.red
	};
	private Point pieceOrigin; //initialises pieceorigin
	private int current_piece; //Initialises current piece
	private int rotation;
	private ArrayList<Integer> nextPieces = new ArrayList<>();
	private long score;//The "long" data type is a 64-bit twos complement integer
	//it can store a very wide range of values
	private Color[][] background;
	private void init() {
		background = new Color[12][24]; //resets the background colour
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 23; j++) {
				if (i == 0 || i == 11 || j == 22) {
					background[i][j] = Color.BLACK; //sets the borders into black
				} else { 
					background[i][j] = Color.BLUE;//sets anything else normally inside into blue
				}
			}
		}
		newPiece();
	}
	public void newPiece() {
		pieceOrigin = new Point(5, 2);
		rotation = 0;//initally there is  always no rotation
		if (nextPieces.isEmpty()) {
			Collections.addAll(nextPieces, 0, 1, 2, 3, 4, 5, 6);
			Collections.shuffle(nextPieces);//randomises what the next pieces will be
		}
		current_piece = nextPieces.get(0);
		nextPieces.remove(0);
	}
	private boolean collidesAt(int x, int y, int rotation) {
		for (Point p : Tetrominos[current_piece][rotation]) {
			if (background[p.x + x][p.y + y] != Color.BLUE) {
				return true;//this is to make sure it does not rotate if it hits the borders
			}
		}
		return false;
	}
	public void rotate(int i) {
		// Rotate the piece clockwise or anti-clockwise
		int newRotation = (rotation + i) % 4;
		if (newRotation < 0) {
			newRotation = 3;
		}
		if (!collidesAt(pieceOrigin.x, pieceOrigin.y, newRotation)) {
			rotation = newRotation;//this is to rotate the image if conditions are met
		}
		repaint();
	}
	public void move(int i) {
		// Move the piece left or right
		if (!collidesAt(pieceOrigin.x + i, pieceOrigin.y, rotation)) {
			pieceOrigin.x += i;	
		}
		repaint();
	}
	private final Point[][][] Tetrominos = {
			{
				{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1) }, // points are for representing a location in a 2d space
				{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3) },
				{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1) },//for the I tetromino
				{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3) } //these are to represent in 2d the initial position of each tetromino
			},  
			{
				{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 0) },//each new point matrix is for a different tetromino shape
				{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 2) },//for the J tetromino
				{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 2) },
				{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 0) }//The tetromino shapes need to be manually created in order to be used
			},
			{
				{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 2) },//point can either be 0 or 1 or 2
				{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 2) },
				{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 0) },
				{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 0) }//for the L tetromino
			},
			{
				{ new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
				{ new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },//for the O tetromino
				{ new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
				{ new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) }
			},
			{
				{ new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1) },//for the S tetromino
				{ new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) },
				{ new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1) },
				{ new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) }
			},
			{
				{ new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(2, 1) },//for the Z tetromino
				{ new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) },
				{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(1, 2) },
				{ new Point(1, 0), new Point(1, 1), new Point(2, 1), new Point(1, 2) }
			},
			{
				{ new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1) },
				{ new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2) },//For the Z tetromino
				{ new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1) },
				{ new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2) }
			}
	};
	public void Down() {
		//moves the piece down manually faster alternative than waiting for piece to go down
		if (!collidesAt(pieceOrigin.x, pieceOrigin.y + 1, rotation)) {
			pieceOrigin.y += 1;
		} else {
			Remainbackground();
		}	
		repaint();
	}
	public void Remainbackground() {
		for (Point p : Tetrominos[current_piece][rotation]) {
			//anytime that piece is rotate it remains on the screen
			background[pieceOrigin.x + p.x][pieceOrigin.y + p.y] = TetrominoColors[current_piece];//this is to remain at same place when rotated
		}
		RowsCleared();
		newPiece();
	}
	public void RowDeleted(int row) {
		//to delete a row when it is full (main component of tetris)
		for (int j = row-1; j > 0; j--) {
			for (int i = 1; i < 11; i++) {
				background[i][j+1] = background[i][j];
			}
		}
	}
	public void RowsCleared() {
		boolean gap;//if there is /is not a gap without a tetromino
		int numClears = 0; //initialised as an integer
		for (int j = 21; j > 0; j--) {
			gap = false;
			for (int i = 1; i < 11; i++) {
				if (background[i][j] == Color.BLUE) {
					gap = true;
					break;
				}
			}
			if (!gap) {
				//this deletes the row if there is no gap present
				RowDeleted(j);
				j += 1;
				numClears += 1;
			}
		}	
		switch (numClears) {//allows the numclears variables to be tested against a list of values
		// the scores scales depending on the number of rows that were cleared
		case 1:
			score += 100;
			break;//to terminate the loop and the program can go into next statement
		case 2:
			score += 300;
			break;
		case 3:
			score += 500;
			break;
		case 4:
			score += 800; 
			//this is for if 4 rows are cleared
			break;
		default:
			break;
		}
	}
	private void drawing(Graphics gui) {		
		//this is to present the tetromino always on the windows 
		//including when it rotated
		gui.setColor(TetrominoColors[current_piece]);
		for (Point p : Tetrominos[current_piece][rotation]) {
			//always fill each quadrant with the piece
			gui.fillRect((p.x + pieceOrigin.x) * 26, 
					   (p.y + pieceOrigin.y) * 26, 
					   25, 25);
		}
	}
	public void modeOn() {
		//used to check if mode is paused
		//will be used in later prototypes when adding paused screen and mode over
        if (ismodePaused) {
            return;
        }
        repaint();
    }
	@Override 
	public void paint(Graphics gui)
	{
		gui.fillRect(0, 0, 26*12, 26*23);//26 is the size of a block
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 23; j++) {
				//paints the colour of the background
				gui.setColor(background[i][j]);
				gui.fillRect(26*i, 26*j, 25, 25);
			}
		}
		gui.setColor(Color.BLACK);//s
		gui.drawString("" + score, 19*12, 25);	
		drawing(gui);
	}
	public static void main(String[] args) {
		JFrame frame = new JFrame("Tetris Prototype");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//close the window when the x button is clicked
		frame.setSize(12*26+10, 26*23+25);
		frame.setVisible(true);
		//setvisible allows the window to be made
		final TetrisGame mode = new TetrisGame();
		//allows the windows to change when there is a new mode
		mode.init();
		frame.add(mode);
		// Controls for Keyboard
		frame.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP://Up Key when pressed
					break;
				case KeyEvent.VK_DOWN ://Down Key when pressed
					mode.Down();
					mode.score += 1; //increases score linearly when button is continously used
					break;
				case KeyEvent.VK_LEFT:	//Left Key
					mode.move(-1);
					mode.rotate(+1);//rotates the objects
					break;
				case KeyEvent.VK_RIGHT://Right Key
					mode.move(+1);
					mode.rotate(-1);//rotate oppositely to left key
					break;
				case KeyEvent.VK_SPACE: //Space Key optional alternative to the Down key
					mode.Down();
					mode.score += 1;
					break;
				} 
			}
			public void keyReleased(KeyEvent e) {
				//make sure when the key is released nothing irregular occurs
			}
		});
		new Thread() {
			@Override public void run() {
				while (true) {
					try {
						Thread.sleep(1000);//time when  running the code 
						//and to stop the code instantaneously reach the bottom when a button is pressed
						mode.Down();
					} catch ( InterruptedException e ) {} // to catch any other alternaives or exceptions
				}
			}
		}.start();	
	}
}
