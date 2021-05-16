import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;



public class MineSweeper extends JFrame {
	private static final long serialVersionUID = 1L;
	public MineButton[][] board;
	JPanel boardPanel = new JPanel();
	JPanel controlPanel = new JPanel();
	JLabel counterLabel = new JLabel("Mine Count: ");
	JLabel counterDisplay = new JLabel();
	JButton clearButton = new JButton(":)");
	int minesCounted;
	int count = 0;
	int size;

	public static void main(String[] args) {
		new MineSweeper();

	}

	public MineSweeper() {
		size = Integer.parseInt((JOptionPane.showInputDialog("Enter the board size")));
		board = new MineButton[size + 2][size + 2];

		for (int r = 0; r < size + 2; r++)
			for (int c = 0; c < size + 2; c++) {
				board[r][c] = new MineButton(r, c);
				board[r][c].addMouseListener(new MineListener(r, c));

			}
		
		setLayout(new BorderLayout());
		boardPanel.setLayout(new GridLayout(size, size));
		for(int r = 1; r < size + 1; r++)
			for(int c = 1; c < size + 1; c++) {
				boardPanel.add(board[r][c]);

			}


		add(boardPanel, BorderLayout.CENTER);
		controlPanel.add(counterLabel);
		controlPanel.add(counterDisplay);
		controlPanel.add(clearButton);
		add(controlPanel, BorderLayout.NORTH);
		clearButton.addActionListener(new ClearListener());
		setSize(size * 50, size * 50);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);



		for(int r = 0; r < size + 2; r++) {
			board[r][0].mine = false;
			board[r][0].exposed = true;
			board[r][size + 1].mine = false;
			board[r][size + 1].exposed = true;
			board[size + 1][r].mine = false;
			board[size + 1][r].exposed = true;
			board[0][r].mine = false;
			board[0][r].exposed = true;
		}
		setVisible(true);

		for(int r = 0; r < size + 2; r++) 
			for(int c = 0; c < size + 2; c++) {
				if(board[r][c].mine)
					count++;
			}

		minesCounted = count;
		counterDisplay.setText("" + count);

	}

	class MineButton extends JButton {

		private static final long serialVersionUID = 1L;
		private int row;
		private int col;
		boolean mine = false;
		boolean exposed = false;
		boolean flagged = false;

		public MineButton(int row, int col) { 
			this.row = row;
			this.col = col;
			if(Math.random() < 0.15) {
				mine = true;
			}
		}
	}

	public void expose(int r, int c) {

		if(board[r][c].exposed || board[r][c].flagged)
			return;
		if(board[r][c].mine) {
			board[r][c].setBackground(Color.RED);	
			board[r][c].setText("X");
			board[r][c].exposed = true;
			for(int i = 1; i < size + 1; i++) {
				for(int j = 1; j < size + 1; j++) {
					if(board[i][j].mine)
						expose(i, j);
				}
			}
			JOptionPane.showMessageDialog(null, "Game Over!");
			System.exit(ABORT);
		}


		else {
			board[r][c].setBackground(Color.WHITE);
			board[r][c].exposed = true;
			int counter = 0;
			for (int i = -1; i <= 1; i++)
				for (int j = -1; j <= 1; j++)
					if (board[r + i][c + j].mine)
						counter++;

			if(counter > 0)
				board[r][c].setText("" + counter);
			else {
				for (int i = -1; i <= 1; i++)
					for (int j = -1; j <= 1; j++)
						expose(r + i, c + j);
			}
		}
	}

	// count number of adjoining squares that are mines
	// setText to number of mines if  > 0
	// Expose all adjacent squares if mines == 0




	public void flagged(int r, int c) {

		if(board[r][c].flagged) {
			board[r][c].setText(null);
			board[r][c].flagged = false;
			board[r][c].setBackground(null);
			count++;
		}
		else {
			board[r][c].setText("|>");
			board[r][c].flagged = true;
			board[r][c].setBackground(Color.YELLOW);
			count--;
		}
		
		if(board[r][c].mine && board[r][c].flagged)
			minesCounted--;
		
		counterDisplay.setText("" + count);
		
		if(minesCounted == 0) {
			JOptionPane.showMessageDialog(null, "YOU WIN!");
			new MineSweeper();
		}
	}




class MineListener implements MouseListener {
	int row, col;

	public MineListener(int r, int c) {
		row = r;
		col = c;
	}

	public void mouseClicked(MouseEvent e) {
		int buttonNumber;
		buttonNumber = e.getButton();
		if(buttonNumber == 1) {
			expose(row, col);


		}
		else if(buttonNumber == 3) {
			flagged(row,col);
		}
	}


	public void mouseEntered(MouseEvent e) {

	}


	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}


	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}


	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}




class ClearListener implements ActionListener {


	public void actionPerformed(ActionEvent arg0) {

		for(int r = 0; r < size + 2; r++)
			for(int c = 0; c < size + 2; c++)
			{
				board[r][c].setBackground(null);
				board[r][c].setText(null);
			}

	}

}

}

