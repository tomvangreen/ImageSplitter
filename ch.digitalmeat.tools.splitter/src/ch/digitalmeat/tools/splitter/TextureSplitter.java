package ch.digitalmeat.tools.splitter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class TextureSplitter {
	public String inputImage;
	public String outputFolder;
	public String namingFormat = "{0}_{1}_{2}";
	public int tileWidth;
	public int tileHeight;
	public boolean skipEmptyTiles;
	public TextureSplitterListener listener;
	public Map<String, String> index = new HashMap<String, String>();

	private void message(String message) {
		if (listener != null) {
			listener.message(message);
		}
	}

	public boolean run() {
		File file = null;
		BufferedImage image = null;
		try {
			file = new File(inputImage);
			image = ImageIO.read(file);

		} catch (Exception ex) {
			message("Failed to load image.");
			return false;
		}
		String imageName = file.getName();
		String nameWithoutExtension = getNameWithoutExtension(imageName);
		String extension = getExtension(imageName);
		message(String.format("Loaded image '%s'.", imageName));
		int width = image.getWidth();
		int height = image.getHeight();
		message(String.format("Dimensions: %d/%d", width, height));

		BufferedImage outputImage = new BufferedImage(tileWidth, tileHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics g = outputImage.getGraphics();
		Graphics2D g2 = (Graphics2D) g;
		int tilesX = width / tileWidth;
		int tilesY = height / tileHeight;
		String updatedFormat = namingFormat.replace("{0}", "%1$s").replace("{1}", "%2$d").replace("{2}", "%3$d").replace("{3}", "%4$d").replace("{4}", "%5$d");
		int exportIndex = 0;
		for (int y = 0; y < tilesY; y++) {
			for (int x = 0; x < tilesX; x++) {
				prepareOutputImage(image, g, g2, x, y);

				if (canExport(outputImage, tileWidth, tileHeight)) {
					if (!saveOutputImage(nameWithoutExtension, extension, outputImage, updatedFormat, x, y, x + y * tilesX, exportIndex++)) {
						return false;
					}
				}

			}
		}

		return true;
	}

	private boolean canExport(BufferedImage outputImage, int tileWidth, int tileHeight) {
		if (!skipEmptyTiles) {
			return true;
		}
		for (int y = 0; y < tileHeight; y++) {
			for (int x = 0; x < tileWidth; x++) {
				int pixel = outputImage.getRGB(x, y);
				// message(String.format("(%d/%d) %d", x, y, pixel));
				if (!((pixel >> 24) == 0x00)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean saveOutputImage(String nameWithoutExtension, String extension, BufferedImage outputImage, String updatedFormat, int x, int y, int tileIndex, int exportIndex) {
		String indexKey = String.format("%d.%d", x, y);
		String outputFileName = index.get(indexKey);
		if (outputFileName == null || outputFileName == "") {
			outputFileName = String.format(updatedFormat, nameWithoutExtension, x, y, tileIndex, exportIndex);
		}
		String fullOutputFileName = String.format("%s\\%s.%s", outputFolder, outputFileName, extension);
		message(String.format("Handling %d/%d: '%s'", x, y, fullOutputFileName));

		File outputFile = new File(fullOutputFileName);
		File parentFile = outputFile.getParentFile();
		parentFile.mkdirs();
		try {
			if (!ImageIO.write(outputImage, extension, outputFile)) {
				message("ImageIO.write failed. No approrpiate writer found.");
				return false;
			}
			return true;
		} catch (Exception ex) {
			message("Failed to write output file.");
			message(ex.getMessage());
			return false;
		}
	}

	private void prepareOutputImage(BufferedImage image, Graphics g, Graphics2D g2, int x, int y) {
		g2.setBackground(new Color(0, 0, 0, 0));
		g2.clearRect(0, 0, tileWidth, tileHeight);

		int srcX = tileWidth * x;
		int srcY = tileHeight * y;
		g.drawImage(image, 0, 0, tileWidth, tileHeight, srcX, srcY, srcX + tileWidth, srcY + tileHeight, null);
	}

	private String getNameWithoutExtension(String imageName) {
		String nameWithoutExtension = null;
		int lastIndex = imageName.lastIndexOf('.');
		if (lastIndex >= 0) {
			nameWithoutExtension = imageName.substring(0, lastIndex);
		} else {
			nameWithoutExtension = imageName;
		}
		return nameWithoutExtension;
	}

	private String getExtension(String imageName) {
		String extension = null;
		int lastIndex = imageName.lastIndexOf('.');
		if (lastIndex >= 0) {
			extension = imageName.substring(lastIndex + 1);
		} else {
			extension = "";
		}
		return extension;
	}

	public interface TextureSplitterListener {
		void message(String message);
	}
}
