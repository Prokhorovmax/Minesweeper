import java.awt.*;

public class Cell {

    private Color color;
    private Polygon polygon;
    private boolean isOpen = false;

    public Cell(Point point) {
        int[] xPoints = {point.x, point.x - 15, point.x - 15, point.x, point.x + 15, point.x + 15};
        int[] yPoints = {point.y, point.y + 10, point.y + 25, point.y + 35, point.y + 25, point.y + 10};
        this.polygon = new Polygon(xPoints, yPoints, 6);
        this.color = Color.LIGHT_GRAY;
    }

    public Color getColor() {
        return this.color;
    }
    public void setColor(Color color){
        this.color = color;
    }

    public Polygon getPolygon() {
        return this.polygon;
    }

    public boolean isOpen(){
        return this.isOpen;
    }

    public void setOpen(){
        this.isOpen = true;
        this.color = Color.WHITE;
    }
}