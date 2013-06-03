/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.jpearl.chess.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class CachedImageLoader
{
	private static HashMap<String,BufferedImage> loadedImages = new HashMap<String,BufferedImage>();
	
	private CachedImageLoader()
	{
		//Unconstructable
	}
	
	public static BufferedImage loadImageFromPath( String path )
	{
		BufferedImage result = loadedImages.get( path );
		if (result == null)
		{
			try
			{
				result = ImageIO.read( new File(path) );
			}
			catch ( IOException e )
			{
				e.printStackTrace();
			}
		}
		return result;
	}
}
