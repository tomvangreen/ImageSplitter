
ImageSplitter
=============

Image Splitter is a simple tool to split up an image into multiple tiles of the same size. It has very basic functionality and is currently only limited for png images.

##Command Line:

`java -jar splitter.jar -i:<InputImage> -o:<OutputFolder> -w:<TileWidth> -h:<TileHeight> -s:<SkipEmptyFiles> -f:<OutputFileFormat> -x:<OutputNamesFile>`

`java -jar splitter.jar -i:spritesheet.png -o:tiles -w:32 -h:32 -f:{0}_l{2}_t{1} -s:1 -x:spritesheet.txt`

###Parameter

* InputImage (-i): Input image (required)
* OutputFolder (-w): Tile width (required)
* TileWidth (-h): Tile height (required)
* TileHeight (-o): Output folder
* SkipEmptyTiles (-s): Skips tiles that are completely transparent.
* OutputFileFormat (-f): The output format of the created files. You can define the wildcards {0} for the original file name (without extension), {1} for x position and {2} for y position in the sprite sheet.
* OutputNamesFile (-x): A special file containing the position in the tileset and a name. When no name has been defined for a given index, the OutputFileFormat is used instead.


#### OutputNamesFile
The output names file contains information about how the exported images should be named. You can define one name per line. The format is: `<X>.<Y>:<Name>`, where X and Y define the index position in the sprite sheet (counting from 0).

An example file would look like:

    0.0:Head
    1.0:Neck
    2.0:Torso
    3.0:LeftArm
    0.1:RightArm
    1.1:Waist
    2.1:LeftLeg
    3.1:RightLeg

## Using in Code
If you want to use the splitter inside your java code, you can either work with the TextureSplitter class directly or use the TextureSplitterBuilder to instanciate the splitter for you. The OutputFilesNames feature is implemented in the builder. If you work with the TextureSplitter class directly, you have to add the index names manually. Just check the builder class to see how this works.

