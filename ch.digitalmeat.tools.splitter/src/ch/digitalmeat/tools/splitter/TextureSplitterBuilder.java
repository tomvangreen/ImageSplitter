package ch.digitalmeat.tools.splitter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class TextureSplitterBuilder {
   private TextureSplitter splitter;

   private TextureSplitter ensure() {
      if (splitter == null) {
         splitter = new TextureSplitter();
      }
      return splitter;
   }

   public ValidationResult validate() {
      ValidationResult result = new ValidationResult();

      ensure();
      if (splitter.inputImage == null) {
         result.validationMessages.add("Input image is not defined.");
      }
      if (splitter.tileWidth == 0) {
         result.validationMessages.add("Tile width is not defined.");
      }
      if (splitter.tileHeight == 0) {
         result.validationMessages.add("Tile height is not defined.");
      }

      result.valid = result.validationMessages.size() == 0;
      return result;
   }

   public TextureSplitter build() {
      TextureSplitter result = ensure();
      splitter = null;
      return result;
   };

   public TextureSplitterBuilder image(String file) {
      ensure().inputImage = file;
      return this;
   }

   public TextureSplitterBuilder output(String folder) {
      ensure().outputFolder = folder;
      return this;
   }

   public TextureSplitterBuilder tileWidth(int width) {
      ensure().tileWidth = width;
      return this;
   }

   public TextureSplitterBuilder tileHeight(int height) {
      ensure().tileHeight = height;
      return this;
   }

   public TextureSplitterBuilder format(String format) {
      ensure().namingFormat = format;
      return this;
   }

   public TextureSplitterBuilder skipEmptyTiles(boolean skip) {
      ensure().skipEmptyTiles = skip;
      return this;
   }

   public TextureSplitterBuilder indexFile(String fileName) {
      try {
         File file = new File(fileName);
         if (!file.exists()) {
            return this;
         }

         BufferedReader reader = new BufferedReader(new FileReader(file));
         ensure();
         String line = null;
         while ((line = reader.readLine()) != null) {
            if (!line.startsWith("#")) {
               String[] splitted = line.split(":");
               if (splitted.length == 2) {
                  splitter.index.put(splitted[0], splitted[1]);
               }
            }
         }

         return this;
      } catch (Exception ex) {
         throw new RuntimeException("Failed to load index file.", ex);
      }
   }

   public class ValidationResult {
      public boolean valid;
      public List<String> validationMessages = new ArrayList<String>();
   }
}
