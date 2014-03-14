package ch.digitalmeat.tools.splitter;

import ch.digitalmeat.tools.splitter.TextureSplitter.TextureSplitterListener;

public class CLI {
   public static void main(String[] args) {
      TextureSplitterBuilder builder = new TextureSplitterBuilder();
      for (String arg : args) {
         try {
            String param = arg.substring(0, 3);
            String value = arg.substring(3);
            if ("-i:".equals(param)) {
               builder.image(value);
            } else if ("-o:".equals(param)) {
               builder.output(value);
            } else if ("-f:".equals(param)) {
               builder.format(value);
            } else if ("-w:".equals(param)) {
               builder.tileWidth(Integer.parseInt(value));
            } else if ("-h:".equals(param)) {
               builder.tileHeight(Integer.parseInt(value));
            } else if ("-s:".equals(param)) {
               builder.skipEmptyTiles(Integer.parseInt(value) == 1);
            } else if ("-x:".equals(param)) {
               builder.indexFile(value);
            }
         } catch (Exception ex) {
            System.out.println("Failed to read argument.");
            System.out.println(arg);
            System.out.println(ex.getMessage());
         }
      }

      TextureSplitterBuilder.ValidationResult result = builder.validate();
      if (!result.valid) {
         System.out.println("Invalid state: ");
         for (String message : result.validationMessages) {
            System.out.println(message);
         }
         return;
      }

      TextureSplitter splitter = builder.build();
      splitter.listener = new TextureSplitterListener() {

         @Override
         public void message(String message) {
            System.out.println(message);

         }
      };
      if (splitter.run()) {
         System.out.println("Completed successfully.");
      } else {
         System.out.println("Failed");
      }
   }
}
