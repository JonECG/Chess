/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.jpearl.chess.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import edu.neumont.pro180.jpearl.chess.environment.Cell;

public class CellPanel extends JPanel
{
	private static final long serialVersionUID = 3142869218407839386L;
	private Cell cell;
	
	public CellPanel( Cell cell )
	{
		this.cell = cell;
		setLayout( new BorderLayout() );
		unhighlight();
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent( g );
		g.setColor( Color.BLACK );
		g.drawRect( 0 , 0 , getWidth()-1, getHeight()-1 );
		if (cell.hasPiece() )
		{
			BufferedImage pieceImage = cell.getPiece().getImage();
			drawImageWithAspect( g, pieceImage, new IntegerRectangle( 0, 0, getWidth(), getHeight() ) );
		}
	}
	
	private static void drawImageWithAspect( Graphics g, BufferedImage image, IntegerRectangle area )
	{
		double imageRatio = ((double) image.getWidth())/image.getHeight();
		double spaceRatio = ((double) area.getWidth())/area.getHeight();
		int resultWidth;
		int resultHeight;
		
		if (imageRatio > spaceRatio)
		{
			resultWidth = area.getWidth();
			resultHeight = (int) (area.getWidth()/imageRatio);
		}
		else
		{
			resultWidth = (int) (area.getHeight()*imageRatio);
			resultHeight = area.getHeight();
		}
		
		int widthPad = (area.getWidth()-resultWidth)/2;
		int heightPad = (area.getHeight()-resultHeight)/2;
		
		g.drawImage( image, area.getX()+widthPad, area.getY()+heightPad, area.getX()+resultWidth+widthPad, area.getY()+resultHeight+heightPad,
				0, 0, image.getWidth(), image.getHeight(), null );
	}
	
	public void highlight( Color highlight )
	{
		setBackground( highlight );
	}
	
	public void unhighlight()
	{
		if ( (cell.getLocation().getX() + cell.getLocation().getY()) % 2 == 0 )
		{
			setBackground( Color.LIGHT_GRAY );
		}
		else
		{
			setBackground( Color.WHITE );
		}
	}

	public Cell getRepresentedCell()
	{
		return cell;
	}
	
	
}
