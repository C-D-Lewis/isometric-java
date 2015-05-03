# isometric-java

Java port of [Pebble isometric library](https://github.com/C-D-Lewis/isometric).

![screenshot](screenshots/screenshot.png)

Place a png in 'res/test.png' alongside compiled `jar` file to see an
isometrically rendered texture.

## Usage

Add the Java source files to your project, then create a context that provides a
Java `Graphics2D` object. Use this object to run a 'program' using `Isometric`
static methods. For example:

```
public void program(Graphics2D g2d) {
  // Black background
  g2d.setColor(Color.BLACK);
  g2d.fillRect(0, 0, Build.WINDOW_SIZE.width, Build.WINDOW_SIZE.height);
  
  Isometric.drawRect(g2d, new Vec3(100, 100, 100), new Dimension(100, 100), Color.BLUE);
  Isometric.fillRect(g2d, new Vec3(50, 50, 50), new Dimension(50, 50), Color.RED);
  
  Isometric.fillBox(g2d, new Vec3(150, 150, 150), new Dimension(25, 25), 25, Color.YELLOW);
  Isometric.drawBox(g2d, new Vec3(150, 150, 150), new Dimension(25, 25), 25, Color.BLACK);
}
```
