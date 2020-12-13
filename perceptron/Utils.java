package sample;

import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Utils {

    public static int[][] createBoolMatrix(WritableImage img){
        int h = (int) img.getHeight();
        int w = (int) img.getWidth();
        int[][] bitMatrix = new int[h][w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                Color color = img.getPixelReader().getColor(j,i);
                if(color.getBlue() != 1 || color.getRed() != 1 || color.getGreen() != 1){
                    bitMatrix[i][j] = 1;
                }
            }
        }
        return bitMatrix;
    }

    public static void scaleImage(WritableImage img){
        if(img == null){
             return;
        }
        int[][] boolMatrix = createBoolMatrix(img);
        int xmin,xmax,ymin,ymax;

        double h = img.getHeight();
        double w = img.getWidth();
        int[][] tmp = new int[(int) h][(int) w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                tmp[i][j] = 0;
            }
        }
        xmin= (int) w;
        xmax=-1;
        ymin= (int) h;
        ymax=-1;

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if(boolMatrix[i][j] == 1){
                    if (j<xmin)
                        xmin=j;
                    if (j>xmax)
                        xmax=j;
                    if (i<ymin)
                        ymin=i;
                    if (i>ymax)
                        ymax=i;
                }
            }
        }

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                tmp[i][j] = boolMatrix[ymin+((int)((i/h)*(ymax-ymin+1)))][xmin+((int)((j/w)*(xmax-xmin+1)))];
            }
        }

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                boolMatrix[i][j] = tmp[i][j];
            }
        }

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                Color color = boolMatrix[i][j] == 1 ? Color.RED : Color.WHITE;
                img.getPixelWriter().setColor(j,i, color);
            }
        }
    }
}
