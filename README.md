ImageSplitter
=============

Image Splitter is a simple tool to split up an image into multiple tiles of the same size. It has very basic functionality and is currently only limited for png images.

Command Line:

java -jar splitter.jar -i:<<InputImage>> -o:<OutputFolder> -w:<TileWidth> -h:<TileHeight> -s:<SkipEmptyFiles> -f:<OutputFileFormat> -x:<OutputNameIndices>

Parameter
InputImage (-i): Input image (required)
OutputFolder (-w): Tile width (required)
TileWidth (-h): Tile height (required)
TileHeight (-o): Output folder
SkipEmptyTiles (-s): Skips tiles that are completely transparent.
OutputFileFormat (-f): The output format of the created files. You can define the wildcards {0} for the original file name (without extension), {1} for x position and {2} for y position in the sprite sheet.
OutputNameIndices (-x): A special file containing the position in the tileset and a name. When no name has been defined for a given index, the OutputFileFormat is used instead.
            } else if ("-x:".equals(param)) {
               builder.indexFile(value);



java -jar splitter.jar -i:spritesheet.png -o:tiles -w:32 -h:32 -f:{0}_l{2}_t{1} -s:1 -x:spritesheet.txt

 



